package cn.gtmap.realestate.exchange.service.impl.inf.hefei;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.hefei.sdkApiHyxx.request.SdkApiHyxxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.hefei.sdkApiHyxx.response.SdkApiHyxxResponseDTO;
import cn.gtmap.realestate.exchange.service.impl.inf.ExchangeBeanRequestServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import com.alibaba.fastjson.JSONObject;
import jodd.util.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-10-10
 * @description  SDK 查询婚姻信息服务
 */
@Service
public class SdkHyxxServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(SdkHyxxServiceImpl.class);
    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return cn.gtmap.realestate.exchange.core.dto.hefei.sdkApiHyxx.request.SdkApiHyxxRequestDTO
     * @description 省级查询婚姻信息（获取token、离婚、结婚接口）
     */
    public SdkApiHyxxResponseDTO requestSdkHyxx(SdkApiHyxxRequestDTO requestDTO){
        SdkApiHyxxResponseDTO response = new SdkApiHyxxResponseDTO();
        // 先请求TOKEN
        Map<String,String> tokenMap = exchangeBeanRequestService.request("hf_sdk_token",new HashMap<>(),Map.class);
        if(StringUtil.isNotBlank(MapUtils.getString(tokenMap,"Token"))){
            String token = MapUtils.getString(tokenMap,"Token");
            requestDTO.setToken(token);
            // 查询结婚信息
            response = exchangeBeanRequestService.request("hf_sdk_jhxx",requestDTO,SdkApiHyxxResponseDTO.class);
            if(response == null || !CheckParameter.checkAnyParameter(response)){
                // 查询离婚
                response = exchangeBeanRequestService.request("hf_sdk_lhxx",requestDTO,SdkApiHyxxResponseDTO.class);
                if(response == null || !CheckParameter.checkAnyParameter(response)){
                    response.setHyzt("未婚");
                }else{
                    response.setHyzt("离婚");
                }
            }else{
                response.setHyzt(CommonConstantUtils.HYZK_YH_MC);
            }
        }else{
            LOGGER.error("获取婚姻token失败,{}",tokenMap);
            throw new AppException("获取婚姻token失败");
        }
        return response;
    }
}
