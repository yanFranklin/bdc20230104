package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.common.core.dto.etl.EtlBatchNationalAccessRequestDTO;
import cn.gtmap.realestate.common.core.dto.etl.EtlNationalAccessResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.rest.etl.EtlNationalAccessRestService;
import cn.gtmap.realestate.etl.service.DjtDJSlsqService;
import cn.gtmap.realestate.etl.service.NationalAccessService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-25
 * @description ETL 处理外市县汇交
 */
@RestController
public class EtlNationalAccessRestController implements EtlNationalAccessRestService {


    @Autowired
    private DjtDJSlsqService djtDJSlsqService;

    @Autowired
    private NationalAccessService nationalAccessService;

    /**
     * @param ywh
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.etl.EtlNationalAccessResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 通过YWH和BDCDYH处理外市县上报汇交
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "通过YWH和BDCDYH处理外市县上报汇交")
    public EtlNationalAccessResponseDTO access(@PathVariable(name = "ywh") String ywh,
                                               @RequestParam(required = false) String bdcdyh) {
        EtlNationalAccessResponseDTO responseDTO = new EtlNationalAccessResponseDTO();
        boolean success = nationalAccessService.disposeByYwh(ywh,bdcdyh);
        responseDTO.setSuccess(success);
        if(!success){
            responseDTO.setMsg("上报异常，请查看上报日志");
        }
        return null;
    }

    /**
     * @param ywh
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.exchange.MessageModel
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 通过YWH和BDCDYH 组织汇交结构
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "通过YWH和BDCDYH 组织汇交结构")
    public MessageModel getMessageModel(@PathVariable(name = "ywh") String ywh,
                                        @RequestParam(required = false) String bdcdyh) {
        // 如果只有YWH 需要 查询DJT_DJ_SLSQ表 获取BDCDYH
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("ywh",ywh);
        if(StringUtils.isBlank(bdcdyh)){
            bdcdyh = djtDJSlsqService.getBdcdyhByYwh(ywh);
        }
        // 如果YWH 和BDCDYH 都为空 则不处理
        if(StringUtils.isNotBlank(bdcdyh)){
            paramMap.put("bdcdyh",bdcdyh);
            return nationalAccessService.getMessageModel(paramMap);
        }else{
            throw new AppException("BDCDYH不能为空");
        }
    }

    /**
     * @param batchDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量上报
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量上报")
    public void plAccess(@RequestBody EtlBatchNationalAccessRequestDTO batchDTO) {
        nationalAccessService.plAccess(batchDTO);
    }

}
