package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.realestate.certificate.core.bo.BdcBdcqzhBO;
import cn.gtmap.realestate.certificate.core.service.BdcXmService;
import cn.gtmap.realestate.certificate.core.service.BdcXtZsbhmbService;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtZsbhmbDO;
import cn.gtmap.realestate.common.core.domain.BdcZhDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.EntityNotFoundException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import com.alibaba.fastjson.JSON;
import io.swagger.models.auth.In;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/10
 * @description  不动产权证号处理公共逻辑类
 */
@Service
public class BdcBdcqzhGgServiceImpl {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcBdcqzhGgServiceImpl.class);

    @Autowired
    private BdcXmService bdcXmService;

    @Autowired
    private BdcZsService bdcZsService;

    @Autowired
    private BdcXtZsbhmbService bdcXtZsbhmbService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private EntityMapper entityMapper;

    /**
     * 证号是否区分部门，如果没有配置则默认1，即默认是需要按照登记部门区分
     */
    @Value("${zhqfbm:1}")
    private Integer zhqfbm;

    /**
     * 证号是否区分区县代码，如果没有配置则默认1，即默认是需要按照区县代码区分
     */
    @Value("${zhqfqxdm:1}")
    private Integer zhqfqxdm;

    /**
     * 是否将证号赋值到二维码内容中 true/false 不配置默认false
     */
    @Value("${ewmnrReadBdczh:false}")
    private boolean ewmnrReadBdczh;

    /**
     * 生成证号是否采用锁方案，默认true采用
     */
    @Value("${bdcqzh.needlock:true}")
    private Boolean bdcqzhNeedLock;

    /**
     * 共用统一流水号的区划设置
     */
    @Value("${zhgylsh:}")
    private String zhgylsh;

    /**
     * 是否启用跳号回收功能
     */
    @Value("${quartz.bdcqzhthhs.enable:false}")
    private Boolean enableBdcqzhthhs;


    /**
     * 获取证号模板配置以及数据库最大证号
     */
    @PostConstruct
    public void init() {
        Example example = new Example(BdcXtZsbhmbDO.class);
        example.createCriteria().andEqualTo("nf", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        List<BdcXtZsbhmbDO> zsbhmbDOList = entityMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(zsbhmbDOList)) {
            LOGGER.error("未查询到当前年份证号配置模板");
            return;
        }

        for(BdcXtZsbhmbDO zsbhmbDO : zsbhmbDOList) {
            String key = getZhmbRedisKey(zsbhmbDO.getNf(), zsbhmbDO.getQxdm(), zsbhmbDO.getDjbmdm(), false);

            // 将不同编号规则缓存到Redis
            redisUtils.addHashValue(Constants.REDIS_BDCQZH_MBPZ, key, JSON.toJSONString(zsbhmbDO));

            // 获取每个证书类型编号规则对应的最大编号
            for(int zslx = 1; zslx <= 3; zslx++) {
                BdcBdcqzhBO qo = new BdcBdcqzhBO();
                qo.setNf(zsbhmbDO.getNf());
                qo.setZhqfqxdm(zhqfqxdm);
                qo.setQxdm(zsbhmbDO.getQxdm());
                qo.setZslx(zslx);
                qo.setZhqfbm(zhqfbm);
                qo.setDjbmdm(zsbhmbDO.getDjbmdm());
                LOGGER.debug("查询证号序列号最大值参数：{}", JSON.toJSONString(qo));
                Integer maxSxh = bdcZsService.queryMaxSxh(qo);

                // 如果设置的初始号大于数据库中实际最大号，以设置的初始号为准
                if(null != zsbhmbDO.getCssxh() && zsbhmbDO.getCssxh() > maxSxh) {
                    maxSxh = zsbhmbDO.getCssxh();
                }

                String zhxlhKey = getZhxlhRedisKey(zsbhmbDO.getNf(), zsbhmbDO.getQxdm(), zsbhmbDO.getDjbmdm(), zslx);
                redisUtils.addLongValue(zhxlhKey, maxSxh.longValue());
                LOGGER.debug("缓存Redis证号序列号：{}，值：{}", zhxlhKey, maxSxh);
            }
        }
    }

    /**
     * 从Redis获取下一个序列号
     */
    public Integer getNextSxhFromRedis(BdcBdcqzhBO bdcBdcqzhBO, Long step) {
        if(enableBdcqzhthhs && 1 == step.intValue()) {
            // 只有生成单个证号时候考虑回收跳号，批量场景下如果回收会导致处理复杂很容易造成和已有号冲突
            String zhthhsKey = getZhthhsRedisKey(bdcBdcqzhBO.getNf(), bdcBdcqzhBO.getQxdm(), bdcBdcqzhBO.getDjbmdm(), bdcBdcqzhBO.getZslx());
            String val = redisUtils.getSetRandomValue(zhthhsKey);
            if(StringUtils.isNotBlank(val) && !"null".equals(val)) {
                LOGGER.info("证书{}获取跳号{}", bdcBdcqzhBO.getZsid(), val);
                return Integer.parseInt(val);
            }
        }

        String zhxlhKey = getZhxlhRedisKey(bdcBdcqzhBO.getNf(), bdcBdcqzhBO.getQxdm(), bdcBdcqzhBO.getDjbmdm(), bdcBdcqzhBO.getZslx());
        return redisUtils.incr(zhxlhKey, step).intValue();
    }

    /**
     * 生成Redis缓存不同类型证号序列号取值Key
     * @param nf 年份
     * @param qxdm 区县代码
     * @param djbmdm 登记部门代码
     * @param zslx 证书类型
     * @return String 键名
     */
    public String getZhxlhRedisKey(String nf, String qxdm, String djbmdm, Integer zslx) {
        return Constants.REDIS_ZHXLH + getZhmbRedisKey(nf, qxdm, djbmdm, true) + "_" + zslx;
    }

    /**
     * 证号跳号回收将跳的号存到redis，这里返回对应的key
     * @param nf 年份
     * @param qxdm 区县代码
     * @param djbmdm 登记部门代码
     * @param zslx 证书类型
     * @return String 键名
     */
    public String getZhthhsRedisKey(String nf, String qxdm, String djbmdm, Integer zslx) {
        return Constants.REDIS_ZHTHHS + getZhmbRedisKey(nf, qxdm, djbmdm, true) + "_" + zslx;
    }

    /**
     * 生成Redis缓存证号模板配置 Hash Key
     * @param nf 年份
     * @param qxdm 区县代码
     * @param djbmdm 登记部门代码
     * @param fzqxdm 是否用分组区县代码作为key元素
     * @return String 键名
     */
    public String getZhmbRedisKey(String nf, String qxdm, String djbmdm, boolean fzqxdm) {
        String key = nf;

        if(null != zhqfqxdm && 1 == zhqfqxdm.intValue()) {
            key += "_" + this.getQxdmKey(qxdm, fzqxdm);
        }
        if(null != zhqfbm && 1 == zhqfbm.intValue()) {
            key += "_" + djbmdm;
        }
        return key;
    }

    /**
     * 如果多个区划共用一个流水号，则用所在分组区划作为Redis键元素
     * @param xmqxdm 当前项目区县代码
     * @param fzqxdm 是否用分组区县代码作为key元素（缓存证号模板配置还是一个个区划一条记录，缓存最大流水号时候可能需要一个分组一个key）
     * @return {String} 区县代码
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    public String getQxdmKey(String xmqxdm, boolean fzqxdm) {
        if(StringUtils.isBlank(zhgylsh) || !fzqxdm) {
            return xmqxdm;
        }

        String[] array = zhgylsh.split(CommonConstantUtils.ZF_YW_FH);
        for(String qxdms : array) {
            String[] qxdmArray = qxdms.split(CommonConstantUtils.ZF_YW_DH);
            for(String qxdm : qxdmArray) {
                if(StringUtils.equals(qxdm, xmqxdm)) {
                    return qxdms;
                }
            }
        }
        return xmqxdm;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   xmid 项目ID
     * @description  获取证号处理信息数据
     */
    public BdcBdcqzhBO getBdcqzhBO(String xmid){
        // 获取项目
        BdcXmDO bdcXmDO = this.getBdcXm(xmid);

        // 获取证号模板
        BdcXtZsbhmbDO bdcXtZsbhmbDO;
        if(bdcqzhNeedLock && null != bdcXmDO) {
            bdcXtZsbhmbDO = this.getBdcXtZsbhmb(bdcXmDO);
        } else {
            // 从Redis缓存获取
            String key = getZhmbRedisKey(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)), bdcXmDO.getQxdm(), bdcXmDO.getDjbmdm(), false);
            String json = redisUtils.getHashValue(Constants.REDIS_BDCQZH_MBPZ, key);

            if(StringUtils.isBlank(json) || "null".equals(json)) {
                // 如果为空再去数据库取下，例如2021年首次获取没有Redis缓存
                bdcXtZsbhmbDO = this.getBdcXtZsbhmb(bdcXmDO);
                redisUtils.addHashValue(Constants.REDIS_BDCQZH_MBPZ, key, JSON.toJSONString(bdcXtZsbhmbDO));
            } else {
                bdcXtZsbhmbDO = JSON.parseObject(json, BdcXtZsbhmbDO.class);
            }
        }

        if(null == bdcXtZsbhmbDO) {
            throw new AppException("未获取到证书编号模板");
        }

        BdcBdcqzhBO bdcBdcqzhBO = new BdcBdcqzhBO(bdcXmDO, bdcXtZsbhmbDO);
        bdcBdcqzhBO.setXmid(xmid);

        if(null == zhqfqxdm){
            zhqfqxdm = 1;
        }
        bdcBdcqzhBO.setZhqfqxdm(zhqfqxdm);

        if(null == zhqfbm){
            zhqfbm = 1;
        }
        bdcBdcqzhBO.setZhqfbm(zhqfbm);
        return bdcBdcqzhBO;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   bdcXmDO 不动产项目
     * @return  {BdcXtZsbhmbDO}  证号模板
     * @description  获取当前年份对应区域的证号模板配置
     */
    public BdcXtZsbhmbDO getBdcXtZsbhmb(BdcXmDO bdcXmDO) {
        BdcXtZsbhmbDO bdcXtZsbhmbDO = bdcXtZsbhmbService.queryBdcXtZsbhmb(bdcXmDO);

        if(null == bdcXtZsbhmbDO){
            LOGGER.error("项目ID{}未查询到关联证书编号模板，获取不动产权证号终止！", bdcXmDO.getXmid());
            throw new EntityNotFoundException("项目无对应证书编号模板！");
        }
        return bdcXtZsbhmbDO;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   xmid 项目ID
     * @return  {BdcXmDO} 不动产项目
     * @description  根据项目ID获取对应项目
     */
    public BdcXmDO getBdcXm(String xmid) {
        try{
            BdcXmDO bdcXmDO = bdcXmService.queryBdcXm(xmid);

            if(null == bdcXmDO){
                LOGGER.error("生成证号查询{}项目不存在，获取不动产权证号终止！", xmid);
                throw new EntityNotFoundException("不存在对应项目，获取不动产权证号终止！");
            }
            return bdcXmDO;
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new EntityNotFoundException("不存在对应项目，获取不动产权证号终止！");
        }
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   xmid 项目ID
     * @return  {List} 证书集合
     * @description  获取项目关联的证书记录
     */
    public List<BdcZsDO> getBdcZs(String xmid) {
        List<BdcZsDO> bdcZsDOList = bdcZsService.queryBdcZsByXmid(xmid);

        if(CollectionUtils.isEmpty(bdcZsDOList)){
            LOGGER.warn("项目ID{}未查询到关联证书信息，获取不动产权证号终止！", xmid);
            throw new EntityNotFoundException("项目无对应证书记录！");
        }
        return bdcZsDOList;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   bdcBdcqzhDTO 证号信息
     * @description  保存不动产权证号
     */
    public int saveBdcqzh(BdcBdcqzhDTO bdcBdcqzhDTO) {
        if(null == bdcBdcqzhDTO || StringUtils.isBlank(bdcBdcqzhDTO.getZsid())){
            return 0;
        }

        BdcZsDO bdcZsDO = new BdcZsDO();
        bdcZsDO.setZsid(bdcBdcqzhDTO.getZsid());
        bdcZsDO.setBdcqzh(bdcBdcqzhDTO.getBdcqzh());
        bdcZsDO.setZhlsh(bdcBdcqzhDTO.getZhlsh());
        bdcZsDO.setNf(bdcBdcqzhDTO.getNf());
        bdcZsDO.setSqsjc(bdcBdcqzhDTO.getSqsjc());
        bdcZsDO.setSzsxqc(bdcBdcqzhDTO.getSzsxqc());
        bdcZsDO.setZhxlh(bdcBdcqzhDTO.getZhxlh());
        bdcZsDO.setBdcqzhjc((bdcZsDO.getNf() + bdcZsDO.getZhlsh()).replaceAll("[^0-9]+", ""));
        //更新二维码内容
        if(ewmnrReadBdczh){
            bdcZsDO.setEwmnr(bdcBdcqzhDTO.getBdcqzh());
        }
        try {
            LOGGER.info("保存生成证号：{}", JSON.toJSONString(bdcZsDO));
            int count = bdcZsService.updateBdcZs(bdcZsDO);

            if(count > 0) {
                // 更新成功则保存序列号到Redis，为了其它请求取号时判断
                String zhxlh = String.valueOf(bdcBdcqzhDTO.getZhxlh());
                redisUtils.addHashValue(Constants.REDIS_APPLICATION_BDCQZH, zhxlh, zhxlh, 7 * 24 * 60 * 60);
            }
            return count;
        } catch (Exception exception){
            LOGGER.error("生成证号：保存证号异常，对应证书ID {}，要保存的证号 {}", bdcBdcqzhDTO.getZsid(), bdcBdcqzhDTO.getBdcqzh());
            throw exception;
        }
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param    bdcBdcqzhBO 证号查询实体
     * @param    bdcZhDO 证号信息实体
     * @return  {BdcBdcqzhDTO} 证号信息实体
     * @description  生成当前证书应该设置的证号
     */
    public BdcBdcqzhDTO generateDqBdcBdcqzh(BdcBdcqzhBO bdcBdcqzhBO, BdcZhDO bdcZhDO) {
        BdcBdcqzhDTO bdcBdcqzhDTO = new BdcBdcqzhDTO();

        // 证书ID
        bdcBdcqzhDTO.setZsid(bdcBdcqzhBO.getZsid());
        // 不动产权证号
        bdcBdcqzhDTO.setBdcqzh(bdcZhDO.getBdcqzh());
        // 省区市简称
        bdcBdcqzhDTO.setSqsjc(bdcZhDO.getSqsjc());
        // 年份
        bdcBdcqzhDTO.setNf(bdcZhDO.getNf());
        // 所在市县全称
        bdcBdcqzhDTO.setSzsxqc(bdcZhDO.getSzsxqc());
        // 证书类型
        bdcBdcqzhDTO.setZslx(bdcBdcqzhBO.getZslx());
        // 证号流水号
        bdcBdcqzhDTO.setZhlsh(bdcZhDO.getZhlsh());
        // 证号序列号
        bdcBdcqzhDTO.setZhxlh(bdcZhDO.getZhxlh());

        return bdcBdcqzhDTO;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param    bdcZsDO 证书信息
     * @return  {BdcBdcqzhDTO} 证号信息实体
     * @description  生成当前证书应该设置的证号
     */
    public BdcBdcqzhDTO generateDqBdcBdcqzh(BdcZsDO bdcZsDO) {
        BdcBdcqzhDTO bdcBdcqzhDTO = new BdcBdcqzhDTO();

        // 证书ID
        bdcBdcqzhDTO.setZsid(bdcZsDO.getZsid());
        // 不动产权证号
        bdcBdcqzhDTO.setBdcqzh(bdcZsDO.getBdcqzh());
        // 省区市简称
        bdcBdcqzhDTO.setSqsjc(bdcZsDO.getSqsjc());
        // 年份
        bdcBdcqzhDTO.setNf(bdcZsDO.getNf());
        // 所在市县全称
        bdcBdcqzhDTO.setSzsxqc(bdcZsDO.getSzsxqc());
        // 证书类型
        bdcBdcqzhDTO.setZslx(bdcZsDO.getZslx());
        // 证号流水号
        bdcBdcqzhDTO.setZhlsh(bdcZsDO.getZhlsh());
        // 证号序列号
        bdcBdcqzhDTO.setZhxlh(bdcZsDO.getZhxlh());

        return bdcBdcqzhDTO;
    }
}
