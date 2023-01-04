package cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.response;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date  2020/12/18
 * @description 个人档案查询-锁定信息
 */
public class GrdacxSdxx {

    /**
     * 锁定人
     */
    private String sdr;

    /**
     * 锁定原因
     */
    private String sdyy;

    /**
     * 登记时间
     */
    private String djsj;

    public String getSdr() {
        return sdr;
    }

    public void setSdr(String sdr) {
        this.sdr = sdr;
    }

    public String getSdyy() {
        return sdyy;
    }

    public void setSdyy(String sdyy) {
        this.sdyy = sdyy;
    }

    public String getDjsj() {
        return djsj;
    }

    public void setDjsj(String djsj) {
        this.djsj = djsj;
    }

    @Override
    public String toString() {
        return "GrdacxSdxx{" +
                "sdr='" + sdr + '\'' +
                ", sdyy='" + sdyy + '\'' +
                ", djsj='" + djsj + '\'' +
                '}';
    }
}
