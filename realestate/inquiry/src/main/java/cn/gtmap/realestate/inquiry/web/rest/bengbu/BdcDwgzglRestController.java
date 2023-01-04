package cn.gtmap.realestate.inquiry.web.rest.bengbu;

import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcDwgzglDO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.bengbu.BdcDwgzglRestService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.service.bengbu.BdcDwgzglService;
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

@RestController
@Api(tags = "（蚌埠）单位公章管理接口")
public class BdcDwgzglRestController implements BdcDwgzglRestService {

    @Autowired
    private BdcDwgzglService bdcDwgzglService;

    @Autowired
    UserManagerUtils userManagerUtils;

    /**
     * 分页查询单位公章信息
     *
     * @param pageable
     * @param bdcDwgzJson
     * @return BdcCfxxDTO                                                             ;>
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "单位公章查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "bdcDwgzJson", value = "单位公章Json", required = false, paramType = "query")})
    public Page<BdcDwgzglDO> listBdcDwgzByPage(Pageable pageable, @RequestParam(name = "bdcDwgzJson", required = false) String bdcDwgzJson) {
        BdcDwgzglDO bdcDwgzglQO = new BdcDwgzglDO();
        if (StringUtils.isNotBlank(bdcDwgzJson)) {
            bdcDwgzglQO = JSONObject.parseObject(bdcDwgzJson, BdcDwgzglDO.class);
        }
        return bdcDwgzglService.listBdcDwgzByPage(pageable, bdcDwgzglQO);
    }

    /**
     * @param bdcDwgzglDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 插入新的单位公章信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("新增单位公章")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDwgzglDO", value = "单位公章参数", dataType = "bdcDwgzglDO", required = true, paramType = "body")})
    public BdcDwgzglDO insertBdcDwgz(@RequestBody BdcDwgzglDO bdcDwgzglDO) {
        return bdcDwgzglService.insertBdcDwgz(bdcDwgzglDO);
    }

    /**
     * 更新单位公章信息
     *
     * @param bdcDwgzglDO
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 19:52 2020/7/27
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新单位公章")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDwgzglDO", value = "单位公章参数", dataType = "bdcDwgzglDO", required = true, paramType = "body")})
    public int updateBdcDwgz(@RequestBody BdcDwgzglDO bdcDwgzglDO) {
        return bdcDwgzglService.updateBdcDwgz(bdcDwgzglDO);
    }
}
