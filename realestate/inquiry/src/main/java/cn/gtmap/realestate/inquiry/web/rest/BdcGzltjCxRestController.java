package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcSlqkDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.GzltjQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.SlqktjQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcGzlTjRestService;
import cn.gtmap.realestate.inquiry.service.BdcGzlTjService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/5/15
 * @description  查询子系统：工作量统计
 */
@RestController
@Api(tags = "不动产工作量统计服务接口")
public class BdcGzltjCxRestController implements BdcGzlTjRestService {
    @Autowired
    private BdcGzlTjService bdcGzlTjService;


    /**
     * @author <a href="mailto:chenyuccheng@gtmap.cn">chenyucheng</a>
     * @param gzltjParamJson 查询条件
     * @return {Page} 工作量部门办件量
     * @description 工作量统计部门办件量查询
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("工作量统计部门办件量查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "gzltjParamJson", value = "工作量统计参数JSON", required = false, paramType = "query")})
    public List<Map> listBdcGzltj(@RequestParam(name = "gzltjParamJson", required = false) String
            gzltjParamJson) {
        return bdcGzlTjService.listBdcGzltj(JSON.parseObject(gzltjParamJson, GzltjQO.class));
    }

    /**
     * @author <a href="mailto:chenyuccheng@gtmap.cn">chenyucheng</a>
     * @param gzltjParamJson 查询条件
     * @return {Page} 工作量部门办件量
     * @description 工作量统计部门办件量查询
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("受理情况统计查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "gzltjParamJson", value = "工作量统计参数JSON", required = false, paramType = "query")})
    public List<Map> listSlqkCount(@RequestParam(name = "gzltjParamJson", required = false) String
                                          gzltjParamJson) {
        return bdcGzlTjService.listSlqkCount(JSON.parseObject(gzltjParamJson, SlqktjQO.class));
    }




    /**
     * @author <a href="mailto:chenyuccheng@gtmap.cn">chenyucheng</a>
     * @param gzltjParamJson 查询条件
     * @return {Page} 工作量部门人员办件量
     * @description 工作量统计部门人员办件量查询
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("工作量统计人员办件量查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "gzltjParamJson", value = "工作量统计参数JSON", required = false, paramType = "query")})
    public List<Map> listBdcGzltjBmry(@RequestParam(name = "gzltjParamJson", required = false) String
                                          gzltjParamJson) {
        return bdcGzlTjService.listBdcGzltjBmry(JSON.parseObject(gzltjParamJson, GzltjQO.class));
    }


    /**
     * @author <a href="mailto:chenyuccheng@gtmap.cn">chenyucheng</a>
     * @param gzltjParamJson 查询条件
     * @return {Page} 工作量部门人员办件量
     * @description 工作量统计办件量明细查询
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("工作量统计办件量明细查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzltjParamJson", value = "工作量统计参数JSON", required = false, paramType = "query")})
    public List<Map> listGzltjMxBjl(@RequestParam(name = "gzltjParamJson", required = false) String
                                              gzltjParamJson) {
        return bdcGzlTjService.listGzltjMxBjl(JSON.parseObject(gzltjParamJson, GzltjQO.class));
    }

    /**
     * @author <a href="mailto:wutao2@gtmap.cn">chenyucheng</a>
     * @param gzltjParamJson 查询条件
     * @return {Page} 工作量部门人员办件量
     * @description 收件量明细查询
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("工作量统计收件量明细查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzltjParamJson", value = "工作量统计参数JSON", required = false, paramType = "query")})
    public List<Map> listGzltjMxSjl(@RequestParam(name = "gzltjParamJson", required = false) String
                                              gzltjParamJson) {
        return bdcGzlTjService.listGzltjMxSjl(JSON.parseObject(gzltjParamJson, GzltjQO.class));
    }

    /**
     * @author <a href="mailto:chenyuccheng@gtmap.cn">chenyucheng</a>
     * @param gzltjParamJson 查询条件
     * @return {Page} 工作量部门人员办件量
     * @description 工作量统计办件量明细查询
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("受理情况统计明细查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzltjParamJson", value = "工作量统计参数JSON", required = false, paramType = "query")})
    public Page<BdcSlqkDTO> listSlqkMx(@RequestParam(name = "gzltjParamJson", required = false) String
                                            gzltjParamJson, Pageable pageable) {
        return bdcGzlTjService.listSlqkMx(JSON.parseObject(gzltjParamJson, SlqktjQO.class),pageable);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("工作量统计办件量打印")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzltjParamJson", value = "工作量统计参数JSON", required = false, paramType = "query")})
    public List<Map> listGzltjMxBjlPrint(@RequestParam(name = "gzltjParamJson", required = false) String
                                            gzltjParamJson) {
        return bdcGzlTjService.listGzltjMxBjlPrint(JSON.parseObject(gzltjParamJson, GzltjQO.class));
    }

    /**
     * djyy多选框
     * @param djxl
     * @return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("工作量统计办件量打印")
    @ApiImplicitParams({@ApiImplicitParam(name = "djxl", value = "受理情况统计", required = false, paramType = "query")})
    public List<Map> djyyList (@RequestParam(name = "djxl", required = false) String djxl){
        return bdcGzlTjService.djyyList(djxl);
    }


}
