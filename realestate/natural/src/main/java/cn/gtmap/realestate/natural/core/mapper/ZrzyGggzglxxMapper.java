package cn.gtmap.realestate.natural.core.mapper;


import cn.gtmap.realestate.common.core.domain.natural.ZrzyGggzglxxDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyGggzglxxDoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZrzyGggzglxxMapper {
    long countByExample(ZrzyGggzglxxDoExample example);

    int deleteByExample(ZrzyGggzglxxDoExample example);

    int deleteByPrimaryKey(String glxxid);

    int insert(ZrzyGggzglxxDO record);

    int insertSelective(ZrzyGggzglxxDO record);

    List<ZrzyGggzglxxDO> selectByExample(ZrzyGggzglxxDoExample example);

    ZrzyGggzglxxDO selectByPrimaryKey(String glxxid);

    int updateByExampleSelective(@Param("record") ZrzyGggzglxxDO record, @Param("example") ZrzyGggzglxxDoExample example);

    int updateByExample(@Param("record") ZrzyGggzglxxDO record, @Param("example") ZrzyGggzglxxDoExample example);

    int updateByPrimaryKeySelective(ZrzyGggzglxxDO record);

    int updateByPrimaryKey(ZrzyGggzglxxDO record);
}