package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcYcslService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.dto.accept.BdcSfDTO;
import cn.gtmap.realestate.common.core.dto.accept.YcslFjxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.YcslYmxxDTO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcYcslRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/25
 * @description 一窗受理服务
 */
@RestController
@Api(tags = "一窗受理服务")
public class BdcYcslRestController extends BaseController implements BdcYcslRestService {
    @Autowired
    BdcYcslService bdcYcslService;

    /**
     * @param xmid 项目ID
     * @return 一窗受理信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询一窗受理信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询一窗受理信息", notes = "查询一窗受理信息服务")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "string", paramType = "path")
    public YcslYmxxDTO queryYcslYmxx(@PathVariable(value = "xmid") String xmid){
        return bdcYcslService.queryYcslYmxx(xmid);

    }

    /**
     * @param gzlslid 工作流受理ID
     * @return 一窗受理信息
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 查询一窗受理核税附件信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询一窗受理信息核税附件", notes = "查询一窗受理信息核税附件")
    @ApiImplicitParams(@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "string"))
    public List<YcslFjxxDTO> queryYcslFjxx(@RequestParam(value = "gzlslid") String gzlslid) {
        return bdcYcslService.queryYcslFjxx(gzlslid);
    }


    /**
     * @param xmid 项目ID
     * @return 权利人和义务人税务信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询税务信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询一窗受理信息", notes = "查询一窗受理信息服务")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "string", paramType = "path")
    public BdcSfDTO queryYcslSwxx(@PathVariable(value = "xmid") String xmid){
        return bdcYcslService.queryYcslSwxx(xmid);
    }

}
