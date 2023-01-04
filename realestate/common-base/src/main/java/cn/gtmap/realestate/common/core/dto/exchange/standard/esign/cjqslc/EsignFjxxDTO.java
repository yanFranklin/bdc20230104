package cn.gtmap.realestate.common.core.dto.exchange.standard.esign.cjqslc;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/10/20
 * @description 附件信息    fjxxList
 */
public class EsignFjxxDTO {


    /**
     * fjid : 必填|附件id
     * sqid : 申请id
     * fjmc : 必填|附件名称，唯一约束
     * fjmm : 附件密码，文档有密码时传入
     * qssx : 签署文档顺序,用于页面文档顺序显示
     */

    private String fjid;
    private String sqid;
    private String fjmc;
    private String fjmm;
    private String qssx;

    public String getFjid() {
        return fjid;
    }

    public void setFjid(String fjid) {
        this.fjid = fjid;
    }

    public String getSqid() {
        return sqid;
    }

    public void setSqid(String sqid) {
        this.sqid = sqid;
    }

    public String getFjmc() {
        return fjmc;
    }

    public void setFjmc(String fjmc) {
        this.fjmc = fjmc;
    }

    public String getFjmm() {
        return fjmm;
    }

    public void setFjmm(String fjmm) {
        this.fjmm = fjmm;
    }

    public String getQssx() {
        return qssx;
    }

    public void setQssx(String qssx) {
        this.qssx = qssx;
    }

    @Override
    public String toString() {
        return "EsingnFjxxList{" +
                "fjid='" + fjid + '\'' +
                ", sqid='" + sqid + '\'' +
                ", fjmc='" + fjmc + '\'' +
                ", fjmm='" + fjmm + '\'' +
                ", qssx='" + qssx + '\'' +
                '}';
    }
}
