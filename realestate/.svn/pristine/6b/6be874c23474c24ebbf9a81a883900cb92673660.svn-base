package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.gtc.starter.gcas.config.GTAutoConfiguration;
import cn.gtmap.realestate.accept.core.service.BdcYjdhSfxxGxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcYjdhSfxxGxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcYjSfxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.accept.BdcYjSfxxQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcYjdhSfxxGxRestService;
import cn.gtmap.realestate.common.util.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/11/11
 * @description 不动产银行月结单号与受理编号关系服务
 */
@RestController
@Api(tags = "不动产银行月结单号与受理编号关系Rest服务")
public class BdcYjdhSfxxGxController extends BaseController implements BdcYjdhSfxxGxRestService {

    @Autowired
    BdcYjdhSfxxGxService bdcYjdhSfxxGxService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据月结收费信息QO查询月结收费信息", notes = "根据月结收费信息QO查询月结收费信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYjSfxxQO", value = "不动产月结收费信息QO", required = true, dataType = "BdcYjSfxxQO", paramType = "body")})
    public List<BdcYjSfxxDTO> listYhyjSfxx(@RequestBody BdcYjSfxxQO bdcYjSfxxQO) {
        return bdcYjdhSfxxGxService.listBdcYjSfxx(bdcYjSfxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "生成月结单号收费信息数据", notes = "生成月结单号收费信息数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYjdhSfxxGxDO", value = "银行月结单号与收费信息关系DO", required = true, dataType = "List<BdcYjdhSfxxGxDO>", paramType = "body")})
    public List<BdcYjdhSfxxGxDO> generateYjdhSfxxGx(@RequestBody List<BdcYjdhSfxxGxDO> bdcYjdhSfxxGxDO) {
        return bdcYjdhSfxxGxService.generateYjSfxxGx(bdcYjdhSfxxGxDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "校验收费信息是否已经下单", notes = "校验收费信息是否已经下单")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYjdhSfxxGxDO", value = "银行月结单号与收费信息关系DO", required = true, dataType = "List<BdcYjdhSfxxGxDO>", paramType = "body")})
    public Map<String, Set<String>> checkSfxxSfxd(@RequestBody  List<BdcYjdhSfxxGxDO> bdcYjdhSfxxGxDOList) {
        return bdcYjdhSfxxGxService.checkSfxxSfxd(bdcYjdhSfxxGxDOList);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据月结单号查询月结收费信息数据", notes = "根据月结单号查询月结收费信息数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "yjdh", value = "月结单号", required = true, dataType = "String", paramType = "param")})
    public List<BdcYjdhSfxxGxDO> queryBdcYjdhSfxxGxByYjdh(@RequestParam(value="yjdh") String yjdh) {

        return bdcYjdhSfxxGxService.queryBdcYjdhSfxxGxByYjdh(yjdh);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据收费信息ID获取月结单号信息", notes = "根据收费信息ID获取月结单号信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "sfxxid", value = "收费信息ID", required = true, dataType = "String", paramType = "path")})
    public List<String> queryYjdhxxBySfxxId(@PathVariable(value = "sfxxid") String sfxxid) {
        return bdcYjdhSfxxGxService.queryYjdhxxBySfxxid(sfxxid);
    }

    /**
     * 互联网调用的接口，和查询月结收费信息相同的逻辑，需要验证参数是否必填
     *
     * @param bdcYjSfxxQO 不动产月结收费信息DO
     * @return BdcYjSfxxDTO 不动产月结收费信息DTO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据月结收费信息QO查询月结收费信息", notes = "根据月结收费信息QO查询月结收费信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcYjSfxxQO", value = "不动产月结收费信息QO", required = true, dataType = "BdcYjSfxxQO", paramType = "body"),
            @ApiImplicitParam(name = "pageSzie", value = "分页参数：页数", required = true, dataType = "int", paramType = "param"),
            @ApiImplicitParam(name = "pageNumber", value = "分页参数：一页数量", required = true, dataType = "int", paramType = "param"),
    })
    public Page<BdcYjSfxxDTO> listYhyjSfxxForHlw(@RequestBody BdcYjSfxxQO bdcYjSfxxQO, @RequestParam(value = "pageSize") int pageSzie,
                                                 @RequestParam(value = "pageNumber")int pageNumber) {
        this.checkYjSfxxBtx(bdcYjSfxxQO);
        List<BdcYjSfxxDTO> bdcYjSfxxDTOList = this.bdcYjdhSfxxGxService.listBdcYjSfxx(bdcYjSfxxQO);
        return PageUtils.listToPage(bdcYjSfxxDTOList, new PageRequest(pageNumber, pageSzie));
    }

    private void checkYjSfxxBtx(BdcYjSfxxQO bdcYjSfxxQO){
        //验证必填项
        if (StringUtils.isBlank(bdcYjSfxxQO.getYhmc())) {
            throw new AppException("银行名称不可为空");
        }
        if (StringUtils.isBlank(bdcYjSfxxQO.getKssj())) {
            throw new AppException("登簿起始时间不可为空");
        }
        if (StringUtils.isBlank(bdcYjSfxxQO.getJssj())) {
            throw new AppException("登簿结束时间不可为空");
        }
        if (Objects.isNull(bdcYjSfxxQO.getSfzt())) {
            throw new AppException("缴费状态不可为空");
        }
        if (StringUtils.isBlank(bdcYjSfxxQO.getQxdm())) {
            throw new AppException("区县代码不可为空");
        }
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据查询参数，生成月结单号", notes = "根据查询参数，生成月结单号")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYjSfxxQO", value = "不动产月结收费信息QO", required = true, dataType = "BdcYjSfxxQO", paramType = "body")})
    public BdcYjSfxxDTO queryYjdh(@RequestBody BdcYjSfxxQO bdcYjSfxxQO) {
        //this.checkYjSfxxBtx(bdcYjSfxxQO);
        return bdcYjdhSfxxGxService.queryYjdh(bdcYjSfxxQO);
    }

}
