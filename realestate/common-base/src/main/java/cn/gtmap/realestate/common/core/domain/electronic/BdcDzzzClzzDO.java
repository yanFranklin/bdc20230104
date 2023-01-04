package cn.gtmap.realestate.common.core.domain.electronic;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 存量电子证照信息 DO
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 */
@Table(name = "BDC_DZZZ_CLZZ")
@ApiModel(value = "BdcDzzzClzzDO", description = "存量电子证照ffq")
public class BdcDzzzClzzDO {
    @Id
    @ApiModelProperty(value = "id")
    private String id;

    /**
     * 操作日期
     */
    @ApiModelProperty(value = "操作日期")
    private Date czrq;

    /**
     * 不动产权证号
     */
    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    /**
     * 签发下载状态 ECertificateQfztEnum
     */
    @ApiModelProperty(value = "签发下载状态")
    private Integer status;

    /**
     * 请求结果 code
     */
    @ApiModelProperty(value = "请求结果 code")
    private String code;

    /**
     * 返回信息
     */
    @ApiModelProperty(value = "返回信息")
    private String msg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCzrq() {
        return czrq;
    }

    public void setCzrq(Date czrq) {
        this.czrq = czrq;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
