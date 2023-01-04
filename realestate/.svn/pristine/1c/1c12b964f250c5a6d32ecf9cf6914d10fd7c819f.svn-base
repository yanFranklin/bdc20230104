package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcGxbmcxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcGxbmcxQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcGxbmcxRestService;
import cn.gtmap.realestate.inquiry.service.BdcGxbmcxService;
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
@Api(tags = "共享部门查询")
public class BdcGxbmcxRestController implements BdcGxbmcxRestService {
    @Autowired
    BdcGxbmcxService bdcGxbmcxService;

    /**
     * 分页共享部门查询
     *
     * @param pageable
     * @param bdcGxbmcxQOJson
     * @return Page
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "共享部门查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "bdcGxbmcxQOJson", value = "查询信息参数JSON", required = false, paramType = "query")})
    public Page<BdcGxbmcxDTO> listBdcGxbmcxByPage(Pageable pageable, String bdcGxbmcxQOJson) {
        BdcGxbmcxQO bdcGxcxQO = new BdcGxbmcxQO();
        if (StringUtils.isNotBlank(bdcGxbmcxQOJson)) {
            bdcGxcxQO = JSONObject.parseObject(bdcGxbmcxQOJson, bdcGxcxQO.getClass());
        }
        return bdcGxbmcxService.listBdcGxbmcxByPage(pageable, bdcGxcxQO);
    }
}
