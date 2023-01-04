package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcLzrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.BdcLzrDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcLzxxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcLzrQO;
import cn.gtmap.realestate.common.core.service.rest.init.BdcLzrRestService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcLzrVO;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.init.core.service.BdcLzrService;
import cn.gtmap.realestate.init.util.Constants;
import cn.gtmap.realestate.init.util.DozerUtils;
import cn.gtmap.realestate.init.web.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 领证人rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-01-17 16:10
 **/
@RestController
@Api(tags = "不动产领证人信息服务接口")
public class BdcLzrRestController extends BaseController implements BdcLzrRestService {

    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    BdcLzrService bdcLzrService;

    /**
     * @param bdcLzrQO 领证人查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询领证人
     * @date : 2020/1/17 16:19
     */
    @Override
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "lzrmc", value = "领证人名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "lzrid", value = "领证人id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qlrid", value = "权利人id", dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcLzrDO> listBdcLzr(@RequestBody BdcLzrQO bdcLzrQO) {
        if (!CheckParameter.checkAnyParameter(bdcLzrQO, "lzrmc", "lzrid", "xmid", "qlrid", "lzrzjh")) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER) + JSONObject.toJSONString(bdcLzrQO));
        }
        BdcLzrDO bdcLzrDO = new BdcLzrDO();
        dozerUtils.sameBeanDateConvert(bdcLzrQO, bdcLzrDO, false);
        return bdcLzrService.queryBdcLzr(bdcLzrDO, "lzrid asc");
    }

    /**
     * @param bdcLzrDO 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增领证人
     * @date : 2020/1/17 16:23
     */
    @ApiOperation(value = "新增领证人信息")
    @ApiImplicitParam(name = "bdcLzrDO", value = "领证人对象", dataType = "BdcLzrDO", paramType = "body")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Override
    public BdcLzrDO insertBdcLzr(@RequestBody BdcLzrDO bdcLzrDO) {
        bdcLzrService.insertBdcLzr(bdcLzrDO);
        return bdcLzrDO;
    }

    @ApiOperation(value = "批量新增领证人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bdcLzrDO", value = "权利人对象", dataType = "BdcLzrDO", paramType = "body"),
            @ApiImplicitParam(name = "djxl", value = "登记小类", dataType = "String", paramType = "query")})
    @ResponseStatus(code = HttpStatus.CREATED)
    @Override
    public List<BdcLzrDO> insertBatchBdcLzr(@RequestBody BdcLzrDO bdcLzrDO, @RequestParam("processInsId") String processInsId, @RequestParam(value = "djxl", required = false) String djxl) {
        return bdcLzrService.insertBatchBdcLzr(bdcLzrDO, processInsId, djxl);
    }

    /**
     * @param bdcLzrDO 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新领证人信息
     * @date : 2020/1/17 16:23
     */
    @ApiOperation(value = "更新领证人信息")
    @ApiImplicitParam(name = "bdcLzrDO", value = "领证人对象", dataType = "BdcLzrDO", paramType = "body")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public int updateBdcLzr(@RequestBody BdcLzrDO bdcLzrDO) {
        return bdcLzrService.updateBdcLzr(bdcLzrDO);
    }

    /**
     * @param lzrid 领证人id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据领证人id删除
     * @date : 2020/1/17 16:23
     */
    @ApiOperation(value = "删除领证人信息")
    @ApiImplicitParam(name = "lzrid", value = "领证人ID", dataType = "String", paramType = "path")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Override
    public void deleteBdcLzr(@PathVariable(name = "lzrid") String lzrid) {
        bdcLzrService.deleteBdcLzr(lzrid);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcLzrDOList]
     * @return void
     * @description 批量删除领证人
     */
    @ApiOperation(value = "批量删除领证人")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcLzrDOList", value = "领证人集合", dataType = "BdcLzrDO", paramType = "body")})
    @Override
    public void batchDeleteBdcLzr(@RequestBody List<BdcLzrDO> bdcLzrDOList) {
        bdcLzrService.batchDeleteBdcLzr(bdcLzrDOList);
    }

    /**
     * @param xmid 项目id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据xmid删除领证人信息
     * @date : 2020/1/17 16:23
     */
    @ApiOperation(value = "删除领证人信息")
    @ApiImplicitParam(name = "xmid", value = "项目id", dataType = "String", paramType = "path")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Override
    public void deleteBdcLzrByXmid(@PathVariable(name = "xmid") String xmid) {
        bdcLzrService.deleteBdcLzrByXmid(xmid);
    }

    /**
     * @param zsid 证书ID
     * @return List<BdcLzrDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据证书ID查询和证书相关的所有项目的受理领证人信息
     */
    @ApiOperation(value = "根据证书ID查询和证书相关的所有项目的受理领证人信息")
    @ApiImplicitParam(name = "zsid", value = "证书ID", dataType = "String", paramType = "path")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcLzrDO> getAllZsXmLzrByZsid(@PathVariable(value = "zsid") String zsid) {
        return bdcLzrService.getAllZsXmLzrByZsid(zsid);
    }

    /**
     * @param bdcDjxxUpdateQO 登记信息更新对象
     * @return 更新数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量更新不动产领证人
     */
    @Override
    @ApiOperation(value = "批量更新不动产权利人")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjxxUpdateQO", value = "登记信息更新对象", dataType = "BdcDjxxUpdateQO", paramType = "query")})
    @ResponseStatus(code = HttpStatus.OK)
    public int updateBatchBdcLzr(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO) throws Exception {
        return bdcLzrService.updateBatchBdcLzr(bdcDjxxUpdateQO);
    }

    /**
     * @param lzrmc   领证人信息
     * @param lzrzjh
     * @param gzlslid
     * @param djxl
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量删除不动产领证人
     */
    @ApiOperation(value = "通过领证人名称和证件号批量删除流程内领证人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "lzrmc", value = "领证人名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "lzrzjh", value = "领证人证件号",required = false,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "djxl", value = "登记小类",required = false, dataType = "String", paramType = "query")})
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Override
    public void deleteBdcLzrsByLzrxx(@RequestParam("lzrmc") String lzrmc, @RequestParam(value = "lzrzjh", required = false) String lzrzjh, @RequestParam("gzlslid") String gzlslid, @RequestParam(value = "djxl",required = false) String djxl) {
        //如果人名、工作流实例id没值则抛异常
        if (StringUtils.isBlank(lzrmc) || StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        BdcLzrDO bdcLzrDO = new BdcLzrDO();
        bdcLzrDO.setLzrmc(lzrmc);
        bdcLzrDO.setLzrzjh(lzrzjh);
        bdcLzrService.deleteBdcLzrsByLzrxx(gzlslid, djxl, bdcLzrDO);
    }

    /**
     * @param bdcLzrDTO 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 合肥批量/批量组合流程方法
     */
    @ApiOperation(value = "批量流程更新领证人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "BdcLzrDTO", value = "领证人信息", dataType = "BdcLzrDTO", paramType = "query")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public Integer updateLzrxxToHfPllc(@RequestBody BdcLzrDTO bdcLzrDTO) throws Exception {
        return bdcLzrService.updateLzrxxToPllc(bdcLzrDTO);
    }

    /**
     * @param bdcLzrDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/2/21 9:55
     */
    @ApiOperation(value = "简单流程更新领证人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "BdcLzrDTO", value = "领证人信息", dataType = "BdcLzrDTO", paramType = "query")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public BdcLzrDO updateLzrxxToHfJdlc(@RequestBody BdcLzrDTO bdcLzrDTO) {
        return bdcLzrService.updateLzrxxToJdlc(bdcLzrDTO);
    }

    /**
     * @param bdcLzrDOList 领证人集合
     * @return 新增个数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量新增领证人
     */
    @ApiOperation(value = "批量新增领证人")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcLzrDOList", value = "领证人集合", dataType = "BdcLzrDO", paramType = "query")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public int insertBatchBdcLzr(@RequestBody List<BdcLzrDO> bdcLzrDOList){
        return bdcLzrService.insertBatchBdcLzr(bdcLzrDOList);

    }

    /**
     * @param gzlslid
     * @param lzfs
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 批量新增领证人lzfs
     */
    @ApiOperation(value = "批量新增领证方式")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "gzlslid", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "lzfs", value = "领证方式", dataType = "String", paramType = "query")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public void updateAllLzfsByGzlslid(@RequestParam("gzlslid") String gzlslid, @RequestParam("lzfs") String lzfs) {
        bdcLzrService.updateAllLzfsByGzlslid(gzlslid, lzfs);
    }

    /**
     * 查询lzfs
     *
     * @param gzlslid
     */
    @ApiOperation(value = "查询领证方式")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "gzlslid", dataType = "String", paramType = "query")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public Integer getLzfsByGzlslid(@RequestParam("gzlslid") String gzlslid){
        return bdcLzrService.getLzfsByGzlslid(gzlslid);
    }

    /**
     * 查询lzfs
     *
     * @param gzlslid
     */
    @ApiOperation(value = "查询领证方式")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "gzlslid", dataType = "String", paramType = "query")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcLzxxDTO> getAllLzfsByGzlslid(@RequestParam("gzlslid") String gzlslid){
        return bdcLzrService.getAllLzfsByGzlslid(gzlslid);
    }

    /**
     * 查询lzfs
     *
     * @param zsid
     */
    @ApiOperation(value = "查询领证方式")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zsid", value = "zsid", dataType = "String", paramType = "query")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public BdcLzxxDTO getLzfsByZsid(@RequestParam("zsid") String zsid){
        return bdcLzrService.getLzfsByZsid(zsid);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcXmDOList]
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcLzrDO>
     * @description 根据项目查询领证人
     */
    @ApiOperation(value = "根据项目查询领证人")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXmDOList", value = "项目集合", dataType = "BdcXmDO", paramType = "body")})
    @Override
    public List<BdcLzrDO> getBdcLzrDOByXm(@RequestBody List<BdcXmDO> bdcXmDOList) {
        return bdcLzrService.getBdcLzrDOByXm(bdcXmDOList);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcLzrDOList]
     * @return int
     * @description 批量更新领证人
     */
    @ApiOperation(value = "批量更新领证人")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcLzrDOList", value = "领证人集合", dataType = "BdcLzrDO", paramType = "body")})
    @Override
    public int batchUpdateBdcLzr(@RequestBody List<BdcLzrDO> bdcLzrDOList) {
        return bdcLzrService.batchUpdateBdcLzr(bdcLzrDOList);
    }

    /**
     * @param gzlslid
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcLzrDO>
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcXmDOList]
     * @description 根据项目查询领证人
     */
    @Override
    public List<BdcLzrDO> getBdcLzrDOByGzlslid(@RequestParam("gzlslid") String gzlslid) {
        return bdcLzrService.getBdcLzrDOByGzlslid(gzlslid);
    }

    /**
     * @param json@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据领证人信息删除领证人
     * @date : 2022/3/25 13:27
     */
    @Override
    public void deleteLzrxx(@RequestBody String json) {
        if (StringUtils.isNotBlank(json)) {
            BdcLzrVO bdcLzrVO = JSON.parseObject(json, BdcLzrVO.class);
            bdcLzrService.deleteLzrxx(bdcLzrVO.getGzlslid(), bdcLzrVO.getLzrmc(), bdcLzrVO.getLzrzjh(), bdcLzrVO.getDjxl(), bdcLzrVO.getQlridList());
        }
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新领证人后回写领证人数据到证书表
     * @date : 2022/8/9 9:26
     */
    @Override
    public void hxLzrxxToZs(@RequestParam("gzlslid") String gzlslid) {
        bdcLzrService.hxLzrxxToZs(gzlslid);
    }


}
