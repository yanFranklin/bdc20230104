package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlSfssDdxxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfssDdxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcDsfSfssDdxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfssDdxxDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfssDdxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcYjSfxxQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlSfssDdxxRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/5/28
 * @description 不动产收费收税订单信息rest服务
 */
@RestController
@Api(tags = "不动产收费收税订单信息rest服务")
public class BdcSlSfssDdxxRestController extends BaseController implements BdcSlSfssDdxxRestService {
    @Autowired
    BdcSlSfssDdxxService bdcSlSfssDdxxService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据查询参数查询不动产收费收税订单信息", notes = "根据查询参数查询不动产收费收税订单信息")
    @ApiImplicitParam(name = "bdcSlSfssDdxxQO", value = "不动产收费收税订单信息查询QO对象", required = true, dataType = "BdcSlSfssDdxxQO", paramType = "query")
    public List<BdcSlSfssDdxxDO> listBdcSlSfssDdxx(@RequestBody BdcSlSfssDdxxQO bdcSlSfssDdxxQO){
        return bdcSlSfssDdxxService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID查询不动产收费收税订单信息", notes = "根据工作流实例ID查询不动产收费收税订单信息")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "BdcSlSfssDdxxQO", paramType = "query")
    public List<BdcSlSfssDdxxDO> listBdcSlSfssDdxxByGzlslid(@PathVariable("gzlslid") String gzlslid){
        BdcSlSfssDdxxQO bdcSlSfssDdxxQO =new BdcSlSfssDdxxQO();
        bdcSlSfssDdxxQO.setGzlslid(gzlslid);
        return bdcSlSfssDdxxService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);

    }

    /**
     * @param bdcSlSfssDdxxDO 收税订单信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新收税订单信息
     * @date : 2020/5/28 17:41
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新收税订单信息", notes = "更新收税订单信息服务")
    @ApiImplicitParam(name = "bdcSlSfssDdxxDO", value = "不动产收税订单信息对象", required = false, dataType = "bdcSlSfssDdxxDO", paramType = "query")
    public Integer updateBdcSlSfssDdxxDO(@RequestBody BdcSlSfssDdxxDO bdcSlSfssDdxxDO) {
        return bdcSlSfssDdxxService.updateBdcSlSfssDdxx(bdcSlSfssDdxxDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "保存并创建收费收税订单信息", notes = "保存并创建收费收税订单信息")
    @ApiImplicitParam(name = "bdcSlSfssDdxxDTOList", value = "不动产收费收税订单信息DTO对象集合", required = true, dataType = "BdcSlSfssDdxxDTO", paramType = "query")
    public List<BdcSlSfssDdxxDO> saveAndCreateDdxx(@RequestParam("jkfs") String jkfs, @RequestBody List<BdcSlSfssDdxxDTO> bdcSlSfssDdxxDTOList) {
        return bdcSlSfssDdxxService.saveAndCreateDdxx(jkfs, bdcSlSfssDdxxDTOList);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "计算权利人与义务人税费总金额", notes = "计算权利人与义务人税费总金额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")
    })
    public Object computeQlrAndYwrSfje(@RequestParam(name="xmid") String xmid, @RequestParam(name="gzlslid") String gzlslid) {
        return bdcSlSfssDdxxService.computeQlrAndYwrSfje(xmid, gzlslid);
    }

    /**
     * @param bdcSlSfssDdxxQO 收费收税订单信息查询QO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据受理编号查询收费状态并且更新订单信息和收费收税信息
     * @date : 2020/5/29 8:44
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询收费状态并且更新订单信息和收费收税信息", notes = "查询收费状态并且更新订单信息和收费收税信息服务")
    @ApiImplicitParam(name = "bdcSlSfssDdxxQO", value = "收费收税订单信息查询QO", required = true, dataType = "BdcSlSfssDdxxQO", paramType = "body")
    public void querySfzt(@RequestBody BdcSlSfssDdxxQO bdcSlSfssDdxxQO) {
        bdcSlSfssDdxxService.queryAndUpdateSfzt(bdcSlSfssDdxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "接收第三方订单信息", notes = "接收第三方订单信息服务")
    @ApiImplicitParam(name = "bdcDsfSfssDdxxDTO", value = "第三方订单信息", required = true, dataType = "BdcDsfSfssDdxxDTO", paramType = "query")
    public void saveDsfDdxx(@RequestBody BdcDsfSfssDdxxDTO bdcDsfSfssDdxxDTO){
        bdcSlSfssDdxxService.saveDsfDdxx(bdcDsfSfssDdxxDTO);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据订单编号保存订单信息", notes = "根据订单编号保存订单信息服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "yjdh", value = "月结单号", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bdcDsfSfssDdxxDTO", value = "第三方订单信息", required = true, dataType = "BdcDsfSfssDdxxDTO", paramType = "body")
    })
    public void saveAndUpdateDdxxByDdbh(@RequestBody List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList,
                                        @RequestParam(name = "gzlslid",required = false) String gzlslid,
                                        @RequestParam(name="yjdh",required = false) String yjdh){
        bdcSlSfssDdxxService.saveAndUpdateDdxxByDdbh(bdcSlSfssDdxxDOList, gzlslid, yjdh);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "收费信息退款申请", notes = "收费信息退款申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlSfssDdxxQO", value = "收费收税订单信息查询QO对象", required = true, dataType = "BdcSlSfssDdxxQO", paramType = "body")
    })
    public List<BdcSlSfssDdxxDO> ddxxTksq(@RequestBody BdcSlSfssDdxxQO bdcSlSfssDdxxQO){
        return bdcSlSfssDdxxService.ddxxTksq(bdcSlSfssDdxxQO);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "收费信息退款状态查询", notes = "收费信息退款状态查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlSfssDdxxQO", value = "收费收税订单信息查询QO对象", required = true, dataType = "BdcSlSfssDdxxQO", paramType = "body")
    })
    public BdcSlSfxxDO querySfxxTkqk(@RequestBody BdcSlSfssDdxxQO bdcSlSfssDdxxQO){
        return bdcSlSfssDdxxService.queryTkqk(bdcSlSfssDdxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "合并缴费", notes = "合并缴费")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    public List<BdcSlSfssDdxxDO> hbjf(@RequestParam(name = "jkfs") String jkfs, @RequestParam(name = "gzlslid") String gzlslid){
        return bdcSlSfssDdxxService.hbjf(jkfs,gzlslid);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证订单信息是否已失效", notes = "验证订单信息是否已失效")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlSfssDdxxQO", value = "不动产收费收税订单信息查询QO对象", required = true, dataType = "BdcSlSfssDdxxQO", paramType = "query")})
    public boolean checkDdxxIsSx(@RequestBody BdcSlSfssDdxxQO bdcSlSfssDdxxQO){
        return bdcSlSfssDdxxService.checkDdxxIsSx(bdcSlSfssDdxxQO);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据月结单号创建订单", notes = "根据月结单号创建订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "yjdh", value = "月结单号", required = true, dataType = "String", paramType = "param"),
            @ApiImplicitParam(name = "hyzfjftj", value = "合一支付缴费途径", required = false, dataType = "String", paramType = "param"),
            @ApiImplicitParam(name = "jkqd", value = "缴款渠道", required = false, dataType = "String", paramType = "param"),
    })
    public List<BdcSlSfssDdxxDO> createYjSfOrder(@RequestParam(value="yjdh") String yjdh,
                                @RequestParam(value="hyzfjftj", required = false) String hyzfjftj,
                                @RequestParam(value="jkqd", required = false) String jkqd) {
        return this.bdcSlSfssDdxxService.createYjSfOrder(yjdh, hyzfjftj, jkqd);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "作废订单信息", notes = "作废订单信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlSfssDdxxQO", value = "不动产收费收税订单信息查询QO对象", required = true, dataType = "BdcSlSfssDdxxQO", paramType = "body")})
    public void zfDdxx(@RequestBody BdcSlSfssDdxxQO bdcSlSfssDdxxQO){
        bdcSlSfssDdxxService.zfDdxx(bdcSlSfssDdxxQO);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新月结订单缴费状态", notes = "更新月结订单缴费状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlSfssDdxxQO", value = "不动产收费收税订单信息查询QO对象", required = true, dataType = "BdcSlSfssDdxxQO", paramType = "body")})
    public void udpateYjDdxxSfztForHlw(@RequestBody BdcSlSfssDdxxQO bdcSlSfssDdxxQO) {
        bdcSlSfssDdxxService.udpateYjDdxxSfzt(bdcSlSfssDdxxQO);
    }
}
