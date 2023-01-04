package cn.gtmap.realestate.common.core.dto.exchange.wwsq.getWwsqBdcdyxx.request;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/7/13
 * @description 6.1.8    分页查询不动产单元信息QO
 */
public class BdcdyxxCxQO {


    /**
     * qlrmc :
     * bdcdyh :
     * bdcdybh :
     * zlmh :
     * qlrzjh :
     * houseid :
     * bdclx :
     * zl :
     */

    private String qlrmc;
    private String bdcdyh;
    private String bdcdybh;
    private String zlmh;
    private String qlrzjh;
    private String houseid;
    private String bdclx;
    private String zl;
    private String qjgldm;

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcdybh() {
        return bdcdybh;
    }

    public void setBdcdybh(String bdcdybh) {
        this.bdcdybh = bdcdybh;
    }

    public String getZlmh() {
        return zlmh;
    }

    public void setZlmh(String zlmh) {
        this.zlmh = zlmh;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getHouseid() {
        return houseid;
    }

    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    @Override
    public String toString() {
        return "BdcdyxxCxQO{" +
                "qlrmc='" + qlrmc + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdybh='" + bdcdybh + '\'' +
                ", zlmh='" + zlmh + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", houseid='" + houseid + '\'' +
                ", bdclx='" + bdclx + '\'' +
                ", zl='" + zl + '\'' +
                ", qjgldm='" + qjgldm + '\'' +
                '}';
    }
}
