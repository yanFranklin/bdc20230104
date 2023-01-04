package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.service.HfCustomService;
import cn.gtmap.realestate.common.core.service.rest.building.HfXzqRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-03-30
 * @description 合肥 行政区 读取服务
 */
@RestController
@Api(tags = "合肥行政区读取服务")
public class HfXzqRestController implements HfXzqRestService{

    @Autowired
    private HfCustomService hfCustomService;

    /**
     * @param bdcdyh
     * @param hslx
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.HfJyztResponseDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询行政区
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询行政区")
    public String queryXzqByBdcdyh(@RequestParam(name = "bdcdyh") String bdcdyh,
                                   @RequestParam(name = "hslx",required = false) String hslx,
                                   @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return hfCustomService.queryXzqByBdcdyh(bdcdyh,hslx);
    }

}
