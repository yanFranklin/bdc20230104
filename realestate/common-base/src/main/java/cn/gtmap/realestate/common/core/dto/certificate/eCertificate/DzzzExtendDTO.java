package cn.gtmap.realestate.common.core.dto.certificate.eCertificate;

import java.util.Date;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/1/10
 * @description 电子证照DTO扩张对象
 */
public class DzzzExtendDTO extends HefeiDzzzDTO {
    /**
     * 不动产权证号	bdc_zs.bdcqzh
     * 证明或权利事项	bdc_zs.zmqlsx
     * 权利人	bdc_zs.qlr
     * 义务人	bdc_zs.ywr
     * 坐落	bdc_zs.zl
     * 不动产单元号	bdc_zs.bdcdyh
     * 其他	bdc_zs.qlqtzk
     * 附记	bdc_zs.fj
     * 登记时间	bdc_zs.szsj
     * 证书id	bdc_zs.zsid
     */
    private String bdcqzh;
    private String zmqlsx;
    private String qlr;
    private String ywr;
    private String zl;
    private String bdcdyh;
    private String qlqtzk;
    private String fj;
    private Date szsj;
    private String zsid;

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getZmqlsx() {
        return zmqlsx;
    }

    public void setZmqlsx(String zmqlsx) {
        this.zmqlsx = zmqlsx;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getQlqtzk() {
        return qlqtzk;
    }

    public void setQlqtzk(String qlqtzk) {
        this.qlqtzk = qlqtzk;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public Date getSzsj() {
        return szsj;
    }

    public void setSzsj(Date szsj) {
        this.szsj = szsj;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    @Override
    public String toString() {
        return "DZZZExtendDTO{" +
                "bdcqzh='" + bdcqzh + '\'' +
                ", zmqlsx='" + zmqlsx + '\'' +
                ", qlr='" + qlr + '\'' +
                ", ywr='" + ywr + '\'' +
                ", zl='" + zl + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", qlqtzk='" + qlqtzk + '\'' +
                ", fj='" + fj + '\'' +
                ", szsj=" + szsj +
                ", zsid='" + zsid + '\'' +
                '}';
    }
}
