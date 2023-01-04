package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.service.BdcdyService;
import cn.gtmap.realestate.building.util.ErrorCodeConstants;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.building.core.service.FwFcqlrService;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjdcbFwQlrResponseDTO;
import cn.gtmap.realestate.common.core.service.rest.building.FwFcqlrRestService;
import cn.gtmap.realestate.common.util.CheckEntityUtils;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/6
 * @description 权利人服务
 */
@RestController
@Api(tags = "权利人服务接口")
public class FwFcqlrRestController extends BaseController implements FwFcqlrRestService {
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 权利人服务接口
     */
    @Autowired
    private FwFcqlrService fwFcqlrService;

    @Autowired
    private BdcdyService bdcdyService;

    /**
     * @param fwFcqlrIndex
     * @return java.lang.Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据主键删除权利人信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据主键删除权利人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwFcqlrIndex", value = "主键id", required = true, dataType = "String", paramType = "path")})
    public Integer deleteFcqlrByFwFcqlrIndex(@PathVariable("fwFcqlrIndex") String fwFcqlrIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwFcqlrService.deleteFcqlrByFwFcqlrIndex(fwFcqlrIndex);
    }

    @Override
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据房屋主键删除权利人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwIndex", value = "主键id", required = true, dataType = "String", paramType = "path")})
    public void deleteFcQlrByFwIndex(@PathVariable("fwIndex")String fwIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        fwFcqlrService.deleteFcqlrByFwIndex(fwIndex);
    }

    /**
     * @param fwFcqlrDOList
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量新增权利人
     */
    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "批量新增权利人")
    public List<FwFcqlrDO> batchInsert(@RequestBody List<FwFcqlrDO> fwFcqlrDOList) {
        if (CollectionUtils.isNotEmpty(fwFcqlrDOList)) {
            boolean validateFlag = fwFcqlrService.validateBatchFk(fwFcqlrDOList);
            if (validateFlag) {
                return fwFcqlrService.batchInsertFwFcQlr(fwFcqlrDOList);
            } else {
                errorException(ErrorCodeConstants.PARAM_ERROR);
            }
        } else {
            errorException(ErrorCodeConstants.PARAM_ERROR);
        }
        return new ArrayList<>(0);
    }

    /**
     * @param fwFcqlrDO
     * @return cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 新增权利人
     */
    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "新增权利人")
    public FwFcqlrDO insertQlr(@RequestBody FwFcqlrDO fwFcqlrDO) {
        if (CheckEntityUtils.checkFk(fwFcqlrDO)) {
            return fwFcqlrService.insertFwFcQlr(fwFcqlrDO);
        } else {
            errorException(ErrorCodeConstants.PARAM_ERROR);
        }
        return null;
    }

    /**
     * @param fwHsIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据房屋户室主键查询权利人
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据房屋id查询权利人")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwHsIndex", value = "房屋户室主键id", required = true, dataType = "String", paramType = "path")})
    public List<FwFcqlrDO> listQlr(@PathVariable("fwHsIndex") String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwFcqlrService.listFwFcQlrByFwIndex(fwHsIndex);
    }

    /**
     * @param bdcdyh
     * @param bdcdyfwlx
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询房屋房产权利人
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询权利人")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")
            , @ApiImplicitParam(name = "bdcdyfwlx", value = "不动产单元房屋类型", required = false, dataType = "String", paramType = "query")})
    public List<FwFcqlrDO> listQlrByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,
                                           @RequestParam(name = "bdcdyfwlx", required = false) String bdcdyfwlx,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        // 先根据BDCDYH查询房屋实体
        BdcdyResponseDTO bdcdy = bdcdyService.queryBdcdy(bdcdyh, bdcdyfwlx);
        // 再根据房屋主键 查询 QLR
        if (bdcdy != null && StringUtils.isNotBlank(bdcdy.getDjid())) {
            return fwFcqlrService.listFwFcQlrByFwIndex(bdcdy.getDjid());
        }
        return new ArrayList<>(0);
    }


    /**
     * @param fwFcqlrDO
     * @param updateNull true表示空字段更新，false表示空字段不更新
     * @return java.lang.Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改权利人信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "修改权利人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "updateNull", value = "空字段不更新", dataType = "boolean", paramType = "query")})
    public Integer updateFwFcQlr(@RequestBody FwFcqlrDO fwFcqlrDO,@RequestParam(name = "updateNull", required = false) boolean updateNull) {
        if (!CheckEntityUtils.checkPkAndFk(fwFcqlrDO)) {
            errorException(ErrorCodeConstants.PARAM_ERROR);
        }
        return fwFcqlrService.updateFwFcQlr(fwFcqlrDO, updateNull);
    }

    /**
     * @param fwFcqlrDOList
     * @param updateNull true表示空字段更新，false表示空字段不更新
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 批量修改权利人信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量修改权利人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "updateNull", value = "空字段不更新", dataType = "boolean", paramType = "query")})
    public void batchUpdateFwFcQlr(@RequestBody List<FwFcqlrDO> fwFcqlrDOList,@RequestParam(name = "updateNull", required = false) boolean updateNull) {
        boolean validateFlag = fwFcqlrService.validateBatchPKAndFk(fwFcqlrDOList);
        if (validateFlag) {
            fwFcqlrService.batchUpdateFwFcQlr(fwFcqlrDOList, updateNull);
        } else {
            errorException(ErrorCodeConstants.PARAM_ERROR);
        }
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param pageable
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.DjdcbFwQlrResponseDTO>
     * @description 分页查询权利人
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询权利人")
    public Page<DjdcbFwQlrResponseDTO> listQlrByPageJson(Pageable pageable, String paramJson) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(paramJson)) {
            map = JSONObject.parseObject(paramJson,Map.class);
        }
        return fwFcqlrService.listQlrByPageJson(pageable,map);
    }


}