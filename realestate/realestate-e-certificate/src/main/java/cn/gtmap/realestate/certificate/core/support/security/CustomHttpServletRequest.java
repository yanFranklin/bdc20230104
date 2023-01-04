package cn.gtmap.realestate.certificate.core.support.security;

import cn.gtmap.realestate.certificate.util.XssShieldUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author <a href="mailto:sly@gtmap.cn">sly</a>
 * @version 1.0  2018/1/9.
 * @description xss拦截 参数特殊字符过滤
 */
public class CustomHttpServletRequest extends HttpServletRequestWrapper {

    public CustomHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        // 返回值之前 先进行过滤
        return XssShieldUtils.stripXss(super.getParameter(XssShieldUtils.stripXss(name)));
    }

    @Override
    public String[] getParameterValues(String name) {
        // 返回值之前 先进行过滤
        String[] values = super.getParameterValues(XssShieldUtils.stripXss(name));
        if(values != null){
            for (int i = 0; i < values.length; i++) {
                values[i] = XssShieldUtils.stripXss(values[i]);
            }
        }
        return values;
    }


}
