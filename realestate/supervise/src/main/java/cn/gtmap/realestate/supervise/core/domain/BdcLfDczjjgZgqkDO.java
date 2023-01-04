package cn.gtmap.realestate.supervise.core.domain;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:zhuyong@gmail.com">zhuyong</a>
 * @version V1.0, 2021/09/07
 * @description 整改情况表
 */
@Table(name = "BDC_LF_DCZJJG_ZGQK")
public class BdcLfDczjjgZgqkDO {
    @Id
    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "质检信息ID")
    private String zjxxid;

    @ApiModelProperty(value = "上传人员ID")
    private String scryid;

    @ApiModelProperty(value = "上传人员")
    private String scry;

    @ApiModelProperty(value = "整改部门ID")
    private String zgbmid;

    @ApiModelProperty(value = "整改部门")
    private String zgbm;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "整改时间")
    private Date zgsj;

    @ApiModelProperty(value = "整改文件名称")
    private String zgwjmc;

    @ApiModelProperty(value = "整改文件ID")
    private String zgwjid;

    @ApiModelProperty(value = "整改文件路径")
    private String zgwjlj;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "整改文件上传时间")
    private Date zgwjscsj;

    @ApiModelProperty(value = "整改情况说明")
    private String zgqksm;

    @ApiModelProperty(value = "整改是否完成")
    private Integer zgsfwc;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZjxxid() {
        return zjxxid;
    }

    public void setZjxxid(String zjxxid) {
        this.zjxxid = zjxxid;
    }

    public String getScryid() {
        return scryid;
    }

    public void setScryid(String scryid) {
        this.scryid = scryid;
    }

    public String getScry() {
        return scry;
    }

    public void setScry(String scry) {
        this.scry = scry;
    }

    public String getZgbmid() {
        return zgbmid;
    }

    public void setZgbmid(String zgbmid) {
        this.zgbmid = zgbmid;
    }

    public String getZgbm() {
        return zgbm;
    }

    public void setZgbm(String zgbm) {
        this.zgbm = zgbm;
    }

    public Date getZgsj() {
        return zgsj;
    }

    public void setZgsj(Date zgsj) {
        this.zgsj = zgsj;
    }

    public String getZgwjmc() {
        return zgwjmc;
    }

    public void setZgwjmc(String zgwjmc) {
        this.zgwjmc = zgwjmc;
    }

    public String getZgwjid() {
        return zgwjid;
    }

    public void setZgwjid(String zgwjid) {
        this.zgwjid = zgwjid;
    }

    public String getZgwjlj() {
        return zgwjlj;
    }

    public void setZgwjlj(String zgwjlj) {
        this.zgwjlj = zgwjlj;
    }

    public Date getZgwjscsj() {
        return zgwjscsj;
    }

    public void setZgwjscsj(Date zgwjscsj) {
        this.zgwjscsj = zgwjscsj;
    }

    public String getZgqksm() {
        return zgqksm;
    }

    public void setZgqksm(String zgqksm) {
        this.zgqksm = zgqksm;
    }

    public Integer getZgsfwc() {
        return zgsfwc;
    }

    public void setZgsfwc(Integer zgsfwc) {
        this.zgsfwc = zgsfwc;
    }

    @Override
    public String toString() {
        return "BdcLfDczjjgZgqkDO{" +
                "id='" + id + '\'' +
                ", zjxxid='" + zjxxid + '\'' +
                ", scryid='" + scryid + '\'' +
                ", scry='" + scry + '\'' +
                ", zgbmid='" + zgbmid + '\'' +
                ", zgbm='" + zgbm + '\'' +
                ", zgsj=" + zgsj +
                ", zgwjmc='" + zgwjmc + '\'' +
                ", zgwjid='" + zgwjid + '\'' +
                ", zgwjlj='" + zgwjlj + '\'' +
                ", zgwjscsj=" + zgwjscsj +
                ", zgqksm='" + zgqksm + '\'' +
                ", zgsfwc=" + zgsfwc +
                '}';
    }
}
