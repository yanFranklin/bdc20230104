package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.FwHsService;
import cn.gtmap.realestate.building.core.service.FwYcHsService;
import cn.gtmap.realestate.building.core.service.FwZhsService;
import cn.gtmap.realestate.building.util.ErrorCodeConstants;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.domain.building.FwZhsDO;
import cn.gtmap.realestate.common.core.service.rest.building.FwZhsRestService;
import cn.gtmap.realestate.common.util.CheckEntityUtils;
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
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/6
 * @description 房屋子户室服务
 */
@RestController
@Api(tags = "子户室服务接口")
public class FwZhsRestController extends BaseController implements FwZhsRestService {
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 子户室服务接口
     */
    @Autowired
    private FwZhsService fwZhsService;

    @Autowired
    private FwHsService fwHsService;

    @Autowired
    private FwYcHsService fwYcHsService;


    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwZhsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询子户室列表
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询子户室列表")
    public List<FwZhsDO> listFwZhsByBdcdyh(@PathVariable("bdcdyh")String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        String fwHsIndex = "";
        FwHsDO fwHsDO = fwHsService.queryFwhsByBdcdyh(bdcdyh);
        if(fwHsDO == null){
            FwYchsDO fwYchsDO = fwYcHsService.queryFwYcHsByBdcdyh(bdcdyh);
            if(fwYchsDO != null){
                fwHsIndex = fwYchsDO.getFwHsIndex();
            }
        }else{
            fwHsIndex = fwHsDO.getFwHsIndex();
        }
        if(StringUtils.isNotBlank(fwHsIndex)){
            return fwZhsService.listFwZhsByFwHsIndex(fwHsIndex);
        }
        return Collections.emptyList();
    }

    /**
     * @param fwZhsIndexList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量删除子户室
     */
    @Override
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "批量删除子户室")
    public void batchDelFwzhs(@RequestBody List<String> fwZhsIndexList,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if(CollectionUtils.isNotEmpty(fwZhsIndexList)){
            for(String fwZhsIndex : fwZhsIndexList){
                fwZhsService.deleteZhsByFwZhsIndex(fwZhsIndex);
            }
        }
    }

    /**
     * @param fwZhsIndex
     * @return java.lang.Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据主键删除子户室信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据主键删除子户室信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwZhsIndex", value = "主键id", required = true, dataType = "String", paramType = "path")})
    public void deleteZhsByFwZhsIndex(@PathVariable("fwZhsIndex") String fwZhsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        fwZhsService.deleteZhsByFwZhsIndex(fwZhsIndex);
    }

    /**
     * @param fwZhsDO
     * @return cn.gtmap.realestate.common.core.domain.building.FwZhsDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 新增子户室服务
     */
    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "新增子户室服务")
    public FwZhsDO insertZhs(@RequestBody FwZhsDO fwZhsDO,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (!CheckEntityUtils.checkFk(fwZhsDO)) {
            errorException(ErrorCodeConstants.PARAM_ERROR);
        }
        return fwZhsService.insertZhs(fwZhsDO);
    }

    /**
     * @param fwZhsDO
     * @return java.lang.Integer
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 更新子户室 更新null值
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "修改子户室服务")
    public Integer updateZhs(@RequestBody FwZhsDO fwZhsDO,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (!CheckEntityUtils.checkPkAndFk(fwZhsDO)) {
            errorException(ErrorCodeConstants.PARAM_ERROR);
        }
        return fwZhsService.updateZhs(fwZhsDO);
    }

    /**
     * @param fwZhsIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwZhsDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据主键查询子户室
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据主键查询子户室")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwZhsIndex", value = "房屋子户室主键", dataType = "string", paramType = "path")})
    public FwZhsDO getFwzhsByIndex(@PathVariable("fwZhsIndex") String fwZhsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwZhsService.queryFwZhsByPk(fwZhsIndex);
    }


    /**
     * @param fwZhsDOList
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwZhsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量新增子户室
     */
    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "批量新增子户室服务")
    public List<FwZhsDO> batchInsert(@RequestBody List<FwZhsDO> fwZhsDOList,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (CollectionUtils.isNotEmpty(fwZhsDOList)) {
            for (FwZhsDO fwZhsDO : fwZhsDOList) {
                if (!CheckEntityUtils.checkFk(fwZhsDO)) {
                    errorException(ErrorCodeConstants.PARAM_ERROR);
                }
            }
            return fwZhsService.batchInsert(fwZhsDOList);
        } else {
            errorException(ErrorCodeConstants.PARAM_ERROR);
        }
        return new ArrayList<>(0);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "关联子户室服务")
    public void relevanceZhs(@RequestBody List<FwZhsDO> fwZhsDOList,
                             @PathVariable("fwHsIndex") String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        fwZhsService.relevanceZhs(fwZhsDOList, fwHsIndex);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "取消关联子户室服务")
    public void cancelRelevanceZhs(@RequestBody List<FwZhsDO> fwZhsDOList,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        fwZhsService.cancelRelevanceZhs(fwZhsDOList);
    }

    /**
     * @param pageable
     * @param fwHsIndex
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.FwZhsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询子户室
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询子户室")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwHsIndex", value = "房屋户室主键", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<FwZhsDO> listFwZhsByPage(Pageable pageable,
                                         @RequestParam(name = "fwHsIndex", required = false) String fwHsIndex,
                                         @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwZhsService.listByPage(pageable,fwHsIndex);
    }

    /**
     * @param fwZhsDO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据子户室信息实体查询有效的不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据子户室信息实体查询有效的不动产单元号")
    public List<String> listValidBdcdyhByFwZhsDO(@RequestBody FwZhsDO fwZhsDO,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwZhsService.listValidBdcdyhByFwZhsDO(fwZhsDO);
    }

    /**
     * @param fwZhsIndexList
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据子户室主键list查询有效的不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据子户室主键list查询有效的不动产单元号")
    public List<String> listValidBdcdyhByFwZhsIndexList(@RequestParam(value = "fwZhsIndexList", required = false) List<String> fwZhsIndexList,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwZhsService.listValidBdcdyhByFwZhsIndexList(fwZhsIndexList);
    }


}