package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcJwcxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcJwcxQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcJwcxRestService;
import cn.gtmap.realestate.inquiry.service.BdcJwcxService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/11/4
 * @description
 */
@RestController
@Api(tags = "纪委查询查询")
public class BdcJwcxRestController implements BdcJwcxRestService {
    @Autowired
    BdcJwcxService bdcJwcxService;

    /**
     * 分页纪委查询
     *
     * @param pageable
     * @param bdcJwcxQOJson
     * @return Page
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "纪委查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "bdcJwcxQOJson", value = "查询信息参数JSON", required = false, paramType = "query")})
    public Page<BdcJwcxDTO> listBdcJwcxByPage(Pageable pageable, String bdcJwcxQOJson) {
        BdcJwcxQO bdcJwcxQO = new BdcJwcxQO();
        if (StringUtils.isNotBlank(bdcJwcxQOJson)) {
            bdcJwcxQO = JSONObject.parseObject(bdcJwcxQOJson, bdcJwcxQO.getClass());
        }
        return bdcJwcxService.listBdcJwcxByPage(pageable, bdcJwcxQO);
    }
}
