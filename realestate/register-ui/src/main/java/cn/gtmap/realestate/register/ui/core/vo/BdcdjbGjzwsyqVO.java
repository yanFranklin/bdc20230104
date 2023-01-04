package cn.gtmap.realestate.register.ui.core.vo;

import cn.gtmap.realestate.common.core.annotations.Zd;
import cn.gtmap.realestate.common.core.domain.BdcGjzwsyqDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDjlxDO;
import cn.gtmap.realestate.common.core.domain.BdcZdGjzwlxDO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen/a>"
 * @version 1.0, 2019/1/3
 * @description
 */
public class BdcdjbGjzwsyqVO extends BdcGjzwsyqDO {
    /**
     * 所有权人
     */
    private String gjzwsyqr;
    /**
     * 权利人证件种类
     */
    private String qlrzjzl;
    /**
     * 权利人证件号
     */
    private String qlrzjh;
    /**
     * 权利人类型
     */
    private String qlrlx;
    /**
     * 不动产权证
     */
    private String bdcqzh;

    @Zd(table = "bdc_zd_djlx", tableClass = BdcZdDjlxDO.class)
    private String djlxmc;

    @Zd(table = "bdc_zd_gjzwlx", tableClass = BdcZdGjzwlxDO.class)
    private String gjzwlxmc;

    /**
     * 土地海域使用起始日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date tdhysyqsrq;
    /**
     * 土地海域使用结束日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date tdhysyjsrq;
    /**
     * 登记日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date djrq;

    /**
     * 注销登记日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    Date zxdjrq;

    public Date getZxdjrq() {
        return zxdjrq;
    }

    public void setZxdjrq(Date zxdjrq) {
        this.zxdjrq = zxdjrq;
    }

    @Override
    public String toString() {
        return "BdcdjbGjzwsyqVO{" +
                "gjzwsyqr='" + gjzwsyqr + '\'' +
                ", qlrzjzl='" + qlrzjzl + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", qlrlx='" + qlrlx + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", djlxmc='" + djlxmc + '\'' +
                ", gjzwlxmc='" + gjzwlxmc + '\'' +
                ", tdhysyqsrq=" + tdhysyqsrq +
                ", tdhysyjsrq=" + tdhysyjsrq +
                ", djrq=" + djrq +
                ", zxdjrq=" + zxdjrq +
                '}';
    }

    public String getGjzwsyqr() {
        return gjzwsyqr;
    }

    public void setGjzwsyqr(String gjzwsyqr) {
        this.gjzwsyqr = gjzwsyqr;
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

    public String getQlrlx() {
        return qlrlx;
    }

    public void setQlrlx(String qlrlx) {
        this.qlrlx = qlrlx;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public Date getTdhysyqsrq() {
        return tdhysyqsrq;
    }

    public void setTdhysyqsrq(Date tdhysyqsrq) {
        this.tdhysyqsrq = tdhysyqsrq;
    }

    public Date getTdhysyjsrq() {
        return tdhysyjsrq;
    }

    public void setTdhysyjsrq(Date tdhysyjsrq) {
        this.tdhysyjsrq = tdhysyjsrq;
    }

    public Date getDjrq() {
        return djrq;
    }

    public void setDjrq(Date djrq) {
        this.djrq = djrq;
    }

    public String getDjlxmc() {
        return djlxmc;
    }

    public void setDjlxmc(String djlxmc) {
        this.djlxmc = djlxmc;
    }

    public String getGjzwlxmc() {
        return gjzwlxmc;
    }

    public void setGjzwlxmc(String gjzwlxmc) {
        this.gjzwlxmc = gjzwlxmc;
    }

}
