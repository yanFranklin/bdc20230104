package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.dto.building.LpbGJRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/16
 * @description
 */
public interface LpbGjRestService {

    /**
     * @param lpbGJRequestDTO
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 楼盘表构建
     */
    @PostMapping("/building/rest/v1.0/lpbgj")
    void lpbGj(@RequestBody LpbGJRequestDTO lpbGJRequestDTO);
}
