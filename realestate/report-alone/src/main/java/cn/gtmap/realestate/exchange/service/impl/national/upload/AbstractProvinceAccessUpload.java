package cn.gtmap.realestate.exchange.service.impl.national.upload;

import cn.gtmap.realestate.exchange.core.domain.CommonResponse;
import cn.gtmap.realestate.exchange.core.national.ProvinceAccess;
import cn.gtmap.realestate.exchange.service.national.NationalAccessUpload;

public abstract class AbstractProvinceAccessUpload extends AccessUploadLog implements NationalAccessUpload {

    /**
     * 通过消息处理响应报文
     *
     * @param message
     */
    @Override
    public CommonResponse dealWithResponseForMq(String message) {
        ProvinceAccess provinceAccess = new ProvinceAccess();
        return dealWithResponseByMqMessage(message, provinceAccess);
    }

}
