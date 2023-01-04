package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.FwHsService;
import cn.gtmap.realestate.building.service.FwhsYwService;
import cn.gtmap.realestate.building.util.ErrorCodeConstants;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.dto.building.DjdcbFwhsResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.FwHsHbZhsRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.FwHsHouseIdDTO;
import cn.gtmap.realestate.common.core.dto.building.FwhsBatchUpdateRequestDTO;
import cn.gtmap.realestate.common.core.qo.building.BdcTddysfxxQO;
import cn.gtmap.realestate.common.core.service.rest.building.FwHsRestService;
import cn.gtmap.realestate.common.core.vo.building.BatchUpdateFwhsVO;
import cn.gtmap.realestate.common.util.CheckEntityUtils;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/6
 * @description 户室服务
 */
@RestController
@Api(tags = "户室服务接口")
public class FwHsRestController extends BaseController implements FwHsRestService {
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋户室服务接口
     */
    @Autowired
    private FwHsService fwHsService;

    @Autowired
    private FwhsYwService fwhsYwService;

    /**
     * @param fwHsDO
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 新增户室服务
     */
    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "新增户室服务")
    public FwHsDO insertFwHs(@RequestBody FwHsDO fwHsDO,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (!CheckEntityUtils.checkFk(fwHsDO)) {
            errorException(ErrorCodeConstants.PARAM_ERROR);
        } else {
            return fwHsService.insertFwHs(fwHsDO);
        }
        return null;
    }

    /**
     * @param fwHsDO
     * @param updateNull true表示空字段更新，false表示空字段不更新
     * @return java.lang.Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改户室信息服务
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "修改户室信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "updateNull", value = "空字段不更新", dataType = "boolean", paramType = "query")})
    public Integer updateFwHs(@RequestBody FwHsDO fwHsDO, @RequestParam(name = "updateNull", required = false) boolean updateNull,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (!CheckEntityUtils.checkPkAndFk(fwHsDO)) {
            errorException(ErrorCodeConstants.PARAM_ERROR);
        }
        return fwHsService.updateFwHsWithChangeBdcdyh(fwHsDO, updateNull);
    }

    /**
     * @param fwHsIndex
     * @return java.lang.Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据主键删除户室信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据主键删除户室信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwHsIndex", value = "主键id", required = true, dataType = "String", paramType = "path")})
    public Integer deleteHsByFwHsIndex(@PathVariable("fwHsIndex") String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHsService.deleteHsByFwHsIndex(fwHsIndex,true);
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询户室基本信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询房屋户室基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public FwHsDO queryFwhsByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHsService.queryFwhsByBdcdyh(bdcdyh);
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsHouseIdDTO
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 根据BDCDYH查询户室houseid信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询房屋户室基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public FwHsHouseIdDTO queryFwhsHouseIdByBdcdyh(@PathVariable("bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHsService.queryFwhsHouseIdByBdcdyh(bdcdyh);
    }

    /**
     * @param bdcdyhList
     * @return
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询流程状态")
    public Map<String, List<String>> queryLcztByBdcdyh(@RequestBody List<String> bdcdyhList) {
        return fwHsService.queryLcztByBdcdyh(bdcdyhList);
    }

    /**
     * @param bdcdyhList
     * @return
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据BDCDYH查询户室流程状态
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询房屋户室流程状态")
    public List<String> queryFwhsLcztByBdcdyh(@RequestBody List<String> bdcdyhList) {
        return fwHsService.queryFwhsLcztByBdcdyh(bdcdyhList);
    }

    /**
     * @param zl
     * @param qjgldm
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据zl查询户室基本信息
     */
    @Override
    public FwHsDO queryFwhsByZlAndBdcdyh(@RequestParam(name ="zl")String zl, @RequestParam(name = "bdcdyh", required = false)String bdcdyh,@RequestParam(name = "qjgldm", required = false)String qjgldm) {
        return fwHsService.queryFwhsByZlAndBdcdyh(zl,bdcdyh);
    }

    /**
     * @param fwDcbIndex
     * @param fwHsIndexList
     * @return java.lang.Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改房屋户室逻辑幢
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "修改房屋户室逻辑幢")
    public void updateFwHsLjz(@RequestParam(value = "fwHsIndexList", required = false) List<String> fwHsIndexList, @RequestParam(value = "fwDcbIndex", required = false) String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        fwHsService.updateFwHsLjz(fwHsIndexList, fwDcbIndex);
    }

    /**
     * @param fcdah
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FCDAH查询FWHS
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据FCDAH查询FWHS")
    @ApiImplicitParams({@ApiImplicitParam(name = "fcdah", value = "房产档案号", required = true, dataType = "String", paramType = "path")})
    public List<FwHsDO> listFwhsByFcdah(@PathVariable("fcdah") String fcdah,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHsService.listKyFwhsByFcdah(fcdah);
    }

    /**
     * @param fwbm
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FWBM 查询 FWHS
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据FWBM查询FWHS")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwbm", value = "房屋编码", required = true, dataType = "String", paramType = "path")})
    public List<FwHsDO> listFwhsByFwbm(@PathVariable("fwbm") String fwbm,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHsService.listKyFwhsByFwbm(fwbm);
    }

    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据 fwDcbIndex 查询 FWHS
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据fwDcbIndex查询FWHS")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwDcbIndex", value = "房屋逻辑幢主键", required = true, dataType = "String", paramType = "path")})
    public List<FwHsDO> listFwhsByFwDcbIndex(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHsService.listKyFwhsByFwDcbIndex(fwDcbIndex);
    }

    /**
     * @param fwHsIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据主键查询房屋户室
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据主键查询房屋户室")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwHsIndex", value = "房屋户室主键", required = true, dataType = "String", paramType = "path")})
    public FwHsDO queryFwHsByFwHsIndex(@PathVariable("fwHsIndex") String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHsService.queryFwHsByIndex(fwHsIndex);
    }


    /**
     * @param pageable
     * @param paramJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询户室")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paramJson", value = "参数Json", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<FwHsDO> listFwHsByPageJson(Pageable pageable,
                                           @RequestParam(value = "paramJson", required = false) String paramJson) {
        Map<String,String> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(paramJson)){
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return fwHsService.listFwHsByPage(pageable,paramMap);
    }

    /**
     * @param pageable
     * @param paramJson
     * @return Map
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "关联功能分页查询户室")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paramJson", value = "参数Json", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<Map> glListFwHsByPageJson(Pageable pageable, String paramJson) {
        Map<String,String> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(paramJson)){
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return fwhsYwService.glListHsByPage(pageable,paramMap);
    }

    /**
     * @param batchUpdateRequestDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量更新户室
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量更新户室")
    public void batchUpdateFwhs(@RequestBody FwhsBatchUpdateRequestDTO batchUpdateRequestDTO) {
        fwhsYwService.batchUpdate(batchUpdateRequestDTO);
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjdcbFwhsResponseDTO
     * @description 根据不动产单元号查询地籍调查表房屋信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据不动产单元号查询地籍调查表房屋信息")
    public DjdcbFwhsResponseDTO queryDjdcbFwhsByBdcdyh(@RequestParam(value = "bdcdyh", required = false) String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHsService.queryDjdcbFwhsByBdcdyh(bdcdyh);
    }

    /**
     * @param pageable
     * @param paramJson
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询FWZHS
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询户室变更子户室列表")
    public Page<FwHsHbZhsRequestDTO> listHsHbZhsByPageJson(Pageable pageable,
                                                           @RequestParam(value = "paramJson", required = false) String paramJson) {
        Map<String,String> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(paramJson)){
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return fwHsService.listHsHbZhsByPage(pageable,paramMap);
    }

    /**
     * @param fwHsDO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据房屋户室实体查询有效的不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据房屋户室实体查询有效的不动产单元号")
    public List<String> listValidBdcdyhByFwHsDO(@RequestBody FwHsDO fwHsDO,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHsService.listValidBdcdyhByFwHsDO(fwHsDO);
    }

    /**
     * @param fwHsIndexList
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据房屋户室主键集合查询有效的不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据房屋户室主键集合查询有效的不动产单元号")
    public List<String> listValidBdcdyhByFwHsIndexList(@RequestBody List<String> fwHsIndexList,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHsService.listValidBdcdyhByFwHsIndexList(fwHsIndexList);
    }

    /**
     * @param bgbh
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据bgbh查询有效的不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据bgbh查询有效的不动产单元号")
    public List<String> listValidBdcdyhByBgbh(@RequestParam(value = "bgbh", required = false) String bgbh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHsService.listValidBdcdyhByBgbh(bgbh);
    }
    /**
     * @param fwHsIndex
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据fwHsIndex查询有效的不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据fwHsIndex查询有效的不动产单元号")
    public List<String> listValidBdcdyhByFwHsIndex(@PathVariable("fwHsIndex") String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHsService.listValidBdcdyhByFwHsIndex(fwHsIndex);
    }

    /**
     * @param jsonData
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据json查询有效的不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据json查询有效的不动产单元号")
    public List<String> listValidBdcdyhByJson(@RequestBody String jsonData,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHsService.listValidBdcdyhByJson(jsonData);
    }

    /**
     * @param batchUpdateFwhsVO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据批量修改户室vo查询有效的不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据批量修改户室vo查询有效的不动产单元号")
    public List<String> listValidBdcdyhByBatchUpdateFwhsVO(@RequestBody BatchUpdateFwhsVO batchUpdateFwhsVO) {
        return fwHsService.listValidBdcdyhByBatchUpdateFwhsVO(batchUpdateFwhsVO);
    }

    /**
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param fwbm  房屋编码
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @description 只根据房屋编码参数查询房屋户室信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "只根据房屋编码参数查询房屋户室信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwbm", value = "房屋编码", required = true, dataType = "String", paramType = "path")})
    public List<FwHsDO> queryFwhsOnlyByFwbm(@PathVariable("fwbm") String fwbm,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHsService.queryFwhsOnlyByFwbm(fwbm);
    }


    /**
     * @param pageable
     * @param paramJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">gaolining</a>
     * @description 分页查询实测预测户室数据，统一返回fwhsDO
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询户室")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paramJson", value = "参数Json", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<FwHsDO> listFwScYcHsByPageJson(Pageable pageable,
                                               @RequestParam(value = "paramJson", required = false) String paramJson) {
        Map<String, String> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(paramJson)) {
            paramMap = JSONObject.parseObject(paramJson, Map.class);
        }
        return fwHsService.listScYcHsByPage(pageable, paramMap);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量更新土地抵押释放信息")
    public void updateFwhsTddysfxx(@RequestBody BdcTddysfxxQO bdcTddysfxxQO){
        fwHsService.updateFwhsTddysfxx(bdcTddysfxxQO);

    }


}