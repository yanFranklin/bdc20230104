package cn.gtmap.realestate.inquiry.ui.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/04/16
 * @description
 */
public  class IpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(IpUtils.class);

    private IpUtils() {
    }

    /**
     * 从request对象中获取ip
     * @param request
     * @return
     */
    public static String getIpFromRequest(HttpServletRequest request){
        if(request != null){
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            return ip;
        }
        return "";
    }

    //查找地址中的ip
    public static String getIps(String ipString) {
        String url = "";
        String regEx = "(\\d+\\.\\d+\\.\\d+\\.\\d+)\\:(\\d+)";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(ipString);
        while (m.find()) {
            url = url + m.group(1) + ":" + m.group(2);
        }
        return url;
    }
}
