package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.dqjg.request;


import java.util.List;

/**
 * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
 * @version 1.0  2019-08-27
 * @description 党群机关
 */
public class QDqjgRequestDTO {
    private List<DqjgCxywcsRequestDTO> cxywcs;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  是否分页
     */
    private boolean loadpage;



    public List<DqjgCxywcsRequestDTO> getCxywcs() {
        return cxywcs;
    }

    public void setCxywcs(List<DqjgCxywcsRequestDTO> cxywcs) {
        this.cxywcs = cxywcs;
    }

    public boolean isLoadpage() {
        return loadpage;
    }

    public void setLoadpage(boolean loadpage) {
        this.loadpage = loadpage;
    }
}
