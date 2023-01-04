package cn.gtmap.realestate.supervise.core.domain;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:zhuyong@gmail.com">zhuyong</a>
 * @version V1.0, 2021/09/13
 * @description 职责权能监管-授权信息管理
 */
@Table(name = "BDC_LF_SQXX")
public class BdcLfSqxxDO {
    @Id
    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "授权说明")
    private String sqsm;

    @ApiModelProperty(value = "审批文件名称")
    private String spwjmc;

    @ApiModelProperty(value = "审批文件ID")
    private String spwjid;

    @ApiModelProperty(value = "审批文件路径")
    private String spwjlj;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "审批文件上传时间")
    private Date spwjscsj;

    @ApiModelProperty(value = "操作人")
    private String czr;

    @ApiModelProperty(value = "操作人ID")
    private String czrid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "操作时间")
    private Date czsj;

    @ApiModelProperty(value = "授权类型")
    private Integer sqlx;


    public Integer getSqlx() {
        return sqlx;
    }

    public void setSqlx(Integer sqlx) {
        this.sqlx = sqlx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSqsm() {
        return sqsm;
    }

    public void setSqsm(String sqsm) {
        this.sqsm = sqsm;
    }

    public String getSpwjmc() {
        return spwjmc;
    }

    public void setSpwjmc(String spwjmc) {
        this.spwjmc = spwjmc;
    }

    public String getSpwjid() {
        return spwjid;
    }

    public void setSpwjid(String spwjid) {
        this.spwjid = spwjid;
    }

    public String getSpwjlj() {
        return spwjlj;
    }

    public void setSpwjlj(String spwjlj) {
        this.spwjlj = spwjlj;
    }

    public Date getSpwjscsj() {
        return spwjscsj;
    }

    public void setSpwjscsj(Date spwjscsj) {
        this.spwjscsj = spwjscsj;
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }

    public String getCzrid() {
        return czrid;
    }

    public void setCzrid(String czrid) {
        this.czrid = czrid;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    @Override
    public String toString() {
        return "BdcLfSqxxDO{" +
                "id='" + id + '\'' +
                ", sqsm='" + sqsm + '\'' +
                ", spwjmc='" + spwjmc + '\'' +
                ", spwjid='" + spwjid + '\'' +
                ", spwjlj='" + spwjlj + '\'' +
                ", spwjscsj='" + spwjscsj + '\'' +
                ", czr='" + czr + '\'' +
                ", czrid='" + czrid + '\'' +
                ", czsj=" + czsj +
                '}';
    }
}
