package cn.gtmap.realestate.natural.core.mapper;

import java.util.List;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyTmclkczyDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyTmclkczyDoExample;
import org.apache.ibatis.annotations.Param;

public interface ZrzyTmclkczyMapper {
    long countByExample(ZrzyTmclkczyDoExample example);

    int deleteByExample(ZrzyTmclkczyDoExample example);

    int deleteByPrimaryKey(String zkxxid);

    int insert(ZrzyTmclkczyDO record);

    int insertSelective(ZrzyTmclkczyDO record);

    List<ZrzyTmclkczyDO> selectByExample(ZrzyTmclkczyDoExample example);

    ZrzyTmclkczyDO selectByPrimaryKey(String zkxxid);

    int updateByExampleSelective(@Param("record") ZrzyTmclkczyDO record, @Param("example") ZrzyTmclkczyDoExample example);

    int updateByExample(@Param("record") ZrzyTmclkczyDO record, @Param("example") ZrzyTmclkczyDoExample example);

    int updateByPrimaryKeySelective(ZrzyTmclkczyDO record);

    int updateByPrimaryKey(ZrzyTmclkczyDO record);
}