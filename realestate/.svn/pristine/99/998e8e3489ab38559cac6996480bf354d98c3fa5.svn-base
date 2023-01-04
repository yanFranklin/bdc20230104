package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcGzdjDO;
import cn.gtmap.realestate.common.core.qo.init.BdcGzdjQO;
import cn.gtmap.realestate.common.core.service.rest.init.BdcGzdjRestService;
import cn.gtmap.realestate.init.core.service.BdcGzdjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/9/13
 * @description 更正登记REST服务
 */
@RestController
@Api(tags = "更正登记服务接口")
public class BdcGzdjRestController implements BdcGzdjRestService {

    @Autowired
    private BdcGzdjService bdcGzdjService;

    @Override
    @ApiOperation(value = "根据xmid查询不动产更正信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcGzdjDO> listBdcGzdjByXmid(@PathVariable("xmid") String xmid){
        return bdcGzdjService.listBdcGzdjByXmid(xmid);

    }

    @Override
    @ApiOperation(value = "查询不动产更正信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcGzdjDO> listBdcGzdj(@RequestBody BdcGzdjQO bdcGzdjQO){
        return bdcGzdjService.listBdcGzdj(bdcGzdjQO);
    }
}
