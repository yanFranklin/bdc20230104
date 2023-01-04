package cn.gtmap.realestate.natural.core.mapper;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyHyDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyHyDoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZrzyHyMapper {
    long countByExample(ZrzyHyDoExample example);

    int deleteByExample(ZrzyHyDoExample example);

    int deleteByPrimaryKey(String zkxxid);

    int insert(ZrzyHyDO record);

    int insertSelective(ZrzyHyDO record);

    List<ZrzyHyDO> selectByExample(ZrzyHyDoExample example);

    ZrzyHyDO selectByPrimaryKey(String zkxxid);

    int updateByExampleSelective(@Param("record") ZrzyHyDO record, @Param("example") ZrzyHyDoExample example);

    int updateByExample(@Param("record") ZrzyHyDO record, @Param("example") ZrzyHyDoExample example);

    int updateByPrimaryKeySelective(ZrzyHyDO record);

    int updateByPrimaryKey(ZrzyHyDO record);
}