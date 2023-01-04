package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.tsqxxz;

import java.util.List;

public class TsqxxzDTO {


    private List<TsqxxzRequest> data;


    public List<TsqxxzRequest> getData() {
        return data;
    }

    public void setData(List<TsqxxzRequest> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TsqxxzDTO{" +
                "data=" + data +
                '}';
    }
}
