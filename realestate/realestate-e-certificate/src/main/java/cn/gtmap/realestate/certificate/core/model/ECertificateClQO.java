package cn.gtmap.realestate.certificate.core.model;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 电子证照存量数据查询对象
 *
 * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
 * @version 下午5:22 2021/2/3
 */
@Api(value = "电子证照存量数据查询对象")
public class ECertificateClQO {

    @ApiModelProperty("签发状态")
    private Integer qfzt;

    @ApiModelProperty("不动产权证号")
    private String bdcqzh;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("缮证日期起")
    private Date szrqStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("缮证日期止")
    private Date szrqEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("签发日期起")
    private Date qfrqStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("签发日期止")
    private Date qfrqEnd;

    @ApiModelProperty("异常信息")
    private String message;

    public Integer getQfzt() {
        return qfzt;
    }

    public void setQfzt(Integer qfzt) {
        this.qfzt = qfzt;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public Date getSzrqStart() {
        return szrqStart;
    }

    public void setSzrqStart(Date szrqStart) {
        this.szrqStart = szrqStart;
    }

    public Date getSzrqEnd() {
        return szrqEnd;
    }

    public void setSzrqEnd(Date szrqEnd) {
        this.szrqEnd = szrqEnd;
    }

    public Date getQfrqStart() {
        return qfrqStart;
    }

    public void setQfrqStart(Date qfrqStart) {
        this.qfrqStart = qfrqStart;
    }

    public Date getQfrqEnd() {
        return qfrqEnd;
    }

    public void setQfrqEnd(Date qfrqEnd) {
        this.qfrqEnd = qfrqEnd;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ECertificateClQO{" +
                "qfzt=" + qfzt +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", szrqStart=" + szrqStart +
                ", szrqEnd=" + szrqEnd +
                ", qfrqStart=" + qfrqStart +
                ", qfrqEnd=" + qfrqEnd +
                ", message='" + message + '\'' +
                '}';
    }
}
