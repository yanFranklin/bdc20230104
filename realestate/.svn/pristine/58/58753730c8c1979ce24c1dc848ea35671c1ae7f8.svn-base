package cn.gtmap.realestate.engine.web.rest;

import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzLwDO;
import cn.gtmap.realestate.common.core.service.rest.engine.BdcGzXzYzLwRestService;
import cn.gtmap.realestate.common.core.vo.engine.ui.BdcGzLwVO;
import cn.gtmap.realestate.engine.core.service.BdcXzYzLwService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2019/3/4
 * @description 限制验证例外服务controller
 */
@RestController
@Api(tags="不动产规则验证例外接口")
public class BdcGzXzYzLwRestController extends BaseController implements BdcGzXzYzLwRestService {
    @Autowired
    BdcXzYzLwService bdcXzYzLwService;

    /**
     * @param czry 操作人员
     * @param ip 操作机器ip
     * @param lwwh 例外文号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询例外信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "分页查询例外信息", notes = "分页查询例外信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "czry", value = "操作人员名称",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ip", value = "ip",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "lwwh", value = "例外文号",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<BdcGzLwVO> listBdcXzyzLwPage(@RequestParam(name = "czry",required = false) String czry,
                                             @RequestParam(name = "ip",required = false) String ip,
                                             @RequestParam(name = "lwwh",required = false) String lwwh,
                                             Pageable pageable){
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isNotBlank(czry)){
            map.put("czry",StringUtils.deleteWhitespace(czry));
        }
        if(StringUtils.isNotBlank(ip)){
            map.put("ip",StringUtils.deleteWhitespace(ip));
        }
        if(StringUtils.isNotBlank(lwwh)){
            map.put("lwwh",StringUtils.deleteWhitespace(lwwh));
        }
        return bdcXzYzLwService.listBdcXzLwByPage(pageable,map);
    }

    /**
     * @param lwid 例外id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除例外信息
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据例外ID删除例外信息", notes = "根据例外ID删除例外信息服务")
    @ApiImplicitParam(name = "lwid", value = "例外id", required = true, dataType = "String", paramType = "query")
    @LogNote(value = "删除例外信息", action = Constants.LOG_ACTION_GZCZ)
    public void deleteBdcLwYz(String lwid){
        bdcXzYzLwService.deleteXzyzLw(lwid);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzLwDOList 例外规则集合
     * @return {int} 操作数据记录数
     * @description 新增验证例外规则
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "新增验证例外规则", notes = "新增验证例外规则")
    @ApiImplicitParam(name = "bdcGzLwDOList", value = "例外规则集合", required = true, dataType = "List", paramType = "body")
    @LogNote(value = "新增验证例外规则", action = Constants.LOG_ACTION_GZCZ)
    public int addBdcGzLw(@RequestBody List<BdcGzLwDO> bdcGzLwDOList) {
        return bdcXzYzLwService.addBdcGzLw(bdcGzLwDOList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzLwDOList 待删除例外规则集合
     * @return {int} 操作数据记录数
     * @description 批量删除已设置的验证例外规则
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "批量删除已设置的验证例外规则", notes = "批量删除已设置的验证例外规则")
    @ApiImplicitParam(name = "bdcGzLwDOList", value = "例外规则集合", required = true, dataType = "List", paramType = "body")
    @LogNote(value = "批量删除已设置的验证例外规则", action = Constants.LOG_ACTION_GZCZ)
    public int deleteBdcGzLwList(@RequestBody List<BdcGzLwDO> bdcGzLwDOList) {
        return bdcXzYzLwService.deleteBdcGzLwList(bdcGzLwDOList);
    }
}
