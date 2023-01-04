package cn.gtmap.realestate.accept.ui.web.rest;

import cn.gtmap.realestate.common.core.service.feign.accept.BdcZdydbymPzFeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/9/22
 * @description 分屏验证功能
 */
@RestController
@RequestMapping("/rest/v1.0/zdyympz")
@Api(tags = "分屏验证")
public class BdcZdydbymPzController {

    @Autowired
    BdcZdydbymPzFeignService bdcZdydbymPzFeignService;

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据对比代号查询配置
     */
    @GetMapping(value = "/{dbdh}")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "dbdh", value = "对比代号", dataType = "string", paramType = "query")})
    public Object queryBdcZdydbPzByDbdh(@PathVariable(value = "dbdh") String dbdh) {
        //获取权利人
        return bdcZdydbymPzFeignService.queryBdcZdydbPzByDbdh(dbdh);

    }

    /**
     * @param
     * @return 婚姻信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  婚姻信息验证
     */
    @GetMapping(value = "/hyxx/{xmid}")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "string", paramType = "query")})
    public Object hyxx(@PathVariable(value = "xmid") String xmid) {
        //获取权利人
        return bdcZdydbymPzFeignService.generateHyxx(xmid);

    }

    /**
     * @param
     * @return 企业信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  企业信息验证
     */
    @GetMapping(value = "/qyxx/{xmid}")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "string", paramType = "query")})
    public Object qyxx(@PathVariable(value = "xmid") String xmid) {
        //获取权利人
        return bdcZdydbymPzFeignService.generateQyxx(xmid);

    }

}
