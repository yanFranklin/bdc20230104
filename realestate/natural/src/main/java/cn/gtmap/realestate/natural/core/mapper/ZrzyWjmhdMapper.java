package cn.gtmap.realestate.natural.core.mapper;

import java.util.List;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyWjmhdDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyWjmhdDoExample;
import org.apache.ibatis.annotations.Param;

public interface ZrzyWjmhdMapper {
    long countByExample(ZrzyWjmhdDoExample example);

    int deleteByExample(ZrzyWjmhdDoExample example);

    int deleteByPrimaryKey(String zkxxid);

    int insert(ZrzyWjmhdDO record);

    int insertSelective(ZrzyWjmhdDO record);

    List<ZrzyWjmhdDO> selectByExample(ZrzyWjmhdDoExample example);

    ZrzyWjmhdDO selectByPrimaryKey(String zkxxid);

    int updateByExampleSelective(@Param("record") ZrzyWjmhdDO record, @Param("example") ZrzyWjmhdDoExample example);

    int updateByExample(@Param("record") ZrzyWjmhdDO record, @Param("example") ZrzyWjmhdDoExample example);

    int updateByPrimaryKeySelective(ZrzyWjmhdDO record);

    int updateByPrimaryKey(ZrzyWjmhdDO record);
}