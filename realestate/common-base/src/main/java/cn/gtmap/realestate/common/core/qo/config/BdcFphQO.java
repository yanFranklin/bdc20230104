package cn.gtmap.realestate.common.core.qo.config;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-09-06
 * @description 发票号QO
 */
public class BdcFphQO {
    /**
     * 年份
     */
    private String nf;
    /**
     * 区县代码
     */
    private String qxdm;
    /**
     * 发票号
     */
    private String fph;
    /**
     * 使用情况
     */
    private Integer syqk;
    /**
     * 领取人
     */
    private String lqr;
    /**
     * 领取人ID
     */
    private String lqrid;
    /**
     * 领取部门
     */
    private String lqbm;
    /**
     * 领取部门ID
     */
    private String lqbmid;
    /**
     * 发票号起
     */
    private String fphq;
    /**
     * 发票号止
     */
    private String fphz;
    /**
     * 排序字段
     */
    private String field;
    /**
     * 排序类型
     */
    private String order;
    /**
     * 起始编号
     */
    private Integer qsbh;
    /**
     * 结束编号
     */
    private Integer jsbh;

    /**
     * 编号位数
     */
    private Integer bhws;
    /**
     * 需要获取发票号的数目
     */
    private Integer num;
    /**
     * 不相等的发票号
     */
    private String notEqualFph;

    /**
     * 自助机标识
     */
    private Integer zzjfbs;

    @ApiModelProperty(value = "发票类型")
    private Integer fplx;

    @ApiModelProperty(value = "发票号id")
    private String fphid;

    public Integer getJsbh() {
        return jsbh;
    }

    public void setJsbh(Integer jsbh) {
        this.jsbh = jsbh;
    }

    public Integer getQsbh() {
        return qsbh;
    }

    public void setQsbh(Integer qsbh) {
        this.qsbh = qsbh;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getFph() {
        return fph;
    }

    public void setFph(String fph) {
        this.fph = fph;
    }

    public Integer getSyqk() {
        return syqk;
    }

    public void setSyqk(Integer syqk) {
        this.syqk = syqk;
    }

    public String getLqr() {
        return lqr;
    }

    public void setLqr(String lqr) {
        this.lqr = lqr;
    }

    public String getLqrid() {
        return lqrid;
    }

    public void setLqrid(String lqrid) {
        this.lqrid = lqrid;
    }

    public String getLqbm() {
        return lqbm;
    }

    public void setLqbm(String lqbm) {
        this.lqbm = lqbm;
    }

    public String getLqbmid() {
        return lqbmid;
    }

    public void setLqbmid(String lqbmid) {
        this.lqbmid = lqbmid;
    }
    
    public String getFphq() {
        return fphq;
    }
    
    public void setFphq(String fphq) {
        this.fphq = fphq;
    }
    
    public String getFphz() {
        return fphz;
    }
    
    public void setFphz(String fphz) {
        this.fphz = fphz;
    }
    
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getBhws() {
        return bhws;
    }

    public void setBhws(Integer bhws) {
        this.bhws = bhws;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getNotEqualFph() {
        return notEqualFph;
    }

    public void setNotEqualFph(String notEqualFph) {
        this.notEqualFph = notEqualFph;
    }

    public Integer getZzjfbs() {
        return zzjfbs;
    }

    public void setZzjfbs(Integer zzjfbs) {
        this.zzjfbs = zzjfbs;
    }

    public Integer getFplx() {
        return fplx;
    }

    public void setFplx(Integer fplx) {
        this.fplx = fplx;
    }

    public String getFphid() {
        return fphid;
    }

    public void setFphid(String fphid) {
        this.fphid = fphid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcFphQO{");
        sb.append("nf='").append(nf).append('\'');
        sb.append(", qxdm='").append(qxdm).append('\'');
        sb.append(", fph='").append(fph).append('\'');
        sb.append(", syqk=").append(syqk);
        sb.append(", lqr='").append(lqr).append('\'');
        sb.append(", lqrid='").append(lqrid).append('\'');
        sb.append(", lqbm='").append(lqbm).append('\'');
        sb.append(", lqbmid='").append(lqbmid).append('\'');
        sb.append(", fphq='").append(fphq).append('\'');
        sb.append(", fphz='").append(fphz).append('\'');
        sb.append(", field='").append(field).append('\'');
        sb.append(", order='").append(order).append('\'');
        sb.append(", qsbh=").append(qsbh);
        sb.append(", jsbh=").append(jsbh);
        sb.append(", bhws=").append(bhws);
        sb.append(", num=").append(num);
        sb.append(", notEqualFph='").append(notEqualFph).append('\'');
        sb.append(", zzjfbs=").append(zzjfbs);
        sb.append(", fplx=").append(fplx);
        sb.append(", fphid='").append(fphid).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
