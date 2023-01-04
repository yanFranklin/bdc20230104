package cn.gtmap.realestate.common.core.vo.register.ui;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.StringJoiner;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/5/28
 * @description 不动产单元VO
 */
public class BdcBdcdyVO {
    /**
     * 工作流实例ID
     */
    String gzlslid;
    /**
     * 项目ID
     */
    String xmid;
    /**
     * 权利人名称
     */
    String qlrmc;
    /**
     * 不动产单元号
     */
    String bdcdyh;

    /**
     * 坐落
     */
    String zl;
    /**
     * 不动产单元唯一编码
     */
    String bdcdywybh;
    /**
     * 幢号
     */
    String zh;
    /**
     * 竣工时间
     */
    String jgsj;
    /**
     * 总层数
     */
    Integer zcs;
    /**
     * 所在层
     */
    Integer szc;
    /**
     * 所在名义层
     */
    String szmyc;
    /**
     * 宗地宗海面积
     */
    Double zdzhmj;

    /**
     * 定着物面积
     */
    Double dzwmj;

    /**
     * 宗地宗海用途
     */
    String zdzhyt;
    /**
     * 宗地宗海用途2
     */
    String zdzhyt2;
    /**
     * 宗地宗海用途3
     */
    String zdzhyt3;
    /**
     * 定着物用途
     */
    Integer dzwyt;
    /**
     * 权利类型
     */
    Integer qllx;
    /**
     * 不动产单元房屋类型
     */
    Integer bdcdyfwlx;
    /**
     * 原产权证号
     */
    String ycqzh;

    /**
     * 土地权利面积
     */
    Double tdqlmj;

    /**
     * 是否主房
     */
    Integer sfzf;

    /**
     * 权利性质
     */
    String qlxz;
    /**
     * 原系统产权证号
     */
    String yxtcqzh;

    /**
     * 预售房屋编码
     */
    String ysfwbm;

    /*
    * 宗地使用类型
    * */
    Integer zdsylx;

    /*
     * 宗地四至东
     * */
    String zdszd;

    /*
     * 宗地四至南
     * */
    String zdszn;

    /*
     * 宗地四至西
     * */
    String zdszx;

    /*
     * 宗地四至北
     * */
    String zdszb;

    /*
     * 登记原因
     * */
    String djyy;

    /*
     * 交易合同号
     * */
    String jyhth;

    /**
     * 房间号
     */
    String fjh;

    /**
     * 房屋结构
     */
    String fwjg;
    /**
     * 土地使用起始时间
     */
    Date tdsyqssj;
    /**
     * 土地使用结束时间
     */
    Date tdsyjssj;
    /**
     * 土地使用起始时间2
     */
    Date tdsyqssj2;
    /**
     * 土地使用结束时间2
     */
    Date tdsyjssj2;
    /**
     * 土地使用起始时间3
     */
    Date tdsyqssj3;
    /**
     * 土地使用结束时间3
     */
    Date tdsyjssj3;

    @ApiModelProperty(value = "工作流定义ID")
    private String gzldyid;


    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "房屋性质")
    private Integer fwxz;

    /*
     * 用途和使用期限拼接展示
     * */
    @ApiModelProperty(value = "用途和使用期限拼接")
    private String ytSyqx;

    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    public Integer getSfzf() {
        return sfzf;
    }

    public void setSfzf(Integer sfzf) {
        this.sfzf = sfzf;
    }

    public Double getDzwmj() {
        return dzwmj;
    }

    public void setDzwmj(Double dzwmj) {
        this.dzwmj = dzwmj;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Double getZdzhmj() {
        return zdzhmj;
    }

    public void setZdzhmj(Double zdzhmj) {
        this.zdzhmj = zdzhmj;
    }

    public String getZdzhyt() {
        return zdzhyt;
    }

    public void setZdzhyt(String zdzhyt) {
        this.zdzhyt = zdzhyt;
    }

    public Integer getDzwyt() {
        return dzwyt;
    }

    public void setDzwyt(Integer dzwyt) {
        this.dzwyt = dzwyt;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
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

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getJgsj() {
        return jgsj;
    }

    public void setJgsj(String jgsj) {
        this.jgsj = jgsj;
    }

    public Integer getZcs() {
        return zcs;
    }

    public void setZcs(Integer zcs) {
        this.zcs = zcs;
    }

    public Integer getSzc() {
        return szc;
    }

    public void setSzc(Integer szc) {
        this.szc = szc;
    }

    public String getSzmyc() {
        return szmyc;
    }

    public void setSzmyc(String szmyc) {
        this.szmyc = szmyc;
    }

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
    }

    public Integer getBdcdyfwlx() {
        return bdcdyfwlx;
    }

    public void setBdcdyfwlx(Integer bdcdyfwlx) {
        this.bdcdyfwlx = bdcdyfwlx;
    }

    public String getYcqzh() {
        return ycqzh;
    }

    public void setYcqzh(String ycqzh) {
        this.ycqzh = ycqzh;
    }

    public Double getTdqlmj() {
        return tdqlmj;
    }

    public void setTdqlmj(Double tdqlmj) {
        this.tdqlmj = tdqlmj;
    }

    public String getYxtcqzh() {
        return yxtcqzh;
    }

    public void setYxtcqzh(String yxtcqzh) {
        this.yxtcqzh = yxtcqzh;
    }

    public String getYsfwbm() {
        return ysfwbm;
    }

    public void setYsfwbm(String ysfwbm) {
        this.ysfwbm = ysfwbm;
    }

    public Integer getZdsylx() {
        return zdsylx;
    }

    public void setZdsylx(Integer zdsylx) {
        this.zdsylx = zdsylx;
    }

    public String getZdszd() {
        return zdszd;
    }

    public void setZdszd(String zdszd) {
        this.zdszd = zdszd;
    }

    public String getZdszn() {
        return zdszn;
    }

    public void setZdszn(String zdszn) {
        this.zdszn = zdszn;
    }

    public String getZdszx() {
        return zdszx;
    }

    public void setZdszx(String zdszx) {
        this.zdszx = zdszx;
    }

    public String getZdszb() {
        return zdszb;
    }

    public void setZdszb(String zdszb) {
        this.zdszb = zdszb;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public String getJyhth() {
        return jyhth;
    }

    public void setJyhth(String jyhth) {
        this.jyhth = jyhth;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    public Date getTdsyqssj() {
        return tdsyqssj;
    }

    public void setTdsyqssj(Date tdsyqssj) {
        this.tdsyqssj = tdsyqssj;
    }

    public Date getTdsyjssj() {
        return tdsyjssj;
    }

    public void setTdsyjssj(Date tdsyjssj) {
        this.tdsyjssj = tdsyjssj;
    }

    public Date getTdsyqssj2() {
        return tdsyqssj2;
    }

    public void setTdsyqssj2(Date tdsyqssj2) {
        this.tdsyqssj2 = tdsyqssj2;
    }

    public Date getTdsyjssj2() {
        return tdsyjssj2;
    }

    public void setTdsyjssj2(Date tdsyjssj2) {
        this.tdsyjssj2 = tdsyjssj2;
    }

    public Date getTdsyqssj3() {
        return tdsyqssj3;
    }

    public void setTdsyqssj3(Date tdsyqssj3) {
        this.tdsyqssj3 = tdsyqssj3;
    }

    public Date getTdsyjssj3() {
        return tdsyjssj3;
    }

    public void setTdsyjssj3(Date tdsyjssj3) {
        this.tdsyjssj3 = tdsyjssj3;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public Integer getFwxz() {
        return fwxz;
    }

    public void setFwxz(Integer fwxz) {
        this.fwxz = fwxz;
    }

    public String getZdzhyt2() {
        return zdzhyt2;
    }

    public void setZdzhyt2(String zdzhyt2) {
        this.zdzhyt2 = zdzhyt2;
    }

    public String getZdzhyt3() {
        return zdzhyt3;
    }

    public void setZdzhyt3(String zdzhyt3) {
        this.zdzhyt3 = zdzhyt3;
    }

    public String getYtSyqx() {
        return ytSyqx;
    }

    public void setYtSyqx(String ytSyqx) {
        this.ytSyqx = ytSyqx;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BdcBdcdyVO.class.getSimpleName() + "[", "]")
                .add("gzlslid='" + gzlslid + "'")
                .add("xmid='" + xmid + "'")
                .add("qlrmc='" + qlrmc + "'")
                .add("bdcdyh='" + bdcdyh + "'")
                .add("zl='" + zl + "'")
                .add("bdcdywybh='" + bdcdywybh + "'")
                .add("zh='" + zh + "'")
                .add("jgsj='" + jgsj + "'")
                .add("zcs=" + zcs)
                .add("szc=" + szc)
                .add("szmyc='" + szmyc + "'")
                .add("zdzhmj=" + zdzhmj)
                .add("dzwmj=" + dzwmj)
                .add("zdzhyt='" + zdzhyt + "'")
                .add("zdzhyt2='" + zdzhyt2 + "'")
                .add("zdzhyt3='" + zdzhyt3 + "'")
                .add("dzwyt=" + dzwyt)
                .add("qllx=" + qllx)
                .add("bdcdyfwlx=" + bdcdyfwlx)
                .add("ycqzh='" + ycqzh + "'")
                .add("tdqlmj=" + tdqlmj)
                .add("sfzf=" + sfzf)
                .add("qlxz='" + qlxz + "'")
                .add("yxtcqzh='" + yxtcqzh + "'")
                .add("ysfwbm='" + ysfwbm + "'")
                .add("zdsylx=" + zdsylx)
                .add("zdszd='" + zdszd + "'")
                .add("zdszn='" + zdszn + "'")
                .add("zdszx='" + zdszx + "'")
                .add("zdszb='" + zdszb + "'")
                .add("djyy='" + djyy + "'")
                .add("jyhth='" + jyhth + "'")
                .add("fjh='" + fjh + "'")
                .add("fwjg='" + fwjg + "'")
                .add("tdsyqssj='" + tdsyqssj + "'")
                .add("tdsyjssj='" + tdsyjssj + "'")
                .add("tdsyqssj2='" + tdsyqssj2 + "'")
                .add("tdsyjssj2='" + tdsyjssj2 + "'")
                .add("tdsyqssj3='" + tdsyqssj3 + "'")
                .add("tdsyjssj3='" + tdsyjssj3 + "'")
                .add("gzldyid='" + gzldyid + "'")
                .add("bdcqzh='" + bdcqzh + "'")
                .add("fwxz='" + fwxz + "'")
                .add("ytSyqx='" + ytSyqx + "'")
                .toString();
    }

}
