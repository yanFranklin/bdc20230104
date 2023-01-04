package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.CalculatedAreaService;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.dto.building.FttdmjRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.LjzJzmjRequestDTO;
import cn.gtmap.realestate.common.core.service.rest.building.CalculatedAreaRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/10
 * @description 面积计算
 */
@RestController
@Api(tags = "面积计算服务接口")
public class CalculatedAreaRestController extends BaseController implements CalculatedAreaRestService {

    @Autowired
    private CalculatedAreaService calculatedAreaService;
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param ljzJzmjRequestDTO
     * @return void
     * @description 计算逻辑幢建筑面积
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "计算逻辑幢建筑面积")
    public void calculatedLjzJzmj(@RequestBody LjzJzmjRequestDTO ljzJzmjRequestDTO) {
        calculatedAreaService.calculatedLjzJzmj(ljzJzmjRequestDTO);
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param ljzJzmjRequestDTO
     * @return void
     * @description 计算逻辑幢建筑面积(通过配置)
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "通过配置计算逻辑幢建筑面积")
    public void calculatedLjzJzmjByConfig(@RequestBody LjzJzmjRequestDTO ljzJzmjRequestDTO) {
        calculatedAreaService.calculatedLjzJzmjByConfig(ljzJzmjRequestDTO);
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fttdmjRequestDTO
     * @return void
     * @description 计算分摊土地面积
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "计算分摊土地面积")
    public Integer calculatedFttdmj(@RequestBody FttdmjRequestDTO fttdmjRequestDTO) {
        return calculatedAreaService.calculatedFttdmj(fttdmjRequestDTO);
    }


    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fttdmjRequestDTO
     * @return void
     * @description 计算分摊土地面积
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "通过配置计算分摊土地面积")
    public void calculatedFttdmjByConfig(@RequestBody FttdmjRequestDTO fttdmjRequestDTO) {
        calculatedAreaService.calculatedFttdmjByConfig(fttdmjRequestDTO);
    }
    /**
     * @param fttdmjRequestDTO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据dto查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByDTO(@RequestBody FttdmjRequestDTO fttdmjRequestDTO) {
        return calculatedAreaService.listValidBdcdyhByDTO(fttdmjRequestDTO);
    }
    /**
     * @param ljzJzmjRequestDTO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据dto查询有效的不动产单元号
     */
    @Override
    public List<String> listValidBdcdyhByDTO(@RequestBody LjzJzmjRequestDTO ljzJzmjRequestDTO) {
        return calculatedAreaService.listValidBdcdyhByDTO(ljzJzmjRequestDTO);
    }
}
