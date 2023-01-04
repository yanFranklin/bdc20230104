package cn.gtmap.realestate.natural.core.mapper;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyGgDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyGgDoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZrzyGgMapper {
    long countByExample(ZrzyGgDoExample example);

    int deleteByExample(ZrzyGgDoExample example);

    int deleteByPrimaryKey(String ggid);

    int insert(ZrzyGgDO record);

    int insertSelective(ZrzyGgDO record);

    List<ZrzyGgDO> selectByExample(ZrzyGgDoExample example);

    ZrzyGgDO selectByPrimaryKey(String ggid);

    int updateByExampleSelective(@Param("record") ZrzyGgDO record, @Param("example") ZrzyGgDoExample example);

    int updateByExample(@Param("record") ZrzyGgDO record, @Param("example") ZrzyGgDoExample example);

    int updateByPrimaryKeySelective(ZrzyGgDO record);

    int updateByPrimaryKey(ZrzyGgDO record);
}