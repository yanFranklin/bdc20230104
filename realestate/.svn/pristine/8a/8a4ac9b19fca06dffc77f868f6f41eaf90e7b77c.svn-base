package cn.gtmap.realestate.natural.core.mapper;

import cn.gtmap.realestate.common.core.domain.natural.ZrzySdDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzySdDoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZrzySdMapper {
    long countByExample(ZrzySdDoExample example);

    int deleteByExample(ZrzySdDoExample example);

    int deleteByPrimaryKey(String zkxxid);

    int insert(ZrzySdDO record);

    int insertSelective(ZrzySdDO record);

    List<ZrzySdDO> selectByExample(ZrzySdDoExample example);

    ZrzySdDO selectByPrimaryKey(String zkxxid);

    int updateByExampleSelective(@Param("record") ZrzySdDO record, @Param("example") ZrzySdDoExample example);

    int updateByExample(@Param("record") ZrzySdDO record, @Param("example") ZrzySdDoExample example);

    int updateByPrimaryKeySelective(ZrzySdDO record);

    int updateByPrimaryKey(ZrzySdDO record);
}