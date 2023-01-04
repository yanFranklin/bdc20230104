package cn.gtmap.realestate.natural.web.rest;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyShxxDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtMryjDO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyShxxQO;
import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyShxxRestService;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyShxxVO;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.natural.service.ZrzyShxxService;
import cn.gtmap.realestate.natural.web.main.BaseController;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangyinghao@gtmap.cn">wyh</a>"
 * @version 1.0, 2021/11/5
 * @description 证书服务
 */
@RestController
@Api(tags = "自然资源证书服务接口")
public class ZrzyShxxRestController extends BaseController implements ZrzyShxxRestService {
    @Autowired
    ZrzyShxxService zrzyShxxService;

    @Autowired
    UserManagerUtils userManagerUtils;

    /**
     * @param zrzyShxxDO 审核信息实体类
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新指定节点的审核信息
     */
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "更新指定节点的审核信息(更新审核意见和签名时间)")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcShxx", value = "审核信息DO对象(对象中必须有shyj)", required = true, dataType = "BdcShxxDO")})
    @Override
    public int updateZrzyShxx(@RequestBody ZrzyShxxDO zrzyShxxDO) {
        return zrzyShxxService.updateBdcShxx(zrzyShxxDO);
    }

    /**
     * @param zrzyShxxDO 审核信息实体类
     * @return int 操作数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据主键查询shxx，没有则保存，有则更新
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新指定节点的审核信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcShxx", value = "审核信息DO对象", required = true, dataType = "BdcShxxDO")})
    @Override
    public int saveOrUpdateZrzyShxx(@RequestBody ZrzyShxxDO zrzyShxxDO) {
        return zrzyShxxService.saveOrUpdateBdcShxx(zrzyShxxDO);
    }

    /**
     * @param shxxid 审核信息ID
     * @return BdcShxxDO 审核信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据主键查询审核信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "依据主键查询审核信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "shxxid", value = "审核信息ID", required = true, dataType = "string", paramType = "path")})
    @Override
    public ZrzyShxxDO queryZrzyShxxById(@PathVariable(name = "shxxid") String shxxid) {
        return zrzyShxxService.queryBdcShxxById(shxxid);
    }

    /**
     * @param zrzyShxxQO 审核信息查询对象
     * @return List<BdcShxxDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询审批信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询审核信息", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "审核项目ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "jdmc", value = "节点名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "shryxm", value = "审核人员姓名", dataType = "string", paramType = "query")})
    @Override
    public List<ZrzyShxxDO> queryZrzyShxx(@RequestBody ZrzyShxxQO zrzyShxxQO) {
        return zrzyShxxService.queryZrzyShxx(zrzyShxxQO);
    }




    /**
     * @param paramList 审核信息集合
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 依据主键更新多条审核信息数据
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("依据主键更新多条审核信息数据")
    @Override
    @ApiImplicitParams({@ApiImplicitParam(name = "paramList", value = "需要更新的审核信息List", required = true, dataType = " List<BdcShxxDO>", paramType = "body")})
    public int updateShxxList(@RequestBody List<ZrzyShxxDO> paramList) {
        return zrzyShxxService.updateShxxList(paramList);
    }

    /**
     * @param shxxid
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 删除审核意见和签名信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("删除审核意见和签名信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "shxxid", value = "审核信息ID", required = true, dataType = "string", paramType = "path")})
    public int deleteShxxSign(@PathVariable(name = "shxxid") String shxxid) {
        return zrzyShxxService.deleteShxxSign(shxxid);
    }

    /**
     * @param shxxidList
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量删除审核意见和签名信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("批量删除审核意见和签名信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "shxxidList", value = "审核信息IDList", required = true, dataType = "List", paramType = "body")})
    public int deleteShxxSign(@RequestBody List<String> shxxidList) {
        return zrzyShxxService.deleteShxxSign(shxxidList);
    }

    /**
     * @param taskId 当前任务ID
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 流程退回删除审核意见和签名信息，并保存审核结束时间
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("流程退回删除审核意见和签名信息，并保存审核结束时间")
    @ApiImplicitParams({@ApiImplicitParam(name = "taskId", value = "任务Id（shxxid）", required = true, dataType = "string", paramType = "path")})
    @Override
    public int deleteSignAndSaveShjssj(@PathVariable(name = "taskId") String taskId) {
        return zrzyShxxService.deleteSignAndSaveShjssj(taskId);
    }

    /**
     * @param taskId 当前任务ID
     * @return int 操作的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新审核结束时间（taskId和shxxid一致）
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新审核结束时间（taskId和shxxid一致）")
    @ApiImplicitParams({@ApiImplicitParam(name = "taskId", value = "任务Id（shxxid）", required = true, dataType = "string", paramType = "path")})
    @Override
    public int updateShjssj(@PathVariable(name = "taskId") String taskId) {
        return zrzyShxxService.updateShjssj(taskId);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据配置的sql生成默认意见")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bdcXtMryjDO", value = "默认意见DO", required = true, dataType = "BdcXtMryjDO", paramType = "body")})
    @Override
    public String generateMryjBySql(@RequestParam(name = "gzlslid") String gzlslid, @RequestBody ZrzyXtMryjDO zrzyXtMryjDO) {
        return zrzyShxxService.generateMryjBySql(gzlslid, zrzyXtMryjDO);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return List<String>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取流程配置的审批表打印类型
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取流程配置的审批表打印类型")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path")})
    @Override
    public Map<String, List<String>> getShxxDylx(@PathVariable(value = "gzlslid") String gzlslid) {
        return null;
    }

    /**
     * @param zrzyShxxQO 审核信息查询对象
     * @return 返回审核节点信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取签名意见，调用平台服务获取当前工作流配置的审核节点信息（出现异常则生成默认的初审，复审，核定节点信息），根据节点信息获取审核信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取签名意见，调用平台服务获取当前工作流配置的审核节点信息（出现异常则生成默认的初审，复审，核定节点信息），根据节点信息获取审核信息"
            , extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcShxxQO", value = "审核信息查询QO", dataType = "BdcShxxQO", paramType = "body")})
    @Override
    public List<ZrzyShxxVO> queryShJdxx(@RequestBody ZrzyShxxQO zrzyShxxQO) {
        return zrzyShxxService.queryShJdxx(zrzyShxxQO);
    }

    /**
     * @param shxxid 任务Id
     * @return BdcShxxDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程节点，最新的审核信息以及默认意见
     */
    @Override
    public ZrzyShxxVO queryMryj(@PathVariable(value = "shxxid") String shxxid) {
        return zrzyShxxService.queryMryj(shxxid);
    }

    /**
     * @param zrzyShxxDO
     * @return signid
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取sign id
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "保存签名信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcShxxDO", value = "审核信息DO", dataType = "BdcShxxDO", paramType = "body")})
    @Override
    public ZrzyShxxVO getShxxSign(@RequestBody ZrzyShxxDO zrzyShxxDO) {
        return zrzyShxxService.getShxxSign(zrzyShxxDO);
    }

    /**
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzlslid 工作流实例ID
     * @return {List} 生成的审核信息
     * @description  生成流程项目所有节点审核信息，意见内容采用默认意见
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "生成流程项目所有节点审核信息，意见内容采用默认意见")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path")
    public List<ZrzyShxxDO> generateShxxOfProInsId(@PathVariable("gzlslid") String gzlslid) {
        return zrzyShxxService.generateShxxOfProInsId(gzlslid);
    }

    /**
     * @param processInsId
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除审核意见和签名信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "删除审核信息pdf")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "query")})
    public void deleteShxxPdf(@RequestParam(name = "processInsId") String processInsId) {
        zrzyShxxService.deleteShxxPdf(processInsId);
    }
}
