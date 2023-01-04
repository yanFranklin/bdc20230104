package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.DjhZtService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.dto.building.DjhZtResponseDTO;
import cn.gtmap.realestate.common.core.service.rest.building.DjhZtRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019/3/6
 * @description 地籍号状态服务
 */
@RestController
@Api(tags = "地籍号状态服务")
public class DjhZtRestController extends BaseController implements DjhZtRestService {
    @Autowired
    private DjhZtService djhZtService;

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjhZtResponseDTO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询djh冻结信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据不动产单元号查询冻结信息")
    public DjhZtResponseDTO getDjhZtByBdcdyh(@RequestParam(name = "bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return djhZtService.getDjhZtByBdcdyh(bdcdyh, Constants.ZDZH_DJXX_ISJD_NOJD);
    }

    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.dto.building.DjhZtResponseDTO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据djh查询冻结信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据djh查询冻结信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "djh", value = "地籍号", required = true, dataType = "String", paramType = "path")})
    public DjhZtResponseDTO getDjhZtByDjh(@PathVariable("djh") String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return djhZtService.getDjhZtByDjh(djh, Constants.ZDZH_DJXX_ISJD_NOJD);
    }

    /**
     * @param djhList
     * @return List<DjhZtResponseDTO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 批量根据djh查询冻结信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量根据djh查询冻结信息")
    public List<DjhZtResponseDTO> listDjhZtByDjh(@RequestBody List<String> djhList,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return djhZtService.listDjhZtByDjh(djhList, Constants.ZDZH_DJXX_ISJD_NOJD);
    }
}
