package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.service.YbdcdyhService;
import cn.gtmap.realestate.common.core.dto.building.YbdcdyhResponseDTO;
import cn.gtmap.realestate.common.core.service.rest.building.YbdcdyhRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 1.0  2022-07-07
 * @description 原不动产单元号查询接口
 */
@RestController
@Api(tags = "原不动产单元号查询接口")
public class YbdcdyhRestController implements YbdcdyhRestService {

    @Autowired
    private YbdcdyhService ybdcdyhService;

    /**
     * @description 查询房屋原不动产单元号信息列表
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/7/7 10:47
     * @param fwBdcdyhList
     * @return List<YbdcdyhResponseDTO>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询房屋原不动产单元号信息列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwBdcdyhList", value = "房屋不动产单元号列表", required = true, dataType = "List", paramType = "body")})
    public List<YbdcdyhResponseDTO> queryFwYbdcdyhList(@RequestBody List<String> fwBdcdyhList) {
        return ybdcdyhService.queryFwYbdcdyhList(fwBdcdyhList);
    }

    /**
     * @description 查询土地原不动产单元号信息列表
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/7/7 10:47
     * @param tdBdcdyhList
     * @return List<YbdcdyhResponseDTO>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询房屋原不动产单元号信息列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "tdBdcdyhList", value = "土地不动产单元号列表", required = true, dataType = "List", paramType = "body")})
    public List<YbdcdyhResponseDTO> queryTdYbdcdyhList(@RequestBody List<String> tdBdcdyhList) {
        return ybdcdyhService.queryTdYbdcdyhList(tdBdcdyhList);
    }
}
