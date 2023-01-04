package cn.gtmap.interchange.web;

import cn.gtmap.interchange.core.domain.InfApply;
import cn.gtmap.interchange.service.InfApplyService;
import cn.gtmap.interchange.service.PushQlygDataService;
import cn.gtmap.interchange.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/realestate-interchange/rest/v1.0/push")
public class PushDataRestController {

    @Autowired
    InfApplyService infApplyService;

    @Autowired
    PushQlygDataService pushQlygDataService;

    @GetMapping("/test")
    public String test(){
        return "hello world  " + new Date().toString();
    }

    /**
     * 推送某一天未同步的数据
     */
    @GetMapping("/wtb")
    public void pushWtbInfApplyData(@RequestParam(value="date") String date){
        if(StringUtils.isNotBlank(date)){
            Date update_date = DateUtils.parseStringToDate(DateUtils.FORMATTER, date);
            Map<String, Object> param = new HashMap<>();
            param.put("update_date", update_date);
            List<InfApply> infApplyList = this.infApplyService.queryWtbInfApply(param);
            if(CollectionUtils.isNotEmpty(infApplyList)){
                this.pushQlygDataService.pushInfApplyData(infApplyList);
            }
        }
    }

    /**
     * 根据业务号推送数据
     * @param ywh
     */
    @GetMapping("/ywh")
    public void pushInfApplyDataByYwh(@RequestParam(value="ywh") String ywh){
        if(StringUtils.isNotBlank(ywh)){
            Map<String, Object> param = new HashMap<>();
            param.put("proid", ywh);
            List<InfApply> infApplyList = this.infApplyService.getInfApply(param);
            if(CollectionUtils.isNotEmpty(infApplyList)){
                this.pushQlygDataService.pushInfApplyData(infApplyList);
            }
        }
    }

}
