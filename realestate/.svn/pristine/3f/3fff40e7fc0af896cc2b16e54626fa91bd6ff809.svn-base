package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.service.rest.init.BdcCzrzRestService;
import cn.gtmap.realestate.init.core.service.BdcCzrzService;
import cn.gtmap.realestate.init.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/3/26
 * @description
 */
@RestController
@Api(tags = "业务操作日志服务")
public class BdcCzrzRestController extends BaseController implements BdcCzrzRestService {

    @Autowired
    private BdcCzrzService bdcCzrzService;

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 生成操作日志
     */
    @Override
    @ApiOperation(value = "生成操作日志")
    @ApiImplicitParams({ @ApiImplicitParam(name = "gzlslid",value = "工作流实例ID", paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "reason",value = "操作原因", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "czlx",value = "操作类型", paramType = "query", dataType = "String")})
    public void initBdcCzrz(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "reason",required = false) String reason, @RequestParam(value = "czlx") Integer czlx, @RequestParam(value = "currentUserName") String currentUserName){
        bdcCzrzService.initBdcCzrz(gzlslid, reason, czlx,currentUserName);

    }

    @Override
    @ApiOperation(value = "根据审批系统业务号获取操作日志")
    @ApiImplicitParams({ @ApiImplicitParam(name = "spxtywh",value = "工作流实例ID", paramType = "path", dataType = "审批系统业务号"),
            @ApiImplicitParam(name = "orderBy",value = "排序方式", paramType = "query", dataType = "String")})
    public List<BdcCzrzDO> listBdcCzrzBySpxtywh(@PathVariable(name = "spxtywh") String spxtywh, @RequestParam(value = "orderBy",required = false) String orderBy){
        return bdcCzrzService.listBdcCzrzBySpxtywh(spxtywh, orderBy);

    }

    @Override
    @ApiOperation(value = "添加不动产操作日志内容")
    @ApiImplicitParams({ @ApiImplicitParam(name = "bdcCzrzDO",value = "操作日志信息", paramType = "body", dataType = "BdcCzrzDO")})
    public void addBdcCzrz(@RequestBody BdcCzrzDO bdcCzrzDO) {
        this.bdcCzrzService.addBdcCzrz(bdcCzrzDO);
    }

    @Override
    @ApiOperation(value = "根据操作日志ID，修改不动产操作日志内容")
    @ApiImplicitParams({ @ApiImplicitParam(name = "bdcCzrzDO",value = "操作日志信息", paramType = "body", dataType = "BdcCzrzDO")})
    public void modifyBdcCzrzByRzid(@RequestBody BdcCzrzDO bdcCzrzDO) {
        this.bdcCzrzService.modifyBdcCzrzByRzid(bdcCzrzDO);
    }

    @Override
    @ApiOperation(value = "查询不动产操作日志内容")
    @ApiImplicitParams({ @ApiImplicitParam(name = "bdcCzrzDO",value = "操作日志信息", paramType = "body", dataType = "BdcCzrzDO")})
    public List<BdcCzrzDO> listBdcCzrz(@RequestBody BdcCzrzDO bdcCzrzDO) {
        return this.bdcCzrzService.queryBdcCzrz(bdcCzrzDO);
    }

    @Override
    @ApiOperation(value = "添加删除操作日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid",value = "工作流实例ID", paramType = "param", dataType = "string"),
            @ApiImplicitParam(name = "opinion",value = "审批意见", paramType = "param", dataType = "string"),
    })
    public void addScCzrzWithOpinion(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name="opinion") String opinion) {
        this.bdcCzrzService.addScCzrzWithOpinion(gzlslid, opinion);
    }

    @Override
    @ApiOperation(value = "添加删除操作日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid",value = "工作流实例ID", paramType = "param", dataType = "string"),
            @ApiImplicitParam(name = "opinion",value = "审批意见", paramType = "param", dataType = "string"),
            @ApiImplicitParam(name = "bdcXmDO",value = "项目信息", paramType = "param", dataType = "cn.gtmap.realestate.common.core.domain.BdcXmDO")
    })
    public void addScCzrzWithOpinionWithXmxx(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name="opinion") String opinion, @RequestBody BdcXmDO bdcXmDO) {
        this.bdcCzrzService.addScCzrzWithOpinionWithXmxx(gzlslid, opinion,bdcXmDO);
    }
}
