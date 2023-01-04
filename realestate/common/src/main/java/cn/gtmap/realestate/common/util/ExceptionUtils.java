package cn.gtmap.realestate.common.util;

import cn.gtmap.gtc.feign.common.exception.GtFeignException;
import cn.gtmap.realestate.common.core.ex.AppException;
import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/30
 * @description 异常信息
 */
public class ExceptionUtils {

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionUtils.class);

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取Feign请求异常信息
     */
    public static String getFeignErrorMsg(Exception ex){
        String errormsg ="";
        if(ex instanceof GtFeignException ||ex instanceof AppException ||ex instanceof IllegalAccessException ||ex.getCause() instanceof GtFeignException ||ex.getCause() instanceof HystrixRuntimeException){
            errormsg = getErrorMsg(ex);
        }else if(ex instanceof InvocationTargetException ||ex.getCause() instanceof InvocationTargetException){
            InvocationTargetException e =null;
            if(ex instanceof InvocationTargetException){
                e =(InvocationTargetException) ex;
            }else{
                e=(InvocationTargetException) ex.getCause();
            }
            if(e.getTargetException() instanceof AppException){
                errormsg = ((InvocationTargetException) ex).getTargetException().getMessage();
            }else {
                errormsg = getErrorMsg(ex);
            }

        } else if(ex.getCause() instanceof Exception){
            errormsg =ex.getCause().getMessage();
        } else{
            errormsg = ex.getMessage();
        }
        LOGGER.error(errormsg,ex);
        return errormsg;

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取异常信息
     */
    private static String getErrorMsg(Exception ex){
        String msg = "通过应用内部服务方式请求方法异常";
        GtFeignException gte = null;
        // lyq 2018-12-30
        // 增加判断是否是feign异常
        if (ex instanceof GtFeignException) {
            gte = (GtFeignException) ex;
        }
        if(ex.getCause() instanceof HystrixRuntimeException){
            ex = (HystrixRuntimeException)ex.getCause();
        }
        // 或者是 有Hystix异常包装过的 feign异常 处理 异常码
        if ( ex.getCause() instanceof GtFeignException) {
            gte = ((GtFeignException) ex.getCause());
        }
        if (gte != null) {
            String responseBody = gte.getMsgBody();
            Map bodyMap = JSONObject.parseObject(responseBody, Map.class);
            if (MapUtils.getInteger(bodyMap, "code") != null
                    && StringUtils.isNotBlank(MapUtils.getString(bodyMap, "msg"))) {
                msg = MapUtils.getString(bodyMap, "msg");
                if(StringUtils.isNotBlank(MapUtils.getString(bodyMap,"detail"))){
                    msg = msg +","+MapUtils.getString(bodyMap,"detail");
                }
            }
        }else{
            msg =ex.getMessage();
        }
        return msg;
    }
}
