package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.service.ZrzService;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.service.rest.building.ZrzRestService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-11
 * @description 自然幢服务
 */
@RestController
@Api(tags = "自然幢服务")
public class ZrzRestController extends BaseController implements ZrzRestService {

    @Autowired
    private ZrzService zrzService;

    /**
     * @param pageable
     * @param paramJson
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 自然幢分页查询
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "自然幢分页查询")
    public Page<Map> listZrzByPage(Pageable pageable, String paramJson) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(paramJson)) {
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return zrzService.listZrzByPage(pageable,paramMap);
    }

    /**
     * @param djh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据LSZD查询宗地下最大自然幢号（查询FW_LJZ表）
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询权属宗地基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "djh", value = "地籍号", required = true, dataType = "String", paramType = "path")})
    public int getMaxZrzhByDjh(@PathVariable("djh") String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return zrzService.getMaxZrzhByDjh(djh);
    }
}
