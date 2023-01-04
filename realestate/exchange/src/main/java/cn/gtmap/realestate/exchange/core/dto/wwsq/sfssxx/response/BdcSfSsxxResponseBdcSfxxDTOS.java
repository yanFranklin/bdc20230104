package cn.gtmap.realestate.exchange.core.dto.wwsq.sfssxx.response;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-01-02
 * @description
 */
public class BdcSfSsxxResponseBdcSfxxDTOS {

    private BdcSfSsxxResponseBdcSlSfxxDO bdcSlSfxxDO;

    private List<BdcSfSsxxResponseBdcSlSfxmDOS> bdcSlSfxmDOS;

    public BdcSfSsxxResponseBdcSlSfxxDO getBdcSlSfxxDO() {
        return bdcSlSfxxDO;
    }

    public void setBdcSlSfxxDO(BdcSfSsxxResponseBdcSlSfxxDO bdcSlSfxxDO) {
        this.bdcSlSfxxDO = bdcSlSfxxDO;
    }

    public List<BdcSfSsxxResponseBdcSlSfxmDOS> getBdcSlSfxmDOS() {
        return bdcSlSfxmDOS;
    }

    public void setBdcSlSfxmDOS(List<BdcSfSsxxResponseBdcSlSfxmDOS> bdcSlSfxmDOS) {
        this.bdcSlSfxmDOS = bdcSlSfxmDOS;
    }
}
