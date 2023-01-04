package cn.gtmap.realestate.exchange.service.impl.inf.sjpt;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.util.CardNumberTransformation;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeCxqq;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeCxqqXm;
import cn.gtmap.realestate.exchange.core.domain.sjpt.PeCxDO;
import cn.gtmap.realestate.exchange.core.dto.sjpt.SjptCxRequestHead;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.*;
import cn.gtmap.realestate.exchange.core.dto.sjpt.token.request.SjptGetTokenRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.sjpt.token.request.SjptGetTokenRequestData;
import cn.gtmap.realestate.exchange.core.dto.sjpt.token.request.SjptGetTokenRequestHead;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.thread.SjptQuarzCommitCxHandlerThread;
import cn.gtmap.realestate.exchange.core.thread.SjptQuarzCxHandlerThread;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.sjpt.CxjgService;
import cn.gtmap.realestate.exchange.service.inf.sjpt.QueryCxjgService;
import cn.gtmap.realestate.exchange.service.inf.sjpt.thread.GxPeCxqqServiceThread;
import cn.gtmap.realestate.exchange.util.BeanList;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import cn.gtmap.realestate.exchange.util.constants.SjptConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-29
 * @description
 */
@Service
public class CxjgServiceImpl implements CxjgService {

    private static Logger LOGGER = LoggerFactory.getLogger(CxjgServiceImpl.class);

    @Autowired
    private EntityMapper sjptEntityMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private GxPeCxqqServiceThread gxPeCxqqServiceThread;

    @Autowired
    private ThreadEngine threadEngine;

    @Value("${sjpt.queryCxjg.thread.threshold.count:2000}")
    private Integer thresholdCount;

    @Value("${sjpt.queryCxjg.thread.max.count:5000}")
    private Integer maxCount;

    @Value("${sjpt.queryCxjg.thread.single.thread.count:500}")
    private Integer singleThreadCount;

    @Value("${sjpt.xzqdm}")
    private String xzqdm;

    @Value("${sjpt.username}")
    private String userName;

    @Value("${sjpt.password}")
    private String password;

    /**
     * 省厅查询不查询证件号的证件种类，多个英文逗号分隔。例如设置6，则证件种类为6的时候，只根据名称查询
     */
    @Value("${sjpt.bcxzjhzjzl:}")
    private String bcxzjhzjzl;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    private BdcXmMapper bdcXmMapper;

    /**
     * 上一次任务是否结束标志
     */
    private static boolean LAST_FINISHED = true;


    /**
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 验证上一次的任务是否结束
     */
    @Override
    public boolean checkLastQuarzFinished() {
        return LAST_FINISHED;
    }

    /**
     * @param callMethod
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 执行提交查询请求
     */
    @Override
    public void executeCommitCxsq(String callMethod) {
        Example example = new Example(GxPeCxqq.class);
        example.setOrderByClause("cjsj asc");
        example.createCriteria().andEqualTo("zt", SjptConstants.CXQQ_ZT_YCLWTS);
        List<GxPeCxqq> gxPeCxqqList = sjptEntityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(gxPeCxqqList)) {
            //根据单号去重
            gxPeCxqqList = gxPeCxqqList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(GxPeCxqq::getCxsqdh))), ArrayList::new));
            //postMan调用处理
            if (SjptConstants.SJPT_QUARTZ_INTERFACE_CALL_METHOD_POSTMAN.equals(callMethod)) {
                if (gxPeCxqqList.size() > 0 && gxPeCxqqList.size() <= thresholdCount) {
                    gxPeCxqqServiceThread.batchCommitCxsq(gxPeCxqqList);
                } else if (gxPeCxqqList.size() <= maxCount) {
                    commitCxqqInTurn(gxPeCxqqList);
                } else if (gxPeCxqqList.size() > maxCount) {
                    gxPeCxqqList = new ArrayList(gxPeCxqqList.subList(0, maxCount));
                    commitCxqqInTurn(gxPeCxqqList);
                }
            } else {
                //定时任务处理
                //循环线程
                if (gxPeCxqqList.size() <= thresholdCount) {
                    gxPeCxqqServiceThread.batchCommitCxsq(gxPeCxqqList);
                } else {
                    commitCxqqInTurn(gxPeCxqqList);
                }
            }
        }
    }

    /**
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 执行查询请求
     */
    @Override
    public void executeQueryCxsq(String callMethod) {
        try {
            LAST_FINISHED = false;
            Example example = new Example(GxPeCxqq.class);
            example.setOrderByClause("cjsj asc");
            String[] ztsArr = {SjptConstants.CXQQ_ZT_WCL, SjptConstants.CXQQ_ZT_BFCL};
            List ztsList = Arrays.asList(ztsArr);
            example.createCriteria().andIn("zt", ztsList);
            List<GxPeCxqq> gxPeCxqqList = sjptEntityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(gxPeCxqqList)) {
                //postMan调用处理
                if (SjptConstants.SJPT_QUARTZ_INTERFACE_CALL_METHOD_POSTMAN.equals(callMethod)) {
                    if (gxPeCxqqList.size() > 0 && gxPeCxqqList.size() <= thresholdCount) {
                        gxPeCxqqServiceThread.batchExexuteQueryCxqq(gxPeCxqqList, true);
                    } else if (gxPeCxqqList.size() <= maxCount) {
                        queryCxqqInTurn(gxPeCxqqList);
                    } else if (gxPeCxqqList.size() > maxCount) {
                        gxPeCxqqList = new ArrayList(gxPeCxqqList.subList(0, maxCount));
                        queryCxqqInTurn(gxPeCxqqList);
                    }
                } else {
                    //定时任务处理
                    //循环线程
                    if (gxPeCxqqList.size() <= thresholdCount) {
                        gxPeCxqqServiceThread.batchExexuteQueryCxqq(gxPeCxqqList, true);
                    } else {
                        //多线程处理操作
                        queryCxqqInTurn(gxPeCxqqList);
                    }
                }
            }
            LOGGER.info("------------------执行查询请求结束");
        } catch (Exception e) {
            LOGGER.error("errorMsg:", e);
        }finally {
            LAST_FINISHED = true;
        }
    }

    private void queryCxqqInTurn(List<GxPeCxqq> gxPeCxqqList) {
        List<List<GxPeCxqq>> list = groupListByQuantity(gxPeCxqqList, singleThreadCount);
        List<SjptQuarzCxHandlerThread> listThread = new ArrayList<>();
        for (List<GxPeCxqq> gxPeCxqqs : list) {
            SjptQuarzCxHandlerThread sjptQuarzCxHandlerThread = new SjptQuarzCxHandlerThread(gxPeCxqqServiceThread, gxPeCxqqs, true);
            listThread.add(sjptQuarzCxHandlerThread);
        }
        threadEngine.excuteThread(listThread, true);
    }

    private void commitCxqqInTurn(List<GxPeCxqq> gxPeCxqqList) {
        List<SjptQuarzCommitCxHandlerThread> listThread = new ArrayList<>();
        //多线程处理操作
        List<List<GxPeCxqq>> list = groupListByQuantity(gxPeCxqqList, singleThreadCount);
        for (List<GxPeCxqq> gxPeCxqqs : list) {
            SjptQuarzCommitCxHandlerThread sjptQuarzCxHandlerThread = new SjptQuarzCommitCxHandlerThread(gxPeCxqqServiceThread, gxPeCxqqs);
            listThread.add(sjptQuarzCxHandlerThread);
        }
        threadEngine.excuteThread(listThread, true);
    }


    /**
     * @param gxPeCxqqXm 查询请求对象
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @version 1.0, 2017/10/18
     * @description 从数据源（共享库或登记业务库）获取到查询请求结果并保存至GXPe库
     */
    @Override
    @Transactional(value = "sjptTransactionManager", rollbackFor = Exception.class)
    public GxPeCxqqXm queryAndSaveApply(final GxPeCxqqXm gxPeCxqqXm) throws Exception {
        LOGGER.info("省厅查询：项目开始，查询申请单号{}", gxPeCxqqXm.getCxsqdh());
        PeCxDO peCxDO = new PeCxDO();
        //转换成查询对象
        BeanUtils.copyProperties(gxPeCxqqXm, peCxDO);

        peCxDO.setQlrzjh(gxPeCxqqXm.getQlrzjh());
        // 判断是否存在不需要查询证件号的情况
        this.setZjhNull(peCxDO, gxPeCxqqXm.getQlrzjlx(), gxPeCxqqXm.getQlrmc());
        //身份证号转换
        if (StringUtils.isNotBlank(peCxDO.getQlrzjh())) {
            peCxDO.setQlrzjhArr(Stream.of(peCxDO.getQlrzjh()).map(CardNumberTransformation::zjhTransformation).collect(Collectors.joining(",")));
        }
        Integer qlrNum = bdcXmMapper.countQlrByZjh(peCxDO.getQlrmc(), peCxDO.getQlrzjhArr(), null, null);

        if (qlrNum > 0) {
            PeCommitCxsqjg peCommitCxsqjg = new PeCommitCxsqjg();
            peCommitCxsqjg.setWsbh(peCxDO.getWsbh());
            for (QueryCxjgService queryCxjgService : BeanList.getQueryCxjgServiceList()) {
                queryCxjgService.cxsj(peCxDO, peCommitCxsqjg, Constants.SJCX_PLCX);
            }
            for (QueryCxjgService queryCxjgService : BeanList.getQueryCxjgServiceList()) {
                queryCxjgService.saveCxjg(peCommitCxsqjg, peCxDO.getCxsqdh());
            }
        }
        //保存项目状态
        gxPeCxqqXm.setCxzt(SjptConstants.CXQQXM_ZT_YCL);
        LOGGER.info("省厅查询：项目结束，查询申请单号{}", gxPeCxqqXm.getCxsqdh());
        return gxPeCxqqXm;
    }


    /**
     * @param gxPeCxqq
     * @return cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeCxqq
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 刷新查询申请状态
     */
    @Override
    public GxPeCxqq flushCxqqZt(final GxPeCxqq gxPeCxqq) {
        if (gxPeCxqq != null
                && StringUtils.isNotBlank(gxPeCxqq.getCxsqdh())
                && !StringUtils.equals(gxPeCxqq.getZt(), "3")) {
            //首先判断是否还有未查询的项目
            String zt = "";
            HashMap queryMap = new HashMap();
            queryMap.put("cxsqdh", gxPeCxqq.getCxsqdh());

            Example example = new Example(GxPeCxqqXm.class);
            example.createCriteria().andEqualTo("cxsqdh", gxPeCxqq.getCxsqdh());
            List<GxPeCxqqXm> list = sjptEntityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(list)) {
                example = new Example(GxPeCxqqXm.class);
                example.createCriteria().andEqualTo("cxsqdh", gxPeCxqq.getCxsqdh())
                        .andEqualNvlTo("cxzt", SjptConstants.CXQQXM_ZT_WCL, SjptConstants.CXQQXM_ZT_WCL);
                List<GxPeCxqqXm> wcxList = sjptEntityMapper.selectByExample(example);
                if (CollectionUtils.isEmpty(wcxList)) {
                    //全部查询
                    zt = SjptConstants.CXQQ_ZT_YCLWTS;
                } else {
                    example = new Example(GxPeCxqqXm.class);
                    example.createCriteria().andEqualTo("cxsqdh", gxPeCxqq.getCxsqdh())
                            .andEqualNvlTo("cxzt", SjptConstants.CXQQXM_ZT_YCL, SjptConstants.CXQQXM_ZT_WCL);
                    List<GxPeCxqqXm> ycxList = sjptEntityMapper.selectByExample(example);
                    if (CollectionUtils.isNotEmpty(ycxList)) {
                        //部分查询
                        zt = SjptConstants.CXQQ_ZT_BFCL;
                    } else {
                        //未查询
                        zt = SjptConstants.CXQQ_ZT_WCL;
                    }
                }
            } else {
                //有请求 但无请求项目时，状态默认为已查询
                zt = SjptConstants.CXQQ_ZT_YCLWTS;
            }
            gxPeCxqq.setZt(zt);
        }
        return gxPeCxqq;
    }

    /**
     * @param cxsqdh
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.SjptCxjgRequestData>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取查询结果
     */
    @Override
    public SjptCxjgRequestDTO getCxjgListByCxsqdh(String cxsqdh) {
        SjptCxjgRequestDTO sjptCxjgRequestDTO = new SjptCxjgRequestDTO();
        List<SjptCxjgRequestDataWithEmptyString> list = new ArrayList<>();
        if (StringUtils.isNotBlank(cxsqdh)) {
            SjptCxjgRequestDataWithEmptyString requestData = new SjptCxjgRequestDataWithEmptyString();
            requestData.setCxsqdh(cxsqdh);
            Example example = new Example(GxPeCxqqXm.class);
            example.createCriteria().andEqualTo("cxsqdh", cxsqdh);
            List<GxPeCxqqXm> cxqqXmList = sjptEntityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(cxqqXmList)) {
                List<PeCommitCxsqjgWithEmptyString> cxjgList = new ArrayList<>();
                //根据文书编号去重
                cxqqXmList = cxqqXmList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(GxPeCxqqXm::getWsbh))), ArrayList::new));
                for (GxPeCxqqXm cxqqXm : cxqqXmList) {
                    PeCommitCxsqjgWithEmptyString peCommitCxsqjg = new PeCommitCxsqjgWithEmptyString();
                    peCommitCxsqjg.setWsbh(cxqqXm.getWsbh());
                    for (QueryCxjgService queryCxjgService : BeanList.getQueryCxjgServiceList()) {
                        // 查询结果
                        List result = queryCxjgService.queryCxjg(cxsqdh, cxqqXm.getWsbh());
                        if (CollectionUtils.isNotEmpty(result)) {
                            //根据qlid 去重
                            Map<String, String> qlidMap = new HashMap<>(result.size());
                            List commitList = new ArrayList(result.size());
                            for (Object ql : result) {
                                JSONObject qlObj = JSON.parseObject(JSON.toJSONString(ql));
                                if (StringUtils.isNotBlank(qlObj.getString("qlid")) && !qlidMap.containsKey(qlObj.getString("qlid"))) {
                                    qlidMap.put(qlObj.getString("qlid"), qlObj.getString("qlid"));
                                    commitList.add(ql);
                                }
                            }
                            // 保存结果进peCommitCxsqjg
                            queryCxjgService.setQlListWithDefaultValueEmptyString(peCommitCxsqjg, commitList);
                        }
                    }
                    cxjgList.add(peCommitCxsqjg);
                }
                requestData.setCxsqjg(cxjgList);
            }
            list.add(requestData);
        }
        sjptCxjgRequestDTO.setData(list);
        SjptCxRequestHead sjptCxRequestHead = new SjptCxRequestHead();
        sjptCxRequestHead.setXzqdm(xzqdm);
        SjptGetTokenRequestDTO sjptGetTokenRequestDTO = new SjptGetTokenRequestDTO();
        SjptGetTokenRequestHead sjptGetTokenRequestHead = new SjptGetTokenRequestHead();
        sjptGetTokenRequestHead.setXzqdm(xzqdm);
        sjptGetTokenRequestDTO.setHead(sjptGetTokenRequestHead);
        SjptGetTokenRequestData sjptGetTokenRequestData = new SjptGetTokenRequestData();
        sjptGetTokenRequestData.setUsername(userName);
        sjptGetTokenRequestData.setPassword(password);
        sjptGetTokenRequestDTO.setData(sjptGetTokenRequestData);
        String sjptToken = commonService.getToken("sjpt_token", sjptGetTokenRequestDTO);
        sjptCxRequestHead.setToken(sjptToken);
        sjptCxjgRequestDTO.setHead(sjptCxRequestHead);
        return sjptCxjgRequestDTO;
    }

    /**
     * @param cxData
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.SjptCxjgRequestData>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 省厅实时查询
     */
    @Override
    public List<SjptCxjgRequestData> getCxjgListByPeCxDO(List<JSONObject> cxData) throws Exception {
        try {
            List<SjptCxjgRequestData> dataList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(cxData)) {
                SjptCxjgRequestData requestData = new SjptCxjgRequestData();
                List<PeCommitCxsqjg> cxjgList = new ArrayList<>();
                PeCxDO cxDOtemp = JSONObject.toJavaObject(cxData.get(0), PeCxDO.class);
                requestData.setCxsqdh(cxDOtemp.getCxsqdh());
                requestData.setCxsqjg(cxjgList);
                for (JSONObject peCxDOJson : cxData) {
                    PeCxDO peCxDO = JSONObject.toJavaObject(peCxDOJson, PeCxDO.class);
                    //身份证号转换
                    if (StringUtils.isNotBlank(peCxDO.getQlrzjh())) {
                        peCxDO.setQlrzjhArr(Stream.of(peCxDO.getQlrzjh()).map(CardNumberTransformation::zjhTransformation).collect(Collectors.joining(",")));
                    }
                    Integer qlrNum = bdcXmMapper.countQlrByZjh(peCxDO.getQlrmc(), peCxDO.getQlrzjhArr(), peCxDO.getBdcqzh(), null);

                    if (qlrNum > 0) {
                        PeCommitCxsqjg cxsqjg = new PeCommitCxsqjg();
                        cxsqjg.setWsbh(peCxDO.getWsbh());
                        for (QueryCxjgService queryCxjgService : BeanList.getQueryCxjgServiceList()) {
                            queryCxjgService.cxsj(peCxDO, cxsqjg, Constants.SJCX_SSCX);
                        }
                        cxjgList.add(cxsqjg);
                    }

                }
                dataList.add(requestData);
            }
            return dataList;
        } catch (Exception e) {
            LOGGER.error("省厅查询异常，查询数据：{}", cxData, e);
            throw e;
        }
    }

    /**
     * 按数量分组list
     * @param list
     * @param quantity
     * @return
     */
    public static List<List<GxPeCxqq>> groupListByQuantity(List<GxPeCxqq> list, int quantity) {
        if (list == null || list.size() == 0) {
            return null;
        }

        if (quantity <= 0) {
            new IllegalArgumentException("Wrong quantity.");
        }

        List<List<GxPeCxqq>> wrapList = new ArrayList();
        int count = 0;
        while (count < list.size()) {
            wrapList.add(new ArrayList(list.subList(count, Math.min((count + quantity), list.size()))));
            count += quantity;
        }

        return wrapList;
    }

    /**
     * 判断是否存在不需要查询证件号的情况，如果设置了则设证号为空，即只根据权利人名称查询
     * @param peCxDO 查询信息实体
     * @param qlrzjlx 权利人证件种类
     * @param qlrmc 权利人名称
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private void setZjhNull(PeCxDO peCxDO, String qlrzjlx, String qlrmc) {
        if (StringUtils.isNotBlank(bcxzjhzjzl)) {
            if (StringToolUtils.existItemEquals(qlrzjlx, bcxzjhzjzl.split(CommonConstantUtils.ZF_YW_DH))) {
                peCxDO.setQlrzjh(null);
                LOGGER.info("当前配置bcxzjhzjzl设置证件种类为{}时只根据权利人名称：{}查询", bcxzjhzjzl, qlrmc);
            }
        }
    }
}
