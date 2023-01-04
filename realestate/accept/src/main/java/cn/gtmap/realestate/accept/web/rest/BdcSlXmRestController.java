package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlXmService;
import cn.gtmap.realestate.accept.service.BdcWlzsService;
import cn.gtmap.realestate.accept.service.BdcYxBdcdyService;
import cn.gtmap.realestate.accept.service.CshBdcSlXmService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.certificate.BdcXmGdxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.accept.BdcGwcDeleteQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcUpdateDagsdQo;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.register.BdcBdcdyQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlXmRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlXmVO;
import cn.gtmap.realestate.common.util.CheckParameter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理项目rest服务
 */
@RestController
@Api(tags = "不动产受理项目服务")
public class BdcSlXmRestController extends BaseController implements BdcSlXmRestService {
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 不动产受理项目数据服务
     */
    @Autowired
    private BdcSlXmService bdcSlXmService;
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 初始化不动产项目信息
     */
    @Autowired
    private CshBdcSlXmService cshBdcSlXmService;
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 已选不动产单元服务
     */
    @Autowired
    private BdcYxBdcdyService bdcYxBdcdyService;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 外联证书服务
     */
    @Autowired
    private BdcWlzsService bdcWlzsService;


    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据项目ID获取不动产受理项目", notes = "根据项目ID获取不动产受理项目服务")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    public BdcSlXmDO queryBdcSlXmByXmid(@PathVariable(value = "xmid") String xmid) {
        return bdcSlXmService.queryBdcSlXmByXmid(xmid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据基本信息ID获取不动产受理项目", notes = "根据基本信息ID获取不动产受理项目服务")
    @ApiImplicitParam(name = "jbxxid", value = "基本信息ID", required = true, dataType = "String", paramType = "path")
    public List<BdcSlXmDO> listBdcSlXmByJbxxid(@PathVariable(value = "jbxxid") String jbxxid) {
        return bdcSlXmService.listBdcSlXmByJbxxid(jbxxid,"");
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID获取不动产受理项目", notes = "根据工作流实例ID获取不动产受理项目服务")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    public List<BdcSlXmDO> listBdcSlXmByGzlslid(@PathVariable(value = "gzlslid") String gzlslid){
        return bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "权属状态修正流程根据工作流实例ID更新权属状态",notes = "权属状态修正流程根据工作流实例ID更新权属状态")
    @ApiImplicitParam(name = "processInsId",value = "工作流实例id",required = true,dataType = "String",paramType = "path")
    public void bdcSlGxqsztByGzlslid(@RequestParam(value = "processInsId") String processInsId) {
        try {
            bdcSlXmService.updateQszt(processInsId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据项目ID集合获取不动产受理项目", notes = "根据项目ID集合获取不动产受理项目服务")
    @ApiImplicitParam(name = "xmidList", value = "项目ID集合", required = true, dataType = "String", paramType = "body")
    public List<BdcSlXmDO> listBdcSlXmByXmids(@RequestBody List<String> xmidList){
        return bdcSlXmService.listBdcSlXmByXmids(xmidList);

    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增不动产受理项目", notes = "新增不动产受理项目服务服务")
    @ApiImplicitParam(name = "bdcSlXmDO", value = "新增不动产受理项目", required = true, dataType = "BdcSlXmDO")
    public BdcSlXmDO insertBdcSlXm(@RequestBody BdcSlXmDO bdcSlXmDO) {
        return bdcSlXmService.insertBdcSlXm(bdcSlXmDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新不动产受理项目", notes = "更新不动产受理项目服务")
    @ApiImplicitParam(name = "bdcSlXmDO", value = "新增不动产受理项目", required = true, dataType = "BdcSlXmDO")
    public Integer updateBdcSlXm(@RequestBody BdcSlXmDO bdcSlXmDO) {
        return bdcSlXmService.updateBdcSlXm(bdcSlXmDO);
    }


    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据项目ID删除不动产受理项目", notes = "根据项目ID删除不动产受理项目服务")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcSlXmByXmid(@PathVariable(value = "xmid") String xmid) {
        return bdcSlXmService.deleteBdcSlXmByXmid(xmid);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据基本信息ID删除不动产受理项目", notes = "根据基本信息ID删除不动产受理项目服务")
    @ApiImplicitParam(name = "jbxxid", value = "基本信息ID", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcSlXmByJbxxid(@PathVariable(value = "jbxxid") String jbxxid) {
        return bdcSlXmService.deleteBdcSlXmByJbxxid(jbxxid);
    }

    @Override
    @ApiOperation(value = "分页已选业务信息")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jbxxid", value = "基本信息ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "String", paramType = "query")})
    public Page<BdcSlYwxxDTO> listBdcSlXmByPageJson(Pageable pageable,String bdcSlXmQO) {
        Map map = JSONObject.parseObject(bdcSlXmQO, HashMap.class);
        return bdcSlXmService.listBdcSLXmDTOByPage(pageable, map);
    }

    @Override
    @ApiOperation(value = "根据查询条件获取已选业务信息")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlXmQO", value = "查询条件", dataType = "String", paramType = "query")})
    public List<BdcSlYwxxDTO> listBdcSlYwxxDTO(String bdcSlXmQO) {
        Map map = JSONObject.parseObject(bdcSlXmQO, HashMap.class);
        return bdcSlXmService.listBdcSLXmDTO(map);
    }

    @Override
    @ApiOperation(value = "分页已选业务信息")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jbxxid", value = "基本信息ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "String", paramType = "query")})
    public YxBdcdyDTO queryYxBdcdyDTOByPage(Pageable pageable, String slXmQO,
                                      @RequestParam(name = "gzldyid", required = false) String gzldyid,
                                      @RequestParam(name = "jbxxid", required = false) String jbxxid) {
        Page<BdcSlYwxxDTO> bdcSlYwxxDTOPage = listBdcSlXmByPageJson(pageable, slXmQO);
        return bdcYxBdcdyService.queryYxBdcdyDTO(bdcSlYwxxDTOPage, jbxxid, gzldyid);
    }

    @Override
    @ApiOperation(value = "已选业务信息(不分页)")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jbxxid", value = "基本信息ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzldyid", value = "工作流定义id", dataType = "String", paramType = "query"),
           })
    public YxBdcdyDTO queryYxBdcdyDTO(String slXmQO,
                                            @RequestParam(name = "gzldyid", required = false) String gzldyid,
                                            @RequestParam(name = "jbxxid", required = false) String jbxxid) {
        List<BdcSlYwxxDTO> bdcSlYwxxDTOList = listBdcSlYwxxDTO(slXmQO);
        return bdcYxBdcdyService.queryYxBdcdyDTO(bdcSlYwxxDTOList, jbxxid, gzldyid,"");
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "单个、多个已选业务信息", notes = "单个、多个已选业务信息服务")
    @ApiImplicitParam(name = "jbxxid", value = "基本信息ID", required = true, dataType = "String", paramType = "path")
    public List<BdcSlYwxxDTO> listBdcSlYwxxByJbxxid(@PathVariable(value = "jbxxid") String jbxxid) {
        return bdcSlXmService.listBdcSLXmDTOByJbxxid(jbxxid);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "已选不动产单元生成数据", notes = "已选不动产单元生成数据服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "czrid", value = "操作人员ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "bdcCshSlxmDTO", value = "不动产初始化受理项目", required = true, dataType = "BdcCshSlxmDTO")
    })
    public void cshYxxm(@PathVariable(value = "czrid") String czrid, @RequestBody BdcCshSlxmDTO bdcCshSlxmDTO) {
        if (bdcCshSlxmDTO != null && CollectionUtils.isNotEmpty(bdcCshSlxmDTO.getBdcSlYwxxDTOList())) {
            cshBdcSlXmService.cshYxxm(bdcCshSlxmDTO.getBdcSlYwxxDTOList(), bdcCshSlxmDTO.getGzlslid(), bdcCshSlxmDTO.getGzldyid(), bdcCshSlxmDTO.getJbxxid(), czrid);
        }
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "外联证书生成数据", notes = "外联证书生成数据服务")
    @ApiImplicitParam(name = "bdcCshSlxmDTO", value = "不动产初始化受理项目", required = true, dataType = "BdcCshSlxmDTO")
    public void wlZs(@RequestBody BdcCshSlxmDTO bdcCshSlxmDTO) {
        if (bdcCshSlxmDTO != null && CollectionUtils.isNotEmpty(bdcCshSlxmDTO.getBdcSlYwxxDTOList()) && StringUtils.isNotBlank(bdcCshSlxmDTO.getJbxxid())) {
            bdcWlzsService.wlZs(bdcCshSlxmDTO, bdcCshSlxmDTO.getJbxxid());
        }
    }

    /**
     * @param bdcCshSlxmDTO 不动产初始化受理项目
     * @param xmids
     * @author <a href="mailto:gailining@gtmap.cn">gaolining</a>
     * @description 外联证书生成数据, 带入抵押信息生成历史关系
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "外联证书生成数据", notes = "外联证书生成数据服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xmids", value = "抵押信息的xmids", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bdcCshSlxmDTO", value = "不动产初始化受理项目", required = true, dataType = "BdcCshSlxmDTO")
    })
    public void wlZsDyaqxx(@RequestBody BdcCshSlxmDTO bdcCshSlxmDTO,@RequestParam(name = "xmids", required = false) String xmids) {
        if (bdcCshSlxmDTO != null && CollectionUtils.isNotEmpty(bdcCshSlxmDTO.getBdcSlYwxxDTOList()) && StringUtils.isNotBlank(bdcCshSlxmDTO.getJbxxid()) && StringUtils.isNotBlank(xmids)) {
            bdcWlzsService.wlZsDyaqxx(bdcCshSlxmDTO, bdcCshSlxmDTO.getJbxxid(), xmids);
        }
    }



    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新不动产受理项目", notes = "更新不动产受理项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "不动产受理项目Json", required = true, dataType = "String")})
    public Integer updateBdcSlXm(@RequestBody String json){
        return bdcSlXmService.updateBdcSlXmList(json);
    }


    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据户室主键和基本信息id删除已选项目信息", notes = "根据户室主键和基本信息id删除已选项目信息服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fwhsIndexs", value = "户室主键", required = true, dataType = "String"),
            @ApiImplicitParam(name = "jbxxid", value = "基本信息id", required = true, dataType = "String")})
    public Integer deleteYxFwhs(@RequestBody String fwhsIndexs,@RequestParam(name = "jbxxid") String jbxxid){
        return bdcSlXmService.deleteYxFwhs(fwhsIndexs, jbxxid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据查询条件获取受理项目集合", notes = "根据查询条件获取受理项目集合服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlXmQO", value = "受理查询对象", required = true, dataType = "BdcSlXmQO")})
    public List<BdcSlXmDO> listBdcSlXm(@RequestBody BdcSlXmQO bdcSlXmQO){
        return bdcSlXmService.listBdcSlXm(bdcSlXmQO);

    }

    @Override
    @ApiOperation(value = "根据登记小类分组查询已选业务信息(不分页)")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jbxxid", value = "基本信息ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzldyid", value = "工作流定义id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sfazfz", value = "是否按幢分组", dataType = "String", paramType = "query"),
    })
    public List<YxBdcdyDTO> queryYxBdcdyDTOGroupByDjxl(String slXmQO,
                                                       @RequestParam(name = "gzldyid", required = false) String gzldyid,
                                                       @RequestParam(name = "jbxxid", required = false) String jbxxid,
                                                       @RequestParam(name = "sfazfz", required = false) String sfazfz) {
        return bdcYxBdcdyService.queryYxBdcdyDTOGroupByDjxl(slXmQO, jbxxid, gzldyid, sfazfz);
    }

    @Override
    @ApiOperation(value = "批量更新证书序号")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jbxxid", value = "基本信息ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzldyid", value = "工作流定义id", dataType = "String", paramType = "query"),
    })
    public Integer batchUpdateBdcSlXmZsxh(@RequestParam(name = "zsxh", required = false) String zsxh, @RequestParam(value = "jbxxid") String jbxxid,@RequestBody List<String> xmidList){
        return bdcSlXmService.batchUpdateBdcSlXmZsxh(zsxh, jbxxid, xmidList);

    }

    /**
     * @param bdcSlXmQO
     * @return 已选数据
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询购物车中所有的数据, 需要包含原项目id
     */
    @Override
    @ApiOperation(value = "根据查询条件获取已选业务信息")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlXmQO", value = "查询条件", dataType = "String", paramType = "query")})
    public List<BdcSlYwxxDTO> listGwcData(String bdcSlXmQO) {
        Map map = JSONObject.parseObject(bdcSlXmQO, HashMap.class);
        return bdcSlXmService.listGwcData(map);
    }

    /**
     * 获取不动产单元号是否重复配置
     * 选择不动产单元页面
     *
     * @param xmid xmid
     * @return 是否去除重复数据
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public boolean bdcdyhIsRepeat(String xmid) {
        return bdcSlXmService.bdcdyhIsRepeat(xmid);
    }

    /**
     * @param bdcSlXmQO 不动产受理项目查询
     * @return 受理项目
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据原xmid和jbxxid查询到购物车中的对应xmid
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据查询条件获取受理项目集合", notes = "根据查询条件获取受理项目集合服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlXmQO", value = "受理查询对象", required = true, dataType = "BdcSlXmQO")})
    public List<BdcSlXmDO> queryBdcSlXmByLsgx(@RequestBody BdcSlXmQO bdcSlXmQO) {
        return bdcSlXmService.queryBdcSlxmByLsgx(bdcSlXmQO);
    }

    /**
     * @param bdcXmDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取领证日期
     * @date : 2020/1/15 9:57
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取领证日期", notes = "获取领证日期服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcXmDO", value = "承诺期限",dataType = "BdcXmDO")})
    public Date getLzrq(@RequestBody BdcXmDO bdcXmDO) throws Exception {
        if(bdcXmDO == null || StringUtils.isBlank(bdcXmDO.getCnqx()) || bdcXmDO.getSlsj() == null) {
            throw new AppException("获取承诺领证日期缺少必要参数");
        }
        return bdcSlXmService.getLzrq(bdcXmDO.convertIntegerCnqx(), bdcXmDO.getSlsj());
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据基本信息ID获取项目ID集合", notes = "根据基本信息ID获取项目ID集合服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jbxxid", value = "基本信息ID",dataType = "String")})
    public List<String> getXmidListByJbxxid(@PathVariable(name = "jbxxid") String jbxxid){
        return bdcSlXmService.getXmidListByJbxxid(jbxxid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询购物车已选项目数据(包含历史关系)", notes = "查询购物车已选项目数据(包含历史关系)服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlXmQO", value = "受理查询对象", required = true, dataType = "BdcSlXmQO")})
    public List<BdcSlXmVO> listGwcSelectDataWithLsgx(@RequestBody BdcSlXmQO bdcSlXmQO){
        return bdcSlXmService.listGwcSelectDataWithLsgx(bdcSlXmQO);

    }

    @ApiOperation(value = "通过基本信息ID查询受理发证状态信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "jbxxid", value = "基本信息ID", dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcSlCshFwkgDataDTO> querySlFzztByJbxxid(@RequestParam(name="jbxxid") String jbxxid){
        return bdcSlXmService.querySlFzztByJbxxid(jbxxid);

    }

    @ApiOperation(value = "更新受理项目中的证书类型与是否发证")
    @ApiImplicitParams({@ApiImplicitParam(name = "jbxxid", value = "基本信息ID", dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<String> updateSlxmFzztPl(@PathVariable(name = "jbxxid") String jbxxid,@RequestBody List<BdcSlXmDO> bdcSlXmDOList,@RequestParam(name="gzldyid") String gzldyid){
        return bdcSlXmService.updateSlxmFzztPl(jbxxid, bdcSlXmDOList,gzldyid);

    }

    @ApiOperation(value = "购物车删除受理信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "jbxxid", value = "基本信息ID", dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void deleteYxxm(@RequestBody BdcGwcDeleteQO bdcGwcDeleteQO) {
        bdcSlXmService.deleteYxxm(bdcGwcDeleteQO);

    }

    /**
     * @param pageable
     * @param bdcdyQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询项目房屋信息
     * @date : 2022/8/18 16:14
     */
    @Override
    public Page<BdcSlFwxxDTO> listBdcSlFwxxByPageJson(Pageable pageable, @RequestParam(name = "bdcdyQO", required = false) String bdcdyQO) {
        BdcBdcdyQO bdcBdcdyQO = JSON.parseObject(bdcdyQO, BdcBdcdyQO.class);
        if (CheckParameter.checkAnyParameter(bdcBdcdyQO, "gzlslid", "bdcdyh", "xmid", "xmidList")) {
            return bdcSlXmService.listBdcFwxxByPage(pageable, bdcBdcdyQO);
        }
        return null;
    }

    /**
     * @param jbxxid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询已选单元信息的规划用途
     * @date : 2022/9/13 11:04
     */
    @Override
    public List<String> listGwcGhytByJbxxid(@RequestParam(name = "jbxxid", required = true) String jbxxid) {
        return bdcSlXmService.listGwcGhyt(jbxxid);
    }

    /**
     * @param jbxxid
     * @param djxl
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 计算购物车的总面积
     * @date : 2022/9/13 11:09
     */
    @Override
    public String queryGwcJzmjByJbxxid(@RequestParam(name = "jbxxid", required = true) String jbxxid, @RequestParam(name = "djxl", required = false) String djxl) {
        return bdcSlXmService.queryGwcDzwmj(jbxxid, djxl);
    }

    /**
     * @param jsonObject
     * @return
     */
    @Override
    public List<BdcXmGdxxDTO> listBdcGdxxHf(@RequestBody HashMap<String, Object> param) {
        return bdcSlXmService.listBdcGdxxHf(param);
    }

    /**
     * 批量更新档案归属地信息
     * @param param
     * @return
     */
    @Override
    public Integer batchUpdateBdcSlXmDagsd(@RequestBody BdcUpdateDagsdQo param) {
        if(StringUtils.isBlank(param.getDagsd())){
            return 0;
        }
        if(CollectionUtils.isEmpty(param.getJbxxidList()) && CollectionUtils.isEmpty(param.getXmidList())){
            return 0;
        }
        return bdcSlXmService.batchUpdateBdcSlXmDagsd(param.getDagsd(),param.getJbxxidList(),param.getXmidList());
    }

    /**
     * @param bdcDjxxUpdateQO 登记信息更新对象
     * @return 更新数量
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description 批量更新受理项目
     */
    @ApiOperation(value = "批量更新受理项目")
    @ApiImplicitParam(name = "bdcDjxxUpdateQO", value = "更新数量", dataType = "BdcDjxxUpdateQO", paramType = "query")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public Integer updateBatchBdcSlXm(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO){
        return bdcSlXmService.updateBatchBdcSlXm(bdcDjxxUpdateQO);
    }


}
