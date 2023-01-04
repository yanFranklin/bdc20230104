package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.LqService;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.LqDcbDO;
import cn.gtmap.realestate.common.core.domain.building.NydQlrDO;
import cn.gtmap.realestate.common.core.dto.building.LqDcbPageResponseDTO;
import cn.gtmap.realestate.common.core.service.rest.building.LqRestService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/14
 * @description 林权相关服务
 */
@RestController
@Api(tags = "林权服务接口")
public class LqRestController extends BaseController implements LqRestService {
    @Autowired
    private LqService lqService;

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.BdcDjsjDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据BDCDYH查询林权基本信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询林权基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public LqDcbDO queryLqByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return lqService.queryLqDcbByBdcdyh(bdcdyh);
    }

    /**
     * @param pageable
     * @param paramJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.LqDcbPageResponseDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询林权信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询林权信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<Map> listLqDcbByPageJson(Pageable pageable,
                                                          @RequestParam(name = "paramJson", required = false) String paramJson) {

        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(paramJson)) {
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return lqService.listLqDcbByPageJson(pageable, paramMap);
    }

    /**
     * @param dcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过dcbIndex查询林地所有权人
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据dcbIndex查询林地所有权人")
    @ApiImplicitParams({@ApiImplicitParam(name = "dcbIndex", value = "地籍调查表id", required = true, dataType = "String", paramType = "path")})
    public List<NydQlrDO> listLdsyqrByDcbIndex(@PathVariable("dcbIndex") String dcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return lqService.listLdsyqrByDcbIndex(dcbIndex);
    }

    /**
     * @param dcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过dcbIndex查询林木使用权人
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据dcbIndex查询林木所有权人")
    @ApiImplicitParams({@ApiImplicitParam(name = "dcbIndex", value = "地籍调查表id", required = true, dataType = "String", paramType = "path")})
    public List<NydQlrDO> listLmsuqrByDcbIndex(@PathVariable("dcbIndex") String dcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return lqService.listLmsuqrByDcbIndex(dcbIndex);
    }

    /**
     * @param dcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过dcbIndex查询林木所有权人
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据dcbIndex查询林木使用权人")
    @ApiImplicitParams({@ApiImplicitParam(name = "dcbIndex", value = "地籍调查表id", required = true, dataType = "String", paramType = "path")})
    public List<NydQlrDO> listLmsyqrByDcbIndex(@PathVariable("dcbIndex") String dcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return lqService.listLmsyqrByDcbIndex(dcbIndex);
    }
    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过djh查询林地所有权人
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "通过djh查询林地所有权人")
    @ApiImplicitParams({@ApiImplicitParam(name = "djh", value = "地籍调查表id", required = true, dataType = "String", paramType = "path")})
    public List<NydQlrDO> listLdsyqrByDjh(@PathVariable("djh") String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return lqService.listLdsyqrByDjh(djh);
    }
    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过djh查询林木使用权人
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "通过djh查询林木所有权人")
    @ApiImplicitParams({@ApiImplicitParam(name = "djh", value = "地籍调查表id", required = true, dataType = "String", paramType = "path")})
    public List<NydQlrDO> listLmsuqrByDjh(@PathVariable("djh") String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return lqService.listLmsuqrByDjh(djh);
    }
    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过djh查询林木所有权人
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据djh查询林木使用权人")
    @ApiImplicitParams({@ApiImplicitParam(name = "djh", value = "地籍调查表id", required = true, dataType = "String", paramType = "path")})
    public List<NydQlrDO> listLmsyqrByDjh(@PathVariable("djh") String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return lqService.listLmsyqrByDjh(djh);
    }

}