package cn.gtmap.realestate.exchange.web;

import cn.gtmap.realestate.exchange.service.AccessModelHandlerService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0
 * @description 上报
 */
@Controller
@RequestMapping("/rest/v1.0/access")
public class NationalAccessRestController {
    private static Logger LOGGER = LoggerFactory.getLogger(NationalAccessRestController.class);


    @Autowired
    AccessModelHandlerService accessModelHandlerService;

    @GetMapping("/xmidlist")
    @ResponseBody
    void autoAccessByXmidList(@RequestParam(name = "xmidList") List<String> xmidList) {
        accessModelHandlerService.autoAccessByXmidList(xmidList);
    }

    /**
     * @param startDate
     * @param endDate
     * @return void
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据时间区间上报-补偿接口
     */
    @GetMapping("/time/interval")
    public void autoAccessByTimeInterval(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("type") String type, @RequestParam(required = false, value = "xmly") String xmly) {
        //获取时间区间的xmList
        List<String> xmidList = accessModelHandlerService.getXmListToAccessByTimeInterval(startDate, endDate, type, xmly);
        if (CollectionUtils.isNotEmpty(xmidList)) {
            LOGGER.info("开始多线程处理该时间段内的项目信息:" + startDate + "~" + endDate);
            accessModelHandlerService.autoAccessByTimeInterval(xmidList);
        } else {
            LOGGER.info("未查询到需要处理的项目信息");
        }
    }

}
