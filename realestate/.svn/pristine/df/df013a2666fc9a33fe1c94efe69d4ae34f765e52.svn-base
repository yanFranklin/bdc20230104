package cn.gtmap.realestate.exchange.service.impl.inf.hefei;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.fjsc.response.HefeiDianFjscResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.fjts.request.HefeiDianFjtsRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.fjts.response.HefeiDianFjtsResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.request.HefeiDianSqghRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.request.HefeiDianSqghRequestData;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.response.HefeiDianSqghResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.request.HefeiRanqiSqghRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.request.HefeiWnRanqiSqghRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.request.RanqiSqghCommonRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.response.HefeiRanqiSqghResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.response.HefeiWnRanqiSqghResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.response.RanqiSqghCommonResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.fjts.response.HefeiShuiFjtsResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.shuifeicx.request.HefeiShuifeicxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.shuifeicx.response.HefeiShuifeicxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.sqgh.request.HefeiShuiSqghRequestTransferPerson;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.sqgh.request.NewHefeiShuiSqghRequestTransferPerson;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.sqgh.response.HefeiShuiSqghResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.sqgh.response.NewHefeiShuiSqghResponseDTO;
import cn.gtmap.realestate.common.core.enums.BdcSdqRqfwbsmEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.util.SocketUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-20
 * @description 合肥水、电、气相关服务
 */
@Service
public class SdqServiceImpl {

    protected static Logger LOGGER = LoggerFactory.getLogger(SdqServiceImpl.class);

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    // 电 申请过户
    private static final String BEANNAME_DIAN_SQGH = "dian_sqgh";
    // 电 附件推送
    private static final String BEANNAME_DIAN_FJTS = "dian_fjts";
    // 电 附件删除
    private static final String BEANNAME_DIAN_FJSC = "dian_fjsc";
    // 燃气 申请过户
    private static final String BEANNAME_RANQI_HFRQ_SQGH = "ranqi_hf_sqgh";
    // 皖能燃气 申请过户
    private static final String BEANNAME_RANQI_WNRQ_SQGH = "ranqi_wn_sqgh";

    private static final String BEANNAME_SHUI_FJTS = "shui_fjts";

    private static final String BEANNAME_SHUI_SQGH = "shui_sqgh";

    private static final String NEW_BEANNAME_SHUI_FJTS = "new_shui_fjts";

    private static final String NEW_BEANNAME_SHUI_SQGH = "new_shui_sqgh";

    // 水查询 请求主机地址
    @Value("${shui.socket.host:}")
    private String socketHost;

    // 水查询 请求端口号
    @Value("${shui.socket.port:0}")
    private Integer socketPort;

    // 水查询 请求超时设置
    @Value("${shui.socket.timeout:30000}")
    private Integer socketTimeOut;


    /**
     * @param commonRequestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.response.HefeiRanqiSqghResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 燃气申请过户
     */
    public RanqiSqghCommonResponseDTO ranqiSqgh(RanqiSqghCommonRequestDTO commonRequestDTO) {
        RanqiSqghCommonResponseDTO ranqiSqghCommonResponseDTO = new RanqiSqghCommonResponseDTO();
        BeanUtils.copyProperties(commonRequestDTO, ranqiSqghCommonResponseDTO);
        if (StrUtil.isBlank(commonRequestDTO.getRqfwbsm())
                || commonRequestDTO.getRqfwbsm().equals(BdcSdqRqfwbsmEnum.HFRQ.key())
        ) {
            HefeiRanqiSqghRequestDTO requestDTO = JSON.parseObject(commonRequestDTO.getRequestContent(), HefeiRanqiSqghRequestDTO.class);
            // 需要根据 requestDTO中的附件ID 下载文档中心的图片，转成base64码
            // toUserSfidimg 新户主身份证照片
            if (StringUtils.isNotBlank(requestDTO.getToUserSfidimg())) {
                String toUserImgBase64Code = wjzxIdToBase64Code(requestDTO.getToUserSfidimg());
                requestDTO.setToUserSfidimg(toUserImgBase64Code);
            } else {
                throw new AppException("新户主身份证照片附件不存在");
            }
            // hetongImg 合同图片
            if (StringUtils.isNotBlank(requestDTO.getHetongimg())) {
                String hetongBase64Code = wjzxIdToBase64Code(requestDTO.getHetongimg());
                requestDTO.setHetongimg(hetongBase64Code);
            } else {
                throw new AppException("合同图片附件不存在");
            }
            LOGGER.info("合肥燃气推送办理气业务，入参：{}", JSON.toJSONString(requestDTO));
            HefeiRanqiSqghResponseDTO request = exchangeBeanRequestService.request(BEANNAME_RANQI_HFRQ_SQGH, requestDTO, HefeiRanqiSqghResponseDTO.class);
            ranqiSqghCommonResponseDTO.setResponseContent(JSON.toJSONString(request));
        } else if (StrUtil.isBlank(commonRequestDTO.getRqfwbsm())
                || commonRequestDTO.getRqfwbsm().equals(BdcSdqRqfwbsmEnum.WNRQ.key())
        ) {
            HefeiWnRanqiSqghRequestDTO requestDTO = JSON.parseObject(commonRequestDTO.getRequestContent(), HefeiWnRanqiSqghRequestDTO.class);
            LOGGER.info("皖能燃气推送办理气业务，入参：{}", JSON.toJSONString(requestDTO));
            HefeiWnRanqiSqghResponseDTO request = exchangeBeanRequestService.request(BEANNAME_RANQI_WNRQ_SQGH,
                    requestDTO,
                    HefeiWnRanqiSqghResponseDTO.class
            );
            ranqiSqghCommonResponseDTO.setResponseContent(JSON.toJSONString(request));
        }
        return ranqiSqghCommonResponseDTO;
    }

    /**
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.response.HefeiDianSqghResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 电申请过户
     */
    public HefeiDianSqghResponseDTO dianSqgh(HefeiDianSqghRequestDTO requestDTO) {
        // 过户信息
        HefeiDianSqghRequestData sqghData = requestDTO.getSqghData();
        String slbh = requestDTO.getSlbh();
        String xmid = requestDTO.getXmid();
        // 先调用附件删除接口 否则重复上传 会导致电接收到的附件重复
        dianFjsc(sqghData.getAppNo(), slbh, xmid);
        List<HefeiDianFjtsRequestDTO> fjList = requestDTO.getFjList();
        // 过户附件不能少于5张（身份证2张，房产证2张，登记簿1张）
        if (fjList.size() < 5) {
            throw new AppException("电过户附件不能少于5张（身份证2张，房产证2张，登记簿1张）");
        }
        LOGGER.info("appNo:{} 开始处理电过户申请附件材料，长度:{}", sqghData.getAppNo(), fjList.size());
        // 循环推送附件
        if (CollectionUtils.isNotEmpty(fjList)) {
            for (int i = 0; i < fjList.size(); i++) {
                HefeiDianFjtsRequestDTO fjtsRequestDTO = fjList.get(i);
                HefeiDianFjtsResponseDTO hefeiDianFjtsResponseDTO = dianFjts(fjtsRequestDTO, slbh, xmid);
                LOGGER.info("appNo：{} 推送附件，附件类型：{},响应结果：{}", sqghData.getAppNo(), fjtsRequestDTO.getAttachType(), JSONObject.toJSONString(hefeiDianFjtsResponseDTO));
                if (!"0000".equals(hefeiDianFjtsResponseDTO.getResultCode())) {
                    throw new AppException("电过户请求上传附件失败，附件类型：" + fjtsRequestDTO.getAttachType());
                }
            }
        }
        LOGGER.info("appNo:{} 结束推送附件材料，开始请求过户申请", sqghData.getAppNo());
        // 推送过户信息
        JSONObject sqghJson = JSONObject.parseObject(JSONObject.toJSONString(sqghData));
        sqghJson.put("slbh", slbh);
        sqghJson.put("xmid", xmid);
        HefeiDianSqghResponseDTO hefeiDianSqghResponseDTO =
                exchangeBeanRequestService.request(BEANNAME_DIAN_SQGH, sqghJson, HefeiDianSqghResponseDTO.class);
        LOGGER.info("appNo:{} 过户申请结束，响应结果:{}", sqghData.getAppNo(), JSONObject.toJSONString(hefeiDianSqghResponseDTO));
        return hefeiDianSqghResponseDTO;
    }

    /**
     * @param appNo
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.fjsc.response.HefeiDianFjscResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 电的附件删除
     */
    private HefeiDianFjscResponseDTO dianFjsc(String appNo, String slbh, String xmid) {
        try {
            JSONObject requestDTO = new JSONObject();
            requestDTO.put("appNo", appNo);
            requestDTO.put("slbh", slbh);
            requestDTO.put("xmid", xmid);
            LOGGER.info("请求删除附件，appNo：{}", appNo);
            HefeiDianFjscResponseDTO responseDTO = exchangeBeanRequestService.request(BEANNAME_DIAN_FJSC, requestDTO, HefeiDianFjscResponseDTO.class);
            LOGGER.info("请求删除附件，appNo：{},响应结果：{}", appNo, JSONObject.toJSONString(responseDTO));
            return responseDTO;
        } catch (Exception e) {
            LOGGER.error("电附件接口异常：", e);
        }
        return null;
    }


    /**
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.fjts.response.HefeiDianFjtsResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 电 附件推送
     */
    public HefeiDianFjtsResponseDTO dianFjts(HefeiDianFjtsRequestDTO requestDTO, String slbh, String xmid) {
        if (CheckParameter.checkAllParameter(requestDTO)) {
            String wjzxId = requestDTO.getAttachFile();
            // baseCode
            String baseCode = wjzxIdToBase64Code(wjzxId);
            requestDTO.setAttachFile(baseCode);
            JSONObject requestJson = JSONObject.parseObject(JSONObject.toJSONString(requestDTO));
            requestJson.put("slbh", slbh);
            requestJson.put("xmid", xmid);
            HefeiDianFjtsResponseDTO fjtsResponseDTO = exchangeBeanRequestService.request(BEANNAME_DIAN_FJTS, requestJson, HefeiDianFjtsResponseDTO.class);
            if ("0000".equals(fjtsResponseDTO.getResultCode())) {
                return fjtsResponseDTO;
            } else {
                throw new AppException("电过户附件请求接口异常");
            }
        } else {
            throw new AppException("电过户附件参数错误：" + JSONObject.toJSONString(requestDTO));
        }
    }

    /**
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.sqgh.response.HefeiShuiSqghResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 水 申请过户接口
     */
    public HefeiShuiSqghResponseDTO shuiSqgh(HefeiShuiSqghRequestTransferPerson requestDTO) {
        dealPersonFjts(requestDTO);
        // 默认状态 20
        requestDTO.setState("20");
        requestDTO.setTypeUplode("方式三");
        // 请求过户接口
        HefeiShuiSqghResponseDTO response = exchangeBeanRequestService.request(BEANNAME_SHUI_SQGH, requestDTO, HefeiShuiSqghResponseDTO.class);
        return response;
    }

    /**
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.sqgh.response.NewHefeiShuiSqghResponseDTO
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 新 水 申请过户接口
     */
    public NewHefeiShuiSqghResponseDTO newShuiSqgh(HefeiShuiSqghRequestTransferPerson requestDTO) {
        NewHefeiShuiSqghRequestTransferPerson newHefeiShuiSqghRequestTransferPerson = newDealPersonFjts(requestDTO);
        NewHefeiShuiSqghResponseDTO response = exchangeBeanRequestService.request(NEW_BEANNAME_SHUI_SQGH, newHefeiShuiSqghRequestTransferPerson, NewHefeiShuiSqghResponseDTO.class);
        return response;
    }


    /**
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.shuifeicx.response.HefeiShuifeicxResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description socket 方式 查询应缴水费
     */
    public HefeiShuifeicxResponseDTO shuifeiCx(HefeiShuifeicxRequestDTO requestDTO) {
        HefeiShuifeicxResponseDTO hefeiShuifeicxResponseDTO = new HefeiShuifeicxResponseDTO();
        // 发送socket
        sendSocket(requestDTO.getYhdm(), hefeiShuifeicxResponseDTO);
        return hefeiShuifeicxResponseDTO;
    }

    /**
     * @param yhdm
     * @param responseDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description socket请求水费查询
     */
    private void sendSocket(String yhdm, HefeiShuifeicxResponseDTO responseDTO) {
        Socket request = null;
        OutputStream requestOp = null;
        InputStream requestInp = null;
        try {
            request = new Socket(socketHost, socketPort);
            request.setSoTimeout(socketTimeOut); //设置读取超时时间10s
            requestOp = request.getOutputStream();
            requestInp = request.getInputStream();
            StringBuilder sb = new StringBuilder();
            // 1.设置请求消息
            // 1.1包长度 5个字节
            sb.append("00068");
            // 1.2包头 55个字节
            //0006800060                   OF0000010003        930384    X5201709940
            sb.append("00060                   OF0000010003        930384    X");
            // 1.3包内容 15 个字节 (3字节交易码 10字节用户代码 2字节月份数)
            sb.append("520");//单用户缴费结果查询
            String meterCode = StringUtils.rightPad(yhdm, 10, " ");//右边用空格补全10位
            sb.append(meterCode);//用户代码
            // 1.4包结束符 0x0a
            sb.append("0x0a");
            // 2.发送消息
            LOGGER.info("水费查询请求参数：{}", sb.toString());
            SocketUtil.writeStr2Stream(sb.toString(), requestOp);
            // 3.读取返回消息
            String result = SocketUtil.readStrFromStream(requestInp);
            // 异常情况
//            String result = "0007800060                   OF0000010003        930384    X123123                 0x0a";
            // 正常情况
//            String result = "0020100060                   OF0000010003        930384    X5200041027499   付义志                                                      广西路与福州路交口徽贵苑9栋1单元5楼管井上2表503室           0000000000";
            LOGGER.info("水费查询返回结果：{}", result);
            if (StringUtils.isNotBlank(result)) {
                // 去掉 包长度(5)+包头（55）供60 个字符 和最后 四个 结束符（0x0a）
                if (result.length() > 60) {
                    // 包长5个字符
                    String packLen = result.substring(0, 5);
                    // 如果包长为 223 说明为 正常数据返回
                    if (Integer.parseInt(packLen) >= 200) {
                        // 3位交易码
                        responseDTO.setJym(StringUtils.deleteWhitespace(bSubstring(result, 60, 63)));
                        // 3位响应码
                        responseDTO.setXym(StringUtils.deleteWhitespace(bSubstring(result, 63, 66)));
                        // 10位用户代码
                        responseDTO.setYhdm(StringUtils.deleteWhitespace(bSubstring(result, 66, 76)));
                        // 60位用户姓名
                        responseDTO.setYhmc(StringUtils.deleteWhitespace(bSubstring(result, 76, 136)));
                        // 60位 用户地址
                        responseDTO.setYhdz(StringUtils.deleteWhitespace(bSubstring(result, 136, 196)));
                        if (result.length() >= 206) {
                            // 10位 预存余额
                            responseDTO.setYcye(StringUtils.deleteWhitespace(bSubstring(result, 196, 206)));
                        }
                        if (result.length() >= 216) {
                            // 10位 应缴金额
                            responseDTO.setYjje(StringUtils.deleteWhitespace(bSubstring(result, 206, 216)));
                        }
                        if (result.length() >= 218) {
                            // 2位 欠费月数
                            responseDTO.setQfys(StringUtils.deleteWhitespace(bSubstring(result, 216, 218)));
                        }
                        if (result.length() >= 228) {
                            // 10位 滞纳金
                            responseDTO.setZnj(StringUtils.deleteWhitespace(bSubstring(result, 218, 228)));
                        }
                        LOGGER.info("水查询实体结果：{}", JSONObject.toJSONString(responseDTO));
                    } else if (result.length() >= 83) {
                        // 如果包长 为 78  说明为 异常返回
                        String errorMsg = StringUtils.deleteWhitespace(bSubstring(result, 63, 83));
                        throw new AppException("水查询接口socket返回异常，异常信息：" + errorMsg);
                    }
                }
            }
        } catch (SocketException e) {
            LOGGER.error("水查询接口异常", e);
            throw new AppException("水查询接口异常");
        } catch (UnknownHostException e) {
            LOGGER.error("水查询接口异常", e);
            throw new AppException("水查询接口异常");
        } catch (IOException e) {
            LOGGER.error("水查询接口异常", e);
            throw new AppException("水查询接口异常");
        } finally {
            if (request != null) {
                try {
                    request.close();
                } catch (IOException e) {
                    LOGGER.error("流关闭异常");
                }
            }
            if (requestOp != null) {
                try {
                    requestOp.close();
                } catch (IOException e) {
                    LOGGER.error("流关闭异常");
                }
            }
            if (requestInp != null) {
                try {
                    requestInp.close();
                } catch (IOException e) {
                    LOGGER.error("流关闭异常");
                }
            }
        }
    }

    /**
     * @param wjzxId 多个用,隔开
     * @return java.lang.String 多个用,隔开
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 水费推送附件 返回地址
     */
    private String newShuiFjts(String wjzxId, HefeiShuiSqghRequestTransferPerson person) {
        StringBuilder path = new StringBuilder("");
        String[] wjzxIdArr = wjzxId.split(",");
        if (wjzxIdArr.length > 0) {
            for (String tempFolderId : wjzxIdArr) {
                // 文件中心ID 为  文件夹ID，需获取文件夹下图片文件
                List<StorageDto> imgList
                        = storageClient.listAllSubsetStorages(tempFolderId, null, null, null, 0, null);
                if (CollectionUtils.isNotEmpty(imgList)) {
                    String tempWjzxId = imgList.get(0).getId();
                    MultipartDto dto = storageClient.download(tempWjzxId);
                    if (dto != null) {
                        InputStream inputStream = new ByteArrayInputStream(dto.getData());
                        MultipartFile file = null;
                        try {
                            LOGGER.info("水附件类型：{}", dto.getContentType());
                            file = new MockMultipartFile(dto.getOriginalFilename(), dto.getOriginalFilename(),
                                    ContentType.APPLICATION_FORM_URLENCODED.toString(), inputStream);
                        } catch (IOException e) {
                            LOGGER.error("文件中心下载图片转换MultipartFile异常", e);
                        }
                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("file", file);
                        paramMap.put("slbh", person.getSlbh());
                        paramMap.put("xmid", person.getXmid());
                        HefeiShuiFjtsResponseDTO response = exchangeBeanRequestService.request(NEW_BEANNAME_SHUI_FJTS, paramMap, HefeiShuiFjtsResponseDTO.class);
                        LOGGER.info("水附件接口返回：{}", JSONObject.toJSONString(response));
                        if (response != null && StringUtils.isNotBlank(response.getData())) {
                            path.append(JSON.parseObject(response.getData()).getString("url")).append(",");
                        } else {
                            throw new AppException("水附件推送接口响应错误：" + JSONObject.toJSONString(response));
                        }
                    }
                }
            }
        }
        String pathStr = path.toString();
        if (pathStr.endsWith(",")) {
            pathStr = pathStr.substring(0, pathStr.length() - 1);
        }
        return pathStr;
    }

    /**
     * @param person
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 新 水 过户前处理福建
     */
    private NewHefeiShuiSqghRequestTransferPerson newDealPersonFjts(HefeiShuiSqghRequestTransferPerson person) {
        //组织入参
        NewHefeiShuiSqghRequestTransferPerson newHefeiShuiSqghRequestTransferPerson = new NewHefeiShuiSqghRequestTransferPerson();
        newHefeiShuiSqghRequestTransferPerson.setSlbh(person.getSlbh());
        newHefeiShuiSqghRequestTransferPerson.setReason(person.getReson());
        newHefeiShuiSqghRequestTransferPerson.setOutId(person.getAcceptNumber());
        newHefeiShuiSqghRequestTransferPerson.setCertType(person.getNewTypeCertificates());
        newHefeiShuiSqghRequestTransferPerson.setCustomerName(person.getOldName());
        newHefeiShuiSqghRequestTransferPerson.setCustomerId(person.getCusno());
        newHefeiShuiSqghRequestTransferPerson.setContactPhone(person.getPhone());
        newHefeiShuiSqghRequestTransferPerson.setCustomerAddress(person.getAddress());
        newHefeiShuiSqghRequestTransferPerson.setNewCustomerName(person.getNewName());
        newHefeiShuiSqghRequestTransferPerson.setNewIdcard(person.getCertificateNo());

        newHefeiShuiSqghRequestTransferPerson.setBusinessType("JMHGH");
        newHefeiShuiSqghRequestTransferPerson.setType("2");
        newHefeiShuiSqghRequestTransferPerson.setDataSource("5");
        JSONObject attactmentInfo = new JSONObject();
        // 户主身份证正面路径
        // 买卖双方身份证复印件路径
        if (StringUtils.isNotBlank(person.getDoubleIdCardPath())) {
            String wjzxId = person.getDoubleIdCardPath();
            attactmentInfo.put("0-0", newShuiFjts(wjzxId, person));
        }
        // 买卖双方身份证复印件路径
        if (StringUtils.isNotBlank(person.getDoubleIdCardPath1())) {
            String wjzxId = person.getDoubleIdCardPath1();
            attactmentInfo.put("0-1", newShuiFjts(wjzxId, person));
        }
        if (StringUtils.isNotBlank(person.getIdCardPath())) {
            String wjzxId = person.getIdCardPath();
            // 上传附件 保存 附件地址
            attactmentInfo.put("1-0", newShuiFjts(wjzxId, person));
        }
        // 户主身份证反面路径
        if (StringUtils.isNotBlank(person.getIdCardPath1())) {
            String wjzxId = person.getIdCardPath1();
            attactmentInfo.put("1-1", newShuiFjts(wjzxId, person));
        }

        // 受理单路径
        if (StringUtils.isNotBlank(person.getServiceHandlingForm())) {
            String wjzxId = person.getServiceHandlingForm();
            attactmentInfo.put("2-0", newShuiFjts(wjzxId, person));
        }
        // 合同路径
        if (StringUtils.isNotBlank(person.getContract())) {
            String wjzxId = person.getContract();
            attactmentInfo.put("3-0", newShuiFjts(wjzxId, person));
        }

        // 户主房产证路径
//        if(StringUtils.isNotBlank(person.getPropertyOwnershipPath())){
//            String wjzxId = person.getPropertyOwnershipPath();
//            attactmentInfo.put("4-0",newShuiFjts(wjzxId,person));
//        }
        newHefeiShuiSqghRequestTransferPerson.setAttachment(attactmentInfo.toJSONString());

        return newHefeiShuiSqghRequestTransferPerson;
    }


    /**
     * @param person
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 水 过户前处理福建
     */
    private void dealPersonFjts(HefeiShuiSqghRequestTransferPerson person) {
        // 户主身份证正面路径
        if (StringUtils.isNotBlank(person.getIdCardPath())) {
            String wjzxId = person.getIdCardPath();
            // 上传附件 保存 附件地址
            person.setIdCardPath(shuiFjts(wjzxId, person));
        }
        // 户主身份证反面路径
        if (StringUtils.isNotBlank(person.getIdCardPath1())) {
            String wjzxId = person.getIdCardPath1();
            person.setIdCardPath1(shuiFjts(wjzxId, person));
        }
        // 买卖双方身份证复印件路径
        if (StringUtils.isNotBlank(person.getDoubleIdCardPath())) {
            String wjzxId = person.getDoubleIdCardPath();
            person.setDoubleIdCardPath(shuiFjts(wjzxId, person));
        }
        // 买卖双方身份证复印件路径
        if (StringUtils.isNotBlank(person.getDoubleIdCardPath1())) {
            String wjzxId = person.getDoubleIdCardPath1();
            person.setDoubleIdCardPath1(shuiFjts(wjzxId, person));
        }

        // 户主房产证路径
        if (StringUtils.isNotBlank(person.getPropertyOwnershipPath())) {
            String wjzxId = person.getPropertyOwnershipPath();
            person.setPropertyOwnershipPath(shuiFjts(wjzxId, person));
        }

        // 受理单路径
        if (StringUtils.isNotBlank(person.getServiceHandlingForm())) {
            String wjzxId = person.getServiceHandlingForm();
            person.setServiceHandlingForm(shuiFjts(wjzxId, person));
        }
        // 合同路径
        if (StringUtils.isNotBlank(person.getContract())) {
            String wjzxId = person.getContract();
            person.setContract(shuiFjts(wjzxId, person));
        }
    }


    /**
     * @param wjzxId 多个用,隔开
     * @return java.lang.String 多个用,隔开
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 水费推送附件 返回地址
     */
    private String shuiFjts(String wjzxId, HefeiShuiSqghRequestTransferPerson person) {
        StringBuilder path = new StringBuilder("");
        String[] wjzxIdArr = wjzxId.split(",");
        if (wjzxIdArr.length > 0) {
            for (String tempFolderId : wjzxIdArr) {
                // 文件中心ID 为  文件夹ID，需获取文件夹下图片文件
                List<StorageDto> imgList
                        = storageClient.listAllSubsetStorages(tempFolderId, null, null, null, 0, null);
                if (CollectionUtils.isNotEmpty(imgList)) {
                    String tempWjzxId = imgList.get(0).getId();
                    MultipartDto dto = storageClient.download(tempWjzxId);
                    if (dto != null) {
                        InputStream inputStream = new ByteArrayInputStream(dto.getData());
                        MultipartFile file = null;
                        try {
                            file = new MockMultipartFile(dto.getOriginalFilename(), dto.getOriginalFilename(),
                                    ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
                        } catch (IOException e) {
                            LOGGER.error("文件中心下载图片转换MultipartFile异常", e);
                        }
                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("file", file);
                        paramMap.put("slbh", person.getSlbh());
                        paramMap.put("xmid", person.getXmid());
                        HefeiShuiFjtsResponseDTO response = exchangeBeanRequestService.request(BEANNAME_SHUI_FJTS, paramMap, HefeiShuiFjtsResponseDTO.class);
                        if (response != null && StringUtils.isNotBlank(response.getData())) {
                            path.append(response.getData()).append(",");
                        } else {
                            throw new AppException("水附件推送接口响应错误：" + JSONObject.toJSONString(response));
                        }
                    }
                }
            }
        }
        String pathStr = path.toString();
        if (pathStr.endsWith(",")) {
            pathStr = pathStr.substring(0, pathStr.length() - 1);
        }
        return pathStr;
    }


    /**
     * @param wjzxId
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    private String wjzxIdToBase64Code(String wjzxId) {
        if (StringUtils.isNotBlank(wjzxId)) {
            if (wjzxId.length() > 200) {
                return wjzxId;
            }
            // 文件中心ID 为  文件夹ID，需获取文件夹下图片文件
            List<StorageDto> imgList
                    = storageClient.listAllSubsetStorages(wjzxId, null, null, null, 0, null);
            if (CollectionUtils.isNotEmpty(imgList)) {
                String tempWjzxId = imgList.get(0).getId();
                BaseResultDto resultDto = storageClient.downloadBase64(tempWjzxId);
                if (resultDto != null && resultDto.getCode() == 0) {
                    return resultDto.getMsg();
                } else {
                    throw new AppException("根据文档中心ID获取文件Base64位码失败：" + resultDto.getMsg());
                }
            }
        }
        return "";
//        return testBaseCode();
    }

    private static String testBaseCode() {
        StringBuffer str = new StringBuffer("");
        File file = new File("D://basecode.txt");
        try {
            FileReader fr = new FileReader(file);
            int ch = 0;
            while ((ch = fr.read()) != -1) {
                str.append((char) ch + "");
            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File reader出错");
        }
        return str.toString();

    }


    /**
     * @param s
     * @param index
     * @param end
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 将字符串 按字节长度 指定起始 和结束索引 进行截取
     */
    public static String bSubstring(String s, int index, int end) {

        String str = "";
        try {
            //截取长度
            int length = end - index - 1;
            byte[] bytes = s.getBytes("Unicode");

            //计算起始字节索引
            int m = 0; // 表示当前的字节数
            int j = 2; // 要截取的字节数，从第3个字节开始
            for (; j < bytes.length && m < index; j++) {
                // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
                if (j % 2 == 1) {
                    m++; // 在UCS2第二个字节时n加1
                } else {
                    // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
                    if (bytes[j] != 0) {
                        m++;
                    }
                }
            }
            // 如果i为奇数时，处理成偶数
            if (j % 2 == 1) {
                // 该UCS2字符是汉字时，去掉这个截一半的汉字
                // 该UCS2字符是字母或数字，则保留该字符
                if (bytes[j - 1] != 0) {
                    j = j - 1;
                } else {
                    j = j + 1;
                }
            }

            //计算结束字节索引
            int n = 0; // 表示当前的字节数
            int i = 2; // 要截取的字节数，从第3个字节开始
            for (; i < bytes.length && n < length; i++) {
                // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
                if (i % 2 == 1) {
                    n++; // 在UCS2第二个字节时n加1
                } else {
                    // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
                    if (bytes[i + j - 2] != 0) {
                        n++;
                    }
                }
            }
            // 如果i为奇数时，处理成偶数
            if (i % 2 == 1) {
                // 该UCS2字符是汉字时，去掉这个截一半的汉字
                if (bytes[i - 1] != 0) {
                    i = i - 1;
                }// 该UCS2字符是字母或数字，则保留该字符
                else {
                    i = i + 1;
                }
            }
            str = new String(bytes, j, i, "Unicode");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("字符截取失败", e);
        }
        return str;
    }

}
