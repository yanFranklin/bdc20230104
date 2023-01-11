package cn.gtmap.realestate.config.core.mapper;

import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by  on 16/9/30.
 */
@Mapper
public interface BdcJobGroupMapper {

    public List<BdcJobGroupDTO> findAll();

    public List<BdcJobGroupDTO> findByAddresstype(@Param("addresstype") int addresstype);

    public int save(BdcJobGroupDO bdcJobGroupDO);

    public int update(BdcJobGroupDO xxlBdcJobGroupDO);

    public int remove(@Param("id") int id);

    public BdcJobGroupDTO load(@Param("id") int id);

    public List<BdcJobGroupDTO> pageList(@Param("offset") int offset,
                                         @Param("pagesize") int pagesize,
                                         @Param("appname") String appname,
                                         @Param("title") String title);

    public int pageListCount(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("appname") String appname,
                             @Param("title") String title);


}
