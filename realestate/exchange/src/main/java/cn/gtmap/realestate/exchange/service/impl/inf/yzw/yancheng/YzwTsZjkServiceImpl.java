package cn.gtmap.realestate.exchange.service.impl.inf.yzw.yancheng;

import cn.gtmap.gtc.workflow.clients.define.v1.WorkDayClient;
import cn.gtmap.gtc.workflow.domain.define.Work;
import cn.gtmap.gtc.workflow.domain.define.WorkDay;
import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.yzw.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.YzwDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.yzt.WorkDayVO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repository;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtilForWorkDay;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.core.domain.yzw.GxYzwTsjlDO;
import cn.gtmap.realestate.exchange.core.domain.yzw.GxYzwYzsbxxDO;
import cn.gtmap.realestate.exchange.core.domain.yzw.yancheng.*;
import cn.gtmap.realestate.exchange.core.dto.yzw.YzwCheckAndTsResultDTO;
import cn.gtmap.realestate.exchange.core.mapper.exchange.YzwCheckMapper;
import cn.gtmap.realestate.exchange.core.mapper.exchange.YzwMapper;
import cn.gtmap.realestate.exchange.core.thread.YzwTsThread;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwCheckService;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwTsQzkService;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwTsZjkService;
import cn.gtmap.realestate.exchange.util.BeanUtil;
import cn.gtmap.realestate.exchange.util.DateUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2020/11/24
 * @description inf_?????????????????????
 */
@Service("yzwTsZjkServiceImpl_yancheng")
public class YzwTsZjkServiceImpl implements YzwTsZjkService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YzwTsZjkServiceImpl.class);


    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    @Qualifier("sjptEntityMapper")
    private EntityMapper sjptMapper;

    @Resource(name = "sjptRepository")
    private Repo sjptRepository;

    @Autowired
    private YzwCheckMapper yzwCheckMapper;

    @Autowired
    private YzwTsQzkService qzkService;

    @Autowired
    private YzwCheckService checkService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    ThreadEngine threadEngine;

    @Autowired
    private YzwMapper yzwMapper;

    @Autowired
    private WorkDayClient workDayClient;

    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    BdcCzrzFeignService bdcCzrzFeignService;

    /**
     * ???????????????????????????????????????
     */
    @Value("#{'${yzw.tsCheck.yzlxList:1,2,3,4,5,6}'.split(',')}")
    private List<String> yzlxList;

    @Value("${yzw.version:}")
    private String yzwVersion;

    /**
     * ?????????
     */
    public static final int WORKDAY = 0;
    /**
     * ?????????
     */
    public static final int OFFDAY = 1;
    /**
     * ?????????
     */
    public static final int HOLIDAY = 2;

    /**
     * ????????????????????????????????????????????????tbm_all??????
     *
     * @param gzlslid
     * @Date 2020/11/25
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
//    @Transactional(value = "yzwTransactionManager", rollbackFor = Exception.class)
    @Override
    public void tszjkEntrance(String gzlslid) {
        //????????????
        int failNum = 0;
        int totalNum = 0;
        String msg = "";

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> list = bdcXmFeignService.listBdcXm(bdcXmQO);
        boolean updateTsxx = true;
        Integer tszt = Constants.YZW_TSZT_TSCG;
        String yzzt = "";

        for (BdcXmDO bdcXmDO : list) {
            if (StringUtils.isNotBlank(bdcXmDO.getZfxzspbh())) {
                InfApplyDO infApplyDO = yzwCheckMapper.getInfApply(bdcXmDO.getZfxzspbh());
                if (infApplyDO == null) {
                    LOGGER.info("infApplyDO?????????");
                }
                if (null != infApplyDO) {
                    insertZjk(infApplyDO, bdcXmDO);
                    GxYzwTsjlDO newGxTsqzkLog = new GxYzwTsjlDO();
                    newGxTsqzkLog.setTsxxid(UUIDGenerator.generate16());
                    newGxTsqzkLog.setYzwbh(infApplyDO.getInternalNo());
                    newGxTsqzkLog.setYwslbh(bdcXmDO.getSlbh());
                    newGxTsqzkLog.setTszt(Constants.YZW_TSZT_WTS);
                    newGxTsqzkLog.setYwid(bdcXmDO.getXmid());
                    newGxTsqzkLog.setTssj(new Date());
                    sjptMapper.insert(newGxTsqzkLog);
                    //??????????????????????????????????????????????????????????????????
                    List<String> failInfApplys = checkTsqzk(infApplyDO);
                    if (CollectionUtils.isNotEmpty(failInfApplys)) {
                        tszt = Constants.YZW_TSZT_TSSB;
                        failNum = failInfApplys.size();
                        msg = "??????????????????????????????" + StringUtils.join(failInfApplys.toArray(new String[failInfApplys.size()]), ",");

                    }
                }
            }
        }
    }

    /**
     * ????????????
     *
     * @param infApplyDO infApplyDO
     * @return
     * @Date 2020/12/1
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public List<String> checkTsqzk(InfApplyDO infApplyDO) {
        Example example = new Example(GxYzwTsjlDO.class);
        List<GxYzwTsjlDO> gxYzwTsjlDOList;
        List<GxYzwYzsbxxDO> gxTsqzkYzsbxxList = Lists.newArrayList();
        //????????????
        String sbycxx = "";
        GxYzwTsjlDO gxYzwTsjlDO = new GxYzwTsjlDO();
        Integer tszt = Constants.YZW_TSZT_TSCG;
        Integer sblx = null;
        Integer yzzt = 2;
        List<String> failInfApplys = Lists.newArrayList();
        example.clear();
        example.createCriteria().andEqualTo("yzwbh", infApplyDO.getInternalNo());
        gxYzwTsjlDOList = sjptMapper.selectByExampleNotNull(example);
        if (CollectionUtils.isNotEmpty(gxYzwTsjlDOList)) {
            gxYzwTsjlDOList.sort(Comparator.comparing(GxYzwTsjlDO::getTssj));
            gxYzwTsjlDO = gxYzwTsjlDOList.get(gxYzwTsjlDOList.size() - 1);
        }
        try {
            if (Constants.YZW_TSZT_TSSB.equals(gxYzwTsjlDO.getTszt())) {
                tszt = gxYzwTsjlDO.getTszt();
            } else {
                YzwCheckAndTsResultDTO yzwCheckAndTsResultDTO = checkService.checkYzwData(infApplyDO.getInternalNo(), yzlxList);
                if (yzwCheckAndTsResultDTO != null && yzwCheckAndTsResultDTO.getSblx() != null) {
                    tszt = Constants.YZW_TSZT_TSSB;
                    sblx = yzwCheckAndTsResultDTO.getSblx();
                    yzwCheckAndTsResultDTO.setTszt(Constants.YZW_TSZT_TSSB);
                    yzzt = Constants.YZW_TSZT_TSSB;
                    sbycxx = yzwCheckAndTsResultDTO.getSbycxx();
                    gxTsqzkYzsbxxList = yzwCheckAndTsResultDTO.getGxYzwYzsbxxDOList();
                } else {
                    yzzt = Constants.YZW_YZZT_YZCG;
                    // ?????????????????????????????????
                    HashMap<String, Object> pushResult = qzkService.tsQzk(infApplyDO);
                    boolean jgyts = MapUtils.getBoolean(pushResult, "jgyts", false);
                    boolean slyts = MapUtils.getBoolean(pushResult, "slyts", false);
                    updateExchangeTBm(infApplyDO, jgyts, slyts);


                }
            }
        } catch (Exception e) {
            LOGGER.error("******????????????????????????{}, ????????????{}", infApplyDO.getInternalNo(), e);
            tszt = Constants.YZW_TSZT_TSSB;
            yzzt = Constants.YZW_YZZT_YZSB;
            sblx = Constants.SBLX_YC;
            sbycxx = e.getMessage();
        } finally {
            if (Constants.YZW_TSZT_TSSB.equals(tszt)) {
                failInfApplys.add(infApplyDO.getInternalNo());
            }
            Example gxTsqzkYzsbxxExample = new Example(GxYzwYzsbxxDO.class);
            gxTsqzkYzsbxxExample.createCriteria().andEqualTo("tsxxid", gxYzwTsjlDO.getTsxxid());
            sjptMapper.deleteByExample(gxTsqzkYzsbxxExample);

            //????????????????????????
            if (CollectionUtils.isNotEmpty(gxTsqzkYzsbxxList)) {
                for (GxYzwYzsbxxDO gxTsqzkYzsbxx : gxTsqzkYzsbxxList) {
                    gxTsqzkYzsbxx.setYzxxid(UUIDGenerator.generate16());
                    gxTsqzkYzsbxx.setTsxxid(gxYzwTsjlDO.getTsxxid());
                }
                sjptMapper.insertBatchSelective(gxTsqzkYzsbxxList);
            }
        }
        gxYzwTsjlDO.setTszt(tszt);
        gxYzwTsjlDO.setYzzt(yzzt);
        gxYzwTsjlDO.setTssj(new Date());
        if (sblx != null) {
            gxYzwTsjlDO.setSblx(sblx);
        }
        gxYzwTsjlDO.setSbycxx(sbycxx);
        sjptMapper.updateByPrimaryKey(gxYzwTsjlDO);

        return failInfApplys;
    }


    /**
     * ????????????????????????????????????
     *
     * @param infApplyDO infApplyDO
     * @Date 2020/11/25
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public void insertZjk(InfApplyDO infApplyDO, BdcXmDO bdcXmDO) {
        LOGGER.info("*********???????????? ????????????:{}", infApplyDO.getInternalNo());
        String internalNo = infApplyDO.getInternalNo();
        //??????????????????????????????--?????????????????????????????????????????????????????????????????????,??????????????????????????????
        BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
        bdcCzrzDO.setGzlslid(bdcXmDO.getGzlslid());
        bdcCzrzDO.setCzlx(BdcCzrzLxEnum.YZW_SJDR.key());
        List<BdcCzrzDO> bdcCzrzDOS = bdcCzrzFeignService.listBdcCzrz(bdcCzrzDO);
        Boolean isYzwDr = false;
        if (CollectionUtils.isNotEmpty(bdcCzrzDOS)) {
            isYzwDr = true;
        }
        if (!isYzwDr) {
            inapplyInfo(infApplyDO, bdcXmDO);
        }
        if (StringUtils.isNotBlank(infApplyDO.getInternalNo())) {
            //?????????????????????
            Example example = new Example(InfApplyMaterialDO.class);
            Example.Criteria criteria = example.createCriteria();
            //???????????????1??????????????????????????????????????????????????????
            if (!StringUtils.equals(infApplyDO.getDataSources(), "1") && (!isYzwDr)) {
                criteria.andEqualTo("internalNo", internalNo);
                List<InfApplyMaterialDO> infApplyMaterialList = sjptMapper.selectByExample(InfApplyMaterialDO.class, example);
                //??????????????????????????????
                pushTbmCaseMaterial(infApplyMaterialList);
            }

            //EMS???????????????
            example = new Example(InfApplyWlxxDO.class);
            criteria = example.createCriteria();
            criteria.andEqualTo("internalNo", internalNo);
            List<InfApplyWlxxDO> infApplyWlxxList = sjptMapper.selectByExample(InfApplyWlxxDO.class, example);
            //????????????????????????????????????????????????,??????????????????????????????
            if (!isYzwDr) {
                pushTbmCaseWlxx(infApplyWlxxList);
            }

            //??????????????????
            example = new Example(InfApplyProcessDO.class);
            criteria = example.createCriteria();
            example.setOrderByClause("to_number(no_ord)");
            criteria.andEqualTo("internalNo", internalNo);
            List<InfApplyProcessDO> infApplyProcessList = sjptMapper.selectByExample(InfApplyProcessDO.class, example);
            //??????????????????????????????
            pushTbmCaseprocesss(infApplyProcessList, infApplyWlxxList);

            //??????????????????
            //??????????????????????????????
            pushTbmCaseAccept(infApplyProcessList);

            //?????????????????????
            example = new Example(InfApplyJgzzDO.class);
            criteria = example.createCriteria();
            criteria.andEqualTo("internalNo", internalNo);
            List<InfApplyJgzzDO> infApplyJgzzList = sjptMapper.selectByExample(InfApplyJgzzDO.class, example);
            //??????????????????????????????
            pushTbmCaseJgzz(infApplyJgzzList);

            //?????????
            example = new Example(InfApplyResultDO.class);
            criteria = example.createCriteria();
            criteria.andEqualTo("internalNo", internalNo);
            List<InfApplyResultDO> infApplyResultList = sjptMapper.selectByExample(InfApplyResultDO.class, example);
            //??????????????????????????????
            pushTbmCaseResult(infApplyResultList, infApplyJgzzList);
        }

    }

    /**
     * ??????inapplyInfo??????
     *
     * @param infApplyDO InfApplyDO
     * @Date 2020/11/25
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public void inapplyInfo(InfApplyDO infApplyDO, BdcXmDO bdcXmDO) {
        TBmCasebaseinfoDO tBmCasebaseinfoDO = dozerBeanMapper.map(infApplyDO, TBmCasebaseinfoDO.class, "tbm_info");
        tBmCasebaseinfoDO.setCasetype("?????????");
        tBmCasebaseinfoDO.setCasenumber(1);

        WorkDayVO workDayVO = getCloudWorkDays();
        //???????????????????????????cnqx?????????
        if (infApplyDO.getApplyDate() != null && infApplyDO.getAnticipate() != null) {
            try {
                if (CommonConstantUtils.SYSTEM_VERSION_YC.equals(yzwVersion)) {
                    tBmCasebaseinfoDO.setCasepromisedate(DateUtilForWorkDay.getNextWorkDay(infApplyDO.getApplyDate(), bdcXmDO.convertIntegerCnqx(), workDayVO.getHolidayList(), workDayVO.getWorkList()));
                } else {
                    tBmCasebaseinfoDO.setCasepromisedate(DateUtilForWorkDay.getNextWorkDay(infApplyDO.getApplyDate(), infApplyDO.getAnticipate(), workDayVO.getHolidayList(), workDayVO.getWorkList()));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isBlank(tBmCasebaseinfoDO.getLegalperson())) {
            tBmCasebaseinfoDO.setLegalperson("???");
        }
        tBmCasebaseinfoDO.setSyncdatetime(new Date());
        //1????????????0?????????
        tBmCasebaseinfoDO.setSyncsign(0);
        LOGGER.info("*********????????????T_BM_CASEBASEINFO:" + infApplyDO.getInternalNo());
        Example example = new Example(TBmCasebaseinfoDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("caseno", tBmCasebaseinfoDO.getCaseno());
        List<TBmCasebaseinfoDO> tBmCasebaseinfoList = sjptMapper.selectByExample(TBmCasebaseinfoDO.class, example);
        if (CollectionUtils.isEmpty(tBmCasebaseinfoList)) {
            sjptMapper.insertSelective(tBmCasebaseinfoDO);
        }
    }


    private WorkDayVO getCloudWorkDays() {
        List<Work> works = workDayClient.getWorks();
        if (CollectionUtils.isEmpty(works) && works.get(0) == null) {
            throw new AppException("????????????????????????????????????");
        }
        List<WorkDay> workDays = workDayClient.getWorkDays(works.get(0).getId(), "", "");
        WorkDayVO workDayVO = new WorkDayVO();
        List<String> workList = new ArrayList<>(workDays.size());
        List<String> holidayList = new ArrayList<>(workDays.size());
        for (WorkDay workDay : workDays) {
            switch (workDay.getDayType()) {
                case WORKDAY:
                    try {
                        if (DateUtilForWorkDay.isWeekends(workDay.getWorkDay())) {
                            workList.add(workDay.getWorkDay());
                        }
                    } catch (Exception e) {
                        LOGGER.info("workList??????");
                    }
                    break;
                case OFFDAY:
                    try {
                        if (!DateUtilForWorkDay.isWeekends(workDay.getWorkDay())) {
                            holidayList.add(workDay.getWorkDay());
                        }
                    } catch (Exception e) {
                        LOGGER.info("holidayList??????");
                    }
                    break;
                case HOLIDAY:
                    holidayList.add(workDay.getWorkDay());
                    break;
            }
        }
        workDayVO.setWorkList(workList);
        workDayVO.setHolidayList(holidayList);
        return workDayVO;
    }


    /**
     * ??????TBmCasebaseinfo?????????????????????
     *
     * @param infApplyList infApplyList
     * @param proid
     * @Date 2020/11/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public void pushTbmCasebaseinfo(List<InfApplyDO> infApplyList, String proid) {
        if (CollectionUtils.isNotEmpty(infApplyList)) {
            BdcXmDO bdcXmDO = new BdcXmDO();
            for (InfApplyDO infApplyDO : infApplyList) {

                inapplyInfo(infApplyDO, bdcXmDO);
            }
        }
    }

    /**
     * ??????TBmCaseprocesss??????????????????
     *
     * @param infApplyProcessList infApplyProcessList
     * @param infApplyWlxxList    infApplyWlxxList
     * @Date 2020/11/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public void pushTbmCaseprocesss
    (List<InfApplyProcessDO> infApplyProcessList, List<InfApplyWlxxDO> infApplyWlxxList) {
        if (CollectionUtils.isNotEmpty(infApplyProcessList)) {
            LOGGER.info("*********????????????T_BM_CASEPROCESS ??????" + infApplyProcessList.size());
            int i = 0;
            for (InfApplyProcessDO infApplyProcess : infApplyProcessList) {
                i++;
                if (StringUtils.isNotBlank(infApplyProcess.getInternalNo()) && StringUtils.isNotBlank(infApplyProcess.getTacheName())) {
                    //??????dozer??????
                    TBmCaseprocessDO tBmCaseprocess = dozerBeanMapper.map(infApplyProcess, TBmCaseprocessDO.class, "tbm_process");
                    tBmCaseprocess.setOnlymark(UUIDGenerator.generate());
                    tBmCaseprocess.setTachestatus("1");
                    tBmCaseprocess.setSyncdatetime(new Date());
                    tBmCaseprocess.setSyncsign(0);
                    LOGGER.info("*********????????????T_BM_CASEPROCESS ???" + infApplyProcess.getNo());
                    Example example = new Example(TBmCaseprocessDO.class);
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andEqualTo("caseno", tBmCaseprocess.getCaseno());
                    criteria.andEqualTo("nodename", tBmCaseprocess.getNodename());
                    List<TBmCaseprocessDO> temp = sjptMapper.selectByExampleNotNull(example);
                    //???????????????
                    if (CollectionUtils.isEmpty(temp)) {
                        if (CollectionUtils.isNotEmpty(infApplyWlxxList) && i == infApplyProcessList.size()) {
                            dozerBeanMapper.map(infApplyWlxxList.get(0), tBmCaseprocess, "tbm_press_wlxx");
                        }
                        sjptMapper.insertSelective(tBmCaseprocess);
                    }

                }
            }
        }
    }

    /**
     * ??????TBmCaseResult??????????????????
     *
     * @param infApplyResultList infApplyResultList
     * @Date 2020/11/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public void pushTbmCaseResult
    (List<InfApplyResultDO> infApplyResultList, List<InfApplyJgzzDO> infApplyJgzzList) {
        if (CollectionUtils.isNotEmpty(infApplyResultList)) {
            LOGGER.info("*********????????????T_BM_CASERESULT ??????" + infApplyResultList.size());
            for (InfApplyResultDO infApplyResult : infApplyResultList) {
                if (StringUtils.isNotBlank(infApplyResult.getInternalNo())) {
                    //??????dozer??????
                    TBmCaseresultDO tBmCaseresult = dozerBeanMapper.map(infApplyResult, TBmCaseresultDO.class, "tbm_result");
                    tBmCaseresult.setResultstatus(StringUtils.equals(infApplyResult.getStatus(), "A") ? "1" : "2");
                    tBmCaseresult.setOnlymark(UUIDGenerator.generate());
                    tBmCaseresult.setSyncdatetime(new Date());
                    tBmCaseresult.setSyncsign(0);
                    tBmCaseresult.setIsdeliveryresults("0");
                    if (null != infApplyJgzzList && CollectionUtils.isNotEmpty(infApplyJgzzList)) {
                        tBmCaseresult.setResultcetrname(infApplyJgzzList.get(0).getDzzzNo());
                        tBmCaseresult.setResultcetrno(infApplyJgzzList.get(0).getZzBh());
                    }
                    LOGGER.info("*********????????????T_BM_CASERESULT ???" + infApplyResult.getNo());
                    Example example = new Example(TBmCaseresultDO.class);
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andEqualTo("caseno", tBmCaseresult.getCaseno());
                    List<TBmCaseresultDO> temp = sjptMapper.selectByExampleNotNull(example);
                    if (CollectionUtils.isEmpty(temp)) {
                        sjptMapper.insertSelective(tBmCaseresult);
                    }
                }
            }
        }
    }

    /**
     * ??????TBmCaseJgzz??????????????????
     *
     * @param infApplyJgzzList infApplyJgzzList
     * @Date 2020/11/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public void pushTbmCaseJgzz(List<InfApplyJgzzDO> infApplyJgzzList) {
        if (CollectionUtils.isNotEmpty(infApplyJgzzList)) {
            LOGGER.info("*********????????????T_BM_JGZZ ??????" + infApplyJgzzList.size());
            for (InfApplyJgzzDO infApplyJgzz : infApplyJgzzList) {
                if (StringUtils.isNotBlank(infApplyJgzz.getInternalNo()) && StringUtils.isNotBlank(infApplyJgzz.getDzzzNo()) && StringUtils.isNotBlank(infApplyJgzz.getZzFile())) {
                    TBmJgzzDO tBmJgzz = dozerBeanMapper.map(infApplyJgzz, TBmJgzzDO.class, "tbm_jgzz");
                    tBmJgzz.setOnlymark(UUIDGenerator.generate());
                    tBmJgzz.setSyncdatetime(new Date());
                    tBmJgzz.setSyncsign(0);
                    LOGGER.info("*********????????????T_BM_JGZZ ???" + infApplyJgzz.getNo());
                    //??????????????????
//                    tBmJgzz.setZzFile(uploadFile(tBmJgzz.getZzFile(), tBmJgzz.getCaseno()));
                    Example example = new Example(TBmJgzzDO.class);
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andEqualTo("caseno", tBmJgzz.getCaseno());
                    criteria.andEqualTo("dzzzNo", tBmJgzz.getDzzzNo());
                    List<TBmJgzzDO> temp = sjptMapper.selectByExampleNotNull(example);
                    if (CollectionUtils.isEmpty(temp)) {
                        sjptMapper.insertSelective(tBmJgzz);
                    }
                }
            }
        }
    }

    /**
     * ??????TBmCaseWlxx??????????????????
     *
     * @param infApplyWlxxList infApplyWlxxList
     * @Date 2020/11/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public void pushTbmCaseWlxx(List<InfApplyWlxxDO> infApplyWlxxList) {
        if (CollectionUtils.isNotEmpty(infApplyWlxxList)) {
            LOGGER.info("*********????????????T_BM_WLXX ??????" + infApplyWlxxList.size());
            InfApplyWlxxDO infApplyWlxx = infApplyWlxxList.get(0);
            if (StringUtils.isNotBlank(infApplyWlxx.getInternalNo())) {
                //??????dozer??????
                TBmWlxxDO tBmWlxx = dozerBeanMapper.map(infApplyWlxx, TBmWlxxDO.class, "tbm_wlxx");
                tBmWlxx.setNo(UUIDGenerator.generate());
                tBmWlxx.setSyncdatetime(new Date());
                tBmWlxx.setSyncsign(0);
                LOGGER.info("*********????????????T_BM_WLXX ???" + infApplyWlxx.getNo());
                Example example = new Example(TBmWlxxDO.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("internalNo", tBmWlxx.getInternalNo());
                List<TBmWlxxDO> temp = sjptMapper.selectByExampleNotNull(example);
                if (CollectionUtils.isEmpty(temp)) {
                    sjptMapper.insertSelective(tBmWlxx);
                }
            }

        }
    }

    /**
     * ??????TBmCaseMaterial??????????????????
     *
     * @param infApplyMaterialList infApplyMaterialList
     * @Date 2020/11/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public void pushTbmCaseMaterial(List<InfApplyMaterialDO> infApplyMaterialList) {
        if (CollectionUtils.isNotEmpty(infApplyMaterialList)) {
            LOGGER.info("*********????????????T_BM_CASEMATERIAL ??????" + infApplyMaterialList.size());
            for (InfApplyMaterialDO infApplyMaterial : infApplyMaterialList) {
                if (StringUtils.isNotBlank(infApplyMaterial.getInternalNo()) && StringUtils.isNotBlank(infApplyMaterial.getSjFileName())) {
                    //??????dozer??????
                    TBmCasematerialDO tBmCasematerial = dozerBeanMapper.map(infApplyMaterial, TBmCasematerialDO.class, "tbm_material");
                    tBmCasematerial.setOnlymark(UUIDGenerator.generate());
                    tBmCasematerial.setSyncdatetime(new Date());
                    tBmCasematerial.setSyncsign(0);
                    tBmCasematerial.setIstake(StringUtils.equals(tBmCasematerial.getIstake(), "true") ? "1" : "0");
                    LOGGER.info("*********????????????T_BM_CASEMATERIAL ???" + infApplyMaterial.getNo());
                    Example example = new Example(TBmCasematerialDO.class);
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andEqualTo("caseno", tBmCasematerial.getCaseno());
                    criteria.andEqualTo("matename", tBmCasematerial.getMatename());
                    List<TBmCasematerialDO> temp = sjptMapper.selectByExampleNotNull(example);
                    if (CollectionUtils.isEmpty(temp)) {
                        sjptMapper.insertSelective(tBmCasematerial);
                    }
                }
            }
        }
    }

    /**
     * ??????TBmCaseAccept??????????????????
     *
     * @param infApplyProcessList infApplyProcessList
     * @Date 2020/11/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public void pushTbmCaseAccept(List<InfApplyProcessDO> infApplyProcessList) {
        if (CollectionUtils.isNotEmpty(infApplyProcessList)) {
            LOGGER.info("*********????????????T_BM_CASEACCEPT ??????1");
            InfApplyProcessDO infApplyProcess = infApplyProcessList.get(0);
            if (StringUtils.isNotBlank(infApplyProcess.getInternalNo())) {
                TBmCaseacceptDO tBmCaseaccept = dozerBeanMapper.map(infApplyProcess, TBmCaseacceptDO.class, "tbm_accept");
                tBmCaseaccept.setOnlymark(UUIDGenerator.generate());
                tBmCaseaccept.setSyncdatetime(new Date());
                tBmCaseaccept.setSyncsign(0);
                tBmCaseaccept.setAcceptresult("1");
                LOGGER.info("*********????????????T_BM_CASEACCEPT ???" + infApplyProcess.getNo());
                Example example = new Example(TBmCaseacceptDO.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("caseno", tBmCaseaccept.getCaseno());
                List<TBmCaseacceptDO> temp = sjptMapper.selectByExampleNotNull(example);
                if (CollectionUtils.isEmpty(temp)) {
                    sjptMapper.insertSelective(tBmCaseaccept);
                }
            }

        }
    }

    @Override
    public List<String> checkTsqzkPl(List<String> yzwbhList, boolean isAll, boolean isToday) {
        if (isAll) {
            yzwbhList = listWtsYzwbh(null, null);
        } else if (isToday) {
            yzwbhList = listWtsYzwbh(DateUtil.formateTime(new Date()), DateUtil.formateTime(new Date()));
        }
        if (CollectionUtils.isEmpty(yzwbhList)) {
            throw new AppException("????????????????????????????????????");
        }
        LOGGER.info("????????????????????????????????????{}???", yzwbhList.size());
        //???????????????????????????????????????
        boolean sfbjs = (yzwbhList.size() <= 1);
        //????????????
        List<YzwTsThread> listThread = new ArrayList<>();
        for (String yzwbh : yzwbhList) {
            YzwTsThread yzwTsThread = new YzwTsThread(this, yzwbh);
            yzwTsThread.setSfbjs(sfbjs);
            listThread.add(yzwTsThread);
        }
        //?????????????????????
        threadEngine.excuteThread(listThread, true);
        //?????????????????????
        List<String> failyzwbhLists = new ArrayList<>();
        for (YzwTsThread yzwTsThread : listThread) {
            List<String> failyzwbhList = yzwTsThread.getYzwbhList();
            if (CollectionUtils.isNotEmpty(failyzwbhList)) {
                failyzwbhLists.addAll(failyzwbhList);
            }

        }

        LOGGER.info("?????????????????????????????????{}???,???????????????????????????:{}", failyzwbhLists.size(), failyzwbhLists);
        return failyzwbhLists;

    }

    /**
     * ?????????????????????
     *
     * @param pageable
     * @param yzwDTO
     * @return
     */
    @Override
    public Page<Map> listShareDataByPage(Pageable pageable, YzwDTO yzwDTO) {
        if (StringUtils.isBlank(yzwDTO.getSqrmc())
                && StringUtils.isBlank(yzwDTO.getYwbh())
                && StringUtils.isBlank(yzwDTO.getSqrzjh())
        ) {
            throw new MissingArgumentException("??????????????????????????????");
        }
        return sjptRepository.selectPaging("listShareDataByPage", yzwDTO, pageable);
    }

    /**
     * ???????????????????????????????????????
     *
     * @param caseno
     * @param processInsId
     */
    @Override
    public void tbyzwsj(String caseno, String processInsId) {
        LOGGER.info("*********??????YZW???????????? ???" + caseno);
        Example example = new Example(TBmCasebaseinfoDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("caseno", caseno);
        List<TBmCasebaseinfoDO> tBmCaseacceptList = sjptMapper.selectByExampleNotNull(example);
        if (CollectionUtils.isNotEmpty(tBmCaseacceptList)) {
            TBmCasebaseinfoDO tBmCaseacceptDO = tBmCaseacceptList.get(0);
            BdcXmQO BdcXmQO = new BdcXmQO();
            BdcXmQO.setGzlslid(processInsId);
            List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(BdcXmQO);
            for (BdcXmDO bdcXmDO : bdcXmDOS) {
                //?????????????????????????????????
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(bdcXmDO.getXmid());
                List<BdcQlrDO> qlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if (CollectionUtils.isNotEmpty(qlrDOList)) {
                    //???????????????
                    boolean haveSameQlr = false;
                    for (BdcQlrDO bdcQlrDO : qlrDOList) {
                        if (bdcQlrDO.getQlrmc().equals(tBmCaseacceptDO.getCaseapplicant())) {
                            haveSameQlr = true;
                            break;
                        }
                    }
                    if (haveSameQlr) {
                        continue;
                    }
                }
                //????????????????????????,??????
                BdcQlrDO BdcQlrDO = new BdcQlrDO();
                BdcQlrDO.setXmid(bdcXmDO.getXmid());
                BdcQlrDO.setQlrmc(tBmCaseacceptDO.getCaseapplicant());
                BdcQlrDO.setQlrlx(Integer.parseInt(tBmCaseacceptDO.getCaseapplicanttype()));
                BdcQlrDO.setQlrlb("1");
                BdcQlrDO.setZjzl(Integer.parseInt(tBmCaseacceptDO.getCaseapplicantpapertype()));
                BdcQlrDO.setZjh(tBmCaseacceptDO.getCaseapplicantpapercode());
                BdcQlrDO.setSxh(qlrDOList.size() + 1);
                bdcQlrFeignService.insertBdcQlr(BdcQlrDO);
            }
            //?????????????????????????????????,??????????????????????????????
            BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
            bdcCzrzDO.setCzsj(new Date());
            bdcCzrzDO.setCzzt(1);
            bdcCzrzDO.setCzlx(BdcCzrzLxEnum.YZW_SJDR.key());
            bdcCzrzDO.setCzyy(BdcCzrzLxEnum.YZW_SJDR.value());
            bdcCzrzDO.setGzlslid(processInsId);
            bdcCzrzDO.setCzr(userManagerUtils.getCurrentUserName());
            bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????????????????
     */
    private List<String> listWtsYzwbh(String tskssj, String tsjssj) {
        List<String> yzwbhList = new ArrayList<>();
        Map param = new HashMap();
        List<Integer> tszts = new ArrayList<>();
        tszts.add(Constants.YZW_TSZT_WTS);
        param.put("tszts", tszts);
        param.put("tskssj", tskssj);
        param.put("tsjssj", tsjssj);
        List<GxYzwTsjlDO> gxYzwTsjlDOList = yzwCheckMapper.listTsjl(param);
        if (CollectionUtils.isNotEmpty(gxYzwTsjlDOList)) {
            for (GxYzwTsjlDO gxYzwTsjlDO : gxYzwTsjlDOList) {
                yzwbhList.add(gxYzwTsjlDO.getYzwbh());
            }

        }
        return yzwbhList;

    }

    /**
     * ??????????????? ????????????
     *
     * @param infApply
     * @param jgyts
     * @param slyts
     */
    public void updateExchangeTBm(InfApplyDO infApply, boolean jgyts, boolean slyts) {
        TBmCasebaseinfoDO tBmCasebaseinfo = sjptMapper.selectByPrimaryKey(TBmCasebaseinfoDO.class, infApply.getInternalNo());
        boolean modfied = false;
        if (tBmCasebaseinfo != null && (jgyts || slyts)) {
            if (jgyts && !StringUtils.equalsIgnoreCase(tBmCasebaseinfo.getJgyts(), CommonConstantUtils.TSZT_SUCCESS_S)) {
                tBmCasebaseinfo.setJgyts(CommonConstantUtils.TSZT_SUCCESS_S);
                modfied = true;
            }

            if (modfied) {
                sjptMapper.updateByPrimaryKey(tBmCasebaseinfo);
            }
        }
    }

    public String uploadFile(String fileUrl, String yzwbh) throws AppException {
        if (StringUtils.isBlank(fileUrl) || StringUtils.isBlank(yzwbh)) {
            return null;
        }
        /*InputStream is = null;
        byte[] bytes = null;
        CloseableHttpResponse response = null;
        StringBuffer path = new StringBuffer();
        try {
            HttpGet httpGet = new HttpGet(fileUrl);
            response = ((CloseableHttpClient) httpClient).execute(httpGet);
            if (response != null && org.apache.http.HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                bytes = EntityUtils.toByteArray(response.getEntity());
            }
            StringBuffer sb = new StringBuffer();
            sb.append("/1/").append(DateUtils.getCurrYear()).append("/").
                    append(DateUtils.getCurrMD()).append("/").append(yzwbh);

            String nodeId = StringUtils.substring(fileUrl, StringUtils.indexOf(fileUrl, "fid=") + 4, fileUrl.length());
            Node node = nodeService.getNode(Integer.valueOf(nodeId));
            String fileName = "";
            if (node != null) {
                fileName = node.getName();
            }

            boolean boo = FtpUtil.uploadFile(Constants.FTP_IP, Constants.FTP_USER, Constants.FTP_PASSWORD,
                    StringUtils.isNotBlank(Constants.FTP_PORT) ? Integer.valueOf(Constants.FTP_PORT) : 0,
                    sb.toString(), fileName, new ByteArrayInputStream(bytes));

            if (boo) {
                path.append("/").append(AppConfig.getProperty("infApply.orgId")).append(sb.toString()).append("/").append(fileName);
            }
        } catch (Exception e) {
            LOGGER.error("msg", e);
            throw new AppException(e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return path.toString();
    }*/
        return "";
    }
}
