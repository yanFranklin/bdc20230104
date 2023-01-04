package cn.gtmap.realestate.common.core.service.rest.etl;

import cn.gtmap.realestate.common.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.common.core.dto.etl.EtlBatchNationalAccessRequestDTO;
import cn.gtmap.realestate.common.core.dto.etl.EtlNationalAccessRequestDTO;
import cn.gtmap.realestate.common.core.dto.etl.EtlNationalAccessResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-25
 * @description ETL 汇交相关服务接口
 */
public interface EtlNationalAccessRestService {


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ywh
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.etl.EtlNationalAccessResponseDTO
     * @description 通过YWH和BDCDYH处理外市县上报汇交
     */
    @GetMapping("/realestate-etl/rest/v1.0/access/{ywh}")
    EtlNationalAccessResponseDTO access(@PathVariable(name = "ywh") String ywh,
                                        @RequestParam(required = false) String bdcdyh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ywh
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.exchange.MessageModel
     * @description 通过YWH和BDCDYH 组织汇交结构
     */
    @GetMapping("/realestate-etl/rest/v1.0/messagemodel/{ywh}")
    MessageModel getMessageModel(@PathVariable(name = "ywh") String ywh,
                                 @RequestParam(required = false) String bdcdyh);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param batchDTO
     * @return void
     * @description 批量上报
     */
    @PostMapping("/realestate-etl/rest/v1.0/placcess")
    void plAccess(@RequestBody EtlBatchNationalAccessRequestDTO batchDTO);
}
