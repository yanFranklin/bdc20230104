package cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.request;


/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022/8/12
 * @description 水电气过户前核验请求
 */
public class SdqHyRequestDTO {
    /**
     * 户号
     */
    private String consno;

    /**
     * 是否查询欠费：1是0否
     */
    private String sfcxqf;

    /**
     * 单位代码
     */
    private String dwdm;

    // 区县代码
    private String qxdm;

    // 业务类型 1.水 2.电 3.气
    private String ywlx;

    // 业务类型 hy:核验 gh:过户
    private String jklx;

    public String getConsno() {
        return consno;
    }

    public void setConsno(String consno) {
        this.consno = consno;
    }

    public String getSfcxqf() {
        return sfcxqf;
    }

    public void setSfcxqf(String sfcxqf) {
        this.sfcxqf = sfcxqf;
    }

    public String getDwdm() {
        return dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getYwlx() {
        return ywlx;
    }

    public void setYwlx(String ywlx) {
        this.ywlx = ywlx;
    }

    public String getJklx() {
        return jklx;
    }

    public void setJklx(String jklx) {
        this.jklx = jklx;
    }

    @Override
    public String toString() {
        return "SdqHyRequestDTO{" +
                "consno='" + consno + '\'' +
                ", sfcxqf='" + sfcxqf + '\'' +
                ", dwdm='" + dwdm + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", ywlx='" + ywlx + '\'' +
                ", jklx='" + jklx + '\'' +
                '}';
    }
}
