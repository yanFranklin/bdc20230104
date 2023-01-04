package cn.gtmap.realestate.register.web.rest.xxbl;

import cn.gtmap.gtc.sso.domain.dto.AuditLogDto;
import cn.gtmap.gtc.starter.gcas.config.GTAutoConfiguration;
import cn.gtmap.realestate.common.core.enums.LogEventEnum;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.register.BdcXxblLogRestService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDbDetailVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDbVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcXxblLogVO;
import cn.gtmap.realestate.register.service.xxbl.BdcXxblCompareService;
import cn.gtmap.realestate.register.service.xxbl.BdcXxblLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 不动产信息补录日志服务接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 11:29 上午 2020/3/19
 */
@RestController
@Api(tags = "不动产信息补录日志服务接口")
public class BdcXxblLogRestController implements BdcXxblLogRestService {

    /**
     * 补录日志服务
     */
    @Autowired
    private BdcXxblLogService bdcXxblLogService;
    /**
     * 日志对比服务
     */
    @Autowired
    private BdcXxblCompareService bdcXxblCompareService;

    /**
     * 根据 event 和 查询参数 获取日志数据
     *
     * @param pageable 分页参数
     * @param paramch  查询参数
     * @param event    日志类型
     * @return {List} 日志 VO 对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据 event 和 查询参数 获取日志数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "paramch", value = "paramch", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "event", value = "event", dataType = "LogEventEnum", paramType = "query")})
    public Page<BdcXxblLogVO> listLog(Pageable pageable, @RequestParam(name = "paramch") String paramch,
                                      @RequestParam(name = "event") LogEventEnum event) {
        Page<AuditLogDto> auditLogDtos = bdcXxblLogService.listLog(pageable, paramch, event);
        List<BdcXxblLogVO> logList = bdcXxblLogService.dealLogPage(auditLogDtos);
        return new GTAutoConfiguration.DefaultPageImpl<>(logList, pageable.getPageNumber(),
                pageable.getPageSize(), auditLogDtos.getTotalElements());
    }

    /**
     * @param id 回退的日志 id
     * @throws Exception 初始化更新业务信息出现异常
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 选择指定 id 的日志中 before 数据回退
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "退回到选择日志中的 before 版本")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", dataType = "String", paramType = "path")})
    public void backLog(@PathVariable(value = "id") String id) throws Exception {
        if (StringUtils.isBlank(id)) {
            throw new MissingArgumentException("id");
        }
        bdcXxblLogService.backLog(id);
    }

    /**
     * @param xmid 当前项目 id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 还原修改的业务的数据 <br>
     * 还原数据取日志中保存的被修改数据的业务信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("还原修改的业务的数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "xmid", dataType = "String", paramType = "query")})
    public void backBllcModifyLog(@RequestParam(value = "xmid") String xmid) throws Exception {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("xmid");
        }
        String ywxxJson = bdcXxblLogService.queryBllcModifyLog(xmid);
        if (StringUtils.isNotBlank(ywxxJson)) {
            bdcXxblLogService.backYwxxFromJson(ywxxJson);
        }
    }

    /**
     * 信息对比<br/>
     * 获取当前项目相比较于初始化结束后修改的数据
     *
     * @param processInsId 工作流实例 id
     * @return {List<BdcDbVO>} 对比 VO 集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取当前项目相比较于初始化结束后修改了哪些数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "processInsId", dataType = "String", paramType = "query")})
    public List<BdcDbVO> xxdb(String processInsId) throws Exception {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("processInsId");
        }
        return bdcXxblCompareService.xxdb(processInsId);
    }

    /**
     * 查询页面高亮显示内容
     *
     * @param processInsId 修改流程的 processInsId
     * @return {List<BdcDbDetailVO>} 对比 VO 集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取当前项目相比较于初始化结束后修改了哪些数据（无结构）")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "processInsId", dataType = "String", paramType = "query")})
    public List<BdcDbDetailVO> queryGlxx(String processInsId) throws Exception {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("processInsId");
        }
        return bdcXxblCompareService.queryGlxx(processInsId);
    }

    /**
     * @param xmid 当前项目 id
     * @return {String} 日志中对应的 ywxx json 字符串
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 查询当前补录修改流程的初始化日志，返回 ywxx值
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询当前补录修改流程的初始化日志")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "xmid", dataType = "String", paramType = "query")})
    public String queryBllcModifyLog(@RequestParam(value = "xmid") String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("xmid");
        }
        return bdcXxblLogService.queryBllcModifyLog(xmid);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取信息补录修改记录对比")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "processInsId", dataType = "String", paramType = "query")})
    public List<BdcDbVO> getSjdb(@RequestParam(value = "processInsId") String processInsId,@RequestParam(value = "xmid",required = false) String xmid) {
        return bdcXxblLogService.getSjdb(processInsId,xmid);
    }
}
