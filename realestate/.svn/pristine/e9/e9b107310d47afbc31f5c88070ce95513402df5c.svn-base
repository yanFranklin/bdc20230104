package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.vo.etl.BatchSharedbVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-04-26
 * @description 共享服务
 */
public interface ShareDataRestService {

    /**
     * @param xmid 项目主键
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据项目主键共享当前受理节点项目
     */
    @GetMapping("/realestate-exchange/rest/v1.0/sharedb/running")
    void shareRunningXmByXmid(String xmid);

    /**
     * @param xmid 项目主键
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据项目主键共享当前项目
     */
    @GetMapping("/realestate-exchange/rest/v1.0/sharedb/xmid")
    void shareXmByXmid(String xmid);

    /**
     * @param xmid 项目主键
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据项目主键共享当前项目状态，可用于项目删除，退回
     */
    @GetMapping("/realestate-exchange/rest/v1.0/sharedb/xmstatus")
    void shareXmStatusByXmid(@RequestParam("xmid") String xmid, @RequestParam("status") String status);

    /**
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据项目主键共享同一流程所有受理节点项目
     */
    @GetMapping("/realestate-exchange/rest/v1.0/sharedb/all/running")
    void shareRunningAllXmByProcessInsId(String processInsId);

    /**
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据项目主键共享当前项目
     */
    @GetMapping("/realestate-exchange/rest/v1.0/sharedb/all/processInsId")
    void shareAllXmByProcessInsId(String processInsId);

    /**
     * @param processInsId processInsId
     * @param status       状态
     * @param reason       原因
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据项目主键共享当前项目状态，可用于项目删除，退回
     */
    @GetMapping("/realestate-exchange/rest/v1.0/sharedb/all/xmstatus")
    void shareAllXmStatusByProcessInsId(@RequestParam("processInsId") String processInsId, @RequestParam("status") String status,
                                        @RequestParam(value = "reason", required = false) String reason);

    /**
     * @param xmid 项目主键
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 根据项目主键补偿共享当前项目
     */
    @GetMapping("/realestate-exchange/rest/v1.0/sharedb/xmid/for/compenstae")
    CommonResponse shareXmByXmidForCompensate(@RequestParam("xmid")String xmid,@RequestParam("rzid")String rzid);

    /**
     * @param batchSharedbVO
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 批量根据项目主键补偿共享当前项目
     */
    @PostMapping("/realestate-exchange/rest/v1.0/batch/sharedb/xmid/for/compenstae")
    CommonResponse batchShareXmByXmidForCompensate(@RequestBody BatchSharedbVO batchSharedbVO);
}
