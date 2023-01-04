package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.service.BdcSlYzSqlService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlYzSqlRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @注释 受理验证sql服务
 * @作者 gln
 * @创建日期 2019/5/27
 * @创建时间 16:40
 * @版本号 V 1.0
 */
@RestController
@Api(tags = "不动产受理验证sql服务")
public class BdcSlYzSqlRestController extends BaseController implements BdcSlYzSqlRestService {
    @Autowired
    private BdcSlYzSqlService bdcSlYzSqlService;

    /**
     * @param checkMap 需要验证的sql和参数
     * @return
     * @author <a href ="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证sql
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("受理验证sql服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "checkMap", value = "需要验证的sql和参数", required = true, paramType = "body")})
    public boolean checkSql(@RequestBody Map<String, String> checkMap) {
        return bdcSlYzSqlService.checkSql(checkMap);
    }
}
