package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcdyxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.nantong.dazt.DaztDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcdyxxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.DaxxQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcdyXxCxRestService;
import cn.gtmap.realestate.inquiry.service.BdcdyXxService;
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


/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0  2019-05-22
 * @description 不动产单元信息查询
 */
@RestController
public class BdcdyXxCxRestController implements BdcdyXxCxRestService {

    @Autowired
    private BdcdyXxService bdcdyXxService;

    /**
     * @param pageable
     * @param bdcdyxxQOJson
     * @return org.springframework.data.domain.Page
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 分页查询已登记不动产单元信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "不动产单元查询#BDCDYCX")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyxxQOJson", value = "参数JSON", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<BdcdyxxDTO> listBdcdyByPage(Pageable pageable, String bdcdyxxQOJson) {
        BdcdyxxQO bdcdyxxQO = new BdcdyxxQO();
        if (StringUtils.isNotBlank(bdcdyxxQOJson)) {
            bdcdyxxQO = JSONObject.parseObject(bdcdyxxQOJson, BdcdyxxQO.class);
        }
        return bdcdyXxService.listBdcdyxxByPage(pageable, bdcdyxxQO);
    }

    /**
     * 根据gzlslid查询档案柜信息
     *
     * @param pageable
     * @param gzlslid
     * @return DaztDTO
     * @Date 2022/1/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Page<DaztDTO> listDagxxByPage(Pageable pageable, String dzxxJson) {
        DaxxQO daxxQO = new DaxxQO();

        if (StringUtils.isNotBlank(dzxxJson)) {
            daxxQO = JSONObject.parseObject(dzxxJson, DaxxQO.class);
        }
        return bdcdyXxService.listDaxxByPage(pageable, daxxQO);
    }
}
