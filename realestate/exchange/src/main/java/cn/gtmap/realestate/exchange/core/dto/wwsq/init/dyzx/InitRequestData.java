package cn.gtmap.realestate.exchange.core.dto.wwsq.init.dyzx;

import cn.gtmap.realestate.common.core.dto.exchange.wwsq.InitZwrXxDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.InitFjxxDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.InitSjrxx;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-13
 * @description 抵押注销业务
 */
public class InitRequestData {

    @NotBlank
    private String sqdjlx;

    private String zmh;

    @NotBlank
    private String zmxmid;

    private String zmly;

    @NotBlank
    private String bjbh;

    private String zxyy;

    @NotBlank
    private String bdcdyh;

    @NotBlank
    private String yybmbm;

    private String bdcdybh;

    private String cqzh;

    private String slr;

    private String dyhth;

    private List<InitRequestQlr> qlr;

    private List<InitFjxxDTO> fjxx;

    private List<InitRequestDyr> dyr;

    private String cjyz;

    private String slly;

    private String djyydm;

    private String djyymc;

    // 0不涉税；1涉税
    private String sfss;

    //预审人
    private String ysr;
    // 预审时间
    private String yssj;
    // 预审提示信息
    private String ystsxx;

    private String zl;

    // 预约开始时间
    private String yykssj;

    // 预约结束时间
    private String yyjssj;
    // 收件人信息
    private List<InitSjrxx> wlxx;
    // 申请人id
    private String sqrid;
    // 自动转发
    private String zdzf;
    //是否自动办结
    private String sfzdbj;

    /**
     * 【35802】
     */
    @ApiModelProperty(value = "债务人信息")
    private List<InitZwrXxDTO> zwrxx;

    /**
     * 36524 【盐城】互联网+接口增加获取推送担保范围信息
     */
    private String dbfw;

    /**
     * 36360 【南通大市】登记平台界面字段取值规则修改
     * 银行推送fj字段
     */
    private String fj;

    //41873 【南通大市】登记系统中流程创建接口对xzqdm参数做处理
    private String xzqdm;

    //46842 【南通大市】抵押注销创建接口新增申请人和证件号
    private String sqrmc;
    private String sqrzjh;


    @ApiModelProperty(value = "登簿人")
    private String dbr;

    @ApiModelProperty(value = "复审人")
    private String fsr;

    @ApiModelProperty("申请部门名称")
    private String sqbmmc;

    @ApiModelProperty("业务类型")
    private String ywlx;

    public String getYwlx() {
        return ywlx;
    }

    public void setYwlx(String ywlx) {
        this.ywlx = ywlx;
    }

    public String getSqbmmc() {
        return sqbmmc;
    }

    public void setSqbmmc(String sqbmmc) {
        this.sqbmmc = sqbmmc;
    }

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    public String getFsr() {
        return fsr;
    }

    public void setFsr(String fsr) {
        this.fsr = fsr;
    }


    public String getSqrmc() {
        return sqrmc;
    }

    public void setSqrmc(String sqrmc) {
        this.sqrmc = sqrmc;
    }

    public String getSqrzjh() {
        return sqrzjh;
    }

    public void setSqrzjh(String sqrzjh) {
        this.sqrzjh = sqrzjh;
    }

    public String getXzqdm() {
        return xzqdm;
    }

    public void setXzqdm(String xzqdm) {
        this.xzqdm = xzqdm;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getDbfw() {
        return dbfw;
    }

    public void setDbfw(String dbfw) {
        this.dbfw = dbfw;
    }

    public String getSfzdbj() {
        return sfzdbj;
    }

    public void setSfzdbj(String sfzdbj) {
        this.sfzdbj = sfzdbj;
    }

    public String getZdzf() {
        return zdzf;
    }

    public void setZdzf(String zdzf) {
        this.zdzf = zdzf;
    }

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid;
    }

    public List<InitSjrxx> getWlxx() {
        return wlxx;
    }

    public void setWlxx(List<InitSjrxx> wlxx) {
        this.wlxx = wlxx;
    }

    public String getYykssj() {
        return yykssj;
    }

    public void setYykssj(String yykssj) {
        this.yykssj = yykssj;
    }

    public String getYyjssj() {
        return yyjssj;
    }

    public void setYyjssj(String yyjssj) {
        this.yyjssj = yyjssj;
    }

    public String getYssj() {
        return yssj;
    }

    public void setYssj(String yssj) {
        this.yssj = yssj;
    }

    public String getYstsxx() {
        return ystsxx;
    }

    public void setYstsxx(String ystsxx) {
        this.ystsxx = ystsxx;
    }

    public String getZmh() {
        return zmh;
    }

    public void setZmh(String zmh) {
        this.zmh = zmh;
    }

    public String getZmxmid() {
        return zmxmid;
    }

    public void setZmxmid(String zmxmid) {
        this.zmxmid = zmxmid;
    }

    public String getZmly() {
        return zmly;
    }

    public void setZmly(String zmly) {
        this.zmly = zmly;
    }

    public String getBjbh() {
        return bjbh;
    }

    public void setBjbh(String bjbh) {
        this.bjbh = bjbh;
    }

    public String getZxyy() {
        return zxyy;
    }

    public void setZxyy(String zxyy) {
        this.zxyy = zxyy;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getYybmbm() {
        return yybmbm;
    }

    public void setYybmbm(String yybmbm) {
        this.yybmbm = yybmbm;
    }

    public String getBdcdybh() {
        return bdcdybh;
    }

    public void setBdcdybh(String bdcdybh) {
        this.bdcdybh = bdcdybh;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getSlr() {
        return slr;
    }

    public void setSlr(String slr) {
        this.slr = slr;
    }

    public List<InitRequestQlr> getQlr() {
        return qlr;
    }

    public void setQlr(List<InitRequestQlr> qlr) {
        this.qlr = qlr;
    }

    public List<InitRequestDyr> getDyr() {
        return dyr;
    }

    public void setDyr(List<InitRequestDyr> dyr) {
        this.dyr = dyr;
    }

    public String getSqdjlx() {
        return sqdjlx;
    }

    public void setSqdjlx(String sqdjlx) {
        this.sqdjlx = sqdjlx;
    }

    public String getDyhth() {
        return dyhth;
    }

    public void setDyhth(String dyhth) {
        this.dyhth = dyhth;
    }

    public List<InitFjxxDTO> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<InitFjxxDTO> fjxx) {
        this.fjxx = fjxx;
    }

    public String getCjyz() {
        return cjyz;
    }

    public void setCjyz(String cjyz) {
        this.cjyz = cjyz;
    }

    public String getSlly() {
        return slly;
    }

    public void setSlly(String slly) {
        this.slly = slly;
    }

    public String getDjyydm() {
        return djyydm;
    }

    public void setDjyydm(String djyydm) {
        this.djyydm = djyydm;
    }

    public String getDjyymc() {
        return djyymc;
    }

    public void setDjyymc(String djyymc) {
        this.djyymc = djyymc;
    }

    public String getSfss() {
        return sfss;
    }

    public void setSfss(String sfss) {
        this.sfss = sfss;
    }

    public String getYsr() {
        return ysr;
    }

    public void setYsr(String ysr) {
        this.ysr = ysr;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public List<InitZwrXxDTO> getZwrxx() {
        return zwrxx;
    }

    public void setZwrxx(List<InitZwrXxDTO> zwrxx) {
        this.zwrxx = zwrxx;
    }

    @Override
    public String toString() {
        return "InitRequestData{" +
                "sqdjlx='" + sqdjlx + '\'' +
                ", zmh='" + zmh + '\'' +
                ", zmxmid='" + zmxmid + '\'' +
                ", zmly='" + zmly + '\'' +
                ", bjbh='" + bjbh + '\'' +
                ", zxyy='" + zxyy + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", yybmbm='" + yybmbm + '\'' +
                ", bdcdybh='" + bdcdybh + '\'' +
                ", cqzh='" + cqzh + '\'' +
                ", slr='" + slr + '\'' +
                ", dyhth='" + dyhth + '\'' +
                ", qlr=" + qlr +
                ", fjxx=" + fjxx +
                ", dyr=" + dyr +
                ", cjyz='" + cjyz + '\'' +
                ", slly='" + slly + '\'' +
                ", djyydm='" + djyydm + '\'' +
                ", djyymc='" + djyymc + '\'' +
                ", sfss='" + sfss + '\'' +
                ", ysr='" + ysr + '\'' +
                ", yssj='" + yssj + '\'' +
                ", ystsxx='" + ystsxx + '\'' +
                ", zl='" + zl + '\'' +
                ", yykssj='" + yykssj + '\'' +
                ", yyjssj='" + yyjssj + '\'' +
                ", wlxx=" + wlxx +
                ", sqrid='" + sqrid + '\'' +
                ", zdzf='" + zdzf + '\'' +
                ", sfzdbj='" + sfzdbj + '\'' +
                ", zwrxx=" + zwrxx +
                ", dbfw='" + dbfw + '\'' +
                ", fj='" + fj + '\'' +
                ", xzqdm='" + xzqdm + '\'' +
                ", sqrmc='" + sqrmc + '\'' +
                ", sqrzjh='" + sqrzjh + '\'' +
                ", dbr='" + dbr + '\'' +
                ", fsr='" + fsr + '\'' +
                ", sqbmmc='" + sqbmmc + '\'' +
                ", ywlx='" + ywlx + '\'' +
                '}';
    }
}
