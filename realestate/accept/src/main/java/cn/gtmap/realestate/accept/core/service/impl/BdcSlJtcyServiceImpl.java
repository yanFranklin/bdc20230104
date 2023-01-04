package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.mapper.BdcSlJtcyMapper;
import cn.gtmap.realestate.accept.core.service.BdcSlJtcyService;
import cn.gtmap.realestate.accept.core.service.BdcSlSqrService;
import cn.gtmap.realestate.accept.core.service.BdcSlXmService;
import cn.gtmap.realestate.accept.core.thread.BdcSlJtcyFeedBackThread;
import cn.gtmap.realestate.accept.service.BdcCommonSlService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJtcyDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJtcyQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSqrQO;
import cn.gtmap.realestate.common.core.qo.accept.CompareHyxxQO;
import cn.gtmap.realestate.common.core.qo.accept.GetJtcyxxQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/26
 * @description 受理家庭成员
 */
@Service
public class BdcSlJtcyServiceImpl implements BdcSlJtcyService,BdcCommonSlService {
    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlJtcyServiceImpl.class);
    /**
     * 南通调用省级平台居民身份信息申请接口BeanName
     */
    private static final String APPLY_BEAN_NAME = "acceptJtcyCxsq";
    /**
     * 申请接口成功返回编码
     */
    private static final String SUCCESS_CODE = "00000";
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private BdcSlJtcyMapper bdcSlJtcyMapper;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    private BdcSlSqrService bdcSlSqrService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    ThreadEngine threadEngine;
    @Autowired
    BdcSlXmService bdcSlXmService;

    private static final String MSG_BDSUCCESS = "比对通过";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 比对校验码：通过
     */
    private static final String COMPARE_CODE_SUCCESS = "0000";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 比对校验码：警告信息
     */
    private static final String COMPARE_CODE_ALERT = "0001";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 比对校验码：提示信息
     */
    private static final String COMPARE_CODE_CONFIRM = "0002";

    /**
     * 互联网推送过来的一窗受理流程对应的审批来源
     */
    private static final int[] HLW_TS_YCSL = {CommonConstantUtils.SPLY_WWSQ_YCSL};

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  一体化流程是否调取公安户籍接口获取家庭成员信息
     */
    @Value("${ycsl.getGaJtcyxx:false}")
    public boolean getGaJtcyxx;

    @Override
    public List<BdcSlJtcyDO> listBdcSlJtcyBySqrid(String sqrid) {
        List<BdcSlJtcyDO> bdcSlJtcyDOList = Collections.emptyList();
        if (StringUtils.isNotBlank(sqrid)) {
            Example example = new Example(BdcSlJtcyDO.class);
            example.createCriteria().andEqualTo("sqrid", sqrid);
            bdcSlJtcyDOList = entityMapper.selectByExample(example);
        }
        if (bdcSlJtcyDOList ==null) {
            bdcSlJtcyDOList = Collections.emptyList();
        }
        return bdcSlJtcyDOList;

    }

    /**
     * @param bdcSlJtcyDO 受理家庭成员信息
     * @return 保存数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存受理家庭成员信息
     */
    @Override
    public Integer updateBdcSlJtcy(BdcSlJtcyDO bdcSlJtcyDO) {
        if (bdcSlJtcyDO != null && StringUtils.isNotBlank(bdcSlJtcyDO.getJtcyid())) {
            return entityMapper.updateByPrimaryKeySelective(bdcSlJtcyDO);
        }
        throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
    }

    /**
     * @param jtcyid 家庭成员id
     * @return 不动产受理交易信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据交易信息id获取不动产受理交易信息
     */
    @Override
    public BdcSlJtcyDO queryBdcSlJtcyByJtcyid(String jtcyid) {
        if (StringUtils.isBlank(jtcyid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return entityMapper.selectByPrimaryKey(BdcSlJtcyDO.class, jtcyid);
    }

    /**
     * @param bdcSlJtcyDO 家庭成员信息
     * @return 不动产家庭成员信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产受理家庭成员信息
     */
    @Override
    public BdcSlJtcyDO insertBdcSlJtcy(BdcSlJtcyDO bdcSlJtcyDO) {
        if (bdcSlJtcyDO != null) {
            if (StringUtils.isBlank(bdcSlJtcyDO.getJtcyid())) {
                bdcSlJtcyDO.setJtcyid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcSlJtcyDO);
        }
        return bdcSlJtcyDO;
    }

    /**
     * @param jtcyid 家庭成员id
     * @return 删除数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理家庭成员信息
     */
    @Override
    public Integer deleteBdcSlJtcyByJtcyid(String jtcyid) {
        int result = 0;
        if (StringUtils.isNotBlank(jtcyid)) {
            result = entityMapper.deleteByPrimaryKey(BdcSlJtcyDO.class, jtcyid);
        }
        return result;
    }

    /**
     * @param sqrid 申请人id
     * @return 删除数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理家庭成员信息
     */
    @Override
    public Integer deleteBdcSlJtcyBySqrid(String sqrid) {
        int result = 0;
        if (StringUtils.isNotBlank(sqrid)) {
            Example example = new Example(BdcSlJtcyDO.class);
            example.createCriteria().andEqualTo("sqrid", sqrid);
            result = entityMapper.deleteByExample(example);
        }
        return result;
    }

    @Override
    public List<BdcSlJtcyDO> listBdcSlJtcy(BdcSlJtcyQO bdcSlJtcyQO){
        if (!CheckParameter.checkAnyParameter(bdcSlJtcyQO)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return bdcSlJtcyMapper.listBdcSlJtcy(bdcSlJtcyQO);

    }

    @Override
    public void deleteBatchBdcSlJtcy(List<String> sqridList,String zjh){
        if(CollectionUtils.isNotEmpty(sqridList) && StringUtils.isNotBlank(zjh)){
            bdcSlJtcyMapper.deleteBatchBdcSlJtcy(sqridList, zjh);
        }
    }

    @Override
    public Object getHygaxx(GetJtcyxxQO getJtcyxxQO){
        if(StringUtils.isAnyBlank(getJtcyxxQO.getQlrzjh(),getJtcyxxQO.getBeanName())){
            LOGGER.error("查询婚姻公安信息参数异常{}", getJtcyxxQO);
            throw new AppException("查询婚姻公安信息参数异常！"+ getJtcyxxQO.toString());
        }
        return exchangeInterfaceFeignService.requestInterface(getJtcyxxQO.getBeanName(),getJtcyxxQO);
    }

    @Override
    public List<BdcSlJtcyDO> getJtcyxx(GetJtcyxxQO getJtcyxxQO){
        if(StringUtils.isBlank(getJtcyxxQO.getQlrmc()) ||StringUtils.isBlank(getJtcyxxQO.getQlrzjh()) ||StringUtils.isBlank(getJtcyxxQO.getSqrid()) ||StringUtils.isBlank(getJtcyxxQO.getSlbh())){
            throw new MissingArgumentException("查询参数缺失！");
        }
        String cxrmc =getJtcyxxQO.getQlrmc();
        List<BdcSlJtcyDO> bdcSlJtcyDOList =new ArrayList<>();
        //配偶信息
        if(StringUtils.isNotBlank(getJtcyxxQO.getHybeanName())){
            getJtcyxxQO.setBeanName(getJtcyxxQO.getHybeanName());
        }else{
            getJtcyxxQO.setBeanName("acceptHyxx");
        }
        BdcSlJtcyDO poJtcy = this.requestHyxx(getJtcyxxQO,true);
        if (Objects.nonNull(poJtcy) && StringUtils.isNotBlank(poJtcy.getJtcymc()) && StringUtils.isNotBlank(poJtcy.getZjh())) {
            bdcSlJtcyDOList.add(poJtcy);
        }
        //调取户籍接口
        bdcSlJtcyDOList.addAll(dealHjJtcyxx(getJtcyxxQO));
        if(poJtcy != null &&StringUtils.isNotBlank(poJtcy.getJtcymc()) &&StringUtils.isNotBlank(poJtcy.getZjh())) {
            getJtcyxxQO.setQlrmc(poJtcy.getJtcymc());
            getJtcyxxQO.setQlrzjh(poJtcy.getZjh());
            //配偶调取户籍接口
            bdcSlJtcyDOList.addAll(dealHjJtcyxx(getJtcyxxQO));
        }

        //家庭成员去重以及去除申请人本人
        LOGGER.info("获取的家庭成员信息,包含查询人{}", JSON.toJSONString(bdcSlJtcyDOList));
        bdcSlJtcyDOList = bdcSlJtcyDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getZjh()))), ArrayList::new));
        bdcSlJtcyDOList = bdcSlJtcyDOList.stream().filter(jtcy -> !StringUtils.equals(cxrmc,jtcy.getJtcymc())).collect(Collectors.toList());
        return bdcSlJtcyDOList;
    }

    @Override
    @Transactional
    public List<BdcSlJtcyDO> saveJkJtcyxx(List<BdcSlJtcyDO> bdcSlJtcyDOList,GetJtcyxxQO getJtcyxxQO){
        // 互联网+的不做数据库的更新
        if(!sfycsl(getJtcyxxQO.getSply())){
            //删除原有家庭成员
            deleteBdcSlJtcyBySqrid(getJtcyxxQO.getSqrid());
        }
        //家庭成员根据证件号去重处理
        if(CollectionUtils.isNotEmpty(bdcSlJtcyDOList)) {
            List<BdcSlJtcyDO> jtcyList = bdcSlJtcyDOList.stream().filter(jtcy -> StringUtils.isNotBlank(jtcy.getZjh())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(jtcyList)) {
                List<BdcSlJtcyDO> bdcSlJtcyDOS = jtcyList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getZjh()))), ArrayList::new));
                if(!sfycsl(getJtcyxxQO.getSply())){
                    for(BdcSlJtcyDO bdcSlJtcyDO:bdcSlJtcyDOS){
                        bdcSlJtcyDO.setJtcyid(UUIDGenerator.generate16());
                        bdcSlJtcyDO.setSqrid(getJtcyxxQO.getSqrid());
                        insertBdcSlJtcy(bdcSlJtcyDO);
                    }
                }else{
                    return bdcSlJtcyDOS;
                }
            }
        }
        return new ArrayList<>();

    }

    @Override
    public void delBatchJtcyxx(String gzlslid, String sqrlb){
        if (StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("gzlslid");
        }
        Map<String,Object> paramMap = new HashMap<>();
        List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        //批量更新,根据其中一个判断是否更新还是新增
        if(CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            paramMap.put("xmidList", bdcXmDTOList.stream().map(x-> x.getXmid()).collect(Collectors.toList()));
            paramMap.put("sqrlb",sqrlb);
            bdcSlJtcyMapper.delBatchJtcyxx(paramMap);
        }
    }

    /**
     * isrk 是否更新婚姻状态
     * 调用第三方接口获取配偶信息
     * <p>1、更新受理权利人的婚姻状态
     *    2、获取配偶信息并返回
     */
    @Override
    public BdcSlJtcyDO requestHyxx(GetJtcyxxQO getJtcyxxQO,boolean isrk){
         if(StringUtils.isBlank(getJtcyxxQO.getBeanName())) {
             //调取婚姻接口
             getJtcyxxQO.setBeanName("acceptHyxx");
         }
        Object response =getHygaxx(getJtcyxxQO);

        if(Objects.nonNull(response)){
            LOGGER.info("申请人名称:{},调取婚姻接口成功,返回结果：{}", getJtcyxxQO.getQlrmc(), response);
            JSONObject hyxxObject = JSONObject.parseObject(JSONObject.toJSONString(response));
            if(Objects.nonNull(hyxxObject.get("hyzt")) && isrk && !sfycsl(getJtcyxxQO.getSply())){
                //更新婚姻状态
                BdcSlSqrDO bdcSlSqrDO =new BdcSlSqrDO();
                bdcSlSqrDO.setHyzk(hyxxObject.get("hyzt").toString());
                bdcSlSqrDO.setSqrid(getJtcyxxQO.getSqrid());
                bdcSlSqrService.updateBdcSlSqr(bdcSlSqrDO);
            }
            if(Objects.nonNull(hyxxObject.get("jtcy") )){
                BdcSlJtcyDO poJtcy = JSONObject.parseObject(JSONObject.toJSONString(hyxxObject.get("jtcy")),BdcSlJtcyDO.class);
                if (Objects.nonNull(poJtcy) && StringUtils.isNotBlank(poJtcy.getJtcymc()) && StringUtils.isNotBlank(poJtcy.getZjh())) {
                    poJtcy.setYsqrgx("配偶");
                    return poJtcy;
                }
            }
        }
        return null;
    }

    @Override
    public CompareHyxxResultDTO compareHyznxx(CompareHyxxQO compareHyxxQO) {

        //获取验证提示信息
        //婚姻信息比对
        CompareHyxxResultDTO resultDTO = compareMzhyxx(compareHyxxQO);
        //户籍子女信息比对
        compareHjznxx(compareHyxxQO,resultDTO);
        //更新比对结果
        BdcSlSqrDO updateParam =new BdcSlSqrDO();
        updateParam.setSqrid(compareHyxxQO.getSqrid());
        if(StringUtils.equals(MSG_BDSUCCESS,resultDTO.getMsg())){
            updateParam.setHyxxbdjg(CommonConstantUtils.SF_S_DM);
            //更新配偶婚姻比对结果
            updatePoHybdxx(resultDTO);
        }else{
            updateParam.setHyxxbdjg(CommonConstantUtils.SF_F_DM);
        }
        bdcSlSqrService.updateBdcSlSqr(updateParam);
        return resultDTO;
    }


    /**
     * 获取家庭成员信息接口（南通）
     *
     * @param sqrmc 申请人名称
     * @param zjh   证件号
     * @param sqrid 申请人ID
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return: void 无返回值
     */
    @Override
    public void getJtcyxxNt(String sqrmc, String zjh, String sqrid) {
        //删除原有家庭成员
        deleteBdcSlJtcyBySqrid(sqrid);
        // 拼装需要进行公安接口查询的人员证件号
        List<String> zjhList = new ArrayList<>(Arrays.asList(new String[]{zjh}));
        GetJtcyxxQO getJtcyxxQO = new GetJtcyxxQO(sqrmc, zjh, sqrid);
        BdcSlJtcyDO po = this.requestHyxx(getJtcyxxQO,true);
        if(Objects.nonNull(po)){
            zjhList.add(po.getZjh());
            po.setSqrid(sqrid);
            insertBdcSlJtcy(po);
        }
        if(getGaJtcyxx) {
            // 先调用家庭成员申请接口，在通过多线程等待60s,在进行反馈接口的调用。完成后，更新家庭成员信息
            this.addJtcyxx(zjhList, sqrid);
        }
    }

    /**
     * 调用南通公安婚姻接口查询当前申请人的家庭成员成员信息，并更新至不动产受理家庭成员表中
     */
    private void addJtcyxx(List<String> zjhList,String sqrid){
        List<String> successApplyZjhList = new ArrayList<>(2);
        for(String zjh : zjhList){
            // 调用家庭成员信息申请接口
            Object applyResp= this.getGaxx(zjh,APPLY_BEAN_NAME);
            if(null != applyResp){
                LOGGER.info("证件号:{},调取南通家庭成员申请接口成功,返回结果：{}", zjh, applyResp);
                JSONObject jsonObject = JSONObject.parseObject((String) applyResp);
                if(SUCCESS_CODE.equals(jsonObject.getString("code"))){
                    successApplyZjhList.add(zjh);
                }
            }
        }
        // 开启线程等待1分钟后，调用家庭成员反馈接口
        if(CollectionUtils.isNotEmpty(successApplyZjhList)){
            List<BdcSlJtcyFeedBackThread> jtcyThreads = new ArrayList<>(1);
            jtcyThreads.add(new BdcSlJtcyFeedBackThread(sqrid, successApplyZjhList, this));
            threadEngine.excuteThread(jtcyThreads,false);
        }
    }

    /**
     * @param zjh 证件号
     * @param beanName
     * @return 家庭成员信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 南通公安接口调用
     */
    @Override
    public Object getGaxx(String zjh, String beanName) {
        if(StringUtils.isAnyBlank(zjh,beanName)){
            throw new MissingArgumentException("查询参数缺失！");
        }
        Map param=new HashMap();
        param.put("zjh",zjh);
        return exchangeInterfaceFeignService.requestInterface(beanName,param);
    }

    /**
     * 批量获取申请人家庭信息，通过XMID获取当前表单的所有申请人
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: xmid 项目ID
     * @param: qlrlb 权利人类别
     * @return: void 无返回值
     */
    @Override
    public void getJtcyxxByXmidNt(String xmid, String qlrlb) {
        if(StringUtils.isNotBlank(xmid)){
            List<BdcSlSqrDO> sqrList = this.bdcSlSqrService.listBdcSlSqrByXmid(xmid, qlrlb);
            for(BdcSlSqrDO sqr : sqrList){
                this.getJtcyxxNt(sqr.getSqrmc(), sqr.getZjh(), sqr.getSqrid());
            }
        }
    }

    /**
     * 同步家庭成员至其他户
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcSlSqrDTO 不动产受理申请人DTO
     * @param gzlslid 工作流实例ID
     */
    @Override
    public void syncJtcyxx(BdcSlSqrDTO bdcSlSqrDTO, String gzlslid) {
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
        if(CollectionUtils.isNotEmpty(bdcSlXmDOList)){
            for(BdcSlXmDO bdcSlXmDO : bdcSlXmDOList){
                // 排除当前申请人
                String currentXmid = bdcSlSqrDTO.getBdcSlSqrDO().getXmid();
                if(StringUtils.isBlank(currentXmid)){
                    BdcSlSqrDO bdcSlSqrDO =bdcSlSqrService.queryBdcSlSqrBySqrid(bdcSlSqrDTO.getBdcSlSqrDO().getSqrid());
                    if(bdcSlSqrDO != null) {
                        currentXmid = bdcSlSqrDO.getXmid();
                    }
                }
                if(StringUtils.equals(bdcSlXmDO.getXmid(), currentXmid)){
                    continue;
                }
                // 查询需要同步家庭成员的项目申请人信息
                BdcSlSqrQO bdcSlSqrQO = new BdcSlSqrQO();
                bdcSlSqrQO.setXmid(bdcSlXmDO.getXmid());
                List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrService.queryBdcSlSqrBySqrQO(bdcSlSqrQO);
                if(CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                    for (BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
                        //当前申请人证件号与同步人员的证件号一致时，同步家庭成员信息
                        if (StringUtils.equals(bdcSlSqrDTO.getBdcSlSqrDO().getZjh(), bdcSlSqrDO.getZjh())) {
                            // 获取需要同步申请人的家庭成员信息
                            String needSyncSqrId = bdcSlSqrDO.getSqrid();
                            List<BdcSlJtcyDO> needSyncJtcyList = this.listBdcSlJtcyBySqrid(needSyncSqrId);
                            // 需同步的申请人不存在家庭成员时，才进行家庭人员信息同步新增
                            if (CollectionUtils.isEmpty(needSyncJtcyList)) {
                                for (BdcSlJtcyDO bdcSlJtcyDO : bdcSlSqrDTO.getBdcSlJtcyDOList()) {
                                    bdcSlJtcyDO.setJtcyid(UUIDGenerator.generate16());
                                    bdcSlJtcyDO.setSqrid(needSyncSqrId);
                                    this.insertBdcSlJtcy(bdcSlJtcyDO);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void saveBdcSlJtcyList(List<BdcSlJtcyDO> bdcSlJtcyDOList){
        if (CollectionUtils.isEmpty(bdcSlJtcyDOList)) {
            LOGGER.info("缺失家庭成员信息");
            return;
        }
        entityMapper.batchSaveSelective(bdcSlJtcyDOList);

    }

    /**
     * @param getJtcyxxQO
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  处理户籍接口家庭成员信息
     */
    private List<BdcSlJtcyDO> dealHjJtcyxx(GetJtcyxxQO getJtcyxxQO){
        List<BdcSlJtcyDO> bdcSlJtcyDOS =new ArrayList<>();
        getJtcyxxQO.setBeanName("acceptJtcyxx");

        Object response =getHygaxx(getJtcyxxQO);

        if(response != null){
            LOGGER.info("申请人名称:{},调取户籍接口成功,返回结果：{}", getJtcyxxQO.getQlrmc(), response);
            bdcSlJtcyDOS=JSONObject.parseArray(JSONObject.toJSONString(response),BdcSlJtcyDO.class);
            if(CollectionUtils.isEmpty(bdcSlJtcyDOS)) {
                return new ArrayList<>();
            }
        }
        return bdcSlJtcyDOS;

    }

    @Override
    public Map<String,String> compareHyxx(GetJtcyxxQO getJtcyxxQO){
       if(StringUtils.isBlank(getJtcyxxQO.getQlrmc()) ||StringUtils.isBlank(getJtcyxxQO.getQlrzjh()) &&StringUtils.isBlank(getJtcyxxQO.getSqrid())){
           throw new MissingArgumentException("查询婚姻接口缺失申请人名称或证件号");
       }
        //获取验证提示信息
       Map<String,String> resultMap =getcompareHyxxMsg(getJtcyxxQO);
        //更新比对结果
       BdcSlSqrDO bdcSlSqrDO =new BdcSlSqrDO();
       bdcSlSqrDO.setSqrid(getJtcyxxQO.getSqrid());
       if(StringUtils.equals(MSG_BDSUCCESS,MapUtils.getString(resultMap,"msg"))){
           bdcSlSqrDO.setHyxxbdjg(CommonConstantUtils.SF_S_DM);
       }else{
           bdcSlSqrDO.setHyxxbdjg(CommonConstantUtils.SF_F_DM);
       }
       bdcSlSqrService.updateBdcSlSqr(bdcSlSqrDO);
       return resultMap;

    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param compareHyxxQO
     * @return
     * @description 民政婚姻信息比对
     */
    private CompareHyxxResultDTO compareMzhyxx(CompareHyxxQO compareHyxxQO){
        BdcSlSqrDO bdcSlSqrDO = bdcSlSqrService.queryBdcSlSqrBySqrid(compareHyxxQO.getSqrid());
        if (Objects.isNull(bdcSlSqrDO)) {
            throw new AppException("申请人对象为空！");
        }
        CompareHyxxResultDTO resultDTO  = new CompareHyxxResultDTO();
        CompareHyxxResultMzhyxxDTO mzhyxxDTO =new CompareHyxxResultMzhyxxDTO();
        //申报婚姻信息
        CompareHyxxResultSbhyxxDTO sbhyxxDTO = new CompareHyxxResultSbhyxxDTO();
        sbhyxxDTO.setSbhyzt(compareHyxxQO.getHyzt());
        //查询申报配偶信息
        BdcSlJtcyQO bdcSlJtcyQO =new BdcSlJtcyQO();
        bdcSlJtcyQO.setYsqrgx(CommonConstantUtils.YSQRGX_PO_MC);
        bdcSlJtcyQO.setSqrid(compareHyxxQO.getSqrid());
        List<BdcSlJtcyDO> poJtcyList =listBdcSlJtcy(bdcSlJtcyQO);
        if(CollectionUtils.isNotEmpty(poJtcyList)){
            sbhyxxDTO.setPoxm(poJtcyList.get(0).getJtcymc());
            sbhyxxDTO.setPozjh(poJtcyList.get(0).getZjh());
            sbhyxxDTO.setPozjzl(poJtcyList.get(0).getZjzl() != null ?poJtcyList.get(0).getZjzl().toString():"");
        }
        resultDTO.setSbhyxxDTO(sbhyxxDTO);

        /**
         * 申请人信息
         */
        CompareResultSqrDTO sqrDTO = new CompareResultSqrDTO();
        sqrDTO.setSqrid(bdcSlSqrDO.getSqrid());
        sqrDTO.setSqrzjzl(Objects.nonNull(bdcSlSqrDO.getZjzl()) ? String.valueOf(bdcSlSqrDO.getZjzl()) : "");
        sqrDTO.setSqrzjh(bdcSlSqrDO.getZjh());
        sqrDTO.setSqrmc(bdcSlSqrDO.getSqrmc());
        sqrDTO.setXmid(bdcSlSqrDO.getXmid());
        resultDTO.setSqrDTO(sqrDTO);

        /**
         * 查询民政婚姻信息
         */
        GetJtcyxxQO getJtcyxxQO = new GetJtcyxxQO();
        getJtcyxxQO.setQlrmc(compareHyxxQO.getSqrxm());
        getJtcyxxQO.setSqrid(compareHyxxQO.getSqrid());
        getJtcyxxQO.setQlrzjh(compareHyxxQO.getSqrzjh());
        getJtcyxxQO.setBeanName("acceptHyxx");
        Object response = getHygaxx(getJtcyxxQO);
//       {jtcy={gxbs=null, jtcyid=, jtcymc=陈艳军, sqrid=, ysqrgx=, zjh=320623198605130018, zjzl=null}, hyzt=已婚}
        if(Objects.isNull(response)){
            resultDTO.setCode(COMPARE_CODE_CONFIRM);
            resultDTO.setMsg("未查询到有效的婚姻信息,是否通过？");
            return resultDTO;
        }
        String result = JSONObject.toJSONString(response);
//        String result = "{\n" +
//                "    \"jtcy\": {\n" +
//                "        \"gxbs\": null,\n" +
//                "        \"jtcyid\": \"\",\n" +
//                "        \"jtcymc\": \"陈艳军\",\n" +
//                "        \"sqrid\": \"\",\n" +
//                "        \"ysqrgx\": \"\",\n" +
//                "        \"zjh\": \"320623198605130018\",\n" +
//                "        \"zjzl\": \"1\"\n" +
//                "    },\n" +
//                "    \"hyzt\": \"已婚\"\n" +
//                "}";
        JSONObject hyxx = JSONObject.parseObject(result);
        LOGGER.info("申请人名称:{},调取婚姻接口成功,返回结果：{}", getJtcyxxQO.getQlrmc(), result);

        if(hyxx.get("hyzt") != null) {
            String hyzt = hyxx.get("hyzt").toString();
            mzhyxxDTO.setHyzt(hyzt);
        }
        //接口配偶信息
        BdcSlJtcyDO poJtcy =null;
        if(hyxx.get("jtcy") != null) {
            poJtcy = JSONObject.parseObject(JSONObject.toJSONString(hyxx.get("jtcy")), BdcSlJtcyDO.class);
            if(poJtcy != null) {
                mzhyxxDTO.setHydjjg(null);
                mzhyxxDTO.setDjrq(null);
                mzhyxxDTO.setPoxm(poJtcy.getJtcymc());
                mzhyxxDTO.setPozjh(poJtcy.getZjh());
                mzhyxxDTO.setPozjzl(Objects.nonNull(poJtcy.getZjzl()) ? String.valueOf(poJtcy.getZjzl()) : "");
            }
        }
        resultDTO.setMzhyxxDTO(mzhyxxDTO);

        //处理比对结果
        dealHybdxx(resultDTO);
        return resultDTO;
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 户籍子女信息比对
      */
    private void compareHjznxx(CompareHyxxQO compareHyxxQO,CompareHyxxResultDTO resultDTO){
        if(StringUtils.isNotBlank(compareHyxxQO.getSqrid()) &&resultDTO.getMzhyxxDTO() != null &&CommonConstantUtils.HYZK_YH_MC.equals(resultDTO.getMzhyxxDTO().getHyzt())) {
            //查询申报子女信息
            BdcSlJtcyQO bdcSlJtcyQO = new BdcSlJtcyQO();
            bdcSlJtcyQO.setYsqrgx(CommonConstantUtils.YSQRGX_WCNZN_MC);
            bdcSlJtcyQO.setSqrid(compareHyxxQO.getSqrid());
            List<BdcSlJtcyDO> znJtcyList = listBdcSlJtcy(bdcSlJtcyQO);
            if(CollectionUtils.isNotEmpty(znJtcyList) &&resultDTO.getSbhyxxDTO() != null){
                znJtcyList = znJtcyList.stream().filter(zn -> StringUtils.isNotBlank(zn.getJtcymc())).collect(Collectors.toList());
                znJtcyList.sort(Comparator.comparing(BdcSlJtcyDO::getJtcymc));
                resultDTO.getSbhyxxDTO().setZnJtcyList(znJtcyList);
            }
            GetJtcyxxQO getJtcyxxQO = new GetJtcyxxQO();
            List<BdcSlJtcyDO> hjznJtcyList =new ArrayList<>();
            /**
             * 查询夫妻双方公安户籍信息
             */
            getJtcyxxQO.setQlrmc(compareHyxxQO.getSqrxm());
            getJtcyxxQO.setQlrzjh(compareHyxxQO.getSqrzjh());
            hjznJtcyList.addAll(dealHjJtcyxx(getJtcyxxQO));
            if(StringUtils.isNotBlank(resultDTO.getMzhyxxDTO().getPoxm())){
                GetJtcyxxQO jtcyxxQO =new GetJtcyxxQO();
                jtcyxxQO.setQlrmc(resultDTO.getMzhyxxDTO().getPoxm());
                jtcyxxQO.setQlrzjh(resultDTO.getMzhyxxDTO().getPozjh());
                hjznJtcyList.addAll(dealHjJtcyxx(jtcyxxQO));
            }
            if(CollectionUtils.isNotEmpty(hjznJtcyList)) {
                //根据证件号去重
                hjznJtcyList = hjznJtcyList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getZjh()))), ArrayList::new));
            }
            if(CollectionUtils.isNotEmpty(hjznJtcyList)){
                hjznJtcyList = hjznJtcyList.stream().filter(hjzn -> StringUtils.isNotBlank(hjzn.getJtcymc())).collect(Collectors.toList());
                hjznJtcyList.sort(Comparator.comparing(BdcSlJtcyDO::getJtcymc));
                resultDTO.getMzhyxxDTO().setZnJtcyList(hjznJtcyList);

            }
            if(StringUtils.equals(MSG_BDSUCCESS,resultDTO.getMsg())){
                //婚姻比对成功则比对子女,否则无需比对
                if(CollectionUtils.isNotEmpty(znJtcyList) ||CollectionUtils.isNotEmpty(hjznJtcyList)){
                    //申报有,接口查询没有,比对通过
                    if(CollectionUtils.isEmpty(hjznJtcyList)){
                        resultDTO.setCode(COMPARE_CODE_SUCCESS);
                        resultDTO.setMsg(MSG_BDSUCCESS);
                        return;
                    }
                    //查询大于申报,比对不通过
                    if(CollectionUtils.isEmpty(hjznJtcyList) ||hjznJtcyList.size() >znJtcyList.size()){
                        resultDTO.setCode(COMPARE_CODE_CONFIRM);
                        resultDTO.setMsg("申报未成年子女数量少于查询数量");
                        return;
                    }
                    //循环查询结果进行比对
                    Map<String,String> mcAndZjh =new HashMap<>(hjznJtcyList.size());
                    for(BdcSlJtcyDO bdcSlJtcyDO:hjznJtcyList){
                        if(StringUtils.isNotBlank(bdcSlJtcyDO.getJtcymc()) &&StringUtils.isNotBlank(bdcSlJtcyDO.getZjh())) {
                            mcAndZjh.put(bdcSlJtcyDO.getJtcymc(), bdcSlJtcyDO.getZjh());
                        }
                    }
                    int successSize =0;
                    for(BdcSlJtcyDO bdcSlJtcyDO:znJtcyList){
                        List<String> znZjhList =new ArrayList<>();
                        if (StringUtils.isNotBlank(mcAndZjh.get(bdcSlJtcyDO.getJtcymc()))) {
                            //15位 18位转换
                            znZjhList =Arrays.asList(CardNumberTransformation.zjhTransformation(mcAndZjh.get(bdcSlJtcyDO.getJtcymc())).split(CommonConstantUtils.ZF_YW_DH));
                        }
                        if(StringUtils.isNotBlank(bdcSlJtcyDO.getJtcymc()) &&mcAndZjh.get(bdcSlJtcyDO.getJtcymc()) != null &&CollectionUtils.isNotEmpty(znZjhList) &&StringUtils.isNotBlank(bdcSlJtcyDO.getZjh()) &&znZjhList.contains(StringUtils.upperCase(bdcSlJtcyDO.getZjh()))){
                            successSize++;
                        }
                    }
                    if(hjznJtcyList.size() ==successSize){
                        resultDTO.setCode(COMPARE_CODE_SUCCESS);
                        resultDTO.setMsg(MSG_BDSUCCESS);
                        return;

                    }
                    resultDTO.setCode(COMPARE_CODE_CONFIRM);
                    resultDTO.setMsg("未成年子女比对失败");
                }
            }

        }


    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 处理婚姻比对信息
      */
    private void dealHybdxx(CompareHyxxResultDTO resultDTO){
        //申报婚姻信息
        CompareHyxxResultSbhyxxDTO sbhyxxDTO =resultDTO.getSbhyxxDTO();
        //接口返回婚姻信息
        CompareHyxxResultMzhyxxDTO mzhyxxDTO =resultDTO.getMzhyxxDTO();
        if(sbhyxxDTO != null &&mzhyxxDTO != null){
            //检查申报婚姻状况
            if(StringUtils.isBlank(sbhyxxDTO.getSbhyzt())){
                resultDTO.setCode(COMPARE_CODE_CONFIRM);
                resultDTO.setMsg("未填写婚姻状况!");
                return;
            }
            //接口返回非已婚,无论申报已婚未婚都比对通过
            if(!CommonConstantUtils.HYZK_YH_MC.equals(mzhyxxDTO.getHyzt())){
                resultDTO.setCode(COMPARE_CODE_SUCCESS);
                resultDTO.setMsg(MSG_BDSUCCESS);
                return;
            }
            //比对婚姻状态
            if (!StringUtils.equals(sbhyxxDTO.getSbhyzt(),mzhyxxDTO.getHyzt())) {
                resultDTO.setCode(COMPARE_CODE_CONFIRM);
                resultDTO.setMsg("婚姻状况比对失败!");
                return;
            }
            //接口已婚,检查申报配偶信息
            if(StringUtils.isBlank(sbhyxxDTO.getPoxm()) ||StringUtils.isBlank(sbhyxxDTO.getPozjh())){
                resultDTO.setCode(COMPARE_CODE_CONFIRM);
                resultDTO.setMsg("请填写配偶信息！");
                return;
            }
            //接口已婚,检查接口配偶信息
            if(StringUtils.isBlank(mzhyxxDTO.getPoxm()) ||StringUtils.isBlank(mzhyxxDTO.getPozjh())){
                resultDTO.setCode(COMPARE_CODE_CONFIRM);
                resultDTO.setMsg("接口返回信息异常,未返回配偶信息！");
                return;
            }
            //检查配偶证件号是否一致
            List<String> mzPoZjhList =new ArrayList<>();
            if (StringUtils.isNotBlank(mzhyxxDTO.getPozjh())) {
                //15位 18位转换
                mzPoZjhList =Arrays.asList(CardNumberTransformation.zjhTransformation(mzhyxxDTO.getPozjh()).split(CommonConstantUtils.ZF_YW_DH));
            }
            if(CollectionUtils.isEmpty(mzPoZjhList) || !mzPoZjhList.contains(StringUtils.upperCase(sbhyxxDTO.getPozjh()))){
                resultDTO.setCode(COMPARE_CODE_CONFIRM);
                resultDTO.setMsg("配偶证件号比对异常!");
                return;
            }else if(!StringUtils.equalsIgnoreCase(mzhyxxDTO.getPoxm(),sbhyxxDTO.getPoxm())){
                resultDTO.setCode(COMPARE_CODE_CONFIRM);
                resultDTO.setMsg("配偶姓名比对异常!");
                return;
            }
            resultDTO.setCode(COMPARE_CODE_SUCCESS);
            resultDTO.setMsg(MSG_BDSUCCESS);
        }

    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 如果买卖双方存在夫妻,根据一方比对通过同步更新另一方婚姻比对结果
      */
    private void updatePoHybdxx(CompareHyxxResultDTO resultDTO){
        if(resultDTO.getMzhyxxDTO() != null &&resultDTO.getSqrDTO() != null &&StringUtils.equals(CommonConstantUtils.HYZK_YH_MC,resultDTO.getMzhyxxDTO().getHyzt()) &&StringUtils.isNoneBlank(resultDTO.getMzhyxxDTO().getPozjh(),resultDTO.getSqrDTO().getXmid())) {
            //判断配偶是否在买卖双方中
            BdcSlSqrQO bdcSlSqrQO = new BdcSlSqrQO();
            bdcSlSqrQO.setZjh(resultDTO.getMzhyxxDTO().getPozjh());
            bdcSlSqrQO.setXmid(resultDTO.getSqrDTO().getXmid());
            List<BdcSlSqrDO> bdcSlSqrDOS = bdcSlSqrService.queryBdcSlSqrBySqrQO(bdcSlSqrQO);
            if(CollectionUtils.isNotEmpty(bdcSlSqrDOS)){
                for(BdcSlSqrDO sqrDO:bdcSlSqrDOS){
                    if(!CommonConstantUtils.SF_S_DM.equals(sqrDO.getHyxxbdjg())) {
                        //更新对应的配偶和子女信息
                        resultDTO.getMzhyxxDTO().setSqrid(sqrDO.getSqrid());
                        resultDTO.getMzhyxxDTO().setPoxm(resultDTO.getSqrDTO().getSqrmc());
                        resultDTO.getMzhyxxDTO().setPozjh(resultDTO.getSqrDTO().getSqrzjh());
                        resultDTO.getMzhyxxDTO().setPozjzl(resultDTO.getSqrDTO().getSqrzjzl());
                        updateHyJtcyxx(resultDTO.getMzhyxxDTO());

                    }
                }
            }
        }

    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 更新婚姻信息家庭成员信息
      */
    private void updateHyJtcyxx(CompareHyxxResultMzhyxxDTO mzhyxxDTO){
        if(StringUtils.isBlank(mzhyxxDTO.getSqrid())){
            throw new AppException("缺失申请人ID");
        }
        //更新婚姻状况和比对结果
        BdcSlSqrDO bdcSlSqrDO =new BdcSlSqrDO();
        bdcSlSqrDO.setHyzk(mzhyxxDTO.getHyzt());
        bdcSlSqrDO.setHyxxbdjg(CommonConstantUtils.SF_S_DM);
        bdcSlSqrDO.setSqrid(mzhyxxDTO.getSqrid());
        bdcSlSqrService.updateBdcSlSqr(bdcSlSqrDO);
        //更新家庭成员
        deleteBdcSlJtcyBySqrid(mzhyxxDTO.getSqrid());
        List<BdcSlJtcyDO> bdcSlJtcyDOS =new ArrayList<>();
        List<BdcSlJtcyDO> znJtcyList =mzhyxxDTO.getZnJtcyList();

        //组织配偶信息
        BdcSlJtcyDO poJtcy =new BdcSlJtcyDO();
        if(StringUtils.isNotBlank(mzhyxxDTO.getPoxm())) {
            poJtcy.setJtcymc(mzhyxxDTO.getPoxm());
            if (StringUtils.isNotBlank(mzhyxxDTO.getPozjzl())) {
                poJtcy.setZjzl(Integer.parseInt(mzhyxxDTO.getPozjzl()));
            }
            poJtcy.setZjh(mzhyxxDTO.getPozjh());
            poJtcy.setYsqrgx(CommonConstantUtils.YSQRGX_PO_MC);
            bdcSlJtcyDOS.add(poJtcy);
        }
        //子女信息
        if(CollectionUtils.isNotEmpty(znJtcyList)){
            for(BdcSlJtcyDO znJtcy:znJtcyList){
                if(StringUtils.isNotBlank(znJtcy.getJtcymc())){
                    bdcSlJtcyDOS.add(znJtcy);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(bdcSlJtcyDOS)){
            for(BdcSlJtcyDO bdcSlJtcyDO:bdcSlJtcyDOS){
                bdcSlJtcyDO.setJtcyid(UUIDGenerator.generate16());
                bdcSlJtcyDO.setSqrid(mzhyxxDTO.getSqrid());
            }
            entityMapper.insertBatchSelective(bdcSlJtcyDOS);
        }
    }

    /**
     * @param getJtcyxxQO 家庭成员查询参数
     * @return 提示信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 民政婚姻信息比对
     */
    private Map<String,String> getcompareHyxxMsg(GetJtcyxxQO getJtcyxxQO){
        Map<String,String> resultMap = new HashMap<>();
        String yzmsg ="比对失败";
        String code =COMPARE_CODE_ALERT;
        if(StringUtils.isBlank(getJtcyxxQO.getQlrmc()) ||StringUtils.isBlank(getJtcyxxQO.getQlrzjh()) &&StringUtils.isBlank(getJtcyxxQO.getSqrid())){
            throw new MissingArgumentException("查询婚姻接口缺失申请人名称或证件号");
        }
        getJtcyxxQO.setBeanName("acceptHyxx");
        Object response =getHygaxx(getJtcyxxQO);
        if(response ==null){
            yzmsg ="未查询到有效的婚姻信息,是否通过？";
            code =COMPARE_CODE_CONFIRM;
        }else {
            LOGGER.info("申请人名称:{},调取婚姻接口成功,返回结果：{}", getJtcyxxQO.getQlrmc(), response);
            JSONObject hyxxObject = JSONObject.parseObject(JSONObject.toJSONString(response));

            if (hyxxObject.get("hyzt") == null) {
                yzmsg = "接口返回信息异常,未返回婚姻状态";
            }else {
                String hyzk = hyxxObject.get("hyzt").toString();

                // 不等于已婚，视为未婚，不是说明未婚，只是没有数据而已，出弹窗让用户自己通过或者不通过
                if(! CommonConstantUtils.HYZK_YH_MC.equals(hyzk)){
                    yzmsg ="未查询到有效的婚姻信息,是否通过？";
                    code =COMPARE_CODE_CONFIRM;
                    resultMap.put("code",code);
                    resultMap.put("msg",yzmsg);
                    return resultMap;
                }

                BdcSlSqrDO bdcSlSqrDO = bdcSlSqrService.queryBdcSlSqrBySqrid(getJtcyxxQO.getSqrid());
                if (bdcSlSqrDO == null) {
                    throw new AppException("申请人对象为空！");
                }

                //首先比对婚姻状况是否正确
                if (!StringUtils.equals(hyzk, bdcSlSqrDO.getHyzk())) {
                    yzmsg = "婚姻状况比对失败！";
                }else {
                        //已婚需要比对配偶信息
                        //查询配偶信息
                        BdcSlJtcyQO bdcSlJtcyQO = new BdcSlJtcyQO();
                        bdcSlJtcyQO.setSqrid(bdcSlSqrDO.getSqrid());
                        bdcSlJtcyQO.setYsqrgx("配偶");
                        List<BdcSlJtcyDO> bdcSlJtcyDOList = listBdcSlJtcy(bdcSlJtcyQO);
                        if (CollectionUtils.isEmpty(bdcSlJtcyDOList)) {
                            yzmsg = "请填写配偶信息！";
                        }else {
                            if (hyxxObject.get("jtcy") == null) {
                                yzmsg = "接口返回信息异常,未返回配偶信息";
                            }else {

                                BdcSlJtcyDO poJtcy = JSONObject.parseObject(JSONObject.toJSONString(hyxxObject.get("jtcy")), BdcSlJtcyDO.class);
                                if (poJtcy != null) {
                                    List<String> pozjhList =new ArrayList<>();
                                    if (StringUtils.isNotBlank(poJtcy.getZjh())) {
                                        //15位 18位转换
                                        pozjhList =Arrays.asList(CardNumberTransformation.zjhTransformation(poJtcy.getZjh()).split(CommonConstantUtils.ZF_YW_DH));

                                    }
                                    if (CollectionUtils.isNotEmpty(pozjhList) &&StringUtils.isNotBlank(bdcSlJtcyDOList.get(0).getZjh()) && pozjhList.contains(StringUtils.upperCase(bdcSlJtcyDOList.get(0).getZjh())) && StringUtils.isNotBlank(poJtcy.getJtcymc()) && StringUtils.equals(poJtcy.getJtcymc(), bdcSlJtcyDOList.get(0).getJtcymc())) {
                                        yzmsg = MSG_BDSUCCESS;
                                        code = COMPARE_CODE_SUCCESS;
                                    }
                                }
                            }
                        }
                }
            }
        }
        resultMap.put("code",code);
        resultMap.put("msg",yzmsg);
        return resultMap;


    }

    @Override
    public void batchDelete(BdcSlDeleteCsDTO bdcSlDeleteCsDTO){
        if (!CheckParameter.checkAnyParameter(bdcSlDeleteCsDTO)) {
            throw new AppException("删除参数为空" + JSONObject.toJSONString(bdcSlDeleteCsDTO));
        }
        bdcSlJtcyMapper.delBatchJtcyxx(Object2MapUtils.object2MapExceptNull(bdcSlDeleteCsDTO));
    }


    @Override
    public void drhybdxx(CompareHyxxResultDTO resultDTO){
        //更新申请人对应对比信息
        updateHyJtcyxx(resultDTO.getMzhyxxDTO());
        //更新配偶方对应信息
        updatePoHybdxx(resultDTO);

    }

    /**
     * @param sply 审批来源
     * @return true:是互联网推送过来的流程
     *         false:不是互联网推送过来的流程
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 判断是否为互联网推送过来的流程
     */
    public boolean sfycsl(Integer sply){
        if (sply != null){
            return ArrayUtils.contains(HLW_TS_YCSL,sply);
        }
        return false;
    }
}
