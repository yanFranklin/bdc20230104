package cn.gtmap.realestate.common.core.dto.etl;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 2022/10/20,1.0
 * @description 蚌埠买受方信息
 */
public class EtlBbHtbaMsfDTO {

    // 买受人名称
    private String msr;

    // 买受人证件号码
    private String msrzjhm;

    // 买受人证件类别
    private String msrzjlb;

    // 买受人联系电话
    private String msrlxdh;

    public String getMsr() {
        return msr;
    }

    public void setMsr(String msr) {
        this.msr = msr;
    }

    public String getMsrzjhm() {
        return msrzjhm;
    }

    public void setMsrzjhm(String msrzjhm) {
        this.msrzjhm = msrzjhm;
    }

    public String getMsrzjlb() {
        return msrzjlb;
    }

    public void setMsrzjlb(String msrzjlb) {
        this.msrzjlb = msrzjlb;
    }

    public String getMsrlxdh() {
        return msrlxdh;
    }

    public void setMsrlxdh(String msrlxdh) {
        this.msrlxdh = msrlxdh;
    }

    @Override
    public String toString() {
        return "EtlBbHtbaMsfDTO{" +
                "msr='" + msr + '\'' +
                ", msrzjhm='" + msrzjhm + '\'' +
                ", msrzjlb='" + msrzjlb + '\'' +
                ", msrlxdh='" + msrlxdh + '\'' +
                '}';
    }
}
