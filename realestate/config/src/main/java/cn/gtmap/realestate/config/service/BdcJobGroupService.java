package cn.gtmap.realestate.config.service;

import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDTO;
import cn.gtmap.realestate.common.core.dto.ReturnT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


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
     * 查询所有执行器列表
     *
     * @return Object Object
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */

    List<BdcJobGroupDTO> listBdcJobGroupAll();

    /**
     * 保存执行器信息 没有记录则新增
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobGroupDO
     * @return jobGroup
     */
    ReturnT<String> saveJobGroup(BdcJobGroupDO bdcJobGroupDO);

    /**
     * 更新执行器信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobGroupDO
     * @return ReturnT
     */
    ReturnT<String> updateJobGroup(BdcJobGroupDO bdcJobGroupDO);

    /**
     * 删除执行器信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    ReturnT<String> removeJobGroup(Integer id);

    /**
     * 根据执行器id查询执行器信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    ReturnT<BdcJobGroupDTO> loadJobGroupById(Integer id);
}
