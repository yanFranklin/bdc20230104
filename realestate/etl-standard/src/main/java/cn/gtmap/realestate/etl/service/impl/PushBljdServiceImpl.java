package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcLzrDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.exchange.yzw.*;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.etl.core.domian.nantong.*;
import cn.gtmap.realestate.etl.service.PushBljdService;
import cn.gtmap.realestate.etl.service.PushQlygDataService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wyh
 * @version 1.0
 * @date 2022/9/6 23:02
 */
@Service
public class PushBljdServiceImpl implements PushBljdService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    @Autowired(required = false)
    @Qualifier("bdcEntityMapper")
    private EntityMapper entityMapper;

    @Autowired(required = false)
    @Qualifier("bjzjkEntityMapper")
    private EntityMapper bjzjkEntityMapper;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Autowired
    BdcZsFeignService bdcZsFeignService;

    /**
     * 推送办结节点
     *
     * @param queryMap
     */
    @Override
    public void pushBlData(Map queryMap) {
        logger.info("开始推送到中间库！！！");
        try {
            String gzlslid = MapUtils.getString(queryMap, "gzlslid");
            if (StringUtils.isNotBlank(gzlslid)) {
                List<InfApplyDO> infApplies = new ArrayList<InfApplyDO>();

                BdcXmQO bdcXmQO=new BdcXmQO();
                bdcXmQO.setGzlslid(gzlslid);
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if(CollectionUtils.isEmpty(bdcXmDOList)){
                    return;
                }
                List<String> xmids = bdcXmDOList.stream().map(BdcXmDO::getXmid).collect(Collectors.toList());
                List<BdcQlrDO> bdcQlrDOS = bdcQlrFeignService.listBdcQlrByXmidList(xmids,"1");

                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    for (BdcXmDO bdcXm : bdcXmDOList) {
                        if (StringUtils.isNotBlank(bdcXm.getZfxzspbh())) {
                            Example example = new Example(InfApplyDO.class);
                            Example.Criteria criteriaApply = example.createCriteria();
                            criteriaApply.andEqualTo("internalNo", bdcXm.getZfxzspbh());
                            List<InfApplyDO> infApplyList = entityMapper.selectByExampleNotNull(example);
                            if (CollectionUtils.isNotEmpty(infApplyList)) {
                                infApplies.addAll(infApplyList);
                                break;
                            }
                        }
                    }
                }
                if (CollectionUtils.isNotEmpty(infApplies)) {
                    //更新保存INF_APPLY基本信息表
                    pushData(infApplies, bdcXmDOList, bdcQlrDOS);
                }
            }
        } catch (Exception e) {
            logger.error("msg", e);
        }
        logger.info("推送结束！！！");
    }

    @Transactional(rollbackFor = Exception.class)
    public void pushData(List<InfApplyDO> infApplyList, List<BdcXmDO> bdcXmDOList, List<BdcQlrDO> bdcQlrDOS) {
        List<InfApplyProcessDO> infApplyProcessDOS = listProcess(infApplyList.get(0).getInternalNo());
        logger.info("推送中间库办理节点数据{},{},{},{}",
                JSON.toJSONString(infApplyList),
                JSON.toJSONString(bdcXmDOList),
                JSON.toJSONString(bdcQlrDOS),
                JSON.toJSONString(infApplyProcessDOS)
        );
        //推送受理信息表
        pushBase(infApplyList.get(0), bdcXmDOList);
        //推送申请信息表
        pushApply(infApplyList.get(0), bdcXmDOList, bdcQlrDOS);
        //推送审核信息表
        pushProcess(infApplyList.get(0), bdcXmDOList, infApplyProcessDOS);
        //推送证书
        pushZs(infApplyList.get(0), bdcXmDOList);
        logger.info("推送中间库办理节点数据结束");
    }

    /**
     * 查询过程信息
     *
     * @param internalNo
     * @return
     */
    List<InfApplyProcessDO> listProcess(String internalNo) {
        Example example = new Example(InfApplyProcessDO.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("to_number(no_ord)");
        criteria.andEqualTo("internalNo", internalNo);
        return entityMapper.selectByExample(InfApplyProcessDO.class, example);
    }

    /**
     * @param infApplyDO
     * @param bdcXmDOList
     */
    public void pushBase(InfApplyDO infApplyDO, List<BdcXmDO> bdcXmDOList) {
        if (CollectionUtils.isNotEmpty(bdcXmDOList) && StringUtils.isNotBlank(infApplyDO.getInternalNo())) {
            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
            List<Map> djlxZd = zdMap.get("djlx");
            List<Map> ajztZd = zdMap.get("ajzt");
            List<Map> qllxZd = zdMap.get("qllx");
            //先删除后新增
            Example example = new Example(BjInfBase.class);
            example.createCriteria().andEqualTo("internalNo", bdcXmDOList.get(0).getSlbh());
            bjzjkEntityMapper.deleteByExampleNotNull(example);

            List<BjInfBase> bjInfBases = new ArrayList<>();
            for (BdcXmDO xmDO : bdcXmDOList) {
                BjInfBase bjInfBase = new BjInfBase();
                bjInfBase.setId(UUID.randomUUID().toString());
                bjInfBase.setAreaNo(infApplyDO.getAreaNo());
                bjInfBase.setAreaName(infApplyDO.getAreaName());
                bjInfBase.setInternalNo(xmDO.getSlbh());
                bjInfBase.setInternalId(xmDO.getXmid());
                if (Objects.nonNull(infApplyDO.getIfUrgent()) && "true".equals(infApplyDO.getIfUrgent())) {
                    bjInfBase.setIfUrgent("1");
                } else {
                    bjInfBase.setIfUrgent("2");
                }
                bjInfBase.setSlsj(xmDO.getSlsj());
                bjInfBase.setBjsj(xmDO.getJssj());
                bjInfBase.setDjlx(StringToolUtils.convertBeanPropertyValueOfZd(xmDO.getDjlx(), djlxZd));
                bjInfBase.setLcmc(xmDO.getGzldymc());
                bjInfBase.setDbsj(xmDO.getDjsj());
                try {
                    bjInfBase.setCnqx(Double.parseDouble(xmDO.getCnqx()));
                } catch (Exception e) {

                }
                bjInfBase.setAjzt(StringToolUtils.convertBeanPropertyValueOfZd(xmDO.getAjzt(), ajztZd));
                bjInfBase.setQllx(StringToolUtils.convertBeanPropertyValueOfZd(xmDO.getQllx(), qllxZd));
                bjInfBase.setBdcdyh(xmDO.getBdcdyh());
                bjInfBases.add(bjInfBase);
            }
            List<List<BjInfBase>> partition = Lists.partition(bjInfBases, 500);
            for (List<BjInfBase> infBasesPart : partition) {
                bjzjkEntityMapper.insertBatchSelective(infBasesPart);
            }
        }
    }

    /**
     * 流程下所有的权利人都要推送
     *
     * @param infApplyDO
     * @param bdcXmDOList
     * @param bdcQlrDOS
     */
    public void pushApply(InfApplyDO infApplyDO, List<BdcXmDO> bdcXmDOList, List<BdcQlrDO> bdcQlrDOS) {
        if (CollectionUtils.isNotEmpty(bdcXmDOList) && StringUtils.isNotBlank(infApplyDO.getInternalNo()) &&
                CollectionUtils.isNotEmpty(bdcQlrDOS)
        ) {
            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
            List<Map> zjzlZd = zdMap.get("zjzl");
            List<Map> qlrlxZd = zdMap.get("qlrlx");

            Map<String, List<BdcXmDO>> bdcxmMap = bdcXmDOList
                    .stream()
                    .collect(Collectors.groupingBy(BdcXmDO::getXmid));

            //先尝试删除
            Example example = new Example(BjInfApply.class);
            example.createCriteria().andEqualTo("internalNo", bdcXmDOList.get(0).getSlbh());
            bjzjkEntityMapper.deleteByExampleNotNull(example);

            //插入数据
            List<BjInfApply> bjInfApplyList = new ArrayList<>();
            for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
                BdcXmDO bdcXmDO = bdcxmMap.getOrDefault(bdcQlrDO.getXmid(), bdcXmDOList).get(0);
                BjInfApply bjInfApply = new BjInfApply();
                bjInfApply.setId(UUID.randomUUID().toString());
                bjInfApply.setInternalNo(bdcXmDO.getSlbh());
                bjInfApply.setInternalId(bdcXmDO.getXmid());
                bjInfApply.setAreaNo(infApplyDO.getAreaNo());
                bjInfApply.setAreaName(infApplyDO.getAreaName());
                BeanUtils.copyProperties(bdcQlrDO, bjInfApply);
                bjInfApply.setZjzl(StringToolUtils.convertBeanPropertyValueOfZd(bdcQlrDO.getZjzl(), zjzlZd));
                bjInfApply.setQlrlx(StringToolUtils.convertBeanPropertyValueOfZd(bdcQlrDO.getQlrlx(), qlrlxZd));
                bjInfApply.setBdcdyh(bdcXmDO.getBdcdyh());
                bjInfApplyList.add(bjInfApply);
            }
            List<List<BjInfApply>> partition = Lists.partition(bjInfApplyList, 500);
            for (List<BjInfApply> infAppliePart : partition) {
                bjzjkEntityMapper.insertBatchSelective(infAppliePart);
            }
        }
    }

    /**
     * 推送审核信息表
     *
     * @param infApplyDO
     * @param bdcXmDOList
     * @param infApplyProcessDOS
     */
    public void pushProcess(InfApplyDO infApplyDO, List<BdcXmDO> bdcXmDOList, List<InfApplyProcessDO> infApplyProcessDOS) {
        if (CollectionUtils.isNotEmpty(bdcXmDOList) && StringUtils.isNotBlank(infApplyDO.getInternalNo())
                && CollectionUtils.isNotEmpty(infApplyProcessDOS)
        ) {
            //先尝试删除
            Example example = new Example(BjInfApplyProcess.class);
            example.createCriteria().andEqualTo("internalNo", bdcXmDOList.get(0).getSlbh());
            bjzjkEntityMapper.deleteByExampleNotNull(example);

            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            for (InfApplyProcessDO infApplyProcessDO : infApplyProcessDOS) {
                BjInfApplyProcess bjInfApplyProcess = new BjInfApplyProcess();
                bjInfApplyProcess.setId(UUID.randomUUID().toString());
                bjInfApplyProcess.setInternalNo(bdcXmDO.getSlbh());
                bjInfApplyProcess.setXh(infApplyProcessDO.getNoOrd());
                bjInfApplyProcess.setTacheName(infApplyProcessDO.getTacheName());
                bjInfApplyProcess.setEndTime(infApplyProcessDO.getProcessDate());
                bjInfApplyProcess.setCreateDate(infApplyProcessDO.getStartTime());
                bjInfApplyProcess.setUserName(infApplyProcessDO.getUserName());
                bjInfApplyProcess.setNote(infApplyProcessDO.getNote());
                bjInfApplyProcess.setEventName(infApplyProcessDO.getEvent_name());
                bjInfApplyProcess.setProcessDate(infApplyProcessDO.getProcessDate());
                bjInfApplyProcess.setQydm(bdcXmDO.getQxdm());
                bjInfApplyProcess.setBdcdyh(bdcXmDO.getBdcdyh());
                bjzjkEntityMapper.insertSelective(bjInfApplyProcess);
            }
        }
    }


    public void pushZs(InfApplyDO infApplyDO, List<BdcXmDO> bdcXmDOList) {
        if (CollectionUtils.isNotEmpty(bdcXmDOList) && StringUtils.isNotBlank(infApplyDO.getInternalNo())) {
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setGzlslid(bdcXmDOList.get(0).getGzlslid());
            List<BdcZsDO> bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);
            if (CollectionUtils.isEmpty(bdcZsDOS)) {
                return;
            }

            //先删除后新增
            Example example = new Example(BjInfApplyZs.class);
            example.createCriteria().andEqualTo("internalNo", bdcXmDOList.get(0).getSlbh());
            bjzjkEntityMapper.deleteByExampleNotNull(example);

            List<BjInfApplyZs> bjInfApplyZsList = new ArrayList<>();
            for (BdcZsDO bdcZsDO : bdcZsDOS) {
                BjInfApplyZs bjInfApplyZs = new BjInfApplyZs();
                bjInfApplyZs.setId(UUID.randomUUID().toString());
                bjInfApplyZs.setInternalNo(bdcXmDOList.get(0).getSlbh());
                bjInfApplyZs.setBdcdyh(bdcZsDO.getBdcdyh());
                bjInfApplyZs.setBdcqzh(bdcZsDO.getBdcqzh());
                bjInfApplyZs.setMj(bdcZsDO.getMj());
                bjInfApplyZs.setYt(bdcZsDO.getYt());
                bjInfApplyZs.setFj(bdcZsDO.getFj());
                bjInfApplyZs.setQlqtzk(bdcZsDO.getQlqtzk());
                bjInfApplyZs.setSyqx(bdcZsDO.getSyqx());
                bjInfApplyZs.setZl(bdcZsDO.getZl());
                bjInfApplyZsList.add(bjInfApplyZs);
            }
            List<List<BjInfApplyZs>> partition = Lists.partition(bjInfApplyZsList, 500);
            for (List<BjInfApplyZs> bjInfApplyZsPart : partition) {
                bjzjkEntityMapper.insertBatchSelective(bjInfApplyZsPart);
            }
        }
    }
}
