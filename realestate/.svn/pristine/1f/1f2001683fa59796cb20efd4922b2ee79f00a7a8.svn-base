package cn.gtmap.realestate.exchange.service.impl.inf.standard;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlYqFjxxDO;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/10/20
 * @description 互联网签名包交互类
 */
@Service(value = "esignServiceImpl")
public class EsignServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsignServiceImpl.class);

    /**
     * 创建签署流程接口地址
     */
    @Value("${esign.signFlowsCreate:http://192.168.10.152:8083/hlw-currency/rest/yq/v1/signFlows/create}")
    private String signFlowsCreateUrl;
    /**
     * 文件下载接口地址
     */
    @Value("${esign.signFlowsDownloadUrl:http://192.168.10.152:8083/hlw-currency/rest/yq/v1/signFlows/download}")
    private String signFlowsDownloadUrl;
    @Autowired
    private BdcUploadFileUtils bdcUploadFileUtils;


    @Autowired
    private HttpClientService httpClientService;


    /**
     * 签署流程创建，推送文件和签署信息
     *
     * @param jsonObject jsonObject
     * @return jsonObject
     * @Date 2022/10/21
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public Object signFlowsCreate(JSONObject jsonObject) {
        if (Objects.isNull(jsonObject)) {
            throw new MissingArgumentException("参数不可全为空！");
        }

        Map<String, JSONObject> fileParams = (Map<String, JSONObject>) jsonObject.get("fileParams");
        Map<String, String> otherParams = (Map<String, String>) jsonObject.get("otherParams");
        Map<String, String> headerParams = new HashMap<>();
        LOGGER.info("otherParams：{}", otherParams.toString());
//        LOGGER.info("fileParams：{}", fileParams.toString());
        LOGGER.info("签署流程创建地址：{}", signFlowsCreateUrl);

        String resultWjsc = uploadFile(signFlowsCreateUrl, fileParams, otherParams, headerParams);
        JSONObject jsonObject1 = JSON.parseObject(resultWjsc);
        LOGGER.info("签署流程创建返回：{}", jsonObject1);
        return jsonObject1;
    }


    /**
     * 使用httpclint 发送文件，如果不传输文件，直接设置fileParams=null，
     * 如果不设置请求头参数，直接设置headerParams=null，就可以进行普通参数的POST请求了
     *
     * @param url          请求路径
     * @param fileParams   文件参数
     * @param otherParams  其他字符串参数
     * @param headerParams 请求头参数
     * @return
     */
    public static String uploadFile(String url, Map<String, JSONObject> fileParams, Map<String, String> otherParams, Map<String, String> headerParams) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            //设置请求头
            if (headerParams != null && headerParams.size() > 0) {
                for (Map.Entry<String, String> e : headerParams.entrySet()) {
                    String value = e.getValue();
                    String key = e.getKey();
                    if (StringUtils.isNotBlank(value)) {
                        httpPost.setHeader(key, value);
                    }
                }
            }
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(Charset.forName("utf-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//加上此行代码解决返回中文乱码问题
            //    文件传输http请求头(multipart/form-data)
            if (fileParams != null && fileParams.size() > 0) {
                for (Map.Entry<String, JSONObject> e : fileParams.entrySet()) {
                    String fileParamName = e.getKey();
                    JSONObject object = e.getValue();
                    MultipartFile file = Base64Utils.base64ToMultipart(CommonConstantUtils.BASE64_QZ_PDF + object.get("bytes").toString());
//                    MultipartFile file = Base64Utils.base64ToMultipart("data:application/pdf;base64," + object.get("bytes"));
//                    MultipartFile file = JSONObject.parseObject(e.getValue(),MultipartFile.class);
                    if (file != null) {
//                        String fileName = file.getOriginalFilename();
                        String fileName = fileParamName;
                        // 文件流
                        builder.addBinaryBody("file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);
                        //builder.addBinaryBody(fileParamName, file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);
                    }
                }
            }
            //    字节传输http请求头(application/json)
            ContentType contentType = ContentType.create("application/json", Charset.forName("UTF-8"));
            if (otherParams != null && otherParams.size() > 0) {
                Map requestJson = new HashMap<>();
                Map<String, String> requestMap = new HashMap<>();

                requestJson.put("data", otherParams);
                requestMap.put("requestJson", JSON.toJSONString(requestJson));
                for (Map.Entry<String, String> e : requestMap.entrySet()) {
                    String value = e.getValue();
                    if (StringUtils.isNotBlank(value)) {
                        builder.addTextBody(e.getKey(), value, contentType);// 类似浏览器表单提交，对应input的name和value
                    }
                }
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, "UTF-8");
                LOGGER.info("通用包返回转换为字符串：{}", result.toString());

            }
        } catch (Exception e) {
            LOGGER.info("异常：{}", e.getMessage());
            e.printStackTrace();
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    LOGGER.info("异常111：{}", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 签署文件下载接口
     *
     * @param lsh 流水号  fjid  附件id
     * @return
     * @Date 2022/10/22
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public void esignDownlod(String lsh, BdcSlYqFjxxDO bdcSlYqFjxxDO) {
        LOGGER.info("lsh:{},fjid:{}", lsh, bdcSlYqFjxxDO.getWjzxid());
        if (StringUtils.isBlank(lsh) || StringUtils.isBlank(bdcSlYqFjxxDO.getWjzxid())) {
            throw new MissingArgumentException("lsh和fjid不能都为空！");
        }

        String fileUrl = signFlowsDownloadUrl + "?lsh=" + lsh + "&fjid=" + bdcSlYqFjxxDO.getWjzxid();
        LOGGER.info("下载地址：{}", fileUrl);
        try {
            MultipartFile multipartFile = Base64Utils.createFileItem(new URL(fileUrl), "pdf");
            LOGGER.info("下载multipartFile：{}", multipartFile.toString());
            BdcPdfDTO bdcPdfDTO = new BdcPdfDTO();
            bdcPdfDTO.setFoldName(bdcSlYqFjxxDO.getWjjmc());
            bdcPdfDTO.setGzlslid(bdcSlYqFjxxDO.getGzlslid());
            bdcPdfDTO.setFileSuffix(CommonConstantUtils.WJHZ_PDF);
            String fjmc = bdcSlYqFjxxDO.getId();
            if (StringUtils.isNotBlank(bdcSlYqFjxxDO.getFjmc())) {
                fjmc = bdcSlYqFjxxDO.getFjmc().substring(0, bdcSlYqFjxxDO.getFjmc().indexOf("."));
            }
            bdcPdfDTO.setPdffjmc(fjmc);
            bdcUploadFileUtils.upload(bdcPdfDTO, multipartFile);

        } catch (Exception e) {
            LOGGER.info("下载签署文件异常,下载文件地址：" + fileUrl + "     异常信息：" + e.getMessage());
            throw new AppException("下载签署文件失败");
        }
    }

    private MultipartFile createFileItemconvertInputStreamToMultipartFile(InputStream inputStream, String fileName) throws IOException {
        FileItem item = null;
        OutputStream os = null;
        try {
            FileItemFactory factory = new DiskFileItemFactory(16, null);
            String textFieldName = "uploadfile";
            item = factory.createItem(textFieldName, ContentType.APPLICATION_OCTET_STREAM.toString(), false, fileName);
            os = item.getOutputStream();

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException("文件下载失败", e);
        } finally {
            if (null != os) {
                os.close();
            }
        }
        return new CommonsMultipartFile(item);
    }


}
