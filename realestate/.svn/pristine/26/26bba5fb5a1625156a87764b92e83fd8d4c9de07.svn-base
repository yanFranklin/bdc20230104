package cn.gtmap.realestate.certificate.core.support.interceptor;

import cn.gtmap.realestate.certificate.core.model.ResponseData;
import cn.gtmap.realestate.certificate.util.StringUtil;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.ResponseHead;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzLogDO;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzTokenDo;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.InterfacePermission;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzLogService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzQqjlService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzTokenService;
import cn.gtmap.realestate.certificate.core.support.annotations.CheckToken;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.certificate.util.JJWTUtil;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/3/19
 */
public class InterceptorCheckToken implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(InterceptorCheckToken.class);

    @Value("${base64.security}")
    private String base64Security;
    @Autowired
    private InterfacePermission interfacePermission;
    @Autowired
    private BdcDzzzTokenService bdcDzzzTokenService;
    @Autowired
    private BdcDzzzQqjlService bdcDzzzQqjlService;
    @Autowired
    private BdcDzzzLogService bdcDzzzLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 有 @CheckToken 注解，需要认证
            if (method.isAnnotationPresent(CheckToken.class)) {
                String token = request.getParameter("token");
                Boolean checkToken = false;
                if (StringUtils.isNotBlank(token)) {
                    Claims claims = JJWTUtil.parseJWT(token, base64Security);
                    if (claims != null && claims.size() > 0) {
                        String yymc = (String) claims.get(Constants.YYMC);
                        request.setAttribute(Constants.YYMC, yymc);
                        checkToken = checkYyqx(request.getServletPath(), yymc);
                    }
                } else {
                    return responseMessage(request, response
                            , new DzzzResponseModel(new ResponseHead(ResponseEnum.FALSE.getCode(), ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode())
                                    , new ResponseData("未获取到token参数！")));
                }

                if (!checkToken) {
                    return responseMessage(request, response
                            , new DzzzResponseModel(new ResponseHead(ResponseEnum.FALSE.getCode(), ResponseEnum.RESPONSE_UNAUTHORIZED_ERROR.getCode())
                                    , new ResponseData("未获得接口授权！")));
                }
            }
        }
        return true;
    }

    /**
     * @param request
     * @param response
     * @param dzzzResponseModel
     * @return
     * @description 插入日志并返回消息
     */
    private boolean responseMessage(HttpServletRequest request, HttpServletResponse response, DzzzResponseModel dzzzResponseModel) {
        //String flag = StringUtil.getUUID32();
        PrintWriter out = null;

        // 请求日志
        BdcDzzzLogDO bdcDzzzLogDO = bdcDzzzLogService.getRequestData("", request.getRequestURI(), (String) request.getAttribute(Constants.YYMC));
        //bdcDzzzLogService.insertBdcDzzzLog(bdcDzzzLogDO);
        try {
            response.setContentType("application/json;charset=utf-8");
            out = response.getWriter();
            out.println(JSONObject.toJSONString(dzzzResponseModel));
            out.flush();
        } catch (Exception e) {
            logger.error("InterceptorCheckToken:preHandle", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }

        // 响应日志
        //bdcDzzzLogService.insertResponseData(bdcDzzzLogDO.getParamJson(),flag, JSON.toJSONString(dzzzResponseModel));
        bdcDzzzLogService.getResponseData(bdcDzzzLogDO,  JSON.toJSONString(dzzzResponseModel));
        bdcDzzzLogService.insertBdcDzzzLog(bdcDzzzLogDO);
        return false;
    }

    /**
     * @param servletPath
     * @param yymc
     * @return
     * @description 验证接口权限
     */
    private boolean checkYyqx(String servletPath, String yymc) {
        BdcDzzzTokenDo bdcDzzzTokenDo = bdcDzzzTokenService.queryTokenByTokenName(yymc);
        if (null != bdcDzzzTokenDo && StringUtils.isNotBlank(servletPath)) {
            if (servletPath.contains("/realestate-e-certificate")) {
                servletPath = servletPath.substring(25);
            }
            String yyqx = bdcDzzzTokenDo.getYyqx();
            String pathValue = interfacePermission.getYyqxMap().get(servletPath);
            if (StringUtils.isNotBlank(yyqx)) {
                String[] yyqxArr = yyqx.split(",");
                for (String y : yyqxArr) {
                    if (StringUtils.equals(y, pathValue)) {
                        bdcDzzzQqjlService.insertBdcDzzzQqjl(bdcDzzzTokenDo, pathValue);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
