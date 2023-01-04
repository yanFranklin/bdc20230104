package cn.gtmap.realestate.engine.web.rest;

import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzBmdDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcBmdLoginDTO;
import cn.gtmap.realestate.common.core.service.rest.engine.BdcGzBmdRestService;
import cn.gtmap.realestate.engine.core.service.BdcGzBmdService;
import cn.gtmap.realestate.engine.util.Constants;
import cn.gtmap.realestate.engine.web.main.BaseController;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 2019/03/04,1.0
 * @description 强制验证白名单人员服务
 */
@RestController
@Api(tags = "不动产规则白名单接口")
public class BdcGzBmdRestController extends BaseController implements BdcGzBmdRestService {

    @Autowired
    BdcGzBmdService bdcGzBmdService;

    /**
     * @param czry   白名单人员
     * @param czrymm 白名单人员密码
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 查看白名单人员是否登录
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据白名单用户和密码查看是否登录", notes = "根据白名单用户和密码查看是否登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "czry", value = "白名单人员名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "czrymm", value = "白名单人员密码", required = true, dataType = "String", paramType = "query")
    })
    public BdcBmdLoginDTO checkLogin(String czry, String czrymm) {
        return bdcGzBmdService.checkIsLogin(czry, czrymm);
    }

    /**
     * @param czry 白名单人员
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 分页查询白名单人员
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "分页查询白名单人员信息", notes = "分页查询白名单人员信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "czry", value = "白名单人员", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<BdcGzBmdDO> bdcBmdPage(Pageable pageable, String czry) {
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isNotBlank(czry)) {
            map.put("czry", StringUtils.deleteWhitespace(czry));
        }
        return bdcGzBmdService.listBdcBmdByPage(pageable, map);
    }


    /**
     * @param bmdDOList
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 删除白名单
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("删除白名单人员")
    @ApiImplicitParams({@ApiImplicitParam(name = "bmdDOList", value = "白名单人员集合", dataType = "BdcGzBmdDO", required = true, paramType = "body")})
    @LogNote(value = "删除白名单人员", action = Constants.LOG_ACTION_GZCZ)
    public int deleteBmd(@RequestBody List<BdcGzBmdDO> bmdDOList) {
        return bdcGzBmdService.deleteBdcBmd(bmdDOList);
    }

    /**
     * @param bdcGzBmdDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 新增白名单
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("新增白名单人员")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcGzBmdDO", value = "白名单人员", dataType = "BdcGzBmdDO", required = true, paramType = "body")})
    @LogNote(value ="新增白名单人员", action = Constants.LOG_ACTION_GZCZ)
    public int insertBdcBmd(@RequestBody BdcGzBmdDO bdcGzBmdDO) {
        return bdcGzBmdService.insertBdcBmd(bdcGzBmdDO);
    }

    /**
     * @param bdcGzBmdDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 修改白名单
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("修改白名单人员")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcGzBmdDO", value = "白名单人员", dataType = "BdcGzBmdDO", required = true, paramType = "body")})
    @LogNote(value ="修改白名单人员", action = Constants.LOG_ACTION_GZCZ)
    public int updateBdcBmd(@RequestBody BdcGzBmdDO bdcGzBmdDO) {
        return bdcGzBmdService.updateBdcBmd(bdcGzBmdDO);
    }
}
