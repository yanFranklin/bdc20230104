package cn.gtmap.realestate.common.core.service.rest.etl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-04-26
 * @description 一窗受理共享业务
 */
public interface ShareYcslDataRestService {

    /**
     * @param processInsId 工作流实例id
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据工作流实例id 共享当前一窗受理项目
     */
    @GetMapping("/realestate-etl/rest/v1.0/sharedb/ycsl/processInsId")
    void shareAllXmByProcessInsId(@RequestParam(value = "processInsId") String processInsId);
}
