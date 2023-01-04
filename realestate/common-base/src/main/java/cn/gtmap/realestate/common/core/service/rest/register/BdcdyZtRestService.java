package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.dto.building.BatchBdcdyhSyncZtRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BatchBdcdyhztRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcdyZtDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/4/17
 * @description 不动产单元状态rest服务
 */
public interface BdcdyZtRestService {
    /**
     * @param bdcdyh 不动产单元号
     * @return BdcdyZtDTO 不动产单元状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元状态
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/bdcdyzt/{bdcdyh}", method = RequestMethod.GET)
    BdcdyZtDTO queryBdcdyZt(@PathVariable(name = "bdcdyh") String bdcdyh);

    /**
     * @param bdcdyList 不动产单元号List
     * @return List<BdcdyZtDTO> 不动产单元状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量查询不动产单元状态
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/bdcdyzt/list", method = RequestMethod.POST)
    List<BdcdyZtDTO> queryBdcdyZtList(@RequestBody List<String> bdcdyList);

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param bdcdyList 不动产单元号List
     * @return void
     * @description 同步BDCDYH状态
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/bdcdyzt/sync", method = RequestMethod.PUT)
    void syncBdcdyZtByBdcdyh(@RequestBody List<String> bdcdyList,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyhList
     * @return cn.gtmap.realestate.common.core.dto.building.BatchBdcdyhSyncZtRequestDTO
     * @description 根据BDCDYH查询状态数量
     */
    @PutMapping("/realestate-register/rest/v1.0/bdcdyzt/listwithnum")
    BatchBdcdyhSyncZtRequestDTO queryBdcdyZtByBdcdyh(@RequestBody List<String> bdcdyhList);

    /**
     * @param gzlslid 工作流实例ID
     * @return List<String> 需要操作的不动产单元信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前流程需要更新权籍的不动产单元号
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/bdcdyzt/{gzlslid}/bdcdyh/syncQj", method = RequestMethod.GET)
    List<String> querySyncQjBdcdyh(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param zsxmid 产权证书项目ID
     * @return BdcdyZtDTO 不动产单元状态
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据产权证书项目ID查询证书状态
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/zsbdcdyzt/zsxmid/{zsxmid}", method = RequestMethod.GET)
    BdcdyZtDTO queryZsBdcdyZtByCqzsxmid(@PathVariable(name = "zsxmid") String zsxmid);

    /**
     * @param cqxmid 产权项目ID
     * @return
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据项目ID验证证书是否存在限制权利
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/bdcdyzt/xmid", method = RequestMethod.GET)
    Boolean existXzqlByCqxmid(@RequestParam(name = "cqxmid") String cqxmid, @RequestParam(name = "bdclx") Integer bdclx);

    /**
     * @param bdcdyh 不动产单元号
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据BDCDYH查询不动产单元现势状态
     */
    @GetMapping("/realestate-register/rest/v1.0/bdcdyzt/xszt/{bdcdyh}")
    BdcdyhZtResponseDTO commonQueryBdcdyhZtBybdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyhList
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO>
     * @description 批量根据BDCDYHLIST查询不动产单元现势状态
     */
    @PostMapping("/realestate-register/rest/v1.0/bdcdyzt/xszt/pl/bdcdyhlist")
    List<BdcdyhZtResponseDTO> commonListBdcdyhZtBybdcdyh(@RequestBody List<String> bdcdyhList,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bdcdyh
     * @param bdcdyhList
     * @param qjgldm  指定权籍
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 批量不动产单元合并，要把这些不动产单元和限制状态求和放到新的不动产单元号里
     */
    @PostMapping("/realestate-register/rest/v1.0/bdcdyhzt/update/{bdcdyh}")
    void commonUpdateBdcdyZtByPlBdcdyh(@PathVariable("bdcdyh") String bdcdyh, @RequestBody List<String> bdcdyhList,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyhList 单元号集合
     * @param qjgldm 权籍管理代码
     * @return {List} 现势状态信息
     * @description 批量查询不动产单元现势状态信息（不循环查询避免批量查询慢）
     */
    @PostMapping("/realestate-register/rest/v1.0/bdcdyhzt/plcx")
    List<BdcdyhZtResponseDTO> commonListBdcdyhZtPlcx(@RequestBody List<String> bdcdyhList,@RequestParam(name = "qjgldm", required = false) String qjgldm);

}
