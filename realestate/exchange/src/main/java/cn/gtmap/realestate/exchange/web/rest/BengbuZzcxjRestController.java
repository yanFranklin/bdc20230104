package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcBzbZmFeignService;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.GetCurrentTimeResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.request.ZfcxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.request.ZfcxRequestQlr;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.response.ZfcxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.response.ZfcxResponseData;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.response.ZfcxResponseFw;
import cn.gtmap.realestate.exchange.service.impl.inf.nantong.ZfcxService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.constants.LogCzxxConstants;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/5/6.
 * @description 蚌埠自助查询机 交互接口
 */
@OpenController(consumer = "蚌埠自助查询机")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0")
@Api(tags = "蚌埠自助查询机查询服务")
public class BengbuZzcxjRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BengbuZzcxjRestController.class);

    // 判断是否需要验证同名权利人证件号为空或不规则的情况
    @Value("${zzcxj.valid.qlrzjh:true}")
    private boolean validQlrzjh;
    @Autowired
    private ZfcxService zfcxService;
    @Autowired
    private BdcBzbZmFeignService bdcBzbZmFeignService;


    /**
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @param areacode
     * @param zfcxRequestDTO
     * @return cn.gtmap.realestate.exchange.core.dto.nantong.zfcx.response.ZfcxResponseDTO
     * @description 自助查询机 有房无房查询
     * 组织返回数据与南通有房无房查询接口用的一套逻辑，只在方法上做了区分，后面有特殊需求方便处理
     */
    @OpenApi(apiName = "",apiDescription = "自助查询机 有房无房查询",openLog = false)
    @PostMapping("bbZzcxj/searchFc/isExitHouse/{areacode}")
    @DsfLog(logEventName = "自助查询机有房无房查询",dsfFlag = "ZZCXJ",requester = "ZZCXJ",responser = "BDC")
    ZfcxResponseDTO zfcx(@PathVariable(name = "areacode")String areacode,
                         @RequestBody ZfcxRequestDTO zfcxRequestDTO){
        long startTime = System.currentTimeMillis();
        ZfcxResponseDTO zfcxResponseDTO = new ZfcxResponseDTO();
        String cdsj = zfcxResponseDTO.getQqsj();
        zfcxResponseDTO.setData(new ZfcxResponseData());
        List<ZfcxRequestQlr> qlrList = zfcxRequestDTO.getQlrlist();
        if(CollectionUtils.isEmpty(qlrList)){
            zfcxResponseDTO.getData().setIsSuccessful("查询失败");
            zfcxResponseDTO.setMessage("未找到查询参数");
            return zfcxResponseDTO;
        }

        // 使用权利人姓名查询权利人 如果存在 证件号为空 或证件号 不足15位 18位 则返回异常
        if (validQlrzjh && zfcxService.checkBdcQlrHasZjhEx(qlrList)) {
            zfcxResponseDTO.getData().setIsSuccessful("查询失败");
            zfcxResponseDTO.setMessage("存在相同权利人名称但是证件号为空或不规范的情况，请去人工窗口查询");
            return zfcxResponseDTO;
        }
        try{
            List<ZfcxResponseFw> fwList = zfcxService.queryFwListByRequestBb(zfcxRequestDTO,cdsj);
            zfcxResponseDTO.getData().setFwlist(fwList);
            zfcxResponseDTO.getData().setIsSuccessful("查询成功");
            Map<String, Object> ywbhMap;
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(zfcxRequestDTO));
            jsonObject.put("cztype", LogCzxxConstants.CZTYPE_CJZM);
            if (CollectionUtils.isNotEmpty(fwList)) {
                ywbhMap = zfcxService.yfcxWebServicePublicQueryLog(
                        JSONObject.toJSONString(zfcxResponseDTO.getData()), jsonObject.toString());
            } else {
                ywbhMap = zfcxService.wfcxWebServicePublicQueryLog(
                        JSONObject.toJSONString(zfcxResponseDTO.getData()), jsonObject.toString());
            }
            if(MapUtils.isNotEmpty(ywbhMap)){
//                String ywbh = CommonUtil.ternaryOperator(ywbhMap.get("cxbh"));
                // 编号改成和登记系统综合查询有房无房查询同一个类型编号
                String ywbh = bdcBzbZmFeignService.getYfwfzmBh();
                zfcxResponseDTO.getData().setYWBH(ywbh);
                zfcxResponseDTO.setSuccess(true);
            }
            zfcxResponseDTO.setTotal(org.apache.commons.collections4.CollectionUtils.size(fwList));
            zfcxResponseDTO.setRecords(org.apache.commons.collections4.CollectionUtils.size(fwList));
            zfcxResponseDTO.setPage(1);
            zfcxResponseDTO.setQqsj(DateUtils.getCurrentTimeStr());
            zfcxResponseDTO.setQtime(System.currentTimeMillis() - startTime);
            zfcxResponseDTO.setCxbh(UUID.randomUUID());
            zfcxResponseDTO.setSize(org.apache.commons.collections4.CollectionUtils.size(fwList));
        } catch (Exception e) {
            zfcxResponseDTO.setSuccess(false);
            zfcxResponseDTO.getData().setIsSuccessful("查询失败");
            LOGGER.error("自助查询机有房无房查询异常",e);
        }
        LOGGER.info("有房无房查询返回结果:{}",JSONObject.toJSONString(zfcxResponseDTO));
        return zfcxResponseDTO;
    }


    /**
     * 获取当前时间接口
     * @param areacode
     * @return
     */
    @OpenApi(apiName = "",apiDescription = "获取当前时间接口",openLog = false)
    @PostMapping("bbZzcxj/searchFc/getCurrentTime/{areacode}")
    @DsfLog(logEventName = "自助查询机获取当前时间",dsfFlag = "ZZCXJ",requester = "ZZCXJ",responser = "BDC")
    GetCurrentTimeResponseDTO zfcx(@PathVariable(name = "areacode")String areacode){
        GetCurrentTimeResponseDTO currentTimeResponseDTO = new GetCurrentTimeResponseDTO();
        StringBuilder currentTime = new StringBuilder();
        JSONObject data = new JSONObject();
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentTime.append(df.format(new Date()));
        data.put("currentTime", currentTime);
        currentTimeResponseDTO.setData(data);
        currentTimeResponseDTO.setSuccess(true);
        return currentTimeResponseDTO;
    }

}
