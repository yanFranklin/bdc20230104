package cn.gtmap.realestate.common.core.domain.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @ClassName:
 * @Description: 附件材料表
 * @date
 */
@Api(value = "BdcZqFjclDO", description = "附件材料表")
@Table(name = "BDC_ZQ_FJCL")
public class BdcZqFjclDO {

    @Id
    @ApiModelProperty(value = "附件ID")
    private String fjid;

    @ApiModelProperty(value = "申请信息ID")
    private String sqxxid;

    @ApiModelProperty(value = "附件名称")
    private String clmc;

    @ApiModelProperty(value = "序号")
    private Integer xh;

    @ApiModelProperty(value = "页数")
    private Integer ys;

    @ApiModelProperty(value = "文件中心NodeId")
    private String wjzxid;

    @ApiModelProperty(value = "备注")
    private String bz;

    public String getFjid() {
        return fjid;
    }

    public void setFjid(String fjid) {
        this.fjid = fjid;
    }

    public String getSqxxid() {
        return sqxxid;
    }

    public void setSqxxid(String sqxxid) {
        this.sqxxid = sqxxid;
    }

    public String getClmc() {
        return clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public Integer getYs() {
        return ys;
    }

    public void setYs(Integer ys) {
        this.ys = ys;
    }

    public String getWjzxid() {
        return wjzxid;
    }

    public void setWjzxid(String wjzxid) {
        this.wjzxid = wjzxid;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Override
    public String toString() {
        return "BdcZqFjclDO{" +
                "fjid='" + fjid + '\'' +
                ", sqxxid='" + sqxxid + '\'' +
                ", clmc='" + clmc + '\'' +
                ", xh=" + xh +
                ", ys=" + ys +
                ", wjzxid=" + wjzxid +
                ", bz='" + bz + '\'' +
                '}';
    }
}
