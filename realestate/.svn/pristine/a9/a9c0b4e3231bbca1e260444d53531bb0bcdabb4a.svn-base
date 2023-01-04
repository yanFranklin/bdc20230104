package cn.gtmap.realestate.exchange.service.impl.national.upload;

import cn.gtmap.realestate.exchange.core.domain.CommonResponse;
import cn.gtmap.realestate.exchange.core.national.NationalAccess;
import cn.gtmap.realestate.exchange.service.national.NationalAccessUpload;

public abstract class AbstractNationalAccessUpload extends AccessUploadLog implements NationalAccessUpload {

    /**
     * 通过消息处理响应报文
     *
     * @param message
     */
    @Override
    public CommonResponse dealWithResponseForMq(String message) {
        NationalAccess nationalAccess = new NationalAccess();
        return dealWithResponseByMqMessage(message, nationalAccess);
    }

}
