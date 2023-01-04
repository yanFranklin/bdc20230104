package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.FwJtcyDO;
import cn.gtmap.realestate.common.core.domain.building.FwKDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/11/30/16:45
 * @Description:房屋家庭成员相关服务
 */
public interface FwJtcyRestService {

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @param qlrIndex 权利人主键
     * @return
     * @description 根据qlrIndex 查询房屋家庭成员
     */
    @GetMapping("/building/rest/v1.0/fwJtcy")
    List<FwJtcyDO> listFwJtcy(@RequestParam("qlrIndex") String qlrIndex);
}
