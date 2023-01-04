package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.init.BdcGgDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.vo.inquiryui.BdcGgVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.StringUtil;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.config.BdcGgConfig;
import cn.gtmap.realestate.init.core.mapper.BdcGgMapper;
import cn.gtmap.realestate.init.core.service.BdcGgService;
import cn.gtmap.realestate.init.util.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: realestate
 * @description: 不动产公告实现层
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-21 15:03
 **/
@Service
public class BdcGgServiceImpl implements BdcGgService {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    BdcGgMapper bdcGgMapper;

    @Autowired
    BdcBhFeignService bdcBhFeignService;

    @Autowired
    BdcGgConfig bdcGgConfig;
    @Autowired
    BdcQlrServiceImpl bdcQlrService;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询不动产公告
     * @date : 2021/7/21 14:52
     */
    @Override
    public List<BdcGgDO> listBdcGg(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcGgDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }

    /**
     * @param ggid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询不动产公告
     * @date : 2021/7/21 14:52
     */
    @Override
    public List<BdcGggxDO> listBdcGggxByGgid(String ggid) {
        if (StringUtils.isNotBlank(ggid)) {
            Example example = new Example(BdcGggxDO.class);
            example.createCriteria().andEqualTo("ggid", ggid);
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }

    /**
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询不动产公告
     * @date : 2021/7/21 14:52
     */
    @Override
    public List<BdcGggxDO> listBdcGggxByXmid(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcGggxDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }

    /**
     * @param ggid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据公告id查公告信息
     * @date : 2022/3/3 9:05
     */
    @Override
    public BdcGgDO queryBdcGg(String ggid) {
        if (StringUtils.isBlank(ggid)) {
            return null;
        }
        return entityMapper.selectByPrimaryKey(BdcGgDO.class, ggid);
    }

    /**
     * @param ywsjid
     * @author <a href="mailto:wutao2@gtmap.cn">wutao</a>
     * @description 根据项目id查公告业务数据信息
     * @date : 2022/5/5 9:05
     */
    @Override
    public List<BdcGgywsjDO> queryBdcGgywsj(String ywsjid) {
        if (StringUtils.isNotBlank(ywsjid)) {
            Example example = new Example(BdcGgywsjDO.class);
            example.createCriteria().andEqualTo("ywsjid", ywsjid);
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }

    @Override
    public List<BdcGgywsjDO> queryBdcGgywsjByGgid(String ggid) {
        List<BdcGgywsjDO> bdcGgywsjDOS = new ArrayList<>();
        if (StringUtils.isNotBlank(ggid)) {
            List<BdcGggxDO> bdcGggxDOS = listBdcGggxByGgid(ggid);
            if (CollectionUtils.isNotEmpty(bdcGggxDOS)) {
                for (BdcGggxDO bdcGggxDO : bdcGggxDOS) {
                    if (StringUtil.isNotBlank(bdcGggxDO.getYwsjid())) {
                        List<BdcGgywsjDO> bdcGgywsjList = queryBdcGgywsj(bdcGggxDO.getYwsjid());
                        if (CollectionUtils.isNotEmpty(bdcGgywsjList)) {
                            bdcGgywsjDOS.addAll(bdcGgywsjList);
                        }
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(bdcGgywsjDOS)) {
            //单元号去重
            bdcGgywsjDOS = bdcGgywsjDOS.stream().filter(bdcGgywsjDO -> StringUtils.isNotBlank(bdcGgywsjDO.getBdcdyh())).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcGgywsjDO::getBdcdyh))), ArrayList::new));
        }
        return bdcGgywsjDOS;
    }

    /**
     * @param bdcGgDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产公告
     * @date : 2021/7/21 14:54
     */
    @Override
    public BdcGgDO insertBdcGg(BdcGgDO bdcGgDO) {
        if (StringUtils.isNotBlank(bdcGgDO.getGgid())) {
            entityMapper.updateByPrimaryKeySelective(bdcGgDO);
        } else {
            bdcGgDO.setGgid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcGgDO);
        }
        return bdcGgDO;
    }

    /**
     * @param xmidList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产公告
     * @date : 2021/7/21 14:54
     */
    @Override
    public int insertBdcGggx(List<String> xmidList, String ggid) {
        if (CollectionUtils.isNotEmpty(xmidList) && StringUtil.isNotBlank(ggid)) {
            Example example = new Example(BdcGggxDO.class);
            example.createCriteria().andEqualTo("ggid", ggid);
            entityMapper.deleteByExample(example);
            for (String xmid : xmidList) {
                BdcGggxDO bdcGggxDO = new BdcGggxDO();
                bdcGggxDO.setGgid(ggid);
                bdcGggxDO.setXmid(xmid);
                bdcGggxDO.setGxid(UUIDGenerator.generate16());
                entityMapper.insertSelective(bdcGggxDO);
            }
            return xmidList.size();
        }
        return 0;
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产公告
     * @date : 2021/7/21 14:54
     */
    @Override
    public Object insertBdcGgYwsj(BdcGgDTO bdcGgDTO, boolean sfgl) {
        BdcGgDO bdcGgDO = new BdcGgDO();
        Boolean sfsc = bdcGgDTO.getSfsc();
        if (StringUtil.isBlank(bdcGgDTO.getGgid())) {
            String ggid = UUIDGenerator.generate16();
            bdcGgDO.setGgid(ggid);
            bdcGgDTO.setGgid(ggid);
        } else {
            bdcGgDO.setGgid(bdcGgDTO.getGgid());
        }
        // 更新公告类型信息
        if (Objects.nonNull(bdcGgDTO.getGglx())) {
            bdcGgDO.setGglx(bdcGgDTO.getGglx());
            entityMapper.saveOrUpdate(bdcGgDO, bdcGgDO.getGgid());
        }
        BdcGgVO bdcGgVO = bdcGgDTO.getBdcGgVO();
        if (Objects.nonNull(bdcGgVO)) {
            String ggmbnr = JSON.toJSONString(bdcGgVO);
            bdcGgDO.setGgmbnr(ggmbnr);
            entityMapper.saveOrUpdate(bdcGgDO, bdcGgDO.getGgid());
        }


        if (CollectionUtils.isNotEmpty(bdcGgDTO.getBdcGgywsjDOList()) && StringUtil.isNotBlank(bdcGgDTO.getGgid())) {
            List<BdcGgywsjDO> bdcGgywsjDOList = bdcGgDTO.getBdcGgywsjDOList();
            if (sfgl) {
                // 判断项目是否已经关联其他公告
                for (BdcGgywsjDO bdcGgywsjDO : bdcGgywsjDOList) {
                    Example example = new Example(BdcGggxDO.class);
                    if (StringUtils.isNotBlank(bdcGgywsjDO.getXmid())) {
                        example.createCriteria().andEqualTo("xmid", bdcGgywsjDO.getXmid());
                        List<BdcGggxDO> bdcGggxDOList = entityMapper.selectByExample(example);
                        if (CollectionUtils.isNotEmpty(bdcGggxDOList)) {
                            throw new AppException("项目已经关联了其他公告");
                        }
                    }
                }
            } else {
                this.deleteGgByggid(bdcGgDTO.getGgid());
            }
            for (BdcGgywsjDO bdcGgywsjDO : bdcGgywsjDOList) {
                String ywsjId = UUIDGenerator.generate16();
                //插入数据到公告关系表
                BdcGggxDO bdcGggxDO = new BdcGggxDO();
                bdcGggxDO.setGgid(bdcGgDTO.getGgid());
                bdcGggxDO.setXmid(bdcGgywsjDO.getXmid());
                bdcGggxDO.setGxid(UUIDGenerator.generate16());
                bdcGggxDO.setYwsjid(ywsjId);
                entityMapper.saveOrUpdate(bdcGggxDO, bdcGggxDO.getGgid());
                // 插入数据到业务数据表
                bdcGgywsjDO.setGgid(bdcGgDTO.getGgid());
                bdcGgywsjDO.setYwsjid(ywsjId);
                entityMapper.saveOrUpdate(bdcGgywsjDO, bdcGgywsjDO.getYwsjid());
            }

        } else if (CollectionUtils.isEmpty(bdcGgDTO.getBdcGgywsjDOList()) && StringUtil.isNotBlank(bdcGgDTO.getGgid()) && sfsc) {
            this.deleteGgByggid(bdcGgDTO.getGgid());
        }
        List<BdcGgywsjDO> bdcGgywsjDOList = queryBdcGgywsjByGgid(bdcGgDO.getGgid());
        //生成编号、联系电话、联系地址
        Example example = new Example(BdcGgDO.class);
        example.createCriteria().andEqualTo("ggid", bdcGgDTO.getGgid());
        List<BdcGgDO> bdcGgDOList = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(bdcGgDOList)) {
            BdcGgDO bdcGg = bdcGgDOList.get(0);
            if (CollectionUtils.isNotEmpty(bdcGgywsjDOList) && StringUtils.isNotBlank(bdcGgywsjDOList.get(0).getQxdm())) {
                JSONObject ggnrObj = JSON.parseObject(bdcGg.getGgmbnr());
                if (!Objects.nonNull(ggnrObj) || !Objects.nonNull(ggnrObj.get("ggbh"))) {
                    String qxdm = bdcGgywsjDOList.get(0).getQxdm();
                    getGgbh(bdcGg, qxdm);
                }
            }
            if (CommonConstantUtils.GGLX_ZSZMYSGG_DM.equals(bdcGg.getGglx())) {
                setSmr(bdcGg);
            }
            if (CommonConstantUtils.GGLX_JCYZ_DM.equals(bdcGg.getGglx())) {
                setQlrswsj(bdcGg);
            }
            insertBdcGg(bdcGg);

        }
        return bdcGgDTO;
    }


    private void deleteGgByggid(String ggid) {
        Example example = new Example(BdcGggxDO.class);
        example.createCriteria().andEqualTo("ggid", ggid);
        // 根据ggid查询公告关系表中的数据
        List<BdcGggxDO> bdcGggxDOList = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(bdcGggxDOList)) {
            // 删除业务数据表中的数据
            for (BdcGggxDO bdcGggxDO : bdcGggxDOList) {
                Example example1 = new Example(BdcGgywsjDO.class);
                example1.createCriteria().andEqualTo("ywsjid", bdcGggxDO.getYwsjid());
                entityMapper.deleteByExample(example1);
            }
            // 删除公告关系表中的数据
            entityMapper.deleteByExample(example);
        }
    }


    /**
     * @param ggids 公告Id
     * @param ggzt  公告zhuangtai
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 批量更新公告状态
     * @date : 2021/7/22 14:54
     */
    @Override
    public int batchUpdatebdcggzt(List<String> ggids, String ggzt) {
        return bdcGgMapper.batchUpdatebdcggzt(ggids, ggzt);
    }

    /**
     * @param bdcGgDO 公告DO
     * @param qxdm    区县代码
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取公告编号
     * @date : 2022/5/6
     */
    @RedissonLock(lockKey = Constants.GGBH_REDIS_LOCK_NAME, description = "获取公告编号", waitTime = 60L, leaseTime = 30L)
    public BdcGgDO getGgbh(BdcGgDO bdcGgDO, String qxdm) {
        LOGGER.info("获取的bdcGgConfig：{}", JSON.toJSON(bdcGgConfig.getGgpzMap()));
        if (MapUtils.isNotEmpty(bdcGgConfig.getGgpzMap())) {
            if (CollectionUtils.isNotEmpty(bdcGgConfig.getGgpzMap().get(qxdm))) {
                List<String> strings = bdcGgConfig.getGgpzMap().get(qxdm);
                JSONObject ggnrObj = JSON.parseObject(bdcGgDO.getGgmbnr());
                if (!Objects.nonNull(ggnrObj)) {
                    ggnrObj = new JSONObject();
                }
                Integer lsh = bdcBhFeignService.queryLsh(qxdm + "GGBH", CommonConstantUtils.ZZSJFW_YEAR);
                ggnrObj.put("ggbh", CommonConstantUtils.ZF_YW_Z_XKH + DateUtils.getCurrYear() + CommonConstantUtils.ZF_YW_Y_XKH + strings.get(0) + lsh + "号");
                ggnrObj.put("lxdh", strings.get(1));
                ggnrObj.put("yyclsddz", strings.get(2));
                bdcGgDO.setGgmbnr(JSON.toJSONString(ggnrObj));
            }
        }
        return bdcGgDO;
    }


    @Override
    public List<BdcXmDO> queryBdcGgGlBdcXmxx(String ggid) {
        if (StringUtils.isBlank(ggid)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到公告信息ID");
        }
        return this.bdcGgMapper.queryBdcGgGlXmxx(ggid);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @date 2022/5/11 8:51
     * @description
     **/
    private void setSmr(BdcGgDO bdcGgDO) {
        JSONObject ggnrObj = JSON.parseObject(bdcGgDO.getGgmbnr());
        if (!Objects.nonNull(ggnrObj)) {
            ggnrObj = new JSONObject();
        }
        List<BdcGgywsjDO> bdcGgywsjDOS = queryBdcGgywsjByGgid(bdcGgDO.getGgid());
        List<BdcQlrDO> bdcQlrDOS = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcGgywsjDOS)) {
            bdcGgywsjDOS = bdcGgywsjDOS.stream().filter(bdcGgywsjDO -> StringUtils.isNotBlank(bdcGgywsjDO.getXmid())).collect(Collectors.toList());
            for (BdcGgywsjDO bdcGgywsjDO : bdcGgywsjDOS) {
                BdcQlrDO bdcQlrDO = new BdcQlrDO();
                bdcQlrDO.setXmid(bdcGgywsjDO.getXmid());
                bdcQlrDO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                List<BdcQlrDO> bdcQlrDOList = bdcQlrService.queryBdcQlrByQlrxx(bdcQlrDO, null);
                if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                    bdcQlrDOS.addAll(bdcQlrDOList);
                }
            }
        }
        String qlrs = bdcQlrDOS.stream().filter(bdcQlrDO -> StringUtils.isNotBlank(bdcQlrDO.getQlrmc())).map(bdcQlrDO -> bdcQlrDO.getQlrmc()).collect(Collectors.joining(","));
        ggnrObj.put("smr", qlrs);
        ggnrObj.put("qlr", qlrs);
        String bdcqzh = bdcGgywsjDOS.stream().filter(bdcGgywsjDO -> StringUtils.isNotBlank(bdcGgywsjDO.getYcqzh())).map(bdcGgywsjDO -> bdcGgywsjDO.getYcqzh()).collect(Collectors.joining(","));
        ggnrObj.put("ycqzh", bdcqzh);
        bdcGgDO.setGgmbnr(JSON.toJSONString(ggnrObj));
    }

    private void setQlrswsj(BdcGgDO bdcGgDO) {
        JSONObject ggnrObj = JSON.parseObject(bdcGgDO.getGgmbnr());
        if (!Objects.nonNull(ggnrObj)) {
            ggnrObj = new JSONObject();
        }
        if (Objects.nonNull(ggnrObj.get("qlrswsjList"))) {
            List<Map> qlrswsjList = (List<Map>) ggnrObj.get("qlrswsjList");
            if (CollectionUtils.isNotEmpty(qlrswsjList)) {
                List printStrList = new ArrayList();
                for (Map map : qlrswsjList) {
                    String qlr = (String) map.get("qlr");
                    String swsj = (String) map.get("swsj");
                    if (StringUtils.isNotBlank(qlr) && StringUtils.isNotBlank(swsj)) {
                        String printStr = qlr + "于" + swsj + "死亡";
                        printStrList.add(printStr);
                    }
                }
                ggnrObj.put("qlrswsj", Joiner.on(",").join(printStrList));
            }
        } else {
            List<Map<String, String>> qlrswsjList = new ArrayList<>();
            List<BdcGgywsjDO> bdcGgywsjDOS = queryBdcGgywsjByGgid(bdcGgDO.getGgid());
            bdcGgywsjDOS = bdcGgywsjDOS.stream().filter(bdcGgywsjDO -> StringUtils.isNotBlank(bdcGgywsjDO.getXmid())).collect(Collectors.toList());
            List<BdcQlrDO> bdcQlrDOS = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(bdcGgywsjDOS)) {
                //获取义务人
                for (BdcGgywsjDO bdcGgywsjDO : bdcGgywsjDOS) {
                    BdcQlrDO bdcQlrDO = new BdcQlrDO();
                    bdcQlrDO.setXmid(bdcGgywsjDO.getXmid());
                    bdcQlrDO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
                    List<BdcQlrDO> bdcQlrDOList = bdcQlrService.queryBdcQlrByQlrxx(bdcQlrDO, null);
                    if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                        bdcQlrDOS.addAll(bdcQlrDOList);
                    }
                }
                //根据义务人去重
                bdcQlrDOS = bdcQlrDOS.stream().filter(bdcQlrDO -> StringUtils.isNotBlank(bdcQlrDO.getQlrmc())).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcQlrDO::getQlrmc))), ArrayList::new));
                for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
                    Map<String, String> qlrSqsj = new HashMap();
                    qlrSqsj.put("qlr", bdcQlrDO.getQlrmc());
                    qlrswsjList.add(qlrSqsj);
                }
                ggnrObj.put("qlrswsjList", qlrswsjList);
            }

        }
        bdcGgDO.setGgmbnr(JSON.toJSONString(ggnrObj));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBdcGgxx(List<String> ggids) {
        if (CollectionUtils.isNotEmpty(ggids)) {
            for (String ggid : ggids) {
                Example example = new Example(BdcGggxDO.class);
                example.createCriteria().andEqualTo("ggid", ggid);
                List<BdcGggxDO> bdcGggxDOList = entityMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(bdcGggxDOList)) {
                    for (BdcGggxDO bdcGggxDO : bdcGggxDOList) {
                        String ywsjid = bdcGggxDO.getYwsjid();
                        String gxid = bdcGggxDO.getGxid();
                        if (StringUtils.isNotBlank(ywsjid) && StringUtils.isNotBlank(gxid)) {
                            Example exampleYwsj = new Example(BdcGgywsjDO.class);
                            exampleYwsj.createCriteria().andEqualTo("ywsjid", ywsjid);
                            entityMapper.deleteByExample(exampleYwsj);
                            entityMapper.deleteByExample(example);
                            Example exampleGg = new Example(BdcGgDO.class);
                            exampleGg.createCriteria().andEqualTo("ggid", ggid);
                            entityMapper.deleteByExample(exampleGg);
                        }
                    }
                }
            }
        }
        return ggids.size();
    }
}
