package cn.gtmap.realestate.exchange.core.dto.wwsq.jtBdcxxTycx.response;

import java.util.Date;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 1.0, 2022/10/21 14:54
 * @description 家庭不动产信息通用查询请求响应DTO
 */
public class JtBdcxxTycxResponseDTO {

    /**
     * 项目ID
     */
    private String xmid;

    /**
     * 工作流实例ID
     */
    private String gzlslid;
    /**
     * 不动产单元号
     */
    private String bdcdyh;
    /**
     * 产权证号
     */
    private String bdcqzh;
    /**
     * 权利人
     */
    private String qlr;
    /**
     * 权利人证件种类
     */
    private String qlrzjzl;
    /**
     * 权利人证件号
     */
    private String qlrzjh;
    /**
     * 义务人
     */
    private String ywr;
    /**
     * 义务人证件种类
     */
    private String ywrzjzl;
    /**
     * 义务人证件号
     */
    private String ywrzjh;
    /**
     * 登记类型
     */
    private String djlx;
    /**
     * 权利类型
     */
    private String qllx;
    /**
     * 权属状态
     */
    private String qszt;
    /**
     * 规划用途
     */
    private String ghyt;
    /**
     * 坐落
     */
    private String zl;
    /**
     * 建筑面积
     */
    private Double jzmj;
    /**
     * 登记时间
     */
    private Date djsj;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
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

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getQlrzjzl() {
        return qlrzjzl;
    }

    public void setQlrzjzl(String qlrzjzl) {
        this.qlrzjzl = qlrzjzl;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public String getYwrzjzl() {
        return ywrzjzl;
    }

    public void setYwrzjzl(String ywrzjzl) {
        this.ywrzjzl = ywrzjzl;
    }

    public String getYwrzjh() {
        return ywrzjh;
    }

    public void setYwrzjh(String ywrzjh) {
        this.ywrzjh = ywrzjh;
    }

    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    @Override
    public String toString() {
        return "JtBdcxxTycxResponseDTO{" +
                "xmid='" + xmid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", qlr='" + qlr + '\'' +
                ", qlrzjzl='" + qlrzjzl + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", ywr='" + ywr + '\'' +
                ", ywrzjzl='" + ywrzjzl + '\'' +
                ", ywrzjh='" + ywrzjh + '\'' +
                ", djlx='" + djlx + '\'' +
                ", qllx='" + qllx + '\'' +
                ", qszt='" + qszt + '\'' +
                ", ghyt='" + ghyt + '\'' +
                ", zl='" + zl + '\'' +
                ", jzmj=" + jzmj +
                ", djsj=" + djsj +
                '}';
    }
}
