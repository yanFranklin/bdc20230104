package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcZhdpCxRestService;
import cn.gtmap.realestate.inquiry.service.BdcZhdpCxService;
import cn.gtmap.realestate.inquiry.web.main.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/10
 * @description 综合大屏-所有中心办理业务的集合展示
 */
@RestController
@Api(tags = "综合大屏-所有中心办理业务的集合展示")
public class BdcZhdpCxRestController extends BaseController implements BdcZhdpCxRestService{
    @Autowired
    private BdcZhdpCxService bdcZhdpCxService;

    /**
     * @return List 当前所有中心办理业务
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 综合大屏所有中心办理业务查询
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("综合大屏所有中心办理业务查询")
    public List<Map> listBdcZhdp() {
        return bdcZhdpCxService.listBdcZhdp();
    }

}
