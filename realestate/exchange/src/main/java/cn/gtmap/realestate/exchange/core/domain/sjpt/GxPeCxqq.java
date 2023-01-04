package cn.gtmap.realestate.exchange.core.domain.sjpt;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Table(name = "gx_pe_cxqq")
public class GxPeCxqq {
    @Id
    private String cxqqid;//查询请求id
    private String cxsqdh;//查询申请单号
    private String cxjgbs;//查询机构标识
    private String cxywlb;//查询业务类别
    private String zt;//1:未生成查询结果 2：查询结果已入库未推送 3：已推送 4:部分查询 5,已超时
    private Date cjsj;//创建时间
    private Date gxsj;//更新时间
    private String cxjgJson;//查询结果JSON（暂无用）
    private Date sbsj;//上报时间
    private String cxfw;//查询范围, 0-历史+现势 ; 1-现势

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    public String getCxqqid() {
        return cxqqid;
    }

    public void setCxqqid(String cxqqid) {
        this.cxqqid = cxqqid;
    }

    public String getCxsqdh() {
        return cxsqdh;
    }

    public void setCxsqdh(String cxsqdh) {
        this.cxsqdh = cxsqdh;
    }

    public String getCxjgbs() {
        return cxjgbs;
    }

    public void setCxjgbs(String cxjgbs) {
        this.cxjgbs = cxjgbs;
    }

    public String getCxywlb() {
        return cxywlb;
    }

    public void setCxywlb(String cxywlb) {
        this.cxywlb = cxywlb;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getCxjgJson() {
        return cxjgJson;
    }

    public void setCxjgJson(String cxjgJson) {
        this.cxjgJson = cxjgJson;
    }

    public Date getSbsj() {
        return sbsj;
    }

    public void setSbsj(Date sbsj) {
        this.sbsj = sbsj;
    }

    public String getCxfw() {
        return cxfw;
    }

    public void setCxfw(String cxfw) {
        this.cxfw = cxfw;
    }
}
