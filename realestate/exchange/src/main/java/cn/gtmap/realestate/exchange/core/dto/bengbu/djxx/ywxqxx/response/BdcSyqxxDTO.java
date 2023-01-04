package cn.gtmap.realestate.exchange.core.dto.bengbu.djxx.ywxqxx.response;

/**
 * @description 不动产所有权信息
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @date 2022/12/22 18:04
 */
public class BdcSyqxxDTO {

    /**
     * 不动产业务号
     */
    private String bdcywh;

    /**
     * 业务事项名称
     */
    private String ywsxmc;

    /**
     * 权利人姓名
     */
    private String qlrxm;

    /**
     * 权利人证件号码
     */
    private String qlrzjhm;

    /**
     * 联系方式
     */
    private String lxfs;

    /**
     * 不动产权证号
     */
    private String bdcqzh;

    /**
     * 登簿时间
     */
    private String dbsj;

    public String getBdcywh() {
        return bdcywh;
    }

    public void setBdcywh(String bdcywh) {
        this.bdcywh = bdcywh;
    }

    public String getYwsxmc() {
        return ywsxmc;
    }

    public void setYwsxmc(String ywsxmc) {
        this.ywsxmc = ywsxmc;
    }

    public String getQlrxm() {
        return qlrxm;
    }

    public void setQlrxm(String qlrxm) {
        this.qlrxm = qlrxm;
    }

    public String getQlrzjhm() {
        return qlrzjhm;
    }

    public void setQlrzjhm(String qlrzjhm) {
        this.qlrzjhm = qlrzjhm;
    }

    public String getLxfs() {
        return lxfs;
    }

    public void setLxfs(String lxfs) {
        this.lxfs = lxfs;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getDbsj() {
        return dbsj;
    }

    public void setDbsj(String dbsj) {
        this.dbsj = dbsj;
    }

    @Override
    public String toString() {
        return "BdcSyqxxDTO{" +
                "bdcywh='" + bdcywh + '\'' +
                ", ywsxmc='" + ywsxmc + '\'' +
                ", qlrxm='" + qlrxm + '\'' +
                ", qlrzjhm='" + qlrzjhm + '\'' +
                ", lxfs='" + lxfs + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", dbsj='" + dbsj + '\'' +
                '}';
    }
}
