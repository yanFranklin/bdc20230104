package cn.gtmap.realestate.common.core.dto.exchange.xuancheng.hhxx;

/**
 * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
 * @version 1.0  2022/12/19
 * @description
 */
public class HhxxRequestCondition {

    /**
     * 逝者姓名
     */
    private String SZXM;

    /**
     * 逝者证件号
     */
    private String SZZJH;

    public String getSZXM() {
        return SZXM;
    }

    public void setSZXM(String SZXM) {
        this.SZXM = SZXM;
    }

    public String getSZZJH() {
        return SZZJH;
    }

    public void setSZZJH(String SZZJH) {
        this.SZZJH = SZZJH;
    }

    @Override
    public String toString() {
        return "HhxxRequestCondition{" +
                "SZXM='" + SZXM + '\'' +
                ", SZZJH='" + SZZJH + '\'' +
                '}';
    }
}
