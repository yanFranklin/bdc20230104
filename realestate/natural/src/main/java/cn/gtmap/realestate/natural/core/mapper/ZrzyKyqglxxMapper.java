package cn.gtmap.realestate.natural.core.mapper;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyKyqglxxDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyKyqglxxDoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZrzyKyqglxxMapper {
    long countByExample(ZrzyKyqglxxDoExample example);

    int deleteByExample(ZrzyKyqglxxDoExample example);

    int deleteByPrimaryKey(String glxxid);

    int insert(ZrzyKyqglxxDO record);

    int insertSelective(ZrzyKyqglxxDO record);

    List<ZrzyKyqglxxDO> selectByExample(ZrzyKyqglxxDoExample example);

    ZrzyKyqglxxDO selectByPrimaryKey(String glxxid);

    int updateByExampleSelective(@Param("record") ZrzyKyqglxxDO record, @Param("example") ZrzyKyqglxxDoExample example);

    int updateByExample(@Param("record") ZrzyKyqglxxDO record, @Param("example") ZrzyKyqglxxDoExample example);

    int updateByPrimaryKeySelective(ZrzyKyqglxxDO record);

    int updateByPrimaryKey(ZrzyKyqglxxDO record);
}