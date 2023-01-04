package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.qyxx.request;


import java.util.List;

/**
 * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
 * @version 1.0  2019-08-27
 * @description 企业基本信息查询 业务参数实体
 */
public class QyxxRequestDTO {
    private List<QyxxCxywcsRequestDTO> cxywcs;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  是否分页
     */
    private boolean loadpage;

    public List<QyxxCxywcsRequestDTO> getCxywcs() {
        return cxywcs;
    }

    public void setCxywcs(List<QyxxCxywcsRequestDTO> cxywcs) {
        this.cxywcs = cxywcs;
    }

    public boolean isLoadpage() {
        return loadpage;
    }

    public void setLoadpage(boolean loadpage) {
        this.loadpage = loadpage;
    }
}
