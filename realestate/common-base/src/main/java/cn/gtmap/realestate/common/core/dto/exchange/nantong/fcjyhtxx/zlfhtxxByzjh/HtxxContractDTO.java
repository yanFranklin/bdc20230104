package cn.gtmap.realestate.common.core.dto.exchange.nantong.fcjyhtxx.zlfhtxxByzjh;

import java.util.List;

public class HtxxContractDTO {

    private List<HtxxDTO> contractList;

    public List<HtxxDTO> getContractList() {
        return contractList;
    }

    public void setContractList(List<HtxxDTO> contractList) {
        this.contractList = contractList;
    }

    @Override
    public String toString() {
        return "ZlfHtxxcontractDTO{" +
                "contractList=" + contractList +
                '}';
    }
}
