package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.csyxzm.request;


import java.util.List;

/**
 * @author <a href ="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 11:25 2020/10/30
 * @description 出生医学证明
 */
public class CsyxzmRequestDTO {


    private List<CsyszmCxywcsDTO> cxywcs;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  是否分页
     */
    private boolean loadpage;

    public List<CsyszmCxywcsDTO> getCxywcs() {
        return cxywcs;
    }

    public void setCxywcs(List<CsyszmCxywcsDTO> cxywcs) {
        this.cxywcs = cxywcs;
    }

    public boolean isLoadpage() {
        return loadpage;
    }

    public void setLoadpage(boolean loadpage) {
        this.loadpage = loadpage;
    }


}
