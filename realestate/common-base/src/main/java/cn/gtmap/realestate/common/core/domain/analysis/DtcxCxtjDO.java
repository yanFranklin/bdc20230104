package cn.gtmap.realestate.common.core.domain.analysis;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author by hy.
 * @version 1.0, 2018/8/27
 * @description
 */
@Table(name = "dtcx_cxtj")
public class DtcxCxtjDO {
    @Id
    /**
     * 条件id
     */
    private String tjid;
    /**
     * 查询id
     */
    private String cxid;

    /**
     * 查询代号
     */
    private String cxdh;
    /**
     * 条件字段id
     */
    private String tjzdid;
    /**
     * 条件字段名称
     */
    private String tjzdname;
    /**
     * 默认显示
     */
    private String mrxs;
    /**
     * 条件类型
     */
    private String tjtype;
    /**
     * 对应字典ID
     */
    private String zdid;
    /**
     * 字段对应方式
     */
    private String zddyfs;

    /**
     * 条件用途
     */
    private String tjusage;
    /**
     * 优先级
     */
    private Integer priority;

    public String getTjusage() {
        return tjusage;
    }

    public void setTjusage(String tjusage) {
        this.tjusage = tjusage;
    }

    public String getCxdh() {
        return cxdh;
    }

    public void setCxdh(String cxdh) {
        this.cxdh = cxdh;
    }

    public String getTjid() {
        return tjid;
    }

    public void setTjid(String tjid) {
        this.tjid = tjid;
    }

    public String getCxid() {
        return cxid;
    }

    public void setCxid(String cxid) {
        this.cxid = cxid;
    }

    public String getTjzdid() {
        return tjzdid;
    }

    public void setTjzdid(String tjzdid) {
        this.tjzdid = tjzdid;
    }

    public String getTjzdname() {
        return tjzdname;
    }

    public void setTjzdname(String tjzdname) {
        this.tjzdname = tjzdname;
    }

    public String getMrxs() {
        return mrxs;
    }

    public void setMrxs(String mrxs) {
        this.mrxs = mrxs;
    }

    public String getTjtype() {
        return tjtype;
    }

    public void setTjtype(String tjtype) {
        this.tjtype = tjtype;
    }

    public String getZdid() {
        return zdid;
    }

    public void setZdid(String zdid) {
        this.zdid = zdid;
    }

    public String getZddyfs() {
        return zddyfs;
    }

    public void setZddyfs(String zddyfs) {
        this.zddyfs = zddyfs;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
