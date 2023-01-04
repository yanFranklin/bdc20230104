package cn.gtmap.realestate.portal.ui.web.rest;

import cn.gtmap.realestate.common.core.service.feign.init.BdcXtBbFeignService;
import cn.gtmap.realestate.portal.ui.web.main.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 不动产系统版本服务接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/5/21 17:19
 */
@RestController
@RequestMapping("/rest/v1.0/xtbb")
@Api(tags = "不动产版本服务接口")
public class BdcXtBbController extends BaseController {
    @Autowired
    private BdcXtBbFeignService bdcXtBbFeignService;

    @GetMapping("/bbgk")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取更新日志列表")
    public Object getGxrzList() {
        return bdcXtBbFeignService.getGxrzList();
    }

}
