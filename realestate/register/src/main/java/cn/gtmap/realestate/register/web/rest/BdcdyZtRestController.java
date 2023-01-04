package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.dto.building.BatchBdcdyhSyncZtRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcdyZtDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.rest.register.BdcdyZtRestService;
import cn.gtmap.realestate.register.service.BdcdyZtService;
import cn.gtmap.realestate.register.web.main.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/4/17
 * @description 不动产单元状态
 */
@RestController
@Api(tags = "不动产单元状态服务接口")
public class BdcdyZtRestController extends BaseController implements BdcdyZtRestService {
    @Autowired
    BdcdyZtService bdcdyZtService;

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询不动产单元状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "string", paramType = "path")})
    @Override
    public BdcdyZtDTO queryBdcdyZt(@PathVariable(name = "bdcdyh") String bdcdyh) {
        return bdcdyZtService.queryBdcdyZt(bdcdyh);
    }

    /**
     * @param bdcdyList 不动产单元号List
     * @return List<BdcdyZtDTO> 不动产单元状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量查询不动产单元状态
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量查询不动产单元状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyList", value = "不动产单元号", required = true, dataType = "List", paramType = "body")})
    @Override
    public List<BdcdyZtDTO> queryBdcdyZtList(@RequestBody List<String> bdcdyList) {
        return bdcdyZtService.queryBdcdyZtList(bdcdyList);
    }

    /**
     * @param bdcdyList 不动产单元号 List
     * @return void
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 同步BDCDYH状态
     */
    @Override
    public void syncBdcdyZtByBdcdyh(@RequestBody List<String> bdcdyList,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        bdcdyZtService.syncBdcdyZtByBdcdyh(bdcdyList,qjgldm);
    }

    /**
     * @param bdcdyhList 不动产单元号+不动产类型 List
     * @return cn.gtmap.realestate.common.core.dto.building.BatchBdcdyhSyncZtRequestDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询状态数量
     */
    @Override
    public BatchBdcdyhSyncZtRequestDTO queryBdcdyZtByBdcdyh(@RequestBody List<String> bdcdyhList) {
        if (CollectionUtils.isNotEmpty(bdcdyhList)) {
            return bdcdyZtService.queryBdcdyhSyncZtList(bdcdyhList);
        }
        return null;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return List<String> 需要操作的不动产单元信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前流程需要更新权籍的不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询当前流程需要更新权籍的不动产单元号")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "string", paramType = "path")})
    public List<String> querySyncQjBdcdyh(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcdyZtService.querySyncQjBdcdyh(gzlslid, Collections.EMPTY_LIST);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据证书项目ID查询证书状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "zsxmid", value = "证书项目ID", required = true, dataType = "string", paramType = "path")})
    @Override
    public BdcdyZtDTO queryZsBdcdyZtByCqzsxmid(@PathVariable(name = "zsxmid") String zsxmid){
        return bdcdyZtService.queryZsBdcdyZtByCqzsxmid(zsxmid);

    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据项目ID验证证书是否存在限制权利")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xmid", value = "证书项目ID", required = true, dataType = "string", paramType = "param"),
            @ApiImplicitParam(name = "bdclx", value = "证书项目ID", required = true, dataType = "integer", paramType = "param"),
    })
    @Override
    public Boolean existXzqlByCqxmid(@RequestParam(name = "cqxmid") String cqxmid, @RequestParam(name = "bdclx") Integer bdclx) {
        return bdcdyZtService.existXzqlByCqxmid(cqxmid, bdclx);
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据bdcdyh查询不动产单元现势状态
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询不动产单元现势状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public BdcdyhZtResponseDTO commonQueryBdcdyhZtBybdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return bdcdyZtService.commonQueryBdcdyhZtBybdcdyh(bdcdyh,qjgldm);
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyhList
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO>
     * @description 批量根据BDCDYHLIST查询不动产单元现势状态
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量根据BDCDYHLIST查询不动产单元现势状态")
    public List<BdcdyhZtResponseDTO> commonListBdcdyhZtBybdcdyh(@RequestBody List<String> bdcdyhList,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return bdcdyZtService.commonListBdcdyhZtBybdcdyh(bdcdyhList,qjgldm);
    }

    /**
     * @param bdcdyh
     * @param bdcdyhList
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 批量不动产单元合并，要把这些不动产单元和限制状态求和放到新的不动产单元号里
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据合并前BDCDYH列表更新新的不动产单元现势状态")
    public void commonUpdateBdcdyZtByPlBdcdyh(@PathVariable("bdcdyh") String bdcdyh, @RequestBody List<String> bdcdyhList,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (CollectionUtils.isNotEmpty(bdcdyhList) && StringUtils.isNotBlank(bdcdyh)) {
            bdcdyZtService.commonUpdateBdcdyZtByPlBdcdyh(bdcdyh, bdcdyhList,qjgldm);
        } else {
            throw new AppException("单元号合并状态更新缺失参数");
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyhList 单元号集合
     * @param qjgldm 权籍管理代码
     * @return {List} 现势状态信息
     * @description 批量查询不动产单元现势状态信息（不循环查询避免批量查询慢）
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量查询不动产单元现势状态信息")
    public List<BdcdyhZtResponseDTO> commonListBdcdyhZtPlcx(@RequestBody List<String> bdcdyhList, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return bdcdyZtService.commonListBdcdyhZtPlcx(bdcdyhList,qjgldm);
    }

}
