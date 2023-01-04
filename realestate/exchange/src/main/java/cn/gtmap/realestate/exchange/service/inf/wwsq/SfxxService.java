package cn.gtmap.realestate.exchange.service.inf.wwsq;

import cn.gtmap.realestate.exchange.core.dto.wwsq.sfxx.response.WwsqSfxxResponseData;

import java.util.Map;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/10/17
 * @description
 */
public interface SfxxService {
    /**
     * 获取收费信息
     * @param map
     * @return
     */
    WwsqSfxxResponseData getSfxx(Map map);
}
