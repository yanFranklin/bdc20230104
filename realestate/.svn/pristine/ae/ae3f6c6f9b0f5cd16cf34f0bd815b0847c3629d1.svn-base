package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlFwxxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlFwxxRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: realestate
 * @description: 受理房屋信息rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-06-25 16:42
 **/
@RestController
@Api(tags = "不动产受理房屋信息服务")
public class BdcSlFwxxRestController extends BaseController implements BdcSlFwxxRestService {
    @Autowired
    BdcSlFwxxService bdcSlFwxxService;

    /**
     * @param xmid 项目id
     * @return 不动产受理房屋信息集合
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据项目id获取不动产受理房屋信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据项目id获取不动产受理房屋信息", notes = "根据项目id获取不动产受理房屋信息服务")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    public List<BdcSlFwxxDO> listBdcSlFwxxByXmid(@PathVariable(name = "xmid") String xmid) {
        return bdcSlFwxxService.listBdcSlFwxxByXmid(xmid);
    }

    /**
     * @param xmidList 项目ID集合
     * @return 不动产受理项目
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description 根据项目ID集合获取不动产受理房屋信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据项目id获取不动产受理房屋信息", notes = "根据项目id获取不动产受理房屋信息服务")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    public List<BdcSlFwxxDO> listBdcSlFwxxByXmids(@RequestBody List<String> xmidList) {
        return bdcSlFwxxService.listBdcSlFwxxByXmids(xmidList);
    }

    @ApiOperation(value = "批量更新房屋信息")
    @ApiImplicitParam(name = "bdcDjxxUpdateQO", value = "更新数量", dataType = "BdcDjxxUpdateQO", paramType = "query")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public Integer updateBatchBdcSlFwxx(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO){
        return bdcSlFwxxService.updateBatchBdcSlFwxx(bdcDjxxUpdateQO);
    }

    /**
     * @param fwxxid 房屋信息id
     * @return 不动产受理房屋信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据房屋信息id获取不动产受理房屋信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据房屋信息id获取不动产受理房屋信息", notes = "根据房屋信息id获取不动产受理房屋信息服务")
    @ApiImplicitParam(name = "fwxxid", value = "房屋信息id", required = true, dataType = "String", paramType = "path")
    public BdcSlFwxxDO queryBdcSlFwxxByFwxxid(@PathVariable(name = "fwxxid") String fwxxid) {
        return bdcSlFwxxService.queryBdcSlFwxxByFwxxid(fwxxid);
    }

    /**
     * @param bdcSlFwxxDO 不动产受理房屋信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产受理房屋信息
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增不动产受理房屋信息", notes = "新增不动产受理房屋信息服务")
    @ApiImplicitParam(name = "bdcSlFwxxDO", value = "动产受理交易信息", required = true, dataType = "BdcSlFwxxDO")
    public BdcSlFwxxDO insertBdcSlFwxx(@RequestBody BdcSlFwxxDO bdcSlFwxxDO) {
        return bdcSlFwxxService.insertBdcSlFwxx(bdcSlFwxxDO);
    }

    /**
     * @param fwxxid 交易信息id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理房屋信息
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据房屋信息ID删除不动产受理房屋信息", notes = "根据房屋信息ID删除不动产受理房屋信息服务")
    @ApiImplicitParam(name = "fwxxid", value = "房屋信息ID", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcSlFwxxByFwxxid(@PathVariable(name = "fwxxid") String fwxxid) {
        return bdcSlFwxxService.deleteBdcSlFwxxByFwxxid(fwxxid);
    }

    /**
     * @param xmid 项目id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理房屋信息
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据项目id删除不动产受理房屋信息", notes = "根据房屋信息ID删除不动产受理房屋信息服务")
    @ApiImplicitParam(name = "xmid", value = "房屋信息ID", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcSlFwxxByXmid(@PathVariable(name = "xmid") String xmid) {
        return bdcSlFwxxService.deleteBdcSlFwxxByXmid(xmid);
    }

    /**
     * @param bdcSlFwxxDO 不动产受理房屋信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存不动产受理房屋信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "保存不动产受理房屋信息", notes = "保存不动产受理房屋信息服务")
    @ApiImplicitParam(name = "bdcSlFwxxDO", value = "保存不动产受理交易信息", required = true, dataType = "BdcSlFwxxDO")
    public Integer updateBdcSlFwxx(@RequestBody BdcSlFwxxDO bdcSlFwxxDO) {
        return bdcSlFwxxService.updateBdcSlFwxx(bdcSlFwxxDO);
    }

    /**
     * @param bdcSlFwxxDO 不动产受理房屋信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据项目ID更新不动产受理房屋信息
     * <p>更新时，先通过项目ID查询房屋信息，未存在房屋信息时，进行数据新增；有数据时进行数据修改。</p>
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据项目ID更新不动产受理房屋信息", notes = "根据项目ID更新不动产受理房屋信息")
    @ApiImplicitParam(name = "bdcSlFwxxDO", value = "不动产受理交易信息", required = true, dataType = "BdcSlFwxxDO")
    public Integer updateBdcSlFwxxByXmid(@RequestBody BdcSlFwxxDO bdcSlFwxxDO) {
        return bdcSlFwxxService.updateBdcSlFwxxByXmid(bdcSlFwxxDO);
    }
}
