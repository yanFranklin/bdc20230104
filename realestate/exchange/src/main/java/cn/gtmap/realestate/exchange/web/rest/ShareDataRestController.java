package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.service.rest.exchange.ShareDataRestService;
import cn.gtmap.realestate.common.core.vo.etl.BatchSharedbVO;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.service.national.AccesssModelHandlerService;
import cn.gtmap.realestate.exchange.service.national.ShareModelHandlerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:shaliyao@gtmap.cn">shaliyao</a>
 * @version 2020/0426,1.0
 * @description
 */
@OpenController(consumer = "不动产数据共享")
@RestController
@Api(tags = "不动产数据共享")
public class ShareDataRestController implements ShareDataRestService {

    @Autowired
    private AccesssModelHandlerService accesssModelHandlerService;
    @Autowired
    private ShareModelHandlerService shareModelHandlerService;

    private static Logger LOGGER = LoggerFactory.getLogger(ShareDataRestController.class);

    /**
     * @param xmid 项目主键
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据项目主键共享当前受理节点项目
     */
    @OpenApi(apiDescription = "根据项目主键共享当前受理节点项目",apiName = "",openLog = false)
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据项目主键共享当前受理节点项目")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目主键", required = true, dataType = "String", paramType = "query")})
    public void shareRunningXmByXmid(String xmid) {
        LOGGER.info("开始根据项目主键共享当前受理节点项目，xmid：{}", xmid);
        shareModelHandlerService.shareRunningXmByXmid(xmid);
    }

    /**
     * @param xmid 项目主键
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据项目主键共享当前项目
     */
    @OpenApi(apiDescription = "根据项目主键共享当前项目",apiName = "",openLog = false)
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据项目主键共享当前项目")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目主键", required = true, dataType = "String", paramType = "query")})
    public void shareXmByXmid(String xmid) {
        LOGGER.info("开始根据项目主键共享当前项目，xmid：{}", xmid);
        shareModelHandlerService.shareXmByXmid(xmid);
    }

    /**
     * @param xmid 项目主键
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据项目主键共享当前项目状态，可用于项目删除，退回
     */
    @OpenApi(apiDescription = "根据项目主键共享当前项目状态",apiName = "",openLog = false)
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据项目主键共享当前项目状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目主键", required = true, dataType = "String", paramType = "query")
            , @ApiImplicitParam(name = "status", value = "项目状态", required = true, dataType = "String", paramType = "query")})
    public void shareXmStatusByXmid(@RequestParam("xmid") String xmid, @RequestParam("status") String status) {
        LOGGER.info("开始根据项目主键共享当前项目状态，xmid：{},status:{}", xmid, status);
        shareModelHandlerService.shareXmStatusByXmid(xmid, status);
    }

    /**
     * @param processInsId 工作流实例id
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据项目主键共享同一流程所有受理节点项目
     */
    @OpenApi(apiDescription = "根据项目主键共享同一流程所有受理节点项目",apiName = "",openLog = false)
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据项目主键共享同一流程所有受理节点项目")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例id", required = true, dataType = "String", paramType = "query")})
    public void shareAllXmByProcessInsId(String processInsId) {
        LOGGER.info("开始根据项目主键共享同一流程所有受理节点项目，processInsId：{}", processInsId);
        shareModelHandlerService.shareAllXmByProcessInsId(processInsId);
    }

    /**
     * @param processInsId 工作流实例id
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据项目主键共享当前项目
     */
    @OpenApi(apiDescription = "根据项目主键共享同一流程所有项目",apiName = "",openLog = false)
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据项目主键共享同一流程所有项目")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例id", required = true, dataType = "String", paramType = "query")})
    public void shareRunningAllXmByProcessInsId(String processInsId) {
        LOGGER.info("开始根据项目主键共享同一流程所有项目，processInsId：{}", processInsId);
        shareModelHandlerService.shareRunningAllXmByProcessInsId(processInsId);
    }

    /**
     * @param processInsId 工作流实例id
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据项目主键共享当前项目状态，可用于项目删除，退回
     */
    @OpenApi(apiDescription = "根据项目主键共享同一流程所有项目状态", apiName = "", openLog = false)
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据项目主键共享同一流程所有项目状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例id", required = true, dataType = "String", paramType = "query")
            , @ApiImplicitParam(name = "status", value = "项目状态", required = true, dataType = "String", paramType = "query")
            , @ApiImplicitParam(name = "reason", value = "原因", required = false, dataType = "String", paramType = "query")})
    public void shareAllXmStatusByProcessInsId(@RequestParam("processInsId") String processInsId, @RequestParam("status") String status,
                                               @RequestParam(value = "reason", required = false) String reason) {
        LOGGER.info("开始根据项目主键共享同一流程所有项目状态，processInsId：{},status:{},reason:{}", processInsId, status, reason);
        shareModelHandlerService.shareAllXmStatusByProcessInsId(processInsId, status, reason);
    }

    /**
     * @param xmid 项目主键
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 根据项目主键补偿共享当前项目
     */
    @Override
    @OpenApi(apiDescription = "根据项目主键补偿共享当前项目", apiName = "", openLog = false)
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据项目主键补偿共享当前项目")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "xmid", required = true, dataType = "String", paramType = "query")
            , @ApiImplicitParam(name = "rzid", value = "日志id", required = true, dataType = "String", paramType = "query")})
    @GetMapping("/realestate-exchange/rest/v1.0/sharedb/xmid/for/compenstae")
    public CommonResponse shareXmByXmidForCompensate(@RequestParam("xmid")String xmid,@RequestParam("rzid")String rzid) {
        LOGGER.info("开始根据项目主键补偿共享当前项目，xmid：{},rzid:{}", xmid, rzid);
        return shareModelHandlerService.shareXmByXmidForCompensate(xmid,rzid);
    }

    /**
     * @param batchSharedbVO
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 批量根据项目主键补偿共享当前项目
     */
    @Override
    @OpenApi(apiDescription = "批量根据项目主键补偿共享当前项目", apiName = "", openLog = false)
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量根据项目主键补偿共享当前项目")
    public CommonResponse batchShareXmByXmidForCompensate(@RequestBody BatchSharedbVO batchSharedbVO) {
        if (CollectionUtils.isNotEmpty(batchSharedbVO.getBdcCzrzDOList())){
            List<String> errorList = new ArrayList<>(batchSharedbVO.getBdcCzrzDOList().size() >> 1);
            batchSharedbVO.getBdcCzrzDOList().forEach(bdcCzrzDO -> {
                CommonResponse commonResponse = shareXmByXmidForCompensate(bdcCzrzDO.getXmid(), bdcCzrzDO.getRzid());
                if (!commonResponse.isSuccess()){
                    errorList.add(bdcCzrzDO.getXmid());
                }
            });
            if (CollectionUtils.isNotEmpty(errorList)){
                return CommonResponse.fail(errorList);
            }
            return CommonResponse.ok();
        }
        return CommonResponse.fail("传入参数为空");
    }


}
