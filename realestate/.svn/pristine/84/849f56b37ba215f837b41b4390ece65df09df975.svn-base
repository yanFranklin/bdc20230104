package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlFwtcxxService;
import cn.gtmap.realestate.accept.service.BdcSlFwCxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwtcxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcFwxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.CompareFwtcxxResultDTO;
import cn.gtmap.realestate.common.core.qo.accept.CompareFwtcxxQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlFwcxRestService;
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
 * @version 1.0, 2019/7/12
 * @description 房屋查询rest服务
 */
@RestController
@Api(tags = "房屋查询rest服务")
public class BdcSlFwcxRestController extends BaseController implements BdcSlFwcxRestService {
    @Autowired
    BdcSlFwCxService bdcSlFwCxService;
    @Autowired
    BdcSlFwtcxxService bdcSlFwtcxxService;

    /**
     * @param xmid 项目ID
     * @return 房屋信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID查询房屋信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据项目ID查询房屋信息", notes = "根据项目ID查询房屋信息服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    })
    public BdcFwxxDTO listFwxxByXmid(@PathVariable(value = "xmid") String xmid){
        return bdcSlFwCxService.listFwxxByXmid(xmid);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据xmid和申请人类别获取房屋套次信息", notes = "根据xmid和申请人类别获取房屋套次信息服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sqrlb", value = "申请人类别", required = true, dataType = "String", paramType = "query")
    })
    public List<BdcSlFwtcxxDO> listBdcSlFwtcxxByXmid(@PathVariable(value = "xmid") String xmid, @RequestParam("sqrlb") String sqrlb){
        return bdcSlFwtcxxService.listBdcSlFwtcxxByXmid(xmid, sqrlb);

    }

    /**
     * @param xmid  项目ID
     * @param sqrlb 申请人类别
     * @param isYz  是否验证比较房查套次与申报套次
     * @return 住房信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID和申请人住房信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据项目ID和申请人住房信息", notes = "根据项目ID和申请人住房信息服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    })
    public List<BdcSlFwtcxxDO> listBdcZfxxDTO(@PathVariable(value = "xmid") String xmid, @RequestParam("sqrlb") String sqrlb, @RequestParam("isYz") Boolean isYz){
        return bdcSlFwCxService.listBdcZfxxDTO(xmid,sqrlb,isYz);

    }

    /**
     * @param xmid  项目ID
     * @param sqrlb 申请人类别
     * @param isYz  是否验证比较房查套次与申报套次
     * @return 住房信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID和申请人住房信息(南通版本)
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据项目ID和申请人住房信息", notes = "根据项目ID和申请人住房信息服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    })
    public List<BdcSlFwtcxxDO> listBdcZfxxDTONT(@PathVariable(value = "xmid") String xmid, @RequestParam("sqrlb")
            String sqrlb, @RequestParam("isYz") Boolean isYz){
        return bdcSlFwCxService.listBdcZfxxDTONT(xmid,sqrlb,isYz);

    }

    /**
     * 房屋套次信息对比
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param compareFwtcxxQO
     * @return
     */
    @Override
    public CompareFwtcxxResultDTO compareFwtcxx(@RequestBody CompareFwtcxxQO compareFwtcxxQO) {
        return bdcSlFwCxService.compareFwtcxx(compareFwtcxxQO);
    }
    /**
     * @param compareFwtcxxQO 套次比对信息
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 导入套次比对信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "导入套次比对信息", notes = "导入套次比对信息服务")
    public void drFwtcxx(@RequestBody CompareFwtcxxQO compareFwtcxxQO){
        bdcSlFwCxService.drFwtcxx(compareFwtcxxQO);

    }
}

