package cn.gtmap.realestate.config.core.mapper;

import cn.gtmap.realestate.common.core.domain.job.BdcJobRegistryDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by xuxueli on 16/9/30.
 */
@Mapper
public interface BdcJobRegistryMapper {

    public List<Integer> findDead(@Param("timeout") int timeout,
                                  @Param("nowTime") Date nowTime);

    public int removeDead(@Param("ids") List<Integer> ids);

    public List<BdcJobRegistryDO> findAll(@Param("timeout") int timeout,
                                          @Param("nowTime") Date nowTime);

    public int registryUpdate(@Param("registrygroup") String registrygroup,
                              @Param("registrykey") String registrykey,
                              @Param("registryvalue") String registryvalue,
                              @Param("updatetime") Date updatetime);

    public int registrySave(@Param("registrygroup") String registrygroup,
                            @Param("registrykey") String registrykey,
                            @Param("registryvalue") String registryvalue,
                            @Param("updatetime") Date updatetime);

    public int registryDelete(@Param("registrygroup") String registrygroup,
                          @Param("registrykey") String registrykey,
                          @Param("registryvalue") String registryvalue);

}
