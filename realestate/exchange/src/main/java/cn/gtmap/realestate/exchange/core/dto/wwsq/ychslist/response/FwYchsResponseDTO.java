package cn.gtmap.realestate.exchange.core.dto.wwsq.ychslist.response;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-03
 * @description
 */
public class FwYchsResponseDTO {

    private List<FwYchsResponseData> data;

    public List<FwYchsResponseData> getData() {
        return data;
    }

    public void setData(List<FwYchsResponseData> data) {
        this.data = data;
    }
}
