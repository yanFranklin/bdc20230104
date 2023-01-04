package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.dto.building.YbdcdyhResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 1.0  2022-07-07
 * @description 权籍原不动产单元号服务
 */
public interface YbdcdyhRestService {

    /**
     * @description 查询房屋原不动产单元号信息列表
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/7/7 10:43
     * @param fwBdcdyhList
     * @return YbdcdyhResponseDTO
     */
    @PostMapping("/building/rest/v1.0/fwybdcdyh/")
    List<YbdcdyhResponseDTO> queryFwYbdcdyhList(@RequestBody List<String> fwBdcdyhList);

    /**
     * @description 查询土地原不动产单元号信息列表
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/7/7 10:43
     * @param tdBdcdyhList
     * @return YbdcdyhResponseDTO
     */
    @PostMapping("/building/rest/v1.0/tdybdcdyh/")
    List<YbdcdyhResponseDTO> queryTdYbdcdyhList(@RequestBody List<String> tdBdcdyhList);

}
