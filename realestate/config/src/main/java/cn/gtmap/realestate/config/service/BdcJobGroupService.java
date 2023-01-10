package cn.gtmap.realestate.config.service;

import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
 * @version 1.0, 2022/01/01 15:12
 * @description jobGroup服务接口
 */
public interface BdcJobGroupService {

    /**
     * 分页获取执行器数据列表
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param pageable pageable
     * @param bdcJobGroupDO jobGroup
     * @return JobGroup
     */
    Page<BdcJobGroupDO> listBdcJobGroupPage(Pageable pageable, BdcJobGroupDO bdcJobGroupDO);

    /**
     * 保存执行器信息 没有记录则新增
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobGroupDO
     * @return jobGroup
     */
    BdcJobGroupDO saveJobGroup(BdcJobGroupDO bdcJobGroupDO);
}
