package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.service.TuxknrService;
import cn.gtmap.realestate.common.core.dto.building.TuxknrResponseDTO;
import cn.gtmap.realestate.common.core.service.rest.building.TuxknrZdtRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-24
 * @description 南通 宗地图特殊需求
 */
@RestController
@Api(tags = "南通宗地图特殊需求")
public class TuxknrZdtRestController implements TuxknrZdtRestService {


    @Autowired
    private TuxknrService tuxknrService;

    /**
     * @param djh
     * @return java.util.List
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH查询 tuxknr 表中的宗地图数据
     */
    @Override
    @ApiOperation(value = "根据DJH查询 tuxknr 表中的宗地图数据")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TuxknrResponseDTO> listTuxknrByDjh(@RequestParam(value = "djh", required = false)String djh) {
        return tuxknrService.listTuxknrByDjh(djh);
    }
}
