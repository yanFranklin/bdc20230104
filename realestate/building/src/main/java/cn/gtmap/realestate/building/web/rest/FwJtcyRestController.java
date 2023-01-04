package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.FwJtcyService;
import cn.gtmap.realestate.common.core.domain.building.FwJtcyDO;
import cn.gtmap.realestate.common.core.service.rest.building.FwJtcyRestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/11/30/16:58
 * @Description:
 */
@RestController
@Api(tags = "房屋家庭成员服务接口")
public class FwJtcyRestController implements FwJtcyRestService {
    @Autowired
    FwJtcyService fwJtcyService;
    @Override
    public List<FwJtcyDO> listFwJtcy(@RequestParam(name = "qlrIndex") String qlrIndex) {
        return fwJtcyService.listFwJtcy(qlrIndex);
    }
}
