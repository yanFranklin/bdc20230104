package cn.gtmap.realestate.common.core.qo.portal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/25
 * @description 不动产任务信息列表查询QO对象
 */
@ApiModel(value = "BdcTaskQO", description = "不动产任务列表查询对象")
public class BdcTaskQO implements Serializable {
    private static final long serialVersionUID = -2047931530847950653L;

    @ApiModelProperty(value = "用户账号")
    private String userId;

    @ApiModelProperty(value = "项目受理编号")
    private String slbh;

    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @Override
    public String toString() {
        return "BdcTaskQO{" +
                "userId='" + userId + '\'' +
                ", slbh='" + slbh + '\'' +
                ", qlr='" + qlr + '\'' +
                ", zl='" + zl + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                '}';
    }
}
