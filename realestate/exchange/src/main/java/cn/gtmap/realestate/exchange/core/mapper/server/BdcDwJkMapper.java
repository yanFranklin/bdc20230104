package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkDO;
import cn.gtmap.realestate.common.core.dto.exchange.openapi.BdcOpenApiDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/7/22 11:24
 */
@Mapper
public interface BdcDwJkMapper {

    void batchInsertApiInfo(@Param("list") List<BdcDwJkDO> dwJkDOList);

    void batchInsertApiInfoMerge(@Param("list") List<BdcDwJkDO> dwJkDOList);

    void insertApiInfo(BdcDwJkDO bdcDwJkDO);

    BdcDwJkDO searchApiInfoById(@Param("jkid") String jkid);

    List<BdcDwJkDO> listSearchApiInfo(@Param("jkids") List<String> jkids);

    List<String> listSearchApiIds(@Param("jkids") Set<String> jkids);

    List<BdcDwJkDO> searchApiInfo(BdcDwJkDO bdcDwJkDO);

    List<BdcDwJkDO> querySimpleApiUrl();

    void updateApiInfo(BdcDwJkDO bdcDwJkDO);

    void updateReleaseStatus(BdcOpenApiDTO bdcOpenApiDTO);

    void deleteApi(@Param("jkid") String jkid);

    void batchDeleteApi(@Param("list") List<String> jkids);

    void deleteApiWithoutUpdate();

    List<BdcDwJkDO> queryApiByName(@Param("jkmc") String jkmc);

    List<BdcDwJkDO> queryApiByUrl(@Param("jkdz") String jkdz);

}
