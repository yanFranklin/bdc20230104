package cn.gtmap.realestate.natural.core.mapper;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyHdDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyHdDoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZrzyHdMapper {
    long countByExample(ZrzyHdDoExample example);

    int deleteByExample(ZrzyHdDoExample example);

    int deleteByPrimaryKey(String zkxxid);

    int insert(ZrzyHdDO record);

    int insertSelective(ZrzyHdDO record);

    List<ZrzyHdDO> selectByExample(ZrzyHdDoExample example);

    ZrzyHdDO selectByPrimaryKey(String zkxxid);

    int updateByExampleSelective(@Param("record") ZrzyHdDO record, @Param("example") ZrzyHdDoExample example);

    int updateByExample(@Param("record") ZrzyHdDO record, @Param("example") ZrzyHdDoExample example);

    int updateByPrimaryKeySelective(ZrzyHdDO record);

    int updateByPrimaryKey(ZrzyHdDO record);
}