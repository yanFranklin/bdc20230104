package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcYztService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcVYztGyGdxmDkDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcYztRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/12/18
 * @description 一张图rest服务
 */
@RestController
@Api(tags = "不动产受理收费信息服务")
public class BdcYztRestController extends BaseController implements BdcYztRestService {


    @Autowired
    private BdcYztService bdcYztService;

    /**
     * @param pageable
     * @param bdcGdspxxQOJson
     * @return Page<BdcVYztGyGdxmDkDO>
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 分页查询 供地审批信息
     */
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "分页查询供地审批信息", notes = "分页查询供地审批信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "bdcGdspxxQOJson", value = "查询参数JSON", required = true, paramType = "query")})
    @Override
    public Page<BdcVYztGyGdxmDkDO> listBdcGdspxxByPage(Pageable pageable, String bdcGdspxxQOJson) {
        return bdcYztService.listBdcGdspxxByPage(pageable,bdcGdspxxQOJson);
    }
}
