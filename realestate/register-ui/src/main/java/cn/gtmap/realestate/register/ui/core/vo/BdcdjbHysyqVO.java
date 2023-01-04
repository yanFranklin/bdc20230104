package cn.gtmap.realestate.register.ui.core.vo;

import cn.gtmap.realestate.common.core.annotations.Zd;
import cn.gtmap.realestate.common.core.domain.BdcHysyqDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDjlxDO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen/a>"
 * @version 1.0, 2019/1/3
 * @description
 */
public class BdcdjbHysyqVO extends BdcHysyqDO {
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
     * 权利人类型
     */
    private String qlrlx;
    /**
     * 不动产权证
     */
    private String bdcqzh;

    @Zd(table = "bdc_zd_djlx", tableClass = BdcZdDjlxDO.class)
    private String djlxmc;

    /**
     * 使用权起始日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date syqqsrq;

    /**
     * 使用权结束日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date syqjsrq;

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


    public String getQlr() {
        return qlr;
    }

    public Date getZxdjrq() {
        return zxdjrq;
    }

    public void setZxdjrq(Date zxdjrq) {
        this.zxdjrq = zxdjrq;
    }

    @Override
    public String toString() {
        return "BdcdjbHysyqVO{" +
                "qlr='" + qlr + '\'' +
                ", qlrzjzl='" + qlrzjzl + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", qlrlx='" + qlrlx + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", djlxmc='" + djlxmc + '\'' +
                ", syqqsrq=" + syqqsrq +
                ", syqjsrq=" + syqjsrq +
                ", djrq=" + djrq +
                ", zxdjrq=" + zxdjrq +
                '}';
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

    public Date getSyqqsrq() {
        return syqqsrq;
    }

    public void setSyqqsrq(Date syqqsrq) {
        this.syqqsrq = syqqsrq;
    }

    public Date getSyqjsrq() {
        return syqjsrq;
    }

    public void setSyqjsrq(Date syqjsrq) {
        this.syqjsrq = syqjsrq;
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

}
