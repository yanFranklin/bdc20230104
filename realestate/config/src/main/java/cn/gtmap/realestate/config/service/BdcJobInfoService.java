package cn.gtmap.realestate.config.service;

import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDTO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobInfoDO;
import cn.gtmap.realestate.common.core.dto.ReturnT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
 * @version 1.0, 2022/01/01 15:12
 * @description jobInfo服务接口
 */
public interface BdcJobInfoService {

    /**
     * 分页获取任务数据列表
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param pageable pageable
     * @param bdcJobInfoDO
     * @return JobGroup
     */
    Page<BdcJobInfoDO> listBdcJobInfoPage(Pageable pageable, BdcJobInfoDO bdcJobInfoDO);


    /**
     * 添加任务
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobInfoDO
     * @return Object Object
     */
    ReturnT<String> addJobnfo(BdcJobInfoDO bdcJobInfoDO);

    /**
     * 保存任务信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobInfoDO
     * @return bdcJobInfoDO
     */
    ReturnT<String> saveJobnfo(@RequestBody BdcJobInfoDO bdcJobInfoDO);

    /**
     * 更新任务信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobInfoDO jobGroup
     * @return jobGroup jobGroup
     */
    ReturnT<String> updateJobnfo(BdcJobInfoDO bdcJobInfoDO);

    /**
     * 删除 任务
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    ReturnT<String> removeJobnfo(Integer id);

    /**
     * 终止 任务
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    ReturnT<String> pauseJobnfo(Integer id);


    /**
     * 启动 任务
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    ReturnT<String> startJobInfo(Integer id);

    /**
     * 执行一次 任务
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    ReturnT<String> triggerJobInfo(Integer id, String executorParam, String addresslist);

    /**
     * 任务下次执行时间
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param scheduleType
     * @param scheduleConf
     * @return ReturnT
     */
    ReturnT<List<String>> nextTriggerTime(String scheduleType, String scheduleConf);

    /**
     * 根据任务id查询任务信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    ReturnT<BdcJobInfoDO> loadJobInfoById(Integer id);
}
