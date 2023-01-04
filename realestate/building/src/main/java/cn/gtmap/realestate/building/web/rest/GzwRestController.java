package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.service.GzwService;
import cn.gtmap.realestate.common.core.dto.building.GzwDcbResponseDTO;
import cn.gtmap.realestate.common.core.service.rest.building.GzwRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-22
 * @description 构筑物查询服务
 */
@RestController
@Api(tags = "构筑物查询服务")
public class GzwRestController implements GzwRestService {

    @Autowired
    private GzwService gzwService;


    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.GzwDcbResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询构筑物基本信息（包含权利人）
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询构筑物基本信息（包含权利人）")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public GzwDcbResponseDTO queryGzwxxByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return gzwService.queryGzwxxByBdcdyh(bdcdyh);
    }
}
