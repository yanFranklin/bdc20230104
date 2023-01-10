package cn.gtmap.realestate.config.core.mapper;

import cn.gtmap.realestate.common.core.domain.job.BdcJobInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * job info
 * @author  2016-1-12 18:03:45
 */
@Mapper
public interface BdcJobInfoMapper {

	public List<BdcJobInfoDO> pageList(@Param("offset") int offset,
                                       @Param("pagesize") int pagesize,
                                       @Param("jobGroup") int jobGroup,
                                       @Param("triggerStatus") int triggerStatus,
                                       @Param("jobDesc") String jobDesc,
                                       @Param("executorHandler") String executorHandler,
                                       @Param("author") String author);
	public int pageListCount(@Param("offset") int offset,
							 @Param("pagesize") int pagesize,
							 @Param("jobGroup") int jobGroup,
							 @Param("triggerStatus") int triggerStatus,
							 @Param("jobDesc") String jobDesc,
							 @Param("executorHandler") String executorHandler,
							 @Param("author") String author);
	
	public int save(BdcJobInfoDO info);

	public BdcJobInfoDO loadById(@Param("id") int id);
	
	public int update(BdcJobInfoDO bdcJobInfoDO);
	
	public int delete(@Param("id") long id);

	public List<BdcJobInfoDO> getJobsByGroup(@Param("jobGroup") int jobGroup);

	public int findAllCount();

	public List<BdcJobInfoDO> scheduleJobQuery(@Param("maxNextTime") long maxNextTime, @Param("count") int count );

	public int scheduleUpdate(BdcJobInfoDO bdcJobInfoDO);


}
