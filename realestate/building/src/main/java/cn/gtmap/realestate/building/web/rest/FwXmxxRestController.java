package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.FwKService;
import cn.gtmap.realestate.building.core.service.FwXmxxService;
import cn.gtmap.realestate.building.util.ErrorCodeConstants;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import cn.gtmap.realestate.common.core.dto.building.XmxxResponseDTO;
import cn.gtmap.realestate.common.core.service.rest.building.FwXmxxRestService;
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
 * @version 1.0  2018/10/30
 * @description
 */
@RestController
@Api(tags = "项目信息服务接口")
public class FwXmxxRestController extends BaseController implements FwXmxxRestService {
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 项目信息service
     */
    @Autowired
    private FwXmxxService fwXmxxService;

    @Autowired
    private FwKService fwKService;

    /**
     * @param pageable
     * @param paramJson
     * @return java.lang.Object
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询项目信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询项目信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmmc", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "zl", value = "坐落", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lszd", value = "隶属宗地", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<XmxxResponseDTO> listXmxxByPageJson(Pageable pageable,
                                                    @RequestParam(value = "paramJson", required = false) String paramJson) {
        Map<String,String> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(paramJson)){
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return fwXmxxService.listXmxxByPage(pageable,paramMap);
    }

    /**
     * @param fwXmxxDO
     * @return cn.gtmap.realestate.common.core.domain.building.FwXmxxDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 新增项目信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "新增项目信息")
    public FwXmxxDO insertFwXmxx(@RequestBody FwXmxxDO fwXmxxDO,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (!CheckEntityUtils.checkFk(fwXmxxDO)) {
            errorException(ErrorCodeConstants.PARAM_ERROR);
        }
        return fwXmxxService.insertFwXmxx(fwXmxxDO);
    }

    /**
     * @param fwXmxxDO
     * @param updateNull true表示空字段更新，false表示空字段不更新
     * @return java.lang.Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改项目信息(预留type字段, 根据type确定是修改还是变更)
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "修改项目信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "updateNull", value = "空字段不更新",dataType = "boolean", paramType = "query")})
    public Integer updateFwXmxx(@RequestBody FwXmxxDO fwXmxxDO,@RequestParam(name = "updateNull",required = false) boolean updateNull,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (!CheckEntityUtils.checkPk(fwXmxxDO)) {
            errorException(ErrorCodeConstants.PARAM_ERROR);
        }
        return fwXmxxService.updateFwXmxxWithChangeBdcdyh(fwXmxxDO, updateNull);
    }

    /**
     * @param fwXmxxIndex
     * @return java.lang.Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 删除项目信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "删除项目信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwXmxxIndex", value = "主键id", required = true, dataType = "String", paramType = "path")})
    public Integer deleteXmxxByFwXmxxIndex(@PathVariable("fwXmxxIndex") String fwXmxxIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwXmxxService.deleteFwXmxxByFwXmxxIndex(fwXmxxIndex,true);
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwXmxxIndexList
     * @param delFwK
     * @return void
     * @description 批量删除项目信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "批量删除项目信息")
    public void batchDelFwXmxxIndex(@RequestParam(value = "fwXmxxIndexList", required = false) List<String> fwXmxxIndexList,
                                    @RequestParam(value = "delFwK", required = false) boolean delFwK,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        fwXmxxService.batchDelFwXmxx(fwXmxxIndexList,delFwK);
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwXmxxIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwXmxxDO
     * @description 根据主键查询项目信息实体
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据主键查询项目信息实体")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwXmxxIndex", value = "主键id", required = true, dataType = "String", paramType = "path")})
    public FwXmxxDO queryXmxxByFwXmxxIndex(@PathVariable("fwXmxxIndex") String fwXmxxIndex, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwXmxxService.queryXmxxByPk(fwXmxxIndex);
    }

    /**
     * @param bdcdyh
     * @param qjgldm
     * @return cn.gtmap.realestate.common.core.domain.building.FwXmxxDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据bdcdyh查询项目信息实体
     */
    @Override
    public FwXmxxDO queryXmxxByBdcdyh(@RequestParam(name = "bdcdyh", required = false) String bdcdyh,
                                      @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwXmxxService.queryXmxxByBdcdyh(bdcdyh);
    }

    /**
     * @param fwXmxxIndex
     * @return java.lang.Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 项目信息关联逻辑幢
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "项目信息关联逻辑幢")
    public Integer relevanceLjz(@RequestParam(name = "fwXmxxIndex",required = false) String fwXmxxIndex,
                                @RequestParam(name = "fwDcbIndex",required = false) String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwXmxxService.relevanceLjz(fwXmxxIndex,fwDcbIndex);
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwDcbIndex
     * @return java.lang.Integer
     * @description 项目信息取消关联逻辑幢
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "项目信息取消关联逻辑幢")
    public Integer cancelRelevanceLjz(@RequestParam(name = "fwDcbIndex",required = false) String fwDcbIndex,
                                      @RequestParam(name = "bdcdyfwlx",required = false) String bdcdyfwlx,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwXmxxService.cancelRelevanceLjz(fwDcbIndex,bdcdyfwlx);
    }

    /**
     * @param fwXmxxDO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目信息实体查询有效的不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据项目信息实体查询有效的不动产单元号")
    public List<String> listValidBdcdyhByXmxxDO(@RequestBody FwXmxxDO fwXmxxDO,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwXmxxService.listValidBdcdyhByXmxxDO(fwXmxxDO);
    }

    /**
     * @param fwXmxxIndexList
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目信息主键list查询有效的不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据项目信息主键list查询有效的不动产单元号")
    public List<String> listValidBdcdyhByXmxxList(@RequestBody List<String> fwXmxxIndexList,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwXmxxService.listValidBdcdyhByXmxxList(fwXmxxIndexList);
    }

    /**
     * @param fwXmxxIndex
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目信息主键查询有效的不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据项目信息主键查询有效的不动产单元号")
    public List<String> listValidBdcdyhByXmxxIndex(@PathVariable("fwXmxxIndex") String fwXmxxIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwXmxxService.listValidBdcdyhByXmxxIndex(fwXmxxIndex);
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
        return fwXmxxService.listValidBdcdyhByJson(jsonData);
    }
}