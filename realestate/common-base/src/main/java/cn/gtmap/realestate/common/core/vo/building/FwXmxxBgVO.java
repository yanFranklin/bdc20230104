package cn.gtmap.realestate.common.core.vo.building;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Id;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-23
 * @description
 */
public class FwXmxxBgVO {

    @Id
    /**
     * 主键
     */
    @NotBlank(message = "项目主键不能为空")
    private String fwXmxxIndex;

    @NotBlank(message = "变更编号不能为空")
    private String bgbh;

    /**
     * 隶属宗地
     */
    private String lszd;
    /**
     * 坐落
     */
    private String zl;
    /**
     * 项目名称
     */
    private String xmmc;
    /**
     * 独用土地面积
     */
    private Double dytdmj;
    /**
     * 分摊土地面积
     */
    private Double fttdmj;
    /**
     * 交易价格
     */
    private Double jyjg;
    /**
     * 房屋类型
     */
    private String fwlx;
    /**
     * 房屋性质
     */
    private String fwxz;
    /**
     * 不动产状态
     */
    private String bdczt;
    /**
     * 备注
     */
    private String bz;
    /**
     * 产别
     */
    private String cb;

    /**
     * 权籍管理代码
     */
    private String qjgldm;

    public String getFwXmxxIndex() {
        return fwXmxxIndex;
    }

    public void setFwXmxxIndex(String fwXmxxIndex) {
        this.fwXmxxIndex = fwXmxxIndex;
    }

    public String getLszd() {
        return lszd;
    }

    public void setLszd(String lszd) {
        this.lszd = lszd;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    public Double getDytdmj() {
        return dytdmj;
    }

    public void setDytdmj(Double dytdmj) {
        this.dytdmj = dytdmj;
    }

    public Double getFttdmj() {
        return fttdmj;
    }

    public void setFttdmj(Double fttdmj) {
        this.fttdmj = fttdmj;
    }

    public Double getJyjg() {
        return jyjg;
    }

    public void setJyjg(Double jyjg) {
        this.jyjg = jyjg;
    }

    public String getFwlx() {
        return fwlx;
    }

    public void setFwlx(String fwlx) {
        this.fwlx = fwlx;
    }

    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    public String getBdczt() {
        return bdczt;
    }

    public void setBdczt(String bdczt) {
        this.bdczt = bdczt;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getCb() {
        return cb;
    }

    public void setCb(String cb) {
        this.cb = cb;
    }

    public String getBgbh() {
        return bgbh;
    }

    public void setBgbh(String bgbh) {
        this.bgbh = bgbh;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }
}
