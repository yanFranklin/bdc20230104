package cn.gtmap.realestate.exchange.core.dto.wwsq.init.zydy;

import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTOForZhlc;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.InitZwrXxDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.InitSjrxx;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.dydj.InitRequestSfxx;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.zy.InitRequestLzr;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-05
 * @description
 */
public class InitZyDyRequestDyxx {

    private String zwlxjsqx;

    private String zwlxksqx;

    private String xlsqdjlx;

    private String xmid;

    private List<InitZyDyRequestQlrxx> qlrxx;

    private String dyfs;

    private String bdbzzqse;

    //    private List<InitFjxxDTO> fjxx;
    private List<FjclDTOForZhlc> fjxx;

    @ApiModelProperty(value = "物流信息")
    private List<InitSjrxx> wlxx;

    private String djyy;

    /**
     * 盐城【35802】增加债务人信息
     */
    private List<InitZwrXxDTO> zwrxx;

    /**
     * 36524 【盐城】互联网+接口增加获取推送担保范围信息
     */
    private String dbfw;

    /**
     * 37185 【南通大市】登记平台抵押权首次登记（组合贷款）流程附记取值规则修改
     * 组合流程增加附记字段，用于记录银行推送的附记信息
     */
    private String fj;


    //是否禁止抵押期间抵押物转让 1：禁止，0：同意
    @ApiModelProperty(value = "是否禁止抵押期间抵押物转让")
    private String sfjzdyqjdywzr;

    @ApiModelProperty(value = "最高债权额")
    private String zgzqe;

    @ApiModelProperty(value = "贷款方式")
    private String dkfs;

    @ApiModelProperty(value = "贷款方式名称")
    private String dkfsmc;

    /**
     * 抵押金额类型代码
     */
    private String dyjelxdm;

    /**
     * 抵押金额类型名称
     */
    private String dyjelxmc;

    /**
     * 抵押顺位
     */
    private String dysw;

    /**
     * 不动产价值
     */
    private String bdcjz;

    /**
     * 收费信息
     */
    private List<InitRequestSfxx> sfxx;

    // 盐城领证人信息
    private List<InitRequestLzr> lzrxx;

    public String getBdcjz() {
        return bdcjz;
    }

    public void setBdcjz(String bdcjz) {
        this.bdcjz = bdcjz;
    }

    public List<InitRequestSfxx> getSfxx() {
        return sfxx;
    }

    public void setSfxx(List<InitRequestSfxx> sfxx) {
        this.sfxx = sfxx;
    }

    public String getDyjelxdm() {
        return dyjelxdm;
    }

    public void setDyjelxdm(String dyjelxdm) {
        this.dyjelxdm = dyjelxdm;
    }

    public String getDyjelxmc() {
        return dyjelxmc;
    }

    public void setDyjelxmc(String dyjelxmc) {
        this.dyjelxmc = dyjelxmc;
    }

    public String getDkfs() {
        return dkfs;
    }

    public void setDkfs(String dkfs) {
        this.dkfs = dkfs;
    }

    public String getDkfsmc() {
        return dkfsmc;
    }

    public void setDkfsmc(String dkfsmc) {
        this.dkfsmc = dkfsmc;
    }

    public List<InitSjrxx> getWlxx() {
        return wlxx;
    }

    public void setWlxx(List<InitSjrxx> wlxx) {
        this.wlxx = wlxx;
    }

    public String getZgzqe() {
        return zgzqe;
    }

    public void setZgzqe(String zgzqe) {
        this.zgzqe = zgzqe;
    }

    public String getSfjzdyqjdywzr() {
        return sfjzdyqjdywzr;
    }

    public void setSfjzdyqjdywzr(String sfjzdyqjdywzr) {
        this.sfjzdyqjdywzr = sfjzdyqjdywzr;
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

    public String getZwlxjsqx() {
        return zwlxjsqx;
    }

    public void setZwlxjsqx(String zwlxjsqx) {
        this.zwlxjsqx = zwlxjsqx;
    }

    public String getZwlxksqx() {
        return zwlxksqx;
    }

    public void setZwlxksqx(String zwlxksqx) {
        this.zwlxksqx = zwlxksqx;
    }

    public String getXlsqdjlx() {
        return xlsqdjlx;
    }

    public void setXlsqdjlx(String xlsqdjlx) {
        this.xlsqdjlx = xlsqdjlx;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public List<InitZyDyRequestQlrxx> getQlrxx() {
        return qlrxx;
    }

    public void setQlrxx(List<InitZyDyRequestQlrxx> qlrxx) {
        this.qlrxx = qlrxx;
    }

    public String getDyfs() {
        return dyfs;
    }

    public void setDyfs(String dyfs) {
        this.dyfs = dyfs;
    }

    public String getBdbzzqse() {
        return bdbzzqse;
    }

    public void setBdbzzqse(String bdbzzqse) {
        this.bdbzzqse = bdbzzqse;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public List<InitZwrXxDTO> getZwrxx() {
        return zwrxx;
    }

    public void setZwrxx(List<InitZwrXxDTO> zwrxx) {
        this.zwrxx = zwrxx;
    }

    public List<FjclDTOForZhlc> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<FjclDTOForZhlc> fjxx) {
        this.fjxx = fjxx;
    }

    public List<InitRequestLzr> getLzrxx() {
        return lzrxx;
    }

    public void setLzrxx(List<InitRequestLzr> lzrxx) {
        this.lzrxx = lzrxx;
    }

    public String getDysw() {
        return dysw;
    }

    public void setDysw(String dysw) {
        this.dysw = dysw;
    }

    @Override
    public String toString() {
        return "InitZyDyRequestDyxx{" +
                "zwlxjsqx='" + zwlxjsqx + '\'' +
                ", zwlxksqx='" + zwlxksqx + '\'' +
                ", xlsqdjlx='" + xlsqdjlx + '\'' +
                ", xmid='" + xmid + '\'' +
                ", qlrxx=" + qlrxx +
                ", dyfs='" + dyfs + '\'' +
                ", bdbzzqse='" + bdbzzqse + '\'' +
                ", fjxx=" + fjxx +
                ", wlxx=" + wlxx +
                ", djyy='" + djyy + '\'' +
                ", zwrxx=" + zwrxx +
                ", dbfw='" + dbfw + '\'' +
                ", fj='" + fj + '\'' +
                ", sfjzdyqjdywzr='" + sfjzdyqjdywzr + '\'' +
                ", zgzqe='" + zgzqe + '\'' +
                ", dkfs='" + dkfs + '\'' +
                ", dkfsmc='" + dkfsmc + '\'' +
                ", dyjelxdm='" + dyjelxdm + '\'' +
                ", dyjelxmc='" + dyjelxmc + '\'' +
                ", dysw='" + dysw + '\'' +
                ", bdcjz='" + bdcjz + '\'' +
                ", sfxx=" + sfxx +
                ", lzrxx=" + lzrxx +
                '}';
    }
}
