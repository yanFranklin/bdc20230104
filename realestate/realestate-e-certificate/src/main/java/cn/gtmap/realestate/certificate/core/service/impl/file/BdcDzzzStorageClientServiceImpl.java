package cn.gtmap.realestate.certificate.core.service.impl.file;

import cn.gtmap.realestate.certificate.core.model.storage.MultipartDto;
import cn.gtmap.realestate.certificate.core.model.storage.StorageDto;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzHttpService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzStorageClientService;
import cn.gtmap.realestate.certificate.util.BdcDzzzPdfUtil;
import cn.gtmap.realestate.certificate.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

@Service
public class BdcDzzzStorageClientServiceImpl implements BdcDzzzStorageClientService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BdcDzzzHttpService bdcDzzzHttpService;

    @Override
    public List<StorageDto> listAllSubsetStorages(String id, String name, Integer enabled, Integer type) {
        Map<String, Object> map = new HashMap();
        map.put("id", id);
        map.put("name", name);
        map.put("enabled", enabled);
        map.put("type", type);
        // 执行get请求
        String response = "";
        try {
            logger.info("大云文件中心获取根目录下文件，请求地址 {}", BdcDzzzPdfUtil.STORAGE_SERVER_URL + "/rest/v1/storages/all/subset");
            logger.info("大云文件中心获取根目录下文件，请求参数 {}", JSON.toJSONString(map));
            byte[] result = bdcDzzzHttpService.doGet(BdcDzzzPdfUtil.STORAGE_SERVER_URL + "/rest/v1/storages/all/subset?" + mapToUrlParam(map));
            logger.info("大云文件中心获取根目录下文件，请求结果 {}", StringUtils.toEncodedString(result, Charsets.UTF_8));
            if (null != result) {
                response =  StringUtils.toEncodedString(result, Charsets.UTF_8);
            }
        } catch (IOException e) {
            logger.error("StorageClientServiceImpl:listAllSubsetStorages",e);
        }

        return paseResponse(response);
    }

    @Override
    public List<StorageDto> listAllRootStorages(String clientId, String spaceId, String owner, String name, Integer enabled, Integer type) {
        Map<String, Object> map = new HashMap();
        map.put("clientId", clientId);
        map.put("spaceId", spaceId);
        map.put("owner", owner);
        map.put("name", name);
        map.put("enabled", enabled);
        map.put("type", type);
        // 执行get请求
        String response = "";
        try {
            byte[] result = bdcDzzzHttpService.doGet(BdcDzzzPdfUtil.STORAGE_SERVER_URL + "/rest/v1/storages/all/root?" + mapToUrlParam(map));
            if (null != result) {
                response =  StringUtils.toEncodedString(result, Charsets.UTF_8);
            }
        } catch (IOException e) {
            logger.error("StorageClientServiceImpl:listAllRootStorages",e);
        }

        return paseResponse(response);
    }

    private List<StorageDto> paseResponse(String response){
        List<StorageDto> storageDtoList = new ArrayList<>();
        if (StringUtils.isNotBlank(response)) {
            JSONArray jsonArray = JSONObject.parseArray(response);
            if (CollectionUtils.isNotEmpty(jsonArray)) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    StorageDto storageDto = jsonArray.getObject(i, StorageDto.class);
                    storageDtoList.add(storageDto);
                }
            }
        }
        return storageDtoList;
    }

    @Override
    public boolean deleteStorages(List<String> ids) {
        StorageHttpDelete httpDelete = new StorageHttpDelete(BdcDzzzPdfUtil.STORAGE_SERVER_URL + "/rest/v1/storages");
        StringEntity entity = new StringEntity(JSONObject.toJSONString(ids), Charset.forName(StringUtil.ENCODING_UTF8));
        httpDelete.setHeader("Content-Type","application/json; charset=UTF-8");
        httpDelete.setEntity(entity);

        String response = "";
        try {
            response = bdcDzzzHttpService.doPost(httpDelete, StringUtil.ENCODING_UTF8);
        } catch (IOException e) {
            logger.error("StorageClientServiceImpl:deleteStorages",e);
        }

        if (StringUtils.equals(response,"true")){
            return true;
        }
        return false;
    }

    @Override
    public StorageDto multipartUpload(MultipartDto multipartDto) {
        StorageDto storageDto = null;
        try {
            logger.info("大云文件中心上传附件，请求地址 {}", BdcDzzzPdfUtil.STORAGE_SERVER_URL + "/rest/v1/storages/multipart/upload");
            logger.info("大云文件中心上传附件，请求参数 {}", JSON.toJSONString(multipartDto));
            byte[] result = bdcDzzzHttpService.doPostWithString(BdcDzzzPdfUtil.STORAGE_SERVER_URL + "/rest/v1/storages/multipart/upload"
                    , JSON.toJSONString(multipartDto), "application/json; charset=UTF-8", StringUtil.ENCODING_UTF8);
            logger.info("大云文件中心上传附件，请求结果 {}", StringUtils.toEncodedString(result, Charsets.UTF_8));
            if (null != result) {
                String response = StringUtils.toEncodedString(result, Charsets.UTF_8);
                if (StringUtils.isNotBlank(response)) {
                    storageDto = JSONObject.parseObject(response, StorageDto.class);
                }
            }
        } catch (IOException e) {
            logger.error("StorageClientServiceImpl:multipartUpload",e);
        }

        return storageDto;
    }

    @Override
    public StorageDto replaceUpload(String id, MultipartDto multipartDto){
        StorageDto storageDto = null;
        try {
            logger.info("大云文件中心更新附件，请求地址 {}", BdcDzzzPdfUtil.STORAGE_SERVER_URL + "/rest/v1/storages/replace/upload");
            logger.info("大云文件中心更新附件，请求参数 {}", JSON.toJSONString(multipartDto));
            byte[] result = bdcDzzzHttpService.doPostWithString(BdcDzzzPdfUtil.STORAGE_SERVER_URL + "/rest/v1/storages/replace/upload?id=" + id
                    , JSON.toJSONString(multipartDto), "application/json; charset=UTF-8", StringUtil.ENCODING_UTF8);
            logger.info("大云文件中心更新附件，请求结果 {}", StringUtils.toEncodedString(result, Charsets.UTF_8));
            if (null != result) {
                String response = StringUtils.toEncodedString(result, Charsets.UTF_8);
                if (StringUtils.isNotBlank(response)) {
                    storageDto = JSONObject.parseObject(response, StorageDto.class);
                }
            }
        } catch (IOException e) {
            logger.error("StorageClientServiceImpl:replaceUpload",e);
        }
        return storageDto;
    }

    @Override
    public StorageDto createRootFolder(String clientId, String spaceId, String name, String owner) {
        StorageDto storageDto = null;

        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("clientId",clientId));
        parameters.add(new BasicNameValuePair("spaceId", spaceId));
        parameters.add(new BasicNameValuePair("owner", owner));
        parameters.add(new BasicNameValuePair("name", name));

        try {
            logger.info("大云文件中心创建根目录，请求地址 {}", BdcDzzzPdfUtil.STORAGE_SERVER_URL + "/rest/v1/storages/root/folder");
            logger.info("大云文件中心创建根目录，请求参数 {}", JSON.toJSONString(parameters));
            byte[] result = bdcDzzzHttpService.doPost(BdcDzzzPdfUtil.STORAGE_SERVER_URL + "/rest/v1/storages/root/folder"
                    , parameters, StringUtil.ENCODING_UTF8);
            logger.info("大云文件中心创建根目录，请求结果 {}", StringUtils.toEncodedString(result, Charsets.UTF_8));
            if (null != result) {
                String response = StringUtils.toEncodedString(result, Charsets.UTF_8);
                if (StringUtils.isNotBlank(response)) {
                    storageDto = JSONObject.parseObject(response, StorageDto.class);
                }
            }
        } catch (IOException e) {
            logger.error("StorageClientServiceImpl:createRootFolder",e);
        }

        return storageDto;
    }

    @Override
    public StorageDto createFolder(String clientId, String spaceId, String nodeId, String name, String owner) {
        StorageDto storageDto = null;

        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("clientId",clientId));
        parameters.add(new BasicNameValuePair("spaceId", spaceId));
        parameters.add(new BasicNameValuePair("owner", owner));
        parameters.add(new BasicNameValuePair("name", name));
        parameters.add(new BasicNameValuePair("nodeId", nodeId));

        try {
            logger.info("大云文件中心创建文件夹，请求地址 {}", BdcDzzzPdfUtil.STORAGE_SERVER_URL + "/rest/v1/storages/folder");
            logger.info("大云文件中心创建文件夹，请求参数 {}", JSON.toJSONString(parameters));
            byte[] result = bdcDzzzHttpService.doPost(BdcDzzzPdfUtil.STORAGE_SERVER_URL + "/rest/v1/storages/folder"
                    , parameters, StringUtil.ENCODING_UTF8);
            logger.info("大云文件中心创建文件夹，请求结果 {}", StringUtils.toEncodedString(result, Charsets.UTF_8));
            if (null != result) {
                String response = StringUtils.toEncodedString(result, Charsets.UTF_8);
                if (StringUtils.isNotBlank(response)) {
                    storageDto = JSONObject.parseObject(response, StorageDto.class);
                }
            }
        } catch (IOException e) {
            logger.error("StorageClientServiceImpl:createFolder",e);
        }

        return storageDto;
    }

    /**
     * @param paramMap
     * @return java.lang.String
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 将Map 转成 URL 后传递的参数结构
     */
    private String mapToUrlParam(Map paramMap) {
        return appendMap(paramMap, "&");
    }

    /**
     * @param paramMap, split
     * @return java.lang.String
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description
     */
    private String appendMap(Map paramMap, String split) {
        if (MapUtils.isNotEmpty(paramMap)) {
            StringBuilder paramSb = new StringBuilder("");
            Iterator<Map.Entry<String, Object>> entrySetIter = paramMap.entrySet().iterator();
            while (entrySetIter.hasNext()) {
                Map.Entry<String, Object> entry = entrySetIter.next();
                if (entry.getValue() != null) {
                    String key = entry.getKey();
                    String value = entry.getValue().toString();
                    paramSb.append(key + "=" + value + split);
                }
            }
            String param = paramSb.toString();
            if (param.endsWith(split)) {
                param = param.substring(0, param.length() - 1);
            }
            return param;
        }
        return "";
    }
}
