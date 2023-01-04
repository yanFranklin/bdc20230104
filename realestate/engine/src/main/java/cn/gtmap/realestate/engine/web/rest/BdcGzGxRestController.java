package cn.gtmap.realestate.engine.web.rest;

import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzGxDO;
import cn.gtmap.realestate.common.core.service.rest.engine.BdcGzGxRestService;
import cn.gtmap.realestate.engine.core.service.BdcGzGxService;
import cn.gtmap.realestate.engine.util.Constants;
import cn.gtmap.realestate.engine.web.main.BaseController;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2019/3/6
 * @description 不动产规则关系接口服务
 */
@RestController
@Api(tags="不动产规则关系接口服务")
public class BdcGzGxRestController extends BaseController implements BdcGzGxRestService {
    @Autowired
    private BdcGzGxService bdcGzGxService;
    /**
     * @param zhid
     * @return bdcGzGxDOList
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 通过zhid获取组合关系列表
     */
    @Override
    @ApiOperation(value = "查询规则关系信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "zhid", value = "组合id", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcGzGxDO> listBdcGzGxByZhid(@PathVariable(value = "zhid") String zhid) {
        return bdcGzGxService.queryBdcGzGxListByZhid(zhid);
    }

    /**
     * @param gzid
     * @return bdcGzGxDOList
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 通过gzid获取所有组合关系
     */
    @Override
    @ApiOperation(value = "查询规则关系信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "gzid", value = "gzid", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcGzGxDO> listBdcGzGxByGzid(@PathVariable(value = "gzid") String gzid) {
        return bdcGzGxService.queryBdcGzGxListByGzid(gzid);
    }

    /**
     * @param bdcGzGxDO
     * @return bdcGzGxDO
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 新增不动产规则关系
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增规则关系", notes = "新增规则关系服务")
    @LogNote(value = "新增规则关系", action = Constants.LOG_ACTION_GZCZ)
    public BdcGzGxDO insertBdcGzGx(@RequestBody BdcGzGxDO bdcGzGxDO) {
        return bdcGzGxService.insertBdcGzGx(bdcGzGxDO);
    }

    /**
     * @param gxid
     * @return num
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 通过规则关系主键gxid删除规则关系
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据关系id删除规则关系信息", notes = "根据关系id删除规则关系信息服务")
    @ApiImplicitParam(name = "gxid", value = "关系id", required = true, dataType = "String", paramType = "query")
    @LogNote(value = "根据关系id删除规则关系信息", action = Constants.LOG_ACTION_GZCZ)
    public void delBdcGzGxByGxid(@PathVariable(value = "gxid") String gxid) {
        bdcGzGxService.delBdcGzGxByGxid(gxid);
    }

    /**
     * @param zhid
     * @return num
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 通过zhid删除规则关系
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据组合id删除规则关系信息", notes = "根据组合id删除规则关系信息服务")
    @ApiImplicitParam(name = "zhid", value = "组合id", required = true, dataType = "String", paramType = "query")
    @LogNote(value = "根据组合id删除规则关系信息", action = Constants.LOG_ACTION_GZCZ)
    public void delBdcGzGxByZhid(@PathVariable(value = "zhid") String zhid) {
        bdcGzGxService.delBdcGzGxByZhid(zhid);
    }

    /**
     *@author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu2</a>
     *@param  gzid
     *@return num 删除的记录数
     *@description 通过gzid删除规则关系
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据gzid删除规则关系信息", notes = "根据gzid删除规则关系信息服务")
    @ApiImplicitParam(name = "gzid", value = "gzid", required = true, dataType = "String", paramType = "query")
    @LogNote(value = "根据gzid删除规则关系信息", action = Constants.LOG_ACTION_GZCZ)
    public void delBdcGzGxByGzid(@PathVariable(value = "gzid") String gzid) {
        bdcGzGxService.delBdcGzGxByGzid(gzid);
    }
}
