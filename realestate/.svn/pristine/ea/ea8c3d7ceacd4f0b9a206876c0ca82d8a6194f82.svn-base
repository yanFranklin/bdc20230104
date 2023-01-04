package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.service.BdcYczfService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcYczfRestService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcYczfVO;
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
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/10/11
 * @description 不动产一次支付服务
 */
@RestController
@Api(tags = "不动产一次支付服务")
public class BdcYczfRestController extends BaseController implements BdcYczfRestService {

    @Autowired
    BdcYczfService bdcYczfService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询不动产一次支付收费收税信息", notes = "查询不动产一次支付收费收税信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, dataType = "String", paramType = "param"),
            @ApiImplicitParam(name = "lx", value = "类型", required = false, dataType = "String", paramType = "param"),
    })
    public Object sczfdd(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "lx") String lx,
                         @RequestParam(value = "qlrlb") String qlrlb) {
        return bdcYczfService.sczfdd(gzlslid, lx, qlrlb);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "一次支付后台分账，查询缴费结果接口", notes = "一次支付后台分账，查询缴费结果接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, dataType = "String", paramType = "param"),
            @ApiImplicitParam(name = "qlrlb", value = "权利人类别", required = true, dataType = "String", paramType = "param"),
    })
    public CommonResponse cxjfjg(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb) {
        return bdcYczfService.cxjfjg(gzlslid, qlrlb);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询不动产一次支付收费收税信息", notes = "查询不动产一次支付收费收税信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, dataType = "String", paramType = "param"),
            @ApiImplicitParam(name = "qlrlb", value = "权利人类别", required = false, dataType = "String", paramType = "param"),
    })
    public BdcYczfVO queryBdcYczfSfssxx(@RequestParam(value = "gzlslid") String gzlslid,
                                              @RequestParam(value = "qlrlb", required = false) String qlrlb) {
        return bdcYczfService.queryBdcYczfSfssxx(gzlslid, qlrlb);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "POS缴费成功通知政融支付", notes = "POS缴费成功通知政融支付")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, dataType = "String", paramType = "param"),
            @ApiImplicitParam(name = "qlrlb", value = "权利人类别", required = true, dataType = "String", paramType = "param"),
    })
    public CommonResponse posZfcgtz(@RequestParam(value = "gzlslid") String gzlslid,
                            @RequestParam(value = "qlrlb") String qlrlb) {
        return bdcYczfService.posZfcgtz(gzlslid, qlrlb);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "线上退款申请", notes = "线上退款申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, dataType = "String", paramType = "param"),
            @ApiImplicitParam(name = "qlrlb", value = "权利人类别", required = true, dataType = "String", paramType = "param"),
    })
    public CommonResponse xstksq(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb) {
        return bdcYczfService.xstksq(gzlslid, qlrlb);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "线上退款结果查询", notes = "线上退款结果查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, dataType = "String", paramType = "param"),
            @ApiImplicitParam(name = "qlrlb", value = "权利人类别", required = true, dataType = "String", paramType = "param"),
    })
    public CommonResponse xstkjgcx(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb) {
        return bdcYczfService.xstkjgcx(gzlslid, qlrlb);
    }
}
