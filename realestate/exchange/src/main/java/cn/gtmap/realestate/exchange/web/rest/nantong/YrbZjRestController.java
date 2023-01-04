package cn.gtmap.realestate.exchange.web.rest.nantong;

import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.zjjy.clfht.ZjClfHtxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJyxxFeignService;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/11/21
 * @description 一人办-住建部门相关接口服务
 */
@OpenController(consumer = "一人办-住建部门相关接口服务")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/yrb")
public class YrbZjRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YrbZjRestController.class);


    @Autowired
    private BdcSlJyxxFeignService bdcSlJyxxFeignService;


    @OpenApi(apiDescription = "接受存量房合同信息接口", apiName = "", openLog = false)
    @PostMapping("/jsclfhtxx")
    @DsfLog(logEventName = "接受存量房合同信息接口", dsfFlag = "ZJJ", requester = "BDC", responser = "ZJJ")
    public BdcCommonResponse jsClfHtxx(@RequestBody ZjClfHtxxDTO zjClfHtxxDTO) {

        LOGGER.info("接受存量房合同信息接口入参：{}", zjClfHtxxDTO.toString());
        if (Objects.isNull(zjClfHtxxDTO) || Objects.isNull(zjClfHtxxDTO.getData())) {
            throw new AppException(0, "存量房合同信息推送数据不能为空！");
        }
        try {
            bdcSlJyxxFeignService.jsZjHtxx(zjClfHtxxDTO.getData());
            return BdcCommonResponse.ok("接受成功！");
        } catch (Exception e) {
            LOGGER.warn("接受存量房合同信息处理报错：{}", e);
            return BdcCommonResponse.fail(e.getMessage());
        }

    }

}
