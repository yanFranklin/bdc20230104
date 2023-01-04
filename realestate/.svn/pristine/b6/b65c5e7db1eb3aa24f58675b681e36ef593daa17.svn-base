package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/13
 * @description 不动产登记小类配置
 */
@Table(name = "BDC_DJXL_PZ")
@ApiModel(value = "BdcDjxlPzDO", description = "不动产登记小类配置")
public class BdcDjxlPzDO implements Serializable, Comparable<BdcDjxlPzDO> {

    @Id
    @ApiModelProperty(value = "配置ID")
    private String pzid;
    @ApiModelProperty(value = "登记小类")
    private String djxl;
    @ApiModelProperty(value = "工作流定义ID")
    private String gzldyid;
    @ApiModelProperty(value = "权利类型")
    private Integer qllx;
    @ApiModelProperty(value = "预告登记种类")
    private String ygdjzl;
    @ApiModelProperty(value = "顺序号")
    private Integer sxh;
    @ApiModelProperty(value = "不动产类型")
    private Integer bdclx;
    @ApiModelProperty(value = "根据不动产单元号判断的权利类型")
    private Integer dyhqllx;
    @ApiModelProperty(value = "是否收费")
    private Integer sfsf;
    @ApiModelProperty(value = "申请书打印类型")
    private String sqsdylx;
    @ApiModelProperty(value = "审批表打印类型")
    private String spbdylx;
    @ApiModelProperty(value = "配置修改时间")
    private Date pzxgsj;
    @ApiModelProperty(value = "原权利类型")
    private Integer yqllx;
    @ApiModelProperty(value = "收件单打印类型")
    private String sjddylx;
    @ApiModelProperty(value = "档案目录打印类型")
    private String damldylx;
    @ApiModelProperty(value = "档案目录封面打印类型")
    private String dafmdylx;
    @ApiModelProperty(value = "金坛档案目录封面打印类型")
    private String jtdafmdylx;
    @ApiModelProperty(value = "溧阳档案目录封面打印类型")
    private String lydafmdylx;
    @ApiModelProperty("是否上报")
    private Integer sfsb;

    @ApiModelProperty("流程删除是否删除收费信息")
    private Integer lcscsfxx;

    public Integer getSfsb() {
        return sfsb;
    }

    public void setSfsb(Integer sfsb) {
        this.sfsb = sfsb;
    }

    public String getDafmdylx() {
        return dafmdylx;
    }

    public void setDafmdylx(String dafmdylx) {
        this.dafmdylx = dafmdylx;
    }

    public String getPzid() {
        return pzid;
    }

    public void setPzid(String pzid) {
        this.pzid = pzid;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    public BdcDjxlPzDO() {
        super();
    }

    public BdcDjxlPzDO(String pzid, String djxl, String gzldyid, Integer qllx, Integer sxh, Integer bdclx) {
        this.pzid = pzid;
        this.djxl = djxl;
        this.gzldyid = gzldyid;
        this.qllx = qllx;
        this.sxh = sxh;
        this.bdclx = bdclx;
    }

    @Override
    public int compareTo(BdcDjxlPzDO o) {
        return this.sxh.compareTo(o.getSxh());
    }

    public Integer getBdclx() {
        return bdclx;
    }

    public void setBdclx(Integer bdclx) {
        this.bdclx = bdclx;
    }

    public Integer getDyhqllx() {
        return dyhqllx;
    }

    public void setDyhqllx(Integer dyhqllx) {
        this.dyhqllx = dyhqllx;
    }

    public Integer getSfsf() {
        return sfsf;
    }

    public void setSfsf(Integer sfsf) {
        this.sfsf = sfsf;
    }

    public String getSqsdylx() {
        return sqsdylx;
    }

    public void setSqsdylx(String sqsdylx) {
        this.sqsdylx = sqsdylx;
    }

    public String getSpbdylx() {
        return spbdylx;
    }

    public void setSpbdylx(String spbdylx) {
        this.spbdylx = spbdylx;
    }

    public Date getPzxgsj() {
        return pzxgsj;
    }

    public void setPzxgsj(Date pzxgsj) {
        this.pzxgsj = pzxgsj;
    }

    public Integer getYqllx() {
        return yqllx;
    }

    public void setYqllx(Integer yqllx) {
        this.yqllx = yqllx;
    }

    public String getSjddylx() {
        return sjddylx;
    }

    public void setSjddylx(String sjddylx) {
        this.sjddylx = sjddylx;
    }

    public String getDamldylx() {
        return damldylx;
    }

    public void setDamldylx(String damldylx) {
        this.damldylx = damldylx;
    }

    public String getYgdjzl() {
        return ygdjzl;
    }

    public void setYgdjzl(String ygdjzl) {
        this.ygdjzl = ygdjzl;
    }

    public String getJtdafmdylx() {
        return jtdafmdylx;
    }

    public void setJtdafmdylx(String jtdafmdylx) {
        this.jtdafmdylx = jtdafmdylx;
    }

    public String getLydafmdylx() {
        return lydafmdylx;
    }

    public void setLydafmdylx(String lydafmdylx) {
        this.lydafmdylx = lydafmdylx;
    }

    public Integer getLcscsfxx() {
        return lcscsfxx;
    }

    public void setLcscsfxx(Integer lcscsfxx) {
        this.lcscsfxx = lcscsfxx;
    }

    @Override
    public String toString() {
        return "BdcDjxlPzDO{" +
                "pzid='" + pzid + '\'' +
                ", djxl='" + djxl + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                ", qllx=" + qllx +
                ", ygdjzl='" + ygdjzl + '\'' +
                ", sxh=" + sxh +
                ", bdclx=" + bdclx +
                ", dyhqllx=" + dyhqllx +
                ", sfsf=" + sfsf +
                ", sqsdylx='" + sqsdylx + '\'' +
                ", spbdylx='" + spbdylx + '\'' +
                ", pzxgsj=" + pzxgsj +
                ", yqllx=" + yqllx +
                ", sjddylx='" + sjddylx + '\'' +
                ", damldylx='" + damldylx + '\'' +
                ", dafmdylx='" + dafmdylx + '\'' +
                ", jtdafmdylx='" + jtdafmdylx + '\'' +
                ", lydafmdylx='" + lydafmdylx + '\'' +
                ", sfsb=" + sfsb +
                ", lcscsfxx=" + lcscsfxx +
                '}';
    }
}
