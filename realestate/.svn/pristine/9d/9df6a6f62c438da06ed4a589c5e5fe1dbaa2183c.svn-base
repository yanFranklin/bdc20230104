package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcYhcxDyaDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcYhcxRestService;
import cn.gtmap.realestate.inquiry.service.BdcYhcxService;
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
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/11/28
 * @description 银行查询
 */
@RestController
@Api(tags = "银行查询")
public class BdcYhcxRestController implements BdcYhcxRestService {
    @Autowired
    private BdcYhcxService bdcYhcxService;

    @Override
    public List<BdcXmDO> listBdcXmByZsid(String zsid) {
        return bdcYhcxService.listBdcXmByZsid(zsid);
    }

    @Override
    public List<BdcYhcxDyaDTO> listBdcDyaqByBdcdyh(@RequestParam("bdcdyhList") List<String> bdcdyhList) {
        return bdcYhcxService.listBdcDyaByBdcdyh(bdcdyhList);
    }

    @Override
    public List<BdcCfDO> listBdcCfByBdcdyh(@RequestParam("bdcdyhList") List<String> bdcdyhList) {
        return bdcYhcxService.listBdcCfByBdcdyh(bdcdyhList);
    }

    @Override
    public List<BdcYyDO> listBdcYyByBdcdyh(@RequestParam("bdcdyhList") List<String> bdcdyhList) {
        return bdcYhcxService.listBdcYyByBdcdyh(bdcdyhList);
    }

    @Override
    public List<String> listBdcSdxxByBdcdyh(@RequestParam("bdcdyhList") List<String> bdcdyhList) {
        return bdcYhcxService.listBdcSdxxByBdcdyh(bdcdyhList);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据封装的证书查询对象查询证书信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZsQO", value = "证书查询对象", dataType = "BdcZsQO", paramType = "body")})
    @Override
    public List<BdcZsDO> listBdcZs(@RequestBody BdcZsQO bdcZsQO) {
        return bdcYhcxService.listBdcZs(bdcZsQO);
    }
}
