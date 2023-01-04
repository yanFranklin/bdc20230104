package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.FwYcHsService;
import cn.gtmap.realestate.building.service.YcScHsGlService;
import cn.gtmap.realestate.building.util.ErrorCodeConstants;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.building.YcHsZtResDTO;
import cn.gtmap.realestate.common.core.dto.building.YcScHsGlRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.YcScHsZzglRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.YchsAndQlrResponseDTO;
import cn.gtmap.realestate.common.core.service.rest.building.FwYcHsRestService;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018/11/10
 * @description 预测户室服务
 */
@RestController
@Api(tags = "预测户室服务接口")
public class FwYcHsRestController extends BaseController implements FwYcHsRestService {

    @Autowired
    private FwYcHsService fwYcHsService;
    @Autowired
    private YcScHsGlService ycScHsGlService;

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwYchsDO
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据BDCDYH查询预测户室基本信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询房屋预测户室基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public FwYchsDO queryFwYcHsByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwYcHsService.queryFwYcHsByBdcdyh(bdcdyh);
    }

    /**
     * @param fwYchsDO
     * @return cn.gtmap.realestate.common.core.domain.building.FwYchsDO
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 新增预测户室服务
     */
    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "新增预测户室服务")
    public FwYchsDO insertFwYcHs(@RequestBody FwYchsDO fwYchsDO,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (!CheckEntityUtils.checkFk(fwYchsDO)) {
            errorException(ErrorCodeConstants.PARAM_ERROR);
        } else {
            return fwYcHsService.insertFwYcHs(fwYchsDO);
        }
        return null;
    }

    /**
     * @param fwYchsDO
     * @param updateNull true表示空字段更新，false，表示空字段不更新
     * @return java.lang.Integer
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 修改预测户室信息服务
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "修改预测户室信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "updateNull", value = "空字段更新", dataType = "boolean", paramType = "query")})
    public Integer updateFwYcHs(@RequestBody FwYchsDO fwYchsDO, boolean updateNull,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (!CheckEntityUtils.checkPkAndFk(fwYchsDO)) {
            errorException(ErrorCodeConstants.PARAM_ERROR);
        }
        return fwYcHsService.updateFwYchsWithChangeBdcdyh(fwYchsDO, updateNull);
    }

    /**
     * @param fwHsIndex
     * @return java.lang.Integer
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据主键删除预测户室信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据主键删除预测户室信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwHsIndex", value = "主键id", required = true, dataType = "String", paramType = "path")})
    public Integer deleteYcHsByFwHsIndex(@PathVariable("fwHsIndex") String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwYcHsService.deleteYcHsByFwHsIndex(fwHsIndex,true);
    }

    /**
     * @param paramJson
     * @param pageable
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.FwYchsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询预测户室信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询预测户室信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<FwYchsDO> listYchsByPage(Pageable pageable,
                                         @RequestParam(value = "paramJson", required = false) String paramJson) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(paramJson)) {
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return fwYcHsService.listYchsByPage(pageable, paramMap);
    }

    /**
     * @param pageable
     * @param paramJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.FwYchsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "关联功能分页查询预测户室信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<Map> glListYchsByPage(Pageable pageable, String paramJson) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(paramJson)) {
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return fwYcHsService.glListYchsByPage(pageable, paramMap);
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.YchsAndQlrResponseDTO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据BDCDYH查询预测户室和权利人信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询预测户室和权利人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public YchsAndQlrResponseDTO queryYchsAndQlrByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwYcHsService.queryYchsAndQlrByBdcdyh(bdcdyh);
    }

    /**
     * @param fwHsIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwYchsDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据主键查询预测户室
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据主键查询房屋预测户室")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwHsIndex", value = "房屋户室主键", required = true, dataType = "String", paramType = "path")})
    public FwYchsDO queryFwYcHsByFwHsIndex(@PathVariable("fwHsIndex") String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwYcHsService.queryFwYcHsByFwHsIndex(fwHsIndex);
    }

    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwYchsDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据逻辑幢主键查询预测户室
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据逻辑幢主键查询预测户室")
    public List<FwYchsDO> listFwYchsByFwDcbIndex(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwYcHsService.queryYchsByFwDcbIndex(fwDcbIndex);
    }

    /**
     * @param ycScHsGlDTO
     * @return
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 预测实测户室关联
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "预测实测户室关联")
    public void ycscHsGl(@RequestBody YcScHsGlRequestDTO ycScHsGlDTO) {
        ycScHsGlService.ycscHsGl(ycScHsGlDTO);
    }

    /**
     * @param ycScHsGlDTO
     * @return
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 预测实测户室取消关联
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "预测实测户室取消关联")
    public void ycscHsQxGl(@RequestBody YcScHsGlRequestDTO ycScHsGlDTO) {
        ycScHsGlService.ycscHsQxGl(ycScHsGlDTO);
    }

    /**
     * @param fwDcbIndex
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 整幢取消关联
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "整幢取消关联")
    public void ycscZzQxgl(@PathVariable("fwDcbIndex")String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        ycScHsGlService.ycscZzQxgl(fwDcbIndex);
    }

    /**
     * @param ycScHsZzglRequestDTO
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 整幢关联
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "预测实测户室整幢关联")
    public void ycscZzgl(@RequestBody YcScHsZzglRequestDTO ycScHsZzglRequestDTO) {
        ycScHsGlService.zzgl(ycScHsZzglRequestDTO);
    }

    /**
     * @param fwDcbIndex
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据房屋fwdcbindex查询预测户室的信息和状态
     * @date : 2020/12/10 10:12
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据主键查询房屋预测户室")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwDcbIndex", value = "房屋户室主键", required = true, dataType = "String", paramType = "path")})
    public List<YcHsZtResDTO> listYchsZt(@PathVariable(name = "fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwYcHsService.listFwychsZt(fwDcbIndex,qjgldm);
    }

    /**
     *
     * @param YSFWBM
     * @return
     */
    @Override
    public List<FwYchsDO> listFwYchsByYsfwbm(@RequestParam(value = "YSFWBM", required = true) String YSFWBM) {
        if(StringUtils.isBlank(YSFWBM)){
            return Collections.emptyList();
        }
        return fwYcHsService.listFwYchsByYsfwbm(YSFWBM);
    }
}
