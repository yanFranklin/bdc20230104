package cn.gtmap.realestate.common.core.domain.exchange;

import cn.gtmap.realestate.common.core.dto.exchange.openapi.BdcOpenApiDTO;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/7/20 14:38
 * @description
 */
@Table(name = "bdc_dw_jk")
public class BdcDwJkDO {

    @Id
    @ApiModelProperty(value = "主键,接口id")
    private String jkid;

    @ApiModelProperty(value = "权限认证")
    private String qxrz;

    @ApiModelProperty(value = "接口地址")
    private String jkdz;

    @ApiModelProperty(value = "接口请求方式")
    private String jkqqfs;

    @ApiModelProperty(value = "接口名称")
    private String jkmc;

    @ApiModelProperty(value = "接口描述")
    private String jkms;

    @ApiModelProperty(value = "接口类型 0:普通简单接口；1：复杂接口")
    private Integer jklx;

    @ApiModelProperty(value = "配置sql")
    private String sjkjb;

    @ApiModelProperty(value = "应用id")
    private String yyid;

    @ApiModelProperty(value = "接口消费方")
    private String jkxff;

    @ApiModelProperty(value = "是否记录日志 0：是1否")
    private Integer sfjlrz;

    @ApiModelProperty(value = "日志记录方式 0：数据库 1：es")
    private Integer rzjlfs;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date cjsj;

    @ApiModelProperty(value = "创建人")
    private String cjr;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间" ,example = "2020-10-01 12:18:48")
    private Date xgsj;

    @ApiModelProperty(value = "修改人")
    private String xgr;

    @ApiModelProperty(value = "是否删除 0：正常 1：删除")
    private Integer sfsc;

    @ApiModelProperty(value = "发布状态 0：未发布 1：已发布")
    private Integer fbzt;

    @ApiModelProperty(value = "参数实例")
    private String cssl;

    @ApiModelProperty(value = "修改时间")
    private Date updatetime;

    @ApiModelProperty(value = "是否包装返回参数")
    private Integer packageResponse;

    @ApiModelProperty(value = "响应头配置")
    private String responseHead;

    @ApiModelProperty(value = "是否包装响应情况参数")
    private Integer packageResponseDetail;

    @ApiModelProperty(value = "返回结果是否list")
    private Integer dataIsList;

    @ApiModelProperty(value = "响应情况配置")
    private String responseDetail;

    @ApiModelProperty(value = "响应体默认值配置")
    private String responseBodyDefaultValue;

    public void convertDTO(BdcOpenApiDTO bdcOpenApiDTO){
        this.setJkid(bdcOpenApiDTO.getId());
        this.setJkdz(bdcOpenApiDTO.getUrl());
        this.setJkqqfs(bdcOpenApiDTO.getRequestMethod());
        this.setJkmc(bdcOpenApiDTO.getName());
        this.setJkms(bdcOpenApiDTO.getDescription());
        this.setJklx(bdcOpenApiDTO.getType());
        this.setSjkjb(bdcOpenApiDTO.getSql());
        this.setYyid(bdcOpenApiDTO.getClientId());
        this.setJkxff(bdcOpenApiDTO.getConsumer());
        this.setSfjlrz(bdcOpenApiDTO.getLogFlag());
        this.setRzjlfs(bdcOpenApiDTO.getLogType());
        this.setFbzt(bdcOpenApiDTO.getReleaseStatus());
        this.setXgr(bdcOpenApiDTO.getUpdatedBy());
        this.setCjr(bdcOpenApiDTO.getCreatedBy());
        this.setCjsj(bdcOpenApiDTO.getCreatedTime());
        this.setXgsj(bdcOpenApiDTO.getUpdatedTime());
        this.setCssl(bdcOpenApiDTO.getParam());
        this.setPackageResponse(bdcOpenApiDTO.getPackageResponse());
        this.setPackageResponseDetail(bdcOpenApiDTO.getPackageResponseDetail());
        this.setResponseHead(CollectionUtils.isNotEmpty(bdcOpenApiDTO.getResponseHead())? JSON.toJSONString(bdcOpenApiDTO.getResponseHead()):null );
        this.setResponseDetail(CollectionUtils.isNotEmpty(bdcOpenApiDTO.getResponseDetail())? JSON.toJSONString(bdcOpenApiDTO.getResponseDetail()):null );
        this.setResponseBodyDefaultValue(CollectionUtils.isNotEmpty(bdcOpenApiDTO.getResponseBodyDefaultParamList())? JSON.toJSONString(bdcOpenApiDTO.getResponseBodyDefaultParamList()):null );
        this.setDataIsList(bdcOpenApiDTO.getDataIsList());
        this.setSfsc(0);
    }

    public String getResponseBodyDefaultValue() {
        return responseBodyDefaultValue;
    }

    public void setResponseBodyDefaultValue(String responseBodyDefaultValue) {
        this.responseBodyDefaultValue = responseBodyDefaultValue;
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

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getJkid() {
        return jkid;
    }

    public void setJkid(String jkid) {
        this.jkid = jkid;
    }

    public String getQxrz() {
        return qxrz;
    }

    public void setQxrz(String qxrz) {
        this.qxrz = qxrz;
    }

    public String getJkdz() {
        return jkdz;
    }

    public void setJkdz(String jkdz) {
        this.jkdz = jkdz;
    }

    public String getJkqqfs() {
        return jkqqfs;
    }

    public void setJkqqfs(String jkqqfs) {
        this.jkqqfs = jkqqfs;
    }

    public String getJkmc() {
        return jkmc;
    }

    public void setJkmc(String jkmc) {
        this.jkmc = jkmc;
    }

    public String getJkms() {
        return jkms;
    }

    public void setJkms(String jkms) {
        this.jkms = jkms;
    }

    public Integer getJklx() {
        return jklx;
    }

    public void setJklx(Integer jklx) {
        this.jklx = jklx;
    }

    public String getSjkjb() {
        return sjkjb;
    }

    public void setSjkjb(String sjkjb) {
        this.sjkjb = sjkjb;
    }

    public String getYyid() {
        return yyid;
    }

    public void setYyid(String yyid) {
        this.yyid = yyid;
    }

    public String getJkxff() {
        return jkxff;
    }

    public void setJkxff(String jkxff) {
        this.jkxff = jkxff;
    }

    public Integer getSfjlrz() {
        return sfjlrz;
    }

    public void setSfjlrz(Integer sfjlrz) {
        this.sfjlrz = sfjlrz;
    }

    public Integer getRzjlfs() {
        return rzjlfs;
    }

    public void setRzjlfs(Integer rzjlfs) {
        this.rzjlfs = rzjlfs;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public Date getXgsj() {
        return xgsj;
    }

    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }

    public String getXgr() {
        return xgr;
    }

    public void setXgr(String xgr) {
        this.xgr = xgr;
    }

    public Integer getSfsc() {
        return sfsc;
    }

    public void setSfsc(Integer sfsc) {
        this.sfsc = sfsc;
    }

    public Integer getFbzt() {
        return fbzt;
    }

    public void setFbzt(Integer fbzt) {
        this.fbzt = fbzt;
    }

    public String getCssl() {
        return cssl;
    }

    public void setCssl(String cssl) {
        this.cssl = cssl;
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

    @Override
    public String toString() {
        return "BdcDwJkDO{" +
                "jkid='" + jkid + '\'' +
                ", qxrz='" + qxrz + '\'' +
                ", jkdz='" + jkdz + '\'' +
                ", jkqqfs='" + jkqqfs + '\'' +
                ", jkmc='" + jkmc + '\'' +
                ", jkms='" + jkms + '\'' +
                ", jklx=" + jklx +
                ", sjkjb='" + sjkjb + '\'' +
                ", yyid='" + yyid + '\'' +
                ", jkxff='" + jkxff + '\'' +
                ", sfjlrz=" + sfjlrz +
                ", rzjlfs=" + rzjlfs +
                ", cjsj=" + cjsj +
                ", cjr='" + cjr + '\'' +
                ", xgsj=" + xgsj +
                ", xgr='" + xgr + '\'' +
                ", sfsc=" + sfsc +
                ", fbzt=" + fbzt +
                ", cssl='" + cssl + '\'' +
                ", updatetime=" + updatetime +
                ", packageResponse=" + packageResponse +
                '}';
    }

    public static final class BdcDwJkDOBuilder {
        private String jkid;
        private String qxrz;
        private String jkdz;
        private String jkqqfs;
        private String jkmc;
        private String jkms;
        private Integer jklx;
        private String sjkjb;
        private String yyid;
        private String jkxff;
        private Integer sfjlrz;
        private Integer rzjlfs;
        private Date cjsj;
        private String cjr;
        private Date xgsj;
        private String xgr;
        private Integer sfsc;
        private Integer fbzt;
        private String cssl;
        private Date updatetime;
        private Integer packageResponse;
        private String responseHead;
        private Integer packageResponseDetail;
        private String responseDetail;
        private String responseBodyDefaultValue;

        private BdcDwJkDOBuilder() {
        }

        public static BdcDwJkDOBuilder aBdcDwJkDO() {
            return new BdcDwJkDOBuilder();
        }

        public BdcDwJkDOBuilder withJkid(String jkid) {
            this.jkid = jkid;
            return this;
        }

        public BdcDwJkDOBuilder withQxrz(String qxrz) {
            this.qxrz = qxrz;
            return this;
        }

        public BdcDwJkDOBuilder withJkdz(String jkdz) {
            this.jkdz = jkdz;
            return this;
        }

        public BdcDwJkDOBuilder withJkqqfs(String jkqqfs) {
            this.jkqqfs = jkqqfs;
            return this;
        }

        public BdcDwJkDOBuilder withJkmc(String jkmc) {
            this.jkmc = jkmc;
            return this;
        }

        public BdcDwJkDOBuilder withJkms(String jkms) {
            this.jkms = jkms;
            return this;
        }

        public BdcDwJkDOBuilder withJklx(Integer jklx) {
            this.jklx = jklx;
            return this;
        }

        public BdcDwJkDOBuilder withSjkjb(String sjkjb) {
            this.sjkjb = sjkjb;
            return this;
        }

        public BdcDwJkDOBuilder withYyid(String yyid) {
            this.yyid = yyid;
            return this;
        }

        public BdcDwJkDOBuilder withJkxff(String jkxff) {
            this.jkxff = jkxff;
            return this;
        }

        public BdcDwJkDOBuilder withSfjlrz(Integer sfjlrz) {
            this.sfjlrz = sfjlrz;
            return this;
        }

        public BdcDwJkDOBuilder withRzjlfs(Integer rzjlfs) {
            this.rzjlfs = rzjlfs;
            return this;
        }

        public BdcDwJkDOBuilder withCjsj(Date cjsj) {
            this.cjsj = cjsj;
            return this;
        }

        public BdcDwJkDOBuilder withCjr(String cjr) {
            this.cjr = cjr;
            return this;
        }

        public BdcDwJkDOBuilder withXgsj(Date xgsj) {
            this.xgsj = xgsj;
            return this;
        }

        public BdcDwJkDOBuilder withXgr(String xgr) {
            this.xgr = xgr;
            return this;
        }

        public BdcDwJkDOBuilder withSfsc(Integer sfsc) {
            this.sfsc = sfsc;
            return this;
        }

        public BdcDwJkDOBuilder withFbzt(Integer fbzt) {
            this.fbzt = fbzt;
            return this;
        }

        public BdcDwJkDOBuilder withCssl(String cssl) {
            this.cssl = cssl;
            return this;
        }

        public BdcDwJkDOBuilder withUpdatetime(Date updatetime) {
            this.updatetime = updatetime;
            return this;
        }

        public BdcDwJkDOBuilder withPackageResponse(Integer packageResponse) {
            this.packageResponse = packageResponse;
            return this;
        }

        public BdcDwJkDOBuilder withResponseHead(String responseHead) {
            this.responseHead = responseHead;
            return this;
        }

        public BdcDwJkDOBuilder withPackageResponseDetail(Integer packageResponseDetail) {
            this.packageResponseDetail = packageResponseDetail;
            return this;
        }

        public BdcDwJkDOBuilder withResponseDetail(String responseDetail) {
            this.responseDetail = responseDetail;
            return this;
        }

        public BdcDwJkDOBuilder withResponseBodyDefaultValue(String responseBodyDefaultValue) {
            this.responseBodyDefaultValue = responseBodyDefaultValue;
            return this;
        }

        public BdcDwJkDO build() {
            BdcDwJkDO bdcDwJkDO = new BdcDwJkDO();
            bdcDwJkDO.setJkid(jkid);
            bdcDwJkDO.setQxrz(qxrz);
            bdcDwJkDO.setJkdz(jkdz);
            bdcDwJkDO.setJkqqfs(jkqqfs);
            bdcDwJkDO.setJkmc(jkmc);
            bdcDwJkDO.setJkms(jkms);
            bdcDwJkDO.setJklx(jklx);
            bdcDwJkDO.setSjkjb(sjkjb);
            bdcDwJkDO.setYyid(yyid);
            bdcDwJkDO.setJkxff(jkxff);
            bdcDwJkDO.setSfjlrz(sfjlrz);
            bdcDwJkDO.setRzjlfs(rzjlfs);
            bdcDwJkDO.setCjsj(cjsj);
            bdcDwJkDO.setCjr(cjr);
            bdcDwJkDO.setXgsj(xgsj);
            bdcDwJkDO.setXgr(xgr);
            bdcDwJkDO.setSfsc(sfsc);
            bdcDwJkDO.setFbzt(fbzt);
            bdcDwJkDO.setCssl(cssl);
            bdcDwJkDO.setUpdatetime(updatetime);
            bdcDwJkDO.setPackageResponse(packageResponse);
            bdcDwJkDO.setResponseHead(responseHead);
            bdcDwJkDO.setPackageResponseDetail(packageResponseDetail);
            bdcDwJkDO.setResponseDetail(responseDetail);
            bdcDwJkDO.setResponseBodyDefaultValue(responseBodyDefaultValue);
            return bdcDwJkDO;
        }
    }
}
