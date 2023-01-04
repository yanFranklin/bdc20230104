package cn.gtmap.realestate.natural.core.mapper;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyShxxDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyShxxDoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZrzyShxxMapper {
    long countByExample(ZrzyShxxDoExample example);

    int deleteByExample(ZrzyShxxDoExample example);

    int deleteByPrimaryKey(String shxxid);

    int insert(ZrzyShxxDO record);

    int insertSelective(ZrzyShxxDO record);

    List<ZrzyShxxDO> selectByExample(ZrzyShxxDoExample example);

    ZrzyShxxDO selectByPrimaryKey(String shxxid);

    int updateByExampleSelective(@Param("record") ZrzyShxxDO record, @Param("example") ZrzyShxxDoExample example);

    int updateByExample(@Param("record") ZrzyShxxDO record, @Param("example") ZrzyShxxDoExample example);

    int updateByPrimaryKeySelective(ZrzyShxxDO record);

    int updateByPrimaryKey(ZrzyShxxDO record);
}