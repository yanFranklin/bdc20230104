package cn.gtmap.realestate.exchange.core.dto.wwsq.jtBdcxx.request;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 1.0, 2022/8/3 8:54
 * @description 家庭成员信息入参
 */
public class JtcyxxRequestDTO {

    /**
     * 证件号码
     */
    private String zjhm;

    /**
     * 证件类型
     */
    private String zjlx;

    /**
     * 姓名
     */
    private String xm;

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    @Override
    public String toString() {
        return "JtcyxxRequestDTO{" +
                "zjhm='" + zjhm + '\'' +
                ", zjlx='" + zjlx + '\'' +
                ", xm='" + xm + '\'' +
                '}';
    }
}
