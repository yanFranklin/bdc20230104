package cn.gtmap.realestate.exchange.core.dto.nantong.clfhtxx.response;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-17
 * @description
 */
public class ClfHtxxResponseDTO {

    private String htbh;

    //单位为万元
    private String jyje;

    //格式：yyyy-MM-dd hh:mm:ss
    private String sqsj;

    //01 签定契约
    //02 申请备案
    //03 审核退回
    //04 备案并发布
    //05 已登记
    //06 撤销合同
    //07 注销备案
    private String htzt;

    private String qxdm;

    private String dljg;

    private List<ClfHtxxResponseCmr> cmr;

    private List<ClfHtxxResponseMsr> msr;

    private List<ClfHtxxResponseFw> fw;

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getJyje() {
        return jyje;
    }

    public void setJyje(String jyje) {
        this.jyje = jyje;
    }

    public String getSqsj() {
        return sqsj;
    }

    public void setSqsj(String sqsj) {
        this.sqsj = sqsj;
    }

    public String getHtzt() {
        return htzt;
    }

    public void setHtzt(String htzt) {
        this.htzt = htzt;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getDljg() {
        return dljg;
    }

    public void setDljg(String dljg) {
        this.dljg = dljg;
    }

    public List<ClfHtxxResponseCmr> getCmr() {
        return cmr;
    }

    public void setCmr(List<ClfHtxxResponseCmr> cmr) {
        this.cmr = cmr;
    }

    public List<ClfHtxxResponseMsr> getMsr() {
        return msr;
    }

    public void setMsr(List<ClfHtxxResponseMsr> msr) {
        this.msr = msr;
    }

    public List<ClfHtxxResponseFw> getFw() {
        return fw;
    }

    public void setFw(List<ClfHtxxResponseFw> fw) {
        this.fw = fw;
    }
}
