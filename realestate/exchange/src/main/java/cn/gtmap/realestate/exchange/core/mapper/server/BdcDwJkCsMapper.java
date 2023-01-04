package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkCsDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/7/22 14:16
 */
@Mapper
public interface BdcDwJkCsMapper {

    void batchInsertApiParamInfo(@Param("list") List<BdcDwJkCsDO> bdcDwJkCsDOList);

    List<BdcDwJkCsDO> searchApiParamByApiId(@Param("jkid") String jkid);

    void deleteApiParam(@Param("jkid") String jkid);

    void batchDeleteApiParam(@Param("list") List<String> jkids);

}
