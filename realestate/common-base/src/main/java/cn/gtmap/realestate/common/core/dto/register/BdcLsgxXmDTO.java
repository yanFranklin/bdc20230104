package cn.gtmap.realestate.common.core.dto.register;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;

import java.util.Date;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 历史关系展示DTO对象
 */
public class BdcLsgxXmDTO extends BdcXmDO {
    /**
     * 抵押方式
     */
    private String dyfs;

    /**
     * 付款方式
     */
    private String fkfs;

    /**
     * 被担保主债权数额
     */
    private Double bdbzzqse;

    /**
     * 查封文号
     */
    private String cfwh;

    /**
     * 权籍管理代码
     */
    private String qjgldm;

    /**
     * 建筑面积
     */
    private String jzmj;

    /**
     * 房间号
     */
    private String fjh;

    /**
     * 是否注销
     */
    private String sfzx;

    /**
     * 证书形态 1：电子 2：纸质
     */
    private Integer zsxt;

    /**
     * 解封业务号
     */
    private String jfywh;

    /**
     * 解封登记时间
     */
    private Date jfdjsj;

    /**
     * 注销抵押业务号
     */
    private String zxdyywh;

    /**
     * 注销抵押登记时间
     */
    private Date zxdydjsj;

    public String getJzmj() { return jzmj; }

    public void setJzmj(String jzmj) { this.jzmj = jzmj; }

    public String getFjh() { return fjh; }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getDyfs() {
        return dyfs;
    }

    public void setDyfs(String dyfs) {
        this.dyfs = dyfs;
    }

    public String getFkfs() {
        return fkfs;
    }

    public void setFkfs(String fkfs) {
        this.fkfs = fkfs;
    }

    public Double getBdbzzqse() {
        return bdbzzqse;
    }

    public void setBdbzzqse(Double bdbzzqse) {
        this.bdbzzqse = bdbzzqse;
    }

    public String getCfwh() {
        return cfwh;
    }

    public void setCfwh(String cfwh) {
        this.cfwh = cfwh;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public String getSfzx() { return sfzx; }

    public void setSfzx(String sfzx) { this.sfzx = sfzx; }

    public Integer getZsxt() {
        return zsxt;
    }

    public void setZsxt(Integer zsxt) {
        this.zsxt = zsxt;
    }

    public String getJfywh() {
        return jfywh;
    }

    public void setJfywh(String jfywh) {
        this.jfywh = jfywh;
    }

    public Date getJfdjsj() {
        return jfdjsj;
    }

    public void setJfdjsj(Date jfdjsj) {
        this.jfdjsj = jfdjsj;
    }

    public String getZxdyywh() {
        return zxdyywh;
    }

    public void setZxdyywh(String zxdyywh) {
        this.zxdyywh = zxdyywh;
    }

    public Date getZxdydjsj() {
        return zxdydjsj;
    }

    public void setZxdydjsj(Date zxdydjsj) {
        this.zxdydjsj = zxdydjsj;
    }

    @Override
    public String toString() {
        return "BdcLsgxXmDTO{" +
                "dyfs='" + dyfs + '\'' +
                ", fkfs='" + fkfs + '\'' +
                ", bdbzzqse=" + bdbzzqse +
                ", cfwh='" + cfwh + '\'' +
                ", qjgldm='" + qjgldm + '\'' +
                ", jzmj='" + jzmj + '\'' +
                ", fjh='" + fjh + '\'' +
                ", sfzx='" + sfzx + '\'' +
                ", zsxt=" + zsxt +
                ", jfywh='" + jfywh + '\'' +
                ", jfdjsj=" + jfdjsj +
                ", zxdyywh='" + zxdyywh + '\'' +
                ", zxdydjsj=" + zxdydjsj +
                '}';
    }
}
