package cn.gtmap.realestate.config.core.mapper;

import cn.gtmap.realestate.common.core.domain.job.JobUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xuxueli 2019-05-04 16:44:59
 */
@Mapper
public interface XxlJobUserDao {

	public List<JobUser> pageList(@Param("offset") int offset,
                                  @Param("pagesize") int pagesize,
                                  @Param("username") String username,
                                  @Param("role") int role);
	public int pageListCount(@Param("offset") int offset,
							 @Param("pagesize") int pagesize,
							 @Param("username") String username,
							 @Param("role") int role);

	public JobUser loadByUserName(@Param("username") String username);

	public int save(JobUser jobUser);

	public int update(JobUser jobUser);
	
	public int delete(@Param("id") int id);

}
