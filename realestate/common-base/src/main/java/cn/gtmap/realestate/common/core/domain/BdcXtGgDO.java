package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/4/19
 * @description 不动产公告信息配置
 */
@Table(name = "BDC_XT_GG")
public class BdcXtGgDO {

    @Id
    @ApiModelProperty(value = "主键")
    private String xtggid;

    @ApiModelProperty(value = "工作流定义ID")
    private String gzldyid;

    @ApiModelProperty(value = "工作流定义名称")
    private String gzldymc;

    @ApiModelProperty(value = "审批来源")
    private Integer sply;

    @ApiModelProperty(value = "公告类型")
    private Integer gglx;

    @ApiModelProperty(value = "公告标题配置")
    private String ggbtpz;

    @ApiModelProperty(value = "公告内容配置")
    private String ggnrpz;

    @ApiModelProperty(value = "操作时间")
    private Date czsj;

    @ApiModelProperty(value = "操作人")
    private String czr;

    @ApiModelProperty(value = "登记小类")
    private String djxl;

    public String getXtggid() {
        return xtggid;
    }

    public void setXtggid(String xtggid) {
        this.xtggid = xtggid;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getGzldymc() {
        return gzldymc;
    }

    public void setGzldymc(String gzldymc) {
        this.gzldymc = gzldymc;
    }

    public Integer getSply() {
        return sply;
    }

    public void setSply(Integer sply) {
        this.sply = sply;
    }

    public Integer getGglx() {
        return gglx;
    }

    public void setGglx(Integer gglx) {
        this.gglx = gglx;
    }

    public String getGgbtpz() {
        return ggbtpz;
    }

    public void setGgbtpz(String ggbtpz) {
        this.ggbtpz = ggbtpz;
    }

    public String getGgnrpz() {
        return ggnrpz;
    }

    public void setGgnrpz(String ggnrpz) {
        this.ggnrpz = ggnrpz;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    @Override
    public String toString() {
        return "BdcXtGgDO{" +
                "xtggid='" + xtggid + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                ", gzldymc='" + gzldymc + '\'' +
                ", sply=" + sply +
                ", gglx=" + gglx +
                ", ggbtpz='" + ggbtpz + '\'' +
                ", ggnrpz='" + ggnrpz + '\'' +
                ", czsj=" + czsj +
                ", czr='" + czr + '\'' +
                ", djxl='" + djxl + '\'' +
                '}';
    }
}
