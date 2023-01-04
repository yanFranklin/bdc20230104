package cn.gtmap.realestate.etl.core.domian;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 2020/06/29,1.0
 * @description 大数据局共享实体do
 */
@Table(name = "gt_cqxx")
public class GtCqxxDo {

    /**
     * 原产权人
     */
    private String ywr;
    /**
     * 原产权人证件号
     */
    private String ywrzjh;
    /**
     * 产权人
     */
    private String qlr;
    /**
     * 产权人证件号
     */
    private String qlrzjh;
    /**
     * 坐落
     */
    private String zl;
    /**
     * 不动产权证号
     */
    private String bdcqzh;
    /**
     * 登记时间
     */
    private Date djsj;
    /**
     * 不动产单元号
     */
    private String bdcdyh;
    /**
     * 主键
     */
    @Id
    private String id;
    /**
     * 插入时间
     */
    private Date crsj;
    /**
     * 更新时间
     */
    private Date gxsj;


    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }


    public String getYwrzjh() {
        return ywrzjh;
    }

    public void setYwrzjh(String ywrzjh) {
        this.ywrzjh = ywrzjh;
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


    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }


    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }


    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }


    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
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
