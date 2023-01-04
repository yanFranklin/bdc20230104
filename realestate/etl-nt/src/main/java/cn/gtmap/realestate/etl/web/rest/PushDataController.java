package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.etl.service.ExportService;
import cn.gtmap.realestate.etl.service.PushBljdService;
import cn.gtmap.realestate.etl.service.PushQlygService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private ExportService exportService;

    @Value("${bjzjk.push.enable:false}")
    private Boolean bjzjkPush;

    @Autowired
    PushBljdService pushBljdService;

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param xmid 项目xmid
     * @rerutn
     * @description 根据项目proid推送共享权利阳光数据到政务前置机
     */
    @RequestMapping("/qlygData")
    public void qlygData(String gzlslid) {
        Map queryMap = new HashMap();
        if(StringUtils.isNotBlank(gzlslid)){
            queryMap.put("gzlslid",gzlslid);
            pushQlygService.pushQlygData(queryMap);
            if(bjzjkPush) {
                pushBljdService.pushBlData(queryMap);
            }
        }
    }


    @RequestMapping(value = "/exportPdfZs")
    public void turnProjectEventPdfZs(String gzlslid) {
        /*exportService.ExportPdfPl(gzlslid);*/
    }

}
