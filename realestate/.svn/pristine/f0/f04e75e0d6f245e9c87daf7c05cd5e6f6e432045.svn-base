package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/10/31
 * @description 不动产受理收件材料
 */
@Table(name = "BDC_SL_SJCL")
@ApiModel(value = "BdcSlSjclDO", description = "不动产受理收件材料")
public class BdcSlSjclDO implements Serializable {
    private static final long serialVersionUID = 2508031530770726451L;
    @Id
    @ApiModelProperty(value = "收件材料ID")
    private String sjclid;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;
    @ApiModelProperty(value = "材料名称")
    private String clmc;
    @ApiModelProperty(value = "份数")
    private Integer fs;
    @ApiModelProperty(value = "序号")
    private Integer xh;
    @ApiModelProperty(value = "收件类型")
    private Integer sjlx;
    @ApiModelProperty(value = "收取部门")
    private String sqbm;
    @ApiModelProperty(value = "默认份数")
    private Integer mrfs;
    @ApiModelProperty(value = "是否收缴收验")
    private String sfsjsy;
    @ApiModelProperty(value = "是否额外收件")
    private String sfewsj;
    @ApiModelProperty(value = "是否补充收件")
    private String sfbcsj;
    @ApiModelProperty(value = "页数")
    private Integer ys;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "文件中心NodeId")
    private String wjzxid;
    @ApiModelProperty(value = "需补材料份数")
    private Integer xbclfs;
    @ApiModelProperty(value = "是否涉税")
    private Integer sfss;
    @ApiModelProperty(value = "登记小类")
    private String djxl;
    @ApiModelProperty(value = "顺序号--组合流程两个相同的登记小类区分用")
    private Integer sxh;
    @ApiModelProperty(value = "是否必传（1：必传，0：不必传）")
    private Integer sfbc;
    @ApiModelProperty(value = "是否批注")
    private Integer sfpz;
    @ApiModelProperty(value = "批注")
    private String pz;

    public String getSjclid() {
        return sjclid;
    }

    public void setSjclid(String sjclid) {
        this.sjclid = sjclid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getClmc() {
        return clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
    }

    public Integer getFs() {
        return fs;
    }

    public void setFs(Integer fs) {
        this.fs = fs;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public Integer getSjlx() {
        return sjlx;
    }

    public void setSjlx(Integer sjlx) {
        this.sjlx = sjlx;
    }

    public Integer getMrfs() {
        return mrfs;
    }

    public void setMrfs(Integer mrfs) {
        this.mrfs = mrfs;
    }

    public String getSfsjsy() {
        return sfsjsy;
    }

    public void setSfsjsy(String sfsjsy) {
        this.sfsjsy = sfsjsy;
    }

    public String getSfewsj() {
        return sfewsj;
    }

    public void setSfewsj(String sfewsj) {
        this.sfewsj = sfewsj;
    }

    public String getSfbcsj() {
        return sfbcsj;
    }

    public void setSfbcsj(String sfbcsj) {
        this.sfbcsj = sfbcsj;
    }

    public Integer getYs() {
        return ys;
    }

    public void setYs(Integer ys) {
        this.ys = ys;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getWjzxid() {
        return wjzxid;
    }

    public void setWjzxid(String wjzxid) {
        this.wjzxid = wjzxid;
    }

    public Integer getXbclfs() {
        return xbclfs;
    }

    public void setXbclfs(Integer xbclfs) {
        this.xbclfs = xbclfs;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public Integer getSfss() {
        return sfss;
    }

    public void setSfss(Integer sfss) {
        this.sfss = sfss;
    }

    public String getSqbm() {
        return sqbm;
    }

    public void setSqbm(String sqbm) {
        this.sqbm = sqbm;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    public Integer getSfbc() {
        return sfbc;
    }

    public void setSfbc(Integer sfbc) {
        this.sfbc = sfbc;
    }

    public Integer getSfpz() {
        return sfpz;
    }

    public void setSfpz(Integer sfpz) {
        this.sfpz = sfpz;
    }

    public String getPz() {
        return pz;
    }

    public void setPz(String pz) {
        this.pz = pz;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BdcSlSjclDO.class.getSimpleName() + "[", "]")
                .add("sjclid='" + sjclid + "'")
                .add("xmid='" + xmid + "'")
                .add("gzlslid='" + gzlslid + "'")
                .add("clmc='" + clmc + "'")
                .add("fs=" + fs)
                .add("xh=" + xh)
                .add("sjlx=" + sjlx)
                .add("sqbm='" + sqbm + "'")
                .add("mrfs=" + mrfs)
                .add("sfsjsy='" + sfsjsy + "'")
                .add("sfewsj='" + sfewsj + "'")
                .add("sfbcsj='" + sfbcsj + "'")
                .add("ys=" + ys)
                .add("bz='" + bz + "'")
                .add("wjzxid='" + wjzxid + "'")
                .add("xbclfs=" + xbclfs)
                .add("sfss=" + sfss)
                .add("djxl='" + djxl + "'")
                .add("sxh=" + sxh)
                .add("sfbc=" + sfbc)
                .toString();
    }
}
