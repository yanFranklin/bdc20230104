package cn.gtmap.realestate.exchange.web.rest;


import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019-08-29
 * @description
 */
@OpenController(consumer = "一张网数据共享服务")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/ywz")
@Api(tags = "一张网数据共享服务")
public class YzwRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YzwRestController.class);

    @Autowired
    private YzwService yzwService;

    /**
     * @param gzlslid      工作流实例ID
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @rerutn
     * @description  办件中做共享数据
     */
    @OpenApi(apiDescription = "办件中做共享数据",apiName = "",openLog = false)
    @RequestMapping("/share/process/data/{gzlslid}")
    @ResponseBody
    public void shareProcessData(@PathVariable("gzlslid") String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            yzwService.shareYzwData(gzlslid,false);
        }
    }

    /**
     * @param gzlslid      工作流实例ID
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @rerutn
     * @description  办结做共享数据
     */
    @OpenApi(apiDescription = "办结做共享数据", apiName = "", openLog = false)
    @RequestMapping("/share/result/data/{gzlslid}")
    @ResponseBody
    public void shareResultData(@PathVariable("gzlslid") String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            yzwService.shareYzwData(gzlslid, true);
        }
    }

    /**
      * 删除时，推送inf_apply_result表
      * @param gzlslid
      * @Date 2021/2/8
      * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
         */
    @OpenApi(apiDescription = "删除时推送inf_apply_result数据", apiName = "", openLog = false)
    @RequestMapping("/share/delete/result")
    @ResponseBody
    public void deleteResultData(@RequestParam(name = "gzlslid") String gzlslid){
        if(StringUtils.isNotBlank(gzlslid)){
            yzwService.deleteTsResult(gzlslid);
        }

    }


}
