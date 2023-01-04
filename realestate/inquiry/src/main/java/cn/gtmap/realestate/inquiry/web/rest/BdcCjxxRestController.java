package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcCjxxDO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcCjxxRestService;
import cn.gtmap.realestate.inquiry.service.BdcCjxxService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022/08/11
 * @description 不动产持件信息DO
 */
@RestController
public class BdcCjxxRestController implements BdcCjxxRestService {

    @Autowired
    private BdcCjxxService bdcCjxxService;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "保存持件信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcCjxxDO", value = "不动产持件信息DO", required = false, paramType = "body")})
    public BdcCjxxDO saveBdcCjxx(@RequestBody BdcCjxxDO bdcCjxxDO) {
        return this.bdcCjxxService.saveBdcCjxx(bdcCjxxDO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "删除持件信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "持件信息ID集合", required = false, paramType = "body")
    })
    public void plDeleteBdcCjxx(@RequestBody List<String> ids) {
        this.bdcCjxxService.plDeleteBdcCjxx(ids);
    }
}
