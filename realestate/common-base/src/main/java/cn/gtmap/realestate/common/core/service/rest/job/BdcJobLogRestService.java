package cn.gtmap.realestate.common.core.service.rest.job;

import cn.gtmap.realestate.common.core.domain.job.BdcJobInfoDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface BdcJobLogRestService {
    /**
     * 分页获取任务数据列表
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param pageable pageable
     * @param jobInfoParamJson jobInfoParamJson
     * @return JobInfo
     */
    @GetMapping("/realestate-config/rest/v1.0/job/info/page")
    Page<BdcJobInfoDO> listBdcInfoPage(Pageable pageable,
                                       @RequestParam(name = "jobInfoParamJson",required = false) String jobInfoParamJson);

}
