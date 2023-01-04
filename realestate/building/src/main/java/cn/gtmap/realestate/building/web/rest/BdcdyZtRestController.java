package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.BdcdyZtService;
import cn.gtmap.realestate.building.rabbitmq.BdcdyztSyncMqService;
import cn.gtmap.realestate.building.util.ErrorCodeConstants;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO;
import cn.gtmap.realestate.common.core.dto.building.BatchBdcdyhSyncZtRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BatchBdcdyhztRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.service.rest.building.BdcdyZtRestService;
import cn.gtmap.realestate.common.util.CheckEntityUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018-11-13
 * @description 不动产单元号现势状态查询服务
 */
@RestController
@Api(tags = "不动产单元号现势状态查询接口")
public class BdcdyZtRestController extends BaseController implements BdcdyZtRestService {
    @Autowired
    private BdcdyZtService bdcdyZtService;

    @Autowired
    private BdcdyztSyncMqService bdcdyztSyncMqService;

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据bdcdyh查询不动产单元现势状态
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询不动产单元现势状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public BdcdyhZtResponseDTO queryBdcdyhZtBybdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return bdcdyZtService.queryBdcdyhZtBybdcdyh(bdcdyh);
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
    public List<BdcdyhZtResponseDTO> listBdcdyhZtByList(@RequestBody List<String> bdcdyhList,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return bdcdyZtService.listBdcdyhZtBybdcdyh(bdcdyhList);
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
    public List<BdcdyhZtResponseDTO> listBdcdyhZtPlcx(@RequestBody List<String> bdcdyhList, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return bdcdyZtService.listBdcdyhZtPlcx(bdcdyhList);
    }

    /**
     * @param bjrqStr YYYY-MM-DD格式日期
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据日期同步办结日期为参数日期的所有BDCDYH的权利状态
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据日期同步办结日期为参数日期的所有BDCDYH的权利状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "bjrqStr", value = "YYYY-MM-DD格式日期", required = true, dataType = "String", paramType = "query")})
    public void syncBdcdyhztByBjrq(@RequestParam(name = "bjrqStr") String bjrqStr,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        Date date = DateUtils.formatDate(bjrqStr);
        bdcdyZtService.syncByDate(date);
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
    public void updateBdcdyZtByPlBdcdyh(@PathVariable("bdcdyh") String bdcdyh, @RequestBody List<String> bdcdyhList,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (CollectionUtils.isNotEmpty(bdcdyhList) && StringUtils.isNotBlank(bdcdyh)) {
            bdcdyZtService.updateBdcdyZtByPlBdcdyh(bdcdyh, bdcdyhList);
        } else {
            errorException(ErrorCodeConstants.PARAM_ERROR);
        }
    }

    /**
     * @param bdcdyhZtRequestDTO
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据BDCDYH更新不动产单元现势状态
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH更新不动产单元现势状态")
    public void updateBdcdyZtByBdcdyh(@RequestBody BatchBdcdyhztRequestDTO bdcdyhZtRequestDTO) {
        if (bdcdyhZtRequestDTO != null && CollectionUtils.isNotEmpty(bdcdyhZtRequestDTO.getBdcdyhZtList())) {
            for (BdcdyhZtRequestDTO requestDTO : bdcdyhZtRequestDTO.getBdcdyhZtList()) {
                if (!CheckEntityUtils.checkPkAndFk(requestDTO)) {
                    errorException(ErrorCodeConstants.PARAM_ERROR);
                }
                bdcdyZtService.updateBdcdyZtByBdcdyh(requestDTO);
            }
        } else {
            errorException(ErrorCodeConstants.PARAM_ERROR);
        }
    }

    /**
     * @param batchBdcdyhSyncZtRequestDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH同步不动产单元状态
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH同步不动产单元状态")
    public void syncBdcdyZtByBdcdyh(@RequestBody BatchBdcdyhSyncZtRequestDTO batchBdcdyhSyncZtRequestDTO) {
        if(batchBdcdyhSyncZtRequestDTO != null){
            try {
                bdcdyztSyncMqService.receiveSyncBdcdyZt(JSONObject.toJSONString(batchBdcdyhSyncZtRequestDTO),null,null);
            } catch (IOException e) {
                errorException(ErrorCodeConstants.PARAM_ERROR);
            }
        }
    }
}
