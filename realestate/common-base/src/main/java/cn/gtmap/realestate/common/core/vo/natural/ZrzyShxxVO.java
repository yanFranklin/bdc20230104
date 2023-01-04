package cn.gtmap.realestate.common.core.vo.natural;


import java.io.Serializable;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/12/14
 * @description 审核信息页面信息
 */
public class ZrzyShxxVO implements Serializable {

    private static final long serialVersionUID = -800991607543194063L;
    /**
     * 审核信息id
     */
    private String shxxid;
    /**
     * 审核意见
     */
    private String shyj;
    /**
     * 签名时间
     */
    private String qmsj;
    /**
     * 签名人员名称
     */
    private String shryxm;
    /**
     * 签名id
     */
    private String qmid;
    /**
     * 节点名称
     */
    private String jdmc;
    /**
     * 节点ID
     */
    private String jdid;
    /**
     * 签名图片地址
     */
    private String qmtpdz;

    public String getShxxid() {
        return shxxid;
    }

    public void setShxxid(String shxxid) {
        this.shxxid = shxxid;
    }

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj;
    }

    public String getQmsj() {
        return qmsj;
    }

    public void setQmsj(String qmsj) {
        this.qmsj = qmsj;
    }

    public String getShryxm() {
        return shryxm;
    }

    public void setShryxm(String shryxm) {
        this.shryxm = shryxm;
    }

    public String getQmid() {
        return qmid;
    }

    public void setQmid(String qmid) {
        this.qmid = qmid;
    }

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    public String getJdid() {
        return jdid;
    }

    public void setJdid(String jdid) {
        this.jdid = jdid;
    }

    public String getQmtpdz() {
        return qmtpdz;
    }

    public void setQmtpdz(String qmtpdz) {
        this.qmtpdz = qmtpdz;
    }
    @Override
    public String toString() {
        return "BdcShxxVO{" +
                "shxxid='" + shxxid + '\'' +
                ", shyj='" + shyj + '\'' +
                ", qmsj='" + qmsj + '\'' +
                ", shryxm='" + shryxm + '\'' +
                ", qmid='" + qmid + '\'' +
                ", jdmc='" + jdmc + '\'' +
                ", jdid='" + jdid + '\'' +
                ", qmtpdz='" + qmtpdz + '\'' +
                '}';
    }

}
