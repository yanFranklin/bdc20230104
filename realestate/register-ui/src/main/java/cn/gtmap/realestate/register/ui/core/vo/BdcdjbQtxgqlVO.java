package cn.gtmap.realestate.register.ui.core.vo;

import cn.gtmap.realestate.common.core.domain.BdcQtxgqlDO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/1/3
 * @description 不动产登记簿其他相关权利
 */
public class BdcdjbQtxgqlVO extends BdcQtxgqlDO {
    /**
     * 权利人
     */
    String qlr;
    /**
     * 权利人证件号
     */
    String qlrzjh;
    /**
     * 权利人证件种类
     */
    String qlrzjzl;

    /**
     * 权利人类型
     */
    String qlrlx;

    /**
     * 不动产权证
     */
    String bdcqzh;

    /**
     * 登记类型名称
     */
    String djlxmc;

    /**
     * 权利类型名称
     */
    String qllxmc;
    /**
     * 权利起始日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    Date qlqsrq;

    /**
     * 权利结束日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")

    Date qljsrq;

    /**
     * 登记日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    Date djrq;

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
        return "BdcdjbQtxgqlVO{" +
                "qlr='" + qlr + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", qlrzjzl='" + qlrzjzl + '\'' +
                ", qlrlx='" + qlrlx + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", djlxmc='" + djlxmc + '\'' +
                ", qllxmc='" + qllxmc + '\'' +
                ", qlqsrq=" + qlqsrq +
                ", qljsrq=" + qljsrq +
                ", djrq=" + djrq +
                ", zxdjrq=" + zxdjrq +
                '}';
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

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getQlrzjzl() {
        return qlrzjzl;
    }

    public void setQlrzjzl(String qlrzjzl) {
        this.qlrzjzl = qlrzjzl;
    }

    public String getQlrlx() {
        return qlrlx;
    }

    public void setQlrlx(String qlrlx) {
        this.qlrlx = qlrlx;
    }

    public Date getQlqsrq() {
        return qlqsrq;
    }

    public void setQlqsrq(Date qlqsrq) {
        this.qlqsrq = qlqsrq;
    }

    public Date getQljsrq() {
        return qljsrq;
    }

    public void setQljsrq(Date qljsrq) {
        this.qljsrq = qljsrq;
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

    public String getQllxmc() {
        return qllxmc;
    }

    public void setQllxmc(String qllxmc) {
        this.qllxmc = qllxmc;
    }
}
