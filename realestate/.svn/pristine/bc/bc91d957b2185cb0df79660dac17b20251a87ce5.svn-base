package cn.gtmap.realestate.natural.core.mapper;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyGdxxDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyGdxxDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZrzyGdxxMapper {
    long countByExample(ZrzyGdxxDOExample example);

    int deleteByExample(ZrzyGdxxDOExample example);

    int deleteByPrimaryKey(String gdxxid);

    int insert(ZrzyGdxxDO record);

    int insertSelective(ZrzyGdxxDO record);

    List<ZrzyGdxxDO> selectByExample(ZrzyGdxxDOExample example);

    ZrzyGdxxDO selectByPrimaryKey(String gdxxid);

    int updateByExampleSelective(@Param("record") ZrzyGdxxDO record, @Param("example") ZrzyGdxxDOExample example);

    int updateByExample(@Param("record") ZrzyGdxxDO record, @Param("example") ZrzyGdxxDOExample example);

    int updateByPrimaryKeySelective(ZrzyGdxxDO record);

    int updateByPrimaryKey(ZrzyGdxxDO record);
}