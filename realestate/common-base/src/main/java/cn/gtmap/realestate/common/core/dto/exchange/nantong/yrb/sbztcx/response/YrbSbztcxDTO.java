package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.sbztcx.response;

import java.util.List;

public class YrbSbztcxDTO {

    private List<YrbSbztcxResponseDTO> sbztxxlist;

    public List<YrbSbztcxResponseDTO> getSbztxxlist() {
        return sbztxxlist;
    }

    public void setSbztxxlist(List<YrbSbztcxResponseDTO> sbztxxlist) {
        this.sbztxxlist = sbztxxlist;
    }

    @Override
    public String toString() {
        return "YrbSbztcxDTO{" +
                "sbztxxlist=" + sbztxxlist +
                '}';
    }
}
