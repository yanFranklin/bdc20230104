package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.service.BdcdyService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.SZdTdsyqlxDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.FwBdcdyDTO;
import cn.gtmap.realestate.common.core.service.rest.building.BdcdyRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-07
 * @description
 */
@RestController
@Api(tags = "不动产单元查询接口")
public class BdcdyRestController implements BdcdyRestService {
    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 不动产单元接口
     */
    @Autowired
    private BdcdyService bdcdyService;

    @Autowired
    private EntityMapper entityMapper;
    /**
     * @param pageable
     * @param paramJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.BdcdyPageResponseDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询实测户室不动产单元信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询实测户室不动产单元信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<Map> listScFwHsBdcdy(Pageable pageable,
                                                      @RequestParam(name = "paramJson",required = false) String paramJson) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(paramJson)) {
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        // 默认查询 有BDCDYH的 BDCDY
        if(StringUtils.isBlank(MapUtils.getString(paramMap,"bdcdyhNotNull"))){
            paramMap.put("bdcdyhNotNull","true");
        }
        // 默认查询 可用状态 BDCDY
        if(StringUtils.isBlank(MapUtils.getString(paramMap,"bdczt"))){
            paramMap.put("bdczt", Constants.BDCZT_KY);
        }
        return bdcdyService.listScFwHsBdcdy(pageable, paramMap);
    }

    /**
     * @param pageable
     * @param paramJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.BdcdyPageResponseDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询预测户室不动产单元信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询预测户室不动产单元信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<Map> listYcFwHsBdcdy(Pageable pageable,
                                     @RequestParam(name = "paramJson", required = false) String paramJson) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(paramJson)) {
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        // 默认查询 有BDCDYH的 BDCDY
        if(StringUtils.isBlank(MapUtils.getString(paramMap,"bdcdyhNotNull"))){
            paramMap.put("bdcdyhNotNull","true");
        }
        // 默认查询 可用状态 BDCDY
        if(StringUtils.isBlank(MapUtils.getString(paramMap,"bdczt"))){
            paramMap.put("bdczt", Constants.BDCZT_KY);
        }
        return bdcdyService.listYcFwHsBdcdy(pageable, paramMap);
    }

    /**
     * @param bdcdyh
     * @param bdcdyfwlx
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.BdcdyPageResponseDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询不动单元基本信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询房屋类型不动单元基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")
            , @ApiImplicitParam(name = "bdcdyfwlx", value = "不动产单元房屋类型", dataType = "String", paramType = "query")})
    public BdcdyResponseDTO queryBdcdy(@PathVariable("bdcdyh") String bdcdyh,
                                       @RequestParam(name = "bdcdyfwlx",required = false) String bdcdyfwlx,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return bdcdyService.queryBdcdy(bdcdyh, bdcdyfwlx);
    }

    /**
     * @param fwbm
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据Fwbm查询不动单元基本信息(包含FW_HS  FW_YCHS FW_LJZ FW_XMXX)
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据Fwbm查询不动单元基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwbm", value = "房屋编码", required = true, dataType = "String", paramType = "query")})
    public BdcdyResponseDTO queryBdcdyByFwbm(@RequestParam("fwbm")String fwbm,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return bdcdyService.queryBdcdyByFwbm(fwbm);
    }

    /**
     * @param ysfwbm
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据ysFwbm查询户室不动单元基本信息(包含FW_HS FW_YCHS)
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据ysFwbm查询户室不动单元基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "ysfwbm", value = "房屋编码", required = true, dataType = "String", paramType = "query")})
    public BdcdyResponseDTO queryHsBdcdyByYsfwbm(@RequestParam("ysfwbm")String ysfwbm,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return queryHsBdcdyByYsfwbmWithHslx(ysfwbm, null, "",qjgldm);
    }

    /**
     * @param ysfwbm
     * @param hslx
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据ysFwbm查询户室不动单元基本信息(可根据户室类型做判断)
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据ysFwbm查询户室不动单元基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "ysfwbm", value = "房屋编码", required = false, dataType = "String", paramType = "query")
            , @ApiImplicitParam(name = "hslx", value = "户室类型", dataType = "String", paramType = "query")})
    public BdcdyResponseDTO queryHsBdcdyByYsfwbmWithHslx(@RequestParam(value = "ysfwbm", required = false) String ysfwbm,
                                                         @RequestParam(name = "hslx", required = false) String hslx,
                                                         @RequestParam(name = "zl", required = false) String zl,
                                                         @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return bdcdyService.queryHsBdcdyByYsFwbm(ysfwbm, hslx, zl);
    }

    /**
     * @param bdcdyh
     * @param hslx
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询户室信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询户室信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")
            , @ApiImplicitParam(name = "hslx", value = "户室类型", dataType = "String", paramType = "query")})
    public FwHsDO queryHsByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,
                                  @RequestParam(name = "hslx",required = false)String hslx,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return bdcdyService.queryHsBdcdy(bdcdyh,hslx);
    }

    /**
     * @param fwbm
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.FwBdcdyDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FWBM查询户室类型的不动产单元
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据FWBM查询户室类型的不动产单元")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwbm", value = "房屋编码", required = true, dataType = "String", paramType = "query")})
    public List<FwBdcdyDTO> listFwBdcdyByFwbm(@RequestParam(name = "fwbm") String fwbm,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return bdcdyService.queryFwBdcdyByFwbm(fwbm);
    }

    /**
     * @param ybdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 合肥版本中 FW_HS FW_LJZ 表中包含ybdcdyh
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "合肥版本中 FW_HS FW_LJZ 表中包含ybdcdyh")
    @ApiImplicitParams({@ApiImplicitParam(name = "ybdcdyh", value = "原不动产单元号", required = true, dataType = "String", paramType = "query")})
    public BdcdyResponseDTO queryFwHsAndFwLjzByYbdcdyh(String ybdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return bdcdyService.queryHsLjzBdcdyByYbdcdyh(ybdcdyh);
    }

    /**
     * @param pageable
     * @param paramJson
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 为外网提供的 分页查询房产信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "为外网提供的分页查询房产信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<Map> listHsForWwsqByPage(Pageable pageable, @RequestParam String paramJson) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(paramJson)) {
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return bdcdyService.listHsForWwsq(pageable,paramMap);
    }

    /**
     * @param pageable
     * @param paramJson
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询户室信息，实测预测
     * @date : 2020/12/16 18:20
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询户室信息，实测预测")
    @ApiImplicitParams({@ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<Map> listScYcHsxxByPage(Pageable pageable,@RequestParam String paramJson) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(paramJson)) {
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return bdcdyService.listScYcHsByPage(pageable,paramMap);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "初始化建设用地量化信息")
    public BdcdyResponseDTO initJsydLhxx(@RequestBody BdcdyResponseDTO bdcdyResponseDTO){
         return bdcdyService.initJsydLhxx(bdcdyResponseDTO);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据bdcdyh查询bdcdyfwlx")
    public String queryBdcdyfwlx(@PathVariable(name = "bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return bdcdyService.queryBdcdyfwlx(bdcdyh);

    }

    /**
     * @param houseid
     * @param bdcdyfwlx
     * @param qjgldm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据house——id先查户室数据，再查相关信息
     * @date : 2022/5/16 15:08
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据houseid查询户室信息")
    public BdcdyResponseDTO queryBdcdyByHoseId(@RequestParam(value = "bdcdyh", required = false) String bdcdyh, @RequestParam(value = "houseid", required = false) String houseid, @RequestParam(name = "bdcdyfwlx", required = false) String bdcdyfwlx, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return bdcdyService.queryBdcdyByHouseId(houseid, bdcdyh, bdcdyfwlx);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据houseid、bdcdyh、zl查询户室信息")
    public List<BdcdyResponseDTO> queryBdcdyByHouseId(@RequestParam(name = "houseid",required = false) String houseid,
                                                      @RequestParam(name = "bdcdyh",required = false) String bdcdyh,
                                                      @RequestParam(name = "zl",required = false) String zl) {
        return bdcdyService.queryBdcdy(houseid,bdcdyh,zl);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据字典代码查询名称")
    public List<SZdTdsyqlxDO> getTdsyqlxZd(String dm) {
        Example example = new Example(SZdTdsyqlxDO.class);
        example.createCriteria().andEqualTo("dm", dm);
        List<SZdTdsyqlxDO> sZdTdsyqlxDOS = entityMapper.selectByExample(example);
        return sZdTdsyqlxDOS;
    }

}
