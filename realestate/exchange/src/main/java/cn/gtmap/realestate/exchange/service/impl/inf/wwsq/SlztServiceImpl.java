package cn.gtmap.realestate.exchange.service.impl.inf.wwsq;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtJgDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.AutoForwardTaskDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcDsfSfxxDTO;
import cn.gtmap.realestate.common.core.enums.HlwShztEnum;
import cn.gtmap.realestate.common.core.enums.HlwSlztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsxmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXtJgFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.matcher.FlowableNodeClientMatcher;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CheckWwsqOrYcslUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.nantong.jsyh.slfk.reject.JsyhReqDataDto;
import cn.gtmap.realestate.exchange.core.dto.nantong.jsyh.slfk.reject.OthrData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.jfxx.TsjfxxRequestBody;
import cn.gtmap.realestate.exchange.core.dto.wwsq.updateslzt.UpdateSlztRequestBody;
import cn.gtmap.realestate.exchange.core.dto.wwsq.updateslzt.UpdateSlztRequestData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.wwsqdeltask.request.WwsqdeltaskRequestDTO;
import cn.gtmap.realestate.exchange.core.enums.CCbSlztEnum;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.wwsq.GxWwSqxxService;
import cn.gtmap.realestate.exchange.service.inf.wwsq.SendMsgService;
import cn.gtmap.realestate.exchange.service.inf.wwsq.SlztService;
import cn.gtmap.realestate.exchange.util.XmlUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/10/16
 * @description 受理状态相关服务
 */
@Service
public class SlztServiceImpl implements SlztService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlztServiceImpl.class);
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private FlowableNodeClientMatcher flowableNodeClient;
    @Autowired
    private SendMsgService sendMsgService;
    @Autowired
    private BdcSlFeignService bdcSlFeignService;
    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;
    @Autowired
    private BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    BdcXtJgFeignService bdcXtJgFeignService;

    @Autowired
    BdcZsFeignService zsFeignService;

    @Autowired
    BdcZsxmFeignService zsxmFeignService;
    @Autowired
    GxWwSqxxService gxWwSqxxService;
    @Autowired
    BdcQlrFeignService qlrFeignService;
    @Value("${updateSlzt.hlw:}")
    private String hlwdzMapString;
    @Value("${turn.jdmc:}")
    private String turnJdmc;

    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Value("#{${yhtsxx.beanId:{'NTCCB': 'jsyh_slxx'}}}")
    private Map<String, String> yhtsBeanid;

    //常州不需要缴费状态的工作流
    @Value("#{'${wwsq.updateslzt.bxyjfzt.gzldyid:}'.split(',')}")
    private List<String> bxyjfzt;

    @Autowired
    BdcXmMapper bdcXmMapper;


    @Override
    public void updateSlzt(Map map) {
        UpdateSlztRequestBody updateSlztRequestBody = new UpdateSlztRequestBody();
        UpdateSlztRequestData updateSlztRequestData = new UpdateSlztRequestData();
        String gzlslid = MapUtils.getString(map, "processInsId");
        String isDelete = MapUtils.getString(map, "isDelete");
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("工作流实例id为空");
        }
        LOGGER.info(JSON.toJSONString(map));

        BdcXmDO temp = queryBdcxmDoByGzlslid(gzlslid);
        LOGGER.info("推送互联网状态查到的项目信息：{}", temp.toString());
        // 判断为的 业务才推送
        if (temp != null && (CheckWwsqOrYcslUtils.checkIsWwsq(temp.getSply()) || CheckWwsqOrYcslUtils.checkIsYhxt(temp.getSply()))) {
            updateSlztRequestData.setSlbh(temp.getSpxtywh());
            // 当是删除时 推送短信
            if (StringUtils.equals(isDelete, "1")) {
                sendMsgService.sendMessage(map, "wwsqmsg");
            }
            orgnizeUpdateSlztBody(updateSlztRequestBody, updateSlztRequestData, map);
            LOGGER.info(JSON.toJSONString(updateSlztRequestBody));
            LOGGER.info(JSON.toJSONString(temp));
//            Map<String, String> hlwMap = JSON.parseObject(hlwdzMapString, Map.class);
            if (CheckWwsqOrYcslUtils.checkIsWwsq(temp.getSply())) {
                LOGGER.info("开始推送互联网：{}", temp.toString());
                exchangeBeanRequestService.request("wwsqupdateslzt_hlw", updateSlztRequestBody);
            }

        } else if (CheckWwsqOrYcslUtils.checkIsYhxt(temp.getSply())) {
            LOGGER.info("开始推送银行系统：{}", temp.toString());
            exchangeBeanRequestService.request("wwsqupdateslzt_yhxt", updateSlztRequestBody);
        }


    }

    @Override
    public void updateSlztCz(Map map) {
        LOGGER.info("进入更新受理状态接口，map:{}", JSON.toJSONString(map));
        UpdateSlztRequestBody updateSlztRequestBody = new UpdateSlztRequestBody();
        UpdateSlztRequestData updateSlztRequestData = new UpdateSlztRequestData();
        String gzlslid = MapUtils.getString(map, "processInsId");
        String isDelete = MapUtils.getString(map, "isDelete");
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("工作流实例id为空");
        }
        BdcXmDO temp = queryBdcxmDoByGzlslid(gzlslid);
        if (null == temp || StringUtils.isBlank(temp.getSlbh())) {
            return;
        }
        LOGGER.info("进入更新受理状态接口，hlwslh:{}", temp.getSlbh());
        // 判断为的 业务才推送
        updateSlztRequestData.setSlbh(temp.getSlbh());
        // 当是删除时 推送短信
        if (StringUtils.equals(isDelete, "1")) {
            sendMsgService.sendMessage(map, "wwsqmsg");
        }
        orgnizeUpdateSlztBody(updateSlztRequestBody, updateSlztRequestData, map);
        //去掉缴费状态,现场确认部分流程不要缴费状态,只需要slzt
        if (bxyjfzt.contains(temp.getGzldyid()) && Objects.nonNull(updateSlztRequestBody.getData())) {
            updateSlztRequestBody.getData().setJfzt(null);
        }
        if (CheckWwsqOrYcslUtils.checkIsWwsq(temp.getSply())) {
            exchangeBeanRequestService.request("wwsqupdateslzt_hlw", updateSlztRequestBody);
        } else if (CheckWwsqOrYcslUtils.checkIsYhxt(temp.getSply())) {
            exchangeBeanRequestService.request("wwsqupdateslzt_yhxt", updateSlztRequestBody);
        }
    }

    @Override
    public void updateSlztNt(Map map) {
        LOGGER.info("进入更新受理状态接口，map:{}", JSON.toJSONString(map));
        UpdateSlztRequestBody updateSlztRequestBody = new UpdateSlztRequestBody();
        UpdateSlztRequestData updateSlztRequestData = new UpdateSlztRequestData();
        String gzlslid = MapUtils.getString(map, "processInsId");
        String isDelete = MapUtils.getString(map, "isDelete");
        String opinion = MapUtils.getString(map, "opinion");
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("工作流实例id为空");
        }
        // 审核不通过，触发推送南通建设银行事件,推送成功返回true
        Boolean isPushNtBBC = rejectEnvent(isDelete, opinion, gzlslid);
        BdcXmDO temp = queryBdcxmDoByGzlslid(gzlslid);
        if (null != temp) {
            LOGGER.info("进入更新受理状态接口，sply:{},审批号：{},受理编号：{}", temp.getSply(), temp.getSpxtywh(), temp.getSlbh());


            updateSlztRequestData.setSlbh(null != temp.getSlbh() ? temp.getSlbh() : temp.getSpxtywh());
            updateSlztRequestBody.setData(updateSlztRequestData);
            updateSlztRequestBody = orgnizeUpdateSlztBodyNt(updateSlztRequestBody, updateSlztRequestData, map);
            // 当是删除时 推送短信
            if (StringUtils.equals(isDelete, "1")) {
                sendMsgService.sendMessage(map, "wwsqmsg");
            }
            Map<String, String> hlwMap = JSON.parseObject(hlwdzMapString, Map.class);
            LOGGER.info("配置地址参数11：{}", hlwdzMapString);

            if (null != hlwMap) {
                LOGGER.info("配置地址参数：{}", hlwMap.toString());

            }
            // 假如推送南通建设银行成功，无需再推送互联网和其他银行，直接返回
            if (isPushNtBBC) {
                return;
            }
            if (Objects.nonNull(temp.getSply()) && (CheckWwsqOrYcslUtils.checkIsWwsqBypz(temp.getSply(), hlwMap))) {
                LOGGER.info("互联网审批来源为：{},受理编号为：{}", temp.getSply(), temp.getSlbh());
                String url = hlwMap.get(temp.getSply().toString());
                exchangeBeanRequestService.requestByUrl("wwsqupdateslzt_hlw", updateSlztRequestBody, url);
                LOGGER.info(hlwMap.toString());
            } else {
                LOGGER.info("YH,审批来源为：{},受理编号为：{}", temp.getSply(), temp.getSpxtywh());
                //南通要求不同shlbh控制，推送不同银行
                exchangeBeanRequestService.request("wwsqupdateslzt_yhxt", updateSlztRequestBody);
            }
        }
    /*boolean isYhxt = false;
        String hlwslh = bdcSlFeignService.getWwsqSlbhByDjGzlslid(gzlslid);
        if (StringUtils.isBlank(hlwslh)) {
            hlwslh = bdcSlFeignService.getYhxtSlbhByDjGzlslid(gzlslid);
            isYhxt = true;
        }*/
      /*  // 当是删除时 推送短信
        if (StringUtils.equals(isDelete, "1")) {
            sendMsgService.sendMessage(map, "wwsqmsg");
        }
        orgnizeUpdateSlztBody(updateSlztRequestBody, updateSlztRequestData, map);
        if (isYhxt) {
            LOGGER.info("YH,受理编号为：{}",hlwslh);
            exchangeBeanRequestService.request("wwsqupdateslzt_yhxt", updateSlztRequestBody);
        } else {
            Map<String, String> hlwMap = JSON.parseObject(hlwdzMapString, Map.class);
            BdcXmDO temp = queryBdcxmDoByGzlslid(gzlslid);
            if (Objects.nonNull(temp.getSply())) {
                LOGGER.info("审批来源为：{},受理编号为：{}",temp.getSply(),temp.getSlbh());
                String url = hlwMap.get(temp.getSply().toString());
                exchangeBeanRequestService.requestByUrl("wwsqupdateslzt_hlw", updateSlztRequestBody, url);
                LOGGER.info(hlwMap.toString());
            }
//            exchangeBeanRequestService.request("wwsqupdateslzt_hlw", updateSlztRequestBody);
        }*/
    }

    /**
     * @param slzt         我们系统中的受理状态
     * @param reason       审核拒绝原因
     * @param processInsId 工作流实例id
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //  流程至审核节点，信息填写错误或者存在其他问题需要退件时，由登记中心审核人员点击“审核不通过”按钮，调用建行接口
     * @Date 2022/5/28 19:40
     **/
    private Boolean rejectEnvent(String slzt, String reason, String processInsId) {
        if (!StringUtils.equals(HlwSlztEnum.ABANDON.getSlzt(), slzt)) {
            return false;
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> xmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        String key = yhtsBeanid.keySet().stream()
                .filter(item -> xmDOList.get(0).getSlbh().contains(item))
                .findFirst().orElse("");
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        //构建参数、必填字段为：不动产办理状态（DealW_St）、不动产办理意见（DealW_Opin）、不动产业务号（RealEst_Bsn_No）
        JsyhReqDataDto reqData = new JsyhReqDataDto();
        reqData.setDealwSt(CCbSlztEnum.ABANDON.getSlzt());
        reqData.setDealwOpin(reason);
        reqData.setAcptNo(xmDOList.get(0).getSlbh());
        OthrData othrData = new OthrData();
        othrData.setRealestBsnNo(xmDOList.get(0).getSlbh());
        reqData.setOthrData(othrData);
        try {
            String xml = XmlUtils.getXmlStrByObjectGBKNotIgnoreNull(reqData, JsyhReqDataDto.class);
            LOGGER.info("推送南通建设银行相关受理信息xml===={}", xml);
            Map data = new HashMap();
            data.put("areaCode", xmDOList.get(0).getQxdm());
            data.put("data", xml);
            //yhtsxx.beanId: {'NTCCB': 'jsyh_slxx','ZSYH': '待定'}
            //2、由于不同银行的反馈接口不同，并且互联网退件也是点击“审核不通过”按钮，因此需要增加配置，
            // 通过受理编号来判断调用哪个接口（如互联网受理的件受理编号为HLW开头，建设受理的件受理编号为NTCCB开头）
            String beanid = "";
            beanid = yhtsBeanid.get(key);
            Object object = exchangeBeanRequestService.request(beanid, data);
            LOGGER.info("推送南通建设银行相关接口返回信息xml===={}", object.toString());
        } catch (Exception e) {
            LOGGER.info("推送南通建设银行相关受理信息,转换xml报错！");
            e.printStackTrace();
        }
        return true;
    }


    @Override
    public void shztTs(Map map) {
        LOGGER.info("进入审核状态推送接口，map:{}", JSON.toJSONString(map));
        Map shzttsParam = new HashMap();
        String gzlslid = MapUtils.getString(map, "processInsId");
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("工作流实例id为空");
        }
        String hlwslh = bdcSlFeignService.getWwsqSlbhByDjGzlslid(gzlslid);
        LOGGER.info("进入审核状态推送接口，hlwslh:{}", hlwslh);
        if (StringUtils.isBlank(hlwslh)) {
            return;
        }
        shzttsParam.put("wwslbh", hlwslh);
        orgnizeShzttsBody(shzttsParam, map);
        Map paramMap = new HashMap();
        paramMap.put("data", shzttsParam);
        exchangeBeanRequestService.request("wwsqshztts_hlw", paramMap);

    }

    /**
     * 根据slbh转发或删除流程
     *
     * @param updateSlztRequestBody updateSlztRequestBody
     * @return Object
     * @Date 2021/12/3
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Object forwardOrDelete(@RequestBody UpdateSlztRequestBody updateSlztRequestBody) {
        UpdateSlztRequestData slztRequestData = updateSlztRequestBody.getData();
        LOGGER.info("互联网推送流程审核状态入参：{}", slztRequestData.toString());
        if (!CheckParameter.checkParameter(slztRequestData, "slbh", "slzt", "spyj", "czrmc", "czsj")) {
            throw new AppException("缺失参数异常");
        }
        BdcXmQO xmQO = new BdcXmQO();
        xmQO.setSlbh(slztRequestData.getSlbh());
        List<BdcXmDO> xmDOList = bdcXmFeignService.listBdcXm(xmQO);
        if (CollectionUtils.isNotEmpty(xmDOList)) {
            String gzlslid = xmDOList.get(0).getGzlslid();
            //审核通过则转下一节点
            if (StringUtils.equals("1", slztRequestData.getSlzt())) {
                AutoForwardTaskDTO autoForwardTaskDTO = new AutoForwardTaskDTO();
                autoForwardTaskDTO.setJdmc(turnJdmc);
                autoForwardTaskDTO.setUsername(slztRequestData.getCzrmc());
                try {
                    bdcSlFeignService.wwsqAutoTurn(gzlslid, autoForwardTaskDTO);
                } catch (Exception e) {
                    LOGGER.info("互联网通知转发到缮证报错！", e);
                    e.printStackTrace();
                }
            } else {
                //删除改流程
                WwsqdeltaskRequestDTO wwsqdeltaskRequestDTO = new WwsqdeltaskRequestDTO();
                wwsqdeltaskRequestDTO.setReason(slztRequestData.getSpyj());
                wwsqdeltaskRequestDTO.setProcessInstanceId(gzlslid);
                Map<String, Object> deleteResp = gxWwSqxxService.deleteTask(wwsqdeltaskRequestDTO);
                LOGGER.info("互联网要求删除改流程！slbh：{}", slztRequestData.toString());

            }

        } else {
            LOGGER.info("此slbh查不到项目数据！：{}", slztRequestData.getSlbh());
            throw new AppException("slbh查不到项目数据！");
        }
        return null;
    }

    /**
     * * 根据slbh查询缴费二维码
     *
     * @param updateSlztRequestBody slztRequestData
     * @return Object
     * @Date 2021/12/3
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Object queryJfewmUrl(@RequestBody UpdateSlztRequestBody updateSlztRequestBody) {
        UpdateSlztRequestData slztRequestData = updateSlztRequestBody.getData();
        if (null != slztRequestData && StringUtils.isNotBlank(slztRequestData.getSlbh())) {
            JSONArray array = new JSONArray();

            BdcXmQO xmQO = new BdcXmQO();
            xmQO.setSlbh(slztRequestData.getSlbh());
            List<BdcXmDO> xmDOList = bdcXmFeignService.listBdcXm(xmQO);
            if (CollectionUtils.isNotEmpty(xmDOList)) {
                String gzlslid = xmDOList.get(0).getGzlslid();
                List<BdcSlSfxxDO> slSfxxDOS = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
                if (CollectionUtils.isNotEmpty(slSfxxDOS)) {
                    for (BdcSlSfxxDO slSfxxDO : slSfxxDOS) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("sfxxid", slSfxxDO.getSfxxid());
                        jsonObject.put("qlrlb", slSfxxDO.getQlrlb());
                        jsonObject.put("jfsewmurl", slSfxxDO.getJfsewmurl());
                        array.add(jsonObject);
                    }
                    return array;
                }
            } else {
                throw new AppException("该受理编号查不到项目数据！请检查数据！");
            }
        } else {
            throw new MissingArgumentException("缺失slbh参数！请检查！");
        }
        return null;
    }

    /**
     * 外网推送缴费信息
     *
     * @param tsjfxxRequestBody @return Object
     * @Date 2021/12/3
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Object wwsqTsjfxx(TsjfxxRequestBody tsjfxxRequestBody) {
        BdcDsfSfxxDTO bdcDsfSfxxDTO = tsjfxxRequestBody.getData();
        if (null != bdcDsfSfxxDTO && StringUtils.isNotBlank(bdcDsfSfxxDTO.getSlbh())) {
            BdcXmQO xmQO = new BdcXmQO();
            xmQO.setSlbh(bdcDsfSfxxDTO.getSlbh());
            List<BdcXmDO> xmDOList = bdcXmFeignService.listBdcXm(xmQO);
            if (CollectionUtils.isNotEmpty(xmDOList)) {
                String gzlslid = xmDOList.get(0).getGzlslid();
                bdcDsfSfxxDTO.setGzlslid(gzlslid);
            } else {
                //登记库无信息，查一窗的信息
                BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxBySlbh(bdcDsfSfxxDTO.getSlbh(), "");
                if (Objects.nonNull(bdcSlJbxxDO)) {
                    bdcDsfSfxxDTO.setGzlslid(bdcSlJbxxDO.getGzlslid());
                } else {
                    LOGGER.info("查不到一窗信息：受理编号：{}", bdcDsfSfxxDTO.getSlbh());
                    throw new MissingArgumentException("无项目信息和一窗流程信息！请检查！");
                }
            }
            if (CommonConstantUtils.JKFS_XSJF.equals(bdcDsfSfxxDTO.getJkfs())) {
                bdcDsfSfxxDTO.setTsdjfxx(true);
            } else {
                bdcDsfSfxxDTO.setTsdjfxx(false);
            }

            CommonResponse commonResponse = bdcSlSfxxFeignService.jsSfxxSaveAndTs(bdcDsfSfxxDTO);
            if (commonResponse.isSuccess()) {
                return commonResponse.getData();
            } else {
                throw new AppException(commonResponse.getFail().getMessage());
            }
        } else {
            throw new MissingArgumentException("缺失必要参数！请检查！");
        }

    }

    private BdcXmDO queryBdcxmDoByGzlslid(String gzlslid) {
        BdcXmDO bdcXmDO = null;
        if (StringUtils.isNotBlank(gzlslid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                bdcXmDO = bdcXmDOList.get(0);
            }
        }
        return bdcXmDO;
    }

    private void orgnizeUpdateSlztBody(UpdateSlztRequestBody updateSlztRequestBody, UpdateSlztRequestData updateSlztRequestData, Map map) {
        String taskId = MapUtils.getString(map, "taskId");
        String opinion = MapUtils.getString(map, "opinion");
        String isDelete = MapUtils.getString(map, "isDelete");
        String nextNodeNames = MapUtils.getString(map, "nextNodeNames");
        String gzlslid = MapUtils.getString(map, "processInsId");
        String czr = MapUtils.getString(map, "czrxm");
        //查询收费信息表
        List<BdcSlSfxxDO> bdcSlSfxxList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
        updateSlztRequestData.setJfzt("1");
        if (CollectionUtils.isNotEmpty(bdcSlSfxxList)) {
            //循环完，有一个未缴费，则返回未缴费
            for (BdcSlSfxxDO sfxxDo : bdcSlSfxxList) {
                if (!CommonConstantUtils.SFZT_YJF.equals(sfxxDo.getSfzt())) {
                    updateSlztRequestData.setJfzt("0");
                }
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        updateSlztRequestData.setCzsj(sdf.format(new Date()));
        updateSlztRequestData.setCzrmc(czr);
        if (StringUtils.isNotBlank(isDelete)) {
            if (StringUtils.isNotBlank(opinion)) {
                updateSlztRequestData.setSpyj(opinion);
            }
            updateSlztRequestData.setSlzt(isDelete);
            updateSlztRequestData.setSlztmc(HlwSlztEnum.getDescriptionBySlzt(isDelete));
            updateSlztRequestBody.setData(updateSlztRequestData);
        } else {
            // 当不是删除时 查询下一个节点
            if (StringUtils.isBlank(taskId)) {
                return;
            }
            if (StringUtils.isNotBlank(nextNodeNames)) {
                updateSlztRequestData.setSlztmc(nextNodeNames);
            } else {
                updateSlztRequestData.setSlztmc("办结");
            }

            updateSlztRequestBody.setData(updateSlztRequestData);
        }
    }

    /**
     * 南通组织推送互联网参数，增加月结逻辑判断
     *
     * @Date 2021/8/25
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    private UpdateSlztRequestBody orgnizeUpdateSlztBodyNt(UpdateSlztRequestBody updateSlztRequestBody, UpdateSlztRequestData updateSlztRequestData, Map map) {
        String taskId = MapUtils.getString(map, "taskId");
        String opinion = MapUtils.getString(map, "opinion");
        String isDelete = MapUtils.getString(map, "isDelete");
        String nextNodeNames = MapUtils.getString(map, "nextNodeNames");
        String gzlslid = MapUtils.getString(map, "processInsId");
        //查询收费信息表
//        List<BdcSlSfxxDO> bdcSlSfxxList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
//        updateSlztRequestData.setJfzt("1");
        /*if (CollectionUtils.isNotEmpty(bdcSlSfxxList)) {
            //循环完，有一个未缴费，则返回未缴费
            for (BdcSlSfxxDO sfxxDo : bdcSlSfxxList) {
                if (!CommonConstantUtils.SFZT_YJF.equals(sfxxDo.getSfzt())) {
                    updateSlztRequestData.setJfzt("0");
                }
            }
        }*/
        LOGGER.info("推送互联网状态参数：{}", map.toString());
        if (StringUtils.isNotBlank(isDelete)) {
            updateSlztRequestData.setSpyj(opinion);
            updateSlztRequestData.setSlzt(isDelete);
            updateSlztRequestData.setSlztmc(HlwSlztEnum.getDescriptionBySlzt(isDelete));
            updateSlztRequestBody.setData(updateSlztRequestData);
        } else {
            // 当不是删除时 查询下一个节点
            if (StringUtils.isBlank(taskId)) {
                return updateSlztRequestBody;
            }
            if (StringUtils.isNotBlank(nextNodeNames)) {
                updateSlztRequestData.setSlztmc(nextNodeNames);
            } else {
                updateSlztRequestData.setSlztmc("办结");
            }
            updateSlztRequestBody.setData(updateSlztRequestData);
        }
        //增加月结逻辑判断，是月结用户则直接跳过，默认为已缴费;因为该接口是整个流程的判断，所以所有项目的权利人和义务人都要判断
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> xmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(xmDOList)) {

            for (BdcXmDO bdcXmDO : xmDOList) {
                BdcQlrQO qlrQO = new BdcQlrQO();
                qlrQO.setXmid(bdcXmDO.getXmid());
                List<BdcQlrDO> qlrDOList = qlrFeignService.listBdcQlr(qlrQO);
                if (CollectionUtils.isNotEmpty(qlrDOList)) {
                    for (BdcQlrDO qlrDO : qlrDOList) {
                        List<BdcXtJgDO> xtJgDOList = bdcXtJgFeignService.listAyjsBdcXtJgYhmc(qlrDO.getQlrmc());
                        //如果不是月结用户，则按sfxx的jfzt进行判断
                        if (CollectionUtils.isEmpty(xtJgDOList)) {
                            LOGGER.info("非月结用户：{},xmid:{}", qlrDO.getQlrmc(), qlrDO.getXmid());
                            // 判断权利人是否是月结用户,不是月结用户，xmid查询收费信息表
                            BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
                            bdcSlSfxxQO.setXmid(qlrDO.getXmid());
                            bdcSlSfxxQO.setQlrlb(qlrDO.getQlrlb());
//                            List<BdcSlSfxxDO> bdcSlSfxxList = bdcSlSfxxFeignService.listBdcSlSfxxByXmid(qlrDO.getXmid());
                            List<BdcSlSfxxDO> bdcSlSfxxList = bdcSlSfxxFeignService.queryBdcSlSfxx(bdcSlSfxxQO);
                            if (CollectionUtils.isNotEmpty(bdcSlSfxxList)) {
                                for (BdcSlSfxxDO sfxxDo : bdcSlSfxxList) {
                                    if (!CommonConstantUtils.SFZT_YJF.equals(sfxxDo.getSfzt())) {
                                        updateSlztRequestData.setJfzt("0");
                                        return updateSlztRequestBody;
                                    }
                                }
                                //循环完，都没有返回，说明都已缴费
                                updateSlztRequestData.setJfzt("1");
                            } else {
                                updateSlztRequestData.setJfzt("1");
                            }
                        } else {
                            LOGGER.info("月结用户：{},xmid:{}", qlrDO.getQlrmc(), qlrDO.getXmid());
                            updateSlztRequestData.setJfzt("1");
                        }
                    }
                }
            }
        }

        return updateSlztRequestBody;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 组织审核状态推送数据
     */
    private void orgnizeShzttsBody(Map shzttsParam, Map map) {
        String opinion = MapUtils.getString(map, "opinion");
        String shzt = MapUtils.getString(map, "isDelete");
        if (StringUtils.isNotBlank(shzt)) {
            shzttsParam.put("shzt", shzt);
            shzttsParam.put("shztmc", HlwShztEnum.getDescriptionByShzt(shzt));
            shzttsParam.put("shyj", opinion);
            //1：登记3.0预审流程，2：登记3.0一体化流程, 3：登记3.0线下流程,4：税务系统，5：登记2.0，放代码
            shzttsParam.put("sjly", "1");

            shzttsParam.put("czsj", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        }
    }


    /**
     * 根据不动产证明号查询办件状态和收费状态
     *
     * @param param
     * @return
     * @Date 2021/8/4
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public Map queryBjztByBdczmh(Map param) {
        Map dataMap = new HashMap(3);

        String bdcqzh = MapUtils.getString(param, "bdczmh");
        String slbh = MapUtils.getString(param, "slbh");
        if (StringUtils.isBlank(bdcqzh) && StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("参数不能为空！请检查！");
        }
        LOGGER.info("queryBjztByBdczmh入参为：bdcqzh{}，slbh{}", bdcqzh, slbh);
        //根据条件查询项目表
        List<BdcXmDO> xmDOList = bdcXmMapper.queryXmBySlbhAndCqzh(bdcqzh, slbh);
        if (CollectionUtils.isNotEmpty(xmDOList)) {
            Integer ajzt = xmDOList.get(0).getAjzt();
            for (BdcXmDO xmDO : xmDOList) {
                BdcQlrQO qlrQO = new BdcQlrQO();
                qlrQO.setXmid(xmDO.getXmid());
                dataMap.put("ajzt", ajzt);
                List<BdcQlrDO> qlrDOList = qlrFeignService.listBdcQlr(qlrQO);
                if (CollectionUtils.isNotEmpty(qlrDOList)) {
                    for (BdcQlrDO qlrDO : qlrDOList) {
                        List<BdcXtJgDO> xtJgDOList = bdcXtJgFeignService.listAyjsBdcXtJgYhmc(qlrDO.getQlrmc());
                        //如果不是月结用户，则按sfxx的jfzt进行判断
                        if (CollectionUtils.isEmpty(xtJgDOList)) {
                            LOGGER.info("不是月结用户：{},xmid{}", qlrDO.getQlrmc(), qlrDO.getXmid());
                            // 判断权利人是否是月结用户,不是月结用户，查询收费信息表
                            BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
                            bdcSlSfxxQO.setXmid(qlrDO.getXmid());
                            bdcSlSfxxQO.setQlrlb(qlrDO.getQlrlb());
                            List<BdcSlSfxxDO> bdcSlSfxxList = bdcSlSfxxFeignService.queryBdcSlSfxx(bdcSlSfxxQO);
                            if (CollectionUtils.isNotEmpty(bdcSlSfxxList)) {
                                for (BdcSlSfxxDO sfxxDo : bdcSlSfxxList) {
                                    if (!CommonConstantUtils.SFZT_YJF.equals(sfxxDo.getSfzt())) {
                                        dataMap.put("jfzt", CommonConstantUtils.SFZT_WJF);
                                        return dataMap;
                                    }
                                }
                                //循环完，都没有返回，说明都已缴费
                                dataMap.put("jfzt", CommonConstantUtils.SFZT_YJF);
                            }
                        } else {
                            LOGGER.info("是月结用户：{},xmid{}", qlrDO.getQlrmc(), qlrDO.getXmid());
                            dataMap.put("jfzt", CommonConstantUtils.SFZT_YJF);
                        }
                    }
                }
            }
        }

        return dataMap;
        //无月结逻辑代码
        /*Map dataMap = new HashMap();

        String bdczmh = MapUtils.getString(param, "bdczmh");
        if (StringUtils.isBlank(bdczmh)) {
            throw new MissingArgumentException("参数不能为空！请检查！");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setBdcqzh(bdczmh);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            String gzlslid = bdcXmDOList.get(0).getGzlslid();
            dataMap.put("ajzt", bdcXmDOList.get(0).getAjzt());
            dataMap.put("bdczmh", bdcXmDOList.get(0).getBdcqzh());
            //查询收费信息表
            List<BdcSlSfxxDO> bdcSlSfxxList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlSfxxList)) {
                for (BdcSlSfxxDO sfxxDo : bdcSlSfxxList) {
                    if (!CommonConstantUtils.SFZT_YJF.equals(sfxxDo.getSfzt())) {
                        dataMap.put("jfzt", 1);
                        return dataMap;
                    }
                }
                //循环完，都没有返回，说明都已缴费
                dataMap.put("jfzt", 2);
            }
        }
        return dataMap;
*/
    }


}
