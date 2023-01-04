package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.dto.building.BdcdyxxPlRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyxxRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.InvocationTargetException;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-05
 * @description 同步业务系统下发权籍信息服务
 */
public interface BdcdyxxRestService {
    
    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyxxPlRequestDTO
     * @return void
     * @description 批量更新不动产单元信息
     */
    @PostMapping("/building/rest/v1.0/bdcdyxx/pl")
    void updateBdcdyxxPl(@RequestBody BdcdyxxPlRequestDTO bdcdyxxPlRequestDTO) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyxxRequestDTO
     * @return void
     * @description 更新不动产单元信息
     */
    @PostMapping("/building/rest/v1.0/bdcdyxx")
    void updateBdcdyxx(@RequestBody BdcdyxxRequestDTO bdcdyxxRequestDTO) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
