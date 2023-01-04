package cn.gtmap.realestate.natural.core.mapper;

import cn.gtmap.realestate.common.core.domain.natural.ZrzySlDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzySlDoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZrzySlMapper {
    long countByExample(ZrzySlDoExample example);

    int deleteByExample(ZrzySlDoExample example);

    int deleteByPrimaryKey(String zkxxid);

    int insert(ZrzySlDO record);

    int insertSelective(ZrzySlDO record);

    List<ZrzySlDO> selectByExample(ZrzySlDoExample example);

    ZrzySlDO selectByPrimaryKey(String zkxxid);

    int updateByExampleSelective(@Param("record") ZrzySlDO record, @Param("example") ZrzySlDoExample example);

    int updateByExample(@Param("record") ZrzySlDO record, @Param("example") ZrzySlDoExample example);

    int updateByPrimaryKeySelective(ZrzySlDO record);

    int updateByPrimaryKey(ZrzySlDO record);
}