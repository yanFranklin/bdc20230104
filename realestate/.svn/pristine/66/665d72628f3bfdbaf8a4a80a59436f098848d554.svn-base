package cn.gtmap.realestate.common.core.service.rest.accept;


import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/9/14
 * @description
 */
public interface BdcSlxxHxRestService {

    /**
     * 保存或更新受理信息到平台
     * @param gzlslid 工作流实例ID
     * @throws Exception
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/hx/{gzlslid}")
    void hxBdcSlxx(@PathVariable("gzlslid") String gzlslid) throws Exception;

    /**
     * 保存或更新受理信息和自定义信息至大云平台
     * @param gzlslid 工作流实例
     * @param zdyxxMap 自定义信息
     * @throws Exception
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/hx/zdyxx/{gzlslid}")
    void hxBdcSlxxWithZdyxx(@PathVariable("gzlslid") String gzlslid, @RequestBody Map zdyxxMap) throws Exception;
}
