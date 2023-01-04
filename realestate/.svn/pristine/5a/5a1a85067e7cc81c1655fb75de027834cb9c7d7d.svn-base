package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.AccessBuildingService;
import cn.gtmap.realestate.building.core.service.FwqsxxService;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.exchange.*;
import cn.gtmap.realestate.common.core.service.rest.building.AccessBuildingRestService;
import cn.gtmap.realestate.common.core.service.rest.building.FwqsxxRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021/07/13
 * @description
 */
@RestController
@RequestMapping("/building/rest/v1.0/fwqsxx")
@Api(tags = "获取权籍数据接口")
public class FwqsxxRestController extends BaseController implements FwqsxxRestService {

    @Autowired
    private AccessBuildingService accessBuildingService;

    @Autowired
    FwqsxxService fwqsxxService;


    /**
     * 获取KTT_FW_ZRZ--和权籍分开
     *
     * @param bdcdyh
     * @return
     */
    @GetMapping("/fwzrz")
    @ApiOperation(value = "查询自然幢信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "String", paramType = "query")})
    public List<Map> queryQsxxKttFwZrzList(@RequestParam("bdcdyh") String bdcdyh) {
        if (StringUtils.isEmpty(bdcdyh)) {
            return null;
        }
        return fwqsxxService.queryKttFwZrzList(bdcdyh);
    }


    @Override
    @GetMapping("/fwljz")
    @ApiOperation(value = "查询逻辑幢信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "String", paramType = "query")})
    public List<KttFwLjzDO> queryQsxxKttFwLjzList(@RequestParam("bdcdyh") String bdcdyh) {
        if (StringUtils.isEmpty(bdcdyh)) {
            return null;
        }
        return fwqsxxService.queryKttFwLjzList(bdcdyh);
    }

    @Override
    @GetMapping("/fwh")
    @ApiOperation(value = "查询户室信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sfdz", value = "是否独幢", dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "qjgldm", value = "权籍代码", dataType = "string", paramType = "query")})
    public List<KttFwHDO> queryQsxxKttFwHList(@RequestParam("bdcdyh") String bdcdyh,
                                              @RequestParam("sfdz") boolean sfdz) {
        if (StringUtils.isEmpty(bdcdyh)) {
            return null;
        }
        if (sfdz) {
            return fwqsxxService.queryKttFwHListIsDz(bdcdyh);
        } else {
            return fwqsxxService.queryKttFwHListNotDz(bdcdyh);
        }
    }

    @Override
    @GetMapping("/fwc")
    @ApiOperation(value = "查询户室层信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "String", paramType = "query")})
    public List<KttFwCDO> queryQsxxKttFwCList(@RequestParam("bdcdyh") String bdcdyh) {
        if (StringUtils.isEmpty(bdcdyh)) {
            return null;
        }
        return fwqsxxService.queryKttFwCList(bdcdyh);
    }
}