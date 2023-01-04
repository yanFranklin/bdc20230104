package cn.gtmap.realestate.natural.web.rest;

import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyGzyzRestService;
import cn.gtmap.realestate.natural.service.ZrzyGzyzService;
import cn.gtmap.realestate.natural.web.main.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/7/22
 * @description 不动产规则验证rest服务
 */
@RestController
@Api(tags = "不动产规则验证rest服务")
public class ZrzyGzyzRestController extends BaseController implements ZrzyGzyzRestService {
    @Autowired
    ZrzyGzyzService zrzyGzyzService;

    /**
     * @param bdcGzYzQO 规则验证查询实体(支持任意参数)
     * @return 验证结果（包含入参）
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 流程规则验证
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "流程规则验证", notes = "流程规则验证服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcGzYzQO", value = "验证查询参数", required = true, dataType = "BdcGzYzQO", paramType = "query")})
    public List<Map<String, Object>> yzBdcgz(@RequestBody(required = false) BdcGzYzQO bdcGzYzQO) {
        LOGGER.info("受理页面传入规则验证参数{}", bdcGzYzQO);
        List<Map<String, Object>> maps = zrzyGzyzService.yzBdcgz(bdcGzYzQO);
        LOGGER.info("受理页面传入规则验证参数{},验证结果：{}",bdcGzYzQO, maps);
        return maps;

    }

    /**
     * @param bdcGzYzQO 规则验证查询实体(支持任意参数)
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 除流程验证外其他验证, 如匹配验证, 页面按钮操作验证等
     * @date : 2020/7/10 14:13
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "流程规则验证", notes = "流程规则验证服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcGzYzQO", value = "验证查询参数", required = true, dataType = "BdcGzYzQO", paramType = "query")})
    public List<Map<String, Object>> qtyz(@RequestBody(required = false)BdcGzYzQO bdcGzYzQO) {
        return zrzyGzyzService.qtyz(bdcGzYzQO);
    }

}
