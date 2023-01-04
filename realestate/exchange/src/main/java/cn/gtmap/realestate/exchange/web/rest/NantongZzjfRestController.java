package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.dto.nantong.zzjfbcsfxx.request.ZzjfBcSfxxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.zzjfbcsfxx.response.ZzjfBcSfxxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.zzjfcxsfxx.request.ZzjfCxSfxxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.zzjfcxsfxx.response.ZzjfCxSfxxResponseDTO;
import cn.gtmap.realestate.exchange.service.impl.inf.nantong.ZfcxService;
import cn.gtmap.realestate.exchange.service.impl.inf.nantong.ZzjfService;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-29
 * @description 南通自助缴费机相关接口
 */
@OpenController(consumer = "南通自助缴费机相关接口")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/zzjf")
@Api(tags = "南通自助缴费机相关接口")
public class NantongZzjfRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NantongZzjfRestController.class);

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    private ZzjfService zzjfService;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return cn.gtmap.realestate.exchange.core.dto.nantong.zzjfcxsfxx.response.ZzjfCxSfxxResponseDTO
     * @description 查询收费信息
     */
    @OpenApi(apiDescription = "查询收费信息",apiName = "",openLog = false)
    @PostMapping("/cx/sfxx")
    @DsfLog(logEventName = "南通自助缴费机查询收费信息",dsfFlag = "ZZJFJ",requester = "ZZJFJ",responser = "BDC")
    public ZzjfCxSfxxResponseDTO cxsfxx(@RequestBody ZzjfCxSfxxRequestDTO requestDTO){
        return zzjfService.cxSfxx(requestDTO);
    }



    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return cn.gtmap.realestate.exchange.core.dto.nantong.zzjfbcsfxx.response.ZzjfBcSfxxResponseDTO
     * @description  保存收费信息
     */
    @OpenApi(apiDescription = "南通自助缴费机保存收费信息",apiName = "",openLog = false)
    @PostMapping("/bc/sfxx")
    @DsfLog(logEventName = "南通自助缴费机保存收费信息",dsfFlag = "ZZJFJ",requester = "ZZJFJ",responser = "BDC")
    public ZzjfBcSfxxResponseDTO bcSfxx(@RequestBody ZzjfBcSfxxRequestDTO requestDTO){
        ZzjfBcSfxxResponseDTO responseDTO = new ZzjfBcSfxxResponseDTO();
        responseDTO.setMsg("保存成功");
        responseDTO.setReturncode("0000");
        try{
            zzjfService.bcSfxx(requestDTO);
        }catch (Exception e){
            responseDTO.setMsg(e.getMessage());
            responseDTO.setReturncode("1000");
        }
        return responseDTO;
    }

    /**
      * 此接口不验证发票号是否在登记中已有，南通特殊需求37188
      * @param requestDTO 请求参数
      * @return ZzjfBcSfxxResponseDTO  响应结果，msg和code
      * @Date 2021/3/22
      * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
         */   
    @OpenApi(apiDescription = "南通自助缴费机保存收费信息-新",apiName = "",openLog = false)
    @PostMapping("/v2/bc/sfxx")
    @DsfLog(logEventName = "南通自助缴费机保存收费信息-新",dsfFlag = "ZZJFJ",requester = "ZZJFJ",responser = "BDC")
    public ZzjfBcSfxxResponseDTO newBcSfxx(@RequestBody ZzjfBcSfxxRequestDTO requestDTO){
        ZzjfBcSfxxResponseDTO responseDTO = new ZzjfBcSfxxResponseDTO();
        responseDTO.setMsg("保存成功");
        responseDTO.setReturncode("0000");
        try{
            zzjfService.bddcSfxxWithOutFph(requestDTO);
        }catch (Exception e){
            responseDTO.setMsg(e.getMessage());
            responseDTO.setReturncode("1000");
        }
        return responseDTO;
    }

}
