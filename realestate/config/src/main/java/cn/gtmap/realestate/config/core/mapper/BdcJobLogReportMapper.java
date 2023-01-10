package cn.gtmap.realestate.config.core.mapper;

import cn.gtmap.realestate.common.core.domain.job.BdcJobLogReportDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * job log
 * @author xuxueli 2019-11-22
 */
@Mapper
public interface BdcJobLogReportMapper {

	public int save(BdcJobLogReportDO bdcJobLogReportDO);

	public int update(BdcJobLogReportDO bdcJobLogReportDO);

	public List<BdcJobLogReportDO> queryLogReport(@Param("triggerDayFrom") Date triggerDayFrom,
                                                  @Param("triggerDayTo") Date triggerDayTo);

	public BdcJobLogReportDO queryLogReportTotal();

}
