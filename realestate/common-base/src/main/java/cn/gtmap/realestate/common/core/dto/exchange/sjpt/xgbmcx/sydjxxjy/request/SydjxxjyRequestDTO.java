package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.sydjxxjy.request;


import java.util.List;

/**
 * @author <a href ="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 11:25 2020/10/30
 * @description 民政部-基金会法人登记证书查询接口
 */
public class SydjxxjyRequestDTO {


    private List<SydjxxjyCxywcsDTO> cxywcs;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  是否分页
     */
    private boolean loadpage;

    public List<SydjxxjyCxywcsDTO> getCxywcs() {
        return cxywcs;
    }

    public void setCxywcs(List<SydjxxjyCxywcsDTO> cxywcs) {
        this.cxywcs = cxywcs;
    }

    public boolean isLoadpage() {
        return loadpage;
    }

    public void setLoadpage(boolean loadpage) {
        this.loadpage = loadpage;
    }
}
