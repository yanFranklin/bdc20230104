package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcSdqghDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztRequestDTO;
import cn.gtmap.realestate.common.core.enums.BdcSdqEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.init.BdcSdqywQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdqghFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.DianService;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.dto.nantong.dian.request.DianGhFileDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.dian.request.DianGhjgQO;
import cn.gtmap.realestate.exchange.core.dto.nantong.dian.request.DianGhxxDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.dian.request.DianHxxQO;
import cn.gtmap.realestate.exchange.core.dto.nantong.dian.response.DianGhDTo;
import cn.gtmap.realestate.exchange.core.dto.nantong.dian.response.DianHxxDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.dian.response.HxxDataDTO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/6/10
 * @description 电过户
 */
@RestController
@Api(tags = "电相关服务接口")
public class DianController implements DianService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DianController.class);
    @Autowired
    BdcSdqghFeignService bdcSdqghFeignService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcZsFeignService bdcZsFeignService;
    @Autowired
    private ECertificateFeignService eCertificateFeignService;
    @Autowired
    private BdcSlSjclFeignService bdcSlSjclFeignService;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Value("${exchange.dian.hxx.url:}")
    private String dianHxxUrl;
    @Value("${exchange.dian.gh.url:}")
    private String dianGhUrl;
    @Value("${exchange.dian.ghjg.url:}")
    private String dianGhJgUrl;

    /**
     * ## 南通供电过户推送材料，配置文件夹名称后，则推送该文件夹下所有文件.多个文件夹名称用逗号分割
     */
    @Value("${exchange.dian.gh.clmcs:}")
    private String clmcs;

    @Autowired
    UserManagerUtils userManagerUtils;

    /**
     * 根据gzlslid进行过户操作
     *
     * @param processInsId@return
     * @Date 2021/6/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据工作流主键进行电过户操作")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流主键", required = true, dataType = "String", paramType = "query")})
    @DsfLog(logEventName = "根据工作流主键进行电过户操作", dsfFlag = "DIAN", requester = "BDC", responser = "DIAN")
    public void sqElectricGh(@RequestParam(name = "processInsId") String processInsId,
                             @RequestParam(value = "currentUserName",required = false) String currentUserName,
                             @RequestParam(value = "jysfbl", required = false) Boolean jysfbl) {
        LOGGER.info("要过户的gzlslid：{}", processInsId);
        // 根据gzlslid查询电表，是否存在需要过户的数据
        BdcSdqywQO bdcSdqghQo = new BdcSdqywQO();
        if (jysfbl != null && jysfbl) {
            bdcSdqghQo.setSfbl(CommonConstantUtils.SF_S_DM);
        }
        bdcSdqghQo.setGzlslid(processInsId);
        bdcSdqghQo.setYwlx(BdcSdqEnum.D.key());
        List<BdcSdqghDO> sdqghDOList = bdcSdqghFeignService.querySdqywOrder(bdcSdqghQo);
        if (CollectionUtils.isNotEmpty(sdqghDOList)) {
            BdcSdqghDO bdcSdqghDO = sdqghDOList.get(0);
            if (StringUtils.isNotBlank(bdcSdqghDO.getConsno())) {
                String gddwdm = getGddwdm(currentUserName);
                // 用户号去查询原户主信息，拿到姓名，去权利人表查询
                DianHxxQO hxxQo = new DianHxxQO();
                hxxQo.setElectricFeeNum(bdcSdqghDO.getConsno());
                hxxQo.setFlag("1");
                hxxQo.setOrg_no(gddwdm);
                List<NameValuePair> parameters = Lists.newArrayList();
                String json = JSONObject.toJSONString(hxxQo);
                parameters.add(new BasicNameValuePair("", JSON.toJSONString(hxxQo)));
                LOGGER.info("---http请求参数:{},请求地址:{}", JSONObject.toJSONString(parameters), dianHxxUrl);

                HttpPost httpPost = new HttpPost(dianHxxUrl);
                StringEntity stringEntity = new StringEntity(json, "utf-8");
                stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity(stringEntity);
                httpPost.setHeader("unitCode", "LandResourcesBureau");
                httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
                String response = "";
                try {
                    response = httpClientService.doPost(httpPost, "UTF-8");
                } catch (Exception e) {
                    LOGGER.error("---请求异常:{},请求url:{},请求param:{}", dianHxxUrl, parameters, e);
                    throw new AppException("httpPost请求异常");
                }
                LOGGER.info("---请求成功,响应结果:{}", response);
                if (Objects.nonNull(response)) {
                    DianHxxDTO dianHxxDTO = JSON.parseObject(response, DianHxxDTO.class);
                    if (StringUtils.equals(CommonConstantUtils.SUCCESS_CODE_0000, dianHxxDTO.getCode()) && Objects.nonNull(dianHxxDTO.getData()) && StringUtils.isNotBlank(dianHxxDTO.getData().getElectricFeeNum())) {
                        DianGhxxDTO ghxxDTO = new DianGhxxDTO();
                        HxxDataDTO hxxDataDTO = dianHxxDTO.getData();
                        // 获取原用户的证件号和联系方式
                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("gzlslid", processInsId);
                        paramMap.put("qlrlb", 2);
                        List<BdcQlrDO> yqlrDOList = bdcdjMapper.queryQlrList(paramMap);
                        if (CollectionUtils.isNotEmpty(yqlrDOList)) {
                            ghxxDTO.setOriginalUserCard(yqlrDOList.get(0).getZjh());
                            ghxxDTO.setOldMobile(StringUtils.isNotBlank(yqlrDOList.get(0).getDh()) ? yqlrDOList.get(0).getDh() : "无");
                            ghxxDTO.setOriginalUserName(yqlrDOList.get(0).getQlrmc());
                        }
                        ghxxDTO.setElectricFeeNum(hxxDataDTO.getElectricFeeNum());
                        ghxxDTO.setQxno("无");
                        ghxxDTO.setAddress("无");
                        ghxxDTO.setOrg_no(gddwdm);
                        // 查询新用户信息
                        Map<String, Object> xparamMap = new HashMap<>();
                        xparamMap.put("gzlslid", processInsId);
                        xparamMap.put("qlrlb", 1);
                        xparamMap.put("qllxs", Arrays.asList(CommonConstantUtils.QLLX_FDCQ));
                        List<BdcQlrDO> xqlrList = bdcdjMapper.queryQlrList(xparamMap);
                        if (CollectionUtils.isNotEmpty(xqlrList)) {
                            for (BdcQlrDO qlrDO : xqlrList) {
                                if (StringUtils.isNotBlank(qlrDO.getDh())) {
                                    ghxxDTO.setNewUserName(qlrDO.getQlrmc());
                                    ghxxDTO.setNewOriginalUserCard(qlrDO.getZjh());
                                    ghxxDTO.setNewMobile(qlrDO.getDh());
                                }
                            }
                        }
                        // 查询bdcdyh
                        BdcXmQO bdcXmQO = new BdcXmQO();
                        bdcXmQO.setGzlslid(processInsId);
                        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                            String bdcdyh = bdcXmDOList.get(0).getBdcdyh();
                            ghxxDTO.setBdcno(bdcdyh);
                            ghxxDTO.setAddress(bdcXmDOList.get(0).getZl());
                            if (StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() >= 19) {
                                ghxxDTO.setQxno(bdcdyh.substring(0, 19));
                            }
                            ghxxDTO.setContractId(StringUtils.isNotBlank(bdcXmDOList.get(0).getSlbh()) ? bdcXmDOList.get(0).getSlbh() : "无");
                        }
                        // 添加 电子证照信息
                        List<DianGhFileDTO> dianGhFileDTOList = new ArrayList<>();
                        for (BdcXmDO bdcXmDO : bdcXmDOList) {
                            DianGhFileDTO dianGhFileDTO = this.getDzzzxx(bdcXmDO);
                            if (null != dianGhFileDTO && StringUtils.isNotBlank(dianGhFileDTO.getFileData())) {
                                dianGhFileDTOList.add(dianGhFileDTO);
                            }
                        }
                        // 添加 配置文件夹下的所有文件
                        if (StringUtils.isNotBlank(clmcs)) {
                            List<DianGhFileDTO> list = getMaterial(processInsId,clmcs);
                            dianGhFileDTOList.addAll(list);
                        }
                        ghxxDTO.setData(dianGhFileDTOList);

                        // 开始过户操作
                        List<NameValuePair> parametersGh = Lists.newArrayList();
                        parametersGh.add(new BasicNameValuePair("", JSON.toJSONString(ghxxDTO)));
                        LOGGER.info("gzlslid:{},请求过户请求参数:{},请求地址:{}", processInsId, JSONObject.toJSONString(parametersGh), dianGhUrl);
                        HttpPost httpPostGh = new HttpPost(dianGhUrl);
                        StringEntity stringEntityGh = new StringEntity(JSONObject.toJSONString(ghxxDTO), "utf-8");
                        stringEntityGh.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                        httpPostGh.setEntity(stringEntityGh);
                        httpPostGh.setHeader("unitCode", "LandResourcesBureau");
                        httpPostGh.setHeader("Content-Type", "application/json; charset=UTF-8");
                        String responseGh;
                        try {
                            responseGh = httpClientService.doPost(httpPostGh, "UTF-8");
                        } catch (Exception e) {
                            LOGGER.error("gzlslid:{}---GH请求异常:{},请求url:{},请求param:{}", processInsId, dianGhUrl, JSONObject.toJSONString(ghxxDTO), e);
                            throw new AppException("httpPost请求异常GH");
                        }
                        LOGGER.info("---GH请求成功,响应结果:{}", responseGh);
                        if (Objects.nonNull(responseGh)) {
                            DianGhDTo ghDTo = JSON.parseObject(responseGh, DianGhDTo.class);
                            if (StringUtils.equals(CommonConstantUtils.SUCCESS_CODE_0000, ghDTo.getCode())) {
                                // 更新水电气表状态
                                BdcSdqBlztRequestDTO sdqBlztRequestDTO = new BdcSdqBlztRequestDTO();
                                sdqBlztRequestDTO.setConsno(bdcSdqghDO.getConsno());
                                sdqBlztRequestDTO.setYwlx(BdcSdqEnum.D.key());
                                sdqBlztRequestDTO.setSqsj(new Date());
                                sdqBlztRequestDTO.setBlzt(3);
                                bdcSdqghFeignService.updateSdqBlzt(sdqBlztRequestDTO);
                            } else {
                                LOGGER.info("过户申请失败！gzlslid为：{},户号为{}，原因：{}", processInsId, bdcSdqghDO.getConsno(), null != ghDTo?ghDTo.toString():"null");
                                BdcSdqBlztRequestDTO sdqBlztRequestDTO = new BdcSdqBlztRequestDTO();
                                sdqBlztRequestDTO.setConsno(bdcSdqghDO.getConsno());
                                sdqBlztRequestDTO.setYwlx(BdcSdqEnum.D.key());
                                sdqBlztRequestDTO.setSqsj(new Date());
                                sdqBlztRequestDTO.setBlzt(4);
                                sdqBlztRequestDTO.setSdqshyj(ghDTo.getData().getMessage());
                                bdcSdqghFeignService.updateSdqBlzt(sdqBlztRequestDTO);
                                throw new AppException("电力系统过户失败！");
                            }
                        } else {
                            LOGGER.info("过户申请未返回信息！gzlslid为：{},户号为{}", processInsId, bdcSdqghDO.getConsno());
                            throw new AppException("电力系统过户失败！");
                        }
                    } else {
                        LOGGER.info("该户号未获取到原户主信息！gzlslid为：{},户号为{}", processInsId, bdcSdqghDO.getConsno());
                        throw new AppException("电力系统查询失败！");
                    }
                }
            } else {
                LOGGER.info("未查询到需要过户的户号，该流程实例id为：{}", processInsId);
                throw new AppException("未查询到需要过户的户号!请检查！");
            }
        } else {
            LOGGER.info("未查询到需要过户的户号信息，该流程实例id为：{}", processInsId);
            throw new AppException("未查询到需要过户的户号信息!请检查！");
        }
    }

    /**
     * @param processInsId
     * @param currentUserName
     * @return void
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/16 16:34
     * @description 申请电过户（可无户号）
     **/
    @Override
    @DsfLog(logEventName = "申请电过户（可无户号）", dsfFlag = "DIAN", requester = "BDC", responser = "DIAN")
    public void sqElectricGhCanNohh(@RequestParam(name = "processInsId") String processInsId,
                                    @RequestParam(value = "currentUserName", required = false) String currentUserName) {
        BdcSdqywQO bdcSdqghQo = new BdcSdqywQO();
        bdcSdqghQo.setSfbl(CommonConstantUtils.SF_S_DM);
        bdcSdqghQo.setGzlslid(processInsId);
        bdcSdqghQo.setYwlx(BdcSdqEnum.D.key());
        List<BdcSdqghDO> sdqghDOList = bdcSdqghFeignService.querySdqywOrder(bdcSdqghQo);
        if (CollectionUtils.isNotEmpty(sdqghDOList)) {
            BdcSdqghDO bdcSdqghDO = sdqghDOList.get(0);
            String gddwdm = getGddwdm(currentUserName);
            DianGhxxDTO ghxxDTO = new DianGhxxDTO();
            // 获取原用户的证件号和联系方式
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("gzlslid", processInsId);
            paramMap.put("qlrlb", 2);
            List<BdcQlrDO> yqlrDOList = bdcdjMapper.queryQlrList(paramMap);
            if (CollectionUtils.isNotEmpty(yqlrDOList)) {
                ghxxDTO.setOriginalUserCard(yqlrDOList.get(0).getZjh());
                ghxxDTO.setOldMobile(StringUtils.isNotBlank(yqlrDOList.get(0).getDh()) ? yqlrDOList.get(0).getDh() : "无");
                ghxxDTO.setOriginalUserName(yqlrDOList.get(0).getQlrmc());
            }
            ghxxDTO.setElectricFeeNum(bdcSdqghDO.getConsno());
            ghxxDTO.setQxno("无");
            ghxxDTO.setAddress("无");
            ghxxDTO.setOrg_no(gddwdm);
            // 查询新用户信息
            Map<String, Object> xparamMap = new HashMap<>();
            xparamMap.put("gzlslid", processInsId);
            xparamMap.put("qlrlb", 1);
            xparamMap.put("qllxs", Arrays.asList(CommonConstantUtils.QLLX_FDCQ));
            List<BdcQlrDO> xqlrList = bdcdjMapper.queryQlrList(xparamMap);
            if (CollectionUtils.isNotEmpty(xqlrList)) {
                for (BdcQlrDO qlrDO : xqlrList) {
                    if (StringUtils.isNotBlank(qlrDO.getDh())) {
                        ghxxDTO.setNewUserName(qlrDO.getQlrmc());
                        ghxxDTO.setNewOriginalUserCard(qlrDO.getZjh());
                        ghxxDTO.setNewMobile(qlrDO.getDh());
                    }
                }
            }
            // 查询bdcdyh
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(processInsId);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                String bdcdyh = bdcXmDOList.get(0).getBdcdyh();
                ghxxDTO.setBdcno(bdcdyh);
                ghxxDTO.setAddress(bdcXmDOList.get(0).getZl());
                if (StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() >= 19) {
                    ghxxDTO.setQxno(bdcdyh.substring(0, 19));
                }
                ghxxDTO.setContractId(StringUtils.isNotBlank(bdcXmDOList.get(0).getSlbh()) ? bdcXmDOList.get(0).getSlbh() : "无");
            }
            // 添加 电子证照信息
            List<DianGhFileDTO> dianGhFileDTOList = new ArrayList<>();
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                DianGhFileDTO dianGhFileDTO = this.getDzzzxx(bdcXmDO);
                if (null != dianGhFileDTO && StringUtils.isNotBlank(dianGhFileDTO.getFileData())) {
                    dianGhFileDTOList.add(dianGhFileDTO);
                }
            }
            ghxxDTO.setData(dianGhFileDTOList);

            // 开始过户操作
            List<NameValuePair> parametersGh = Lists.newArrayList();
            parametersGh.add(new BasicNameValuePair("", JSON.toJSONString(ghxxDTO)));
            LOGGER.info("gzlslid:{},请求过户请求参数:{},请求地址:{}", processInsId, JSONObject.toJSONString(parametersGh), dianGhUrl);
            HttpPost httpPostGh = new HttpPost(dianGhUrl);
            StringEntity stringEntityGh = new StringEntity(JSONObject.toJSONString(ghxxDTO), "utf-8");
            stringEntityGh.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPostGh.setEntity(stringEntityGh);
            httpPostGh.setHeader("unitCode", "LandResourcesBureau");
            httpPostGh.setHeader("Content-Type", "application/json; charset=UTF-8");
            String responseGh;

            try {
                responseGh = httpClientService.doPost(httpPostGh, "UTF-8");
            } catch (Exception e) {
                LOGGER.error("gzlslid:{}---GH请求异常:{},请求url:{},请求param:{}", processInsId, dianGhUrl, JSONObject.toJSONString(ghxxDTO), e);
                throw new AppException("httpPost请求异常GH");
            }
            LOGGER.info("---GH请求成功,响应结果:{}", responseGh);
            if (Objects.nonNull(responseGh)) {
                DianGhDTo ghDTo = JSON.parseObject(responseGh, DianGhDTo.class);
                if (StringUtils.equals(CommonConstantUtils.SUCCESS_CODE_0000, ghDTo.getCode())) {
                    // 更新电表状态
                    BdcSdqBlztRequestDTO sdqBlztRequestDTO = new BdcSdqBlztRequestDTO();
                    sdqBlztRequestDTO.setConsno(bdcSdqghDO.getConsno());
                    sdqBlztRequestDTO.setGzlslid(processInsId);
                    sdqBlztRequestDTO.setYwlx(BdcSdqEnum.D.key());
                    sdqBlztRequestDTO.setBlzt(3);
                    bdcSdqghFeignService.updateSdqBlzt(sdqBlztRequestDTO);
                } else {
                    LOGGER.info("过户申请失败！gzlslid为：{},户号为{}，原因：{}", processInsId, bdcSdqghDO.getConsno(), ghDTo.toString());
                    throw new AppException("电力系统过户失败！");
                }
            } else {
                LOGGER.info("过户申请未返回信息！gzlslid为：{},户号为{}", processInsId, bdcSdqghDO.getConsno());
                throw new AppException("电力系统过户失败！");
            }
        } else {
            LOGGER.info("未查询到需要过户的户号信息，该流程实例id为：{}", processInsId);
            throw new AppException("未查询到需要过户的户号信息!请检查！");
        }
    }

    /**
     * 获取电子证照信息
     */
    private DianGhFileDTO getDzzzxx(BdcXmDO bdcXmDO){
        List<BdcZsDO> bdcZsDOList = this.bdcZsFeignService.queryBdcZsByXmid(bdcXmDO.getXmid());
        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
            BdcZsDO bdcZsDO = bdcZsDOList.get(0);

            if (StringUtils.isNotBlank(bdcZsDO.getZzbs())) {
                LOGGER.info("电子证照标识：{}", bdcZsDO.getZzbs());
                DianGhFileDTO dianGhFileDTO = new DianGhFileDTO();
                dianGhFileDTO.setFileName(bdcZsDO.getBdcqzh() + CommonConstantUtils.WJHZ_PDF);
                if (StringUtils.isNotBlank(bdcZsDO.getStorageid())) {
                    LOGGER.info("电子证照文件id：{}", bdcZsDO.getStorageid());
                    BaseResultDto baseResultDto = storageClient.downloadBase64(bdcZsDO.getStorageid());
                    if (null != baseResultDto) {
                        LOGGER.info("大云下载电子证照文件不为空：{}", StringUtils.left(baseResultDto.getMsg(), 999));
                        dianGhFileDTO.setFileData(baseResultDto.getMsg());
                    }
                }
                if (StringUtils.isNotBlank(dianGhFileDTO.getFileData())) {
                    dianGhFileDTO.setFileData(this.eCertificateFeignService.getECertificateContent(bdcZsDO.getZzbs()));
                    LOGGER.info("电子证照包下载文件：{}", StringUtils.left(dianGhFileDTO.getFileData(), 999));

                }
                return dianGhFileDTO;
            }
        }
        return null;
    }

    /**
     * @Author  <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @Description 供电推送材料，支持文件夹配置，推送文件夹下所有文件
     * @Param processInsId 流程实例id
     * @Param clmcs 文件夹名称
     * @return list 推送文件
     **/
    private List<DianGhFileDTO> getMaterial(String processInsId, String clmcs) {
        List<DianGhFileDTO> list = new ArrayList<>();
        if (StringUtils.isBlank(clmcs)) {
            return Collections.emptyList();
        }
        List<String> clmcList = Arrays.asList(clmcs.split(","));
        for (String clmc : clmcList){
            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByClmc(processInsId, clmc);
            if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)){
                List<StorageDto> listFile = storageClient.listAllSubsetStorages(bdcSlSjclDOList.get(0).getWjzxid(), "", 1, 1,0,null);
                if (CollectionUtils.isNotEmpty(listFile)){
                    for (StorageDto storageDto : listFile){
                        BaseResultDto baseResultDto = storageClient.downloadBase64(storageDto.getId());
                        DianGhFileDTO fileDTO = new DianGhFileDTO();
                        String fileDate = "";
                        fileDTO.setFileName(storageDto.getName());
                        // 去除base64前缀
                        if (StringUtils.isNotBlank(baseResultDto.getMsg())){
                            fileDate = baseResultDto.getMsg().substring(baseResultDto.getMsg().indexOf(",") + 1);
                        }
                        fileDTO.setFileData(fileDate);
                        list.add(fileDTO);
                    }
                }
            }

        }
        return  list;
    }


    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据用电户号查询电客户信息")
    @DsfLog(logEventName = "根据用电户号查询电客户信息", dsfFlag = "DIAN", requester = "BDC", responser = "DIAN")
    @ApiImplicitParams({@ApiImplicitParam(name = "electricFeeNum", value = "电户号", required = true, dataType = "String", paramType = "query")})
    public Object queryDianYhxx(@RequestParam(name = "electricFeeNum") String electricFeeNum, @RequestParam(value = "currentUserName",required = false) String currentUserName){
        DianHxxQO hxxQo = new DianHxxQO();
        hxxQo.setElectricFeeNum(electricFeeNum);
        hxxQo.setFlag("1");
        LOGGER.info("当前用户名：{}", currentUserName);
        hxxQo.setOrg_no(getGddwdm(currentUserName));
        List<NameValuePair> parameters = Lists.newArrayList();
        String json = JSONObject.toJSONString(hxxQo);
        parameters.add(new BasicNameValuePair("", JSON.toJSONString(hxxQo)));
        LOGGER.info("---根据用电户号查询电客户信息请求参数:{},请求地址:{}", JSONObject.toJSONString(parameters), dianHxxUrl);

        HttpPost httpPost = new HttpPost(dianHxxUrl);
        StringEntity stringEntity = new StringEntity(json, "utf-8");
        stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("unitCode", "LandResourcesBureau");
        httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
        String response = "";
        try {
            response = httpClientService.doPost(httpPost, "UTF-8");
        } catch (Exception e) {
            LOGGER.error("---根据用电户号查询电客户信息请求异常:{},请求url:{},请求param:{}", dianHxxUrl, parameters, e);
            throw new AppException("httpPost请求异常");
        }

        LOGGER.info("---根据用电户号查询电客户信息请求成功,响应结果:{}", response);
        if (Objects.nonNull(response)) {
            DianHxxDTO dianHxxDTO = JSON.parseObject(response, DianHxxDTO.class);
            if (StringUtils.equals(CommonConstantUtils.SUCCESS_CODE_0000, dianHxxDTO.getCode()) && Objects.nonNull(dianHxxDTO.getData()) && StringUtils.isNotBlank(dianHxxDTO.getData().getElectricFeeNum())) {
                return dianHxxDTO.getData();
            }
        } else {
            throw new AppException(ErrorCode.CUSTOM, "调用查询电客户基本信息接口失败，未查询到信息。");
        }
        return null;
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询电过户信息")
    @DsfLog(logEventName = "查询电过户信息", dsfFlag = "DIAN", requester = "BDC", responser = "DIAN")
    @ApiImplicitParams({@ApiImplicitParam(name = "contractId", value = "申请流水号", required = true, dataType = "String", paramType = "query")})
    public Object queryDianGhxx(@RequestParam(name = "contractId") String contractId, @RequestParam(value = "currentUserName",required = false) String currentUserName){
        if (StringUtils.isBlank(contractId)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到申请流水号");
        }

        DianGhjgQO dianGhjgQO = new DianGhjgQO();
        dianGhjgQO.setContractId(contractId);
        dianGhjgQO.setOrg_no(getGddwdm(currentUserName));

        // 发送请求，请求市区exchange接口
        List<NameValuePair> parameters = Lists.newArrayList();
        String json = JSONObject.toJSONString(dianGhjgQO);
        parameters.add(new BasicNameValuePair("", JSON.toJSONString(dianGhjgQO)));
        LOGGER.info("---http请求参数:{},请求地址:{}", JSONObject.toJSONString(parameters), dianGhJgUrl);

        HttpPost httpPost = new HttpPost(dianGhJgUrl);
        StringEntity stringEntity = new StringEntity(json, "utf-8");
        stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("unitCode", "LandResourcesBureau");
        httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
        String response = "";
        try {
            response = httpClientService.doPost(httpPost, "UTF-8");
        } catch (Exception e) {
            LOGGER.error("---请求异常:{},请求url:{},请求param:{}", dianGhJgUrl, parameters, e);
            throw new AppException("httpPost请求异常");
        }
        LOGGER.info("---请求成功,响应结果:{}", response);
        if (Objects.nonNull(response)) {
            DianHxxDTO dianHxxDTO = JSON.parseObject(response, DianHxxDTO.class);
            if (StringUtils.equals(CommonConstantUtils.SUCCESS_CODE_0000, dianHxxDTO.getCode()) && Objects.nonNull(dianHxxDTO.getData())) {
                return dianHxxDTO.getData();
            }
            return response;
        } else {
            throw new AppException(ErrorCode.CUSTOM, "调用查询过户信息结果信息接口失败，未查询到信息。");
        }
    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 获取供电单位代码
      */
    private String getGddwdm(String currentUserName){
        if (StringUtils.isNotBlank(currentUserName)) {
            UserDto userDto = userManagerUtils.getUserByName(currentUserName);
            LOGGER.info("当前用户组织信息：{}", userDto.getOrgRecordList().toString());
            if (CollectionUtils.isEmpty(userDto.getOrgRecordList()) || StringUtils.isBlank(userDto.getOrgRecordList().get(0).getRegionCode())) {
                LOGGER.info("未获取到当前用户信息或组织结构,用户名：" + currentUserName);
                throw new AppException("未获取到当前用户信息或组织结构,用户名：" + currentUserName);
            }
            /**
             * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
             * @description  过户单位代码配置，map结构，key为用户的组织结构代码，value为对应的供电单位编码
             */
            String gddwdm = EnvironmentConfig.getEnvironment().getProperty("gddwdm." + userDto.getOrgRecordList().get(0).getRegionCode());
            if (StringUtils.isBlank(gddwdm)) {
                throw new AppException("未获取到当前用户组织结构对应的供电单位代码："+userDto.getOrgRecordList().get(0).getRegionCode());
            }
            return gddwdm;
        }
        return "";

    }


}
