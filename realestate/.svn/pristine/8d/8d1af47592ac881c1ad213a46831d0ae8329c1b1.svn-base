package cn.gtmap.realestate.common.core.service.rest.building;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-03-30
 * @description 合肥 行政区 查询服务
 */
public interface HfXzqRestService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @param hslx sc 为实测户室  yc 为 预测户室  为空则先读取实测 后查询预测
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.HfJyztResponseDTO>
     * @description 根据BDCDYH 查询 行政区
     */
    @PostMapping("/building/rest/v1.0/hf/xzq/queryxzqbybdcdyh")
    String queryXzqByBdcdyh(@RequestParam(name = "bdcdyh") String bdcdyh,
                            @RequestParam(name = "hslx") String hslx,
                            @RequestParam(name = "qjgldm", required = false) String qjgldm);

}
