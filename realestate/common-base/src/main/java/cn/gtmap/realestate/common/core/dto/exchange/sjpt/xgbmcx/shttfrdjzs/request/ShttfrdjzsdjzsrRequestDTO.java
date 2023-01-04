package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.shttfrdjzs.request;


import java.util.List;

/**
 * @author <a href ="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 11:25 2020/10/30
 * @description 民政部-社会团体法人登记证书查询接口
 */
public class ShttfrdjzsdjzsrRequestDTO {


    private List<ShttfrdjzsCxywcsDTO> cxywcs;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  是否分页
     */
    private boolean loadpage;

    public List<ShttfrdjzsCxywcsDTO> getCxywcs() {
        return cxywcs;
    }

    public void setCxywcs(List<ShttfrdjzsCxywcsDTO> cxywcs) {
        this.cxywcs = cxywcs;
    }

    public boolean isLoadpage() {
        return loadpage;
    }

    public void setLoadpage(boolean loadpage) {
        this.loadpage = loadpage;
    }
}
