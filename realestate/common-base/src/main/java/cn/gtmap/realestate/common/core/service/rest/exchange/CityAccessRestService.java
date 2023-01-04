package cn.gtmap.realestate.common.core.service.rest.exchange;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022-10-31
 * @description 市级自动上报 服务
 */
public interface CityAccessRestService {

    /**
     * 市级上报（根据工作流实例ID进行上报），工作流事件
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param processInsId 工作流主键
     */
    @GetMapping("/realestate-exchange/rest/v1.0/city/access/proccessid")
    void cityAccessByProcessInsId(@RequestParam(name = "processInsId") String processInsId);
}
