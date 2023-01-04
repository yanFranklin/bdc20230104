package cn.gtmap.realestate.common.core.service.rest.etl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-06-26
 * @description 共享登记数据给大数据局使用
 */
public interface ShareDsjjDataRestService {

    /**
     * @param processInsId 工作流实例id
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据工作流实例id 共享登记数据
     */
    @GetMapping("/realestate-etl/rest/v1.0/sharedb/dsjj/processInsId")
    void shareDsjjDataByProcessInsId(@RequestParam(value = "processInsId") String processInsId);
}
