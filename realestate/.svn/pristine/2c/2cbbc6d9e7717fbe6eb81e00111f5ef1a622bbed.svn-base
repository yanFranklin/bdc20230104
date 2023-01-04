package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.dto.building.TuxknrResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-24
 * @description 南通 宗地图特殊需求
 */
public interface TuxknrZdtRestService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return java.util.List
     * @description 根据DJH查询 tuxknr 表中的宗地图数据
     */
    @PostMapping("/building/rest/v1.0/tux/listbydjh")
    List<TuxknrResponseDTO> listTuxknrByDjh(@RequestParam(value = "djh", required = false) String djh);
}
