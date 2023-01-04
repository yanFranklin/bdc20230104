package cn.gtmap.realestate.common.core.service.rest.building;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-18
 * @description 实体处理服务
 */
public interface EntityRestService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param json
     * @param className
     * @description 根据JSON更新实体
     */
    @PostMapping("/building/rest/v1.0/entity/update")
    void updateEntityByJson(@RequestParam(name = "json") String json,
                           @RequestParam(name = "className") String className);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param jsonArray
     * @param className
     * @description 根据JSON更新实体
     */
    @PostMapping("/building/rest/v1.0/entity/batchupdate")
    void batchUpdateEntityByJson(@RequestParam(name = "jsonArray")String jsonArray,
                                 @RequestParam(name = "className")String className);
}
