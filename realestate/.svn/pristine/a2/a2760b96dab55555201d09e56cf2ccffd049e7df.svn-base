package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcTdcbnydsyqDO;
import cn.gtmap.realestate.common.core.dto.register.BdcDkxxDTO;
import cn.gtmap.realestate.common.core.service.rest.register.BdcTdcbjyqNydqtsyqRestService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDkxxQO;
import cn.gtmap.realestate.register.service.BdcTdcbjyqNydqtsyqService;
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

@RestController
@Api(tags = "土地承包服务接口")
public class BdctdcbjyqNydqtsyqRestController implements BdcTdcbjyqNydqtsyqRestService {

    @Autowired
    BdcTdcbjyqNydqtsyqService tdcbService;

    /**
     * 根据项目id查土地承包经营权信息
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 16:53 2020/8/25
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "xmid查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "xmid", dataType = "String", paramType = "path")})
    public BdcTdcbnydsyqDO queryByxmid(@PathVariable(name = "xmid") String xmid) {
        return tdcbService.queryByxmid(xmid);
    }

    /**
     * 根据gzlslid查询地块信息
     *
     * @param gzlslid
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 10:23 2020/8/25
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据gzlslid查询地块信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "gzlslid", dataType = "String", paramType = "path")})
    public List<BdcDkxxDTO> queryDkxxByGzlslid(@PathVariable(name = "gzlslid") String gzlslid) {
        return tdcbService.queryDkxxBygzlslid(gzlslid);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcDkxxQO 查询实体
     * @return {List} 地块信息列表
     * @description 土地承包经营权查询项目或证书关联的地块列表
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "土地承包经营权查询项目或证书关联的地块列表")
    @ApiImplicitParam(name = "bdcDkxxQO", value = "bdcDkxxQO", dataType = "BdcDkxxQO", paramType = "body")
    public List<BdcDkxxDTO> queryTdcbjyqDkxx(@RequestBody BdcDkxxQO bdcDkxxQO) {
        return tdcbService.queryTdcbjyqDkxx(bdcDkxxQO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcDkxxQO 查询实体
     * @return {List} 地块信息列表
     * @description 未生成证书情况下查找目标证书 土地承包经营权地块列表
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "土地承包经营权查询项目或证书关联的地块列表")
    @ApiImplicitParam(name = "bdcDkxxQO", value = "bdcDkxxQO", dataType = "BdcDkxxQO", paramType = "body")
    public List<BdcDkxxDTO> queryTdcbjyqDkxxBeforeZsInit(@RequestBody BdcDkxxQO bdcDkxxQO) {
        return tdcbService.queryTdcbjyqDkxxBeforeZsInit(bdcDkxxQO);
    }
}
