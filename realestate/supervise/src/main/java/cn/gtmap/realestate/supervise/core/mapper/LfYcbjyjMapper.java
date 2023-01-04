package cn.gtmap.realestate.supervise.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.supervise.core.qo.LfBdcXmQO;

import java.util.Date;
import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/09/22
 * @description 廉防1：异常办件预警
 */
public interface LfYcbjyjMapper {
    /**
     * 查询超期办件最近同步统计数据时间
     * @return {Date} 上一次同步数据时间
     */
    Date queryOverdueNewestSyncDataTime();

    /**
     * 查询超快办件最近同步统计数据时间
     * @return {Date} 上一次同步数据时间
     */
    Date queryFastProcessNewestSyncDataTime();

    /**
     * 查询非工作时间办件最近同步统计数据时间
     * @return {Date} 上一次同步数据时间
     */
    Date queryNonWorkDayNewestSyncDataTime();

    /**
     * 更新数据到超期办件表
     */
    void updateBdcXmSjToCqbj();

    /**
     * 更新数据到超快办件表
     */
    void updateBdcXmSjToCkbj();

    /**
     * 更新数据到非工作日办件表
     */
    void updateBdcXmSjToFgzrbj();

    /**
     * 根据登记时间查询不动产项目信息
     */
    List<BdcXmDO> queryBdcXmByDjsj(LfBdcXmQO bdcXmQO);
}
