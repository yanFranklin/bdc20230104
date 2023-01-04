package cn.gtmap.realestate.natural.core.mapper;

import cn.gtmap.realestate.common.core.domain.natural.ZrzySzyDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzySzyDoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZrzySzyMapper {
    long countByExample(ZrzySzyDoExample example);

    int deleteByExample(ZrzySzyDoExample example);

    int deleteByPrimaryKey(String zkxxid);

    int insert(ZrzySzyDO record);

    int insertSelective(ZrzySzyDO record);

    List<ZrzySzyDO> selectByExample(ZrzySzyDoExample example);

    ZrzySzyDO selectByPrimaryKey(String zkxxid);

    int updateByExampleSelective(@Param("record") ZrzySzyDO record, @Param("example") ZrzySzyDoExample example);

    int updateByExample(@Param("record") ZrzySzyDO record, @Param("example") ZrzySzyDoExample example);

    int updateByPrimaryKeySelective(ZrzySzyDO record);

    int updateByPrimaryKey(ZrzySzyDO record);
}