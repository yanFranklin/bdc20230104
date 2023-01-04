package cn.gtmap.realestate.common.core.dto.init;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0, 2019/12/18
 * @description 部分不动产项目的数据
 */
@ApiModel(value = "BdcXmDTO",description = "部分不动产项目的数据对象")
public class BdcXmDTO {
    @ApiModelProperty(value = "项目id")
    private String xmid;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "权利类型")
    private Integer qllx;

    @ApiModelProperty(value = "登记小类")
    private String djxl;

    @ApiModelProperty(value = "工作流定义ID")
    private String gzldyid;

    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;

    @ApiModelProperty(value = "区县代码")
    private String qxdm;

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public Integer getQllx() {
        return qllx;
    }

    @Override
    public String toString() {
        return "BdcXmDTO{" +
                "xmid='" + xmid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", qllx=" + qllx +
                ", djxl='" + djxl + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                ", qjgldm='" + qjgldm + '\'' +
                ", qxdm='" + qxdm + '\'' +
                '}';
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof BdcXmDTO)){
            return false;
        }
        BdcXmDTO bdcXmDTO = (BdcXmDTO) o;
        return Objects.equals(xmid, bdcXmDTO.xmid) && Objects.equals(slbh, bdcXmDTO.slbh) && Objects.equals(gzlslid, bdcXmDTO.gzlslid) && Objects.equals(bdcdyh, bdcXmDTO.bdcdyh) && Objects.equals(qllx, bdcXmDTO.qllx) && Objects.equals(djxl, bdcXmDTO.djxl) && Objects.equals(gzldyid, bdcXmDTO.gzldyid) && Objects.equals(qjgldm, bdcXmDTO.qjgldm) && Objects.equals(qxdm, bdcXmDTO.qxdm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xmid, slbh, gzlslid, bdcdyh, qllx, djxl, gzldyid, qjgldm, qxdm);
    }
}
