package cn.gtmap.realestate.etl.core.domian.wwsq;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 2020/06/01,1.0
 * @description 外网申请材料信息实体do
 */
@Table(name = "gx_ww_sqxx_clxx")
public class GxWwSqxxClxxDo {

    /**
     * 主键
     */
    @Id
    private String clid;
    /**
     * 材料名称
     */
    private String clmc;
    /**
     * 申请信息表主键
     */
    private String sqxxid;
    /**
     * 材料类型
     */
    private String cllx;
    /**
     * 序号
     */
    private String xh;
    /**
     * 项目ID
     */
    private String xmid;
    /**
     * 份数
     */
    private String fs;
    /**
     * 页数
     */
    private String ys;

    @Transient
    private List<GxWwSqxxFjxxDo> fjxx;

    public List<GxWwSqxxFjxxDo> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<GxWwSqxxFjxxDo> fjxx) {
        this.fjxx = fjxx;
    }

    public String getClid() {
        return clid;
    }

    public void setClid(String clid) {
        this.clid = clid;
    }


    public String getClmc() {
        return clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
    }


    public String getSqxxid() {
        return sqxxid;
    }

    public void setSqxxid(String sqxxid) {
        this.sqxxid = sqxxid;
    }


    public String getCllx() {
        return cllx;
    }

    public void setCllx(String cllx) {
        this.cllx = cllx;
    }


    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }


    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getFs() {
        return fs;
    }

    public void setFs(String fs) {
        this.fs = fs;
    }

    public String getYs() {
        return ys;
    }

    public void setYs(String ys) {
        this.ys = ys;
    }

    @Override
    public String toString() {
        return "GxWwSqxxClxxDo{" +
                "clid='" + clid + '\'' +
                ", clmc='" + clmc + '\'' +
                ", sqxxid='" + sqxxid + '\'' +
                ", cllx='" + cllx + '\'' +
                ", xh='" + xh + '\'' +
                ", xmid='" + xmid + '\'' +
                ", fs='" + fs + '\'' +
                ", ys='" + ys + '\'' +
                ", fjxx=" + fjxx +
                '}';
    }
}
