package cn.gtmap.realestate.natural.core.mapper;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyQsqglxxDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyQsqglxxDoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZrzyQsqglxxMapper {
    long countByExample(ZrzyQsqglxxDoExample example);

    int deleteByExample(ZrzyQsqglxxDoExample example);

    int deleteByPrimaryKey(String glxxid);

    int insert(ZrzyQsqglxxDO record);

    int insertSelective(ZrzyQsqglxxDO record);

    List<ZrzyQsqglxxDO> selectByExample(ZrzyQsqglxxDoExample example);

    ZrzyQsqglxxDO selectByPrimaryKey(String glxxid);

    int updateByExampleSelective(@Param("record") ZrzyQsqglxxDO record, @Param("example") ZrzyQsqglxxDoExample example);

    int updateByExample(@Param("record") ZrzyQsqglxxDO record, @Param("example") ZrzyQsqglxxDoExample example);

    int updateByPrimaryKeySelective(ZrzyQsqglxxDO record);

    int updateByPrimaryKey(ZrzyQsqglxxDO record);
}