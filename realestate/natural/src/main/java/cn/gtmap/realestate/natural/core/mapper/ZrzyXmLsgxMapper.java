package cn.gtmap.realestate.natural.core.mapper;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXmLsgxDoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZrzyXmLsgxMapper {
    long countByExample(ZrzyXmLsgxDoExample example);

    int deleteByExample(ZrzyXmLsgxDoExample example);

    int deleteByPrimaryKey(String gxid);

    int insert(ZrzyXmLsgxDO record);

    int insertSelective(ZrzyXmLsgxDO record);

    List<ZrzyXmLsgxDO> selectByExample(ZrzyXmLsgxDoExample example);

    ZrzyXmLsgxDO selectByPrimaryKey(String gxid);

    int updateByExampleSelective(@Param("record") ZrzyXmLsgxDO record, @Param("example") ZrzyXmLsgxDoExample example);

    int updateByExample(@Param("record") ZrzyXmLsgxDO record, @Param("example") ZrzyXmLsgxDoExample example);

    int updateByPrimaryKeySelective(ZrzyXmLsgxDO record);

    int updateByPrimaryKey(ZrzyXmLsgxDO record);
}