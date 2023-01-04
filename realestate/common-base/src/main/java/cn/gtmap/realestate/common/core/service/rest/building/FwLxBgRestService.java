package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.dto.building.FwlxBgRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-11-28
 * @description 房屋类型变更服务
 */
public interface FwLxBgRestService {

    /**
     * @param fwlxBgRequestDTO
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsBgxxDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋类型变更
     */
    @PostMapping("/building/rest/v1.0/fwlxbg")
    void fwLxBg(@RequestBody FwlxBgRequestDTO fwlxBgRequestDTO);
}
