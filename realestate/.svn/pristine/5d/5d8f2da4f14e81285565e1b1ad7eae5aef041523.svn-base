package cn.gtmap.realestate.natural.core.service.impl;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyXmDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtZsbhmbDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyZsDO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyCqzhDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.EntityNotFoundException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.natural.core.bo.ZrzyZscCqzhBO;
import cn.gtmap.realestate.natural.core.service.ZrzyXmService;
import cn.gtmap.realestate.natural.core.service.ZrzyXtZsbhmbService;
import cn.gtmap.realestate.natural.service.ZrzyZsService;
import cn.gtmap.realestate.natural.util.Constants;
import com.alibaba.fastjson.JSON;
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
 * @description 不动产权证号处理公共逻辑类
 */
@Service
public class ZrzyDjcqzhGgServiceImpl {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZrzyDjcqzhGgServiceImpl.class);

    @Autowired
    private ZrzyXmService zrzyXmService;

    @Autowired
    private ZrzyZsService zrzyZsService;

    @Autowired
    private ZrzyXtZsbhmbService zrzyXtZsbhmbService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private EntityMapper entityMapper;

    /**
     * 证号是否区分部门，如果没有配置则默认1，即默认是需要按照登记部门区分
     */
//    @Value("${zhqfbm:0}")
    private Integer zhqfbm = 0;

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
    @Value("${zrzycqzh.needlock:true}")
    private Boolean zrzycqzhNeedLock;

    /**
     * 共用统一流水号的区划设置
     */
    @Value("${zhgylsh:}")
    private String zhgylsh;

    /**
     * 暂时全部设置为1
     */
    public static final Integer ZSLX = 1;


    /**
     * 获取证号模板配置以及数据库最大证号
     */
    @PostConstruct
    public void init() {
        Example example = new Example(ZrzyXtZsbhmbDO.class);
        example.createCriteria()
                .andEqualTo("nf", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        List<ZrzyXtZsbhmbDO> zsbhmbDOList = entityMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(zsbhmbDOList)) {
            LOGGER.error("未查询到当前年份证号配置模板");
            return;
        }

        for (ZrzyXtZsbhmbDO zsbhmbDO : zsbhmbDOList) {
            String key = getZhmbRedisKey(zsbhmbDO.getNf(),
                    zsbhmbDO.getQxdm(),
                    zsbhmbDO.getDjbmdm(),
                    false);

            // 将不同编号规则缓存到Redis
            redisUtils.addHashValue(Constants.REDIS_ZRZYBDCQZH_MBPZ, key, JSON.toJSONString(zsbhmbDO));

            // 获取每个证书类型编号规则对应的最大编号
            ZrzyZscCqzhBO qo = new ZrzyZscCqzhBO();
            qo.setNf(zsbhmbDO.getNf());
            qo.setZhqfqxdm(zhqfqxdm);
            qo.setQxdm(zsbhmbDO.getQxdm());
            qo.setZslx(ZSLX);
            qo.setZhqfbm(zhqfbm);
            qo.setDjbmdm(zsbhmbDO.getDjbmdm());
            LOGGER.debug("查询证号序列号最大值参数：{}", JSON.toJSONString(qo));
            Integer maxSxh = zrzyZsService.queryMaxSxh(qo);

            // 如果设置的初始号大于数据库中实际最大号，以设置的初始号为准
            if (null != zsbhmbDO.getCssxh() && zsbhmbDO.getCssxh() > maxSxh) {
                maxSxh = zsbhmbDO.getCssxh();
            }

            String zhxlhKey = getZhxlhRedisKey(
                    zsbhmbDO.getNf(),
                    zsbhmbDO.getQxdm(),
                    zsbhmbDO.getDjbmdm(),
                    ZSLX);
            redisUtils.addLongValue(zhxlhKey, maxSxh.longValue());
            LOGGER.debug("缓存Redis证号序列号：{}，值：{}", zhxlhKey, maxSxh);
        }
    }

    /**
     * 从Redis获取下一个序列号
     */
    public Integer getNextSxhFromRedis(ZrzyZscCqzhBO bdcBdcqzhBO, Long step) {
        String zhxlhKey = getZhxlhRedisKey(
                bdcBdcqzhBO.getNf(),
                bdcBdcqzhBO.getQxdm(),
                bdcBdcqzhBO.getDjbmdm(),
                ZSLX);
        return redisUtils.incr(zhxlhKey, step).intValue();
    }

    /**
     * 生成Redis缓存不同类型证号序列号取值Key
     *
     * @param nf     年份
     * @param qxdm   区县代码
     * @param djbmdm 登记部门代码
     * @param zslx   证书类型
     * @return String 键名
     */
    private String getZhxlhRedisKey(String nf, String qxdm, String djbmdm, Integer zslx) {
        return Constants.REDIS_ZHXLH + getZhmbRedisKey(nf, qxdm, djbmdm, true) + "_" + zslx;
    }

    /**
     * 生成Redis缓存证号模板配置 Hash Key
     *
     * @param nf     年份
     * @param qxdm   区县代码
     * @param djbmdm 登记部门代码
     * @param fzqxdm 是否用分组区县代码作为key元素
     * @return String 键名
     */
    private String getZhmbRedisKey(String nf, String qxdm, String djbmdm, boolean fzqxdm) {
        String key = nf;

        if (null != zhqfqxdm && 1 == zhqfqxdm.intValue()) {
            key += "_" + this.getQxdmKey(qxdm, fzqxdm);
        }
        if (null != zhqfbm && 1 == zhqfbm.intValue()) {
            key += "_" + djbmdm;
        }
        return key;
    }

    /**
     * 如果多个区划共用一个流水号，则用所在分组区划作为Redis键元素
     *
     * @param xmqxdm 当前项目区县代码
     * @param fzqxdm 是否用分组区县代码作为key元素（缓存证号模板配置还是一个个区划一条记录，缓存最大流水号时候可能需要一个分组一个key）
     * @return {String} 区县代码
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private String getQxdmKey(String xmqxdm, boolean fzqxdm) {
        if (StringUtils.isBlank(zhgylsh) || !fzqxdm) {
            return xmqxdm;
        }

        String[] array = zhgylsh.split(CommonConstantUtils.ZF_YW_FH);
        for (String qxdms : array) {
            String[] qxdmArray = qxdms.split(CommonConstantUtils.ZF_YW_DH);
            for (String qxdm : qxdmArray) {
                if (StringUtils.equals(qxdm, xmqxdm)) {
                    return qxdms;
                }
            }
        }
        return xmqxdm;
    }

    /**
     * @param xmid 项目ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取证号处理信息数据
     */
    public ZrzyZscCqzhBO getBdcqzhBO(String xmid) {
        // 获取项目
        ZrzyXmDO zrzyXmDO = getZrzyXm(xmid);

        // 获取证号模板
        ZrzyXtZsbhmbDO zrzyXtZsbhmbDO;
        if (zrzycqzhNeedLock && null != zrzyXmDO) {
            zrzyXtZsbhmbDO = getBdcXtZsbhmb(zrzyXmDO);
        } else {
            // 从Redis缓存获取
            String key = getZhmbRedisKey(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)),
                    zrzyXmDO.getQxdm(),
                    "",
                    false);
            String json = redisUtils.getHashValue(Constants.REDIS_ZRZYBDCQZH_MBPZ, key);

            if (StringUtils.isBlank(json) || "null".equals(json)) {
                // 如果为空再去数据库取下，例如2021年首次获取没有Redis缓存
                zrzyXtZsbhmbDO = getBdcXtZsbhmb(zrzyXmDO);
                redisUtils.addHashValue(Constants.REDIS_ZRZYBDCQZH_MBPZ, key, JSON.toJSONString(zrzyXtZsbhmbDO));
            } else {
                zrzyXtZsbhmbDO = JSON.parseObject(json, ZrzyXtZsbhmbDO.class);
            }
        }

        if (null == zrzyXtZsbhmbDO) {
            throw new AppException("未获取到证书编号模板");
        }

        ZrzyZscCqzhBO bdcBdcqzhBO = new ZrzyZscCqzhBO(zrzyXmDO, zrzyXtZsbhmbDO);
        bdcBdcqzhBO.setXmid(xmid);

        if (null == zhqfqxdm) {
            zhqfqxdm = 1;
        }
        bdcBdcqzhBO.setZhqfqxdm(zhqfqxdm);

        if (null == zhqfbm) {
            zhqfbm = 1;
        }
        bdcBdcqzhBO.setZhqfbm(zhqfbm);
        return bdcBdcqzhBO;
    }

    /**
     * @param zrzyXmDO 不动产项目
     * @return {BdcXtZsbhmbDO}  证号模板
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取当前年份对应区域的证号模板配置
     */
    public ZrzyXtZsbhmbDO getBdcXtZsbhmb(ZrzyXmDO zrzyXmDO) {
        ZrzyXtZsbhmbDO zrzyXtZsbhmbDO = zrzyXtZsbhmbService.queryZrzyXtZsbhmb(zrzyXmDO);

        if (null == zrzyXtZsbhmbDO) {
            LOGGER.error("项目ID{}未查询到关联证书编号模板，获取不动产权证号终止！", zrzyXmDO.getXmid());
            throw new EntityNotFoundException("项目无对应证书编号模板！");
        }
        return zrzyXtZsbhmbDO;
    }

    /**
     * @param xmid 项目ID
     * @return {BdcXmDO} 不动产项目
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据项目ID获取对应项目
     */
    public ZrzyXmDO getZrzyXm(String xmid) {
        try {
            ZrzyXmDO zrzyXmDO = zrzyXmService.queryZrzyXmByXmid(xmid);

            if (null == zrzyXmDO) {
                LOGGER.error("生成证号查询{}项目不存在，获取不动产权证号终止！", xmid);
                throw new EntityNotFoundException("不存在对应项目，获取不动产权证号终止！");
            }
            return zrzyXmDO;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new EntityNotFoundException("不存在对应项目，获取不动产权证号终止！");
        }
    }

    /**
     * @param xmid 项目ID
     * @return {List} 证书集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取项目关联的证书记录
     */
    public List<ZrzyZsDO> getZrzyZs(String xmid) {
        List<ZrzyZsDO> zrzyZsDOS = zrzyZsService.queryZrzyZsByXmid(xmid);

        if (CollectionUtils.isEmpty(zrzyZsDOS)) {
            LOGGER.warn("项目ID{}未查询到关联证书信息，获取不动产权证号终止！", xmid);
            throw new EntityNotFoundException("项目无对应证书记录！");
        }
        return zrzyZsDOS;
    }

    /**
     * @param zrzyCqzhDTO 证号信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 保存不动产权证号
     */
    public int saveZrzycqzh(ZrzyCqzhDTO zrzyCqzhDTO) {
        if (null == zrzyCqzhDTO || StringUtils.isBlank(zrzyCqzhDTO.getZsid())) {
            return 0;
        }

        ZrzyZsDO zrzyZsDO = new ZrzyZsDO();
        zrzyZsDO.setZsid(zrzyCqzhDTO.getZsid());
        zrzyZsDO.setXmid(zrzyCqzhDTO.getXmid());
        zrzyZsDO.setZrzycqzh(zrzyCqzhDTO.getZrzycqzh());
        zrzyZsDO.setZhlsh(zrzyCqzhDTO.getZhlsh());
        zrzyZsDO.setZhxlh(zrzyCqzhDTO.getZhxlh());
        zrzyZsDO.setQzysxlh(zrzyCqzhDTO.getQzysxlh());
        zrzyZsDO.setNf(Short.parseShort(zrzyCqzhDTO.getNf()));
        //更新二维码内容
        if (ewmnrReadBdczh) {
            zrzyZsDO.setEwmnr(zrzyCqzhDTO.getZrzycqzh());
        }
        try {
            LOGGER.info("保存生成证号：{}", JSON.toJSONString(zrzyZsDO));
            int count = zrzyZsService.updateZrzyZs(zrzyZsDO);

            if (count > 0) {
                // 更新成功则保存序列号到Redis，为了其它请求取号时判断
                String zhxlh = String.valueOf(zrzyCqzhDTO.getZhxlh());
                redisUtils.addHashValue(Constants.REDIS_APPLICATION_BDCQZH, zhxlh, zhxlh, 7 * 24 * 60 * 60);
            }
            return count;
        } catch (Exception exception) {
            LOGGER.error("生成证号：保存证号异常，对应证书ID {}，要保存的证号 {}", zrzyCqzhDTO.getZsid(),
                    zrzyCqzhDTO.getZrzycqzh());
            throw exception;
        }
    }

    /**
     * @param bdcZsDO 证书信息
     * @return {BdcBdcqzhDTO} 证号信息实体
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 生成当前证书应该设置的证号
     */
    public ZrzyCqzhDTO generateDqBdcBdcqzh(ZrzyZsDO zrzyZsDO) {
        ZrzyCqzhDTO zrzyCqzhDTO = new ZrzyCqzhDTO();
        // 证书ID
        zrzyCqzhDTO.setZsid(zrzyZsDO.getZsid());
        // 不动产权证号
        zrzyCqzhDTO.setZrzycqzh(zrzyZsDO.getZrzycqzh());
        // 省区市简称
        zrzyCqzhDTO.setSqsjc(zrzyZsDO.getSqsjc());
        // 年份
        zrzyCqzhDTO.setNf(zrzyZsDO.getNf().toString());

        // 证书类型
        zrzyCqzhDTO.setZslx(ZSLX);
        // 证号流水号
        zrzyCqzhDTO.setZhlsh(zrzyZsDO.getZhlsh());
        zrzyCqzhDTO.setQzysxlh(zrzyZsDO.getQzysxlh());

        return zrzyCqzhDTO;
    }
}
