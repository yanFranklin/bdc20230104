package cn.gtmap.realestate.exchange.core.dto.hefei.ems.ddjr.request;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-16
 * @description
 */
public class EmsDdjrRequestOrderNormals {

    private List<EmsDdjrRequestOrderNormal> orderNormals;

    public List<EmsDdjrRequestOrderNormal> getOrderNormals() {
        return orderNormals;
    }

    public void setOrderNormals(List<EmsDdjrRequestOrderNormal> orderNormals) {
        this.orderNormals = orderNormals;
    }
}
