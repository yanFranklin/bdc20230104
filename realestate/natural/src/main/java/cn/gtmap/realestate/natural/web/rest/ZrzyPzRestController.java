package cn.gtmap.realestate.natural.web.rest;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtLcpzDO;
import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyPzRestService;
import cn.gtmap.realestate.natural.core.service.pzxx.ZrzyXtLcpzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/26
 * @description 自然资源配置REST服务
 */
@RestController
@Api(tags = "自然资源配置接口")
public class ZrzyPzRestController implements ZrzyPzRestService {

    @Autowired
    private ZrzyXtLcpzService zrzyXtLcpzService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流定义ID查询流程配置", notes = "根据工作流定义ID查询流程配置服务")
    @ApiImplicitParam(name = "gzldyid", value = "工作流定义ID", required = true, dataType = "String", paramType = "path")
    public ZrzyXtLcpzDO queryZrzyXtLcpz(@PathVariable(value = "gzldyid") String gzldyid){
        return zrzyXtLcpzService.queryZrzyXtLcpz(gzldyid);

    }
}
