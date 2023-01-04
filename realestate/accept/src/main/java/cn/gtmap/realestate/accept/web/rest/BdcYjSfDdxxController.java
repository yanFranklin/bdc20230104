package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcYjSfDdxxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcYjSfDdxxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcYjSfDdxxRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/11/11
 * @description 不动产银行月结单号与受理编号关系服务
 */
@RestController
@Api(tags = "不动产权利类型rest服务")
public class BdcYjSfDdxxController extends BaseController implements BdcYjSfDdxxRestService {

    @Autowired
    BdcYjSfDdxxService bdcYjSfDdxxService;

    // 合一支付缴费途径，默认值设置
    @Value("${sfxx.hyzfjftj:}")
    private Integer hyzfjftj;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "作废月结订单信息内容", notes = "作废月结订单信息内容")
    @ApiImplicitParams({@ApiImplicitParam(name = "String", value = "月结单号集合", required = true, dataType = "List<String>", paramType = "body")})
    public void zfYjddxx(@RequestBody List<String> yjdhList) {
        this.bdcYjSfDdxxService.zfYjSfDdxxByYjdh(yjdhList);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新月结订单状态", notes = "更新月结订单状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYjSfDdxxDO", value = "月结收费订单信息", required = true, dataType = "BdcYjSfDdxxDO", paramType = "body")})
    public void modifyYjDdzt(@RequestBody BdcYjSfDdxxDO bdcYjSfDdxxDO) {
        if(StringUtils.isBlank(bdcYjSfDdxxDO.getYjdh())){
            throw new MissingArgumentException("缺失参数月结单号");
        }
        bdcYjSfDdxxDO.setZtxgsj(new Date());
        this.bdcYjSfDdxxService.updateYjSfDdxxByYjdh(bdcYjSfDdxxDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据月结单号查询月结收费订单信息", notes = "根据月结单号查询月结收费订单信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "yjdh", value = "月结单号", required = true, dataType = "String", paramType = "path")})
    public BdcYjSfDdxxDO queryBdcYjSfDdxxByYjdh(@PathVariable(name="yjdh") String yjdh) {
        return this.bdcYjSfDdxxService.queryBdcYjSfDdxxByYjdh(yjdh);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取合一支付缴费途径默认配置", notes = "获取合一支付缴费途径默认配置")
    public String getHyzfjftjConfig() {
        if(Objects.nonNull(hyzfjftj)){
            return String.valueOf(hyzfjftj);
        }
        return "";
    }
}
