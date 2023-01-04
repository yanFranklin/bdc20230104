package cn.gtmap.realestate.register.ui.core.vo;

import cn.gtmap.realestate.common.core.domain.BdcJzqDO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * *
 *
 * @author <a href="mailto:liaoxiang@gtmap.cn>liaoxiang</a>"
 * @version 1.0, 2019/1/2
 * @description 登记簿居住权VO
 */
public class BdcdjbJzqVO extends BdcJzqDO {
    /**
     * 居住权人
     */
    String jzqr;
    /**
     * 居住权人证件种类
     */
    String jzqrzjzl;
    /**
     * 居住权人证件号
     */
    String jzqrzjh;
    /**
     * 义务人
     */
    String ywr;
    /**
     * 不动产权证明号
     */
    String bdcqzmh;
    /**
     * 登记类型
     */
    String djlxmc;

    /**
     * 居住权起始日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    Date jzqksrq;

    /**
     * 居住权结束日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    Date jzqjsrq;

    /**
     * 登记日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    Date djrq;

    public String getJzqr() {
        return jzqr;
    }

    public void setJzqr(String jzqr) {
        this.jzqr = jzqr;
    }

    public String getJzqrzjzl() {
        return jzqrzjzl;
    }

    public void setJzqrzjzl(String jzqrzjzl) {
        this.jzqrzjzl = jzqrzjzl;
    }

    public String getJzqrzjh() {
        return jzqrzjh;
    }

    public void setJzqrzjh(String jzqrzjh) {
        this.jzqrzjh = jzqrzjh;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public String getBdcqzmh() {
        return bdcqzmh;
    }

    public void setBdcqzmh(String bdcqzmh) {
        this.bdcqzmh = bdcqzmh;
    }

    public String getDjlxmc() {
        return djlxmc;
    }

    public void setDjlxmc(String djlxmc) {
        this.djlxmc = djlxmc;
    }

    public Date getJzqksrq() {
        return jzqksrq;
    }

    public void setJzqksrq(Date jzqksrq) {
        this.jzqksrq = jzqksrq;
    }

    public Date getJzqjsrq() {
        return jzqjsrq;
    }

    public void setJzqjsrq(Date jzqjsrq) {
        this.jzqjsrq = jzqjsrq;
    }

    public Date getDjrq() {
        return djrq;
    }

    public void setDjrq(Date djrq) {
        this.djrq = djrq;
    }
}

