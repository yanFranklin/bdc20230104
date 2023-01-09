package cn.gtmap.realestate.config.core.mapper;

import cn.gtmap.realestate.common.core.domain.job.JobGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xuxueli on 16/9/30.
 */
@Mapper
public interface XxlJobGroupDao {

    public List<JobGroup> findAll();

    public List<JobGroup> findByAddressType(@Param("addressType") int addressType);

    public int save(JobGroup xxlJobGroup);

    public int update(JobGroup xxlJobGroup);

    public int remove(@Param("id") int id);

    public JobGroup load(@Param("id") int id);

    public List<JobGroup> pageList(@Param("offset") int offset,
                                      @Param("pagesize") int pagesize,
                                      @Param("appname") String appname,
                                      @Param("title") String title);

    public int pageListCount(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("appname") String appname,
                             @Param("title") String title);

}
