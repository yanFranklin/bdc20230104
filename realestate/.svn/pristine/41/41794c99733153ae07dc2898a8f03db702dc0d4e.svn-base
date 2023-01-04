package cn.gtmap.realestate.accept.web.rest;


import cn.gtmap.realestate.accept.service.BdcByslYwfwService;
import cn.gtmap.realestate.accept.service.BdcSlBysldjService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcByslDO;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcBysldjRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @version 1.0, 2022/08/24.
 * @description Accept不予登记/不予受理Rest接口服务
 */

@RestController
@Api(tags = "Accept不予登记不予受理Rest接口服务")
public class BdcBysldjRestController extends BaseController implements BdcBysldjRestService {

    @Autowired
    private BdcSlBysldjService bdcSlByslService;
    @Autowired
    BdcByslYwfwService bdcByslYwfwService;

    /**
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 不动产不予受理/不予登记信息数据服务
     */

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "依据工作流实例ID获取不予受理/不予登记信息", notes = "依据工作流实例ID获取不予受理/不予登记信息")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    public List<BdcByslDO> queryBdcByslDOByGzlslid(@PathVariable(name = "gzlslid") String gzlslid) {
        return bdcSlByslService.queryBdcByslDOBygzlslid(gzlslid);
    }


    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "新增不予受理/不予登记信息", notes = "依据工作流实例ID获取不予受理/不予登记信息")
    public BdcByslDO insertBysldj(@RequestBody BdcByslDO bdcByslDO) {
        return bdcSlByslService.insertBdcByslDO(bdcByslDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "依据不予受理ID删除不予受理/不予登记信息", notes = "依据工作流实例ID获取不予受理/不予登记信息")
    @ApiImplicitParam(name = "byslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcByslDOByByslid(@PathVariable(name = "byslid") String byslid) {
        return bdcSlByslService.deleteBdcByslDOByByslid(byslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "依据工作流实例ID删除不予受理/不予登记信息", notes = "依据工作流实例ID获取不予受理/不予登记信息")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcByslDOByGzlslid(@PathVariable(name = "gzlslid") String gzlslid) {
        return bdcSlByslService.deleteBdcByslDOBygzlslid(gzlslid);
    }

    /**
     * @param bdcPdfDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据受理编号更新当前不予受理流程的附件材料
     * @date : 2022/8/25 10:32
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据受理编号更新流程附件", notes = "根据受理编号更新流程附件信息")
    public void updateByslSjcl(@RequestBody BdcPdfDTO bdcPdfDTO) {
        bdcByslYwfwService.updateByslFjxx(bdcPdfDTO);
    }

    /**
     * @param processInsId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 工作流事件，推送不予受理/登记信息到签章台账
     * @date : 2022/8/25 16:05
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "推送不予受理信息到签章业务", notes = "推送不予受理信息到签章业务服务")
    public void tsByslDzqz(@RequestParam(name = "processInsId") String processInsId) {
        bdcByslYwfwService.pushBysldjToDzqz(processInsId);
    }


    /**
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 依据工作流实例ID更新不予受理信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新不予受理信息", notes = "更新不予受理信息")
    public Integer saveBdcByslDOByGzlslid(@RequestBody String json) {
        if(StringUtils.isBlank(json)){
            throw new NullPointerException("空参数异常！");
        }
        return bdcSlByslService.saveBdcByslDOBygzlslid(json);
    }

}
