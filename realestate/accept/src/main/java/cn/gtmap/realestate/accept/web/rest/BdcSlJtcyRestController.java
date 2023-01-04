package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlJtcyService;
import cn.gtmap.realestate.accept.service.BdcJtcyCxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJtcyDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcFileBase64DTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSqrDTO;
import cn.gtmap.realestate.common.core.dto.accept.CompareHyxxResultDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJtcyQO;
import cn.gtmap.realestate.common.core.qo.accept.CompareHyxxQO;
import cn.gtmap.realestate.common.core.qo.accept.GetJtcyxxQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlJtcyRestService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcJtcyCxYmxxVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 不动产受理家庭成员服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-06-26 17:13
 **/
@RestController
@Api(tags = "不动产受理家庭成员服务")
public class BdcSlJtcyRestController extends BaseController implements BdcSlJtcyRestService {
    @Autowired
    BdcSlJtcyService bdcSlJtcyService;

    @Autowired
    BdcJtcyCxService bdcJtcyCxService;

    /**
     * @param bdcSlJtcyDO 不动产受理家庭成员
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存不动产受理家庭成员
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "保存不动产受理家庭成员信息", notes = "保存不动产受理家庭成员信息服务")
    @ApiImplicitParam(name = "bdcSlJtcyDO", value = "不动产受理家庭成员", required = true, dataType = "BdcSlJtcyDO")
    public Integer updateBdcSlJtcy(@RequestBody BdcSlJtcyDO bdcSlJtcyDO) {
        return bdcSlJtcyService.updateBdcSlJtcy(bdcSlJtcyDO);
    }

    /**
     * @param sqrid 申请人id
     * @return 不动产受理家庭成员集合
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据申请人id获取不动产受理家庭成员信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据申请人id获取不动产受理家庭成员信息", notes = "根据申请人id获取不动产受理家庭成员信息服务")
    @ApiImplicitParam(name = "sqrid", value = "申请人id", required = true, dataType = "String", paramType = "path")
    public List<BdcSlJtcyDO> listBdcSlJtcyBySqrid(@PathVariable(value = "sqrid") String sqrid) {
        return bdcSlJtcyService.listBdcSlJtcyBySqrid(sqrid);
    }

    /**
     * @param jtcyid 家庭成员id
     * @return 不动产受理家庭成员
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据家庭成员id获取不动产受理家庭成员
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据家庭成员id获取不动产受理家庭成员", notes = "根据家庭成员id获取不动产受理家庭成员服务")
    @ApiImplicitParam(name = "jtcyid", value = "家庭成员id", required = true, dataType = "String", paramType = "path")
    public BdcSlJtcyDO queryBdcSlJtcyByJtcyid(@PathVariable(value = "jtcyid") String jtcyid) {
        return bdcSlJtcyService.queryBdcSlJtcyByJtcyid(jtcyid);
    }

    /**
     * @param bdcSlJtcyDO 不动产受理家庭成员
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产受理家庭成员
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增不动产受理家庭成员", notes = "新增不动产受理家庭成员服务")
    @ApiImplicitParam(name = "bdcSlJtcyDO", value = "不动产受理家庭成员信息", required = true, dataType = "BdcSlJtcyDO")
    public BdcSlJtcyDO insertBdcSlJtcy(@RequestBody BdcSlJtcyDO bdcSlJtcyDO) {
        return bdcSlJtcyService.insertBdcSlJtcy(bdcSlJtcyDO);
    }

    /**
     * @param jtcyid 家庭成员id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理家庭成员
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据家庭成员id删除不动产受理家庭成员", notes = "根据家庭成员id删除不动产受理家庭成员服务")
    @ApiImplicitParam(name = "jtcyid", value = "家庭成员id", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcSlJtcyByJtcyid(@PathVariable(value = "jtcyid") String jtcyid) {
        return bdcSlJtcyService.deleteBdcSlJtcyByJtcyid(jtcyid);
    }

    /**
     * @param sqrid 申请人id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理家庭成员
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据申请人id删除不动产受理家庭成员", notes = "根据申请人id删除不动产受理家庭成员服务")
    @ApiImplicitParam(name = "sqrid", value = "申请人id", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcSlJtcyBySqrid(@PathVariable(value = "sqrid") String sqrid) {
        return bdcSlJtcyService.deleteBdcSlJtcyBySqrid(sqrid);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据申请人ID集合和证件号批量删除不动产受理家庭成员", notes = "根据申请人ID集合和证件号批量删除不动产受理家庭成员服务")
    @ApiImplicitParam(name = "sqridList", value = "申请人id集合", required = true, dataType = "String", paramType = "query")
    public void deleteBatchBdcSlJtcy(@RequestBody List<String> sqridList,@RequestParam(value = "zjh") String zjh){
        bdcSlJtcyService.deleteBatchBdcSlJtcy(sqridList, zjh);



    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "婚姻/公安接口调用", notes = "婚姻/公安接口调用服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "getJtcyxxQO", value = "获取家庭成员接口入参", required = true, dataType = "GetJtcyxxQO")})
    public Object getHygaxx(@RequestBody GetJtcyxxQO getJtcyxxQO){
        return bdcSlJtcyService.getHygaxx(getJtcyxxQO);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "调用接口获取家庭成员信息", notes = "调用接口获取家庭成员信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "getJtcyxxQO", value = "获取家庭成员接口入参", required = true, dataType = "GetJtcyxxQO")})
    public List<BdcSlJtcyDO> getJtcyxx(@RequestBody GetJtcyxxQO getJtcyxxQO){
        //通过接口获取并组织家庭成员信息
        List<BdcSlJtcyDO> bdcSlJtcyDOList =bdcSlJtcyService.getJtcyxx(getJtcyxxQO);
        //家庭成员信息入库
        return bdcSlJtcyService.saveJkJtcyxx(bdcSlJtcyDOList,getJtcyxxQO);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "调用接口获取家庭成员信息（南通）", notes = "调用接口获取家庭成员信息服务（南通）")
    @ApiImplicitParams({@ApiImplicitParam(name = "sqrmc", value = "申请人名称", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "zjh", value = "证件号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sqrid", value = "申请人ID", dataType = "String", paramType = "path")})
    public void getJtcyxxNt(@PathVariable(value = "sqrmc")String sqrmc,@PathVariable(value = "zjh")String zjh,@PathVariable(value = "sqrid")String sqrid){
        if(StringUtils.isBlank(sqrmc) ||StringUtils.isBlank(zjh) ||StringUtils.isBlank(sqrid)){
            throw new MissingArgumentException("查询参数缺失！");
        }
        bdcSlJtcyService.getJtcyxxNt(sqrmc, zjh, sqrid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = " 调用接口获取当前项目下所有申请人的家庭成员信息（南通版本）", notes = "调用接口获取当前项目下所有申请人的家庭成员信息（南通版本）")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "qlrlb", value = "权利人类别", required = true, dataType = "String", paramType = "path")})
    public void getJtcyxxByXmidNt(@PathVariable(value = "xmid")String xmid, @PathVariable(value = "qlrlb")String qlrlb) {
        if(StringUtils.isBlank(xmid)){
            throw new MissingArgumentException("缺失参数项目ID！");
        }
        this.bdcSlJtcyService.getJtcyxxByXmidNt(xmid, qlrlb);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "同步家庭成员信息至其他户", notes = "同步家庭成员信息至其他户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlSqrDTO", value = "不动产受理申请人DTO", required = true, dataType = "BdcSlSqrDTO", paramType = "body"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    public void syncJtcyxx(@RequestBody BdcSlSqrDTO bdcSlSqrDTO, @RequestParam(value = "gzlslid") String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("缺失参数工作流实例ID！");
        }
        this.bdcSlJtcyService.syncJtcyxx(bdcSlSqrDTO,gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询家庭成员信息", notes = "查询家庭成员信息服务")
    @ApiImplicitParam(name = "bdcSlJtcyQO", value = "查询模型", required = true, dataType = "BdcSlJtcyQO", paramType = "query")
    public List<BdcSlJtcyDO> listBdcSlJtcy(@RequestBody BdcSlJtcyQO bdcSlJtcyQO){
        return bdcSlJtcyService.listBdcSlJtcy(bdcSlJtcyQO);

    }

    /**
     * @param getJtcyxxQO 家庭成员查询参数
     * @return 提示信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 民政婚姻信息比对
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "民政婚姻信息比对", notes = "民政婚姻信息比对服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "getJtcyxxQO", value = "获取家庭成员接口入参", required = true, dataType = "GetJtcyxxQO")})
    public Map<String,String> compareHyxx(@RequestBody GetJtcyxxQO getJtcyxxQO){
        return bdcSlJtcyService.compareHyxx(getJtcyxxQO);
    }

    /**
     * @param compareHyxxQO
     * @return 提示信息
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 南通一体化民政婚姻子女信息比对
     */
    @Override
    public CompareHyxxResultDTO compareHyznxx(@RequestBody CompareHyxxQO compareHyxxQO) {
        return bdcSlJtcyService.compareHyznxx(compareHyxxQO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "导入婚姻比对信息", notes = "导入婚姻比对信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "resultDTO", value = "婚姻比对信息", required = true, dataType = "CompareHyxxResultDTO")})
    public void drhybdxx(@RequestBody CompareHyxxResultDTO resultDTO){
        bdcSlJtcyService.drhybdxx(resultDTO);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "南通查询婚姻信息，并生成查询文件PDF", notes = "南通查询婚姻信息，并生成查询文件PDF")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "isFirstHand", value = "是否一手房", required = false, dataType = "boolean", paramType = "query")})
    public void queryHyxxAndGenerateFile(@PathVariable(value = "gzlslid") String gzlslid,@RequestParam(value = "isFirstHand", required = false) boolean isFirstHand) {
        bdcJtcyCxService.queryHyxxAndGenerateFile(gzlslid,isFirstHand);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "南通查询户籍信息，并生成查询文件PDF", notes = "南通查询户籍信息，并生成查询文件PDF")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "isFirstHand", value = "是否一手房", required = false, dataType = "boolean", paramType = "query")})
    public void queryHjxxAndGenerateFile(@PathVariable(value = "gzlslid") String gzlslid,@RequestParam(value = "isFirstHand", required = false) boolean isFirstHand) {
        bdcJtcyCxService.queryHjxxAndGenerateFile(gzlslid,isFirstHand);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "上传家庭成员页面截图图片", notes = "上传家庭成员页面截图图片")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcFileBase64DTO", value = "文件base64DTO", required = true, dataType = "BdcFileBase64DTO", paramType = "body")})
    public void uploadScreenShot(@RequestBody BdcFileBase64DTO bdcFileBase64DTO) {
        bdcJtcyCxService.uploadScreenShot(bdcFileBase64DTO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID，获取家庭成员查询页面信息", notes = "根据工作流实例ID，获取家庭成员查询页面信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    public BdcJtcyCxYmxxVO queryJtcyYmxx(@PathVariable(value="gzlslid") String gzlslid) {
        return this.bdcJtcyCxService.queryJtcyYmxx(gzlslid);
    }
}
