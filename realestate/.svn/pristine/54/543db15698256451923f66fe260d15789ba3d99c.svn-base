package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.FwKDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-28
 * @description 图层表 服务
 */
public interface FwKRestService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param lszd
     * @param zrzh
     * @return cn.gtmap.realestate.common.core.domain.building.FwKDO
     * @description 根据LSZD 和 ZRZH 查询图层表
     */
    @GetMapping("/building/rest/v1.0/fwk")
    FwKDO queryFwKByLszdAndZrzh(@RequestParam("lszd") String lszd, @RequestParam("zrzh")String zrzh,@RequestParam(name = "qjgldm", required = false) String qjgldm);
}
