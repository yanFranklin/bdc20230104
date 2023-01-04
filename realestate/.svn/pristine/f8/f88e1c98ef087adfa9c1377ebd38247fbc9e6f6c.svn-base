package cn.gtmap.realestate.exchange.web.rest.yancheng;

import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfDaCheckLog;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.dto.yancheng.zzcxj.response.FwqlCxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.zzcxj.response.FwqlCxResponseData;
import cn.gtmap.realestate.exchange.core.dto.yancheng.zzcxj.response.FwqsCxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.request.FwqlCxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.request.FwqlCxRequestQlr;
import cn.gtmap.realestate.exchange.service.impl.inf.nantong.ZfcxService;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.BdcFwQsxxService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.constants.LogCzxxConstants;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0  2020-11-23
 * @description (盐城) 住房查询服务
 */
@OpenController(consumer = "(盐城) 住房查询服务")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0")
@Api(tags = "(盐城) 住房查询服务")
public class BdcZfcxRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZfcxRestController.class);

    @Autowired
    private BdcFwQsxxService bdcFwQsxxService;
    @Autowired
    private ZfcxService zfcxService;


    /**
     * 自助查询机房屋权属信息查询服务（以物为主）
     * 说明：代码参照 StandardZfcxRestController#houseStatusByQl 改动，因为请求参数、逻辑、返回值都有差异，因此重新定义接口
     *
     * @param areacode 行政区划代码
     * @param fwqlCxRequestDTO 查询参数
     * @return FwqlCxResponseDTO 房屋权属信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @OpenApi(apiDescription = "自助查询机房屋权属信息查询服务（以物为主）",apiName = "",openLog = false)
    @PostMapping(value = "/yancheng/fwqsxx/{areacode}")
    @DsfLog(logEventName = "自助查询机房屋权属信息查询服务（以物为主）",dsfFlag = "ZZCXJ",requester = "ZZCXJ",responser = "BDC")
    @DsfDaCheckLog(logEventName = "自助查询机房屋权属信息查询服务（以物为主）",dsfFlag = "ZZCXJ",requester = "ZZCXJ",responser = "BDC",interfaceFlagCode = "06",checkFlagIndex = 1,checkFlagClassName = "cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.request.FwqlCxRequestDTO",checkFlagLever = "0",interfaceUrl = "/realestate-exchange/rest/v1.0/yancheng/fwqsxx/{areacode}")
    Object queryFwQsxx(@PathVariable(name = "areacode")String areacode, @RequestBody FwqlCxRequestDTO fwqlCxRequestDTO){
        long startTime = System.currentTimeMillis();
        FwqlCxResponseDTO fwqlCxResponseDTO = new FwqlCxResponseDTO();
        fwqlCxResponseDTO.setData(new FwqlCxResponseData());
        List<FwqlCxRequestQlr> qlrList = fwqlCxRequestDTO.getQlrlist();
        if(CollectionUtils.isEmpty(qlrList)){
            fwqlCxResponseDTO.getData().setIsSuccessful("查询失败");
            fwqlCxResponseDTO.setMessage("未找到查询参数");
            return fwqlCxResponseDTO;
        }

        try{
            // 查询房屋权属信息
            List<FwqsCxResponseDTO> fwQsxxList = bdcFwQsxxService.queryFwQsxx(fwqlCxRequestDTO, fwqlCxResponseDTO.getQqsj());

            // 处理查询结果信息结构
            fwqlCxResponseDTO.getData().setFwlist(fwQsxxList);
            fwqlCxResponseDTO.getData().setIsSuccessful("查询成功");
            Map<String, Object> ywbhMap;
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(fwqlCxRequestDTO));
            jsonObject.put("cztype", LogCzxxConstants.CZTYPE_CJZM);
            ywbhMap = zfcxService.fwqsWebServiceQuery(JSONObject.toJSONString(fwqlCxResponseDTO.getData()), jsonObject.toString());
            if(MapUtils.isNotEmpty(ywbhMap)){
                String ywbh = CommonUtil.ternaryOperator(ywbhMap.get("cxbh"));
                fwqlCxResponseDTO.getData().setYWBH(ywbh);
                fwqlCxResponseDTO.setSuccess(true);
            }
            fwqlCxResponseDTO.setTotal(org.apache.commons.collections4.CollectionUtils.size(fwQsxxList));
            fwqlCxResponseDTO.setRecords(org.apache.commons.collections4.CollectionUtils.size(fwQsxxList));
            fwqlCxResponseDTO.setPage(1);
            fwqlCxResponseDTO.setQqsj(DateUtils.getCurrentTimeStr());
            fwqlCxResponseDTO.setQtime(System.currentTimeMillis() - startTime);
            fwqlCxResponseDTO.setCxbh(UUID.randomUUID());
            fwqlCxResponseDTO.setSize(org.apache.commons.collections4.CollectionUtils.size(fwQsxxList));
            return fwqlCxResponseDTO;
        }
        catch (Exception e) {
            fwqlCxResponseDTO.setSuccess(false);
            fwqlCxResponseDTO.getData().setIsSuccessful("查询失败");
            LOGGER.error("自助查询机房屋权属查询(以权利为主)查询异常", e);
        }

        LOGGER.info("房屋权属查询返回结果:{}",JSONObject.toJSONString(fwqlCxResponseDTO));
        return fwqlCxResponseDTO;
    }
}
