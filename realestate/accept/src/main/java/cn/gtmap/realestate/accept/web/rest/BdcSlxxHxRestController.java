package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlJbxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlxxHxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlxxHxRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/9/14
 * @description
 */
@RestController
@Api(tags = "不动产受理信息回写rest服务")
public class BdcSlxxHxRestController extends BaseController implements BdcSlxxHxRestService {

    @Autowired
    BdcSlxxHxService bdcSlxxHxService;

    @Autowired
    BdcSlJbxxService bdcSlJbxxService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "保存或更新受理信息到平台", notes = "保存或更新受理信息到平台")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String")
    public void hxBdcSlxx(@PathVariable("gzlslid") String gzlslid) throws Exception{
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("gzlslid");

        }
        BdcSlJbxxDO bdcSlJbxxDO =bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        bdcSlxxHxService.hxBdcSlxx(bdcSlJbxxDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "保存或更新受理信息和自定义信息至大云平台", notes = "保存或更新受理信息和自定义信息至大云平台")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "zdyxxMap", value = "自定义信息", required = true, dataType = "Map", paramType = "body"),
    })
    public void hxBdcSlxxWithZdyxx(@PathVariable("gzlslid") String gzlslid, @RequestBody Map zdyxxMap) throws Exception {
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("gzlslid");
        }
        BdcSlJbxxDO bdcSlJbxxDO =bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        bdcSlxxHxService.hxBdcSlxxWithZdyxx(bdcSlJbxxDO, zdyxxMap);
    }


}
