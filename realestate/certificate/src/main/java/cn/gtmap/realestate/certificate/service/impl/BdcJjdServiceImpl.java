package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.certificate.core.mapper.BdcJjdMapper;
import cn.gtmap.realestate.certificate.service.BdcJjdService;
import cn.gtmap.realestate.common.core.domain.BdcJjdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmYjdGxDO;
import cn.gtmap.realestate.common.core.dto.BdcUrlDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcAjxxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcJjdQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcJjdXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 移交单业务实现类
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/8/27 18:16
 */
@Service
public class BdcJjdServiceImpl implements BdcJjdService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcJjdServiceImpl.class);
    /**
     * 交接单状态：未转发
     */
    private static final Integer JJDZT_WZF = 0;
    /**
     * 交接单状态：已转发
     */
    private static final Integer JJDZT_YZF = 1;
    /**
     * 交接单状态：已接收
     */
    private static final Integer JJDZT_YJS = 2;
    /**
     * 交接单状态：拒绝接收
     */
    private static final Integer JJDZT_JJJS = 3;

    @Autowired
    BdcJjdMapper bdcJjdMapper;
    @Autowired
    MessageProvider messageProvider;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcPrintFeignService bdcPrintFeignService;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcBhFeignService bdcBhFeignService;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    private RedisUtils redisUtils;

    // 交接单版本 南通jjd 海门hmjjd
    @Value("${version.jjdDylx:jjd}")
    private String jjdDylx;

    /**
     * 生成并保存移交单信息
     *
     * @param bdcJjdQO 交接单查询QO
     * @return {BdcJjdDO} 交接单对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BdcJjdDO generateAndSaveJjdxx(BdcJjdQO bdcJjdQO) {
        // 1. 生成交接单号
        BdcJjdDO bdcJjdDO = generateJjdBh(bdcJjdQO);
        // 2. 批量插入到 BDC_XM_YJD_GX 表
        if (null != bdcJjdDO && StringUtils.isNotBlank(bdcJjdDO.getJjdid())) {
            String jjdid = bdcJjdDO.getJjdid();
            List<BdcXmYjdGxDO> bdcXmYjdGxDOList = Lists.newArrayList();
            Integer djlx = null;
            BdcXmQO bdcXmQO = new BdcXmQO();
            for (int i = 0; i < bdcJjdQO.getListGzlslid().size(); i++) {
                bdcXmQO.setGzlslid( bdcJjdQO.getListGzlslid().get(i));
                List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
                // 获取登记类型
                if (null == bdcJjdDO.getJjdlx()) {
                    if (CollectionUtils.isNotEmpty(bdcXmDOS)) {
                        for (BdcXmDO xmDO : bdcXmDOS) {
                            if (null == djlx) {
                                djlx = xmDO.getDjlx();
                            } else if (!djlx.equals(xmDO.getDjlx())) {
                                //存在多种权利类型默认为 综合交接单
                                bdcJjdDO.setJjdlx(0);
                                break;
                            }
                        }
                    } else {
                        bdcJjdDO.setJjdlx(0);
                    }
                }
                BdcXmYjdGxDO bdcXmYjdGxDO = new BdcXmYjdGxDO();
                bdcXmYjdGxDO.setGxid(UUIDGenerator.generate16());
                bdcXmYjdGxDO.setXmid(bdcJjdQO.getListGzlslid().get(i));
                bdcXmYjdGxDO.setYjdid(jjdid);
                bdcXmYjdGxDO.setSxh(i);
                bdcXmYjdGxDOList.add(bdcXmYjdGxDO);
            }
            // 交接单类型为空，将登记类型赋值给 交接单类型
            if (bdcJjdDO.getJjdlx() == null) {
                bdcJjdDO.setJjdlx(djlx);
            }
            // 更新交接单类型
            entityMapper.saveOrUpdate(bdcJjdDO, bdcJjdDO.getJjdid());
            entityMapper.insertBatchSelective(bdcXmYjdGxDOList);
        }
        return bdcJjdDO;
    }

    /**
     * 生成移交单编号
     *
     * @param bdcJjdQO 交接单查询QO
     * @return {BdcJjdDO} 不动产交接单对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public BdcJjdDO generateJjdBh(BdcJjdQO bdcJjdQO) {
        String timeFormat = "yyyyMMdd";
        BdcJjdDO bdcJjdDO = new BdcJjdDO();
        Date currentDate = new Date();
        String dateStr = DateFormatUtils.format(currentDate, timeFormat);
        Integer count= bdcBhFeignService.queryLsh("JJDH", "ALL");
        // 组织 交接单数据
        String jjdh = dateStr + String.format("%05d", count);
        bdcJjdDO.setJjdid(UUIDGenerator.generate16());
        bdcJjdDO.setJjdh(jjdh);
        bdcJjdDO.setZfr(bdcJjdQO.getZfr());
        bdcJjdDO.setZfrid(bdcJjdQO.getZfrid());
        // 生成交接单确定是 0 未转发状态
        bdcJjdDO.setJjdzt(JJDZT_WZF);
        bdcJjdDO.setGzlslid(bdcJjdQO.getDajjGzlslid());
        entityMapper.insertSelective(bdcJjdDO);
        return bdcJjdDO;
    }

    /**
     * 获取交接单打印 Xml
     *
     * @param jjdid     交接单ID
     * @param bdcUrlDTO url DTO
     * @return String
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public String jjdPrintXml(String jjdid, BdcUrlDTO bdcUrlDTO) {
        String xml = "";
        if (StringUtils.isNotBlank(jjdid)) {
            Map<String, List<Map>> paramMap = new HashMap();

            List<Map> maps = Lists.newArrayList();
            Map<String, String> map = Maps.newHashMap();
            map.put("jjdid", jjdid);
            maps.add(map);
            paramMap.put(jjdDylx, maps);
            xml = bdcPrintFeignService.print(paramMap);
        }
        return xml;
    }

    /**
     * 删除交接单项目关系
     *
     * @param gzlslid gzlslid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public void delJjdXmGx(String gzlslid) {
        // 删除该项目关联的全部交接单
        Example example = new Example(BdcXmYjdGxDO.class);
        example.createCriteria().andEqualTo("xmid", gzlslid);
        entityMapper.deleteByExampleNotNull(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delJjd(String jjdid) {
        if (StringUtils.isBlank(jjdid)) {
            throw new MissingArgumentException("删除交接单信息缺少交接单主键！");
        }
        // 删除全部的关联关系
        bdcJjdMapper.deleteJjdGx(jjdid);
        BdcJjdDO bdcJjdDO = entityMapper.selectByPrimaryKey(BdcJjdDO.class, jjdid);
        // 删除交接单时将转发出的交接单都同步删除
        Example jjdExample = new Example(BdcJjdDO.class);
        jjdExample.createCriteria().andEqualTo("jjdh", bdcJjdDO.getJjdh());
        entityMapper.deleteByExampleNotNull(jjdExample);
    }

    /**
     * 转发交接单 <br/>
     * 「转发只能转发一次，下一个部门重新生成交接单」
     *
     * @param bdcJjdDO 交接单对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @Transactional
    public BdcJjdDO forwardJjd(BdcJjdDO bdcJjdDO) {
        if (JJDZT_JJJS.equals(bdcJjdDO.getJjdzt())) {
            // 拒绝接收项目需要更新空值
            BdcJjdDO yBdcJjdDO = entityMapper.selectByPrimaryKey(BdcJjdDO.class, bdcJjdDO.getJjdid());
            yBdcJjdDO.setJssj(null);
            yBdcJjdDO.setJsks(bdcJjdDO.getJsks());
            yBdcJjdDO.setJsrid(bdcJjdDO.getJsrid());
            yBdcJjdDO.setJsr(bdcJjdDO.getJsr());
            entityMapper.updateByPrimaryKeyNull(yBdcJjdDO);
        }

        // 交接单状态修改为已转发
        bdcJjdDO.setJjdzt(JJDZT_YZF);
        bdcJjdDO.setZfsj(new Date());
        String currentUserName = userManagerUtils.getCurrentUserName();
        String organizationByUserName = userManagerUtils.getOrganizationByUserName(currentUserName);
        bdcJjdDO.setZfks(organizationByUserName);
        entityMapper.saveOrUpdate(bdcJjdDO, bdcJjdDO.getJjdid());
        return bdcJjdDO;
    }

    /**
     * 确认接收交接单
     *
     * @param jjdid 交接单 id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public Boolean acceptJjd(String jjdid) {
        return updateJjdZt(jjdid, JJDZT_YJS);
    }

    /**
     * 拒绝接收交接单
     *
     * @param jjdid 交接单ID
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public Boolean backJjd(String jjdid) {
        return updateJjdZt(jjdid, JJDZT_JJJS);
    }

    @Override
    public List<BdcXmDO> queryJjdXm(BdcJjdXmQO bdcJjdXmQO) {
        return bdcJjdMapper.queryJjdXm(bdcJjdXmQO);
    }

    @Override
    public List<BdcXmYjdGxDO> queryJjdGx(String jjdid) {
        if (StringUtils.isBlank(jjdid)) {
            throw new MissingArgumentException("缺少查询参数 JJDID");
        }
        Example example = new Example(BdcXmYjdGxDO.class);
        example.createCriteria().andEqualTo("yjdid", jjdid);
        return entityMapper.selectByExample(example);
    }

    @Override
    public List<BdcXmYjdGxDO> queryJjdGxByXmid(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺少查询参数 XMID");
        }
        Example example = new Example(BdcXmYjdGxDO.class);
        example.createCriteria().andEqualTo("xmid", xmid);
        return entityMapper.selectByExample(example);
    }

    @Override
    public List<BdcXmYjdGxDO> queryJjdGxByGzlslidList(List<String> gzlslidList) {
        if(CollectionUtils.isEmpty(gzlslidList)){
            throw new MissingArgumentException("缺失参数工作流实例ID");
        }

        return bdcJjdMapper.queryJjdGxByGzlslidList(gzlslidList);
    }

    @Override
    public List<BdcJjdDO> listBdcJjd(BdcJjdQO bdcJjdQO) {
        if (!CheckParameter.checkAnyParameter(bdcJjdQO)) {
            throw new MissingArgumentException("缺失查询条件参数！");
        }
        return bdcJjdMapper.listBdcJjd(bdcJjdQO);
    }

    @Override
    public List<BdcAjxxDTO> listBdcAjxx(Map map) {
        return bdcJjdMapper.listBdcAjxx(map);
    }

    /**
     * 修改交接单状态
     * <p>
     * 修改当前交接单 以及 userIdType 为 当前用户 jjdh 相同的交接单的状态
     *
     * @param jjdid 交接单 id
     * @param zt    状态 0：未转发 1：已转发 2： 已经接收 3： 拒绝接收
     * @return 修改过后的 交接单对象
     */
    @Transactional
    public Boolean updateJjdZt(String jjdid, Integer zt) {
        UserDto currentUser = userManagerUtils.getCurrentUser();
        BdcJjdDO bdcJjdDO = entityMapper.selectByPrimaryKey(BdcJjdDO.class, jjdid);
        // 校验当前用户是否可以更新交接单状态
        if (StringUtils.equals(bdcJjdDO.getZfrid(), currentUser.getId())) {
            return false;
        }
        bdcJjdDO.setJsr(currentUser.getAlias());
        bdcJjdDO.setJsrid(currentUser.getId());
        // 转发给全部的需要重新赋值科室
        if (StringUtils.isBlank(bdcJjdDO.getJsks())) {
            bdcJjdDO.setJsks(currentUser.getRoleRecordList().get(0).getAlias());
        }
        bdcJjdDO.setJjdzt(zt);
        // 确认接收更新时间
        if (JJDZT_YJS.equals(zt)) {
            bdcJjdDO.setJssj(new Date());
        }
        entityMapper.saveOrUpdate(bdcJjdDO, bdcJjdDO.getJjdid());
        return true;
    }

    /**
     * 判断是否交接单可以创建
     *
     * @param gzlslid gzlslid
     * @return {Integer} 0 表示可以创建， 1 不能创建 当前用户已经用此gzlslid创建过交接单，不能再创建
     */
    @Override
    public Integer checkIsCreat(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失工作流实例 ID！");
        }
        List<BdcJjdDO> bdcJjdDOS = bdcJjdMapper.queryBdcJjdByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcJjdDOS)) {
            return 0;
        }
        UserDto currentUser = userManagerUtils.getCurrentUser();
        for(BdcJjdDO jjdDO : bdcJjdDOS){
            if(StringUtils.equals(jjdDO.getZfrid(), currentUser.getId())){
                return 1;
            }
        }
        return 0;
    }

    @Override
    public List<BdcJjdDO> queryBdcJjd() {
        return bdcJjdMapper.queryBdcJjd();
    }

    @Override
    public int updateJjdzt(List<BdcJjdDO> bdcJjdDOList) {
        return bdcJjdMapper.updateJjdzt(bdcJjdDOList);
    }

    @Override
    public List<BdcJjdDO> getBdcJjdListByGzlslid(String gzlslid) {
        return bdcJjdMapper.getBdcJjdListByGzlslid(gzlslid);
    }

    @Override
    public List<BdcJjdDO> getAllNewJjdByJjdid(String jjdid) {
        return bdcJjdMapper.getAllNewJjdByJjdid(jjdid);
    }

    @Override
    public List<BdcXmDO> getBdcXmDOByJjdid(String jjdid) {
        return bdcJjdMapper.getBdcXmDOByJjdid(jjdid);
    }

    /**
     * 批量转发交接单
     *
     * @param bdcJjdDOList 交接单对象集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void forwardJjd(List<BdcJjdDO> bdcJjdDOList) {
        if(CollectionUtils.isEmpty(bdcJjdDOList)) {
            throw new MissingArgumentException("未定义需要转发的交接单");
        }

        String userName = userManagerUtils.getCurrentUserName();
        String orgName = userManagerUtils.getOrganizationByUserName(userName);
        Date zfsj = new Date();

        for(BdcJjdDO bdcJjdDO : bdcJjdDOList) {
            // 这里参数判空沿用单个交接单转发验证逻辑 BdcJjdController.forwardJjd(BdcJjdDO)
            if (StringUtils.isBlank(bdcJjdDO.getJjdid()) && bdcJjdDO.getJjdzt() != null
                    && (StringUtils.isAnyBlank(bdcJjdDO.getJsrid(), bdcJjdDO.getJsks()) ) ) {
                throw new MissingArgumentException("交接单转发参数缺失！");
            }

            // @see BdcJjdServiceImpl.forwardJjd(cn.gtmap.realestate.common.core.domain.BdcJjdDO)
            if (JJDZT_JJJS.equals(bdcJjdDO.getJjdzt())) {
                // 拒绝接收项目需要更新空值
                BdcJjdDO yBdcJjdDO = entityMapper.selectByPrimaryKey(BdcJjdDO.class, bdcJjdDO.getJjdid());
                yBdcJjdDO.setJssj(null);
                yBdcJjdDO.setJsks(bdcJjdDO.getJsks());
                yBdcJjdDO.setJsrid(bdcJjdDO.getJsrid());
                yBdcJjdDO.setJsr(bdcJjdDO.getJsr());
                entityMapper.updateByPrimaryKeyNull(yBdcJjdDO);
            }

            // 交接单状态修改为已转发
            bdcJjdDO.setJjdzt(JJDZT_YZF);
            bdcJjdDO.setZfsj(zfsj);
            bdcJjdDO.setZfks(orgName);
            entityMapper.saveOrUpdate(bdcJjdDO, bdcJjdDO.getJjdid());
        }
    }

    /**
     * 生成南通交接单批量打印xml
     * @param key 打印参数缓存Redis Key
     * @return {String} 移交单打印xml
     */
    @Override
    public String jjdBatchPrintXml(String key) {
        String json = redisUtils.getStringValue(key);
        List<BdcJjdDO> jjdDOList  = JSON.parseArray(json, BdcJjdDO.class);
        if(CollectionUtils.isEmpty(jjdDOList)) {
            throw new MissingArgumentException("未定义交接单打印数据");
        }

        Map<String, List<Map>> paramMap = new HashMap();
        List<Map> maps = Lists.newArrayList();

        for(BdcJjdDO bdcJjdDO : jjdDOList) {
            Map<String, String> map = Maps.newHashMap();
            map.put("jjdid", bdcJjdDO.getJjdid());
            maps.add(map);
        }

        paramMap.put(jjdDylx, maps);
        return bdcPrintFeignService.print(paramMap);
    }
}
