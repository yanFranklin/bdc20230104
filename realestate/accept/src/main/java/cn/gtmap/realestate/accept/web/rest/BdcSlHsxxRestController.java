package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlHsxxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSwxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.swxx.SwHsztDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlHsxxQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlHsxxRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/27
 * @description 不动产受理核税信息rest服务
 */
@RestController
@Api(tags = "不动产受理核税信息服务")
public class BdcSlHsxxRestController extends BaseController implements BdcSlHsxxRestService {

    @Autowired
    BdcSlHsxxService bdcSlHsxxService;

    /**
     * @param bdcSlHsxxDO 不动产受理核税信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新不动产受理核税信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新不动产受理核税信息", notes = "更新不动产受理核税信息服务")
    @ApiImplicitParam(name = "bdcSlHsxxDO", value = "更新不动产受理核税信息", required = true, dataType = "BdcSlHsxxDO")
    public int updateBdcSlHsxx(@RequestBody BdcSlHsxxDO bdcSlHsxxDO){
        return bdcSlHsxxService.updateBdcSlHsxx(bdcSlHsxxDO);

    }

    /**
     * @param bdcSlHsxxQO 不动产受理核税信息
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 查询不动产受理核税信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询不动产受理核税信息", notes = "查询不动产受理核税信息")
    @ApiImplicitParam(name = "bdcSlHsxxQO", value = "不动产受理核税信息查询条件", required = true, dataType = "bdcSlHsxxQO")
    public List<BdcSlHsxxDO> listBdcSlHsxx(@RequestBody BdcSlHsxxQO bdcSlHsxxQO){
        return bdcSlHsxxService.listBdcSlHsxxByHsxxQo(bdcSlHsxxQO);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "修改不动产受理核税信息", notes = "修改不动产受理核税信息")
    @ApiImplicitParam(name = "bdcSlHsxxDO", value = "更新不动产受理核税信息", required = true, dataType = "BdcSlHsxxDO")
    public Integer updateBdcSlHsxxByXmidAndNsrsbh(@RequestBody BdcSlHsxxDO bdcSlHsxxDO){
        return bdcSlHsxxService.updateBdcSlHsxxByXmidAndNsrsbh(bdcSlHsxxDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据xmid和申请人类别查询税务信息", notes = "根据xmid和申请人类别查询税务信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sqrlb", value = "申请人类别", dataType = "String", paramType = "query")})
    public List<BdcSwxxDTO> queryBdcSwxxDTO(@PathVariable(value = "xmid")String xmid, @RequestParam(value = "sqrlb",required = false)String sqrlb){
        return bdcSlHsxxService.queryBdcSwxxDTO(xmid, sqrlb);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID更新完税状态", notes = "根据工作流实例ID更新完税状态服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "wszt", value = "完税状态", required = true, dataType = "String", paramType = "path")})
    public void updateWsztByGzlslid(@PathVariable(value = "wszt") Integer wszt, @PathVariable(value = "gzlslid") String gzlslid) {
        bdcSlHsxxService.updateBatchWszt(wszt, gzlslid);

    }

    /**
     * @param wszt
     * @param gzlslid
     * @param qlrlb
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据权利人类别不同分别更新完税状态
     * @date : 2022/9/21 16:31
     */
    @Override
    public void updateWsztByQlrlb(@PathVariable(value = "wszt") Integer wszt, @PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb) {
        bdcSlHsxxService.updateBatchWsztByQlrlb(wszt, gzlslid, qlrlb);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID更新核税信息", notes = "根据工作流实例ID更新核税信息")
    public void updateHsxxByGzlslid(@RequestBody BdcSlHsxxDO bdcSlHsxxDO, @RequestParam(value = "gzlslid") String gzlslid) {
        bdcSlHsxxService.batchUpdateBdcSlHsxx(bdcSlHsxxDO, gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据受理编号获取核税信息", notes = "根据受理编号获取核税信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "slbh", value = "受理编号", required = true, dataType = "String", paramType = "path")})
    public List<BdcSlHsxxDO> listBdcSlHsxxBySlbh(@PathVariable(value = "slbh") String slbh){
        return bdcSlHsxxService.listBdcSlHsxxBySlbh(slbh);

    }

    /**
     * @param bdcSlHsxxDO 不动产受理核税信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增受理核税信息数据
     * @date : 2020/5/19 14:15
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "新增受理核税信息数据", notes = "新增受理核税信息数据服务")
    public Integer insertBdcSlHsxxDO(@RequestBody BdcSlHsxxDO bdcSlHsxxDO) {
        return bdcSlHsxxService.insertBdcSlHsxx(bdcSlHsxxDO);
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新核税状态
     * @date : 2020/10/28 14:57
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新核税状态", notes = "更新核税状态数据服务")
    @ApiImplicitParam(name = "swHsztDTO", value = "更新不动产受理核税信息", required = true, dataType = "SwHsztDTO")
    public Integer updateHszt(@RequestBody SwHsztDTO swHsztDTO) {
        return bdcSlHsxxService.updateHszt(swHsztDTO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID和申请人类别查询不动产核税信息", notes = "根据工作流实例ID和申请人类别查询不动产核税信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sqrlb", value = "申请人类别", required = false, dataType = "String", paramType = "query"),
    })
    public List<BdcSlHsxxDO> listBdcSlHsxxByGzlslidAndSqrlb(@RequestParam(value="gzlslid") String gzlslid,
                                                            @RequestParam(value="sqrlb", required = false)String sqrlb) {
        return bdcSlHsxxService.listBdcSlHsxxByGzlslidAndSqrlb(gzlslid, sqrlb);
    }
}
