package cn.gtmap.realestate.exchange.core.dto.wwsq.init.zy;

import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-11
 * @description 家庭成员实体
 */
@IgnoreCast(ignoreNum = 3)
public class InitRequestJtcy {

    private String jtcymc;

    private String jtcyzjh;

    private String jtgx;

    private String jtcyzjzl;

    private String gxbs;

    public String getJtcymc() {
        return jtcymc;
    }

    public void setJtcymc(String jtcymc) {
        this.jtcymc = jtcymc;
    }

    public String getJtgx() {
        return jtgx;
    }

    public void setJtgx(String jtgx) {
        this.jtgx = jtgx;
    }

    public String getJtcyzjzl() {
        return jtcyzjzl;
    }

    public void setJtcyzjzl(String jtcyzjzl) {
        this.jtcyzjzl = jtcyzjzl;
    }

    public String getJtcyzjh() {
        return jtcyzjh;
    }

    public void setJtcyzjh(String jtcyzjh) {
        this.jtcyzjh = jtcyzjh;
    }

    public String getGxbs() {
        return gxbs;
    }

    public void setGxbs(String gxbs) {
        this.gxbs = gxbs;
    }
}
