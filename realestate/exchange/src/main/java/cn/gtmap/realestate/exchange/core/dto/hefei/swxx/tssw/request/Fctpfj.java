package cn.gtmap.realestate.exchange.core.dto.hefei.swxx.tssw.request;

/*
 * @author <a href="mailto:huangzijian@gtmap.cn">huangzijian</a>
 * @version 1.0, 2018/11/28
 * @description 
 */
public class Fctpfj {
    // 房产图片
    private String fctp;

    // 房产图片ID "masmytt_fctpid_" 材料序号
    private String fctpid;

    // 图片类型
    private String fctplx;

    // 房产信息ID HFBDC
    private String fcxxid;

    public String getFctp() {
        return fctp;
    }

    public void setFctp(String fctp) {
        this.fctp = fctp;
    }

    public String getFctpid() {
        return fctpid;
    }

    public void setFctpid(String fctpid) {
        this.fctpid = fctpid;
    }

    public String getFctplx() {
        return fctplx;
    }

    public void setFctplx(String fctplx) {
        this.fctplx = fctplx;
    }

    public String getFcxxid() {
        return fcxxid;
    }

    public void setFcxxid(String fcxxid) {
        this.fcxxid = fcxxid;
    }
}
