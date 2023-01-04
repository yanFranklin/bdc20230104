package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkXffdzgxDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtamp.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/30 11:24
 */
@Mapper
public interface BdcDwJkXffdzgxMapper {

    BdcDwJkXffdzgxDO searchDzGxByQxbs(@Param("qxbs") String qxbs);

    List<BdcDwJkXffdzgxDO> listBdcDwJkXffdzgxDO(@Param("qxbs") String qxbs);

    void insertDzGx(BdcDwJkXffdzgxDO bdcDwJkXffdzgxDO);

    void updateDzGx(BdcDwJkXffdzgxDO bdcDwJkXffdzgxDO);

    void deleteDzGx(@Param("list") List<String> ids);
}
