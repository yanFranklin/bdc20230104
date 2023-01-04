package cn.gtmap.realestate.exchange.core.dto.hefei.ems.ddjr.response;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-16
 * @description EMS订单接入的响应结构
 */
public class EmsDdjrResponseDTO {

    private List<EmsDdjrResponseResponseOrders> ResponseOrders;

    public List<EmsDdjrResponseResponseOrders> getResponseOrders() {
        return ResponseOrders;
    }

    public void setResponseOrders(List<EmsDdjrResponseResponseOrders> responseOrders) {
        ResponseOrders = responseOrders;
    }
}
