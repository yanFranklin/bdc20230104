package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYjxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.CourtUpdateYwcjzt;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlYjxxFeginService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.SjrptRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.TokenUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.config.filter.RequestWrapper;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.dto.sjrpt.OrgReqData;
import cn.gtmap.realestate.exchange.service.impl.inf.sjrpt.SjrptServiceImpl;
import cn.gtmap.realestate.exchange.service.impl.inf.standard.BdcCourtKzServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.util.HttpClientUtils;
import cn.gtmap.realestate.exchange.util.sjrpt.Sm2Util;
import cn.gtmap.realestate.exchange.util.sjrpt.Sm3Util;
import cn.gtmap.realestate.exchange.util.sjrpt.Sm4Util;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SmUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangyinghao
 */
@OpenController(consumer = "法院点对点")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/court")
@Api(tags = "省金融平台服务")
public class CourtWorkflowRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourtWorkflowRestController.class);

    @Autowired
    private HttpClientUtils httpClientUtils;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    BdcCourtKzServiceImpl bdcCourtKzService;

    @Autowired
    CommonService commonService;

    @Autowired
    BdcCzrzFeignService bdcCzrzFeignService;


    @OpenApi(apiDescription = "流程办结解除锁定", apiName = "", openLog = false)
    @PostMapping("/bjjs")
    public void courtBjJsBdcdy(@RequestParam(name = "processInsId") String processInsId,
                               @RequestParam(name = "currentUserName") String currentUserName) {
        BdcXmQO xmQO = new BdcXmQO();
        xmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(xmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS) || null == bdcXmDOS.get(0)) {
            throw new AppException("数据异常，gzlslid：" + processInsId + "未查询到对应的项目数据");
        }
        //获取退回意见
        BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
        bdcCzrzDO.setGzlslid(processInsId);
        bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_WWTJ.key());
        List<BdcCzrzDO> bdcCzrzDOS = bdcCzrzFeignService.listBdcCzrz(bdcCzrzDO);
        String opinion = "";
        if(CollectionUtils.isNotEmpty(bdcCzrzDOS) && StringUtils.isNotBlank(bdcCzrzDOS.get(0).getCzyy())){
            opinion = bdcCzrzDOS.get(0).getCzyy();
        }
        List<BdcDysdDO> bdcDysdDOList = new ArrayList<>();
        for (BdcXmDO bdcXmDO : bdcXmDOS) {
            BdcDysdDO bdcDysdDO = new BdcDysdDO();
            bdcDysdDO.setBdcdyh(bdcXmDO.getBdcdyh());
            bdcDysdDO.setSdsqwh(bdcXmDO.getSpxtywh());
            if (bdcXmDO.getAjzt().equals(CommonConstantUtils.AJZT_YB_DM)) {
                bdcDysdDO.setJsyy("流程办结");
            } else {
                bdcDysdDO.setJsyy(opinion);
            }
            bdcDysdDOList.add(bdcDysdDO);
        }
        if (CollectionUtils.isNotEmpty(bdcDysdDOList)) {
            try {
                bdcCourtKzService.jsBdcdy(bdcDysdDOList);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.info("流程{}，解锁失败{}", processInsId, e.getMessage());
            }
        }
    }
}
