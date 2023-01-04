package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.LpbGjService;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.dto.building.LpbGJRequestDTO;
import cn.gtmap.realestate.common.core.service.rest.building.LpbGjRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/16
 * @description 楼盘表构建服务
 */
@RestController
@Api(tags = "楼盘表构建服务")
public class LpbGjRestController extends BaseController implements LpbGjRestService {
    @Autowired
    private LpbGjService lpbGjService;

    /**
     * @param lpbGJRequestDTO
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 楼盘表构建服务
     */
    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "楼盘表构建")
    public void lpbGj(@RequestBody LpbGJRequestDTO lpbGJRequestDTO) {
        lpbGjService.lpbGj(lpbGJRequestDTO);
    }
}