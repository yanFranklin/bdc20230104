package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.service.DcxxService;
import cn.gtmap.realestate.common.core.dto.building.DcxxDTO;
import cn.gtmap.realestate.common.core.service.rest.building.FwDcxxRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-27
 * @description 房屋户室调查信息服务
 */
@RestController
@Api(tags = "房屋户室调查信息服务")
public class FwDcxxRestController implements FwDcxxRestService {

    @Autowired
    private DcxxService dcxxService;

    /**
     * @param bdcdyfwlx
     * @param fwIndex
     * @return cn.gtmap.realestate.common.core.dto.building.DcxxDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FW_HS_INDEX 查询房屋户室调查信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据FW_HS_INDEX查询房屋户室调查信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwHsIndex", value = "房屋户室主键", required = true, dataType = "String", paramType = "path")})
    public DcxxDTO queryFwDcxx(@PathVariable("bdcdyfwlx") String bdcdyfwlx,
                                    @PathVariable("fwIndex") String fwIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return dcxxService.queryFwDcxx(bdcdyfwlx,fwIndex);
    }

    /**
     * @param dcxxDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存调查信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "保存房屋户室调查信息")
    public Integer updateFwDcxx(@RequestBody DcxxDTO dcxxDTO) {
        return dcxxService.updateFwDcxx(dcxxDTO);
    }

    /**
     * @param bdcdyfwlx
     * @param fwIndex
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 删除房屋户室调查信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "删除房屋户室调查信息")
    public void deleteFwDcxx(@PathVariable("bdcdyfwlx") String bdcdyfwlx,
                                          @PathVariable("fwIndex") String fwIndex,
                             @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        dcxxService.deleteFwDcxx(bdcdyfwlx,fwIndex);
    }
}
