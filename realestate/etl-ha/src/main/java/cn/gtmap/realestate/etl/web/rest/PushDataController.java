package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.etl.service.PushQlygService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/*
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0, 2019/9/12
 * @description 将共享库抽取到外网前置机
 */

@RestController
@RequestMapping("/realestate-etl/rest/v1.0/push")
public class PushDataController {
    @Autowired
    private PushQlygService pushQlygService;

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param gzlslid 工作流实例ID
     * @rerutn
     * @description 根据项目proid推送共享权利阳光数据到政务前置机
     */
    @RequestMapping("/qlygData")
    public void qlygData(String gzlslid) {
        Map queryMap = new HashMap();
        if(StringUtils.isNotBlank(gzlslid)){
            queryMap.put("gzlslid",gzlslid);
            pushQlygService.pushQlygData(queryMap);
        }
    }


    @RequestMapping(value = "/exportPdfZs")
    public void turnProjectEventPdfZs(String gzlslid) {
        /*exportService.ExportPdfPl(gzlslid);*/
    }

}
