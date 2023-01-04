package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlCsjpzService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlCsjpzDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlCsjPzRestService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: realestate
 * @description: 受理长三角业务数据配置
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-05-10 13:57
 **/
@RestController
@Api("长三角业务配置接口服务")
public class BdcSlCsjPzRestController extends BaseController implements BdcSlCsjPzRestService {
    @Autowired
    BdcSlCsjpzService bdcSlCsjpzService;

    /**
     * @param bdcSlCsjpzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询长三角配置服务
     * @date : 2022/5/10 13:59
     */
    @ApiOperation(value = "根据查询参数返回长三角配置信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlCsjpzDO", value = "长三角配置封装查询对象", dataType = "BdcSlCsjpzDO", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcSlCsjpzDO> listCsjpz(@RequestBody BdcSlCsjpzDO bdcSlCsjpzDO) {
        return bdcSlCsjpzService.listCsjpz(bdcSlCsjpzDO);
    }

    /**
     * @param pzid@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除配置
     * @date : 2022/5/10 14:03
     */
    @Override
    public int deleteCsjpz(@PathVariable(value = "pzid") String pzid) {
        return bdcSlCsjpzService.deletCsjpz(pzid);
    }

    /**
     * @param bdcSlCsjpzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存配置
     * @date : 2022/5/10 14:03
     */
    @Override
    public BdcSlCsjpzDO saveCsjpz(@RequestBody BdcSlCsjpzDO bdcSlCsjpzDO) {
        return bdcSlCsjpzService.saveCsjpz(bdcSlCsjpzDO);
    }
}
