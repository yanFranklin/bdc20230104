package cn.gtmap.realestate.exchange.core.dto.nantong.daxx;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/12/16
 * @description 启动-推送档案柜信息DO
 */
@Table(name = "BDC_DAZTXX")
public class NanTongDaxxDo {

    /**
     * slbh : 202012120002
     * rgwz : 2层1号
     * rgsj  : 2021-12-12:12:12:12
     * dazt : 1
     * cgsj : 2021-12-15:12:12:12
     */
    @Id
    private String slbh;
    private String rgwz;
    private Date rgsj;
    private Integer dazt;
    private Date cgsj;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getRgwz() {
        return rgwz;
    }

    public void setRgwz(String rgwz) {
        this.rgwz = rgwz;
    }

    public Date getRgsj() {
        return rgsj;
    }

    public void setRgsj(Date rgsj) {
        this.rgsj = rgsj;
    }

    public Integer getDazt() {
        return dazt;
    }

    public void setDazt(Integer dazt) {
        this.dazt = dazt;
    }

    public Date getCgsj() {
        return cgsj;
    }

    public void setCgsj(Date cgsj) {
        this.cgsj = cgsj;
    }

    @Override
    public String toString() {
        return "NanTongDaxxDo{" +
                "slbh='" + slbh + '\'' +
                ", rgwz='" + rgwz + '\'' +
                ", rgsj='" + rgsj + '\'' +
                ", dazt='" + dazt + '\'' +
                ", cgsj='" + cgsj + '\'' +
                '}';
    }
}
