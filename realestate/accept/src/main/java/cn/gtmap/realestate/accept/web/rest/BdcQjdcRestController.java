package cn.gtmap.realestate.accept.web.rest;


import cn.gtmap.realestate.accept.core.service.BdcSlQjdcService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlQjdcsqDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcQjdcRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 权籍调查restController
 * @date : 2021/8/5 13:39
 */
@RestController
@Api(tags = "不动产权籍调查服务")
public class BdcQjdcRestController extends BaseController implements BdcQjdcRestService {

    @Autowired
    BdcSlQjdcService bdcSlQjdcService;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询权籍调查数据
     * @date : 2021/8/5 13:40
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询权籍调查数据", notes = "查询权籍调查数据服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, dataType = "path")})
    public List<BdcSlQjdcsqDO> listSlQjdc(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcSlQjdcService.listSlQjdc(gzlslid);
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id删除权籍调查数据
     * @date : 2021/8/5 13:41
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例id删除权籍调查数据", notes = "根据工作流实例id删除权籍调查数据服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, dataType = "path")})
    public int deleteSlQjdc(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcSlQjdcService.deleteSlQjdc(gzlslid);
    }

    /**
     * @param bdcSlQjdcsqDO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增权籍调查数据
     * @date : 2021/8/5 13:41
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "不动产受理登记原因收件材料配置", notes = "不动产受理登记原因收件材料配置服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlQjdcsqDO", value = "权籍调查信息", required = true, dataType = "bdcSlQjdcsqDO")})
    public BdcSlQjdcsqDO saveSlQjdc(@RequestBody BdcSlQjdcsqDO bdcSlQjdcsqDO) {
        return bdcSlQjdcService.saveSlQjdc(bdcSlQjdcsqDO);
    }
}
