package cn.gtmap.realestate.common.core.dto.exchange.openapi;

import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkCsDO;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkDO;
import cn.gtmap.realestate.common.core.enums.OpenApiParamConstansEnum;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/10/22 15:13
 * @description TODO
 */
public class BdcOpenApiDetailInfoDTO {

    @ApiModelProperty(value = "apiId")
    private String id;

    @ApiModelProperty(value = "接口地址")
    private String url;

    @ApiModelProperty(value = "接口请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "接口名称")
    private String name;

    @ApiModelProperty(value = "接口描述")
    private String description;

    @ApiModelProperty(value = "接口类型 0:普通简单接口；1：复杂接口")
    private Integer type;

    @ApiModelProperty(value = "配置sql")
    private String sql;

    @ApiModelProperty(value = "应用id")
    private String clientId;

    @ApiModelProperty(value = "接口消费方")
    private String consumer;

    @ApiModelProperty(value = "是否记录日志 0：是;1：否")
    private Integer logFlag;

    @ApiModelProperty(value = "日志记录方式 0：数据库;1：es")
    private Integer logType;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "创建人")
    private String createdBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间" ,example = "2020-10-01 12:18:48")
    private Date updatedTime;

    @ApiModelProperty(value = "修改人")
    private String updatedBy;

    @ApiModelProperty(value = "发布状态 0未发布 1已发布")
    private Integer releaseStatus;

    @ApiModelProperty(value = "rest参数")
    private List<BdcOpenApiParamDTO> restParamList;

    @ApiModelProperty(value = "rest参数json字符串")
    private String restParamJson;

    @ApiModelProperty(value = "query参数")
    private List<BdcOpenApiParamDTO> queryParamList;

    @ApiModelProperty(value = "query参数json字符串")
    private String queryParamJson;

    @ApiModelProperty(value = "请求体参数")
    private List<BdcOpenApiParamDTO> requestBodyParamList;

    @ApiModelProperty(value = "请求体参数json字符串")
    private String requestBodyParamJson;

    @ApiModelProperty(value = "返回参数")
    private List<BdcOpenApiParamDTO> returnTypeParamList;

    @ApiModelProperty(value = "返回参数json字符串")
    private String returnTypeParamJson;

    @ApiModelProperty(value = "返回参数json字符串")
    private String paramJson;

    @ApiModelProperty(value = "是否包装返回参数")
    private Integer packageResponse;

    @ApiModelProperty(value = "是否返回响应情况参数")
    private Integer packageResponseDetail;

    @ApiModelProperty(value = "返回结果是否list")
    private Integer dataIsList;

    private String tokenUrl;

    private String tokenGrantType;

    private String tokenClientSecret;

    private String tokenClientId;

    private String responseHead;

    private String responseDetail;

    @ApiModelProperty(value = "默认参数")
    private List<BdcOpenApiDefaultParamDTO> responseBodyDefaultValueList;

    public List<BdcOpenApiDefaultParamDTO> getResponseBodyDefaultValueList() {
        return responseBodyDefaultValueList;
    }

    public void setResponseBodyDefaultValueList(List<BdcOpenApiDefaultParamDTO> responseBodyDefaultValueList) {
        this.responseBodyDefaultValueList = responseBodyDefaultValueList;
    }

    public String getResponseHead() {
        return responseHead;
    }

    public void setResponseHead(String responseHead) {
        this.responseHead = responseHead;
    }

    public Integer getPackageResponse() {
        return packageResponse;
    }

    public void setPackageResponse(Integer packageResponse) {
        this.packageResponse = packageResponse;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getTokenGrantType() {
        return tokenGrantType;
    }

    public void setTokenGrantType(String tokenGrantType) {
        this.tokenGrantType = tokenGrantType;
    }

    public String getTokenClientSecret() {
        return tokenClientSecret;
    }

    public void setTokenClientSecret(String tokenClientSecret) {
        this.tokenClientSecret = tokenClientSecret;
    }

    public String getTokenClientId() {
        return tokenClientId;
    }

    public void setTokenClientId(String tokenClientId) {
        this.tokenClientId = tokenClientId;
    }

    public Integer getPackageResponseDetail() {
        return packageResponseDetail;
    }

    public void setPackageResponseDetail(Integer packageResponseDetail) {
        this.packageResponseDetail = packageResponseDetail;
    }

    public String getResponseDetail() {
        return responseDetail;
    }

    public void setResponseDetail(String responseDetail) {
        this.responseDetail = responseDetail;
    }

    public Integer getDataIsList() {
        return dataIsList;
    }

    public void setDataIsList(Integer dataIsList) {
        this.dataIsList = dataIsList;
    }

    public void convertDO(BdcDwJkDO bdcDwJkDO, List<BdcDwJkCsDO> bdcDwJkCsDOList){
        this.setId(bdcDwJkDO.getJkid());
        this.setUrl(bdcDwJkDO.getJkdz());
        this.setRequestMethod(bdcDwJkDO.getJkqqfs());
        this.setName(bdcDwJkDO.getJkmc());
        this.setDescription(bdcDwJkDO.getJkms());
        this.setType(bdcDwJkDO.getJklx());
        this.setSql(bdcDwJkDO.getSjkjb());
        this.setClientId(bdcDwJkDO.getYyid());
        this.setConsumer(bdcDwJkDO.getJkxff());
        this.setLogFlag(bdcDwJkDO.getSfjlrz());
        this.setLogType(bdcDwJkDO.getRzjlfs());
        this.setCreatedTime(bdcDwJkDO.getCjsj());
        this.setCreatedBy(bdcDwJkDO.getCjr());
        this.setUpdatedTime(bdcDwJkDO.getXgsj());
        this.setUpdatedBy(bdcDwJkDO.getXgr());
        this.setReleaseStatus(bdcDwJkDO.getFbzt());
        this.setParamJson(bdcDwJkDO.getCssl());
        this.setPackageResponse(bdcDwJkDO.getPackageResponse());
        this.setResponseHead(bdcDwJkDO.getResponseHead());
        this.setPackageResponseDetail(bdcDwJkDO.getPackageResponseDetail());
        this.setResponseDetail(bdcDwJkDO.getResponseDetail());
        this.setDataIsList(bdcDwJkDO.getDataIsList());
        if (StringUtils.isNotBlank(bdcDwJkDO.getResponseBodyDefaultValue())){
            this.setResponseBodyDefaultValueList(JSON.parseArray(bdcDwJkDO.getResponseBodyDefaultValue(),BdcOpenApiDefaultParamDTO.class));
        }


        List<BdcOpenApiParamDTO> paramDTOList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(bdcDwJkCsDOList)){
            Map<String, BdcDwJkCsDO> map = bdcDwJkCsDOList.stream().collect(Collectors.toMap(BdcDwJkCsDO::getCsid, a -> a,(k1, k2)->k1));
            bdcDwJkCsDOList.forEach(bdcDwJkCsDO -> {
                String parentFieldName = "";
                if(!StringUtils.equals("0",bdcDwJkCsDO.getCsfid()) && StringUtils.isNotEmpty(bdcDwJkCsDO.getCsfid())){
                    BdcDwJkCsDO parent = map.get(bdcDwJkCsDO.getCsfid());
                    parentFieldName = Objects.isNull(parent) ? "" : parent.getCsm();
                }
                BdcOpenApiParamDTO bdcOpenApiParamDTO = new BdcOpenApiParamDTO();
                bdcOpenApiParamDTO.setId(bdcDwJkCsDO.getCsid());
                bdcOpenApiParamDTO.setParentId(bdcDwJkCsDO.getCsfid());
                bdcOpenApiParamDTO.setFieldName(bdcDwJkCsDO.getCsm());
                bdcOpenApiParamDTO.setParentFieldName(parentFieldName);
                bdcOpenApiParamDTO.setFieldType(bdcDwJkCsDO.getCszdlx());
                bdcOpenApiParamDTO.setFieldRemark(bdcDwJkCsDO.getCssm());
                bdcOpenApiParamDTO.setParamType(bdcDwJkCsDO.getCslx());
                bdcOpenApiParamDTO.setDefaultValue(bdcDwJkCsDO.getCsmrz());
                bdcOpenApiParamDTO.setLevel(bdcDwJkCsDO.getCscj());
                bdcOpenApiParamDTO.setRequired(bdcDwJkCsDO.getSfbt());
                bdcOpenApiParamDTO.setCszdmc(bdcDwJkCsDO.getCszdmc());
                bdcOpenApiParamDTO.setCspj(bdcDwJkCsDO.getCspj());
                if (StringUtils.isNotBlank(bdcDwJkCsDO.getJkcsext())){
                    bdcOpenApiParamDTO.setChildParamId(JSON.parseObject(bdcDwJkCsDO.getJkcsext()).getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_CURRENT_JKID.getKey()));
                }
                paramDTOList.add(bdcOpenApiParamDTO);
            });
        }

        this.setRestParamList(paramDTOList.stream().filter(x -> x.getParamType().equals(2)).collect(Collectors.toList()));

        this.setRequestBodyParamList(paramDTOList.stream().filter(x -> x.getParamType().equals(0)).collect(Collectors.toList()));

        this.setQueryParamList(paramDTOList.stream().filter(x -> x.getParamType().equals(1)).collect(Collectors.toList()));

        this.setReturnTypeParamList(paramDTOList.stream().filter(x -> x.getParamType().equals(3)).collect(Collectors.toList()));
    }

    public void convertDOWithOutBdcDwJkDO(List<BdcDwJkCsDO> bdcDwJkCsDOList){
        List<BdcOpenApiParamDTO> paramDTOList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(bdcDwJkCsDOList)){
            Map<String, BdcDwJkCsDO> map = bdcDwJkCsDOList.stream().collect(Collectors.toMap(BdcDwJkCsDO::getCsid, a -> a,(k1, k2)->k1));
            bdcDwJkCsDOList.forEach(bdcDwJkCsDO -> {
                String parentFieldName = "";
                if(!StringUtils.equals("0",bdcDwJkCsDO.getCsfid()) && StringUtils.isNotEmpty(bdcDwJkCsDO.getCsfid())){
                    BdcDwJkCsDO parent = map.get(bdcDwJkCsDO.getCsfid());
                    parentFieldName = Objects.isNull(parent) ? "" : parent.getCsm();
                }
                BdcOpenApiParamDTO bdcOpenApiParamDTO = new BdcOpenApiParamDTO();
                bdcOpenApiParamDTO.setId(bdcDwJkCsDO.getCsid());
                bdcOpenApiParamDTO.setParentId(bdcDwJkCsDO.getCsfid());
                bdcOpenApiParamDTO.setFieldName(bdcDwJkCsDO.getCsm());
                bdcOpenApiParamDTO.setParentFieldName(parentFieldName);
                bdcOpenApiParamDTO.setFieldType(bdcDwJkCsDO.getCszdlx());
                bdcOpenApiParamDTO.setFieldRemark(bdcDwJkCsDO.getCssm());
                bdcOpenApiParamDTO.setParamType(bdcDwJkCsDO.getCslx());
                bdcOpenApiParamDTO.setDefaultValue(bdcDwJkCsDO.getCsmrz());
                bdcOpenApiParamDTO.setLevel(bdcDwJkCsDO.getCscj());
                bdcOpenApiParamDTO.setRequired(bdcDwJkCsDO.getSfbt());
                bdcOpenApiParamDTO.setCszdmc(bdcDwJkCsDO.getCszdmc());
                bdcOpenApiParamDTO.setCspj(bdcDwJkCsDO.getCspj());
                if (StringUtils.isNotBlank(bdcDwJkCsDO.getJkcsext())){
                    bdcOpenApiParamDTO.setChildParamId(JSON.parseObject(bdcDwJkCsDO.getJkcsext()).getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_CURRENT_JKID.getKey()));
                }
                paramDTOList.add(bdcOpenApiParamDTO);
            });
        }

        this.setRestParamList(paramDTOList.stream().filter(x -> x.getParamType().equals(2)).collect(Collectors.toList()));

        this.setRequestBodyParamList(paramDTOList.stream().filter(x -> x.getParamType().equals(0)).collect(Collectors.toList()));

        this.setQueryParamList(paramDTOList.stream().filter(x -> x.getParamType().equals(1)).collect(Collectors.toList()));

        this.setReturnTypeParamList(paramDTOList.stream().filter(x -> x.getParamType().equals(3)).collect(Collectors.toList()));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public Integer getLogFlag() {
        return logFlag;
    }

    public void setLogFlag(Integer logFlag) {
        this.logFlag = logFlag;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(Integer releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public List<BdcOpenApiParamDTO> getRestParamList() {
        return restParamList;
    }

    public void setRestParamList(List<BdcOpenApiParamDTO> restParamList) {
        this.restParamList = restParamList;
    }

    public String getRestParamJson() {
        return restParamJson;
    }

    public void setRestParamJson(String restParamJson) {
        this.restParamJson = restParamJson;
    }

    public List<BdcOpenApiParamDTO> getQueryParamList() {
        return queryParamList;
    }

    public void setQueryParamList(List<BdcOpenApiParamDTO> queryParamList) {
        this.queryParamList = queryParamList;
    }

    public String getQueryParamJson() {
        return queryParamJson;
    }

    public void setQueryParamJson(String queryParamJson) {
        this.queryParamJson = queryParamJson;
    }

    public List<BdcOpenApiParamDTO> getRequestBodyParamList() {
        return requestBodyParamList;
    }

    public void setRequestBodyParamList(List<BdcOpenApiParamDTO> requestBodyParamList) {
        this.requestBodyParamList = requestBodyParamList;
    }

    public String getRequestBodyParamJson() {
        return requestBodyParamJson;
    }

    public void setRequestBodyParamJson(String requestBodyParamJson) {
        this.requestBodyParamJson = requestBodyParamJson;
    }

    public List<BdcOpenApiParamDTO> getReturnTypeParamList() {
        return returnTypeParamList;
    }

    public void setReturnTypeParamList(List<BdcOpenApiParamDTO> returnTypeParamList) {
        this.returnTypeParamList = returnTypeParamList;
    }

    public String getReturnTypeParamJson() {
        return returnTypeParamJson;
    }

    public void setReturnTypeParamJson(String returnTypeParamJson) {
        this.returnTypeParamJson = returnTypeParamJson;
    }

    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }


    public static final class BdcOpenApiDetailInfoDTOBuilder {
        private String id;
        private String url;
        private String requestMethod;
        private String name;
        private String description;
        private Integer type;
        private String sql;
        private String clientId;
        private String consumer;
        private Integer logFlag;
        private Integer logType;
        private Date createdTime;
        private String createdBy;
        private Date updatedTime;
        private String updatedBy;
        private Integer releaseStatus;
        private List<BdcOpenApiParamDTO> restParamList;
        private String restParamJson;
        private List<BdcOpenApiParamDTO> queryParamList;
        private String queryParamJson;
        private List<BdcOpenApiParamDTO> requestBodyParamList;
        private String requestBodyParamJson;
        private List<BdcOpenApiParamDTO> returnTypeParamList;
        private String returnTypeParamJson;
        private String paramJson;
        private Integer packageResponse;
        private Integer packageResponseDetail;
        private String tokenUrl;
        private String tokenGrantType;
        private String tokenClientSecret;
        private String tokenClientId;
        private String responseHead;
        private String responseDetail;
        private List<BdcOpenApiDefaultParamDTO> responseBodyDefaultValueList;

        private BdcOpenApiDetailInfoDTOBuilder() {
        }

        public static BdcOpenApiDetailInfoDTOBuilder aBdcOpenApiDetailInfoDTO() {
            return new BdcOpenApiDetailInfoDTOBuilder();
        }

        public BdcOpenApiDetailInfoDTOBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withRequestMethod(String requestMethod) {
            this.requestMethod = requestMethod;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withType(Integer type) {
            this.type = type;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withSql(String sql) {
            this.sql = sql;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withConsumer(String consumer) {
            this.consumer = consumer;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withLogFlag(Integer logFlag) {
            this.logFlag = logFlag;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withLogType(Integer logType) {
            this.logType = logType;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withCreatedTime(Date createdTime) {
            this.createdTime = createdTime;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withCreatedBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withUpdatedTime(Date updatedTime) {
            this.updatedTime = updatedTime;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withReleaseStatus(Integer releaseStatus) {
            this.releaseStatus = releaseStatus;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withRestParamList(List<BdcOpenApiParamDTO> restParamList) {
            this.restParamList = restParamList;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withRestParamJson(String restParamJson) {
            this.restParamJson = restParamJson;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withQueryParamList(List<BdcOpenApiParamDTO> queryParamList) {
            this.queryParamList = queryParamList;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withQueryParamJson(String queryParamJson) {
            this.queryParamJson = queryParamJson;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withRequestBodyParamList(List<BdcOpenApiParamDTO> requestBodyParamList) {
            this.requestBodyParamList = requestBodyParamList;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withRequestBodyParamJson(String requestBodyParamJson) {
            this.requestBodyParamJson = requestBodyParamJson;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withReturnTypeParamList(List<BdcOpenApiParamDTO> returnTypeParamList) {
            this.returnTypeParamList = returnTypeParamList;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withReturnTypeParamJson(String returnTypeParamJson) {
            this.returnTypeParamJson = returnTypeParamJson;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withParamJson(String paramJson) {
            this.paramJson = paramJson;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withPackageResponse(Integer packageResponse) {
            this.packageResponse = packageResponse;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withPackageResponseDetail(Integer packageResponseDetail) {
            this.packageResponseDetail = packageResponseDetail;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withTokenUrl(String tokenUrl) {
            this.tokenUrl = tokenUrl;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withTokenGrantType(String tokenGrantType) {
            this.tokenGrantType = tokenGrantType;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withTokenClientSecret(String tokenClientSecret) {
            this.tokenClientSecret = tokenClientSecret;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withTokenClientId(String tokenClientId) {
            this.tokenClientId = tokenClientId;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withResponseHead(String responseHead) {
            this.responseHead = responseHead;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withResponseDetail(String responseDetail) {
            this.responseDetail = responseDetail;
            return this;
        }

        public BdcOpenApiDetailInfoDTOBuilder withResponseBodyDefaultValueList(List<BdcOpenApiDefaultParamDTO> responseBodyDefaultValueList) {
            this.responseBodyDefaultValueList = responseBodyDefaultValueList;
            return this;
        }

        public BdcOpenApiDetailInfoDTO build() {
            BdcOpenApiDetailInfoDTO bdcOpenApiDetailInfoDTO = new BdcOpenApiDetailInfoDTO();
            bdcOpenApiDetailInfoDTO.setId(id);
            bdcOpenApiDetailInfoDTO.setUrl(url);
            bdcOpenApiDetailInfoDTO.setRequestMethod(requestMethod);
            bdcOpenApiDetailInfoDTO.setName(name);
            bdcOpenApiDetailInfoDTO.setDescription(description);
            bdcOpenApiDetailInfoDTO.setType(type);
            bdcOpenApiDetailInfoDTO.setSql(sql);
            bdcOpenApiDetailInfoDTO.setClientId(clientId);
            bdcOpenApiDetailInfoDTO.setConsumer(consumer);
            bdcOpenApiDetailInfoDTO.setLogFlag(logFlag);
            bdcOpenApiDetailInfoDTO.setLogType(logType);
            bdcOpenApiDetailInfoDTO.setCreatedTime(createdTime);
            bdcOpenApiDetailInfoDTO.setCreatedBy(createdBy);
            bdcOpenApiDetailInfoDTO.setUpdatedTime(updatedTime);
            bdcOpenApiDetailInfoDTO.setUpdatedBy(updatedBy);
            bdcOpenApiDetailInfoDTO.setReleaseStatus(releaseStatus);
            bdcOpenApiDetailInfoDTO.setRestParamList(restParamList);
            bdcOpenApiDetailInfoDTO.setRestParamJson(restParamJson);
            bdcOpenApiDetailInfoDTO.setQueryParamList(queryParamList);
            bdcOpenApiDetailInfoDTO.setQueryParamJson(queryParamJson);
            bdcOpenApiDetailInfoDTO.setRequestBodyParamList(requestBodyParamList);
            bdcOpenApiDetailInfoDTO.setRequestBodyParamJson(requestBodyParamJson);
            bdcOpenApiDetailInfoDTO.setReturnTypeParamList(returnTypeParamList);
            bdcOpenApiDetailInfoDTO.setReturnTypeParamJson(returnTypeParamJson);
            bdcOpenApiDetailInfoDTO.setParamJson(paramJson);
            bdcOpenApiDetailInfoDTO.setPackageResponse(packageResponse);
            bdcOpenApiDetailInfoDTO.setPackageResponseDetail(packageResponseDetail);
            bdcOpenApiDetailInfoDTO.setTokenUrl(tokenUrl);
            bdcOpenApiDetailInfoDTO.setTokenGrantType(tokenGrantType);
            bdcOpenApiDetailInfoDTO.setTokenClientSecret(tokenClientSecret);
            bdcOpenApiDetailInfoDTO.setTokenClientId(tokenClientId);
            bdcOpenApiDetailInfoDTO.setResponseHead(responseHead);
            bdcOpenApiDetailInfoDTO.setResponseDetail(responseDetail);
            bdcOpenApiDetailInfoDTO.setResponseBodyDefaultValueList(responseBodyDefaultValueList);
            return bdcOpenApiDetailInfoDTO;
        }
    }
}
