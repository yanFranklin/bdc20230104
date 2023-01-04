package cn.gtmap.realestate.common.core.support.spring;


import cn.gtmap.gtc.feign.common.exception.GtFeignException;
import cn.gtmap.realestate.common.core.enums.ExceptionMessageEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2018/10/29
 * @description 全局Controller统一异常处理类
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    private final ObjectMapper objectMapper;

    @Autowired
    public ControllerExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(Exception.class)
    public void getError(HttpServletResponse response, Exception ex) throws IOException {
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        Integer errorCode = AppException.SERVER_EX;
        StringBuilder errorMsgBuilder = new StringBuilder();
        if (ex instanceof IOException) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            errorCode = AppException.IO_EX;
            errorMsgBuilder.append(ExceptionMessageEnum.IO_EX.getMessage());
        }
        if (ex instanceof AppException) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            if (((AppException) ex).getCode() != null) {
                errorCode = ((AppException) ex).getCode();
            }
            errorMsgBuilder.append(ex.getMessage());
        }
        if (ex instanceof ConstraintViolationException) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorCode = ErrorCode.VALIDATION_EX;
            for (ConstraintViolation violation : ((ConstraintViolationException) ex).getConstraintViolations()) {
                errorMsgBuilder.append(violation.getMessage());
            }
        }
        if (ex instanceof TimeoutException) {
            response.setStatus(HttpStatus.NOT_IMPLEMENTED.value());
            errorCode = ErrorCode.IO_EX;
            errorMsgBuilder.append(ExceptionMessageEnum.TIMEOUT_EX.getMessage());
        }
        if (ex instanceof BindException) {
            // 实体内的 Validate 注解 验证不通过时 抛BindException
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            BindingResult bindingResult = ((BindException) ex).getBindingResult();
            if (bindingResult != null && bindingResult.getFieldError() != null) {
                FieldError fieldError = bindingResult.getFieldError();
                if (fieldError != null) {
                    errorCode = ErrorCode.VALIDATION_EX;
                    errorMsgBuilder.append(fieldError.getDefaultMessage());
                }
            }
            if (errorCode != ErrorCode.VALIDATION_EX) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                errorMsgBuilder.append(ex.getMessage());
            }
        }

        GtFeignException gte = null;
        // lyq 2018-12-30
        // 增加判断是否是feign异常
        if (ex instanceof GtFeignException) {
            gte = (GtFeignException) ex;
        }
        // 或者是 有Hystix异常包装过的 feign异常 处理 异常码
        if (ex.getCause() != null && ex.getCause() instanceof GtFeignException) {
            gte = ((GtFeignException) ex.getCause());
        }
        if (gte != null) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            String responseBody = gte.getMsgBody();
            Map bodyMap = JSONObject.parseObject(responseBody, Map.class);
            if (MapUtils.getInteger(bodyMap, "code") != null
                    && StringUtils.isNotBlank(MapUtils.getString(bodyMap, "msg"))) {
                errorCode = MapUtils.getInteger(bodyMap, "code");
                errorMsgBuilder.append(MapUtils.getString(bodyMap, "msg"));
            } else {
                errorCode = gte.getCode();
            }
        }
        //数据库异常
        if (ex instanceof DataAccessException || ex instanceof SQLException) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorCode = AppException.DB_EX;
            errorMsgBuilder.append(ExceptionMessageEnum.DB_EX.getMessage());
        }
        //消息队列异常
        if (ex instanceof AmqpException) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorCode = AppException.MQ_EX;
            errorMsgBuilder.append(ExceptionMessageEnum.MQ_EX.getMessage());
        }
        //不支持此操作
        if(ex instanceof UnsupportedOperationException){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorCode = AppException.UN_SUPPORTED;
            errorMsgBuilder.append(ExceptionMessageEnum.UN_SUPPORT_EX.getMessage());
        }
        //类访问异常
        if(ex instanceof ClassNotFoundException || ex instanceof ClassCastException){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorCode = AppException.CLASS_EX;
            errorMsgBuilder.append(ExceptionMessageEnum.CLASS_EX.getMessage());
        }
        //其他异常统一返回服务器异常
        if (StringUtils.isBlank(errorMsgBuilder.toString())) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorMsgBuilder.append(ExceptionMessageEnum.SERVER_EX.getMessage());
        }
        List<String> exceptionDetail=Lists.newArrayList();
        exceptionDetail.add(ex.getMessage());
        if(ex.getCause() !=null){
            exceptionDetail.add(ex.getCause().getMessage());
        }
        logger.error(errorMsgBuilder.toString(), ex);
        Map<String, Object> resultMap = new ImmutableMap.Builder<String, Object>().put("code", errorCode)
                .put("msg", errorMsgBuilder).put("detail",exceptionDetail).build();
        objectMapper.writeValue(response.getWriter(), resultMap);
    }
}
