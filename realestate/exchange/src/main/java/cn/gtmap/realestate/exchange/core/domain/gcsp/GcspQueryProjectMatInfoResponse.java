package cn.gtmap.realestate.exchange.core.domain.gcsp;

import java.io.Serializable;
import java.util.List;

public class GcspQueryProjectMatInfoResponse implements Serializable {

    private static final long serialVersionUID = -7350366394426958871L;

    private String applyinstCode;
    private String localCode;
    private String gcbm;
    private String certinstCode;
    private List<GcspQueryProjectFolderInfo> matVo;

    public String getApplyinstCode() {
        return applyinstCode;
    }

    public void setApplyinstCode(String applyinstCode) {
        this.applyinstCode = applyinstCode;
    }

    public String getLocalCode() {
        return localCode;
    }

    public void setLocalCode(String localCode) {
        this.localCode = localCode;
    }

    public String getGcbm() {
        return gcbm;
    }

    public void setGcbm(String gcbm) {
        this.gcbm = gcbm;
    }

    public String getCertinstCode() {
        return certinstCode;
    }

    public void setCertinstCode(String certinstCode) {
        this.certinstCode = certinstCode;
    }

    public List<GcspQueryProjectFolderInfo> getMatVo() {
        return matVo;
    }

    public void setMatVo(List<GcspQueryProjectFolderInfo> matVo) {
        this.matVo = matVo;
    }
}
