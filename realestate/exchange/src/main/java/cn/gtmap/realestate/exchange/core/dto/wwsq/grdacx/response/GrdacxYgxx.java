package cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.response;

import java.util.Date;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0  2020-12-18
 * @description  个人档案查询预告信息
 */
public class GrdacxYgxx {

    /**
     * 预告登记证明号
     */
    private String ygdjzmh;

    /**
     *  权利人
     */
    private String qlr;

    /**
     * 预告登记种类
     */
    private String ygdjzl;

    /**
     * 权利期限
     */
    private String zwlxqssj;

    private String zwlxjssj;

    /**
     * 附记
     */
    private String fj;

    /**
     * 登记时间
     */
    private String djsj;


    public String getYgdjzmh() {
        return ygdjzmh;
    }

    public void setYgdjzmh(String ygdjzmh) {
        this.ygdjzmh = ygdjzmh;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getYgdjzl() {
        return ygdjzl;
    }

    public void setYgdjzl(String ygdjzl) {
        this.ygdjzl = ygdjzl;
    }

    public String getZwlxqssj() {
        return zwlxqssj;
    }

    public void setZwlxqssj(String zwlxqssj) {
        this.zwlxqssj = zwlxqssj;
    }

    public String getZwlxjssj() {
        return zwlxjssj;
    }

    public void setZwlxjssj(String zwlxjssj) {
        this.zwlxjssj = zwlxjssj;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getDjsj() {
        return djsj;
    }

    public void setDjsj(String djsj) {
        this.djsj = djsj;
    }

    @Override
    public String toString() {
        return "GrdacxYgxx{" +
                "ygdjzmh='" + ygdjzmh + '\'' +
                ", qlr='" + qlr + '\'' +
                ", ygdjzl='" + ygdjzl + '\'' +
                ", zwlxqssj='" + zwlxqssj + '\'' +
                ", zwlxjssj='" + zwlxjssj + '\'' +
                ", fj='" + fj + '\'' +
                ", djsj=" + djsj +
                '}';
    }
}
