package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.accept.core.service.BdcSlPjqService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlPjqDO;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.accept.BdcMkqmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlPjqDTO;
import cn.gtmap.realestate.common.core.dto.accept.SlymPjqDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlPjqQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlPjqRestService;
import cn.gtmap.realestate.common.util.LogActionConstans;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @program: realestate
 * @description: 受理评价器
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-08-08 14:48
 **/
@RestController
@Api(tags = "不动产受理评价器服务")
public class BdcSlPjqRestController extends BaseController implements BdcSlPjqRestService {

    protected final Logger LOGGER = LoggerFactory.getLogger(BdcSlPjqRestController.class);

    @Autowired
    BdcSlPjqService bdcSlPjqService;
    @Value("#{'${nochecksfpj.username:}'.split(',')}")
    private List<String> usernameList;

    /**
     * @param ywbh 受理编号
     * @param jdmc
     * @return 不动产评价器Do
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据业务编号获取评价器信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据业务编号,当前节点名称获取评价器信息", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")), notes = "根据业务编号,当前节点名称获取评价器信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "ywbh", value = "业务编号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "jdmc", value = "节点名称", required = true, dataType = "String", paramType = "path")})
    public BdcSlPjqDO queryBdcSlPjqByYwbh(@PathVariable(value = "ywbh") String ywbh, @PathVariable(value = "jdmc") String jdmc) {
        return bdcSlPjqService.queryBdcSlPjqByYwbh(ywbh, jdmc);
    }

    /**
     * @param ywbh 受理编号
     * @return 不动产评价器Do
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据业务编号获取评价器信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据业务编号,当前节点名称获取评价器信息", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")), notes = "根据业务编号,当前节点名称获取评价器信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "ywbh", value = "业务编号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "jdmc", value = "节点名称", required = true, dataType = "String", paramType = "path")})
    public BdcSlPjqDO queryBdcSlPjqBySlbh(@PathVariable(value = "ywbh") String ywbh) {
        return bdcSlPjqService.queryBdcSlPjqByYwbh(ywbh, "");
    }

    /**
     * @param ywbh     受理编号
     * @param jdmc     节点名称
     * @param username 用户登录名
     * @return 不动产评价器Do
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据业务编号获取评价器信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据业务编号,当前节点名称获取评价器信息", notes = "根据业务编号,当前节点名称获取评价器信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "ywbh", value = "业务编号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "jdmc", value = "节点名称", required = true, dataType = "String", paramType = "path")})
    public BdcSlPjqDO queryBdcSlPjqByYwbh(@PathVariable(value = "ywbh") String ywbh, @PathVariable(value = "jdmc")String jdmc,@PathVariable(value = "username") String username) {
        if(CollectionUtils.isNotEmpty(usernameList) && usernameList.contains(username)) {
            return new BdcSlPjqDO();
        }
        return bdcSlPjqService.queryBdcSlPjqByYwbh(ywbh,jdmc);
    }

    /**
     * @param bdcSlPjqDO 不动产评价器Do
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产受理评价器信息
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增不动产受理评价器信息", notes = "新增不动产受理评价器信息服务")
    @ApiImplicitParam(name = "bdcSlPjqDO", value = "不动产受理评价器信息", required = true, dataType = "BdcSlPjqDO")
    public BdcSlPjqDO insertBdcSlPjq(@RequestBody BdcSlPjqDO bdcSlPjqDO) {
        return bdcSlPjqService.insertBdcSlPjq(bdcSlPjqDO);
    }

    /**
     * @param bdcSlPjqDO 不动产评价器Do
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新不动产受理评价器信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新不动产受理评价器信息", notes = "更新不动产受理评价器信息服务")
    @ApiImplicitParam(name = "bdcSlPjqDO", value = "不动产受理评价器信息", required = true, dataType = "BdcSlPjqDO")
    public Integer updateBdcSlPjq(@RequestBody BdcSlPjqDO bdcSlPjqDO) {
        return bdcSlPjqService.updateBdcSlPjq(bdcSlPjqDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "分页查询不动产受理评价统计信息", notes = "分页查询不动产受理评价统计信息服务")
    @ApiImplicitParam(name = "bdcSlPjqDO", value = "不动产受理评价器信息", required = true, dataType = "BdcSlPjqDTO")
    public Page<BdcSlPjqDO> listBdcSlPjTjByPage(@RequestParam("pageSize") Integer pageSize,
                                                 @RequestParam("pageNumber") Integer pageNumber,
                                                 @RequestBody BdcSlPjqDTO bdcSlPjqDTO){
        return bdcSlPjqService.listBdcSlPjTjByPage(bdcSlPjqDTO, pageSize, pageNumber);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "分页分组查询不动产受理评价统计信息", notes = "分页分组查询不动产受理评价统计信息服务")
    @ApiImplicitParam(name = "bdcSlPjqQO", value = "不动产受理评价器信息", required = true, dataType = "BdcSlPjqQO")
    public Page<BdcSlPjqDTO> listGroupBdcSlPjTjByPage(@RequestParam("pageSize") Integer pageSize,
                                                      @RequestParam("pageNumber") Integer pageNumber,
                                                      @RequestBody BdcSlPjqQO bdcSlPjqQO) {
        return bdcSlPjqService.listGroupBdcSlPjTjByPage(bdcSlPjqQO, pageSize, pageNumber);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "分组查询不动产受理评价统计信息", notes = "分组查询不动产受理评价统计信息服务")
    @ApiImplicitParam(name = "bdcSlPjqQO", value = "不动产受理评价器信息", required = true, dataType = "BdcSlPjqQO")
    public List<BdcSlPjqDTO> listGroupBdcSlPjTj(@RequestBody BdcSlPjqQO bdcSlPjqQO) {
        return bdcSlPjqService.listGroupBdcSlPjTj(bdcSlPjqQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据配置调用不同评价器", notes = "根据配置调用不同评价器")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "taskid", value = "节点ID", required = true, dataType = "String", paramType = "query")})
    public SlymPjqDTO commonPj(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "taskid")String taskid, @RequestParam(value = "clientip")String clientip){
        return bdcSlPjqService.commonPj(gzlslid, taskid,clientip);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "摩科人证对比", notes = "摩科人证对比")
    @ApiImplicitParams({@ApiImplicitParam(name = "qlrmc", value = "权利人名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrzjh", value = "权利人证件号", required = true, dataType = "String", paramType = "query")})
    public Object mkrzdb(@RequestParam(value = "qlrmc") String qlrmc, @RequestParam(value = "qlrzjh")String qlrzjh, @RequestParam(value = "gzlslid")String gzlslid,@RequestParam(value = "clientip")String clientip){
        return  bdcSlPjqService.mkrzdb(qlrmc, qlrzjh, gzlslid, clientip);
    }


    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "人证对比", notes = "人证对比")
    @ApiImplicitParams({@ApiImplicitParam(name = "qlrmc", value = "权利人名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrzjh", value = "权利人证件号", required = true, dataType = "String", paramType = "query")})
    public Object commonRzdb(@RequestParam(value = "qlrmc",required = false) String qlrmc, @RequestParam(value = "qlrzjh",required = false)String qlrzjh, @RequestParam(value = "gzlslid")String gzlslid,@RequestParam(value = "clientip")String clientip){
        return bdcSlPjqService.commonRzdb(qlrmc, qlrzjh, gzlslid, clientip);


    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "保存评价记录", notes = "保存评价记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "slymPjqDTO", value = "评价结果", required = true, paramType = "query")})
    public BdcCommonResponse savePjjl(@RequestBody SlymPjqDTO slymPjqDTO){
        UserDto userDto = userManagerUtils.getCurrentUser();
        try {
            bdcSlPjqService.savePjjl(slymPjqDTO, userDto);
        } catch (Exception e) {
            LOGGER.error("保存评价记录失败,失败信息e:{}", e);
            return BdcCommonResponse.fail("保存评价记录失败");
        }
        return BdcCommonResponse.ok();

    }


    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "摩科签字", notes = "摩科签字")
    @ApiImplicitParams({@ApiImplicitParam(name = "qlrmc", value = "权利人名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrzjh", value = "权利人证件号", required = true, dataType = "String", paramType = "query")})
    public Object mkqz(@RequestParam(value = "qlrmc",required = false) String qlrmc, @RequestParam(value = "qlrzjh",required = false)String qlrzjh, @RequestParam(value = "gzlslid")String gzlslid,@RequestParam(value = "clientip")String clientip){
        return bdcSlPjqService.mkqz(qlrmc, qlrzjh, gzlslid, clientip);
    }


    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "摩科推送评价到省厅", notes = "摩科推送评价到省厅")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "clientip", value = "系统ip", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "taskId", required = true, dataType = "String", paramType = "query")})
    public Object mktspj(@RequestParam(value = "processInsId") String processInsId,@RequestParam(value = "clientip") String clientip,@RequestParam(value = "taskId") String taskId){
        return bdcSlPjqService.mktspj(processInsId,taskId,clientip);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @LogNote(value = "保存附件至附件中心", action = LogActionConstans.OTHER )
    public Object mkqzhc(@RequestBody BdcMkqmDTO bdcMkqmDTO) throws IOException {
        return bdcSlPjqService.mkqzhc(bdcMkqmDTO);
    }

}
