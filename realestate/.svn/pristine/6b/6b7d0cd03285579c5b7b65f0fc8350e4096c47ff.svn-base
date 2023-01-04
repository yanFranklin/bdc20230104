package cn.gtmap.realestate.exchange.service.impl.national.upload;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.exchange.core.national.CityAccess;
import cn.gtmap.realestate.exchange.service.national.NationalAccessUpload;

public abstract class AbstractCityAccessUpload extends AccessUploadLog implements NationalAccessUpload {

    /**
     * 市级上报通过消息处理响应报文
     *
     * @param message
     */
    @Override
    public CommonResponse dealWithResponseForMq(String message) {
        CityAccess cityAccess = new CityAccess();
        return dealWithResponseByMqMessage(message, cityAccess);
    }

}
