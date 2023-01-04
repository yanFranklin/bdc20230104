package cn.gtmap.realestate.etl.core.domian.wwsq;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/8/19
 * @description 共享外网收费项目
 */
@Table(name = "GX_WW_SQXX_SFXM")
public class GxWwSqxxSfxmDO {

    /**
     * 主键
     */
    @Id
    private String sfxmid;

    /**
     * 标准
     */
    private String bz;

    /**
     * 单位
     */
    private String dw;

    /**
     * 单位名称
     */
    private String dwmc;

    /**
     * 收费项目代码
     */
    private String sfxm;

    /**
     * 收费项目名称
     */
    private String sfxmmc;

    /**
     * 数量
     */
    private Integer sl;

    /**
     * 应缴金额
     */
    private Double yjje;

    /**
     * 收费信息ID
     */
    private String sfxxid;

    public String getSfxmid() {
        return sfxmid;
    }

    public void setSfxmid(String sfxmid) {
        this.sfxmid = sfxmid;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getSfxm() {
        return sfxm;
    }

    public void setSfxm(String sfxm) {
        this.sfxm = sfxm;
    }

    public String getSfxmmc() {
        return sfxmmc;
    }

    public void setSfxmmc(String sfxmmc) {
        this.sfxmmc = sfxmmc;
    }

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    public Double getYjje() {
        return yjje;
    }

    public void setYjje(Double yjje) {
        this.yjje = yjje;
    }

    public String getSfxxid() {
        return sfxxid;
    }

    public void setSfxxid(String sfxxid) {
        this.sfxxid = sfxxid;
    }
}
