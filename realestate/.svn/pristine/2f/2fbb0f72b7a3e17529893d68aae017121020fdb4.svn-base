package cn.gtmap.realestate.supervise.core.domain;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:zhuyong@gmail.com">zhuyong</a>
 * @version V1.0, 2021/10/15
 * @description 廉防附件信息表
 */
@Table(name = "BDC_LF_FJXX")
public class BdcLfFjxxDO {
    @Id
    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "业务ID")
    private String ywid;

    @ApiModelProperty(value = "文件名称")
    private String wjmc;

    @ApiModelProperty(value = "文件ID")
    private String wjid;

    @ApiModelProperty(value = "文件路径")
    private String wjlj;

    @ApiModelProperty(value = "文件上传时间")
    private Date wjscsj;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYwid() {
        return ywid;
    }

    public void setYwid(String ywid) {
        this.ywid = ywid;
    }

    public String getWjmc() {
        return wjmc;
    }

    public void setWjmc(String wjmc) {
        this.wjmc = wjmc;
    }

    public String getWjid() {
        return wjid;
    }

    public void setWjid(String wjid) {
        this.wjid = wjid;
    }

    public String getWjlj() {
        return wjlj;
    }

    public void setWjlj(String wjlj) {
        this.wjlj = wjlj;
    }

    public Date getWjscsj() {
        return wjscsj;
    }

    public void setWjscsj(Date wjscsj) {
        this.wjscsj = wjscsj;
    }

    @Override
    public String toString() {
        return "BdcLfFjxxDO{" +
                "id='" + id + '\'' +
                ", ywid='" + ywid + '\'' +
                ", wjmc='" + wjmc + '\'' +
                ", wjid='" + wjid + '\'' +
                ", wjlj='" + wjlj + '\'' +
                ", wjscsj=" + wjscsj +
                '}';
    }
}
