package cn.gtmap.realestate.engine.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzLogDTO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzLogQO;
import cn.gtmap.realestate.common.core.service.rest.engine.BdcGzYzLogRestService;
import cn.gtmap.realestate.engine.core.service.BdcGzYzLogService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/5/13
 * @description 规则验证日志
 */

@RestController
@Api(tags = "规则验证日志服务接口")
public class BdcGzYzLogRestController implements BdcGzYzLogRestService {

    @Autowired
    BdcGzYzLogService bdcGzYzLogService;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("规则验证日志分组分页查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "bdcGzYzLogQOJson", value = "规则验证日志查询参数json字符串", dataType = "BdcGzYzLogQO", required = false, paramType = "query")})
    public Page<BdcGzYzLogDTO> listBdcGzYzLogGroupPage(@LayuiPageable Pageable pageable,
                                                       @RequestParam(name = "bdcGzYzLogQOJson",required = false) String bdcGzYzLogQOJson) {
        return bdcGzYzLogService.listBdcGzYzLogGroupPage(pageable, JSON.parseObject(bdcGzYzLogQOJson, BdcGzYzLogQO.class));
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("规则验证日志分页查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "bdcGzYzLogQOJson", value = "规则验证日志查询参数json字符串", dataType = "BdcGzYzLogQO", required = false, paramType = "query")})
    public Page<BdcGzYzLogDTO> listBdcGzYzLogPage(@LayuiPageable Pageable pageable,
                                                       @RequestParam(name = "bdcGzYzLogQOJson",required = false) String bdcGzYzLogQOJson) {
        return bdcGzYzLogService.listBdcGzYzLogPage(pageable, JSON.parseObject(bdcGzYzLogQOJson, BdcGzYzLogQO.class));
    }
}
