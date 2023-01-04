package cn.gtmap.realestate.etl.core.domian.wwsq;

import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 2020/06/01,1.0
 * @description 外网申请实体do
 */
@Table(name = "gx_ww_sqxx_fjxx")
public class GxWwSqxxFjxxDo {

    /**
     * 主键
     */
    @Id
    private String fjid;
    /**
     * 材料id
     */
    private String clid;
    /**
     * 附件名称
     */
    private String fjmc;
    /**
     * 附件路径
     */
    private String fjlj;
    /**
     * 格式
     */
    private String gs;
    /**
     * 序号
     */
    private String xh;
    /**
     * 附件内容
     */
    private String fjnr;


    public String getFjid() {
        return fjid;
    }

    public void setFjid(String fjid) {
        this.fjid = fjid;
    }


    public String getClid() {
        return clid;
    }

    public void setClid(String clid) {
        this.clid = clid;
    }


    public String getFjmc() {
        return fjmc;
    }

    public void setFjmc(String fjmc) {
        this.fjmc = fjmc;
    }


    public String getFjlj() {
        return fjlj;
    }

    public void setFjlj(String fjlj) {
        this.fjlj = fjlj;
    }


    public String getGs() {
        return gs;
    }

    public void setGs(String gs) {
        this.gs = gs;
    }


    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }


    public String getFjnr() {
        return fjnr;
    }

    public void setFjnr(String fjnr) {
        this.fjnr = fjnr;
    }

    @Override
    public String toString() {
        return "GxWwSqxxFjxxDo{" +
                "fjid='" + fjid + '\'' +
                ", clid='" + clid + '\'' +
                ", fjmc='" + fjmc + '\'' +
                ", fjlj='" + fjlj + '\'' +
                ", gs='" + gs + '\'' +
                ", xh='" + xh + '\'' +
                ", fjnr='" + fjnr + '\'' +
                '}';
    }
}
