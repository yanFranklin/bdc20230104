package cn.gtmap.realestate.common.core.support.spring;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.EnumSet;

/**
 * html特殊页面过滤器
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/8/16.
 * @description
 */
@Component
@ConfigurationProperties(prefix = "html")
public class TsHtmlFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TsHtmlFilter.class);

    /**
     * 页面版本
     */
    private String version;
    /**
     * 页面父版本
     */
    private String parversion;

    /**
    * 是否展示大屏页面
    **/
    @Value("${sfzsdp:false}")
    private boolean sfzsdp;

    @Value("${role:ROLE_DP}")
    private String dp_Role;

    @Value("${dpPath:/home/bdcDp.html}")
    private String dpPath;

    @Value("${homePagePath:/home/home-page.html}")
    private String homePagePath;

    @Bean("htmlFilter")
    public Filter filter() {
        return new Filter() {
            @Override
            public void init(FilterConfig filterConfig) throws ServletException {
                LOGGER.info("html filter init!");
            }

            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)  throws IOException, ServletException{
                HttpServletRequest httpRequest = (HttpServletRequest)request;
                String path=httpRequest.getServletPath();
                String toHomePage = request.getParameter("toHomePage");
                if(sfzsdp && StringUtils.isBlank(toHomePage) && homePagePath.equals(path)){
                    HttpSession session = httpRequest.getSession();
                    SecurityContext ctx =(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
                    Authentication au = ctx.getAuthentication();
                    Collection<? extends GrantedAuthority> authorities = au.getAuthorities();
                    for (GrantedAuthority authority : authorities) {
                        if(authority.getAuthority().equals(dp_Role)){
                                Resource resource = new ClassPathResource("/META-INF/resources" + dpPath);
                                if (resource.exists()) {
                                    httpRequest.getRequestDispatcher(dpPath).forward(httpRequest, response);
                                    return;
                                }
                        }
                    }

                }
                if(StringUtils.isNotBlank(version) && path.indexOf("/"+version+"/")<0){
                    String tspath=path.replaceFirst("view",version);
                    if(!StringUtils.equals(tspath,path)) {
                        Resource resource = new ClassPathResource("/META-INF/resources" + tspath);
                        if (resource.exists()) {
                            httpRequest.getRequestDispatcher(tspath).forward(httpRequest, response);
                            return;
                        }
                        if (StringUtils.isNotBlank(parversion) && path.indexOf("/" + parversion + "/") < 0) {
                            tspath = path.replaceFirst("view", parversion);
                            if (!StringUtils.equals(tspath, path)) {
                                resource = new ClassPathResource("/META-INF/resources" + tspath);
                                if (resource.exists()) {
                                    httpRequest.getRequestDispatcher(tspath).forward(httpRequest, response);
                                    return;
                                }
                            }
                        }
                    }
                }
                chain.doFilter(request,response);
            }

            @Override
            public void destroy() {
            }
        };
    }

    @Bean
    public DelegatingFilterProxyRegistrationBean delegatingFilterProxyRegistrationBean(){
        DelegatingFilterProxyRegistrationBean bean = new DelegatingFilterProxyRegistrationBean("htmlFilter");
        bean.addUrlPatterns("*.html");
        bean.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        return bean;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getParversion() {
        return parversion;
    }

    public void setParversion(String parversion) {
        this.parversion = parversion;
    }

}
