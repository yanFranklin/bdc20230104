package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlJyxxCezsService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxCezsDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlJyxxCezsRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/5/13
 * @description 不动产受理交易差额征收信息rest服务
 */
@RestController
@Api(tags = "不动产受理交易差额征收信息服务")
public class BdcSlJyxxCezsRestController  extends BaseController implements BdcSlJyxxCezsRestService {

    @Autowired
    BdcSlJyxxCezsService bdcSlJyxxCezsService;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "保存不动产受理交易差额征收信息", notes = "保存不动产受理交易差额征收信息服务")
    @ApiImplicitParam(name = "bdcSlJyxxDO", value = "不动产受理交易差额征收信息", required = true, dataType = "BdcSlJyxxCezsDO")
    public BdcSlJyxxCezsDO saveBdcSlJyxxCezs(@RequestBody BdcSlJyxxCezsDO bdcSlJyxxCezsDO) {
        if(Objects.isNull(bdcSlJyxxCezsDO)){
            throw new MissingArgumentException("缺少不动产受理差额征收信息。");
        }
        return bdcSlJyxxCezsService.saveBdcSlJyxx(bdcSlJyxxCezsDO);
    }


}
