package cn.gtmap.realestate.common.core.dto.register;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2020/1/9
 * @description 登簿用户信息
 */
@Api(value = "XxblDbDTO", description = "登簿操作的用户信息")
public class XxblDbDTO {
    @ApiModelProperty(value = "工作流实例ID")
    private  String gzlslid;
    @ApiModelProperty(value = "登簿人姓名")
    private  String dbr;
    @ApiModelProperty(value = "登记机构")
    private String djjg;
    @ApiModelProperty(value = "登记时间")
    private Date djsj;

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    @Override
    public String toString() {
        return "XxblDbDTO{" +
                "gzlslid='" + gzlslid + '\'' +
                ", dbr='" + dbr + '\'' +
                ", djjg='" + djjg + '\'' +
                ", djsj=" + djsj +
                '}';
    }
}
