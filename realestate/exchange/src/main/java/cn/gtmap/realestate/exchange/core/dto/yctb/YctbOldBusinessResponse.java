package cn.gtmap.realestate.exchange.core.dto.yctb;

import java.io.Serializable;
import java.util.List;

public class YctbOldBusinessResponse implements Serializable {

    private static final long serialVersionUID = 2169762965277736558L;

    private YctbOldBusinessReqmap reqmap;

    private List<YctbOldBusinessQlrInfo> applicantList;

    public YctbOldBusinessReqmap getReqmap() {
        return reqmap;
    }

    public void setReqmap(YctbOldBusinessReqmap reqmap) {
        this.reqmap = reqmap;
    }

    public List<YctbOldBusinessQlrInfo> getApplicantList() {
        return applicantList;
    }

    public void setApplicantList(List<YctbOldBusinessQlrInfo> applicantList) {
        this.applicantList = applicantList;
    }
}
