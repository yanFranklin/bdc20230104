package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcGxjkPdfDTO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcGxjkRestService;
import cn.gtmap.realestate.inquiry.service.BdcGxJkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2021/2/4
 * @description
 */
@RestController
@Api(tags = "省级共享接口服务")
public class BdcGxjkRestController implements BdcGxjkRestService {

    @Autowired
    private BdcGxJkService bdcGxJkService;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存共享接口数据到REDIS")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataList", value = "共享接口数据", required = false, paramType = "query")})
    public String saveGxjkDataToRedis(@RequestBody List<Map> dataList){
        return bdcGxJkService.saveGxjkDataToRedis(dataList);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取PDF打印数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcGxjkPdfDTO", value = "共享接口PDF实体", required = false, paramType = "query")})
    public String getPrintXmlOfGxjk(@RequestBody BdcGxjkPdfDTO bdcGxjkPdfDTO){
        return bdcGxJkService.getPrintXmlOfGxjk(bdcGxjkPdfDTO);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存验证结果文件到大云并关联到当前流程作为附件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcGxjkPdfDTO", value = "共享接口PDF实体", required = false, paramType = "query")})
    public String saveGxjkPdfFile(@RequestBody BdcGxjkPdfDTO bdcGxjkPdfDTO){
        return bdcGxJkService.saveGxjkPdfFile(bdcGxjkPdfDTO);
    }
}
