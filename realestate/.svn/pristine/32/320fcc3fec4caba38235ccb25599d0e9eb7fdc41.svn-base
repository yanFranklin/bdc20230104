package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcZhjgDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZhjgQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcZhjgRestService;
import cn.gtmap.realestate.inquiry.service.BdcZhjgService;
import com.alibaba.fastjson.JSONObject;
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

import java.util.List;


/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-07-10
 * @description 综合监管
 */
@RestController
public class BdcZhjgRestController implements BdcZhjgRestService {

    @Autowired
    private BdcZhjgService bdcZhjgService;

    /**
     * @param pageable
     * @param bdcZhjgQOJson
     * @return org.springframework.data.domain.Page
     * @author <a href="mailto:cucheng@gtmap.cn">chenyucheng</a>
     * @description 综合监管
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询批量查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "bdcPlcxQOJson", value = "查询信息参数JSON", required = false, paramType = "query")})
    public Page<BdcZhjgDTO> listBdcZhjgByPage(Pageable pageable, String bdcZhjgQOJson) {
        BdcZhjgQO bdcZhjgQO = new BdcZhjgQO();
        if (StringUtils.isNotBlank(bdcZhjgQOJson)) {
            bdcZhjgQO = JSONObject.parseObject(bdcZhjgQOJson, BdcZhjgQO.class);
        }
        Page<BdcZhjgDTO> page = bdcZhjgService.listBdcZhjgByPage(pageable, bdcZhjgQO);
        return page;
    }

    /**
     * @param bdcZhjgQOJson
     * @return org.springframework.data.domain.Page
     * @author <a href="mailto:cucheng@gtmap.cn">chenyucheng</a>
     * @description 综合监管
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询批量查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcPlcxQOJson", value = "查询信息参数JSON", required = false, paramType =
            "query")})
    public List listBdcZhjg(String bdcZhjgQOJson) {
        BdcZhjgQO bdcZhjgQO = new BdcZhjgQO();
        if (StringUtils.isNotBlank(bdcZhjgQOJson)) {
            bdcZhjgQO = JSONObject.parseObject(bdcZhjgQOJson, BdcZhjgQO.class);
        }
        return bdcZhjgService.listBdcZhjg(bdcZhjgQO);
    }

}
