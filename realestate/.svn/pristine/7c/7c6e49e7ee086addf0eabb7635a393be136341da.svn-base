package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.etl.config.SchedulerTask;
import cn.gtmap.realestate.etl.util.QuartzUtil;
import cn.gtmap.realestate.etl.web.main.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019-04-18
 * @description
 */
@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Autowired
    private SchedulerTask schedulerTask;

    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 打开（关闭）处理失败定时任务开关
     */
    @RequestMapping("/disposefalse/open")
    @ResponseBody
    public Map disposefalse(Boolean bl){
        QuartzUtil.setDisposeFalseSwitch(bl);
        if(bl){
            return returnHtmlResult(bl, "打开成功");
        }else {
            return returnHtmlResult(bl, "关闭成功");
        }
    }
    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 查看处理失败定时任务开关
     */
    @RequestMapping("/checkswitch")
    @ResponseBody
    public Map checkSwitch(){
        if(QuartzUtil.isDisposeFalseSwitch()){
            return returnHtmlResult(QuartzUtil.isDisposeFalseSwitch(), "打开");
        }else {
            return returnHtmlResult(QuartzUtil.isDisposeFalseSwitch(), "关闭");
        }
    }

    @ResponseBody
    @RequestMapping("/dealtask")
    public String dealTask(){
        schedulerTask.listDjtDjSlSqQuarz();
        return "end";
    }
}
