package cn.gtmap.realestate.etl.core.domian;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 2020/06/29,1.0
 * @description 大数据局共享实体do
 */
@Table(name = "gt_cf_fw")
public class GtCfFwDo {

    /**
     * 查封机构
     */
    private String cfjg;
    /**
     * 查封文号
     */
    private String cfwh;
    /**
     * 查封开始时间
     */
    private Date cfqssj;
    /**
     * 查封结束时间
     */
    private Date cfjssj;
    /**
     * 解封机构
     */
    private String jfjg;
    /**
     * 解封文号
     */
    private String jfwh;
    /**
     * 解封登记时间
     */
    private Date jfdjsj;
    /**
     * 是否有效
     */
    private String sfyx;
    /**
     * 宗地四至东
     */
    private String zdszd;
    /**
     * 宗地四至南
     */
    private String zdszn;
    /**
     * 宗地四至西
     */
    private String zdszx;
    /**
     * 宗地四至北
     */
    private String zdszb;
    /**
     * 不动产单元号
     */
    private String bdcdyh;
    /**
     * 坐落
     */
    private String zl;
    /**
     * 主键
     */
    @Id
    private Integer id;
    /**
     * 插入时间
     */
    private Date crsj;
    /**
     * 更新时间
     */
    private Date gxsj;


    public String getCfjg() {
        return cfjg;
    }

    public void setCfjg(String cfjg) {
        this.cfjg = cfjg;
    }


    public String getCfwh() {
        return cfwh;
    }

    public void setCfwh(String cfwh) {
        this.cfwh = cfwh;
    }


    public Date getCfqssj() {
        return cfqssj;
    }

    public void setCfqssj(Date cfqssj) {
        this.cfqssj = cfqssj;
    }


    public Date getCfjssj() {
        return cfjssj;
    }

    public void setCfjssj(Date cfjssj) {
        this.cfjssj = cfjssj;
    }


    public String getJfjg() {
        return jfjg;
    }

    public void setJfjg(String jfjg) {
        this.jfjg = jfjg;
    }


    public String getJfwh() {
        return jfwh;
    }

    public void setJfwh(String jfwh) {
        this.jfwh = jfwh;
    }


    public Date getJfdjsj() {
        return jfdjsj;
    }

    public void setJfdjsj(Date jfdjsj) {
        this.jfdjsj = jfdjsj;
    }


    public String getSfyx() {
        return sfyx;
    }

    public void setSfyx(String sfyx) {
        this.sfyx = sfyx;
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getCrsj() {
        return crsj;
    }

    public void setCrsj(Date crsj) {
        this.crsj = crsj;
    }


    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

}
