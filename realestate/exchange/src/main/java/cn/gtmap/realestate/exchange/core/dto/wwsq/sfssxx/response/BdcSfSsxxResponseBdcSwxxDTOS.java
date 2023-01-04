package cn.gtmap.realestate.exchange.core.dto.wwsq.sfssxx.response;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-01-02
 * @description
 */
public class BdcSfSsxxResponseBdcSwxxDTOS {

    private List<BdcSfSsxxResponseBdcSlHsxxMxDOList> bdcSlHsxxMxDOList;

    private BdcSfSsxxResponseBdcSlHsxxDO bdcSlHsxxDO;

    public List<BdcSfSsxxResponseBdcSlHsxxMxDOList> getBdcSlHsxxMxDOList() {
        return bdcSlHsxxMxDOList;
    }

    public void setBdcSlHsxxMxDOList(List<BdcSfSsxxResponseBdcSlHsxxMxDOList> bdcSlHsxxMxDOList) {
        this.bdcSlHsxxMxDOList = bdcSlHsxxMxDOList;
    }

    public BdcSfSsxxResponseBdcSlHsxxDO getBdcSlHsxxDO() {
        return bdcSlHsxxDO;
    }

    public void setBdcSlHsxxDO(BdcSfSsxxResponseBdcSlHsxxDO bdcSlHsxxDO) {
        this.bdcSlHsxxDO = bdcSlHsxxDO;
    }
}
