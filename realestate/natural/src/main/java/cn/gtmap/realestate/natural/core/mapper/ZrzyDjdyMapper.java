package cn.gtmap.realestate.natural.core.mapper;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyDjdyDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyDjdyDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZrzyDjdyMapper {
    long countByExample(ZrzyDjdyDOExample example);

    int deleteByExample(ZrzyDjdyDOExample example);

    int deleteByPrimaryKey(String xmid);

    int insert(ZrzyDjdyDO record);

    int insertSelective(ZrzyDjdyDO record);

    List<ZrzyDjdyDO> selectByExample(ZrzyDjdyDOExample example);

    ZrzyDjdyDO selectByPrimaryKey(String xmid);

    int updateByExampleSelective(@Param("record") ZrzyDjdyDO record, @Param("example") ZrzyDjdyDOExample example);

    int updateByExample(@Param("record") ZrzyDjdyDO record, @Param("example") ZrzyDjdyDOExample example);

    int updateByPrimaryKeySelective(ZrzyDjdyDO record);

    int updateByPrimaryKey(ZrzyDjdyDO record);
}