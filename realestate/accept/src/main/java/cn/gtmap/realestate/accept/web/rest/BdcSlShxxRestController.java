package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlShxxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlShxxDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlShxxQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlShxxRestService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcShxxVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/30
 * @description 审核信息业务Controller
 */
@RestController
@Api(tags = "不动产审核信息服务接口")
public class BdcSlShxxRestController extends BaseController implements BdcSlShxxRestService {
    @Autowired
    BdcSlShxxService bdcSlShxxService;

    /**
     * @param bdcShxx 审核信息实体类
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 更新指定节点的审核信息
     */
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "更新指定节点的审核信息(更新审核意见和签名时间)")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcShxx", value = "审核信息DO对象(对象中必须有shyj)", required = true, dataType = "BdcSlShxxDO")})
    @Override
    public int updateBdcShxx(@RequestBody BdcSlShxxDO bdcShxx) {
        return bdcSlShxxService.updateBdcShxx(bdcShxx);
    }

    /**
     * @param bdcSlShxxDO 审核信息实体类
     * @return int 操作数量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据主键查询shxx，没有则保存，有则更新
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新指定节点的审核信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcShxx", value = "审核信息DO对象", required = true, dataType = "BdcSlShxxDO")})
    @Override
    public int saveOrUpdateBdcShxx(@RequestBody BdcSlShxxDO bdcSlShxxDO) {
        return bdcSlShxxService.saveOrUpdateBdcShxx(bdcSlShxxDO);
    }

    /**
     * @param shxxid 审核信息ID
     * @return BdcSlShxxDO 审核信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据主键查询审核信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "依据主键查询审核信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "shxxid", value = "审核信息ID", required = true, dataType = "string", paramType = "path")})
    @Override
    public BdcSlShxxDO queryBdcShxxById(@PathVariable(name = "shxxid") String shxxid) {
        return bdcSlShxxService.queryBdcShxxById(shxxid);
    }

    /**
     * @param bdcSlShxxQO 审核信息查询对象
     * @return List<BdcSlShxxDO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查询审批信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询审核信息", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "审核项目ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "jdmc", value = "节点名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "shryxm", value = "审核人员姓名", dataType = "string", paramType = "query")})
    @Override
    public List<BdcSlShxxDO> queryBdcShxx(@RequestBody BdcSlShxxQO bdcSlShxxQO) {
        return bdcSlShxxService.queryBdcShxx(bdcSlShxxQO);
    }

    /**
     * @param paramList 审核信息集合
     * @return 更新数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 依据主键更新多条审核信息数据
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("依据主键更新多条审核信息数据")
    @Override
    @ApiImplicitParams({@ApiImplicitParam(name = "paramList", value = "需要更新的审核信息List", required = true, dataType = " List<BdcSlShxxDO>", paramType = "body")})
    public int updateShxxList(@RequestBody List<BdcSlShxxDO> paramList) {
        return bdcSlShxxService.updateShxxList(paramList);
    }

    /**
     * @param shxxid
     * @return 更新数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除审核意见和签名信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("删除审核意见和签名信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "shxxid", value = "审核信息ID", required = true, dataType = "string", paramType = "path")})
    public int deleteShxx(@PathVariable(name = "shxxid") String shxxid) {
        return bdcSlShxxService.deleteShxx(shxxid);
    }

    /**
     * @param shxxidList
     * @return 更新数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 批量删除审核意见和签名信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("批量删除审核意见和签名信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "shxxidList", value = "审核信息IDList", required = true, dataType = "List", paramType = "body")})
    public int deleteShxxSign(@RequestBody List<String> shxxidList) {
        return bdcSlShxxService.deleteShxxSign(shxxidList);
    }

    /**
     * @param taskId 当前任务ID
     * @return 更新数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 流程退回删除审核意见和签名信息，并保存审核结束时间
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("流程退回删除审核意见和签名信息，并保存审核结束时间")
    @ApiImplicitParams({@ApiImplicitParam(name = "taskId", value = "任务Id（shxxid）", required = true, dataType = "string", paramType = "path")})
    @Override
    public int deleteSignAndSaveShjssj(@PathVariable(name = "taskId") String taskId) {
        return bdcSlShxxService.deleteSignAndSaveShjssj(taskId);
    }

    /**
     * @param taskId 当前任务ID
     * @return int 操作的数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 更新审核结束时间（taskId和shxxid一致）
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新审核结束时间（taskId和shxxid一致）")
    @ApiImplicitParams({@ApiImplicitParam(name = "taskId", value = "任务Id（shxxid）", required = true, dataType = "string", paramType = "path")})
    @Override
    public int updateShjssj(@PathVariable(name = "taskId") String taskId) {
        return bdcSlShxxService.updateShjssj(taskId);
    }


    /**
     * @param bdcSlShxxQO 审核信息查询对象
     * @return 返回审核节点信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 获取签名意见，调用平台服务获取当前工作流配置的审核节点信息（出现异常则生成默认的初审，复审，核定节点信息），根据节点信息获取审核信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取签名意见，调用平台服务获取当前工作流配置的审核节点信息（出现异常则生成默认的初审，复审，核定节点信息），根据节点信息获取审核信息"
            , extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlShxxQO", value = "审核信息查询QO", dataType = "bdcSlShxxQO", paramType = "body")})
    @Override
    public List<BdcShxxVO> queryShJdxx(@RequestBody BdcSlShxxQO bdcSlShxxQO) {
        return bdcSlShxxService.queryShJdxx(bdcSlShxxQO);
    }



    /**
     * @param BdcSlShxxDO
     * @return signid
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 获取sign id
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询签名信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "BdcSlShxxDO", value = "审核信息DO", dataType = "BdcSlShxxDO", paramType = "body")})
    @Override
    public BdcShxxVO getShxxSign(@RequestBody BdcSlShxxDO BdcSlShxxDO) {
        return bdcSlShxxService.getShxxSign(BdcSlShxxDO);
    }

}
