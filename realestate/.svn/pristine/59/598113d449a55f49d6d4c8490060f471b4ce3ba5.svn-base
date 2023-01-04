package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcSdqghDO;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.response.SdqHyResponseDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztResponseDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztUpdateHyDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqxxDTO;
import cn.gtmap.realestate.common.core.enums.BdcSdqEnum;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcSdqywQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcSdqghRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.inquiry.service.BdcSdqghService;
import cn.gtmap.realestate.inquiry.web.main.BaseController;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/10/31
 * @description 查询不动产信息接口
 */
@RestController
@Api(tags = "业务项目信息服务接口")
public class BdcSdqghRestRestController extends BaseController implements BdcSdqghRestService {
    @Autowired
    private BdcSdqghService bdcSdqghService;
    /**
     * 水查询过户方法，是否查询
     */
    @Value("${shuicxgh.sfcx:false}")
    private Boolean shuisfcx;

    /**
     * 电查询过户方法，是否查询
     */
    @Value("${diancxgh.sfcx:false}")
    private Boolean diansfcx;

    /**
     * 气查询过户方法，是否查询
     */
    @Value("${shuicxgh.sfcx:false}")
    private Boolean qisfcx;

    /**
     *@author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     *@param bdcSdqghDO
     *@description 新增水电气过户信息
     */
    @ApiOperation(value = "新增水电气过户信息")
    @ApiImplicitParam(name = "bdcSdqghDO", value = "水电气过户对象",dataType = "bdcSdqghDO",paramType = "body")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Override
    public Integer insertSdqghDO(@RequestBody BdcSdqghDO bdcSdqghDO){
        return bdcSdqghService.insertSdqghDO(bdcSdqghDO);
    }

    /**
     * 通过传入参数返回不动产水电气业务
     *
     * @param bdcSdqywQO
     * @return
     */
    @ApiOperation(value = "查询水电气业务信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcSdqghDO> querySdqywOrder(@RequestBody BdcSdqywQO bdcSdqywQO) {
        bdcSdqywQO.setSortParameter("ID ASC");
        return bdcSdqghService.querySdqyw(bdcSdqywQO);
    }

    @ApiOperation(value = "查询水电气业务信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcSdqghDO> querySdqyw(@RequestBody BdcSdqywQO bdcSdqywQO) {
        return bdcSdqghService.querySdqyw(bdcSdqywQO);
    }

    /**
     * 水过户
     * @author  <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param   processInsId
     * @description 登簿成功后把水的业务过户
     */
    @ApiOperation(value = "登簿成功后把水的业务过户")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public boolean shuigh(@PathVariable("processInsId") String processInsId,
                          @PathVariable("isOneWebSource") String isOneWebSource){
        return bdcSdqghService.shuigh(processInsId,isOneWebSource);
    }

    /**
     * 电过户
     * @author  <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param   processInsId
     * @description 登簿成功后把电的业务过户
     */
    @ApiOperation(value = "登簿成功后把电的业务过户")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public boolean diangh(@PathVariable("processInsId") String processInsId,
                          @PathVariable("isOneWebSource") String isOneWebSource){
        return bdcSdqghService.diangh(processInsId,isOneWebSource);
    }

    /**
     * 气过户
     * @author  <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param   processInsId
     * @description 登簿成功后把气的业务过户
     */
    @ApiOperation(value = "登簿成功后把气的业务过户")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public boolean qigh(@PathVariable("processInsId") String processInsId,
                        @PathVariable("isOneWebSource") String isOneWebSource){
        return bdcSdqghService.qigh(processInsId,isOneWebSource);
    }

    /**
     * 气 查询是否满足过户条件
     * @return
     */
    @ApiOperation(value = "气 查询是否满足过户条件")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Object getQiZt(@RequestBody BdcSdqywQO bdcSdqywQO) {
        if (StringUtils.isAnyBlank(bdcSdqywQO.getConsno(), bdcSdqywQO.getGzlslid(), bdcSdqywQO.getRqlx())) {
            throw new MissingArgumentException("consNo, gzlslid, rqlx");
        }
        return bdcSdqghService.getQiZt(bdcSdqywQO);
    }

    /**
     * 气 录办理的户号，和3.0件关联上
     * @return
     */
    @ApiOperation(value = "录办理的户号，和3.0件关联上")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Object saveData(@RequestBody BdcSdqywQO bdcSdqywQO) {
        return bdcSdqghService.saveData(bdcSdqywQO);
    }

    /**
     * 气过户
     * @author  <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param   processInsId
     * @description 登簿成功后把气的业务过户
     */
    @ApiOperation(value = "查询一窗打印操作的登记库的水电气业务数据")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<Map> getSdqSqbYwDyData(@PathVariable("processInsId") String processInsId){
        return bdcSdqghService.getSdqSqbYwDyData(processInsId);
    }

    /**
     *@author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     *@param bdcSdqBlztUpdateHyDTO
     *@description 更新水电气过户核验信息
     */
    @ApiOperation(value = "更新水电气过户核验信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void updateSdqghhy(@RequestBody BdcSdqBlztUpdateHyDTO bdcSdqBlztUpdateHyDTO){
        bdcSdqghService.updateSdqghhy(bdcSdqBlztUpdateHyDTO);
    }

    @ApiOperation(value = "更新水电气过户状态")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcSdqBlztResponseDTO updateSdqBlzt(@RequestBody BdcSdqBlztRequestDTO bdcSdqBlztRequestDTO){
        return bdcSdqghService.updateSdqBlzt(bdcSdqBlztRequestDTO);
    }

    /**
     * @param processInsId 工作流实例ID
     * @return
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 电过户核验，过户前查询是否可以过户
     */
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CommonResponse commonDianghhy(@PathVariable("processInsId") String processInsId) {
        return bdcSdqghService.commonDianghhy(processInsId);
    }

    /**
     * @param processInsId 工作流实例ID
     * @param qlrxxsjly    权利人信息数据来源 默认匹配户主信息 1:匹配户主获取权利人 2读取业务所有的权利人
     * @param appendSign   权利人拼接字符
     * @param fjxxsjly     附件数据来源 默认为0 0：不传附件 1：电子证照
     * @return 过户结果
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 电过户请求, 先查询后过户
     */
    @ApiOperation(value = "电业务过户请求，先查询后过户")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CommonResponse commonDianCxgh(@PathVariable("processInsId") String processInsId,
                                         @RequestParam(name = "qlrxxsjly", required = false) Integer qlrxxsjly,
                                         @RequestParam(name = "appendSign", required = false) String appendSign,
                                         @RequestParam(name = "fjxxsjly", required = false) Integer fjxxsjly) {
        CommonResponse commonResponse = new CommonResponse();
        if (diansfcx) {
            // 查询
            commonResponse = bdcSdqghService.commonDianghhy(processInsId);
        }
        if (!diansfcx || commonResponse.isSuccess()) {
            // 过户
            commonResponse = bdcSdqghService.commonDiangh(processInsId, qlrxxsjly, appendSign, fjxxsjly);
        }
        return commonResponse;
    }

    /**
     * @param processInsId 工作流实例ID
     * @param qlrxxsjly    权利人信息数据来源 默认匹配户主信息 1:匹配户主获取权利人 2读取业务所有的权利人
     * @param appendSign   权利人拼接字符
     * @param fjxxsjly     附件数据来源 默认为0 0：不传附件 1：电子证照
     * @return 过户结果
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 水过户请求, 先查询后过户
     */
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CommonResponse commonShuiCxgh(@PathVariable("processInsId") String processInsId,
                                         @RequestParam(name = "qlrxxsjly", required = false) Integer qlrxxsjly,
                                         @RequestParam(name = "appendSign", required = false) String appendSign,
                                         @RequestParam(name = "fjxxsjly", required = false) Integer fjxxsjly) {
        CommonResponse commonResponse = new CommonResponse();
        if (shuisfcx) {
            // 查询
            commonResponse = bdcSdqghService.commonShuighhy(processInsId);
        }
        if (!shuisfcx || commonResponse.isSuccess()) {
            // 过户
            commonResponse = bdcSdqghService.commonShuigh(processInsId, qlrxxsjly, appendSign, fjxxsjly);
        }
        return commonResponse;
    }

    /**
     * @param processInsId 工作流实例ID
     * @param qlrxxsjly    权利人信息数据来源 默认匹配户主信息 1:匹配户主获取权利人 2读取业务所有的权利人
     * @param appendSign   权利人拼接字符
     * @param fjxxsjly     附件数据来源 默认为0 0：不传附件 1：电子证照
     * @return 过户结果
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 气过户请求, 先查询后过户
     */
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CommonResponse commonQiCxgh(@PathVariable("processInsId") String processInsId,
                                       @RequestParam(name = "qlrxxsjly", required = false) Integer qlrxxsjly,
                                       @RequestParam(name = "appendSign", required = false) String appendSign,
                                       @RequestParam(name = "fjxxsjly", required = false) Integer fjxxsjly) {
        CommonResponse commonResponse = new CommonResponse();
        if (qisfcx) {
            // 查询
            commonResponse = bdcSdqghService.commonQighhy(processInsId);
        }
        if (!qisfcx || commonResponse.isSuccess()) {
            // 过户
            commonResponse = bdcSdqghService.commonQigh(processInsId, qlrxxsjly, appendSign, fjxxsjly);
        }
        return commonResponse;
    }


    @ResponseStatus(HttpStatus.OK)
    @Override
    public CommonResponse commonDiangh(@PathVariable("processInsId") String processInsId,
                                       @RequestParam(name = "qlrxxsjly", required = false) Integer qlrxxsjly,
                                       @RequestParam(name = "appendSign", required = false) String appendSign,
                                       @RequestParam(name = "fjxxsjly", required = false) Integer fjxxsjly){
        return bdcSdqghService.commonDiangh(processInsId,qlrxxsjly,appendSign, fjxxsjly);

    }



    @ApiOperation(value = "气业务过户请求")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CommonResponse commonQigh(@PathVariable("processInsId") String processInsId,
                                     @RequestParam(name = "qlrxxsjly", required = false) Integer qlrxxsjly,
                                     @RequestParam(name = "appendSign", required = false) String appendSign,
                                     @RequestParam(name = "fjxxsjly", required = false) Integer fjxxsjly){
        return bdcSdqghService.commonQigh(processInsId, qlrxxsjly, appendSign, fjxxsjly);
    }

    @ApiOperation(value = "电过户附件推送")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CommonResponse commonDianghFjcl(@PathVariable("processInsId") String processInsId){
        return bdcSdqghService.commonDianghFjcl(processInsId);
    }

    @ApiOperation(value = "电过户请求(工作流事件,业务信息+附件推送)")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CommonResponse commonDianghWorkflow(@PathVariable("processInsId") String processInsId,@RequestParam(name = "qlrxxsjly", required = false) Integer qlrxxsjly,@RequestParam(name = "appendSign", required = false) String appendSign){
        CommonResponse commonResponse =commonDiangh(processInsId,qlrxxsjly,appendSign,0);
        if(commonResponse.isSuccess()){
            //附件推送
            return commonDianghFjcl(processInsId);
        }
        return commonResponse;

    }

    @ApiOperation(value = "保存水电气过户信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void saveSdq(@PathVariable("processInsId") String gzlslid,
                        @RequestBody List<BdcSdqghDO> bdcSdqghDOS,
                        @RequestParam(value = "needHz", required = false) Boolean needHz){
        bdcSdqghService.saveSdq(gzlslid, bdcSdqghDOS, needHz);

    }

    @ApiOperation(value = "查询水电气信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcSdqxxDTO querySdqxx(@PathVariable("processInsId") String gzlslid){
        return bdcSdqghService.querySdqxx(gzlslid);

    }

    @ApiOperation(value = "电推送过户撤销")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CommonResponse commonDianghCancel(@PathVariable("processInsId") String processInsId){
        return bdcSdqghService.commonDianghCancel(processInsId);

    }

    @ApiOperation(value = "气推送过户撤销")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CommonResponse commonQighCancel(@PathVariable("processInsId") String processInsId){
        return bdcSdqghService.commonQighCancel(processInsId);

    }

    @ApiOperation(value = "过户申请前查询状态，是否欠费")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CommonResponse commonDianZt(@PathVariable("processInsId") String processInsId,@PathVariable("consNo") String consNo){
        return bdcSdqghService.commonDianZt(processInsId, consNo);

    }

    @ApiOperation(value = "过户办理状态查询")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CommonResponse commonDianGhblzt(@PathVariable("processInsId") String processInsId,@PathVariable("consNo") String consNo){
        return bdcSdqghService.commonDianGhblzt(processInsId, consNo);
    }

    @ApiOperation(value = "水业务过户请求")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CommonResponse commonShuigh(@PathVariable("processInsId") String processInsId,
                                       @RequestParam(name = "qlrxxsjly", required = false) Integer qlrxxsjly,
                                       @RequestParam(name = "appendSign", required = false) String appendSign,
                                       @RequestParam(name = "fjxxsjly", required = false) Integer fjxxsjly){
        // 推送业务
        CommonResponse commonResponse = bdcSdqghService.commonShuigh(processInsId,qlrxxsjly,appendSign,fjxxsjly);
        if(commonResponse.isSuccess()){
            //附件推送
            return bdcSdqghService.commonShuighFjcl(processInsId);
        }
        return commonResponse;

    }

    @ApiOperation(value = "水推送过户撤销")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CommonResponse commonShuighCancel(@PathVariable("processInsId") String processInsId){
        return bdcSdqghService.commonShuighCancel(processInsId);

    }

    @ApiOperation(value = "舒城水推送过户")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CommonResponse shuchengShuigh(@PathVariable("processInsId") String processInsId) {
        return bdcSdqghService.shuchengShuigh(processInsId);
    }

    @ApiOperation(value = "舒城水过户登记簿数据")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CommonResponse<String> shuchengShuiDjbData(@PathVariable(name = "processInsId") String processInsId) {
        return bdcSdqghService.shuchengShuiDjbData(processInsId);
    }

}
