package cn.gtmap.realestate.common.core.service.rest.job;

import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface BdcJobGroupRestService {
    /**
     * 分页获取执行器数据列表
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param pageable pageable
     * @param jobParamJson jobParamJson
     * @return JobGroup
     */
    @GetMapping("/realestate-config/rest/v1.0/job/group/page")
    Page<BdcJobGroupDO> listBdcJobGroupPage(Pageable pageable,
                                            @RequestParam(name = "jobParamJson",required = false) String jobParamJson);

    /**
     * 保存执行器信息 没有记录则新增
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobGroupDO
     * @return jobGroup
     */
    @PostMapping(value = "/realestate-config/rest/v1.0/job/group/save")
    BdcJobGroupDO saveJobGroup(@RequestBody BdcJobGroupDO bdcJobGroupDO);

}
