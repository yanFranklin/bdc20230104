package cn.gtmap.realestate.building.core.mapper;

import cn.gtmap.realestate.common.core.dto.building.YbdcdyhResponseDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 1.0  2022-07-07
 * @description
 */
public interface YbdcdyhMapper {
    /**
     * @description 查询房屋原不动产单元号信息列表
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/7/7 11:08
     * @param fwBdcdyhList
     * @return List<YbdcdyhResponseDTO>
     */
    List<YbdcdyhResponseDTO> queryFwYbdcdyhList(@Param("fwBdcdyhList") List<String> fwBdcdyhList);

    /**
     * @description 查询土地原不动产单元号信息列表
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/7/7 11:14
     * @param tdBdcdyhList
     * @return List<YbdcdyhResponseDTO>
     */
    List<YbdcdyhResponseDTO> queryTdYbdcdyhList(@Param("tdBdcdyhList") List<String> tdBdcdyhList);
}
