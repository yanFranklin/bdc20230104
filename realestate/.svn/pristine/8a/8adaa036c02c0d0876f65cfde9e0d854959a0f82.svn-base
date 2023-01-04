package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.service.DjxxService;
import cn.gtmap.realestate.common.core.domain.building.ZdQsdcDO;
import cn.gtmap.realestate.common.core.service.rest.building.ZdQsdcRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-19
 * @description 宗地权属调查
 */
@RestController
@Api(tags = "宗地权属调查")
public class ZdQsdcRestController implements ZdQsdcRestService{

    @Autowired
    private DjxxService djxxService;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.ZdQsdcDO
     * @description 根据bdcdyh查询 宗地权属调查服务
     */
    @Override
    @ApiOperation(value = "分页查询宗地信息")
    @ResponseStatus(code = HttpStatus.OK)
    public ZdQsdcDO queryZdQsdcByBdcdyh(@PathVariable("bdcdyh")String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return djxxService.queryZdQsdcByBdcdyh(bdcdyh);
    }
}
