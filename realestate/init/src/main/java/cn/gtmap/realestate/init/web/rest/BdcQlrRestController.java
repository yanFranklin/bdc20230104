package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcDsQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcQlrGroupDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcQlrIdsDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDsQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.rest.init.BdcQlrRestService;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.init.core.service.BdcQlrService;
import cn.gtmap.realestate.init.core.service.BdcQlrtzService;
import cn.gtmap.realestate.init.util.Constants;
import cn.gtmap.realestate.init.util.DozerUtils;
import cn.gtmap.realestate.init.web.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/1
 * @description 查询不动产权利人接口
 */
@RestController
@Api(tags = "不动产权利人信息服务接口")
public class BdcQlrRestController extends BaseController implements BdcQlrRestService {
    @Autowired
    private BdcQlrService bdcQlrService;
    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private BdcQlrtzService bdcQlrtzService;

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcQlrQO
     *@return List<bdcQLrDO>
     *@description 根据查询参数返回不动产权利人信息
     */
    @ApiOperation(value ="查询权利人信息")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求获取成功"),@ApiResponse(code = 500,message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrmc", value = "权利人名称",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "zjh", value = "证件号",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrlb", value = "权利人类别",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrid", value = "权利人id",  dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcQlrDO> listBdcQlr(@RequestBody BdcQlrQO bdcQlrQO){
        if (!CheckParameter.checkAnyParameter(bdcQlrQO,"xmid","qlrmc","zjh","qlrid","dlrmc","dlrzjh","zsid")) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER)+ JSONObject.toJSONString(bdcQlrQO));
        }
        BdcQlrDO bdcQlrDO = new BdcQlrDO();
        dozerUtils.sameBeanDateConvert(bdcQlrQO, bdcQlrDO, false);
        return bdcQlrService.queryBdcQlrByQlrxx(bdcQlrDO,"qlrlb asc,sxh asc");
    }

    @ApiOperation(value ="根据查询参数查询权利人信息，支持按模糊类型查询")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求获取成功"),@ApiResponse(code = 500,message = "请求参数错误")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcQlrQO", value = "不动产权利人QO",  dataType = "BdcQlrQO", paramType = "body"),
    })
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcQlrDO> listBdcQlrWithMhlx(@RequestBody BdcQlrQO bdcQlrQO) {
        if (!CheckParameter.checkAnyParameter(bdcQlrQO,"xmid","qlrmc","zjh","qlrid","dlrmc","dlrzjh","zsid")) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER)+ JSONObject.toJSONString(bdcQlrQO));
        }
        return this.bdcQlrService.queryBdcQlrWithMhlx(bdcQlrQO);
    }

    /**
     *@author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     *@param bdcDsQlrQO
     *@return List<bdcDsQLrDO>
     *@description 根据查询参数返回不动产权利人信息
     */
    @ApiOperation(value ="查询第三权利人信息")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求获取成功"),@ApiResponse(code = 500,message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrmc", value = "权利人名称",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "zjh", value = "证件号",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrlb", value = "权利人类别",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrid", value = "权利人id",  dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcDsQlrDO> listBdcDsQlr(@RequestBody BdcDsQlrQO bdcDsQlrQO){
        if (!CheckParameter.checkAnyParameter(bdcDsQlrQO,"xmid","qlrmc","zjh","qlrid","dlrmc","dlrzjh")) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER)+ JSONObject.toJSONString(bdcDsQlrQO));
        }
        BdcDsQlrDO bdcDsQlrDO = new BdcDsQlrDO();
        dozerUtils.sameBeanDateConvert(bdcDsQlrQO, bdcDsQlrDO, false);
        return bdcQlrService.queryBdcDsQlr(bdcDsQlrDO,"qlrlb asc,sxh asc");
    }

    /**
     * 根据 gzlslid 返回全部的不动产权利人信息
     * @param gzlslid 工作流实例 ID
     * @return List<bdcQLrDO> 权利人集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ApiOperation(value = "根据 gzlslid 查询权利人信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "gzlslid", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "qlrlb", value = "qlrlb", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "djxl", value = "djxl", dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcQlrDO> listAllBdcQlr(@PathVariable("gzlslid") String gzlslid, @RequestParam(value = "qlrlb",required = false) String qlrlb, @RequestParam(value = "djxl",required = false) String djxl) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return bdcQlrService.listAllBdcQlr(gzlslid,null,qlrlb,djxl,null);
    }

    /**
     * 根据 slbh 返回全部的不动产权利人信息
     * @param slbh
     * @param qlrlb
     * @return List<bdcQLrDO> 权利人集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    @ApiOperation(value = "根据 slbh 查询权利人信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "slbh", value = "slbh", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "qlrlb", value = "qlrlb", dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcQlrDO> listAllBdcQlrBySlbh(@PathVariable("slbh") String slbh, @RequestParam(value = "qlrlb", required = false) String qlrlb) {
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return bdcQlrService.listAllBdcQlr(null,slbh,qlrlb,null,null);
    }

    /**
     * 根据 bdcdyh 返回全部的现势产权权利人
     * @param bdcdyh@return List<bdcQLrDO> 权利人集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    @ApiOperation(value = "根据 bdcdyh 返回全部的现势产权权利人")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "bdcdyh", dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcQlrDO> listCqQlr(@PathVariable("bdcdyh") String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return bdcQlrService.listXsCqQlr(bdcdyh);
    }

    /**
     * 根据 qlrid 批量删除权利人信息
     *
     * @param qlridlist 权利人id 集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ApiOperation(value = "根据 qlrid 批量删除权利人信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "qlridlist", value = "qlridlist", dataType = "List", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void deleteBatchQlr(@RequestBody List<String> qlridlist) {
        bdcQlrService.deleteBatchQlr(qlridlist);
    }

    /**
     * 根据 qlridlist 批量删除数据，根据 xmid 循环新增 bdcQlrDO 数据
     *
     * @param bdcQlrIdsDTO 权利人id DTO 对象
     * @return {List<BdcQlrDO>}
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ApiOperation(value = "根据 qlrid 批量更新权利人信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcQlrIdsDTO", value = "bdcQlrIdsDTO", dataType = "BdcQlrIdsDTO", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcQlrDO> updateBatchQlr(@RequestBody BdcQlrIdsDTO bdcQlrIdsDTO) {
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        List<String> qlridlist = bdcQlrIdsDTO.getQlridlist();
        List<String> xmidlist = bdcQlrIdsDTO.getXmidlist();
        BdcQlrDO bdcQlrDO = bdcQlrIdsDTO.getBdcQlrDO();
        if (CollectionUtils.isEmpty(qlridlist) || CollectionUtils.isEmpty(xmidlist) || bdcQlrDO == null) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        List<List> partqlridList= ListUtils.subList(qlridlist,500);
        for(List data:partqlridList){
            // 根据 qlridList 批量删除数据
            bdcQlrService.deleteBatchQlr(data);
        }
        List<List> partxmidList= ListUtils.subList(xmidlist,500);
        for(List data:partxmidList){
            // 新增 qlridlist.size() 条 bdcQlrDO 数据
            bdcQlrDOList.addAll(bdcQlrService.insertBdcQlrByNum(bdcQlrDO, data));
        }
        return bdcQlrDOList;
    }

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcQlrDO
     *@description 插入新的权利人信息
     */
    @ApiOperation(value = "新增权利人信息")
    @ApiImplicitParam(name = "bdcQlrDO", value = "权利人对象",dataType = "BdcQlrDO",paramType = "body")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Override
    public BdcQlrDO insertBdcQlr(@RequestBody BdcQlrDO bdcQlrDO){
        bdcQlrService.insertBdcQlr(bdcQlrDO);
        return bdcQlrDO;
    }

    /**
     *@author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     *@param bdcDsQlrDO
     *@description 插入新的第三权利人信息
     */
    @ApiOperation(value = "新增第三权利人信息")
    @ApiImplicitParam(name = "bdcDsQlrDO", value = "第三权利人对象",dataType = "BdcDsQlrDO",paramType = "body")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Override
    public BdcDsQlrDO insertBdcDsQlr(@RequestBody BdcDsQlrDO bdcDsQlrDO){
        bdcQlrService.insertBdcDsQlr(bdcDsQlrDO);
        return bdcDsQlrDO;
    }

    public int insertBdcDsQlrPl(@RequestBody List<BdcDsQlrDO> bdcDsQlrDOList){
        return bdcQlrService.insertBdcDsQlrPl(bdcDsQlrDOList);

    }

    /**
     *@author <a href="mailto:chenyucehng@gtmap.cn">chenyucehng</a>
     *@param bdcDsQlrDO
     *@description 更新权利人信息
     */
    @ApiOperation(value = "更新第三权利人信息")
    @ApiImplicitParam(name = "bdcDsQlrDO",value = "权利人对象",dataType = "BdcDsQlrDO",paramType = "body")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public int updateBdcDsQlr(@RequestBody BdcDsQlrDO bdcDsQlrDO){
        return bdcQlrService.updateBdcDsQlr(bdcDsQlrDO);
    }

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcQlrDO
     *@description 更新权利人信息
     */
    @ApiOperation(value = "更新权利人信息")
    @ApiImplicitParam(name = "bdcQlrDO",value = "权利人对象",dataType = "BdcQlrDO",paramType = "body")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public int updateBdcQlr(@RequestBody BdcQlrDO bdcQlrDO){
       return bdcQlrService.updateBdcQlr(bdcQlrDO);
    }

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param qlrid
     *@description 删除权利人信息
     */
    @ApiOperation(value = "删除权利人信息")
    @ApiImplicitParam(name = "qlrid", value = "权利人ID",dataType = "String",paramType = "path")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Override
    public void deleteBdcQlr(@PathVariable("qlrid") String qlrid){
        bdcQlrService.deleteBdcQlrByQlrId(qlrid);
    }

    @ApiOperation(value = "根据项目ID或者权利人类别删除权利人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid",value = "项目ID",dataType = "String",paramType = "path"),
            @ApiImplicitParam(name = "qlrlb",value = "权利人类别",dataType = "String",paramType = "query")})
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Override
    public void delQlr(@PathVariable("xmid") String xmid, @RequestParam(value = "qlrlb",required = false) String qlrlb){
       bdcQlrService.deleteQlr(xmid,qlrlb);
    }

    /**
     *@author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     *@param qlrid
     *@description 删除第三权利人信息
     */
    @ApiOperation(value = "删除第三权利人信息")
    @ApiImplicitParam(name = "qlrid", value = "权利人ID",dataType = "String",paramType = "path")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Override
    public void deleteBdcDsQlr(@PathVariable("qlrid") String qlrid){
        bdcQlrService.deleteBdcDsQlrByQlrId(qlrid);
    }

    @ApiOperation(value = "根据xmid和权利人类别删除第三权利人信息")
    @ApiImplicitParam(name = "xmid", value = "项目ID",dataType = "String",paramType = "path")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Override
    public void delBdcDsQlr(@PathVariable("xmid") String xmid, @RequestParam(value = "qlrlb",required = false) String qlrlb){
        bdcQlrService.delBdcDsQlr(xmid, qlrlb);

    }

    /**
     * 批量业务
     *@param bdcQlrDO
      * @param processInsId
     * @param djxl         非必填
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 批量插入新的权利人信息(包括权利人和义务人,根据权利人对象做判断)
     */
    @ApiOperation(value = "批量新增权利人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId",value = "工作流实例ID",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "bdcQlrDO",value = "权利人对象",dataType = "BdcQlrDO",paramType = "body"),
            @ApiImplicitParam(name = "djxl",value = "登记小类",dataType = "String",paramType = "query")})
    @ResponseStatus(code = HttpStatus.CREATED)
    @Override
    public List<BdcQlrDO> insertBatchBdcQlr(@RequestBody BdcQlrDO bdcQlrDO, @RequestParam("processInsId") String processInsId, @RequestParam(value = "djxl", required = false) String djxl) {
        return bdcQlrService.insertBatchQlr(processInsId,djxl,bdcQlrDO);
    }

    @ApiOperation(value = "根据项目ID集合批量插入权利人")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcQlrIdsDTO", value = "bdcQlrIdsDTO", dataType = "BdcQlrIdsDTO", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcQlrDO> insertBatchBdcQlrByXmids(@RequestBody BdcQlrIdsDTO bdcQlrIdsDTO){
        return bdcQlrService.insertBatchBdcQlrByXmids(bdcQlrIdsDTO);



    }

    /**
     * 批量业务
     *
     * @param bdcQlrDO
     * @param processInsId
     * @param djxl         非必填
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 批量更新权利人信息 根据权利人ID获取原权利人对象内的名称、证件号、类别去判定更新
     */
    @ApiOperation(value = "批量更新权利人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId",value = "工作流实例ID",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "bdcQlrDO",value = "权利人对象",dataType = "BdcQlrDO",paramType = "body"),
            @ApiImplicitParam(name = "djxl",value = "登记小类",dataType = "String",paramType = "query")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcQlrDO> updateBatchBdcQlr(@RequestBody BdcQlrDO bdcQlrDO, @RequestParam("processInsId") String processInsId, @RequestParam(value = "djxl", required = false) String djxl) {
        BdcQlrDO yqlr=bdcQlrService.queryBdcQlrByQlrid(bdcQlrDO.getQlrid());
        deleteBdcQlrsByQlrxx(yqlr.getQlrmc(),yqlr.getZjh(),processInsId,yqlr.getQlrlb(),djxl,new ArrayList<>());
        return bdcQlrService.insertBatchQlr(processInsId,djxl,bdcQlrDO);
    }

    /**
     * 批量业务
     * @param bdcQlrList
     * @param processInsId
     * @param qlrlb        1：权利人  2：义务人  空的话包含1和2
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 批量更新权利人信息   删除原来类别人员进行新增操作
     */
    @ApiOperation(value = "批量更新权利人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId",value = "工作流实例ID",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "bdcQlrList",value = "权利人对象集合",dataType = "List<BdcQlrDO>",paramType = "body"),@ApiImplicitParam(name = "qlrlb",value = "权利人类别",dataType = "String",paramType = "query")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcQlrDO> updateBatchBdcQlr(@RequestBody List<BdcQlrDO> bdcQlrList, @RequestParam("processInsId") String processInsId, @RequestParam(value = "qlrlb", required = false) String qlrlb) {
        bdcQlrService.deleteBatchQlr(processInsId, qlrlb);
        return bdcQlrService.insertBatchQlr(processInsId,bdcQlrList);
    }

    /**
     * 批量业务
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param processInsId
     *@param qlrlb 1:权利人  2：义务人 不传递权利人和义务人全删掉
     *@description 批量删除权利人信息
     */
    @ApiOperation(value = "批量删除权利人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId",value = "工作流实例ID",dataType = "String",paramType = "path"),
            @ApiImplicitParam(name = "qlrlb",value = "权利人类别",dataType = "String",paramType = "query")})
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Override
    public void deleteBatchBdcQlr(@PathVariable("processInsId") String processInsId,@RequestParam("qlrlb") String qlrlb) {
        bdcQlrService.deleteBatchQlr(processInsId, qlrlb);
    }

    /**
     * 通过权利人名称和证件号批量删除流程内权利人信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param qlr
     *@param qlrzjh
     *@param qlrlb  1:权利人  2：义务人 不传递权利人和义务人全删掉
     *@param djxl         非必填
     *@param processInsId 工作流实例id
     *@description 通过权利人名称和证件号批量删除流程内权利人信息
     *@return 删除数量
     */
    @ApiOperation(value = "通过权利人名称和证件号批量删除流程内权利人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "qlr",value = "权利人名称",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "qlrzjh",value = "权利人证件号",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "processInsId",value = "工作流实例id",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "qlrlb",value = "权利人类别",dataType = "String",paramType = "query")})
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Override
    public void deleteBdcQlrsByQlrxx(@RequestParam("qlr") String qlr, @RequestParam(value = "qlrzjh",required = false) String qlrzjh, @RequestParam("processInsId") String processInsId, @RequestParam(value = "qlrlb", required = false) String qlrlb, @RequestParam(value = "djxl", required = false) String djxl,@RequestBody List<String> xmidList) {
        //如果人名、工作流实例id没值则抛异常
        if(StringUtils.isBlank(qlr)  || StringUtils.isBlank(processInsId)){
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        BdcQlrDO bdcQlrDO=new BdcQlrDO();
        bdcQlrDO.setQlrlb(qlrlb);
        bdcQlrDO.setQlrmc(qlr);
        bdcQlrDO.setZjh(qlrzjh);
        bdcQlrService.deleteBdcQlrsByQlrxx(processInsId,djxl,bdcQlrDO,xmidList);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param qlrlb 权利人类别
     * @return 权利人或义务人组织
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量发一本证的义务人或者权利人组织
     */
    @ApiOperation(value = "批量发一本证的义务人或者权利人组织")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid",value = "工作流实例ID",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "qlrlb",value = "权利人类别",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "djxl",value = "登记小类",dataType = "String",paramType = "query")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public String queryQlrsYbzs(@RequestParam("gzlslid") String gzlslid, @RequestParam("qlrlb") String qlrlb,@RequestParam(value = "djxl",required = false) String djxl){
        return bdcQlrService.queryQlrsYbzs(gzlslid, qlrlb,djxl);
    }

    /**
     * @param bdcDjxxUpdateQO 登记信息更新对象
     * @return  更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  批量更新不动产权利人
     */
    @ApiOperation(value = "批量更新不动产权利人")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjxxUpdateQO",value = "登记信息更新对象",dataType = "BdcDjxxUpdateQO",paramType = "query")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public int updateBatchBdcQlr(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO) throws Exception{
        return bdcQlrService.updateBatchBdcQlr(bdcDjxxUpdateQO);

    }

    @ApiOperation(value = "对权利人/义务人根据证件号进行分组")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "gzlslid", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "qlrlb", value = "qlrlb", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "djxl", value = "djxl", dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcQlrGroupDTO> groupQlrYwrByZjh(@PathVariable("gzlslid") String gzlslid,
                                                 @RequestParam(value = "qlrlb",required = false) String qlrlb,
                                                 @RequestParam(value = "djxl",required = false) String djxl,
                                                 @RequestBody(required = false) String xmidList){
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return bdcQlrService.groupQlrYwrByZjh(gzlslid, qlrlb, djxl,xmidList);

    }

    @ApiOperation(value = "通过当前权利人获得与其组合同步修改的所有权利人信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "权利人信息", value = "obj", dataType = "JSONObject", paramType = "query"),
            @ApiImplicitParam(name = "修改类型(update:更新,insert:新增，DELETE:删除)", value = "type", dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<JSONObject> listZhBdcQlr(@RequestBody JSONObject obj, @RequestParam(value = "type") String type){
        return bdcQlrService.listZhBdcQlr(obj, type);
    }

    @ApiOperation(value = "通过当前权利人获得与其组合同步修改的所有权利人信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "权利人信息", value = "obj", dataType = "JSONObject", paramType = "query"),
            @ApiImplicitParam(name = "修改类型(update:更新,insert:新增，DELETE:删除)", value = "type", dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcQlrDO> listPlBdcQlr(@RequestBody JSONObject obj, @RequestParam(value = "type") String type){
        BdcQlrDO bdcQlrDO = JSON.parseObject(JSON.toJSONString(obj), BdcQlrDO.class);
        return bdcQlrService.listPlBdcQlr(Arrays.asList(bdcQlrDO), type);
    }

    @ApiOperation(value = "根据条件查询权利信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcQlrDO> listBdcQlrByCondition(@RequestBody BdcQlrDO bdcQlrDO) {
        return bdcQlrService.listBdcQlrByCondition(bdcQlrDO);
    }

    /**
     * @param qlr
     * @param qlrzjh
     * @param processInsId
     * @param qlrlb
     * @param djxl
     * @param xmidList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/2/7 10:29
     */
    @ApiOperation(value = "通过权利人名称和证件号批量删除流程内权利人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "qlr", value = "权利人名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrzjh", value = "权利人证件号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "processInsId", value = "工作流实例id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrlb", value = "权利人类别", dataType = "String", paramType = "query")})
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Override
    public void deleteDsQlrsByQlrxx(@RequestParam("qlr") String qlr, @RequestParam(value = "qlrzjh", required = false) String qlrzjh, @RequestParam("processInsId") String processInsId, @RequestParam(value = "qlrlb", required = false) String qlrlb, @RequestParam(value = "djxl", required = false) String djxl, @RequestBody(required = false) List<String> xmidList) {
        //如果人名、工作流实例id没值则抛异常
        if (StringUtils.isBlank(qlr) || StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        BdcDsQlrDO bdcDsQlrDO = new BdcDsQlrDO();
        bdcDsQlrDO.setQlrlb(qlrlb);
        bdcDsQlrDO.setQlrmc(qlr);
        bdcDsQlrDO.setZjh(qlrzjh);
        bdcQlrService.deleteDsQlrsByQlrxx(processInsId, djxl, bdcDsQlrDO, xmidList);
    }

    @Override
    @ApiOperation(value = "获取权利人特征默认值")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public Integer getQlrtzMrz(@RequestParam(value = "qllx")Integer qllx,@RequestParam(value = "qlrlb")String qlrlb){
        return bdcQlrtzService.getQlrtzMrz(qllx, qlrlb);

    }

    @Override
    @ApiOperation(value = "查询权利人信息")
    public List<BdcQlrDO> listBdcQlrByXmidList(@RequestBody List<String> xmidList, @RequestParam(value = "qlrlb",required = false) String qlrlb){
        return bdcQlrService.listBdcQlrByXmidList(xmidList, qlrlb);

    }

    /**
     * 查询证书（证明）关联的权利人（义务人）信息
     * @param zsid 证书ID
     * @param qlrlb 权利人类别
     * @return {List} 权利人信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ApiOperation(value = "查询证书（证明）关联的权利人（义务人）信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zsid", value = "证书ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrlb", value = "权利人类别", dataType = "String", paramType = "query")})
    public List<BdcQlrDO> listBdcQlrByZsid(@RequestParam("zsid")String zsid, @RequestParam("qlrlb")String qlrlb) {
        return bdcQlrService.listBdcQlrByZsid(zsid, qlrlb);
    }
}
