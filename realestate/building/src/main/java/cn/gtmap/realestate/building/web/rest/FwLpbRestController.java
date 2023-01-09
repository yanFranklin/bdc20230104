package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.config.lpb.LpbConfig;
import cn.gtmap.realestate.building.core.bo.ColumnBO;
import cn.gtmap.realestate.building.core.bo.LpbConfigBO;
import cn.gtmap.realestate.building.core.resource.FwHsResouce;
import cn.gtmap.realestate.building.core.resource.FwYchsResource;
import cn.gtmap.realestate.building.core.resource.FwhsQlrResource;
import cn.gtmap.realestate.building.core.resource.LpbResource;
import cn.gtmap.realestate.building.core.service.FwFcqlrService;
import cn.gtmap.realestate.building.core.service.FwHsService;
import cn.gtmap.realestate.building.core.service.FwLjzService;
import cn.gtmap.realestate.building.core.service.FwYcHsService;
import cn.gtmap.realestate.building.service.LpbExportService;
import cn.gtmap.realestate.building.service.LpbImportService;
import cn.gtmap.realestate.building.util.LpbInfoTypeEnum;
import cn.gtmap.realestate.building.util.LpbUtils;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.building.FwhsQlrDTO;
import cn.gtmap.realestate.common.core.dto.building.ImportLpbRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.ResourceDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.rest.building.LpbRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-10-31
 * @description 楼盘表展现服务
 */
@RestController
@Api(tags = "楼盘表服务接口")
public class FwLpbRestController extends BaseController implements LpbRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FwLpbRestController.class);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 户室服务
     */
    @Autowired
    private FwHsService fwHsService;

    @Autowired
    private FwYcHsService fwYcHsService;

    @Autowired
    private FwLjzService fwLjzService;

    @Autowired
    private LpbImportService lpbImportService;

    @Autowired
    private LpbExportService lpbExportService;

    @Autowired
    private LpbConfig lpbConfig;

    @Autowired
    FwFcqlrService fwFcqlrService;

    /**
     * @param code
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 刷新楼盘表配置
     */
    @Override
    @ApiOperation(value = "刷新楼盘表配置")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "配置标识", required = true, dataType = "string", paramType = "path")})
    public void refreshLpbConfig(@RequestParam(name = "code", required = false) String code) {
        if (StringUtils.isNotBlank(code)) {
            LpbConfig.initLpbConfigByCode(code, "");
        } else {
            Map<String, LpbConfigBO> configMap = LpbConfig.getConfigMap();
            if (MapUtils.isNotEmpty(configMap)) {
                for (String key : configMap.keySet()) {
                    LpbConfig.initLpbConfigByCode(key, "");
                }
            }
        }
    }

    /**
     * @param fwDcbIndex
     * @param code
     * @return ResourceDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询楼盘表数据
     */
    @Override
    @ApiOperation(value = "查询楼盘表数据")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "配置标识", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "fwDcbIndex", value = "逻辑幢主键", required = true, dataType = "string", paramType = "path")})
    public ResourceDTO queryFwHsListByFwDcbIndex(@PathVariable("fwDcbIndex") String fwDcbIndex
            , @PathVariable("code") String code,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (StringUtils.isBlank(fwDcbIndex)) {
            throw new AppException("逻辑幢主键为空!");
        }
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwDcbIndex);
        List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwDcbIndex);
        if (fwLjzDO != null) {
            FwHsResouce fwHsResouce = new FwHsResouce(fwHsDOList);
            LpbResource lpbResource = new LpbResource(fwLjzDO, fwHsResouce);
            return lpbResource.withCode(code).convertDTO();
        }
        return null;
    }


    /**
     * @param fwDcbIndex
     * @param code
     * @return ResourceDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询楼盘表数据
     */
    @Override
    @ApiOperation(value = "查询预测楼盘表数据")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "配置标识", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "fwDcbIndex", value = "逻辑幢主键", required = true, dataType = "string", paramType = "path")})
    public ResourceDTO queryFwYchsListByFwDcbIndex(@PathVariable("fwDcbIndex") String fwDcbIndex
            , @PathVariable("code") String code,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwDcbIndex);
        List<FwYchsDO> fwYchsDOList = fwYcHsService.queryYchsByFwDcbIndex(fwDcbIndex);
        if (fwLjzDO != null) {
            FwYchsResource fwYchsResouce = new FwYchsResource(fwYchsDOList);
            LpbResource lpbResource = new LpbResource(fwLjzDO, fwYchsResouce);
            return lpbResource.withCode(code).convertDTO();
        }
        return null;
    }


    /**
     * @param fwHsIndex
     * @param code
     * @return ResourceDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 单个户室查询
     */
    @Override
    @ApiOperation(value = "查询户室基本数据")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "配置标识", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "fwHsIndex", value = "户室表主键", required = true, dataType = "string", paramType = "path")})
    public ResourceDTO queryFwHsByIndex(@PathVariable("fwHsIndex") String fwHsIndex, @PathVariable("code") String code,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        FwHsDO fwHsDO = fwHsService.queryFwHsByIndex(fwHsIndex);
        if (fwHsDO != null) {
            FwHsResouce fwHsResouce = new FwHsResouce(fwHsDO);
            return fwHsResouce.withCode(code).convertDTO();
        }
        return null;
    }

    /**
     * @param fwDcbIndex
     * @param code
     * @param qjgldm
     * @return ResourceDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从户室基本信息实体查询预测信息
     */
    @Override
    public ResourceDTO queryFwYcHsByIndexInFwhs(@PathVariable("fwDcbIndex") String fwDcbIndex,
                                                @PathVariable("code") String code,
                                                @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        List<FwHsDO> fwHsDOS = fwHsService.queryFwycHsByIndexAndScmj(fwDcbIndex);
        if (CollectionUtils.isNotEmpty(fwHsDOS)) {
            List<FwYchsDO> fwYchsDOList = new ArrayList<>();
            for (FwHsDO fwHsDO : fwHsDOS) {
                FwYchsDO fwYchsDO = new FwYchsDO();
                BeanUtils.copyProperties(fwHsDO,fwYchsDO);
                fwYchsDOList.add(fwYchsDO);
            }
            FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwDcbIndex);
            FwYchsResource fwYchsResouce = new FwYchsResource(fwYchsDOList);
            LpbResource lpbResource = new LpbResource(fwLjzDO, fwYchsResouce);
            return lpbResource.withCode(code).convertDTO();
        }
        return null;
    }

    /**
     * @return java.lang.Integer
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 导入楼盘表数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "导入楼盘表数据")
    public void importLpbInfo(@RequestBody ImportLpbRequestDTO importLpbRequestDTO) {
        lpbImportService.lpbImport(LpbUtils.getLjzInfoByExcel(importLpbRequestDTO),
                importLpbRequestDTO.getFgyyhs(), LpbUtils.getfwhsAndFwQlrList(importLpbRequestDTO.getLpbList()));
    }

    /**
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 导入预测楼盘表数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "导入预测楼盘表数据")
    public void importYcLpbInfo(@RequestBody ImportLpbRequestDTO importLpbRequestDTO) {
        lpbImportService.lpbYcImport(LpbUtils.getLjzInfoByExcel(importLpbRequestDTO),
                importLpbRequestDTO.getFgyyhs(), LpbUtils.getfwychsAndFwQlrList(importLpbRequestDTO.getLpbList()));
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "导入实测面积")
    public void importScmj(@RequestBody ImportLpbRequestDTO importLpbRequestDTO) {
        lpbImportService.scmjImport(importLpbRequestDTO.getFwDcbIndex(), LpbUtils.getfwhsList(importLpbRequestDTO.getLpbList()));
    }

    /**
     * @param fwDcbIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 导出楼盘表数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "导出楼盘表数据")
    public List<Map<String, Object>> exportLpb(@RequestParam(name = "fwDcbIndex", required = false) String fwDcbIndex,
                                               @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return lpbExportService.exportLpb(fwDcbIndex);
    }

    /**
     * @param fwDcbIndex
     * @param code
     * @return cn.gtmap.realestate.common.core.dto.building.ResourceDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    @ApiOperation(value = "查询楼盘表数据幢维度配置信息")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "配置标识", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "fwDcbIndex", value = "逻辑幢主键", required = true, dataType = "string", paramType = "path")})
    public ResourceDTO getLpbConfigInfoResource(@PathVariable("fwDcbIndex") String fwDcbIndex,
                                                @PathVariable("code") String code,
                                                @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwDcbIndex);
        if (fwLjzDO != null) {
            LpbResource lpbResource = new LpbResource(fwLjzDO);
            return lpbResource.withCode(code).convertDTO();
        }
        return null;
    }


    /**
     * @param fwDcbIndex
     * @param code
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.ResourceDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    @ApiOperation(value = "查询楼盘表数据户室资源列表")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "配置标识", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "fwDcbIndex", value = "逻辑幢主键", required = true, dataType = "string", paramType = "path")})
    public List<ResourceDTO> getFwHsResList(@PathVariable("fwDcbIndex") String fwDcbIndex,
                                            @PathVariable("code") String code,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS");
        LOGGER.info("请求户室列表资源当前时间{}", simpleDateFormat.format(new Date()));
        List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwDcbIndex);
        LOGGER.info("户室资源列表长度:{},获取户室数据结束{},开始封装户室列表数据", fwHsDOList.size(), simpleDateFormat.format(new Date()));
        if (CollectionUtils.isNotEmpty(fwHsDOList)) {
            FwHsResouce fwHsResouce = new FwHsResouce(fwHsDOList);
            LOGGER.info("新建户室资源{}", simpleDateFormat.format(new Date()));
            fwHsResouce.withCode(code);
            List<ResourceDTO> resultList = fwHsResouce.getInnerResourceDTO();
            LOGGER.info("请求户室列表资源结束{}", simpleDateFormat.format(new Date()));
            return resultList;
        }
        return Collections.emptyList();
    }

    /**
     * @param fwDcbIndex
     * @param code
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.ResourceDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询幢下的预测户室资源列表
     */
    @Override
    @ApiOperation(value = "查询幢下的预测户室资源列表")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "配置标识", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "fwDcbIndex", value = "逻辑幢主键", required = true, dataType = "string", paramType = "path")})
    public List<ResourceDTO> getFwYchsResList(@PathVariable("fwDcbIndex") String fwDcbIndex,
                                              @PathVariable("code") String code,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        List<FwYchsDO> fwYchsDOList = fwYcHsService.queryYchsByFwDcbIndex(fwDcbIndex);
        if (CollectionUtils.isNotEmpty(fwYchsDOList)) {
            FwYchsResource fwYchsResouce = new FwYchsResource(fwYchsDOList);
            return fwYchsResouce.withCode(code).getInnerResourceDTO();
        }
        return Collections.emptyList();
    }

    /**
     * @return List<Map < String, Object>>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 查询楼盘表状态颜色配置
     */
    @Override
    public Map<String, Object> getLpbColorPz() {
        Map<String, Object> map = new HashMap();
        //先用accept的处理
        List<ColumnBO> columnBOList = lpbConfig.getColumnList("accept", LpbConfig.FLAG_LJZ);
        if (CollectionUtils.isNotEmpty(columnBOList)) {
            for (int i = 0; i < columnBOList.size(); i++) {
                ColumnBO columnBO = columnBOList.get(i);
                if (columnBO != null) {
                    if (StringUtils.equals(LpbInfoTypeEnum.CONSTANT.type, columnBO.getType()) &&
                            StringUtils.isNotBlank(columnBO.getValue()) &&
                            columnBO.getValue().indexOf("_color") > -1) {
                        map.put(columnBO.getValue(), columnBO);
                    }
                }
            }
        }
        return map;
    }

    /**
     * @param fwDcbIndex
     * @param code
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.ResourceDTO>
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 预测户室信息包含权利人
     */
    @Override
    @ApiOperation(value = "查询楼盘表数据户室资源列表")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "配置标识", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "fwDcbIndex", value = "逻辑幢主键", required = true, dataType = "string", paramType = "path")})
    public List<ResourceDTO> getFwYchsWithQlr(@PathVariable("fwDcbIndex") String fwDcbIndex,
                                              @PathVariable("code") String code,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        LOGGER.info("请求户室列表资源");
        List<FwhsQlrDTO> fwhsAndFwQlrList = fwYcHsService.queryYchsWithQlr(fwDcbIndex);
        LOGGER.info("户室资源列表长度:{}", fwhsAndFwQlrList.size());
        if (CollectionUtils.isNotEmpty(fwhsAndFwQlrList)) {
            FwhsQlrResource fwhsQlrResource = new FwhsQlrResource(fwhsAndFwQlrList);
            fwhsQlrResource.withCode(code);
            List<ResourceDTO> resultList = fwhsQlrResource.getInnerResourceDTO();
            LOGGER.info("请求户室列表资源结束");
            return resultList;
        }
        return Collections.emptyList();
    }


    /**
     * @param fwDcbIndex
     * @param code
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.ResourceDTO>
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 户室信息包含权利人
     */
    @Override
    @ApiOperation(value = "查询楼盘表数据户室资源列表")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "配置标识", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "fwDcbIndex", value = "逻辑幢主键", required = true, dataType = "string", paramType = "path")})
    public List<ResourceDTO> getFwHsResListWithQlr(@PathVariable("fwDcbIndex") String fwDcbIndex,
                                                   @PathVariable("code") String code,
                                                   @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        LOGGER.info("请求户室列表资源");
        List<FwhsQlrDTO> fwhsAndFwQlrList = fwHsService.listFwhsWithQlr(fwDcbIndex);
        LOGGER.info("户室资源列表长度:{}", fwhsAndFwQlrList.size());
        if (CollectionUtils.isNotEmpty(fwhsAndFwQlrList)) {
            FwhsQlrResource fwhsQlrResource = new FwhsQlrResource(fwhsAndFwQlrList);
            fwhsQlrResource.withCode(code);
            List<ResourceDTO> resultList = fwhsQlrResource.getInnerResourceDTO();
            LOGGER.info("请求户室列表资源结束");
            return resultList;
        }
        return Collections.emptyList();
    }
}
