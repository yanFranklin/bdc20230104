package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.dto.building.BatchBdcdyhSyncZtRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BatchBdcdyhztRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018/11/13
 * @description 不动产单元号现势状态查询服务
 */
public interface BdcdyZtRestService {

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据BDCDYH查询不动产单元现势状态
     */
    @GetMapping("/building/rest/v1.0/bdcdyhzt/{bdcdyh}")
    BdcdyhZtResponseDTO queryBdcdyhZtBybdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param batchBdcdyhztRequestDTO
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据BDCDYH修改不动产单元现势状态
     */
    @PutMapping("/building/rest/v1.0/bdcdyhzt")
    void updateBdcdyZtByBdcdyh(BatchBdcdyhztRequestDTO batchBdcdyhztRequestDTO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param batchBdcdyhSyncZtRequestDTO
     * @return void
     * @description 同步BDCDYH状态
     */
    @PutMapping("/building/rest/v1.0/bdcdyhzt/sync")
    void syncBdcdyZtByBdcdyh(BatchBdcdyhSyncZtRequestDTO batchBdcdyhSyncZtRequestDTO);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyhList
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO>
     * @description 批量根据BDCDYHLIST查询不动产单元现势状态
     */
    @PostMapping("/building/rest/v1.0/bdcdyhzt/pl/bdcdyhlist")
    List<BdcdyhZtResponseDTO> listBdcdyhZtByList(@RequestBody List<String> bdcdyhList,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyhList 单元号集合
     * @param qjgldm 权籍管理代码
     * @return {List} 现势状态信息
     * @description 批量查询不动产单元现势状态信息（不循环查询避免批量查询慢）
     */
    @PostMapping("/building/rest/v1.0/bdcdyhzt/plcx")
    List<BdcdyhZtResponseDTO> listBdcdyhZtPlcx(@RequestBody List<String> bdcdyhList,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bjrqStr YYYY-MM-DD格式日期
     * @return void
     * @description 根据日期同步办结日期为参数日期的所有BDCDYH的权利状态
     */
    @GetMapping("/building/rest/v1.0/bdcdyhzt/syncbybjrq")
    void syncBdcdyhztByBjrq(@RequestParam(name = "bjrqStr") String bjrqStr,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bdcdyh
     * @param bdcdyhList
     * @param qjgldm  指定权籍
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 批量不动产单元合并，要把这些不动产单元和限制状态求和放到新的不动产单元号里
     */
    @PostMapping("/building/rest/v1.0/bdcdyhzt/update/{bdcdyh}")
    void updateBdcdyZtByPlBdcdyh(@PathVariable("bdcdyh") String bdcdyh, @RequestBody List<String> bdcdyhList,@RequestParam(name = "qjgldm", required = false) String qjgldm);

}