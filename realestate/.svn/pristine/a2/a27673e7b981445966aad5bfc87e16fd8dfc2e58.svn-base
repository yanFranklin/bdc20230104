package cn.gtmap.realestate.natural.core.mapper;

import java.util.List;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyPwqglxxDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyPwqglxxDoExample;
import org.apache.ibatis.annotations.Param;

public interface ZrzyPwqglxxMapper {
    long countByExample(ZrzyPwqglxxDoExample example);

    int deleteByExample(ZrzyPwqglxxDoExample example);

    int deleteByPrimaryKey(String glxxid);

    int insert(ZrzyPwqglxxDO record);

    int insertSelective(ZrzyPwqglxxDO record);

    List<ZrzyPwqglxxDO> selectByExample(ZrzyPwqglxxDoExample example);

    ZrzyPwqglxxDO selectByPrimaryKey(String glxxid);

    int updateByExampleSelective(@Param("record") ZrzyPwqglxxDO record, @Param("example") ZrzyPwqglxxDoExample example);

    int updateByExample(@Param("record") ZrzyPwqglxxDO record, @Param("example") ZrzyPwqglxxDoExample example);

    int updateByPrimaryKeySelective(ZrzyPwqglxxDO record);

    int updateByPrimaryKey(ZrzyPwqglxxDO record);
}