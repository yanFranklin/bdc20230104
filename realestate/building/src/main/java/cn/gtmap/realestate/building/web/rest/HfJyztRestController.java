package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.service.HfJyztService;
import cn.gtmap.realestate.common.core.dto.building.HfJyztResponseDTO;
import cn.gtmap.realestate.common.core.service.rest.building.HfJyztRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-23
 * @description 合肥交易状态相关服务
 */
@RestController
@Api(tags = "合肥交易状态相关服务")
public class HfJyztRestController implements HfJyztRestService {

    @Autowired
    private HfJyztService hfJyztService;

    /**
     * @param ysfwbmList
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.HfJyztResponseDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据YSFWBM 查询 交易状态
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据YSFWBM查询交易状态")
    public List<HfJyztResponseDTO> queryJyztByYsfwbm(@RequestBody List<String> ysfwbmList,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return hfJyztService.queryJyztByYsfwbm(ysfwbmList);
    }
}
