package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.realestate.certificate.core.service.BdcDzzzClzzService;
import cn.gtmap.realestate.common.core.domain.electronic.BdcDzzzClzzDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 存量电子证照管理接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 */
@Api(tags = "存量电子证照管理接口")
@RestController
@RequestMapping(value = "/realestate-e-certificate/feign/clzz")
public class BdcDzzzClzzRestController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BdcDzzzClzzService bdcDzzzClzzService;

    @PostMapping(value = "/zzxx")
    @ApiOperation(value = "更新存量电子证照信息", notes = "更新存量电子证照信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDzzzClzzDO", value = "当前项目信息", dataType = "BdcDzzzClzzDO", paramType = "body")})
    public int saveOrUpdateClzz(@RequestBody BdcDzzzClzzDO bdcDzzzClzzDO) {
        if (StringUtils.isBlank(bdcDzzzClzzDO.getBdcqzh())) {
            throw new MissingArgumentException("产权证号");
        }
        return bdcDzzzClzzService.saveOrUpdateClzz(bdcDzzzClzzDO);
    }
}
