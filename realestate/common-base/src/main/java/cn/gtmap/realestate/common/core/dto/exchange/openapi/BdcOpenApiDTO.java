package cn.gtmap.realestate.common.core.dto.exchange.openapi;

import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkCsDO;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkDO;
import cn.gtmap.realestate.common.core.enums.OpenApiParamConstansEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.com">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/9/25 15:12
 */
public class BdcOpenApiDTO {

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

    @ApiModelProperty(value = "接口类型 0:自定义接口；1：程序接口（xml接口）; 2:程序接口（controller）")
    private Integer type;

    @ApiModelProperty(value = "配置sql")
    private String sql;

    @ApiModelProperty(value = "应用id")
    private String clientId;

    @ApiModelProperty(value = "接口消费方")
    private String consumer;

    @ApiModelProperty(value = "是否记录日志 0：是;1：否")
    private Integer logFlag;

    @ApiModelProperty(value = "日志记录方式 0：数据库;1：ES")
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

    @ApiModelProperty(value = "是否包装返回参数 0不包装 1包装")
    private Integer packageResponse;

    @ApiModelProperty(value = "是否包装返回详情 0不包装 1包装")
    private Integer packageResponseDetail;

    @ApiModelProperty(value = "结果是否返回list 0 否 1 是")
    private Integer dataIsList;

    @ApiModelProperty(value = "参数")
    private List<BdcOpenApiParamDTO> paramList;

    @ApiModelProperty(value = "响应头参数")
    private List<BdcOpenApiResponseHeadDTO> responseHead;

    @ApiModelProperty(value = "响应情况参数")
    private List<BdcOpenApiResponseDetailDTO> responseDetail;

    private String param;

    @ApiModelProperty(value = "响应体默认值")
    private List<BdcOpenApiParamDTO> responseBodyDefaultParamList;

    public List<BdcOpenApiResponseHeadDTO> getResponseHead() {
        return responseHead;
    }

    public void setResponseHead(List<BdcOpenApiResponseHeadDTO> responseHead) {
        this.responseHead = responseHead;
    }

    public List<BdcOpenApiParamDTO> getResponseBodyDefaultParamList() {
        return responseBodyDefaultParamList;
    }

    public void setResponseBodyDefaultParamList(List<BdcOpenApiParamDTO> responseBodyDefaultParamList) {
        this.responseBodyDefaultParamList = responseBodyDefaultParamList;
    }

    public Integer getPackageResponse() {
        return packageResponse;
    }

    public void setPackageResponse(Integer packageResponse) {
        this.packageResponse = packageResponse;
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

    public List<BdcOpenApiParamDTO> getParamList() {
        return paramList;
    }

    public void setParamList(List<BdcOpenApiParamDTO> paramList) {
        this.paramList = paramList;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Integer getPackageResponseDetail() {
        return packageResponseDetail;
    }

    public void setPackageResponseDetail(Integer packageResponseDetail) {
        this.packageResponseDetail = packageResponseDetail;
    }

    public List<BdcOpenApiResponseDetailDTO> getResponseDetail() {
        return responseDetail;
    }

    public void setResponseDetail(List<BdcOpenApiResponseDetailDTO> responseDetail) {
        this.responseDetail = responseDetail;
    }

    public Integer getDataIsList() {
        return dataIsList;
    }

    public void setDataIsList(Integer dataIsList) {
        this.dataIsList = dataIsList;
    }

    @Override
    public String toString() {
        return "BdcOpenApiDTO{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", sql='" + sql + '\'' +
                ", clientId='" + clientId + '\'' +
                ", consumer=" + consumer +
                ", logFlag=" + logFlag +
                ", logType=" + logType +
                ", createdTime=" + createdTime +
                ", createdBy='" + createdBy + '\'' +
                ", updatedTime=" + updatedTime +
                ", updatedBy='" + updatedBy + '\'' +
                ", releaseStatus=" + releaseStatus +
                ", paramList=" + paramList +
                '}';
    }

    public void convertDO(BdcDwJkDO bdcDwJkDO, List<BdcDwJkCsDO> bdcDwJkCsDOList){
        if (bdcDwJkDO == null){
            return;
        }
        if (CollectionUtils.isEmpty(bdcDwJkCsDOList)){
            return;
        }
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
        this.setPackageResponse(bdcDwJkDO.getPackageResponse());
        if (StringUtils.isNotBlank(bdcDwJkDO.getResponseHead())){
            this.setResponseHead(JSON.parseArray(bdcDwJkDO.getResponseHead(),BdcOpenApiResponseHeadDTO.class));
        }
        this.setPackageResponseDetail(bdcDwJkDO.getPackageResponseDetail());
        if (StringUtils.isNotBlank(bdcDwJkDO.getResponseDetail())){
            this.setResponseDetail(JSON.parseArray(bdcDwJkDO.getResponseDetail(),BdcOpenApiResponseDetailDTO.class));
        }
        this.setDataIsList(bdcDwJkDO.getDataIsList());
        if (StringUtils.isNotBlank(bdcDwJkDO.getResponseBodyDefaultValue())){
            this.setResponseBodyDefaultParamList(JSON.parseArray(bdcDwJkDO.getResponseBodyDefaultValue(),BdcOpenApiParamDTO.class));
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
                    JSONObject jsonObject = JSON.parseObject(bdcDwJkCsDO.getJkcsext());
                    bdcOpenApiParamDTO.setChildParamId(jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_CURRENT_JKID.getKey()));
                    bdcOpenApiParamDTO.setSql(jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_SQL.getKey()));
                    if (StringUtils.isNotBlank(jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_YES.getKey())) && OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_YES.getValue().equals(jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_YES.getKey()))){
                        bdcOpenApiParamDTO.setIsListFlag("on");
                    }
                }
                paramDTOList.add(bdcOpenApiParamDTO);
            });
        }

        this.setParamList(paramDTOList);

    }
}
