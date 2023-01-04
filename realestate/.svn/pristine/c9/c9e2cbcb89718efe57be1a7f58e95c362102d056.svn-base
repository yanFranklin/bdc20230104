package cn.gtmap.realestate.certificate.util;


import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.ResponseHead;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import com.alibaba.fastjson.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/3/25
 */
public class ExceptionUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);

    public static DzzzResponseModel getExceptionInfo(Throwable e){
        if (e instanceof HttpMessageNotReadableException || e instanceof JSONException) {
            return new DzzzResponseModel(new ResponseHead(ResponseEnum.FALSE.getCode(), ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode())
                    ,null);
        }
        return new DzzzResponseModel(new ResponseHead(ResponseEnum.FALSE.getCode(), ResponseEnum.RESPONSE_SERVICE_TIMEOUT_ERROR.getCode())
                ,null);
    }

    private ExceptionUtil() {
    }
}
