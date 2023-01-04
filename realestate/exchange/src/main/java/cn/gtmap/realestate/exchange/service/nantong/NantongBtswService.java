package cn.gtmap.realestate.exchange.service.nantong;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.accept.TsswDataDTO;
import cn.gtmap.realestate.common.core.dto.exchange.PageDTO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Pageable;

/**
 * @author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @date 2022/4/26
 * @description
 */
public interface NantongBtswService {
    /**
     * 获取请求参数
     * @author wangyinghao
     * @param tsswDataDTO
     * @return Object
     * @description
     */
    Object getClfjyxxParam(TsswDataDTO tsswDataDTO);

    /**
     * 获取请求参数
     *
     * @param tsswDataDTO
     * @return Object
     * @author wangyinghao
     * @description
     */
    public Object getZlfjyxxParam(TsswDataDTO tsswDataDTO);

    /**
     *
     * @param param
     * @return
     */
    public Object swkkzt(JSONObject param);

    /**
     *
     * @param param
     * @return
     */
    public Object sendShztxx(JSONObject param);
}
