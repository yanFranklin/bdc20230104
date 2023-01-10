package cn.gtmap.realestate.config.core.mapper;

import cn.gtmap.realestate.common.core.domain.job.BdcJobLogGlueDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * job log for glue
 * @author  2016-5-19 18:04:56
 */
@Mapper
public interface BdcJobLogGlueMapper {
	
	public int save(BdcJobLogGlueDO bdcJobLogGlueDO);
	
	public List<BdcJobLogGlueDO> findByJobId(@Param("jobId") int jobId);

	public int removeOld(@Param("jobId") int jobId, @Param("limit") int limit);

	public int deleteByJobId(@Param("jobId") int jobId);
	
}
