package cn.gtmap.realestate.init.web.rest.changzhou;

import cn.gtmap.realestate.common.core.domain.BdcGgDO;
import cn.gtmap.realestate.common.core.domain.BdcGggxDO;
import cn.gtmap.realestate.common.core.domain.BdcGgywsjDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.BdcGgDTO;
import cn.gtmap.realestate.common.core.service.rest.init.changzhou.BdcGgRestService;
import cn.gtmap.realestate.init.core.service.BdcGgService;
import cn.gtmap.realestate.init.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 不动产公告restcontroller服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-21 10:22
 **/
@RestController
@Api(tags = "不从产公告rest服务")
public class BdcGgRestController extends BaseController implements BdcGgRestService {

    @Autowired
    BdcGgService bdcGgService;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询公告信息
     * @date : 2021/7/21 10:24
     */
    @ApiOperation(value = "查询公告信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例id", dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcGgDO> listBdcGg(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcGgService.listBdcGg(gzlslid);
    }

    /**
     * @param ggid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据公告id查询公告信息
     * @date : 2022/3/3 8:58
     */

    @ApiOperation(value = "查询公告信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "ggid", value = "公告id", dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcGgDO queryBdcGgByGgid(@PathVariable(value = "ggid") String ggid) {
        return bdcGgService.queryBdcGg(ggid);
    }

    /**
     * @param ywsjid 业务数据ID
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @description 根据公告id查询公告信息
     * @date : 2022/5/5 8:58
     */

    @ApiOperation(value = "查询公告业务数据信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "ywsjid", value = "业务数据id", dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcGgywsjDO> queryBdcGgywsjByYwsjid(@PathVariable(value = "ywsjid") String ywsjid) {
        return bdcGgService.queryBdcGgywsj(ywsjid);
    }

    @ApiOperation(value = "查询公告业务数据信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "ggid", value = "项目id", dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcGgywsjDO> queryBdcGgywsjByGgid(@PathVariable(value = "ggid") String ggid) {
        return bdcGgService.queryBdcGgywsjByGgid(ggid);
    }

    /**
     * @param ggid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据公告id查询公告信息
     * @date : 2022/3/3 8:58
     */

    @ApiOperation(value = "查询公告关系信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "ggid", value = "公告id", dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcGggxDO> queryBdcGggxByGgid(@PathVariable(value = "ggid") String ggid) {
        return bdcGgService.listBdcGggxByGgid(ggid);
    }

    /**
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据公告id查询公告信息
     * @date : 2022/3/3 8:58
     */

    @ApiOperation(value = "查询公告关系信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "ggid", value = "公告id", dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcGggxDO> listBdcGggxByXmid(@PathVariable(value = "xmid") String xmid) {
        return bdcGgService.listBdcGggxByXmid(xmid);
    }

    /**
     * @param bdcGgDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产公告数据
     * @date : 2021/7/21 10:26
     */
    @ApiOperation(value = "新增不动产公告数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcGgDO", value = "不动产公告信息对象", dataType = "BdcGgDO", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcGgDO inserBdcGg(@RequestBody BdcGgDO bdcGgDO) {
        return bdcGgService.insertBdcGg(bdcGgDO);
    }

    /**
     * @param  xmidList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产公告数据
     * @date : 2021/7/21 10:26
     */
    @ApiOperation(value = "新增不动产公告数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcGgDO", value = "不动产公告信息对象", dataType = "BdcGgDO", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int insertBdcGggx(@RequestBody List<String> xmidList, @RequestParam(name = "ggid") String ggid) {
        return bdcGgService.insertBdcGggx(xmidList, ggid);
    }

    /**
     * @param  bdcGgDTO
     * @author <a href="mailto:wutao2@gtmap.cn">wutao</a>
     * @description 新增不动产公告业务数据
     * @date : 2021/7/21 10:26
     */
    @ApiOperation(value = "新增不动产公告数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcGgDTO", value = "不动产公告信息对象", dataType = "BdcGgDO", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Object insertBdcGgYwsj(@RequestBody BdcGgDTO bdcGgDTO,boolean sfgl){
        return bdcGgService.insertBdcGgYwsj(bdcGgDTO,sfgl);
    }

    @Override
    public int batchUpdatebdcggzt(@RequestBody List<String> ggids, @RequestParam(name = "ggzt") String ggzt) {
        return bdcGgService.batchUpdatebdcggzt(ggids,ggzt);
    }

    @ApiOperation(value = "根据公告ID查询公告关联的流程信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "ggid", value = "公告ID", dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcXmDO> queryBdcGgGlBdcXmxx(@PathVariable(value = "ggid") String ggid) {
        return bdcGgService.queryBdcGgGlBdcXmxx(ggid);
    }

    @ApiOperation(value = "根据公告ID删除公告信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "ggid", value = "公告ID", dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int deleteBdcGgxx(@RequestBody List<String> ggids) {
        return bdcGgService.deleteBdcGgxx(ggids);
    }

}
