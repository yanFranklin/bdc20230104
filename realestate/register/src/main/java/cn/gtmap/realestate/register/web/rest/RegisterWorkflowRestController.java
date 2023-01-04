package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzlwFeignService;
import cn.gtmap.realestate.common.core.service.rest.register.RegisterWorkflowRestService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.register.service.*;
import cn.gtmap.realestate.register.service.xxbl.BdcXxblService;
import cn.gtmap.realestate.register.web.main.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/27
 * @description 审核登簿用于工作流转发的服务
 */
@RestController
@Api(tags = "审核登簿子系统工作流服务")
public class RegisterWorkflowRestController extends BaseController implements RegisterWorkflowRestService {
    @Autowired
    BdcShxxRestController bdcShxxRestController;
    @Autowired
    BdcDbxxRestController bdcDbxxRestController;
    @Autowired
    BdcdyZtRestController bdcdyZtRestController;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcShxxService bdcShxxService;
    @Autowired
    BdcDbxxService bdcDbxxService;
    @Autowired
    BdcXmxxService bdcXmxxService;
    @Autowired
    BdcdyZtService bdcdyZtService;
    @Autowired
    BdcGzlwFeignService bdcGzlwFeignService;
    @Autowired
    BdcFgfRestCotroller bdcFgfRestCotroller;
    @Autowired
    BdcLcczxgService bdcLcczxgService;
    @Autowired
    BdcGgtsService bdcGgtsService;
    @Autowired
    BdcGzyzService bdcGzyzService;

    @Autowired
    BdcXxblService bdcXxblService;
    /**
     * @param processInsId    工作流实例id
     * @param currentUserName 当前账户
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void updateBdcDbxxAndQsztSyncQj(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName) {
        bdcDbxxService.updateBdcDbxxAndQsztSyncQj(processInsId, currentUserName);
    }

    /**
     * @param processInsId    工作流实例id
     * @param currentUserName 当前账户
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "path")})
    public void updateBdcDbxxAndQsztSyncQj2(@PathVariable(value = "processInsId") String processInsId, @PathVariable(value = "currentUserName") String currentUserName) {
        bdcDbxxService.updateBdcDbxxAndQsztSyncQj(processInsId, currentUserName);
    }

    /**
     * @param processInsId    工作流实例id
     * @param currentUserName 当前账户
     * @author <a href ="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 登簿时更新部分解封解压的规则例外审核状态
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("登簿时更新部分解封解压的规则例外审核状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void updateBdcGzlwShzt(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName) {
        bdcGzlwFeignService.updateBdcGzlwShzt(processInsId, currentUserName);
    }

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 登簿退回登簿信息和权属状态(还原当前权利为临时 ， 原权利为现势 ， 清空当前登簿信息 ， 清空注销权利的注销登簿信息)
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("登簿退回登簿信息和权属状态(还原当前权利为临时，原权利为现势，清空当前登簿信息，清空注销权利的注销登簿信息)")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query"), @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void revertBdcDbxxAndQsztSyncQj(@RequestParam(value = "processInsId") String processInsId) {
        bdcDbxxService.revertBdcDbxxAndQsztSyncQj(processInsId, true);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param syncQj  是否同步权籍
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 退回登簿信息和权属状态，调用端可控制是否更新权籍信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("登簿退回登簿信息和权属状态(还原当前权利为临时，原权利为现势，清空当前登簿信息，清空注销权利的注销登簿信息),调用端可控制是否更新权籍信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "syncQj", value = "是否更新权籍信息", required = true, dataType = "boolean", paramType = "query")})
    public void revertBdcDbxxAndQsztSyncQj(@PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "syncQj") boolean syncQj) {
        bdcDbxxService.revertBdcDbxxAndQsztSyncQj(gzlslid, syncQj);
    }


    /**
     * @param processInsId 工作流实例ID
     * @param qszt         权属状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新原注销权利的登簿信息和权属状态
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新原注销权利的登簿信息和权属状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "qszt", value = "权属状态", required = true, dataType = "Integer", paramType = "query")})
    @Override
    public void updateYzxqlDbxxAndQszt(String processInsId, Integer qszt) {
        bdcDbxxRestController.updateYzxqlDbxxAndQszt(processInsId, qszt);
    }


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param processInsId 工作流实例ID
     * @description 更新案件状态为2已完成状态
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新案件状态为2已完成状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    @Override
    public void changeAjzt(@RequestParam(value = "processInsId") String processInsId) {
        LOGGER.info("更新案件状态为2已完成状态：{}", processInsId);
        bdcDbxxRestController.changeAjzt(processInsId);
    }

    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param processInsId 工作流实例ID
     * @return
     * @description 同步权籍不动产单元状态(不包含锁定)
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("同步权籍不动产单元状态(不包含锁定)")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void synQjBdcdyzt(@RequestParam("processInsId") String processInsId){
        bdcDbxxRestController.synQjBdcdyzt(processInsId);
    }

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 同步权籍不动产单元状态2(不包含锁定)
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("同步权籍不动产单元状态2(不包含锁定)")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "path")})
    public void synQjBdcdyzt2(@PathVariable(value = "processInsId") String processInsId) {
        bdcDbxxRestController.synQjBdcdyzt(processInsId);
    }

    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param processInsId
     * @description 同步权籍基本信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("同步权籍基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void synQjJbxx(@RequestParam("processInsId") String processInsId){
        bdcDbxxRestController.synQjJbxx(processInsId);
    }

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 合并 同步权籍基本信息和不动产单元状态（不包含锁定）服务
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("同步权籍基本信息和不动产单元状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void synQjJbxxAndBdcdyzt(String processInsId) {
        bdcdyZtService.synQjJbxxAndBdcdyzt(processInsId);
    }

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 招行流程抵押流程增加回传功能
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("招行流程抵押流程增加回传功能")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void zsyhDyHcfw(String processInsId) {
        bdcDbxxRestController.zsyhDyHcfw(processInsId);
    }


    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 招行流程抵押注销流程增加回传功能
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("招行流程抵押注销流程增加回传功能")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void zsyhDyzxHcfw(String processInsId) {
        bdcDbxxRestController.zsyhDyzxHcfw(processInsId);
    }


    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 现省直房改房上市交易成功后，把不动产登记的数据再推送给房改办进行登记
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("省直房改房上市交易成功后，把不动产登记的数据再推送给房改办进行登记")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void djSzfgb(String processInsId) {
        bdcFgfRestCotroller.djSzfgb(processInsId);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [processInsId]
     * @return void
     * @description 更新当前流程所注销权利的附记 通过工作流配置
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新注销权利附记")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void updateZxqlfj(String processInsId) {
        bdcDbxxRestController.updateZxqlfj(processInsId);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("撤销流程，修改权属状态和案件状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "param")})
    public void cancelProcessQsztAndAjzt(@RequestParam(name = "processInsId") String processInsId) {
        bdcDbxxService.cancelProcessQsztAndAjzt(processInsId);
    }

    /**
     * @param processInsId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 创建档案交接流程
     * @date : 2021/6/9 9:25
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("创建档案交接流程")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void cjDajjLc(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name = "currentUserName", required = false) String currentUserName) throws Exception {
        bdcLcczxgService.cjDajjLc(processInsId, currentUserName);
    }

    /**
     * @param processInsId
     * @param currentUserName
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 档案交接流程转发事件
     * @date : 2021/6/15 9:58
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("转发档案交接流程")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void zfDajj(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name = "currentUserName") String currentUserName, @RequestParam(name = "taskId") String taskId) {
        bdcLcczxgService.zfDajj(taskId, processInsId, currentUserName);
    }

    /**
     * @param processInsId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 档案交接流程转发事件
     * @date : 2021/6/15 9:58
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("退回档案交接流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "opinion", value = "退回意见", required = false, dataType = "string", paramType = "query")
    })
    public void thDajj(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name = "opinion") String opinion) {
        bdcLcczxgService.thDajj(processInsId, opinion);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("档案接收节点办结时保存档案交接接收人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "currentUserName", value = "用户名", required = false, dataType = "string", paramType = "query")
    })
    public void saveDajjJsr(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name = "currentUserName") String currentUserName){
        bdcLcczxgService.saveDajjJsr(processInsId, currentUserName);

    }

    /**
     * @param processInsId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 档案交接流程退回事件
     * @date : 2021/6/15 9:58
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("办结档案交接流程")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void bjDajj(@RequestParam(name = "processInsId") String processInsId) {
        bdcLcczxgService.bjDajj(processInsId);
    }

    /**
     * 变更登记（带抵押）流程登簿时候追加抵押证明附记内容、重新生成抵押证明电子证照
     * @param processInsId    工作流实例id
     * @param currentUserName 当前用户
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("变更登记（带抵押）流程登簿时候追加抵押证明附记内容、重新生成抵押证明电子证照")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "currentUserName", value = "当前用户", required = false, dataType = "string", paramType = "query")})
    public void appendFjAndRebuildDzzz(@RequestParam(value = "processInsId") String processInsId,
                                       @RequestParam(value = "currentUserName", required = false) String currentUserName) {
        bdcDbxxService.appendFjAndRebuildDzzz(processInsId, currentUserName);
    }

    /**
     * 推送公告台账
     * @param processInsId    工作流实例id
     * @author <a href ="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("推送公告台账")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void pushBdcGgtz(@RequestParam(name = "processInsId") String processInsId) {
        bdcGgtsService.pushBdcGgtz(processInsId);
    }

    /**
     * @param processInsId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新锁定状态为锁定-1
     * @date : 2021/8/19 15:00
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新锁定状态为锁定,并且更新所选择权利的附记增加 已裁决")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void updateSdztSd(@RequestParam(name = "processInsId") String processInsId) {
        bdcLcczxgService.updateSdztSdAndBz(processInsId);
    }

    /**
     * @param processInsId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新锁定状态为正常 0
     * @date : 2021/8/19 15:00
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新锁定状态为锁定,并且更新所选择权利的附记增加 已裁决")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void updateSdztJs(String processInsId) {
        bdcLcczxgService.updateSdztJs(processInsId);
    }

    /**
     * @param processInsId
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取行政代码
     * @date : 2021/9/27
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取行政代码")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public Map<String, String> getXzdm(@RequestParam(value = "processInsId") String processInsId) {
        return bdcLcczxgService.getXzdm(processInsId);
    }

    /**
     * @param processInsId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新锁定状态为正常 0
     * @date : 2021/8/19 15:00
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新锁定状态为锁定,并且更新所选择权利的附记增加 已裁决")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void updateSdztJsForDelete(@RequestParam("processInsId") String processInsId) {
        bdcLcczxgService.updateSdztJsForLcsc(processInsId);
    }

    /**
     * @param processInsId
     * @param currentUserName
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 撤销登记单元锁定状态改为解锁
     * @date : 2022/9/1 13:52
     */
    @Override
    public void updateCxdjDysdzt(@RequestParam(name = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName) {
        bdcLcczxgService.updateSdztForCxdj(processInsId, currentUserName);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("冻结/解冻时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void updateQsztSyncQjForDj(@RequestParam("processInsId") String processInsId) {
        bdcDbxxService.updateQsztSyncQjForDj(processInsId);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("核定该宗地上每一户室所占份额")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void syncHdhsfe(@RequestParam("processInsId") String processInsId) {
        bdcDbxxService.syncHdhsfe(processInsId);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("登簿将预查封转为现势查封")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void changeYcfToCf(@RequestParam(value = "processInsId") String processInsId){
        bdcDbxxService.changeYcfToCf(processInsId);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("登簿将预查封转为现势查封,生成新的查封记录，注销原有的预查封")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void initCfFromYcf(@RequestParam(value = "processInsId") String processInsId){
        bdcDbxxService.initCfFromYcf(processInsId);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("房地产权首次登记更新外联证书的权利附记")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void updateSpfscdjWlzsQlfj(@RequestParam(value = "processInsId")  String processInsId) throws Exception {
        bdcDbxxService.updateSpfscdjWlzsQlfj(processInsId);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("自动转发或自动办结验证地址")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public Map<String, String> checkZdzfzdbj(@RequestParam(name = "processInsId") String processInsId){
        return bdcGzyzService.checkZdzfzdbj(processInsId);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取节点自动转发指定人员")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public Map<String, String> getZdzfSlr(@RequestParam(name = "processInsId") String processInsId,@RequestParam(name = "currentNodeName") String currentNodeName){
        return bdcLcczxgService.getZdzfSlr(processInsId, currentNodeName);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("删除信息补录数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void deleteXxbl(@RequestParam(value = "processInsId") String processInsId) throws Exception {
        bdcXxblService.deleteBlxx(processInsId);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("恢复信息补录数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void hfXxbl(String processInsId) throws Exception {
        bdcXxblService.hyql(processInsId);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取外网申请退回件原审核人员")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public Map<String, String> getWwwsqYshry(@RequestParam(name = "processInsId") String processInsId,@RequestParam(name = "currentNodeName") String currentNodeName){
        return bdcLcczxgService.getWwwsqYshry(processInsId,currentNodeName);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新外网件登簿人")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void updateWwsqDbr(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName){
        bdcDbxxService.updateWwsqDbr(processInsId, currentUserName);


    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("办理在建建筑抵押时，更新户室所在的土地证附记")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void updateZjjzTdzfj(@RequestParam(value = "processInsId") String processInsId) throws Exception {
        bdcDbxxService.updateZjjzTdzfj(processInsId);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("登簿更新关联单元号土地抵押释放信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void updateTddysfxx(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName) throws Exception{
        bdcDbxxService.updateTddysfxx(processInsId,currentUserName);

    }

    /**
     * 登记系统房屋发生转移类登记后，将合同的状态修改为注销
     *
     * @param processInsId
     * @param currentUserName
     * @throws Exception
     */
    @Override
    public void updateHtbazt(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName) throws Exception {
        bdcDbxxService.updateHtbazt(processInsId,currentUserName);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("推送工改系统办件状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "currentUserName", value = "当前用户", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "spjg", value = "审批结果", required = false, dataType = "string", paramType = "query")})
    public void tzggxtBjzt(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "spjg",required = false) String spjg, @RequestParam(value = "spjd",required = false) String spjd, @RequestParam(value = "reason", required = false) String reason){
        bdcDbxxService.tzggxtBjzt(processInsId,spjg,spjd,reason);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("推送工改系统电子证照信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void tsggztDzzzxx(@RequestParam(value = "processInsId") String processInsId){
        bdcDbxxService.tsggztDzzzxx(processInsId);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("外联产权，产权选择注销，新流程在登簿的时候将产权上的现势限制权利挂到当前新产权上，单元号变更为新产权的单元号,单元号变更为新产权的单元号")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void wlcqzx(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName) throws Exception{
        bdcDbxxService.wlcqzx(processInsId,currentUserName);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("登簿时更新林权流转状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query")})
    public void updateLqlzzt(@RequestParam(value = "processInsId") String processInsId){
        bdcDbxxService.updateLqlzzt(processInsId);

    }
}
