package cn.gtmap.realestate.exchange.core.dto.hefei.wjwswzm.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-10
 * @description
 */
@XStreamAlias("data")
public class WjwSwzmResponseData {

    // 公民身份证号
    @XStreamAlias("GMSFHM")
    private String gmsfhm;

    @XStreamAlias("XM")
    private String xm;

    // 性别代码
    @XStreamAlias("XBDM")
    private String xbdm;
    // 民族代码
    @XStreamAlias("MZDM")
    private String mzdm;

    // 死亡原因
    @XStreamAlias("SWYY")
    private String swyy;

    // 死亡日期
    @XStreamAlias("SWRQ")
    private String swrq;

    public String getGmsfhm() {
        return gmsfhm;
    }

    public void setGmsfhm(String gmsfhm) {
        this.gmsfhm = gmsfhm;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }


    public String getMzdm() {
        return mzdm;
    }

    public void setMzdm(String mzdm) {
        this.mzdm = mzdm;
    }

    public String getSwyy() {
        return swyy;
    }

    public void setSwyy(String swyy) {
        this.swyy = swyy;
    }

    public String getXbdm() {
        return xbdm;
    }

    public void setXbdm(String xbdm) {
        this.xbdm = xbdm;
    }

    public String getSwrq() {
        return swrq;
    }

    public void setSwrq(String swrq) {
        this.swrq = swrq;
    }
}
