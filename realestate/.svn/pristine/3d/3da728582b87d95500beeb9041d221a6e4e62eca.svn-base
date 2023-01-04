package cn.gtmap.realestate.init.core.mapper;

import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshFwkgDataDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 不动产初始化开关服务Mapper
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2020/6/23.
 * @description
 */
public interface BdcCshFwkgSlMapper {

    /**
     * 通过不动产实例ID查询不动产初始化开关服务受理信息
     * @param gzlslid 工作流实例ID
     * @return 不动产初始化开关服务DTO
     */
    List<BdcSlCshFwkgDataDTO> queryBdcCshFwkgSlByGzlslid(String gzlslid);

    int batchUpdateCshFwkgSlSfhz(@Param("xmids") List<String> xmids, @Param("sfhz") String sfhz);

}
