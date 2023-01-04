package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.dmxxcx.request;

import java.util.List;

/**
 * @author <a href ="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 11:25 2020/10/30
 * @description 地名服务查询
 */
public class DmxxcxRequestDTO {


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  查询业务参数
     */
    private List<DmxxcxCxywcsRequestDTO> cxywcs;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  是否分页
     */
    private boolean loadpage;

    public List<DmxxcxCxywcsRequestDTO> getCxywcs() {
        return cxywcs;
    }

    public void setCxywcs(List<DmxxcxCxywcsRequestDTO> cxywcs) {
        this.cxywcs = cxywcs;
    }

    public boolean isLoadpage() {
        return loadpage;
    }

    public void setLoadpage(boolean loadpage) {
        this.loadpage = loadpage;
    }
}
