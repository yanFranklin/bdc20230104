package cn.gtmap.realestate.etl.core.domian.wwsq;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/11/1
 * @description 审核信息
 */
@Table(name = "gx_ww_shxx")
public class GxWwShxxDO {

    /**
     * 全局唯一,主键
     */
    @Id
    private String shxxid;
    /**
     * 外网申请受理编号
     */
    private String wwslbh;
    /**
     * 节点名称
     */
    private String jdmc;
    /**
     * 顺序号
     */
    private Integer sxh;
    /**
     * 审核人员姓名
     */
    private String shryxm;
    /**
     * 审核意见
     */
    private String shyj;
    /**
     * 操作结果
     */
    private String czjg;
    /**
     * 签名时间
     */
    private Date qmsj;

    public String getShxxid() {
        return shxxid;
    }

    public void setShxxid(String shxxid) {
        this.shxxid = shxxid;
    }

    public String getWwslbh() {
        return wwslbh;
    }

    public void setWwslbh(String wwslbh) {
        this.wwslbh = wwslbh;
    }

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    public String getShryxm() {
        return shryxm;
    }

    public void setShryxm(String shryxm) {
        this.shryxm = shryxm;
    }

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj;
    }

    public String getCzjg() {
        return czjg;
    }

    public void setCzjg(String czjg) {
        this.czjg = czjg;
    }

    public Date getQmsj() {
        return qmsj;
    }

    public void setQmsj(Date qmsj) {
        this.qmsj = qmsj;
    }
}
