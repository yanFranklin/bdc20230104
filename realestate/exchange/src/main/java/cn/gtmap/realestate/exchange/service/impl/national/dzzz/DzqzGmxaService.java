package cn.gtmap.realestate.exchange.service.impl.national.dzzz;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.reponse.GmxaDzzzDownloadReponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.reponse.GmxaDzzzKeySignReponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.reponse.GmxaDzzzUploadReponse;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

/**
 * 北京国脉信安签章util
 *
 * @author wangyinghao
 */
@Component
@Slf4j
public class DzqzGmxaService {
    @Autowired
    HttpClientService httpClientService;
    /**
     * 用户唯一标识
     */
    @Value("${gmxa.url:}")
    private String url;

    /**
     * 用户唯一标识
     */
    @Value("${gmxa.userid:}")
    private String userId;

    /**
     * 业务系统id
     */
    @Value("${gmxa.siteid:}")
    private String siteId;

    /**
     * 组织
     */
    @Value("${gmxa.deptid:}")
    private String deptId;

    /**
     * 印章编码
     */
    @Value("${gmxa.sealcode:}")
    private String sealCode;

    /**
     * 印章编码
     */
    @Value("${gmxa.sealid:}")
    private String sealId;

    /**
     * 印章名称
     */
    @Value("${gmxa.sealname:}")
    private String sealName;

    /**
     * 用章人
     */
    @Value("${gmxa.outuserid:}")
    private String outUserId;

    /**
     * 签章关键字
     */
    @Value("${gmxa.keyword:}")
    private String keyword;

    public String getKeySignDzqzGmxa(String filename, String pdfFilePath) throws Exception {
        DzqzGmxaData.DzqzGmxaDataBuilder dzqzGmxaDataBuilder = DzqzGmxaData.DzqzGmxaDataBuilder.anDzqzGmxaDataBuilder();
        dzqzGmxaDataBuilder.withBaseConfig(url,deptId,sealCode,sealId,outUserId,sealName,siteId,userId)
                .withFileType("1")
                .withFileDatas(pdfFilePath);
        DzqzGmxaData dzqzGmxaData = dzqzGmxaDataBuilder.build();
        DzqzGmxaUploadServiceImpl dzqzGmxaUploadService = new DzqzGmxaUploadServiceImpl();
        dzqzGmxaUploadService.init(dzqzGmxaData);
        dzqzGmxaUploadService.buildRequest();
        GmxaDzzzUploadReponse gmxaDzzzUploadReponse = dzqzGmxaUploadService.send();
        if (!dzqzGmxaUploadService.isSuccess()) {
            throw new AppException("上传PDF出错，错误信息为：" + gmxaDzzzUploadReponse.getErrorMsg());
        }
        String layoutPath = "";
        List<GmxaDzzzUploadReponse.FileItem> fileItemList = gmxaDzzzUploadReponse.getData();
        if (CollectionUtils.isNotEmpty(fileItemList)) {
            layoutPath = fileItemList.get(0).getLayoutPath();
        }
        //pdf文件签章
        DzqzGmxaKeySignServiceImpl dzqzGmxaKeySignService = new DzqzGmxaKeySignServiceImpl();
        dzqzGmxaDataBuilder.withTimestamp(System.currentTimeMillis())
                .withDocumentPath(layoutPath)
                .withPositionUnitType("1")
                .withKeyword(keyword)
                .withDocumentName(filename)
                .withTitle(filename)
        ;
        dzqzGmxaKeySignService.init(dzqzGmxaDataBuilder.build());
        dzqzGmxaKeySignService.buildRequest();
        GmxaDzzzKeySignReponse gmxaDzzzKeySignReponse = dzqzGmxaKeySignService.send();
        if (!dzqzGmxaKeySignService.isSuccess()) {
            throw new AppException("pdf文件签章，错误信息为：" + gmxaDzzzKeySignReponse.getErrorMsg());
        }
        //签章后的文件地址
        String singedLayout = "";
        if(CollectionUtils.isNotEmpty(gmxaDzzzKeySignReponse.getData())){
            singedLayout = gmxaDzzzKeySignReponse.getData().get(0).getLayoutPath();
        }
        //下载pdf文件
        dzqzGmxaDataBuilder.withDownloadDocumentPath(singedLayout);
        DzqzGmxaDownloadServiceImpl dzqzGmxaDownloadService = new DzqzGmxaDownloadServiceImpl();
        dzqzGmxaDownloadService.init(dzqzGmxaDataBuilder.build());
        dzqzGmxaDownloadService.buildRequest();
        GmxaDzzzDownloadReponse gmxaDzzzDownloadReponse = dzqzGmxaDownloadService.send();
        if (!dzqzGmxaDownloadService.isSuccess()) {
            throw new AppException("下载pdf文件，错误信息为：" + gmxaDzzzDownloadReponse.getErrorMsg());
        }
        String fileContent = "";
        if(StrUtil.isNotEmpty(gmxaDzzzDownloadReponse.getFileContent())){
            fileContent = gmxaDzzzDownloadReponse.getFileContent();
        }

        //验证pdf文件是否签章
        dzqzGmxaDataBuilder.withFile(fileContent)
                .withTimestamp(System.currentTimeMillis());
        DzqzGmxaVerifyServiceImpl dzqzGmxaVerifyService = new DzqzGmxaVerifyServiceImpl();
        dzqzGmxaVerifyService.init(dzqzGmxaDataBuilder.build());
        dzqzGmxaVerifyService.buildRequest();
        dzqzGmxaVerifyService.send();
        if (!dzqzGmxaVerifyService.isSuccess()) {
            throw new AppException("验证pdf文件是否签章失败");
        }
        return fileContent;
    }

    /**
     * 提交请求
     *
     * @param dto
     * @param url
     */
    public String commonPost(Object dto, String url, String tsxxlx) {
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("", JSON.toJSONString(dto)));
        log.info("---调用{}接口 http请求参数:{},请求地址:{}", tsxxlx, JSONObject.toJSONString(parameters), url);
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(dto), "utf-8");
        stringEntity.setContentEncoding(CharEncoding.UTF_8);
        stringEntity.setContentType("application/json");
        httpPost.setEntity(stringEntity);
        //httpPost.setHeader("unitCode", "LandResourcesBureau");
        httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
        String response = "";
        try {
            response = httpClientService.doPost(httpPost, "UTF-8");
            log.info("---调用{}接口请求成功,响应结果:{}", tsxxlx, response);
            return response;
        } catch (Exception e) {
            log.error("---调用{}接口异常:{},请求url:{},请求param:{}", tsxxlx, url, JSONObject.toJSONString(dto), e);
            throw new AppException("httpPost请求异常");
        }
    }

    /**
     * @param url
     * @param tsxxlx
     * @return
     */
    public String commonGet(String url, String tsxxlx) {
        log.info("---调用{}接口 ,请求地址:{}", tsxxlx, url);
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("unitCode", "LandResourcesBureau");
        httpGet.setHeader("Content-Type", "application/json; charset=UTF-8");
        String response = "";
        try {
            response = httpClientService.doGet(httpGet);
            log.info("---调用{}接口请求成功,响应结果:{}", tsxxlx, response);
            return response;
        } catch (Exception e) {
            log.error("---调用{}接口异常:{},请求url:{}", tsxxlx, url, e);
            throw new AppException("httpPost请求异常");
        }
    }

    /**
     * @param url
     * @param tsxxlx
     * @return
     */
    public InputStream sreramGet(String url, String tsxxlx) {
        log.info("---调用{}接口 ,请求地址:{}", tsxxlx, url);
        InputStream inputStream;
        try {
            inputStream = httpClientService.doGetReturnStream(url);
            log.info("---调用{}接口请求成功,响应结果:{}", tsxxlx, inputStream);
            return inputStream;
        } catch (Exception e) {
            log.error("---调用{}接口异常:{},请求url:{}", tsxxlx, url, e);
            throw new AppException("http请求异常");
        }
    }
}
