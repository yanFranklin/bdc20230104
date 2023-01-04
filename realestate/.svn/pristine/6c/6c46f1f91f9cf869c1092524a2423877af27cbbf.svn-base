package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.service.AcceptBdcdyService;
import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyLcztResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.LpxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.getWwsqBdcdyxx.response.WwsqBdcdyxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.gzwxxcx.request.GzwxxcxQO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.gzwxxcx.response.GzwxxResponseDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlBdcdyhQO;
import cn.gtmap.realestate.common.core.qo.building.WwsqBdcdyxxQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.rest.building.AcceptBdcdyRestService;
import cn.gtmap.realestate.common.util.BdcSdUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.LogConstans;
import cn.gtmap.realestate.common.util.PageUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-23
 * @description 为受理子系统提供
 * 根据BDCLX分页查询BDCDY服务
 */
@RestController
@Api(tags = "为受理子系统提供根据BDCLX分页查询BDCDY服务")
public class AcceptBdcdyRestController implements AcceptBdcdyRestService{

    @Autowired
    private AcceptBdcdyService acceptBdcdyService;

    @Autowired
    BdcSdFeignService bdcSdFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;



    /**
     * @param pageable
     * @param qlxzAndZdtzm 权利性质及宗地特征码（BDCDYH 的 第13+14位） 多个用,隔开
     * @param zdtzm 宗地特征码(BDCDYH 的 第14位) 多个用,隔开
     * @param dzwtzm 定着物特征码(BDCDYH 的 第20位) 多个用,隔开
     * @param fwlx 判断查询类型 xmxx ljz hs ychs，空为全部
     * @param paramJson
     * @return Page<Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询土地及定着物类型不动产单元信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询土地及定着物类型不动产单元信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zdtzm", value = "宗地特征码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "dzwtzm", value = "定着物特征码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fwlx", value = "类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<Map> listTdAndDzwBdcdyByPage(Pageable pageable,
                                             @RequestParam(name = "qlxzAndZdtzm",required = false) String qlxzAndZdtzm,
                                          @RequestParam(name = "zdtzm",required = false) String zdtzm,
                                          @RequestParam(name = "dzwtzm") String dzwtzm,
                                          @RequestParam(name = "fwlx",required = false) String fwlx,
                                          @RequestParam(name = "paramJson",required = false) String paramJson) {
        Map<String,Object> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(paramJson)){
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return acceptBdcdyService.listTdAndDzwBdcdyByPage(pageable,StringUtils.upperCase(qlxzAndZdtzm),
                StringUtils.upperCase(zdtzm),StringUtils.upperCase(dzwtzm),StringUtils.lowerCase(fwlx),paramMap);
    }

    /**
     * @param qlxzAndZdtzm 权利性质及宗地特征码（BDCDYH 的 第13+14位） 多个用,隔开
     * @param zdtzm        宗地特征码(BDCDYH 的 第14位) 多个用,隔开
     * @param dzwtzm       定着物特征码(BDCDYH 的 第20位) 多个用,隔开
     * @param fwlx         判断查询类型 xmxx ljz hs ychs，空为全部
     * @param paramJson
     * @return List<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 不分页查询土地及定着物类型不动产单元信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "不分页查询土地及定着物类型不动产单元信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zdtzm", value = "宗地特征码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "dzwtzm", value = "定着物特征码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fwlx", value = "类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "String", paramType = "query")})
    public List<Map> listTdAndDzwBdcdy(String qlxzAndZdtzm, String zdtzm, String dzwtzm, String fwlx, String paramJson) {
        Map<String,Object> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(paramJson)){
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return acceptBdcdyService.listTdAndDzwBdcdy(qlxzAndZdtzm,zdtzm,dzwtzm,fwlx,paramMap);
    }

    /**
     * @param pageable
     * @param zdtzm
     * @param dzwtzm
     * @param paramJson
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询海域不动产单元信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询海域不动产单元信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zdtzm", value = "宗地特征码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "dzwtzm", value = "定着物特征码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<Map> listHyBdcdyByPage(Pageable pageable,
                                          @RequestParam(name = "zdtzm",required = false) String zdtzm,
                                          @RequestParam(name = "dzwtzm",required = false) String dzwtzm,
                                          @RequestParam(name = "paramJson",required = false) String paramJson) {
        Map<String,Object> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(paramJson)){
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return acceptBdcdyService.listHyBdcdyByPage(pageable,zdtzm,dzwtzm,paramMap);
    }

    /**
     * @param zdtzm
     * @param dzwtzm
     * @param paramJson
     * @return List<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 不分页查询海域不动产单元信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "不分页查询海域不动产单元信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zdtzm", value = "宗地特征码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "dzwtzm", value = "定着物特征码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "String", paramType = "query")})
    public List<Map> listHyBdcdy(String zdtzm, String dzwtzm, String paramJson) {
        Map<String,Object> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(paramJson)){
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return acceptBdcdyService.listHyBdcdy(zdtzm,dzwtzm,paramMap);
    }

    /**
     * @param pageable
     * @param zdtzm
     * @param dzwtzm
     * @param paramJson
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询构筑物不动产单元信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询构筑物不动产单元信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zdtzm", value = "宗地特征码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "dzwtzm", value = "定着物特征码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<Map> listGzwBdcdyByPage(Pageable pageable,
                                        @RequestParam(name = "zdtzm",required = false) String zdtzm,
                                        @RequestParam(name = "dzwtzm",required = false) String dzwtzm,
                                        @RequestParam(name = "paramJson",required = false) String paramJson) {
        Map<String,Object> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(paramJson)){
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return acceptBdcdyService.listGzwBdcdyByPage(pageable,zdtzm,dzwtzm,paramMap);
    }

    /**
     * @param zdtzm
     * @param dzwtzm
     * @param paramJson
     * @return List<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 不分页查询构筑物不动产单元信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "不分页查询构筑物不动产单元信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zdtzm", value = "宗地特征码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "dzwtzm", value = "定着物特征码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "String", paramType = "query")})
    public List<Map> listGzwBdcdy(String zdtzm, String dzwtzm, String paramJson) {
        Map<String,Object> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(paramJson)){
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return acceptBdcdyService.listGzwBdcdy(zdtzm,dzwtzm,paramMap);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询承包宗地不动产单元信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zdtzm", value = "宗地特征码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "dzwtzm", value = "定着物特征码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "String", paramType = "query")})
    public Page<Map> listCbzdBdcdyByPage(Pageable pageable,
                                         @RequestParam(name = "zdtzm",required = false) String zdtzm,
                                         @RequestParam(name = "dzwtzm",required = false) String dzwtzm,
                                         @RequestParam(name = "paramJson",required = false) String paramJson){
        Map<String,Object> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(paramJson)){
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return acceptBdcdyService.listCbzdBdcdyByPage(pageable,zdtzm,dzwtzm,paramMap);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "不分页查询承包宗地不动产单元信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zdtzm", value = "宗地特征码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "dzwtzm", value = "定着物特征码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "String", paramType = "query")})
    public List<Map> listCbzdBdcdy(@RequestParam(name = "zdtzm",required = false) String zdtzm,
                            @RequestParam(name = "dzwtzm",required = false) String dzwtzm,
                            @RequestParam(name = "paramJson",required = false) String paramJson){
        Map<String,Object> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(paramJson)){
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return acceptBdcdyService.listCbzdBdcdy(zdtzm,dzwtzm,paramMap);

    }

    @Override
    @LogNote(value = "选择不动产单元号", action = LogConstans.LOG_TYPE_XZTZ, custom = LogConstans.LOG_TYPE_XZTZ)
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页或列表查询不动产单元信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paramJson", value = "查询参数", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "loadpage", value = "是否分页", dataType = "String", paramType = "query")})
    public Object listBdcdyhByPageOrList(Pageable pageable,
                                         @RequestParam(name = "paramJson",required = false) String paramJson,
                                         @RequestParam(name = "loadpage",required = false) Boolean loadpage){
        BdcSlBdcdyhQO bdcSlBdcdyhQO = new BdcSlBdcdyhQO();
        if(StringUtils.isNotBlank(paramJson)){
            bdcSlBdcdyhQO = JSONObject.parseObject(paramJson,BdcSlBdcdyhQO.class);
        }
        if(bdcSlBdcdyhQO.getDyhcxlx() != null) {
            switch (bdcSlBdcdyhQO.getDyhcxlx()) {
                /**
                 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
                 * @description 分页查询土地及其定着物类型不动产单元信息
                 */
                case 1:
                    if (Boolean.TRUE.equals(loadpage)) {
                        Page<Map> maps = listTdAndDzwBdcdyByPage(pageable, bdcSlBdcdyhQO.getQlxzAndZdtzm(), bdcSlBdcdyhQO.getZdtzm(), bdcSlBdcdyhQO.getDzwtzm(), bdcSlBdcdyhQO.getBdcdyfwlx(), JSON.toJSONString(bdcSlBdcdyhQO));
                        if(CollectionUtils.isNotEmpty(maps.getContent()) && StringUtils.isNotBlank(bdcSlBdcdyhQO.getSfsdzt())){
                            //添加限制信息
                            for (Map map : maps.getContent()) {
                                setSdxx(map);
                            }
                        }
                        return PageUtils.addLayUiCodeWithQjgldm(maps, bdcSlBdcdyhQO.getQjgldm());
                    } else {
                        return addQjgldm(listTdAndDzwBdcdy(bdcSlBdcdyhQO.getQlxzAndZdtzm(), bdcSlBdcdyhQO.getZdtzm(), bdcSlBdcdyhQO.getDzwtzm(), bdcSlBdcdyhQO.getBdcdyfwlx(), JSON.toJSONString(bdcSlBdcdyhQO)),bdcSlBdcdyhQO.getQjgldm());
                    }
                    /**
                     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
                     * @description 分页查询海域及其定着物类型不动产单元信息
                     */
                case 2:
                    if (Boolean.TRUE.equals(loadpage)) {
                        return PageUtils.addLayUiCodeWithQjgldm(listHyBdcdyByPage(pageable, bdcSlBdcdyhQO.getZdtzm(), bdcSlBdcdyhQO.getDzwtzm(), JSON.toJSONString(bdcSlBdcdyhQO)), bdcSlBdcdyhQO.getQjgldm());
                    } else {
                        return addQjgldm(listHyBdcdy(bdcSlBdcdyhQO.getZdtzm(), bdcSlBdcdyhQO.getDzwtzm(), JSON.toJSONString(bdcSlBdcdyhQO)),bdcSlBdcdyhQO.getQjgldm());
                    }
                    /**
                     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
                     * @description 分页查询构筑物不动产单元信息
                     */
                case 3:
                    if (Boolean.TRUE.equals(loadpage)) {
                        return PageUtils.addLayUiCodeWithQjgldm(listGzwBdcdyByPage(pageable, bdcSlBdcdyhQO.getZdtzm(), bdcSlBdcdyhQO.getDzwtzm(), JSON.toJSONString(bdcSlBdcdyhQO)), bdcSlBdcdyhQO.getQjgldm());
                    } else {
                        return addQjgldm(listGzwBdcdy(bdcSlBdcdyhQO.getZdtzm(), bdcSlBdcdyhQO.getDzwtzm(), JSON.toJSONString(bdcSlBdcdyhQO)),bdcSlBdcdyhQO.getQjgldm());
                    }
                    /**
                     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
                     * @description 分页查询承包宗地不动产单元信息
                     */
                case 4:
                    if (Boolean.TRUE.equals(loadpage)) {
                        return PageUtils.addLayUiCodeWithQjgldm(listCbzdBdcdyByPage(pageable, bdcSlBdcdyhQO.getZdtzm(), bdcSlBdcdyhQO.getDzwtzm(), JSON.toJSONString(bdcSlBdcdyhQO)), bdcSlBdcdyhQO.getQjgldm());
                    } else {
                        return addQjgldm(listCbzdBdcdy(bdcSlBdcdyhQO.getZdtzm(), bdcSlBdcdyhQO.getDzwtzm(), JSON.toJSONString(bdcSlBdcdyhQO)),bdcSlBdcdyhQO.getQjgldm());
                    }
                default:
                    break;
            }
        }
        return null;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyLcztResponseDTO
     * @description 根据BDCDYH查询流程状态
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询流程状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "String", paramType = "path")})
    public BdcdyLcztResponseDTO queryBdcdyLczt(@PathVariable(name = "bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required =false) String qjgldm){
        return acceptBdcdyService.queryBdcdyLczt(bdcdyh);
    }

    /**
     * @param gzlslid
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwLjzDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据工作流实例ID查询当前项目下的所有房屋逻辑幢
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID查询当前项目下的所有房屋逻辑幢")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "作流实例ID", dataType = "String", paramType = "path")})
    public List<FwLjzDO> listFwLjzByGzlslid(@PathVariable(name = "gzlslid") String gzlslid,@RequestParam(name = "qjgldm", required =false) String qjgldm) {
        return acceptBdcdyService.listFwLjzByGzlslid(gzlslid);
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/1/14 15:46
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID查询当前项目下的所有房屋逻辑幢,分组户室信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "作流实例ID", dataType = "String", paramType = "path")})
    public LpxxDTO listFwLjzFzHsxx(@PathVariable(name = "gzlslid") String gzlslid,@RequestParam(name = "qjgldm", required =false) String qjgldm) {
        return acceptBdcdyService.listFwljzFzxx(gzlslid);
    }

    @ApiOperation(value = "根据施工编号查询坐落")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public String queryZlBySgbh(@RequestParam(name = "sgbh")String sgbh){
        return  acceptBdcdyService.queryZlBySgbh(sgbh);
    }

    /**
     * @param bdcSlYwxxDTOList 不动产受理项目前台
     * @return map 根据幢分组
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据不动产单元号查询逻辑幢信息，用于购物车发证方式任意组合时按幢分组")
    public Map<String, List<BdcSlYwxxDTO>> getLjzxx(@RequestBody List<BdcSlYwxxDTO> bdcSlYwxxDTOList,@RequestParam(name = "qjgldm", required =false) String qjgldm) {
        return acceptBdcdyService.getLjzxx(bdcSlYwxxDTOList);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据不动产单元号获取权属性质")
    public String getQsxzByBdcdyh(@RequestParam(name = "bdcdyh") String bdcdyh){
        return acceptBdcdyService.getQsxzByBdcdyh(bdcdyh);

    }

    /**
     * @param qxdm
     * @return
     * @author
     * @description 获取区县下林地总面积和宗地数量
     */
    @Override
    public List<Map>  getLdmjAndZd(@RequestParam(name = "qxdm") String qxdm) {
        return acceptBdcdyService.getLdmjAndZd(Arrays.asList(qxdm.split(",")));
    }

    @Override
    public Page<WwsqBdcdyxxDTO> listWwsqBdcdyxxDTOByPage(Pageable pageable, @RequestParam(name = "paramJson",required = false) String paramJson){
        WwsqBdcdyxxQO wwsqBdcdyxxQO= new WwsqBdcdyxxQO();
        if(StringUtils.isNotBlank(paramJson)){
            wwsqBdcdyxxQO = JSONObject.parseObject(paramJson, WwsqBdcdyxxQO.class);
        }
        return acceptBdcdyService.listWwsqBdcdyxxDTOByPage(pageable,wwsqBdcdyxxQO);
    }

    @Override
    public Page<GzwxxResponseDTO> listWwsqGzwxxDTOByPage(Pageable pageable, @RequestParam(name = "paramJson",required = false) String paramJson){
        GzwxxcxQO gzwxxcxQO =new GzwxxcxQO();
        if(StringUtils.isNotBlank(paramJson)){
            gzwxxcxQO = JSONObject.parseObject(paramJson, GzwxxcxQO.class);
        }
        return acceptBdcdyService.listWwsqGzwxxDTOByPage(pageable,gzwxxcxQO);

    }


    /**
     * 集合添加权籍管理代码字段
     *
     * @param list
     * @return
     */
    public static Object addQjgldm(List<Map> list, String qjgldm) {
        if(CollectionUtils.isNotEmpty(list)){
            for(Map content:list){
                content.put("qjgldm",qjgldm);
            }
        }
        return list;
    }

    /**
     * @param dataMap
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">wangzijie</a>
     * @description 保存宗地相关锁定信息
     */
    private void setSdxx(Map dataMap) {
        if (dataMap != null) {
            String xmid = MapUtils.getString(dataMap,"xmid");
            String bdcdyh = MapUtils.getString(dataMap,"bdcdyh");
            List<BdcZssdDO> bdcZssdDOS = new ArrayList<>();
            List<BdcDysdDO> bdcDysdDOS = new ArrayList<>();
            if(StringUtils.isNotBlank(xmid)){
                bdcZssdDOS = bdcSdFeignService.queryBdczsSdByXmid(xmid);
            }
            if(StringUtils.isNotBlank(bdcdyh)){
                BdcDysdDO bdcDysdDO = new BdcDysdDO();
                bdcDysdDO.setBdcdyh(bdcdyh);
                bdcDysdDO.setSdzt(CommonConstantUtils.SDZT_SD);
                bdcDysdDO.setSdlx(CommonConstantUtils.SDLX_CJSD);
                bdcDysdDOS = bdcSdFeignService.queryBdcdySd(bdcDysdDO);
            }

            //存在单元锁定或者证书锁定
            if(CollectionUtils.isNotEmpty(bdcZssdDOS) || CollectionUtils.isNotEmpty(bdcDysdDOS)){
                List<Map> sdlxZdMap = bdcZdFeignService.queryBdcZd("sdlx");
                dataMap.put("sdzt", BdcSdUtils.sdyyWithList(bdcZssdDOS, bdcDysdDOS, sdlxZdMap));
            }
        }
    }
}
