package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.FwHsHistoryService;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO;
import cn.gtmap.realestate.common.core.dto.building.FwHsBgHistoryDTO;
import cn.gtmap.realestate.common.core.dto.building.FwHsBgHistoryNewDTO;
import cn.gtmap.realestate.common.core.service.rest.building.FwHsHistroyRestService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/4
 * @description 房屋户室历史操作服务
 */
@RestController
@Api(tags = "房屋户室历史操作服务")
public class FwHsHistoryRestController extends BaseController implements FwHsHistroyRestService {
    @Autowired
    private FwHsHistoryService fwHsHistoryService;

    /**
     * @param bglx
     * @param bdcdyh
     * @param bgrq
     * @param pageable return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.SSjHsbgljbDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询户室变更历史记录
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询户室变更历史记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "bglx", value = "变更类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bgrq", value = "变更时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<SSjHsbgljbDO> listHsbgHsitroyByPageJson(Pageable pageable
            , @RequestParam(value = "paramJson", required = false) String paramJson) {
        Map<String,String> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(paramJson)){
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return fwHsHistoryService.listHsbgHsitroyByPageJson(pageable,paramMap);
    }


    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwHsIndex
     * @return java.util.Map
     * @description 查询户室变更记录
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据房屋户室主键查询户室变更记录")
    public List<List<FwHsBgHistoryDTO>> getHsBgHistoryByFwHsIndex(@PathVariable(value = "fwHsIndex")String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHsHistoryService.getHsBgHistory(fwHsIndex);
    }
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return java.util.Map
     * @description 查询户室变更记录（老）不删除
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据不动产单元号查询户室变更记录")
    public List<List<FwHsBgHistoryDTO>> getHsBgHistoryByBdcdyh(@RequestParam(name = "bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHsHistoryService.getHsBgHistoryByBdcdyh(bdcdyh);
    }


    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return java.util.Map
     * @description 查询户室变更记录(新)
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据不动产单元号查询户室变更记录")
    public List<FwHsBgHistoryNewDTO> getHsBgHistoryNewByBdcdyh(@RequestParam(name = "bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm){
        return fwHsHistoryService.getHsBgHistoryNewByBdcdyh(bdcdyh);
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwHsIndex
     * @return java.util.Map
     * @description 查询变更的户室信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询户室变更历史户室记录")
    public FwHsDO getHFwHsByFwHsIndex(String fwHsIndex, boolean last,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHsHistoryService.getHFwHsByFwHsIndexNew(fwHsIndex);
    }


}