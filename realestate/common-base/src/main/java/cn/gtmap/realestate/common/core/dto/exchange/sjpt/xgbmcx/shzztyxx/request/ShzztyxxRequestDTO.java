package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.shzztyxx.request;


import java.util.List;

/**
 * @author <a href ="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 11:25 2020/10/30
 * @description 社会组织信息查询
 */
public class ShzztyxxRequestDTO {


    private List<ShzztyxxCxywcsDTO> cxywcs;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  是否分页
     */
    private boolean loadpage;

    public List<ShzztyxxCxywcsDTO> getCxywcs() {
        return cxywcs;
    }

    public void setCxywcs(List<ShzztyxxCxywcsDTO> cxywcs) {
        this.cxywcs = cxywcs;
    }

    public boolean isLoadpage() {
        return loadpage;
    }

    public void setLoadpage(boolean loadpage) {
        this.loadpage = loadpage;
    }
}
