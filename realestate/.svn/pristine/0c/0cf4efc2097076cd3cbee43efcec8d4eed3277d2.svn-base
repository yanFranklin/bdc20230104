package cn.gtmap.realestate.portal.ui.config;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截
 * @author <a href=""mailto:lisongtao@gtmap.cn>lisongtao</a>
 * @version 1.0, 2019/2/25
 * @description
 */
public class CommonInterceptor implements HandlerInterceptor {

    private static  final Logger LOGGER = LoggerFactory.getLogger(CommonInterceptor.class);

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 用户信息工具
     */
    @Autowired
    protected UserManagerUtils userManagerUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //验证是否能获取到用户
        Boolean checkUser=false;
        String userName=userManagerUtils.getCurrentUserName();
        if(StringUtils.isNotBlank(userName)){
            checkUser=true;
        }
        //验证不通过的返回响应结构
        if(!checkUser){
            throw new AppException("获取当前用户为空,请重新登陆");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Do nothing
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Do nothing
    }
}
