package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.core.dto.accept.BdcNtFjParamDTO;
import cn.gtmap.realestate.common.core.service.rest.etl.RudongFcjyDataRestService;
import cn.gtmap.realestate.etl.service.FcjyDataService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/4/2
 * @description 如东交易库数据获取
 */
@RestController
@Api(tags = "如东交易库数据读取")
public class RudongFcjyDataRestController implements RudongFcjyDataRestService {

    @Autowired
    private FcjyDataService fcjyDataService;

    @ApiOperation(value = "查询存量房备案信息分页")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcHtxxQoStr", value = "参数JSON结构", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public Page<Map> bdcClfHtxxByPageJson(Pageable pageable,
                                      @RequestParam(name = "bdcHtxxQoStr",required = false) String bdcHtxxQoStr){
        Map<String,String> paramMap =new HashMap<>();
        if(StringUtils.isNotBlank(bdcHtxxQoStr)){
            paramMap=JSONObject.parseObject(bdcHtxxQoStr,HashMap.class);
        }
        return fcjyDataService.bdcClfHtxxByPageJson(pageable,paramMap);


    }

    @ApiOperation(value = "查询商品房备案信息分页")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcHtxxQoStr", value = "参数JSON结构", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public Page<Map> bdcSpfHtxxByPageJson(Pageable pageable,
                                          @RequestParam(name = "bdcHtxxQoStr",required = false) String bdcHtxxQoStr){
        Map<String,String> paramMap =new HashMap<>();
        if(StringUtils.isNotBlank(bdcHtxxQoStr)){
            paramMap=JSONObject.parseObject(bdcHtxxQoStr,HashMap.class);
        }
        return fcjyDataService.bdcSpfHtxxByPageJson(pageable,paramMap);


    }

    @ApiOperation(value = "上传房产交易附件到流程")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwfclx", value = "房屋房产类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "htbh", value = "合同编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bdcNtFjParamDTO", value = "权利人证件号信息", dataType = "bdcNtFjParamDTO", paramType = "body")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public void uploadFcjyfj(@RequestParam(name = "fwfclx")String fwfclx,@RequestParam(name = "gzlslid")String gzlslid,@RequestParam(name = "htbh")String htbh,@RequestBody BdcNtFjParamDTO bdcNtFjParamDTO){
        fcjyDataService.uploadFcjyfj(fwfclx, gzlslid, htbh,bdcNtFjParamDTO);

    }
}
