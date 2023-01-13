package cn.gtmap.realestate.common.core.service.rest.job;

import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDTO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobInfoDO;
import cn.gtmap.realestate.common.core.dto.ReturnT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface BdcJobInfoRestService {
    /**
     * 分页获取任务数据列表
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param pageable pageable
     * @param jobInfoParamJson jobInfoParamJson
     * @return JobInfo
     */
    @GetMapping("/realestate-config/rest/v1.0/job/info/page")
    Page<BdcJobInfoDO> listBdcJobInfoPage(Pageable pageable,
                                       @RequestParam(name = "jobInfoParamJson",required = false) String jobInfoParamJson);

    /**
     * 添加任务
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobInfoDO
     * @return Object Object
     */
    @RequestMapping("/realestate-config/rest/v1.0/job/info/add")
    ReturnT<String> addJobnfo(@RequestBody BdcJobInfoDO bdcJobInfoDO);

    /**
     * 保存任务信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobInfoDO
     * @return bdcJobInfoDO
     */
    @PostMapping("/realestate-config/rest/v1.0/job/info/save")
    ReturnT<String> saveJobnfo(@RequestBody BdcJobInfoDO bdcJobInfoDO);

    /**
     * 更新任务信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobInfoDO jobGroup
     * @return jobGroup jobGroup
     */
    @PostMapping("/realestate-config/rest/v1.0/job/info/update")
    ReturnT<String> updateJobnfo(@RequestBody BdcJobInfoDO bdcJobInfoDO);

    /**
     * 删除 任务
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    @RequestMapping("/realestate-config/rest/v1.0/job/info/remove")
    ReturnT<String> removeJobnfo(@RequestParam(value = "id",required = false) Integer id);

    /**
     * 终止 任务
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    @RequestMapping("/realestate-config/rest/v1.0/job/info/stop")
    ReturnT<String> pauseJobnfo(@RequestParam(value = "id",required = false) Integer id);


    /**
     * 启动 任务
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    @RequestMapping("/realestate-config/rest/v1.0/job/info/start")
    ReturnT<String> startJobInfo(@RequestParam(value = "id",required = false) Integer id);

    /**
     * 执行一次 任务
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    @RequestMapping("/realestate-config/rest/v1.0/job/info/trigger")
    ReturnT<String> triggerJobInfo(@RequestParam(value = "id",required = false) Integer id, @RequestParam(value = "executorParam",required = false) String executorParam, @RequestParam(value = "addresslist",required = false) String addresslist);

    /**
     * 任务下次执行时间
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param scheduleType
     * @param scheduleConf
     * @return ReturnT
     */
    @RequestMapping("/realestate-config/rest/v1.0/job/info/nextTriggerTime")
    ReturnT<List<String>> nextTriggerTime(@RequestParam(value = "scheduleType",required = false) String scheduleType, @RequestParam(value = "scheduleConf",required = false) String scheduleConf);

    /**
     * 根据任务id查询任务信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    @RequestMapping("/realestate-config/rest/v1.0/job/info/loadById")
    ReturnT<BdcJobInfoDO> loadJobInfoById(@RequestParam(value = "id", required = false) Integer id);
}
