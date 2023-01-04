package cn.gtmap.realestate.etl.service.impl;


import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcGdxxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.etl.convert.ThirdpartyOrderConvert;
import cn.gtmap.realestate.etl.core.dto.DaxxFile;
import cn.gtmap.realestate.etl.core.mapper.exchange.BdcXmMapper;
import cn.gtmap.realestate.etl.core.thirdparty.order.request.QueryAllFileRequest;
import cn.gtmap.realestate.etl.core.thirdparty.order.request.QueryFileInfoRequest;
import cn.gtmap.realestate.etl.core.thirdparty.order.response.Data;
import cn.gtmap.realestate.etl.core.thirdparty.order.response.Img;
import cn.gtmap.realestate.etl.core.thirdparty.order.response.QueryAllFileResponse;
import cn.gtmap.realestate.etl.core.thirdparty.order.response.QueryFileInfoResponse;
import cn.gtmap.realestate.etl.service.PhotoForOrderInterface;
import cn.gtmap.realestate.etl.util.XmlUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2020/06/23,1.0
 * @description 蚌埠交易查询webservice
 */
@Service
public class PhotoForOrderServiceImpl implements PhotoForOrderInterface {

    protected final static Logger log = LoggerFactory.getLogger(PhotoForOrderServiceImpl.class);

    @Value("${third.party.order.ip:http://localhost:8800/realestate-etl/rest/v1.0/third/party/photo/info}")
    private String urlIP;

    @Value("${daxxUrl:}")
    private String daxxUrl;

    @Value("${daxx.file.Url:}")
    private String daxxFileUrl;

    @Autowired
    public XmlUtils xmlUtils;

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    @Qualifier("bdcEntityMapper")
    private EntityMapper entityMapper;

    @Autowired
    private ThirdpartyOrderConvert thirdpartyOrderConvert;

    @Autowired
    private BdcXmMapper bdcXmMapper;


    /**
     * 交易查询受理编号关联的文件信息
     *
     * @param param
     * @return
     */
    @Override
    public String queryAllFileInfo(String param) throws Exception {
        log.info("交易查询受理编号关联的文件信息入参:{}", param);
        String beginTime = "2020-10-01";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = df.parse(beginTime);
        QueryAllFileRequest queryAllFileRequest = (QueryAllFileRequest) XmlUtils.getObjectByXmlStr(param, QueryAllFileRequest.class);
        if (queryAllFileRequest != null && queryAllFileRequest.getParam() != null && "3".equals(queryAllFileRequest.getParam().getFlag())) {
            String slbh = queryAllFileRequest.getParam().getDjbh();
            if (StringUtils.isNoneBlank(slbh)) {
                Example bdcXmExample = new Example(BdcXmDO.class);
                bdcXmExample.createCriteria().andEqualTo("slbh", slbh);
                List<BdcXmDO> bdcXmInfoList = entityMapper.selectByExample(bdcXmExample);
                if (CollectionUtils.isNotEmpty(bdcXmInfoList)) {
                    log.info("查询到相关bdcXm信息");
                    //根据受理时间判断是否为2020-10-01之前的业务，如果是，走查档案的逻辑
                    Date slsj = bdcXmInfoList.get(0).getSlsj();
//                    Date slsj = df.parse(bdcXmInfoList.get(0).getSlsj().toString());
                    //受理时间为国庆前的话，查询档案地址返回给交易
                    if (slsj.getTime() < begin.getTime()) {
                        //用sql查，带修改
                        Map map = new HashMap<>();
                        map.put("gzlslid", bdcXmInfoList.get(0).getGzlslid());
                        map.put("damx", "Ywsltp");
                        log.info("查询sql参数为{}", map.get("gzlslid"));
                        BdcGdxxDO bdcGdxxDO = bdcXmMapper.getBdcGdxxByGzlslid(map);
                        log.info("查询da到结果{}", bdcGdxxDO.toString());
                        if (null != bdcGdxxDO && StringUtils.isNotBlank(bdcGdxxDO.getDaid())) {
                            //调用档案接口，查询档案信息，返回附件地址
                            List<DaxxFile> daxxFileList = postRequest(bdcGdxxDO.getDaid());
                            List<Img> imgList = new ArrayList<>(4);
                            if (CollectionUtils.isNotEmpty(daxxFileList)) {
                                for (DaxxFile daxx : daxxFileList) {
                                    String id = daxx.getId();
                                    String mc = daxx.getName();
                                    String url = daxx.getPath();
                                    String sl = "1";
                                    imgList.add(Img.ImgBuilder.anImg().withId(id).withMc(mc).withSl(sl).withUrl(url).build());
                                }
                            }
                            QueryAllFileResponse queryAllFileResponse = QueryAllFileResponse.QueryAllFileResponseBuilder.aQueryAllFileResponse().withData(Data.DataBuilder.aData().withDjbh(slbh).withImg(imgList).build()).build();
                            return XmlUtils.getXmlStrByObject(queryAllFileResponse, QueryAllFileResponse.class);
                        } else {
                            throw new AppException("档案id为空！请检查数据！");
                        }
                    } else {
                        String gzlslid = bdcXmInfoList.get(0).getGzlslid();
                        List<StorageDto> storageDtos = storageClient.queryMenus("clientId", gzlslid, "", null, null);
                        if (CollectionUtils.isNotEmpty(storageDtos)) {
                            log.info("查询当前流程的附件信息:{}", gzlslid);
                            List<Img> imgList = thirdpartyOrderConvert.getImgListByStorageDtoList(storageDtos);
                            QueryAllFileResponse queryAllFileResponse = QueryAllFileResponse.QueryAllFileResponseBuilder.aQueryAllFileResponse().withData(Data.DataBuilder.aData().withDjbh(slbh).withImg(imgList).build()).build();
                            return XmlUtils.getXmlStrByObject(queryAllFileResponse, QueryAllFileResponse.class);
                        }
                    }
                }
            } else {
                return errorMsg("查询失败:传递无djbh");
            }
        } else {
            return errorMsg("查询失败:传递参数问题" + param);
        }
        return

                errorMsg("查询失败！");

    }

    /**
     * @param ownerId 档案id
     * @throws IOException
     * @author <a href="mailto:haungjian@gtmap.cn">haungjian</a>
     * @description 请求档案接口
     */
    private List<DaxxFile> postRequest(String ownerId) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        List<DaxxFile> daxxFiles = new ArrayList<>();
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        if (StringUtils.isNotBlank(ownerId)) {
            String url = daxxUrl + ownerId;
            log.info("蚌埠请求档案内容1：{}", ownerId);
            log.info("蚌埠请求档案内容：{}", daxxUrl);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
            String respon = restTemplate.postForObject(url, null, String.class);
           /* String respon = "{\n" +
                    "\"empty\":true,\n" +
                    "\"index\":1,\n" +
                    "\"items\":[\n" +
                    "{\n" +
                    "\"XOffice\":false,\n" +
                    "\"alias\":\"\",\n" +
                    "\"content\":\"\",\n" +
                    "\"converttype\":\"\",\n" +
                    "\"extension\":\"jpg\",\n" +
                    "\"fileSize\":239717,\n" +
                    "\"fullText\":\"\",\n" +
                    "\"id\":\"0bef93a3a68d46b58ca2dbee88a6134c\",\n" +
                    "\"image\":true,\n" +
                    "\"kgb\":0.71,\n" +
                    "\"linktype\":\"\",\n" +
                    "\"name\":\"0001.jpg\",\n" +
                    "\"ownerId\":\"2002-3-2178\",\n" +
                    "\"ownerModelName\":\"Wsda\",\n" +
                    "\"path\":\"E:\\\\tomcat_cluster\\\\连云港附件\\\\data\\\\archive\\\\2017DASZH\\\\文书原文\\\\2002\\\\002\\\\0387-2002-002-0003\\\\0001.jpg\",\n" +
                    "\"receiveTime\":\"\",\n" +
                    "\"status\":\"\",\n" +
                    "\"updateTime\":\"2017-11-27\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"XOffice\":false,\n" +
                    "\"alias\":\"\",\n" +
                    "\"content\":\"\",\n" +
                    "\"converttype\":\"\",\n" +
                    "\"extension\":\"jpg\",\n" +
                    "\"fileSize\":399676,\n" +
                    "\"fullText\":\"\",\n" +
                    "\"id\":\"25984a85afc449a3a280e31a6a7e5e6a\",\n" +
                    "\"image\":true,\n" +
                    "\"kgb\":0.71,\n" +
                    "\"linktype\":\"\",\n" +
                    "\"name\":\"0002.jpg\",\n" +
                    "\"ownerId\":\"2002-3-2178\",\n" +
                    "\"ownerModelName\":\"Wsda\",\n" +
                    "\"path\":\"E:\\\\tomcat_cluster\\\\连云港附件\\\\data\\\\archive\\\\2017DASZH\\\\文书原文\\\\2002\\\\002\\\\0387-2002-002-0003\\\\0002.jpg\",\n" +
                    "\"receiveTime\":\"\",\n" +
                    "\"status\":\"\",\n" +
                    "\"updateTime\":\"2017-11-27\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"pageCount\":0,\n" +
                    "\"showPages\":[],\n" +
                    "\"size\":20,\n" +
                    "\"totalCount\":0\n" +
                    "}\n";*/
            if (null != respon) {
                JSONObject jsonObject = JSONObject.parseObject(respon);
                daxxFiles = JSONArray.parseArray(jsonObject.getString("items"), DaxxFile.class);

            }
        }
        log.info("档案内容：{}", daxxFiles.toString());
        return daxxFiles;
    }

    /**
     * 交易查询单个的文件信息
     *
     * @param param
     * @return
     */
    @Override
    public String queryFileInfo(String param) throws Exception {
        log.info("交易查询单个的文件信息:{}", param);
        String beginTime = "2020-10-01";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = df.parse(beginTime);
        QueryFileInfoRequest queryFileInfoRequest = (QueryFileInfoRequest) XmlUtils.getObjectByXmlStr(param, QueryFileInfoRequest.class);
        if (queryFileInfoRequest != null && queryFileInfoRequest.getParam() != null) {
            String fileId = queryFileInfoRequest.getParam().getId();
//            StorageDto storageDto = storageClient.findById(fileId);
            if (StringUtils.isNoneBlank(queryFileInfoRequest.getParam().getDjbh())) {
                Example bdcXmExample = new Example(BdcXmDO.class);
                bdcXmExample.createCriteria().andEqualTo("slbh", queryFileInfoRequest.getParam().getDjbh());
                List<BdcXmDO> bdcXmInfoList = entityMapper.selectByExample(bdcXmExample);
                if (CollectionUtils.isNotEmpty(bdcXmInfoList)) {
                    log.info("查询到相关bdcXm信息");
                    String gzlslid = bdcXmInfoList.get(0).getGzlslid();
                    //获取业务办理时间
                    Date slsj = bdcXmInfoList.get(0).getSlsj();

                    List<Img> imgList = new ArrayList<>(4);
                    //受理时间为国庆前的话，查询档案地址返回给交易
                    if (slsj.getTime() < begin.getTime()) {
                        //说明是国庆上线前的数据，返回档案文件地址给交易处
                        String url = daxxFileUrl + fileId;
                        final Base64.Encoder encoderDaUrl = Base64.getEncoder();
                        final byte[] textByteDaUrl = url.getBytes(StandardCharsets.UTF_8);
                        final String encodedText = encoderDaUrl.encodeToString(textByteDaUrl);
                        imgList.add(Img.ImgBuilder.anImg().withUrl(encodedText).build());
                        QueryFileInfoResponse queryFileInfoResponse = QueryFileInfoResponse.QueryFileInfoResponseBuilder.aQueryFileInfoResponse().withData(Data.DataBuilder.aData().withDjbh(queryFileInfoRequest.getParam().getDjbh()).withMc(queryFileInfoRequest.getParam().getMc()).withImg(imgList).build()).build();
                        return XmlUtils.getXmlStrByObject(queryFileInfoResponse, QueryFileInfoResponse.class);

                    } else {

                        List<StorageDto> storageDtos = storageClient.queryMenus("clientId", gzlslid, "", null, null);
                        if (CollectionUtils.isNotEmpty(storageDtos)) {
                            StorageDto storageDto = storageDtos.stream().filter(storageDtoTemp -> storageDtoTemp.getId().equals(fileId)).findAny().get();
//                            List<Img> imgList = new ArrayList<>(4);
                            if (storageDto != null && CollectionUtils.isNotEmpty(storageDto.getChildren())) {
                                for (StorageDto childrenStorageDto : storageDto.getChildren()) {
                                    String id = childrenStorageDto.getId();
                                    String url = urlIP + "?fileId=" + id;
                                    final Base64.Encoder encoder = Base64.getEncoder();
                                    final byte[] textByte = url.getBytes(StandardCharsets.UTF_8);
                                    final String encodedText = encoder.encodeToString(textByte);
                                    imgList.add(Img.ImgBuilder.anImg().withUrl(encodedText).build());
                                }
                            } else {
                                log.info("未匹配到该文件夹的信息:{}", fileId);
                            }
                            QueryFileInfoResponse queryFileInfoResponse = QueryFileInfoResponse.QueryFileInfoResponseBuilder.aQueryFileInfoResponse().withData(Data.DataBuilder.aData().withDjbh(queryFileInfoRequest.getParam().getDjbh()).withMc(queryFileInfoRequest.getParam().getMc()).withImg(imgList).build()).build();
                            return XmlUtils.getXmlStrByObject(queryFileInfoResponse, QueryFileInfoResponse.class);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 文件下载
     *
     * @param fileId
     * @return
     */
    @Override
    public String fileDownload(String fileId) {
        BaseResultDto baseResultDto = storageClient.downloadBase64(fileId);
        if (baseResultDto != null && BaseResultDto.BaseResultCode.SECUCCESS.intValue() == baseResultDto.getCode()) {
            String base64Str = baseResultDto.getMsg();
            return dealFileBase64XmlStr(fileId, base64Str);
        }
        return null;
    }


    private String dealFileBase64XmlStr(String fileName, String fileBase64Str) {
        String soapXml = "<?xml version='1.0' ?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soap:Body><Car name=\"@name\"><BNZE xsi:type=\"xsi:base64Binary\">@base64Str</BNZE></Car></soap:Body></soap:Envelope>";
        soapXml = soapXml.replace("@name", fileName);
        soapXml = soapXml.replace("@base64Str", fileBase64Str);
        return soapXml;
    }

    /**
     * 处理错误信息
     *
     * @param msg msg
     * @return xml 错误信息
     */
    private String errorMsg(String msg) {
        return "<data><errormessage>" + msg + "</errormessage></data>";
    }

}
