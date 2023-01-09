package cn.gtmap.realestate.config.core.mapper;

import cn.gtmap.realestate.common.core.domain.job.LogReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * job log
 * @author xuxueli 2019-11-22
 */
@Mapper
public interface XxlJobLogReportDao {

	public int save(LogReport logReport);

	public int update(LogReport logReport);

	public List<LogReport> queryLogReport(@Param("triggerDayFrom") Date triggerDayFrom,
                                          @Param("triggerDayTo") Date triggerDayTo);

	public LogReport queryLogReportTotal();

}
