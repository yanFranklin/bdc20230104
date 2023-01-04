package cn.gtmap.realestate.common.core.dto.exchange.nantong.zjjy.jyqd;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/11/17
 * @description 推送住建买受人信息
 */
@ApiModel(value = "ZjClfMsfxx", description = "推送住建买受人信息")
public class ZjClfMsfxxDTO {


    /**
     * MSR :
     * MSRZJHM :
     * MSRGX :
     * ZYBL :
     */
    @JSONField(name="MSR")
    private String MSR;
    @JSONField(name="MSRZJHM")
    private String MSRZJHM;
    @JSONField(name="MSRGX")
    private String MSRGX;
    @JSONField(name="ZYBL")
    private String ZYBL;
    @JSONField(name="MSRDZ")
    private String MSRDZ;
    @JSONField(name="MSRDH")
    private String MSRDH;

    public String getMSR() {
        return MSR;
    }
    @JSONField(name="MSR")
    public void setMSR(String MSR) {
        this.MSR = MSR;
    }

    public String getMSRZJHM() {
        return MSRZJHM;
    }
    @JSONField(name="MSRZJHM")
    public void setMSRZJHM(String MSRZJHM) {
        this.MSRZJHM = MSRZJHM;
    }

    public String getMSRGX() {
        return MSRGX;
    }

    @JSONField(name="MSRGX")
    public void setMSRGX(String MSRGX) {
        this.MSRGX = MSRGX;
    }

    public String getZYBL() {
        return ZYBL;
    }

    @JSONField(name="ZYBL")
    public void setZYBL(String ZYBL) {
        this.ZYBL = ZYBL;
    }

    public String getMSRDZ() {
        return MSRDZ;
    }

    @JSONField(name="MSRDZ")
    public void setMSRDZ(String MSRDZ) {
        this.MSRDZ = MSRDZ;
    }

    public String getMSRDH() {
        return MSRDH;
    }

    @JSONField(name="MSRDH")
    public void setMSRDH(String MSRDH) {
        this.MSRDH = MSRDH;
    }


    @Override
    public String toString() {
        return "ZjClfMsfxxDTO{" +
                "MSR='" + MSR + '\'' +
                ", MSRZJHM='" + MSRZJHM + '\'' +
                ", MSRGX='" + MSRGX + '\'' +
                ", ZYBL='" + ZYBL + '\'' +
                ", MSRDZ='" + MSRDZ + '\'' +
                ", MSRDH='" + MSRDH + '\'' +
                '}';
    }
}
