package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.service.BdcSfxxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcLcTsjjsDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcQuerySfztDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSfjfblResponseDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcTsdjfxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcYhkkDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSfxxCzQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJfblQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSfxxRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/5/11
 * @description 不动产受理发票信息RestController服务
 */
@RestController
@Api(tags = "不动产收费信息服务接口")
public class BdcSfxxRestController extends BaseController implements BdcSfxxRestService {

    @Autowired
    BdcSfxxService bdcSfxxService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "废除二维码信息", notes = "废除二维码信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSfxxCzQO", value = "不动产常州收费信息QO", required = true, dataType = "BdcSfxxCzQO", paramType = "body")
    })
    public void abolishEwm(@RequestBody BdcSfxxCzQO bdcSfxxCzQO) {
        this.bdcSfxxService.abolishEwm(bdcSfxxCzQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "抵押批量缴费流程，废除二维码信息", notes = "抵押批量缴费流程，废除二维码信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSfxxCzQO", value = "不动产常州收费信息QO", required = true, dataType = "BdcSfxxCzQO", paramType = "body")
    })
    public void abolishEwmDyapl(@RequestBody BdcSfxxCzQO bdcSfxxCzQO) {
        this.bdcSfxxService.abolishEwmDyapl(bdcSfxxCzQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询推送缴费结果", notes = "查询推送缴费结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSfxxCzQO", value = "不动产常州收费信息QO", required = true, dataType = "BdcSfxxCzQO", paramType = "body")
    })
    public BdcTsdjfxxResponseDTO cxtsdjfxx(@RequestBody BdcSfxxCzQO bdcSfxxCzQO) {
        return this.bdcSfxxService.cxtsdjfxx(bdcSfxxCzQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询财政收费状态", notes = "查询财政收费状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSfxxCzQO", value = "不动产常州收费信息QO", required = true, dataType = "BdcSfxxCzQO", paramType = "body")
    })
    public BdcQuerySfztDTO querySfzt(@RequestBody BdcSfxxCzQO bdcSfxxCzQO) {
        return this.bdcSfxxService.querySfzt(bdcSfxxCzQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "抵押批量查询财政收费状态", notes = "抵押批量查询财政收费状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSfxxCzQO", value = "不动产常州收费信息QO", required = true, dataType = "BdcSfxxCzQO", paramType = "body")
    })
    public BdcQuerySfztDTO dyaplQuerySfztByTsid(@RequestBody BdcSfxxCzQO bdcSfxxCzQO) {
        return this.bdcSfxxService.dyaplQuerySfztByTsid(bdcSfxxCzQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询财政收费状态", notes = "查询财政收费状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sfxxid", value = "收费信息ID", required = true, dataType = "String", paramType = "path")
    })
    public void syncSfxxSfzt(@PathVariable(value = "sfxxid") String sfxxid, @RequestParam(value = "jksj", required = false) String jksj) {
        this.bdcSfxxService.syncSfxxSfzt(sfxxid, jksj);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询财政收费状态", notes = "查询财政收费状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSfxxCzQO", value = "不动产常州收费信息QO", required = true, dataType = "BdcSfxxCzQO", paramType = "body")
    })
    public Map tksq(@RequestBody BdcSfxxCzQO bdcSfxxCzQO) {
        return this.bdcSfxxService.tksq(bdcSfxxCzQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "修改收费项目的收费状态，同步更新收费信息的收费状态", notes = "修改收费项目的收费状态，同步更新收费信息的收费状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSfxxCzQO", value = "不动产常州收费信息QO", required = true, dataType = "BdcSfxxCzQO", paramType = "body")
    })
    public void modifySfxmSfzt(@RequestBody BdcSfxxCzQO bdcSfxxCzQO) {
        this.bdcSfxxService.modifySfxmSfzt(bdcSfxxCzQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取登记收费二维码 和 土地收益金二维码", notes = "获取登记收费二维码 和 土地收益金二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sfxxid", value = "收费信息ID", required = true, dataType = "String", paramType = "path")
    })
    public Map<String, String> getSfEwmAndTdsyjEwm(@PathVariable(value = "sfxxid") String sfxxid) {
        return this.bdcSfxxService.getSfEwmAndTdsyjEwm(sfxxid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "领取发票号", notes = "领取发票号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSfxxCzQO", value = "不动产常州收费信息QO", required = true, dataType = "BdcSfxxCzQO", paramType = "body")
    })
    public String lqfph(@RequestBody BdcSfxxCzQO bdcSfxxCzQO) {
        return this.bdcSfxxService.lqfph(bdcSfxxCzQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "作废发票号", notes = "作废发票号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSfxxCzQO", value = "不动产常州收费信息QO", required = true, dataType = "BdcSfxxCzQO", paramType = "body")
    })
    public void zffph(@RequestBody BdcSfxxCzQO bdcSfxxCzQO) {
       this.bdcSfxxService.zffph(bdcSfxxCzQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "取消发票号", notes = "取消发票号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSfxxCzQO", value = "不动产常州收费信息QO", required = true, dataType = "BdcSfxxCzQO", paramType = "body")
    })
    public void qxfph(@RequestBody BdcSfxxCzQO bdcSfxxCzQO) {
        this.bdcSfxxService.qxfph(bdcSfxxCzQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更改收费信息ID", notes = "更改收费信息ID")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSfxxCzQOList", value = "不动产常州收费信息QO集合", required = true, dataType = "BdcSfxxCzQO", paramType = "body")
    })
    public void rechangeSfxxid(@RequestBody List<BdcSfxxCzQO> bdcSfxxCzQOList) {
        this.bdcSfxxService.rechangeSfxxid(bdcSfxxCzQOList);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更改缴款方式，更新缴款状态、缴费时间", notes = "更改缴款方式，更新缴款状态、缴费时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSfxxCzQO", value = "不动产常州收费信息QO", required = true, dataType = "BdcSfxxCzQO", paramType = "body")
    })
    public void changeJkfsModifySfzt(@RequestBody BdcSfxxCzQO bdcSfxxCzQO) {
        this.bdcSfxxService.changeJkfsModifySfzt(bdcSfxxCzQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更改缴费状态", notes = "更改缴费状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSfxxCzQO", value = "不动产常州收费信息QO", required = true, dataType = "BdcSfxxCzQO", paramType = "body")
    })
    public void modifySfzt(@RequestBody BdcSfxxCzQO bdcSfxxCzQO) {
        this.bdcSfxxService.modifySfzt(bdcSfxxCzQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "批量更新收费信息收费状态 和 收费项目缴费状态", notes = "批量更新收费信息收费状态 和 收费项目缴费状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSfxxCzQO", value = "不动产常州收费信息QO", required = true, dataType = "BdcSfxxCzQO", paramType = "body")
    })
    public void plModifySfzt(@RequestBody BdcSfxxCzQO bdcSfxxCzQO) {
        this.bdcSfxxService.plModifySfzt(bdcSfxxCzQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据收费信息ID查询收费项目信息, 并根据土地收益金进行过滤", notes = "根据收费信息ID查询收费项目信息, 并根据土地收益金进行过滤")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sfxxid", value = "收费信息ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sftdsyj", value = "是否土地收益金", required = true, dataType = "boolean", paramType = "param")
    })
    public List<BdcSlSfxmDO> queryBdcSlSfxmBySfxxidWithFilter(@PathVariable(value = "sfxxid") String sfxxid,
                                                              @RequestParam(value = "sftdsyj") boolean sftdsyj) {
        return this.bdcSfxxService.queryBdcSlSfxmBySfxxidWithFilter(sfxxid, sftdsyj);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询产权收费推送信息", notes = "查询产权收费推送信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlSfxxQO", value = "收费信息查询QO", required = true, dataType = "bdcSlSfxxQO", paramType = "body")
    })
    public BdcLcTsjjsDTO listCqTssfDTO(@RequestBody BdcSlSfxxQO bdcSlSfxxQO) {
        return this.bdcSfxxService.listCqTssfDTO(bdcSlSfxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询抵押权收费推送信息", notes = "查询抵押权收费推送信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlSfxxQO", value = "收费信息查询QO", required = true, dataType = "bdcSlSfxxQO", paramType = "body")
    })
    public BdcLcTsjjsDTO listDyaqTssfDTO(@RequestBody BdcSlSfxxQO bdcSlSfxxQO) {
        return this.bdcSfxxService.listDyaqTssfDTO(bdcSlSfxxQO);
    }

    /**
     * 同步未缴费信息
     *
     * @param jftbsj 日期
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "同步未缴费信息", notes = "同步未缴费信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "jftbsj", value = "日期", required = true, dataType = "date", paramType = "query"),
    })
    public void wjftb(@RequestParam(value = "jftbsj", required = true) String jftbsj) {
        bdcSfxxService.wjftb(jftbsj);
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取当前流程的合一支付二维码并在页面展现
     * @date : 2022/5/17 16:43
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取当前流程的合一支付二维码并在页面展现", notes = "获取当前流程的合一支付二维码并在页面展现")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, dataType = "String", paramType = "path")})
    public Map queryHyzfEwm(@PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "xmid", required = false) String xmid) {
        return bdcSfxxService.querHyzfEwm(gzlslid, xmid);
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取当前流程的合一支付后的缴费状态
     * @date : 2022/5/17 16:43
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取当前流程的合一支付后的缴费状态", notes = "获取当前流程的合一支付后的缴费状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, dataType = "String", paramType = "path")})
    public void queryHyzfJfzt(@PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "xmid", required = false) String xmid) {
        bdcSfxxService.queryHyzfJfzt(gzlslid, xmid);
    }

    /**
     * @param bdcSlJfblQO
     * @param bdcSlJfblQO
     * @return
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 缴费办理
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "缴费办理", notes = "缴费办理")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlJfblQO", value = "缴费办理请求QO", dataType = "bdcSlJfblQO", paramType = "body")})
    public BdcSfjfblResponseDTO jfbl(@RequestBody BdcSlJfblQO bdcSlJfblQO) {
        return bdcSfxxService.jfbl(bdcSlJfblQO);
    }

    /**
     * @param bdcSlJfblQO
     * @param sfyj
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 查询缴费情况
     * @date : 2022/5/26 18:43
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询缴费情况", notes = "查询缴费情况")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlJfblQO", value = "缴费办理请求QO", dataType = "bdcSlJfblQO", paramType = "body"),
            @ApiImplicitParam(name = "sfyj", value = "是否月结", dataType = "String", paramType = "param")})
    public void cxjfqk(@RequestBody BdcSlJfblQO bdcSlJfblQO, @RequestParam(value = "sfyj") boolean sfyj) {
        bdcSfxxService.cxjfqk(bdcSlJfblQO, sfyj);
    }

    /**
     * @param bdcSlJfblQO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 非税开票
     * @date : 2022/5/27 9:43
     */
    @Override
    public Map<String, String> fskp(@RequestBody BdcSlJfblQO bdcSlJfblQO) {
        return bdcSfxxService.fskp(bdcSlJfblQO);
    }

    /**
     * @param bdcSlJfblQO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 作废缴款通知书
     * @date : 2022/5/26 18:43
     */
    @Override
    public Map<String, String> zfjktzs(@RequestBody BdcSlJfblQO bdcSlJfblQO) {
        return bdcSfxxService.zfjktzs(bdcSlJfblQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询小微企业收费信息", notes = "查询小微企业收费信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlSfxxQO", value = "收费信息查询QO", required = true, dataType = "bdcSlSfxxQO", paramType = "body")
    })
    public BdcLcTsjjsDTO listXwqySfxx(@RequestBody BdcSlSfxxQO bdcSlSfxxQO) {
        return bdcSfxxService.listXwqySfxx(bdcSlSfxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更改收费信息减免原因为小微企业、是否收登记费为否", notes = "更改收费信息减免原因为小微企业、是否收登记费为否")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlSfxxQO", value = "收费信息查询QO", required = true, dataType = "bdcSlSfxxQO", paramType = "body")
    })
    public void modifySfxxJmyyAndSfsdjf(@RequestBody BdcSfxxCzQO bdcSfxxCzQO) {
        this.bdcSfxxService.modifySfxxJmyyAndSfsdjf(bdcSfxxCzQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "挂接收费信息", notes = "挂接收费信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "slbh", value = "受理编号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sfxxid", value = "收费信息id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrlb", value = "权利人类别", required = false, dataType = "String", paramType = "query")
    })
    public void gjSfxx(@RequestParam(name = "slbh") String slbh, @RequestParam(name = "sfxxid") String sfxxid, @RequestParam(name = "qlrlb", required = false) String qlrlb) {
        bdcSfxxService.gjSfxx(slbh, sfxxid, qlrlb);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据htbh获取获取税款信息", notes = "根据htbh获取获取税款信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "htbh", value = "合同编号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query"),
    })
    public List<BdcYhkkDTO> getYhkkxx(@RequestParam(value = "htbh") String htbh,
                                      @RequestParam(value = "gzlslid") String gzlslid) {
        return bdcSfxxService.getYhkkxx(htbh, gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取税票信息", notes = "获取税票信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrlb", value = "权利人类别", required = true, dataType = "String", paramType = "query"),
    })
    public CommonResponse hqsp(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb) {
        return bdcSfxxService.hqsp(gzlslid, qlrlb);
    }
}
