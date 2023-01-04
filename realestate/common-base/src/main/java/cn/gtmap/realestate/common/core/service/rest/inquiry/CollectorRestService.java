package cn.gtmap.realestate.common.core.service.rest.inquiry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/07/22
 * @description
 */
public interface CollectorRestService {
    /***
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description 调用zipKin接口获取慢服务信息
    */
    @GetMapping("/api/v1/traces")
    List<List<Map<String,Object>>> getTraces(@RequestParam(value = "serviceName",required = false) String serviceName,
                                             @RequestParam(value = "spanName",required = false) String spanName,
                                             @RequestParam(value = "annotationQuery",required = false) String annotationQuery,
                                             @RequestParam(value = "minDuration",required = false) Integer minDuration,
                                             @RequestParam(value = "maxDuration",required = false) Integer maxDuration,
                                             @RequestParam(value = "endTs",required = false) Long endTs,
                                             @RequestParam(value = "lookback",required = false) Long lookback,
                                             @RequestParam(value = "limit",required = false) Integer limit);

    /**
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description 根据traceId获取对应链路信息
    */
    @GetMapping("/api/v1/trace/{traceIdHex}")
    List<Map<String,Object>> getDetailMsg(@PathVariable(name = "traceIdHex") String traceId);
}
