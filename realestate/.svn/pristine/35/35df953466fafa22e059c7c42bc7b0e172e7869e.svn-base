package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcZhpSjclRestService;
import cn.gtmap.realestate.inquiry.service.BdcZhpSjclService;
import cn.gtmap.realestate.inquiry.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/10
 * @description 综合屏数据处理
 */
@RestController
@Api(tags = "综合屏数据处理")
public class BdcZhpSjclRestController extends BaseController implements BdcZhpSjclRestService {
    @Autowired
    private BdcZhpSjclService bdcZhpSjclService;

    /**
     * @param json 队列信息
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 综合屏队列信息处理
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("综合屏队列信息处理")
    @ApiImplicitParams({@ApiImplicitParam(name = "json", value = "队列信息", required = true, dataType = "String")})
    public void dlxx(@RequestBody String json) {
        bdcZhpSjclService.dlxx(json);
    }

    /**
     * @param json 传入信息
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 综合屏叫号、暂停、解除暂停、评分信息处理
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("叫号、暂停、解除暂停、评分信息处理")
    @ApiImplicitParams({@ApiImplicitParam(name = "json", value = "处理信息", required = true, dataType = "String")})
    public void xxcl(@RequestBody String json) {
        Map jsonMap = JSONObject.parseObject(json, HashMap.class);
        if (StringUtils.equals(MapUtils.getString(jsonMap, "MODE"), "CALL")) {
            bdcZhpSjclService.jhxx(json);
        }
        if (StringUtils.equals(MapUtils.getString(jsonMap, "MODE"), "PAUSE")) {
            bdcZhpSjclService.ztxx(json);
        }
        if (StringUtils.equals(MapUtils.getString(jsonMap, "MODE"), "CANCELPAUSE")) {
            bdcZhpSjclService.jcztxx(json);
        }
        if (StringUtils.equals(MapUtils.getString(jsonMap, "MODE"), "APPVALUE")) {
            bdcZhpSjclService.pfxx(json);
        }
    }

}
