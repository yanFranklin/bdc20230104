package cn.gtmap.realestate.accept.ui.web.rest;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/12/16
 * @description 一体化审批平台
 */
@RestController
@RequestMapping("/rest/v1.0/ythsp")
@Api(tags = "一体化审批平台")
public class BdcYthSpController extends BaseController {

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 查询一体化审批附件
      */
    @GetMapping("/fj/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "query", required = true)})
    public Object spfj(@PathVariable(name="gzlslid")String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("查询审批附件缺失参数gzlslid");
        }
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDTOList)) {
            throw new AppException("查询审批附件未查询到项目信息");
        }
        JSONObject param = new JSONObject();
        //获取token
        JSONObject tokenParam = new JSONObject();
        Object tokenResponse = exchangeInterfaceFeignService.requestInterface("yth_spfj_token_req", tokenParam);
        if (Objects.nonNull(tokenResponse)) {
            Map tokenMap = (Map) tokenResponse;
            LOGGER.info("当前流程{}查询一体化附件,获取token{}", gzlslid, tokenResponse);
            param.put("access_token", MapUtils.getString(tokenMap, "access_token", ""));
        }
        param.put("qjh", bdcXmDTOList.get(0).getBdcdyh());
        return exchangeInterfaceFeignService.requestInterface("yth_spfj", param);

    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 复制审批附件到登记
      */
    @ResponseBody
    @PostMapping("/fj/copy")
    public void copySpFj(@RequestBody String json,@RequestParam(value = "processInsId") String gzlslid) {
        if(StringUtils.isBlank(json) ||StringUtils.isBlank(gzlslid)){
            throw new AppException("上传审批附件失败,未获取到附件信息或工作流实例ID");
        }
        bdcSlSjclFeignService.uploadYthSpfj(json, gzlslid);



    }


}
