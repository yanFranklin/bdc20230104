package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.FwKService;
import cn.gtmap.realestate.common.core.domain.building.FwKDO;
import cn.gtmap.realestate.common.core.service.rest.building.FwKRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-28
 * @description 图层服务
 */
@RestController
@Api(tags = "图层服务接口")
public class FwKRestController implements FwKRestService{

    @Autowired
    private FwKService fwKService;

    /**
     * @param lszd
     * @param zrzh
     * @return cn.gtmap.realestate.common.core.domain.building.FwKDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据LSZD 和 ZRZH 查询图层表
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据LSZD和ZRZH查询图层表")
    @ApiImplicitParams({@ApiImplicitParam(name = "lszd", value = "隶属宗地", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "zrzh", value = "自然幢号", required = true, dataType = "String", paramType = "query")})
    public FwKDO queryFwKByLszdAndZrzh(String lszd, String zrzh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwKService.queryFwKByLszdAndZrzh(lszd, zrzh, qjgldm);
    }
}
