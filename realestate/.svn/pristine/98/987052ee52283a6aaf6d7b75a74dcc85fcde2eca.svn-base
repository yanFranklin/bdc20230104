package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.dto.building.DjhZtResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019/3/6
 * @description 地籍号状态服务
 */
public interface DjhZtRestService {


    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjhZtResponseDTO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询djh冻结信息
     */
    @GetMapping("/building/rest/v1.0/djhzt/bdcdyh")
    DjhZtResponseDTO getDjhZtByBdcdyh(@RequestParam(name = "bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);
    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.dto.building.DjhZtResponseDTO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据djh查询冻结信息
     */
    @GetMapping("/building/rest/v1.0/djhzt/{djh}")
    DjhZtResponseDTO getDjhZtByDjh(@PathVariable("djh") String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param djhList
     * @return List<DjhZtResponseDTO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 批量根据djh查询冻结信息
     */
    @PostMapping("/building/rest/v1.0/djhzt/pl/djh")
    List<DjhZtResponseDTO> listDjhZtByDjh(@RequestBody List<String> djhList,@RequestParam(name = "qjgldm", required = false) String qjgldm);
}
