package cn.gtmap.realestate.exchange.service.impl.nantong;

import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessInstanceClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.request.SdqHyRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.response.SdqHyDataDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.response.SdqHyResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztUpdateHyDTO;
import cn.gtmap.realestate.common.core.enums.BdcSdqEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcSdqywQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdqghFeignService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.exchange.config.SdqConfig;
import cn.gtmap.realestate.exchange.core.component.BdcDmToDsfZdComponent;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.AbstactFileInfo;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.factory.QiFactory;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.factory.ShuiFactory;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.qi.*;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.shui.ShuiGhTsxxDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.shui.ShuiGhxxZjDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.response.AbstractResponse;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.response.SqGhxxResponse;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.response.qi.QiDTo;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.response.qi.QiGhHmDataDto;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.response.qi.QiGhjcHmDataDto;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.response.shui.ShuiDTO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.nantong.NantongSqService;
import cn.gtmap.realestate.exchange.util.ASCIIUtil;
import cn.gtmap.realestate.exchange.util.HttpClientUtils;
import cn.gtmap.realestate.exchange.util.Sha1Util;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @date 2022/4/26
 * @description
 */
@Service
public class NantongSqServiceImpl implements NantongSqService {

    private static Logger LOGGER = LoggerFactory.getLogger(NantongSqServiceImpl.class);

    /**
     * 接口类型为过户
     */
    private static final String JKLX_GH = "gh";

    /**
     * 接口类型为核验
     */
    private static final String JKLX_HY = "hy";

    @Autowired
    BdcSdqghFeignService bdcSdqghFeignService;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Autowired
    SdqConfig sdqConfig;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    private BdcZdCache bdcZdCache;

    @Autowired
    private BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    private HttpClientUtils httpClientUtils;

    @Autowired
    private BdcZsFeignService bdcZsFeignService;

    @Autowired
    private ECertificateFeignService eCertificateFeignService;

    @Autowired
    private ProcessInstanceClient processInstanceClient;

    @Autowired
    BdcDmToDsfZdComponent bdcDmToDsfZdComponent;

    @Autowired
    private HttpClientService httpClientService;

    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;

    /**
     * 通州水气的appkey
     */
    @Value("${sdq.tzsq.appkey:}")
    private String appkey;

    /**
     * @return
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 供气推送过户
     * 1、权利人义务人不管是单独还是多个：权利人、义务人均推送，多个的话用逗号隔开；
     * 4、只推送权利人，只推送户主；
     * 5、海门区，特殊
     * @Date 2022/4/27
     * @Param
     **/
    @Override
    public void qgh(String processInsId) {
        LOGGER.info("气过户的工作流id为：{}", processInsId);
        // 根据工作流id查询`不动产水电气过户信息表`，校验过户的数据和户号
        BdcSdqghDO bdcSdqghDO = getBdcSdqghAndValid(processInsId, BdcSdqEnum.Q);
        // 如果没有过户信息，不过户
        if (Objects.isNull(bdcSdqghDO)) {
            LOGGER.info("未查询到水电气过户信息，不进行过户，工作流实例id:{}", processInsId);
            return;
        }
        // 获取不动产项目信息并校验
        BdcXmDO bdcXmDO = getBdcXmAndValid(processInsId, BdcSdqEnum.Q);
        // 获取用户配置
        SdqConfig.UnitInfo config = getConfig(BdcSdqEnum.Q, bdcXmDO.getQxdm(), bdcSdqghDO.getRqfwbsm(), JKLX_GH);
        //请求dto
        if (Objects.equals(5, config.getPushStyle())) {
            // 获取选择的权利人、义务人
            BdcQlrDO selectiveQlr = getNewInfo(processInsId, bdcSdqghDO.getXhzmc(), BdcSdqEnum.Q);
            BdcQlrDO selectiveYwr = getOldInfo(processInsId, bdcSdqghDO.getHzmc(), BdcSdqEnum.Q);
            // 获取附件
            Map<String, ClnrDTO> fileMap = new HashMap<>();
            ClnrDTO sfgImg = getsfzz(processInsId, config.getClmcs());
            fileMap.put("sfgImg", sfgImg);

            // 获取证书 -- 对方值接受图片内容将证书做转换
            ClnrDTO bdczImg = getzs(bdcXmDO.getXmid());
            fileMap.put("bdczImg", bdczImg);

            // 获取临时token
            String tempToken = getTempToken(config);
            // 获取真实token
            String token = getToken(tempToken, selectiveQlr.getDh(), config.getTokenUrl());
            // 构建请求参数
            if(Objects.nonNull(selectiveQlr.getZjzl())) {
                selectiveQlr.setZjzl(Integer.parseInt(bdcDmToDsfZdComponent
                        .conver("BDC_ZD_ZJLX", "QGH", selectiveQlr.getZjzl().toString())
                        .toString())
                );
            }
            QiGhHmDto req = QiFactory.buildGhHmParam(bdcSdqghDO,
                    selectiveYwr, selectiveQlr,
                    null,
                    null,
                    config);
            Map<String, Object> paramMap = JSON.parseObject(JSON.toJSONString(req), new TypeReference<Map<String, Object>>() {
            });
            // 过户
            QiGhHmDataDto returnDto = formGh(bdcSdqghDO, config.getGhUrl(), paramMap, fileMap, BdcSdqEnum.Q, QiGhHmDataDto.class, token);
            // 更新状态
            LOGGER.info("气过户请求参数{}", JSON.toJSONString(returnDto));
            boolean isSuccess = StringUtils.equals(CommonConstantUtils.SUCCESS_CODE_0000, returnDto.getCode()) ||
                    StringUtils.equals(CommonConstantUtils.NANTONG_SQ_CHECK_RETURN_CODE_SUCCESS, returnDto.getCode());
            if (!isSuccess) {
                bdcSdqghDO.setSdqshyj(returnDto.getMsg());
            }
            updateSdqgh(bdcSdqghDO, BdcSdqEnum.Q, isSuccess);
        } else if (Objects.equals(4, config.getPushStyle())) {
            commonSqGh(processInsId,BdcSdqEnum.Q, bdcSdqghDO, bdcXmDO, config);
        } else if (Objects.equals(1, config.getPushStyle())) {
            //获取新户主的信息
            List<BdcQlrDO> allQrl = getAllQrlByXmid(bdcXmDO.getXmid());
            List<BdcQlrDO> allYwl = getAllYwlByXmid(bdcXmDO.getXmid());
            QiGhxxZjDTO.QiFileInfo fileInfo = getDzzzxx(bdcXmDO, QiGhxxZjDTO.QiFileInfo.class);
            QiGhxxZjDTO req = QiFactory.buildGhxxZjParam(bdcSdqghDO, bdcXmDO, fileInfo, allQrl, allYwl);
            Map<String, Object> paramMap = JSON.parseObject(JSON.toJSONString(req), new TypeReference<Map<String, Object>>() {
            });
            QiDTo qiGhDto = gh(bdcSdqghDO, config.getGhUrl(), paramMap, BdcSdqEnum.Q, QiDTo.class);
            boolean isSuccess = Objects.equals(CommonConstantUtils.NANTONG_SQ_CHECK_RETURN_CODE_SUCCESS, qiGhDto.getCode()) ||
                    Objects.equals(CommonConstantUtils.SUCCESS_CODE_0000, qiGhDto.getCode());
            if (!isSuccess) {
                if (qiGhDto.getData() != null) {
                    bdcSdqghDO.setSdqshyj(qiGhDto.getData().getMessage());
                } else {
                    bdcSdqghDO.setSdqshyj(qiGhDto.getMsg());
                }
            }
            updateSdqgh(bdcSdqghDO, BdcSdqEnum.Q, isSuccess);
        }

    }

    /**
     * @param processInsId 工作流实例id
     * @param ywlx         业务类型
     * @param bdcSdqghDO   水电气信息
     * @param bdcXmDO      项目信息
     * @param config       配置项
     * @description 水气通用过户
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @date: 2022/12/30 11:15
     * @return: void
     **/
    private void commonSqGh(String processInsId, BdcSdqEnum ywlx, BdcSdqghDO bdcSdqghDO, BdcXmDO bdcXmDO, SdqConfig.UnitInfo config) {
        // 获取选择的权利人
        BdcQlrDO selectiveQlr = getNewInfo(processInsId, bdcSdqghDO.getXhzmc(), ywlx);
        // 获取附件
        Map<String, List<ClnrDTO>> material = getMaterial(processInsId, config.getClmcs(), ywlx);
        // 构建请求参数
        SqGhxxRequest req = QiFactory.buildGhxxParam(bdcSdqghDO, bdcXmDO, selectiveQlr, material, ywlx.key().toString());
        String beanName = "";
        Object requestObject = null;
        if (BdcSdqEnum.Q.equals(ywlx)) {
            beanName = "qi_gh";
            requestObject = req;
            if (StringUtils.isNotBlank(appkey)){
                beanName = "tzqi_gh";
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("body",req);
                requestObject = jsonObject;
            }
        }
        if (BdcSdqEnum.S.equals(ywlx)) {
            beanName = "shui_gh";
            requestObject = req;
            if (StringUtils.isNotBlank(appkey)){
                beanName = "tzshui_gh";
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("body",req);
                requestObject = jsonObject;
            }
        }
        Object response = null;
        try {
            response = exchangeBeanRequestService.request(beanName, requestObject);
        } catch (Exception e) {
            updateFailRecord(bdcSdqghDO, ywlx.key());
            throw new AppException("httpPost请求异常GH");
        }
        if (Objects.nonNull(response)) {
            SqGhxxResponse ghxxResponse = JSONObject.parseObject(JSONObject.toJSONString(response), SqGhxxResponse.class);
            boolean isSuccess = Objects.equals(CommonConstantUtils.NANTONG_SQ_CHECK_RETURN_CODE_SUCCESS, ghxxResponse.getCode()) ||
                    Objects.equals(CommonConstantUtils.SUCCESS_CODE_0000, ghxxResponse.getCode());
            if (!isSuccess) {
                if (ghxxResponse.getData()!=null){
                    bdcSdqghDO.setSdqshyj(ghxxResponse.getData().getMessage());
                }else{
                    bdcSdqghDO.setSdqshyj(ghxxResponse.getMsg());
                }
            }
            // 更新状态
            updateSdqgh(bdcSdqghDO, ywlx, isSuccess);
        } else {
            updateFailRecord(bdcSdqghDO, ywlx.key());
            throw new AppException(ywlx.value() + "过户失败！");
        }
    }

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //获取临时token
     * @Date 2022/5/31 16:01
     **/
    private String getTempToken(SdqConfig.UnitInfo config) {
        if (StringUtil.isEmpty(config.getTempTokenUrl())) {
            throw new AppException("获取临时token的地址未配置");
        }
        if (StringUtil.isEmpty(config.getClientId())) {
            throw new AppException("clientId未配置");
        }
        if (StringUtil.isEmpty(config.getSecret())) {
            throw new AppException("secret未配置");
        }
        QiGhHmlsTokenDto authToken = QiFactory.buildGhHmlsToken(config);
        Map<String, Object> paramMap = JSON.parseObject(JSON.toJSONString(authToken), new TypeReference<Map<String, Object>>() {
        });
        String res = httpClientUtils.sendPostRequest(config.getTempTokenUrl(), paramMap);
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (StrUtil.equals(jsonObject.getString("code"), CommonConstantUtils.NANTONG_SQ_CHECK_RETURN_CODE_SUCCESS)) {
            return jsonObject.getJSONObject("data").getString("authToken");
        }
        return "";
    }

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //获取token
     * @Date 2022/5/31 16:58
     **/
    private String getToken(String authToken, String mobile, String url) {
        QiGhHmTokenDto token = QiFactory.buildGhHmToken(authToken, mobile);
        Map<String, Object> paramMap = JSON.parseObject(JSON.toJSONString(token), new TypeReference<Map<String, Object>>() {
        });
        String res = httpClientUtils.sendPostRequest(url, paramMap);
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (StrUtil.equals(jsonObject.getString("code"), CommonConstantUtils.NANTONG_SQ_CHECK_RETURN_CODE_SUCCESS)) {
            return jsonObject.getJSONObject("data").getString("token");
        }
        return "";
    }


    /**
     * @return
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 供水推送过户
     * @Date 2022/4/28
     * @Param processInsId  工作流实例id
     **/
    @Override
    public void sgh(String processInsId) {
        LOGGER.info("水过户的工作流id为：{}", processInsId);
        // 根据工作流id查询`不动产水电气过户信息表`，校验过户的数据和户号
        BdcSdqghDO bdcSdqghDO = getBdcSdqghAndValid(processInsId, BdcSdqEnum.S);
        // 如果没有过户信息，不过户
        if (Objects.isNull(bdcSdqghDO)) {
            LOGGER.info("未查询到水电气过户信息，不进行过户，工作流实例id:{}", processInsId);
            return;
        }
        // 获取不动产项目信息并校验
        BdcXmDO bdcXmDO = getBdcXmAndValid(processInsId, BdcSdqEnum.S);
        // 获取用户配置
        SdqConfig.UnitInfo config = getConfig(BdcSdqEnum.S, bdcXmDO.getQxdm(), bdcSdqghDO.getRqfwbsm(), JKLX_GH);
        Map<String, Object> paramMap = new HashMap<>();
        //请求dto
        if (Objects.equals(4, config.getPushStyle())) {
            commonSqGh(processInsId,BdcSdqEnum.S, bdcSdqghDO, bdcXmDO, config);
        } else if (Objects.equals(1, config.getPushStyle())) {
            //获取新户主的信息
            List<BdcQlrDO> allQrl = getAllQrlByXmid(bdcXmDO.getXmid());
            List<BdcQlrDO> allYwl = getAllYwlByXmid(bdcXmDO.getXmid());
            List<ShuiGhxxZjDTO.ShuiFileInfo> fileInfoList = new ArrayList<>();
            Map<String, List<ClnrDTO>> material = getMaterial(processInsId, config.getClmcs(), BdcSdqEnum.S);
            for (Map.Entry<String, List<ClnrDTO>> stringListEntry : material.entrySet()) {
                for (ClnrDTO clnrDTO : stringListEntry.getValue()) {
                    ShuiGhxxZjDTO.ShuiFileInfo fileInfo = new ShuiGhxxZjDTO.ShuiFileInfo();
                    fileInfo.setFileData(clnrDTO.getFjnr());
                    fileInfo.setFileName(clnrDTO.getFjmc());
                    fileInfoList.add(fileInfo);
                }
            }
            ShuiGhxxZjDTO req = ShuiFactory.buildGhxxZjParam(bdcSdqghDO, bdcXmDO, fileInfoList, allQrl, allYwl);
            // 过户、更新状态
            paramMap = JSON.parseObject(JSON.toJSONString(req), new TypeReference<Map<String, Object>>() {
            });
            ShuiDTO shuiGhDTO = gh(bdcSdqghDO, config.getGhUrl(), paramMap, BdcSdqEnum.S, ShuiDTO.class);
            // 返回码为0000或1，都是成功
            boolean isSuccess = Objects.equals(CommonConstantUtils.NANTONG_SQ_CHECK_RETURN_CODE_SUCCESS, shuiGhDTO.getCode()) || Objects.equals(CommonConstantUtils.SUCCESS_CODE_0000, shuiGhDTO.getCode());
            if (!isSuccess) {
                if (shuiGhDTO.getData() != null) {
                    bdcSdqghDO.setSdqshyj(shuiGhDTO.getData().getMessage());
                }else {
                    bdcSdqghDO.setSdqshyj(shuiGhDTO.getMsg());
                }
            }
            updateSdqgh(bdcSdqghDO, BdcSdqEnum.S, isSuccess);
        }
    }

    /**
     * @return QiCheckConsnoDataDTo
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 校验供水户号是否合法
     * @Date 2022/4/28
     * @Param processInsId 工作流实例id
     * @Param processInsId 户号
     **/
    @Override
    public CommonResponse<Integer> sghjc(String processInsId, String consno,String dwdm) {
        // 根据工作流id查询`不动产水电气过户信息表`，校验过户的数据和户号
        BdcSdqghDO bdcSdqghDO = getBdcSdqghAndValid(processInsId, BdcSdqEnum.S);
        // 获取不动产项目信息的qxdm
        BdcXmQO bdcXmQ0 = new BdcXmQO();
        bdcXmQ0.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQ0);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            LOGGER.info("未查询到水需要过户核验的项目信息，该流程实例id为：{}", processInsId);
            throw new AppException("未查询到水需要过户核验的项目信息!请检查！");
        }
        BdcXmDO bdcXmDO = bdcXmDOList.get(0);
        return getSqghjcCommonResponse(processInsId, consno, BdcSdqEnum.S, bdcSdqghDO, bdcXmDO);
    }

    @NotNull
    private CommonResponse<Integer> getSqghjcCommonResponse(String processInsId, String consno,BdcSdqEnum ywlx, BdcSdqghDO bdcSdqghDO, BdcXmDO bdcXmDO) {
        SdqHyRequestDTO requestDTO = new SdqHyRequestDTO();
        requestDTO.setDwdm(bdcSdqghDO.getRqfwbsm());
        requestDTO.setQxdm(bdcXmDO.getQxdm());
        requestDTO.setConsno(consno);
        requestDTO.setSfcxqf("1");
        requestDTO.setYwlx(ywlx.key().toString());
        requestDTO.setJklx(JKLX_HY);
        // 过户 户号验证接口
        LOGGER.info("调用{}过户校验接口开始，该流程实例id为：{}，请求参数为：{}", ywlx.value(),processInsId,requestDTO.toString());
        String beanName = "";
        if (BdcSdqEnum.S.equals(ywlx)){
            beanName = StringUtils.isBlank(appkey)?"shui_ghhy":"tzshui_ghhy";
        }
        if (BdcSdqEnum.Q.equals(ywlx)){
            beanName = StringUtils.isBlank(appkey)?"qi_ghhy":"tzqi_ghhy";
        }
        Object response = exchangeBeanRequestService.request(beanName,requestDTO);
        if (Objects.nonNull(response)) {
            SdqHyResponseDTO sdqHyResponseDTO = JSONObject.parseObject(JSONObject.toJSONString(response),SdqHyResponseDTO.class);
            LOGGER.info("调用{}过户校验接口结束，该流程实例id为：{},校验结果为{}", ywlx.value(),processInsId, sdqHyResponseDTO.toString());
            SdqHyDataDTO data = sdqHyResponseDTO.getData();
            List<BdcQlrDO> allYwl = getAllYwl(processInsId);
            Boolean isContainOriginal = allYwl
                    .stream()
                    .anyMatch(item -> StringUtils.equals(data.getHzmc(), item.getQlrmc()));
            if (!isContainOriginal) {
                return CommonResponse.fail("9999", "户号的老户主和接口访问的老户主名称不一致", 1);
            } else {
                // 返回code为0000或1，如果data.getLimitFlag()有值，还要看limitflag==0
                if ((Objects.equals(sdqHyResponseDTO.getCode(), CommonConstantUtils.NANTONG_S_CHECK_RETURN_CODE_SUCCESS) || Objects.equals(sdqHyResponseDTO.getCode(), CommonConstantUtils.NANTONG_SQ_CHECK_RETURN_CODE_SUCCESS)) &&
                        (StringUtils.isBlank(data.getLimitFlag()) || (StringUtils.isNotBlank(data.getLimitFlag()) && Objects.equals(data.getLimitFlag(), "0")))) {
                    updateCheckInfo(bdcSdqghDO.getId(), true, "", data.getHzmc());
                    return CommonResponse.ok(0);
                } else {
                    String reason =  sdqHyResponseDTO.getMsg();
                    if (StringUtils.isNotBlank(data.getReason())){
                        reason = data.getReason();
                    }
                    updateCheckInfo(bdcSdqghDO.getId(), false, reason, data.getHzmc());
                    return CommonResponse.fail("9999", reason, 1);
                }
            }
        }else{
            return CommonResponse.fail("9999", "请求户号验证接口错误", 1);
        }
    }

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //获取不动产证书的电子照
     * @Date 2022/5/26 9:36
     **/
    public ClnrDTO getzs(String xmid) {
        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(xmid);
        if (CollectionUtils.isEmpty(bdcZsDOList)) {
            throw new AppException("不动产权证书为空");
        }
        BdcZsDO bdcZsDO = bdcZsDOList.get(0);
        MultipartDto downloadFile = storageClient.download(bdcZsDO.getStorageid());
        if (Objects.isNull(downloadFile)) {
            throw new AppException("电子证照获取失败");
        }
        try {
            //将文件下载到本地
            String pdfOpfile = printPath + downloadFile.getName();
            File file = new File(pdfOpfile);
            if (Objects.isNull(file)) {
                file.createNewFile();
            }
            //写入文件内容
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(downloadFile.getData());
            fos.flush();
            fos.close();
            String bdczImgnr = FileUtils.pdf2Image(pdfOpfile,printPath,300);
            int dot = downloadFile.getName().lastIndexOf('.');
            String imagePdfName = downloadFile.getName().substring(0, dot);
            ClnrDTO clnrDTO = new ClnrDTO();
            clnrDTO.setFjmc(imagePdfName + ".jpg");
            clnrDTO.setFjnr(bdczImgnr);
            return clnrDTO;
        } catch (IOException e) {
            throw new AppException("电子证照获取失败");
        }
    }

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //获取不动产证书的电子照
     * @Date 2022/5/26 9:36
     **/
    private byte[] getzsByte(String xmid) {
        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(xmid);
        if (CollectionUtils.isEmpty(bdcZsDOList)) {
            throw new AppException("不动产权证书为空");
        }
        BdcZsDO bdcZsDO = bdcZsDOList.get(0);
        MultipartDto multipartDto = storageClient.download(bdcZsDO.getStorageid());
        if (Objects.isNull(multipartDto)) {
            throw new AppException("电子证照获取失败");
        }
        return multipartDto.getData();
    }

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 更新核验信息
     * @Date 2022/4/28
     **/
    private void updateCheckInfo(String id, Boolean isFlag, String reason, String hzmc) {
        BdcSdqBlztUpdateHyDTO bdcSdqBlztUpdateHyDTO = new BdcSdqBlztUpdateHyDTO();
        bdcSdqBlztUpdateHyDTO.setId(id);
        bdcSdqBlztUpdateHyDTO.setHyjg(isFlag ? CommonConstantUtils.HYJG_SUCESS : CommonConstantUtils.HYJG_FAIL);
        bdcSdqBlztUpdateHyDTO.setHyxq(reason);
        bdcSdqBlztUpdateHyDTO.setHzmc(hzmc);
        bdcSdqghFeignService.updateSdqghhy(bdcSdqBlztUpdateHyDTO);
    }

    /**
     * @return ShuiCheckConsnoDataDTo
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 校验供气户号是否合法
     * @Date 2022/4/28
     * @Param processInsId 工作流实例id
     * @Param consno 户号
     **/
    @Override
    public CommonResponse<Integer> qghjc(String processInsId, String consno) {
        // 根据工作流id查询`不动产水电气过户信息表`，校验过户的数据和户号
        BdcSdqghDO bdcSdqghDO = getBdcSdqghAndValid(processInsId, BdcSdqEnum.Q);
        // 获取不动产项目信息的qxdm
        BdcXmQO bdcXmQ0 = new BdcXmQO();
        bdcXmQ0.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQ0);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            LOGGER.info("未查询到气需要过户核验的项目信息，该流程实例id为：{}", processInsId);
            throw new AppException("未查询到气需要过户核验的项目信息!请检查！");
        }
        BdcXmDO bdcXmDO = bdcXmDOList.get(0);
        //获取地址
        SdqConfig.UnitInfo config = getConfig(BdcSdqEnum.Q, bdcXmDO.getQxdm(), bdcSdqghDO.getRqfwbsm(), JKLX_HY);
        if (Objects.equals(5, config.getPushStyle())) {
            // 海地区特殊处理
            QiGhjcHmDto qiGhjcHmDto = QiFactory.buildGhjcHmParam(bdcSdqghDO, config);
            BdcQlrDO selectiveQlr = getSelectiveQlr(processInsId, bdcSdqghDO.getXhzmc());
            // 获取临时token
            String tempToken = getTempToken(config);
            // 获取真实token
            String token = getToken(tempToken, selectiveQlr.getDh(), config.getTokenUrl());
            QiGhjcHmDataDto res = sendCheckConsnoReq(QiGhjcHmDataDto.class, config.getCheckUrl(), qiGhjcHmDto, token);
            // 姓名一致校验成功
            if (CommonConstantUtils.NANTONG_SQ_CHECK_RETURN_CODE_SUCCESS.equals(res.getRespCode()) ||
                    CommonConstantUtils.SHUCHENG_RETURN_CODE_SUCCESS.equals(res.getRespCode())
            ) {
                List<BdcQlrDO> allYwl = getAllYwl(processInsId);
                Boolean isContainOriginal = allYwl.stream()
                        .anyMatch(item -> StringUtils.equals(res.getCustomerName(), item.getQlrmc()));
                if (!isContainOriginal) {
                    updateCheckInfo(bdcSdqghDO.getId(), false, "户号的老户主和接口访问的老户主名称不一致", res.getCustomerName());
                    return CommonResponse.fail("9999", "户号的老户主和接口访问的老户主名称不一致", 1);
                }
                updateCheckInfo(bdcSdqghDO.getId(), true, "", res.getCustomerName());
                return CommonResponse.ok(0);
            }
            updateCheckInfo(bdcSdqghDO.getId(), false, res.getRespDesc(), res.getCustomerName());
            return CommonResponse.fail("9999", res.getRespDesc(), 1);
        } else {
            return getSqghjcCommonResponse(processInsId, consno, BdcSdqEnum.Q, bdcSdqghDO, bdcXmDO);
        }
    }

    @Override
    public CommonResponse bdcSdqZlsTs(String processInsId) {
        if(StringUtils.isBlank(processInsId)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数：工作流实例ID");
        }
        BdcSdqywQO bdcSdqywQO = new BdcSdqywQO();
        bdcSdqywQO.setGzlslid(processInsId);
        bdcSdqywQO.setYwlx(BdcSdqEnum.S.key());
        bdcSdqywQO.setSfbl(CommonConstantUtils.SF_S_DM);
        List<BdcSdqghDO> bdcSdqghDOList = this.bdcSdqghFeignService.querySdqyw(bdcSdqywQO);
        if(CollectionUtils.isEmpty(bdcSdqghDOList)){
            LOGGER.error("推送登记数据至自来水公司失败，未获取需要推送的水过户信息，工作流实例ID：{}", processInsId);
            return CommonResponse.fail("推送登记数据至自来水公司失败，未获取需要推送的水过户信息，工作流实例ID："+ processInsId);
        }
        ShuiGhTsxxDTO shuiGhTsxxDTO = new ShuiGhTsxxDTO();
        shuiGhTsxxDTO.setCode(bdcSdqghDOList.get(0).getConsno());

        // 组合流程获取房地产权的登记费信息
        int lclx = bdcXmFeignService.makeSureBdcXmLx(processInsId);
        String cqXmid = "";
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            if (lclx == 2 || lclx == 4) {
                for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                    if (ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, bdcXmDTO.getQllx())) {
                        cqXmid = bdcXmDTO.getXmid();
                        break;
                    }
                }
            }else{
                cqXmid = bdcXmDTOList.get(0).getXmid();
            }
        }else{
            LOGGER.error("推送登记数据至自来水公司失败，未获取到不动产项目信息，工作流实例ID：{}", processInsId);
            return CommonResponse.fail("推送登记数据至自来水公司失败，未获取到不动产项目信息,工作流实例ID："+ processInsId);
        }
        List<BdcQlrDO> qlrxxList = this.getAllQrlByXmid(cqXmid).stream().sorted(Comparator.comparing(BdcQlrDO::getSxh)).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(qlrxxList)){
            LOGGER.error("推送登记数据至自来水公司失败，未获取到权利人信息，工作流实例ID：{}", processInsId);
            return CommonResponse.fail("推送登记数据至自来水公司失败，未获取到权利人信息,工作流实例ID："+ processInsId);
        }
        shuiGhTsxxDTO.setNewname(qlrxxList.get(0).getQlrmc());
        shuiGhTsxxDTO.setNewtel(qlrxxList.get(0).getDh());
        shuiGhTsxxDTO.setCardAvatar(qlrxxList.get(0).getZjh());
        // 查询产权证号信息
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(processInsId);
        List<BdcZsDO> bdcZsDOList = this.bdcZsFeignService.listBdcZs(bdcZsQO);
        if(CollectionUtils.isEmpty(bdcZsDOList)){
            LOGGER.error("推送登记数据至自来水公司失败，未获取到新产权证号信息，工作流实例ID：{}", processInsId);
            return CommonResponse.fail("推送登记数据至自来水公司失败，未获取到新产权证号信息,工作流实例ID："+ processInsId);
        }
        shuiGhTsxxDTO.setProperty(bdcZsDOList.get(0).getBdcqzh());

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            LOGGER.error("推送登记数据至自来水公司失败，未获取到不动产项目信息，工作流实例ID：{}", processInsId);
            return CommonResponse.fail("推送登记数据至自来水公司失败，未获取到不动产项目信息,工作流实例ID："+ processInsId);
        }
        // 获取区县代码
        String qxdm = bdcXmDOList.get(0).getQxdm();
        SdqConfig.TsxxInfo tsxxInfo = Optional.ofNullable(sdqConfig.getGstsxx()).map(t->t.get(qxdm)).orElse(null);
        if(Objects.isNull(tsxxInfo)){
            LOGGER.error("推送登记数据至自来水公司失败，未配置推送地址等信息，工作流实例ID：{}", processInsId);
            return CommonResponse.fail("推送登记数据至自来水公司失败，未配置推送地址等信息，工作流实例ID："+ processInsId);
        }
        shuiGhTsxxDTO.setCompanyCode(tsxxInfo.getCompanycode());
        shuiGhTsxxDTO.setChannelCode(tsxxInfo.getChannelcode());
        shuiGhTsxxDTO.setBusinessTypeCode(tsxxInfo.getBusinesstypecode());
        shuiGhTsxxDTO.setType(tsxxInfo.getType());
        shuiGhTsxxDTO.setRemark(bdcXmDOList.get(0).getZl());
        // 请求自来水公司接口
        String response = this.sendPostShuiTsxx(tsxxInfo, shuiGhTsxxDTO);
        if(StringUtils.isBlank(response)){
            LOGGER.error("推送登记数据至自来水公司失败，调用自来水公司接口返回值为空，工作流实例ID：{}", processInsId);
            return CommonResponse.fail("推送登记数据至自来水公司失败，调用自来水公司接口返回值为空，工作流实例ID："+ processInsId);
        }
        JSONObject result = JSONObject.parseObject(response);
        if(result.containsKey("code")&& StringUtils.equals(result.getString("code"), "9999")){
            return CommonResponse.ok(result.get("data"));
        }else{
            return CommonResponse.fail("推送登记数据至自来水公司失败，调用自来水公司接口返回值为空，工作流实例ID："+ processInsId);
        }
    }

    // 推送登记数据至自来水公司请求
    private String sendPostShuiTsxx(SdqConfig.TsxxInfo tsxxInfo, ShuiGhTsxxDTO shuiGhTsxxDTO){
        // 时间戳
        long time = System.currentTimeMillis();
        // 随机串
        String nonce = RandomStringUtils.randomNumeric(10);
        // 签名
        String sign = this.generateSign(shuiGhTsxxDTO, tsxxInfo.getSignkey());
        HttpPost httpPost = new HttpPost(tsxxInfo.getTsurl());
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
        httpPost.setHeader("time", String.valueOf(time));
        httpPost.setHeader("nonce", nonce);
        httpPost.setHeader("sign", sign);
        StringEntity entity = new StringEntity(JSONObject.toJSONString(shuiGhTsxxDTO), Charset.forName("UTF-8"));
        entity.setContentType("application/json; charset=utf-8");
        httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
        httpPost.setEntity(entity);
        String response = "";
        try{
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
            httpPost.setConfig(requestConfig);
            response = httpClientService.doPost(httpPost, "UTF-8");
            LOGGER.info("请求水推送信息返回值内容：{}", response);
        }catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }

    /**
     * (1)对请求非空参数，按ASCII码进行从小到大排序，使用key=value&key1=value2的方式拼接成stringA
     * (2)在 stringA 后拼接 key = (平台分配给商户的key) 的字符串 temp 进行 sha1 加密运算，在对字符串转大写
     */
    private String generateSign(ShuiGhTsxxDTO shuiGhTsxxDTO,String signKey){
        StringBuffer stringA = new StringBuffer();
        Map<String, Object> paramMap = JSON.parseObject(JSON.toJSONString(shuiGhTsxxDTO), new TypeReference<Map<String, Object>>() {});
        paramMap = CollectionUtil.sort(paramMap, Comparator.comparing(ASCIIUtil::getASCII));
        Set<Map.Entry<String, Object>> keys = paramMap.entrySet();
        for (Map.Entry<String, Object> key : keys) {
            if (Objects.nonNull(key.getValue())) {
                stringA.append(key.getKey()).append("=").append(key.getValue().toString()).append("&");
            }
        }
        stringA.deleteCharAt(stringA.length() - 1);
        StringBuffer signSb = new StringBuffer().append("stringA=").append(stringA).append("&key=").append(signKey);
        LOGGER.info("加密前的参数为{}", signSb.toString());
        String sha1str = StringUtils.upperCase(Sha1Util.SHA1Encrypt(signSb.toString()));
        LOGGER.info("SHA1加密后的参数为{}", sha1str);
        return sha1str;
    }

    /**
     * @return List<BdcQlrDO>
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 获取工作流实例相关的权利人列表
     * @Date 2022/4/28
     * @Param processInsId 工作流实例id
     **/
    private List<BdcQlrDO> getAllQrl(String processInsId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("gzlslid", processInsId);
        paramMap.put("qlrlb", CommonConstantUtils.QLRLB_QLR);
        return Optional.of(bdcdjMapper.queryQlrList(paramMap)).orElse(new ArrayList<>());
    }


    private List<BdcQlrDO> getAllQrlByXmid(String xmid) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("xmid", xmid);
        paramMap.put("qlrlb", CommonConstantUtils.QLRLB_QLR);
        return Optional.of(bdcdjMapper.queryQlrList(paramMap)).orElse(new ArrayList<>());
    }

    /**
     * @return List<BdcQlrDO>
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 获取工作流实例相关的义务人列表
     * @Date 2022/4/28
     * @Param processInsId 工作流实例id
     **/
    private List<BdcQlrDO> getAllYwl(String processInsId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("gzlslid", processInsId);
        paramMap.put("qlrlb", CommonConstantUtils.QLRLB_YWR);
        return Optional.of(bdcdjMapper.queryQlrList(paramMap)).orElse(new ArrayList<>());
    }

    private List<BdcQlrDO> getAllYwlByXmid(String xmid) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("xmid", xmid);
        paramMap.put("qlrlb", CommonConstantUtils.QLRLB_YWR);
        return Optional.of(bdcdjMapper.queryQlrList(paramMap)).orElse(new ArrayList<>());
    }

    /**
     * @param ywlx 业务类性
     * @param qxdm 区县代码
     * @param dwdm 单位代码
     * @param jklx 接口类型 1：过户 2：核验
     * @return SdqConfig.UnitInfo
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description : 获取用户的配置
     * @Date 2022/4/27
     **/
    private SdqConfig.UnitInfo getConfig(BdcSdqEnum ywlx, String qxdm, String dwdm, String jklx) {
        SdqConfig.UnitInfo unitInfo = new SdqConfig.UnitInfo();
        if (Objects.equals(BdcSdqEnum.S.key(), ywlx.key())) {
            unitInfo = Optional.ofNullable(sdqConfig.getGsdwxx())
                    .map(item -> item.get(qxdm))
                    .map(item -> item.get(dwdm))
                    .orElseThrow(() -> new AppException(ywlx.value() + "的区县/单位代码未配置"));
        } else if (Objects.equals(BdcSdqEnum.Q.key(), ywlx.key())) {
            unitInfo = Optional.ofNullable(sdqConfig.getGqdwxx())
                    .map(item -> item.get(qxdm))
                    .map(item -> item.get(dwdm))
                    .orElseThrow(() -> new AppException(ywlx.value() + "区县/单位代码未配置"));
        }
        if (Objects.isNull(unitInfo)) {
            throw new AppException(ywlx.value() + "区县/单位代码未配置");
        }
        if (StringUtils.isBlank(unitInfo.getCheckUrl()) && JKLX_HY.equals(jklx)) {
            throw new AppException(ywlx.value() + "区县/单位核验地址未配置");
        }
        if (StringUtils.isBlank(unitInfo.getGhUrl()) && JKLX_GH.equals(jklx)) {
            throw new AppException(ywlx.value() + "区县/单位过户地址未配置");
        }
        if (Objects.isNull(unitInfo.getPushStyle())) {
            throw new AppException(ywlx.value() + "区县/单位推送方式未配置");
        }
        return unitInfo;
    }

    /**
     * @return
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 访问校验户号接口，返回实体类
     * @Date 2022/4/28
     * @Param t 泛型T
     * @Param checkUrl 校验户号的地址
     * @Param consno 户号
     **/
    private <T> T sendCheckConsnoReq(Class<T> t, String checkUrl, String consno) {
        Map<String, Object> params = new HashMap<>();
        params.put("consno", consno);
        String responseStr = httpClientUtils.sendPostRequest(checkUrl, params);
        //尝试获取其中的data域做为返回值，实际返回和文档返回不一致,老是这样，尝试做兼容
        JSONObject jsonObject = JSON.parseObject(responseStr);
        String code = jsonObject.getString("code");
        JSONObject data = jsonObject.getJSONObject("data");
        if (Objects.nonNull(code) && Objects.nonNull(data)) {
            return Optional.ofNullable(responseStr)
                    .map(item -> JSON.parseObject(JSON.toJSONString(data), t))
                    .orElseThrow(() -> new AppException("请求户号验证接口错误"));
        }
        return Optional.ofNullable(responseStr)
                .map(item -> JSON.parseObject(responseStr, t))
                .orElseThrow(() -> new AppException("请求户号验证接口错误"));
    }

    /**
     * @return
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 水过户 访问校验户号接口，返回实体类
     * @Date 2022/4/28
     * @Param t 泛型T
     * @Param checkUrl 校验户号的地址
     * @Param consno 户号
     * @Param dwdm 供水单位代码
     **/
    private <T> T sendCheckConsnoReq(Class<T> t, String checkUrl, String consno, String dwdm) {
        Map<String, Object> params = new HashMap<>();
        params.put("electricFeeNum", consno);
        params.put("org_no", dwdm);
        params.put("flag", 1);
        String responseStr = httpClientUtils.sendPostRequest(checkUrl, params);
        LOGGER.info("访问校验户号接口的响应responseStr{}", responseStr);
        //处理接口返回结果
        responseStr = responseStr.substring(1,responseStr.length()-1);
        String str = responseStr.replace("\\","");
        LOGGER.info("访问校验户号接口的响应格式转换str{}", str);
        JSONObject jsonObject = JSON.parseObject(str);
        return Optional.ofNullable(responseStr)
                .map(item -> JSON.parseObject(str, t))
                .orElseThrow(() -> new AppException("请求户号验证接口错误"));
    }
    private <T> T sendCheckConsnoReq(Class<T> t, String checkUrl, QiGhjcHmDto req, String token) {
        Map<String, Object> params = JSON.parseObject(JSON.toJSONString(req), new TypeReference<Map<String, Object>>() {
        });
        params.put("token", token);
        String responseStr = httpClientUtils.sendPostRequest(checkUrl, params);
        JSONObject jsonObject = JSON.parseObject(responseStr);
        //尝试获取其中的data域做为返回值，实际返回和文档返回不一致,老是这样，尝试做兼容
        String code = jsonObject.getString("code");
        JSONObject data = jsonObject.getJSONObject("data");
        if (Objects.nonNull(code) && Objects.nonNull(data)) {
            return Optional.ofNullable(responseStr)
                    .map(item -> JSON.parseObject(JSON.toJSONString(data), t))
                    .orElseThrow(() -> new AppException("请求户号验证接口错误"));
        }
        return Optional.ofNullable(responseStr)
                .map(item -> JSON.parseObject(responseStr, t))
                .orElseThrow(() -> new AppException("请求户号验证接口错误"));
    }


    /**
     * @param processInsId 流程实例id
     * @param filterQlrmc  勾选的权利人名称
     * @return
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 更新过户后的信息
     **/
    private BdcQlrDO getNewInfo(String processInsId, String filterQlrmc, BdcSdqEnum ywlx) {
        if (StringUtils.isEmpty(filterQlrmc)) {
            LOGGER.info("未查询到需要{}过户的新户主名称，该流程实例id为：{}", ywlx.value(), processInsId);
            throw new AppException("未查询到" + ywlx.value() + "需要过户的新户主名称!请检查！");
        }
        return getSelectiveQlr(processInsId, filterQlrmc);
    }

    private BdcQlrDO getOldInfo(String processInsId, String filterYwrmc, BdcSdqEnum ywlx) {
        if (StringUtils.isEmpty(filterYwrmc)) {
            LOGGER.info("未查询到需要{}过户的新户主名称，该流程实例id为：{}", ywlx.value(), processInsId);
            throw new AppException("未查询到" + ywlx.value() + "需要过户的新户主名称!请检查！");
        }
        return getSelectiveYwr(processInsId, filterYwrmc);
    }

    private BdcQlrDO getSelectiveYwr(String processInsId, String filterYwrmc) {
        Map<String, Object> paramMap = new HashMap(2);
        paramMap.put("gzlslid", processInsId);
        paramMap.put("qlrlb", CommonConstantUtils.QLRLB_YWR);
        List<BdcQlrDO> qlrDOList = bdcdjMapper.queryQlrList(paramMap);
        return qlrDOList.stream()
                .filter(item -> StringUtils.equals(filterYwrmc, item.getQlrmc()))
                .findFirst().orElseThrow(() -> new AppException("勾选的户主不是当前所属义务人"));
    }

    /**
     * 证件种类名称
     *
     * @param zjzl
     * @return
     */
    private String converZjzl(Integer zjzl) {
        return bdcZdCache.getFeildValue("BDC_ZD_ZJZL", zjzl, BdcZdZjzlDO.class);
    }

    /**
     * @param processInsId 流程实例id
     * @param filterQlrmc  勾选的权利人名称
     * @return
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 获取勾选的权利人
     **/
    private BdcQlrDO getSelectiveQlr(String processInsId, String filterQlrmc) {
        Map<String, Object> paramMap = new HashMap(2);
        paramMap.put("gzlslid", processInsId);
        paramMap.put("qlrlb", CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> qlrDOList = bdcdjMapper.queryQlrList(paramMap);
        return qlrDOList.stream()
                .filter(item -> StringUtils.equals(filterQlrmc, item.getQlrmc()))
                .findFirst().orElseThrow(() -> new AppException("勾选的户主不是当前所属权利人"));
    }

    /**
     * @return
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 更新附件
     * @Date 2022/4/28
     * @Param bdcSdqghDO 不动产过户的do
     * @Param t 过户请求的参数实体类
     * @Param processInsId 流程实例id
     * @Param clmcs 文件夹名称
     * @Param bdcSdqEnum 业务类型
     **/
    private Map<String, List<ClnrDTO>> getMaterial(String processInsId, String clmcs, BdcSdqEnum bdcSdqEnum) {
        Map<String, List<ClnrDTO>> result = new HashMap<>();
        if (StringUtils.isEmpty(clmcs)) {
            return new HashMap<>();
        }
        List<String> clmcList = Arrays.asList(clmcs.split(","));
        for (String clmc : clmcList) {
            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByClmc(processInsId, clmc);
            if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
                throw new AppException("南通水气过户，配置的附件文件夹不存在，文件夹为：{}" + clmc);
            }
            List<StorageDto> listFile = storageClient.listAllSubsetStorages(bdcSlSjclDOList.get(0).getWjzxid(), "", 1, 1, 0, null);
            List<ClnrDTO> clnrDTOList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(listFile)) {
                for (StorageDto storageDto : listFile) {
                    BaseResultDto baseResultDto = storageClient.downloadBase64(storageDto.getId());
                    ClnrDTO clnrDTO = new ClnrDTO();
                    clnrDTO.setFjmc(storageDto.getName());
                    clnrDTO.setFjnr(baseResultDto.getMsg());
                    clnrDTOList.add(clnrDTO);
                }
            }
            result.put(clmc, clnrDTOList);
        }
        return result;
    }

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //获取文件夹下的身份证照
     * @Date 2022/5/26 9:37
     **/
    private ClnrDTO getsfzz(String processInsId, String clmcs) {
        String clmc = Stream.of(clmcs.split(","))
                .map(String::trim).findFirst().orElseThrow(() -> new AppException("请配置clmcs"));
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByClmc(processInsId, clmc);
        if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
            throw new AppException("收件材料下的没有电子身份证证件");
        }
        List<StorageDto> listFile = storageClient.listAllSubsetStorages(bdcSlSjclDOList.get(0).getWjzxid(), "", 1, 1, 0, null);
        if (CollectionUtils.isNotEmpty(listFile)) {
            MultipartDto download = storageClient.download(listFile.get(0).getId());
            if (download != null) {
                ClnrDTO clnrDTO = new ClnrDTO();
                clnrDTO.setFjmc(listFile.get(0).getName());
                clnrDTO.setFjnr(Base64Utils.encodeByteToBase64Str(download.getData()));
                return clnrDTO;
            }
        }
        throw new AppException("获取身份证电子证件照失败");
    }

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //获取文件夹下的身份证照
     * @Date 2022/5/26 9:37
     **/
    private byte[] getsfzzByte(String processInsId, String clmcs) {
        String clmc = Stream.of(clmcs.split(","))
                .map(String::trim).findFirst().orElseThrow(() -> new AppException("请配置clmcs"));
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByClmc(processInsId, clmc);
        if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
            throw new AppException("收件材料下的没有电子身份证证件");
        }
        List<StorageDto> listFile = storageClient.listAllSubsetStorages(bdcSlSjclDOList.get(0).getWjzxid(), "", 1, 1, 0, null);
        if (CollectionUtils.isNotEmpty(listFile)) {
            MultipartDto multipartDto = storageClient.download(listFile.get(0).getId());
            if (multipartDto != null) {
                return multipartDto.getData();
            }
        }
        throw new AppException("获取身份证电子证件照失败");
    }

    /**
     * @return
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 根据业务类型获取过户号信息并校验
     * @Date 2022/4/28
     * @Param processInsId 工作流实例id
     * @Param ywlx 业务类型
     **/
    private BdcSdqghDO getBdcSdqghAndValid(String processInsId, BdcSdqEnum ywlx) {
        BdcSdqywQO sdqghQueryParam = new BdcSdqywQO();
        sdqghQueryParam.setGzlslid(processInsId);
        sdqghQueryParam.setYwlx(ywlx.key());
        List<BdcSdqghDO> sdqghDOList = bdcSdqghFeignService.querySdqywOrder(sdqghQueryParam);
        // 没有过户信息，不进行过户
        if (CollectionUtils.isEmpty(sdqghDOList)) {
            LOGGER.info("未查询到需要{}过户的户号信息，该流程实例id为：{}", ywlx.value(), processInsId);
            return new BdcSdqghDO();
        }
        BdcSdqghDO bdcSdqghDO = sdqghDOList.get(0);
        if (StringUtils.isBlank(bdcSdqghDO.getConsno())) {
            LOGGER.info("未查询到需要{}过户的户号，该流程实例id为：{}", ywlx.value(), processInsId);
            throw new AppException("未查询到" + ywlx.value() + "需要过户的户号!请检查！");
        }
        if (StringUtils.isEmpty(bdcSdqghDO.getRqfwbsm())) {
            LOGGER.info("未查询到需要{}过户的第三方服务标识，该流程实例id为：{}", ywlx.value(), processInsId);
            throw new AppException("未查询到" + ywlx.value() + "需要过户的第三方服务标识!请检查！");
        }
        return bdcSdqghDO;
    }

    /**
     * 操作过户
     *
     * @param bdcSdqghDO
     * @param reqUrl
     * @param param
     * @param bdcSdqEnum
     * @param clazz
     */
    private <T> T gh(BdcSdqghDO bdcSdqghDO, String reqUrl, Map<String, Object> param, BdcSdqEnum bdcSdqEnum, Class<T> clazz) {
        String responseGh = "";
        try {
            responseGh = httpClientUtils.sendPostRequest(reqUrl, param, bdcSdqEnum.value());
        } catch (Exception e) {
            updateFailRecord(bdcSdqghDO, bdcSdqEnum.key());
            throw new AppException("httpPost请求异常GH");
        }
        if (Objects.isNull(responseGh)) {
            updateFailRecord(bdcSdqghDO, bdcSdqEnum.key());
            throw new AppException("水统过户失败！");
        }
        return JSON.parseObject(responseGh, clazz);
    }

    public <T> T formGh(BdcSdqghDO bdcSdqghDO, String reqUrl, Map<String, Object> param, Map<String, ClnrDTO> files, BdcSdqEnum bdcSdqEnum, Class<T> clazz, String token) {
        String responseGh = "";
        try {
            param.put("token", token);
            Map<String, Object> headers = new HashMap<>();
            headers.put("token", token);
            responseGh = httpClientUtils.sendFormRequestWithFile(reqUrl, param, headers, files);
        } catch (Exception e) {
            LOGGER.info("httpPost请求异常GH,错误信息 : {}",e.getMessage());
            updateFailRecord(bdcSdqghDO, bdcSdqEnum.key());
            throw new AppException("httpPost请求异常GH");
        }
        if (Objects.isNull(responseGh)) {
            updateFailRecord(bdcSdqghDO, bdcSdqEnum.key());
            throw new AppException(bdcSdqEnum.name() + "过户失败！");
        }
        return JSON.parseObject(responseGh, clazz);
    }


    /**
     * @return
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 更新水电气表状态
     * @Date 2022/4/28
     * @Param
     **/
    private void updateSdqgh(BdcSdqghDO bdcSdqghDO, BdcSdqEnum bdcSdqEnum, Boolean isSuccess) {
        if (isSuccess) {
            //更新水电气表状态
            BdcSdqBlztRequestDTO sdqBlztRequestDTO = new BdcSdqBlztRequestDTO();
            sdqBlztRequestDTO.setConsno(bdcSdqghDO.getConsno());
            sdqBlztRequestDTO.setYwlx(bdcSdqEnum.key());
            sdqBlztRequestDTO.setBlzt(3);
            sdqBlztRequestDTO.setSqsj(new Date());
            bdcSdqghFeignService.updateSdqBlzt(sdqBlztRequestDTO);
        } else {
            updateFailRecord(bdcSdqghDO, bdcSdqEnum.key());
            throw new AppException(bdcSdqEnum.value() + "过户失败！");
        }
    }

    /**
     * @return
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 推送失败更新记录
     * @Date 2022/4/26
     * @Param bdcSdqghDO
     * @Param ywlx
     **/
    private void updateFailRecord(BdcSdqghDO bdcSdqghDO, Integer ywlx) {
        BdcSdqBlztRequestDTO sdqBlztRequestDTO = new BdcSdqBlztRequestDTO();
        sdqBlztRequestDTO.setConsno(bdcSdqghDO.getConsno());
        sdqBlztRequestDTO.setYwlx(ywlx);
        sdqBlztRequestDTO.setBlzt(4);
        sdqBlztRequestDTO.setSdqshyj(bdcSdqghDO.getSdqshyj());
        sdqBlztRequestDTO.setSqsj(new Date());
        if (Objects.nonNull(bdcSdqghDO.getTscs())) {
            sdqBlztRequestDTO.setTscs(bdcSdqghDO.getTscs() + 1);
        } else {
            sdqBlztRequestDTO.setTscs(1);
        }
        bdcSdqghFeignService.updateSdqBlzt(sdqBlztRequestDTO);
    }

    /**
     * @return
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 获取不动产项目信息并校验
     * @Date 2022/4/28
     * @Param
     **/
    private BdcXmDO getBdcXmAndValid(String processInsId, BdcSdqEnum ywlx) {
        BdcXmQO bdcXmQo = new BdcXmQO();
        bdcXmQo.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQo);
        ProcessInstanceData processInstance = processInstanceClient.getProcessInstance(processInsId);
        if (Objects.isNull(processInstance)) {
            LOGGER.info("未查询到工作流实例{}的信息，该流程实例id为：{}", ywlx.value(), processInsId);
            throw new AppException("未查询到" + ywlx.value() + "需要过户的工作流实例信息!请检查！");
        }
        if (processInstance.getProcessDefinitionName().contains("按揭")) {
            bdcXmDOList = bdcXmDOList.stream()
                    .filter(item -> Objects.nonNull(item.getQllx()) && (!Arrays.asList(95,37).contains(item.getQllx())))
                    .collect(Collectors.toList());
        }

        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            LOGGER.info("未查询到{}需要过户的项目信息，该流程实例id为：{}", ywlx.value(), processInsId);
            throw new AppException("未查询到" + ywlx.value() + "需要过户的项目信息!请检查！");
        }
        BdcXmDO bdcXmDO = bdcXmDOList.get(0);
        if (StringUtils.isEmpty(bdcXmDO.getBdcqzh())) {
            LOGGER.info("未查询到{}需要过户的不动产权证号，该流程实例id为：{}", ywlx.value(), processInsId);
            //throw new AppException("未查询到" + ywlx.value() + "需要过户的不动产权证号!请检查！");
        }
        return bdcXmDO;
    }


    /**
     * @return
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 获取电子证照信息
     * @Date 2022/4/28
     * @Param
     **/
    private <T extends AbstactFileInfo> T getDzzzxx(BdcXmDO bdcXmDO, Class<T> tClass) {
        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(bdcXmDO.getXmid());
        if (CollectionUtils.isEmpty(bdcZsDOList)) {
            return null;
        }
        BdcZsDO bdcZsDO = bdcZsDOList.get(0);
        if (StringUtils.isBlank(bdcZsDO.getZzbs())) {
            return null;
        }
        T t = null;
        try {
            t = tClass.getConstructor().newInstance();
            t.setFileName(bdcZsDO.getBdcqzh() + CommonConstantUtils.WJHZ_PDF);
            if (StringUtils.isNotBlank(bdcZsDO.getStorageid())) {
                BaseResultDto baseResultDto = storageClient.downloadBase64(bdcZsDO.getStorageid());
                if (null != baseResultDto) {
                    t.setFileData(baseResultDto.getMsg());
                }
            }
            if (StringUtils.isNotBlank(t.getFileData())) {
                t.setFileData(this.eCertificateFeignService.getECertificateContent(bdcZsDO.getZzbs()));
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOGGER.info("获取电子证照的");
            e.printStackTrace();
        }
        return t;
    }
}
