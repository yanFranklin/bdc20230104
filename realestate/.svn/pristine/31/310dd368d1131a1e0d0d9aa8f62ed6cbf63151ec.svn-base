package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.realestate.certificate.service.BdcGdsjPzService;
import cn.gtmap.realestate.certificate.web.BaseController;
import cn.gtmap.realestate.common.core.service.rest.certificate.BdcGdsjPzRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/20
 * @description 归档配置xml接口
 */
@RestController
@Api(tags = "不动产归档配置服务接口")
public class BdcGdsjPzRestController extends BaseController implements BdcGdsjPzRestService {

    @Autowired
    BdcGdsjPzService bdcGdsjPzService;

    /**
     * @param gzlslid
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @ApiIgnore
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例id查询归档配置信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例id", dataType = "string", paramType = "path")})
    @Override
    public String bdcXxGd(@PathVariable("gzlslid") String gzlslid, @RequestParam(value = "xmid",required = false)String xmid) {
        return bdcGdsjPzService.gdBdcXx(gzlslid,xmid);
    }
}
