package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcGrzfCxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcGrzfQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcGrzfFeignService;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcGrzfRestService;
import cn.gtmap.realestate.inquiry.service.BdcGrzfService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/10/5 14:45
 * @description 个人住房查询
 */
@RestController
@Api(tags = "个人住房查询")
public class BdcGrzfRestController implements BdcGrzfRestService {

    @Autowired
    BdcGrzfService grzfService;

    /**
     * 个人住房信息查询
     *
     * @param pageable
     * @param grzfJson 权利人名称
     * @return BdcGrzfCxDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询个人住房信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grzfJson", value = "参数JSON", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<BdcGrzfCxDTO> listGrzfByPage(Pageable pageable, String grzfJson) {
        BdcGrzfQO grzfQO = new BdcGrzfQO();
        if (StringUtils.isNotBlank(grzfJson)) {
            grzfQO = JSONObject.parseObject(grzfJson, grzfQO.getClass());
        }
        return grzfService.listGrzfByPage(pageable, grzfQO);
    }
}
