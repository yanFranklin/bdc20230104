package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.ZdQsdcDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-19
 * @description 宗地权属调查服务
 */
public interface ZdQsdcRestService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.ZdQsdcDO
     * @description 根据bdcdyh 查询 宗地权属调查服务
     */
    @GetMapping("/building/rest/v1.0/zdqsdc/{bdcdyh}")
    ZdQsdcDO queryZdQsdcByBdcdyh(@PathVariable("bdcdyh")String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);
}
