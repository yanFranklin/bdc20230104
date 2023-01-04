package cn.gtmap.realestate.etl.web.main;

import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019-04-23
 * @description
 */
@Validated
public class BaseController {

    /**
     * sly 页面请求时的默认返回参数
     *
     * @param success 是否成功
     * @param msg 要返回的信息
     * @return
     */
    public static Map returnHtmlResult(Boolean success, String msg) {
        Map resultMap = new HashMap(1);
        resultMap.put("success", success);
        resultMap.put("msg", msg);
        return resultMap;
    }
}
