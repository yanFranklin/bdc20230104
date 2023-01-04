package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlYqFjxxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYqFjxxDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlYqFjxxRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description 不动产受理邮寄信息REST服务
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/12/12.
 *
 */
@RestController
@Api(tags = "不动产受理邮寄信息REST服务")
public class BdcSlYqFjxxRestController extends BaseController implements BdcSlYqFjxxRestService {

    @Autowired
    private BdcSlYqFjxxService bdcSlYqFjxxService;


    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询不动产受理云签附件信息", notes = "查询不动产受理云签附件信息")
    @ApiImplicitParam(name = "bdcSlYqFjxxDO", value = "不动产云签附件信息DO", required = true, dataType = "BdcSlYqFjxxDO", paramType = "body")
    public List<BdcSlYqFjxxDO> listBdcSlYqFjxx(@RequestBody BdcSlYqFjxxDO bdcSlYqFjxxDO) {
        return bdcSlYqFjxxService.listBdcSlYqFjxx(bdcSlYqFjxxDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "批量保存不动产云签附件信息", notes = "批量保存不动产云签附件信息")
    @ApiImplicitParam(name = "bdcSlYjxxDTO", value = "不动产云签附件信息DO", required = true, dataType = "List", paramType = "body")
    public void batchSaveBdcSlYqFjxx(@RequestBody List<BdcSlYqFjxxDO> bdcSlYqFjxxDOList) {
        bdcSlYqFjxxService.batchSaveBdcSlYqFjxx(bdcSlYqFjxxDOList);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID删除云签附件信息", notes = "根据工作流实例ID删除云签附件信息")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "param")
    public void deleteBdcSlYqFjxxByGzlslid(@RequestParam(name="gzlslid") String gzlslid) {
        this.bdcSlYqFjxxService.deleteBdcSlYqFjxxByGzlslid(gzlslid);
    }

}
