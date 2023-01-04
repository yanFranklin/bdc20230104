package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.service.TdlpbService;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.service.rest.building.TdlpbRestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/08/04
 * @Description:
 */
@RestController
@Api(tags = "土地楼盘表服务接口")
public class TdlpbRestController extends BaseController implements TdlpbRestService {
    @Autowired
    TdlpbService tdlpbService;

    @Override
    public List<Map<String,Object>> listTdlpbxx(@RequestParam(value = "djh", required = false) String djh) {
        return tdlpbService.getTdlpbxx(djh);
    }
}
