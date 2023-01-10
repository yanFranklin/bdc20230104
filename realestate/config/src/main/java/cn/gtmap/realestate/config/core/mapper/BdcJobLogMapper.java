package cn.gtmap.realestate.config.core.mapper;

import cn.gtmap.realestate.common.core.domain.job.BdcJobLogDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * job log
 * @author  2016-1-12 18:03:06
 */
@Mapper
public interface BdcJobLogMapper {

	// exist jobId not use jobGroup, not exist use jobGroup
	public List<BdcJobLogDO> pageList(@Param("offset") int offset,
                                      @Param("pagesize") int pagesize,
                                      @Param("jobGroup") int jobGroup,
                                      @Param("jobId") int jobId,
                                      @Param("triggerTimeStart") Date triggerTimeStart,
                                      @Param("triggerTimeEnd") Date triggerTimeEnd,
                                      @Param("logStatus") int logStatus);
	public int pageListCount(@Param("offset") int offset,
							 @Param("pagesize") int pagesize,
							 @Param("jobGroup") int jobGroup,
							 @Param("jobId") int jobId,
							 @Param("triggerTimeStart") Date triggerTimeStart,
							 @Param("triggerTimeEnd") Date triggerTimeEnd,
							 @Param("logStatus") int logStatus);
	
	public BdcJobLogDO load(@Param("id") long id);

	public long save(BdcJobLogDO bdcJobLogDO);

	public int updateTriggerInfo(BdcJobLogDO bdcJobLogDO);

	public int updateHandleInfo(BdcJobLogDO bdcJobLogDO);
	
	public int delete(@Param("jobId") int jobId);

	public Map<String, Object> findLogReport(@Param("from") Date from,
											 @Param("to") Date to);

	public List<Long> findClearLogIds(@Param("jobGroup") int jobGroup,
									  @Param("jobId") int jobId,
									  @Param("clearBeforeTime") Date clearBeforeTime,
									  @Param("clearBeforeNum") int clearBeforeNum,
									  @Param("count") int count);
	public int clearLog(@Param("logIds") List<Long> logIds);

	public List<Long> findFailJobLogIds(@Param("count") int count);

	public int updateAlarmStatus(@Param("logId") long logId,
								 @Param("oldAlarmStatus") int oldAlarmStatus,
								 @Param("newAlarmStatus") int newAlarmStatus);

	public List<Long> findLostJobIds(@Param("losedTime") Date losedTime);

}
