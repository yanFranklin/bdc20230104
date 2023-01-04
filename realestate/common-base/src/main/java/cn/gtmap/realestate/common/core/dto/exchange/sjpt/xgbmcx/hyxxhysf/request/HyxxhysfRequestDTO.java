package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.hyxxhysf.request;


import java.util.List;

/**
 * @author <a href ="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 11:25 2020/10/30
 * @description 民政部-婚姻登记信息核验（双方）接口
 */
public class HyxxhysfRequestDTO {


    private List<HyxxhysfCxywcsDTO> cxywcs;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  是否分页
     */
    private boolean loadpage;

    public List<HyxxhysfCxywcsDTO> getCxywcs() {
        return cxywcs;
    }

    public void setCxywcs(List<HyxxhysfCxywcsDTO> cxywcs) {
        this.cxywcs = cxywcs;
    }

    public boolean isLoadpage() {
        return loadpage;
    }

    public void setLoadpage(boolean loadpage) {
        this.loadpage = loadpage;
    }
}
