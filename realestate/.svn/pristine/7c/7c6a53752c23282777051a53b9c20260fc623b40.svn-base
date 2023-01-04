package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.service.BdcSwService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSysxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYjkxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSfSsxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSwxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.TsswDataDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfskxxhq.response.YrbClfskxxhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.ewmjkxx.response.YrbEwmjkxxResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qslxdhq.response.YrbQslxdhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswspzhq.response.YrbQswspzhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfskxxhq.response.YrbZlfjsxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.swxx.QuerySwxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.swxx.ReceiveSwxxRequestDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSwxxQO;
import cn.gtmap.realestate.common.core.qo.accept.TsswDataQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSwRestService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSwJkmxxVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSwxxVO;
import cn.gtmap.realestate.common.util.LogActionConstans;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/24
 * @description 税务信息rest服务
 */
@RestController
@Api(tags = "不动产税务信息服务")
public class BdcSwRestController extends BaseController implements BdcSwRestService {
    @Autowired
    BdcSwService bdcSwService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "税务存量房信息推送", notes = "税务存量房信息推送服务")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    public Object tsSwxx(@PathVariable(value = "gzlslid") String gzlslid,@RequestParam(value = "beanName") String beanName){
        return bdcSwService.tsSwxx(gzlslid,beanName);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取税务计税信息，并推送", notes = "获取税务计税信息，并推送")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beanName", value = "接口标识符", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = false, dataType = "String", paramType = "query")})
    public Object tsjsxx(@RequestParam(value="gzlslid") String gzlslid, @RequestParam(value="beanName")  String beanName,
                         @RequestParam(value="xmid", required = false) String xmid) {
        return bdcSwService.tsjsxx(gzlslid, beanName, xmid);
    }


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 南通一窗受理推送计税信息接口调整
     * @date : 2022/9/19 9:59
     */
    @Override
    public Object ntYcslTsjsxx(@RequestParam(value="gzlslid") String gzlslid,@RequestParam(value="xmid", required = false) String xmid,@RequestParam(value = "fclx", required = false) String fclx) {
        return bdcSwService.ntYcslTsjsxx(gzlslid,xmid,fclx);
    }

    /**
     * @param xmid 项目ID
     * @param slbh 受理编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  税务信息查询
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "税务信息查询", notes = "税务信息查询服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "slbh", value = "受理编号", required = true, dataType = "String", paramType = "path")})
    public QuerySwxxResponseDTO getSwxx(@PathVariable(value = "xmid") String xmid, @PathVariable(value = "slbh")String slbh,@RequestParam(value = "gxlx") String gxlx){
        BdcSwxxQO bdcSwxxQO =new BdcSwxxQO();
        bdcSwxxQO.setXmid(xmid);
        bdcSwxxQO.setSlbh(slbh);
        bdcSwxxQO.setGxlx(gxlx);
        return bdcSwService.getSwxx(bdcSwxxQO);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "税务信息查询", notes = "税务信息查询服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSwxxQO", value = "税务信息查询QO", required = true, dataType = "BdcSwxxQO", paramType = "query")})
    public QuerySwxxResponseDTO getSwxx(@RequestBody BdcSwxxQO bdcSwxxQO){
        return bdcSwService.getSwxx(bdcSwxxQO);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "税务信息批量查询", notes = "税务信息批量查询服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSwxxQO", value = "税务信息查询QO", required = true, dataType = "BdcSwxxQO", paramType = "query")})
    public Object getPlSwxx(@RequestBody BdcSwxxQO bdcSwxxQO){
        return bdcSwService.getPlSwxx(bdcSwxxQO);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "处理保存税务查询结果", notes = "处理保存税务查询结果服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "responseDTO", value = "税务查询结果", required = true, dataType = "QuerySwxxResponseDTO", paramType = "query")})
    public void handleQuerySwxxResponse(@RequestBody QuerySwxxResponseDTO responseDTO, @PathVariable(value = "xmid") String xmid,@PathVariable(value = "gxlx")String gxlx){
        bdcSwService.handleQuerySwxxResponse(responseDTO, xmid, gxlx);

    }

    /**
     * @param xmid 项目ID
     * @param slbh 受理编号
     * @param gxlx 更新类型 1：先删除原有税务信息，重新插入税务信息 2.根据纳税人识别号更新核税信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 税务信息查询，合肥德宽版
     * @date : 2020/8/11 15:57
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "税务信息查询", notes = "税务信息查询服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "slbh", value = "受理编号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "gxlx", value = "更新类型", required = false, dataType = "String", paramType = "query")})
    public QuerySwxxResponseDTO getSwxxDk(@PathVariable(value = "xmid") String xmid, @PathVariable(value = "slbh")String slbh,@RequestParam(value = "gxlx") String gxlx) {
        return bdcSwService.getSwxxDk(xmid,slbh,gxlx);
    }

    /**
     * @param xmid 项目ID
     * @param slbh 受理编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  税务申报取消作废
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "税务申报取消作废", notes = "税务申报取消作废服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "slbh", value = "受理编号", required = true, dataType = "String", paramType = "path")})
    public Object qxzfSwxx(@PathVariable(value = "xmid") String xmid,@PathVariable(value = "slbh")String slbh){
        return bdcSwService.qxzfSwxx(xmid, slbh);

    }

    /**
     * @param xmid 项目ID
     * @param slbh 受理编号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 取消作废税务信息 -合肥德宽版税务系统
     * @date : 2020/8/11 14:55
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "税务申报取消作废", notes = "税务申报取消作废服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "slbh", value = "受理编号", required = true, dataType = "String", paramType = "path")})
    public Object qxzfSwxxDk(@PathVariable(value = "xmid") String xmid,@PathVariable(value = "slbh")String slbh) {
        return bdcSwService.qxzfSwxxDK(xmid,slbh);
    }

    /**
     * @param slbh 受理编号
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 批量取消作废税务信息 -合肥德宽版税务系统
     * @date : 2022/9/26
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "税务申报批量取消作废", notes = "税务申报批量取消作废服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "slbh", value = "受理编号", required = true, dataType = "String", paramType = "path")})
    public Object plQxzfSwxxDk(@PathVariable(value = "slbh")String slbh) {
        return bdcSwService.plQxzfSwxxDK(slbh);
    }


    /**
     * @param processInsId 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 流程作废取消 税务
     * @date : 2020/10/19 11:22
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID税务申报取消作废", notes = "根据工作流实例ID税务申报取消作废服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", dataType = "String", paramType = "path")})
    public Object qxzfLcSwxx(@PathVariable(value = "processInsId") String processInsId){
        return bdcSwService.qxzfLcSwxx(processInsId);

    }

    /**
     * @param htbh 合同编号
     * @param wszt 完税状态
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据合同编号更新完税状态
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据合同编号更新完税状态", notes = "根据合同编号更新完税状态服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "htbh", value = "项目ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "wszt", value = "完税状态", required = true, dataType = "String", paramType = "path")})
    public Map updateWsztByHtbh(@PathVariable(value = "htbh") String htbh,@PathVariable(value = "wszt") String wszt){
        return bdcSwService.updateWsztByHtbh(htbh, wszt);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 完税状态
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证是否完税
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID验证是否完税", notes = "根据工作流实例ID验证是否完税")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    public Boolean checkSfwsByGzlslid(@PathVariable(value = "gzlslid") String gzlslid){
        return bdcSwService.checkSfwsByGzlslid(gzlslid);

    }
    /**
     * @param gzlslid 工作流实例ID
     * @return QuerySwxxResponseDTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 商品房完税状态
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID获取商品房完税状态", notes = "根据工作流实例ID获取商品房完税状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    public List<QuerySwxxResponseDTO> querySpfwszt(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcSwService.querySpfwszt(gzlslid);
    }

    /**
     * @param bdcXmDO 不动产项目DO
     * @return QuerySwxxResponseDTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 商品房完税状态
     */
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据项目查询商品房完税状态", notes = "根据项目查询商品房完税状态")
    @Override
    public QuerySwxxResponseDTO getSpfXmWszt(@RequestBody BdcXmDO bdcXmDO,@RequestParam(value = "gxlx") String gxlx) {
        return bdcSwService.getSpfXmWszt(bdcXmDO,gxlx);
    }



    /**
     * @param gzlslid 工作流实例ID
     * @return String 最终结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取税务三要素核税信息
     */
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取税务三要素核税信息", notes = "获取税务三要素核税信息")
    @Override
    public String getSwsysHsxx(@RequestParam(value = "gzlslid") String gzlslid) {
        return bdcSwService.getSwsysHsxx(gzlslid);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return Boolean 建行订单查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 建行订单查询并缴库入库
     */
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "建行订单查询并缴库入库", notes = "建行订单查询并缴库入库")
    @Override
    public Object yhddcxAndJkrk(@RequestParam(value = "gzlslid") String gzlslid) {
        return bdcSwService.yhddcxAndJkrk(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "调用一卡清接口，执行推送缴库", notes = "调用一卡清接口，执行推送缴库")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID",  dataType = "String", paramType = "query")})
    public CommonResponse ykqTsJkrk(@RequestParam(value="gzlslid") String gzlslid) {
        return bdcSwService.ykqTsJkrk(gzlslid);
    }

    /**
     * @param bdcSfSsxxDTO 工作流实例ID
     * @return Boolean 建行缴库入库
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 建行缴库入库
     */
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "建行缴库入库", notes = "建行缴库入库")
    @Override
    public Object yhjkrk(@RequestBody BdcSfSsxxDTO bdcSfSsxxDTO) {
        return bdcSwService.yhjkrk(bdcSfSsxxDTO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "调用一卡清接口，执行推送缴库", notes = "调用一卡清接口，执行推送缴库")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID",  dataType = "String", paramType = "query")})
    public CommonResponse ykqZhjq(@RequestParam(value="processInsId") String processInsId) {
        return  bdcSwService.ykqZhjq(processInsId);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据查询参数提供税务所需信息", notes = "根据查询参数提供税务所需信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "tsswDataQO", value = "查询参数",  dataType = "TsswDataQO", paramType = "query")})
    public TsswDataDTO getTsswDataDTO(@RequestBody TsswDataQO tsswDataQO){
        return bdcSwService.getTsswDataDTO(tsswDataQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据合同编号保存税务信息", notes = "根根据合同编号保存税务信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSwxxDTOList", value = "税务信息"),@ApiImplicitParam(name = "htbh", value = "合同编号")})
    public void saveSwxxDTOByHtbh(@RequestBody List<BdcSwxxDTO> bdcSwxxDTOList, @RequestParam(value = "htbh") String htbh){
        bdcSwService.saveSwxxDTOByHtbh(bdcSwxxDTOList, htbh);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据受理编号更新税务三要素", notes = "根据受理编号更新税务三要素服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlHsxxDOList", value = "需要更新的税务信息集合"),@ApiImplicitParam(name = "slbh", value = "受理编号")})
    public void updateSwsysByNsrbhAndSlbh(@RequestBody List<BdcSlHsxxDO> bdcSlHsxxDOList, @RequestParam(value = "slbh") String slbh){
        bdcSwService.updateSwsysByNsrbhAndSlbh(bdcSlHsxxDOList, slbh);


    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "接受德宽的税务信息", notes = "接受德宽的税务信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "receiveSwxxRequestDTO", value = "需要更新的税务信息集合")})
    public Map insertOrUpdateSwxx(@RequestBody ReceiveSwxxRequestDTO receiveSwxxRequestDTO){
        return bdcSwService.insertOrUpdateSwxx(receiveSwxxRequestDTO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "接收第三方的缴税状态", notes = "接收第三方的缴税状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "receiveSwxxRequestDTO", value = "税务信息", dataType = "ReceiveSwxxRequestDTO", paramType = "body")})
    public CommonResponse jsDsfJszt(@RequestBody ReceiveSwxxRequestDTO receiveSwxxRequestDTO) {
        return bdcSwService.jsDsfJszt(receiveSwxxRequestDTO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取完税信息，根据完税状态处理完税信息", notes = "获取完税信息，根据完税状态处理完税信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID",  dataType = "string", paramType = "path")})
    public Object getAndHandleWsxx(@PathVariable(value = "gzlslid") String gzlslid) throws IOException {
        return bdcSwService.getAndHandleWsxx(gzlslid);
    }

    /**
     * @param gzlslid@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/10/27 10:46
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "单独推送附件材料给税务", notes = "单独推送附件材料给税务服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID",  dataType = "string", paramType = "path")})
    public void tsFjcl(@PathVariable(value = "gzlslid") String gzlslid) {
        bdcSwService.tsFjcl(gzlslid);
    }

    /**
     * 根据sply和processInsId判断当前流程是否完税 完税页面要给出相应提示
     *
     * @param processInsId
     * @param sply
     * @return 是否需要提示 boolean
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "单独推送附件材料给税务", notes = "单独推送附件材料给税务服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID",  dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sply", value = "审批来源",  dataType = "string", paramType = "query")})
    public Object showSpWsXx(@RequestParam("processInsId") String processInsId, @RequestParam("sply")String sply) {
        return bdcSwService.showSpWsXx(processInsId,sply);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "推送核税信息", notes = "推送核税信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID",  dataType = "string", paramType = "query")})
    public Object tshsxx(@RequestParam(value = "gzlslid")String gzlslid) {
        return bdcSwService.tshsxx(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据受理编号保存税务三要素信息", notes = "根据受理编号保存税务三要素信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlSysxxDOList", value = "三要素信息")})
    public Integer insertSwsys(@RequestBody List<BdcSlSysxxDO> bdcSlSysxxDOList, @RequestParam(value = "slbh")String slbh){
        return bdcSwService.insertSwsys(bdcSlSysxxDOList, slbh);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据受理编号获取三要素信息", notes = "根据受理编号获取三要素信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "slbh", value = "受理编号")})
    public List<BdcSlSysxxDO> listSysxxBySlbh(@PathVariable(value = "slbh") String slbh) {
        return bdcSwService.listSysxxBySlbh(slbh);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "一人办件获取税务信息", notes = "一人办件获取税务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beanName", value = "请求接口名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fwlx", value = "房屋类型", required = false, dataType = "String", paramType = "query"),
    })
    public Object getYrbjSwxx(@RequestParam(value = "gzlslid") String gzlslid,
                              @RequestParam(value = "beanName") String beanName,
                              @RequestParam(value = "xmid", required = false) String xmid,
                              @RequestParam(value = "fwlx", required = false) String fwlx) {
        return bdcSwService.getYrbjSwxx(gzlslid, beanName, xmid, fwlx);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取申报单信息", notes = "获取申报单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fwlx", value = "房屋类型", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "htbh", value = "合同编号", required = true, dataType = "String", paramType = "query"),
    })
    public void getSwSbd(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "fwlx", required = false) String fwlx, @RequestParam(value = "htbh") String htbh) {
        bdcSwService.getSwSbdxx(gzlslid, fwlx, htbh);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取存量房计税信息", notes = "获取存量房计税信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSwxxQO", value = "税务信息查询QO", required = true, dataType = "BdcSwxxQO", paramType = "body"),
    })
    public YrbClfskxxhqDTO getClfJsxx(@RequestBody BdcSwxxQO bdcSwxxQO) {
        return bdcSwService.getClfJsxx(bdcSwxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取增量房计税信息", notes = "获取增量房计税信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSwxxQO", value = "税务信息查询QO", required = true, dataType = "BdcSwxxQO", paramType = "body"),
    })
    public YrbZlfjsxxDTO getZlfJsxx(@RequestBody BdcSwxxQO bdcSwxxQO) {
        return bdcSwService.getZlfJsxx(bdcSwxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "确认申报单", notes = "确认申报单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fwlx", value = "房屋类型", required = false, dataType = "String", paramType = "query"),
    })
    public Object qrSbd(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "fwlx", required = false) String fwlx) {
        return bdcSwService.qrSbd(gzlslid, fwlx);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取税票", notes = "获取税票")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "jszt", value = "缴税状态", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fwlx", value = "房屋类型", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "htbh", value = "合同编号", required = false, dataType = "String", paramType = "query"),
    })
    public CommonResponse getSpxx(@RequestParam(value = "gzlslid") String gzlslid,
                        @RequestParam(value = "jszt") String jszt,
                        @RequestParam(value = "fwlx", required = false) String fwlx,
                        @RequestParam(value = "htbh", required = false) String htbh) {
        return bdcSwService.getSpxx(gzlslid, jszt, fwlx, htbh);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取契税完税信息", notes = "获取契税完税信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query"),
    })
    public CommonResponse hqqsws(@RequestParam(value = "gzlslid") String gzlslid) {
        return this.bdcSwService.hqqsws(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取缴税二维码", notes = "获取缴税二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "htbh", value = "合同编号", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fwlx", value = "房屋类型", required = false, dataType = "String", paramType = "query"),
    })
    public List<BdcSwJkmxxVO> getJsewm(@RequestParam(value = "gzlslid") String gzlslid,
                                       @RequestParam(value = "htbh", required = false) String htbh,
                                       @RequestParam(value = "fwlx", required = false) String fwlx) {
        return bdcSwService.getJsewm(gzlslid, htbh, fwlx);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取税务契税完税凭证", notes = "获取税务契税完税凭证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSwxxQO", value = "税务信息查询QO", required = true, dataType = "BdcSwxxQO", paramType = "body"),
    })
    public Object getQswspz(@RequestBody BdcSwxxQO bdcSwxxQO) {
        return bdcSwService.getQswspz(bdcSwxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取契税联系单", notes = "获取契税联系单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSwxxQO", value = "税务信息查询QO", required = true, dataType = "BdcSwxxQO", paramType = "body"),
    })
    public Object getQslxd(@RequestBody BdcSwxxQO bdcSwxxQO) {
        return bdcSwService.getQslxd(bdcSwxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "处理保存契税完税凭证查询结果", notes = "处理保存契税完税凭证查询结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "yrbQswspzhqDTO", value = "契税完税凭证查询结果", required = true, dataType = "YrbQswspzhqDTO", paramType = "body"),
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "xmid", paramType = "path"),
            @ApiImplicitParam(name = "gxlx", value = "更新类型", required = true, dataType = "gxlx", paramType = "path"),
    })
    public void handleQswspzResponse(@RequestBody YrbQswspzhqDTO yrbQswspzhqDTO, @PathVariable(value = "xmid") String xmid,
                                     @PathVariable(value="gxlx") String gxlx){
        bdcSwService.handleQswspzResponse(yrbQswspzhqDTO, xmid, gxlx);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "处理保存契税联系单查询结果", notes = "处理保存契税联系单查询结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "yrbQslxdhqDTO", value = "契税联系单查询结果", required = true, dataType = "YrbQslxdhqDTO", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "xmid", paramType = "path"),
    })
    public void handleQslxdResponse(@RequestBody YrbQslxdhqDTO yrbQslxdhqDTO, @PathVariable(value = "xmid")String xmid) {
        bdcSwService.handleQslxdResponse(yrbQslxdhqDTO, xmid);
    }

    /**
     * @param gzlslid@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 接口5推确认结果
     * @date : 2022/9/21 11:48
     */
    @Override
    public void qrswjg(@RequestParam(value = "gzlslid") String gzlslid) {
        bdcSwService.qrswjg(gzlslid);
    }

    /**
     * @param gzlslid
     * @param htbh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取税务完税信息
     * @date : 2022/9/21 14:05
     */
    @Override
    public void swWsxx(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "htbh") String htbh) {
        bdcSwService.swWsxx(gzlslid, htbh);
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 一窗受理获取税票
     * @date : 2022/9/21 14:23
     */
    @Override
    public String getSwSp(@RequestParam(value = "gzlslid") String gzlslid) {
        return bdcSwService.getSwSp(gzlslid);
    }

    /**
     * @param bdcSwxxQOStr 查询参数
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 获取税票信息列表
     */
    @Override
    public Page<BdcSwxxVO> listSwxxByPage(Pageable pageable, @RequestParam(name = "bdcSwxxQOStr", required = false) String bdcSwxxQOStr) {
        return bdcSwService.listSwxxByPage(pageable, bdcSwxxQOStr);
    }

    /**
     * @description 获取税费信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/9/23 10:07
     * @param bdcSwxxQO
     * @return Object
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取税费信息", notes = "获取税费信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSwxxQO", value = "税务信息查询QO", required = true, dataType = "BdcSwxxQO", paramType = "body")
    })
    public Object getYhsfxx(@RequestBody BdcSwxxQO bdcSwxxQO) {
        return bdcSwService.getYhsfxx(bdcSwxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "通知税务任务状态", notes = "通知税务任务状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSwxxQO", value = "税务信息查询QO", required = true, dataType = "BdcSwxxQO", paramType = "body")
    })
    public Object tzSwRwzt(@RequestBody BdcSwxxQO bdcSwxxQO) {
        return bdcSwService.tzSwRwzt(bdcSwxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "房产交易信息申报信息", notes = "房产交易信息申报信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSwxxQO", value = "税务信息查询QO", required = true, dataType = "BdcSwxxQO", paramType = "body")
    })
    public void getSwxxSbdwj(@RequestBody BdcSwxxQO bdcSwxxQO) {
        bdcSwService.getSwxxSbdwj(bdcSwxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取房产交易待缴款信息清单", notes = "获取房产交易待缴款信息清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSwxxQO", value = "税务信息查询QO", required = true, dataType = "BdcSwxxQO", paramType = "body")
    })
    public List<BdcSlYjkxxDO> getFcjyJkxxQd(@RequestBody BdcSwxxQO bdcSwxxQO) {
        return bdcSwService.getFcjyJkxxQd(bdcSwxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "生成缴款信息二维码内容", notes = "生成缴款信息二维码内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSwxxQO", value = "税务信息查询QO", required = true, dataType = "BdcSwxxQO", paramType = "body")
    })
    public Object getFcjyJkEwm(@RequestBody BdcSwxxQO bdcSwxxQO) {
        return bdcSwService.getFcjyJkEwm(bdcSwxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取税务缴款状态", notes = "获取税务缴款状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSwxxQO", value = "税务信息查询QO", required = true, dataType = "BdcSwxxQO", paramType = "body")
    })
    public YrbEwmjkxxResponse getSwJkzt(@RequestBody BdcSwxxQO bdcSwxxQO) {
        return bdcSwService.getSwJkzt(bdcSwxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取税务申报状态", notes = "获取税务申报状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSwxxQO", value = "税务信息查询QO", required = true, dataType = "BdcSwxxQO", paramType = "body")
    })
    public CommonResponse hqswsbzt(@RequestBody BdcSwxxQO bdcSwxxQO) {
        return bdcSwService.hqswsbzt(bdcSwxxQO);
    }
}
