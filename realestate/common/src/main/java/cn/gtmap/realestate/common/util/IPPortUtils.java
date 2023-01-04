package cn.gtmap.realestate.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/19
 * @description 获取当前IP和端口
 */
@Component
public class IPPortUtils {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(IPPortUtils.class);
    /**
     * 冒号
     */
    private static final String SPLIT = ":";
    /**
     * 请求头协议Protocol http
     */
    private static final String PROTOCOL_HTTP = "http://";

    /**
     * @return
     * @throws MalformedObjectNameException 获取当前机器的端口号
     */
    public static String getLocalPort() throws MalformedObjectNameException {
        MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
        Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
                Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
        return objectNames.iterator().next().getKeyProperty("port");
    }

    /**
     * @return 获取当前机器的IP
     */
    public static String getLocalIP() {
        StringBuilder ipAddrStr = new StringBuilder();
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            LOGGER.error("未知的域名", e);
        }
        if (null != addr) {
            byte[] ipAddr = addr.getAddress();
            for (int i = 0; i < ipAddr.length; i++) {
                if (i > 0) {
                    ipAddrStr = ipAddrStr.append(".");
                }
                ipAddrStr = ipAddrStr.append(ipAddr[i] & 0xFF);
            }
        }
        return ipAddrStr.toString();
    }

    /**
     * @return String 返回server路径
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取服务的IP和端口
     */
    public static String serverPath() {
        String ipAddrStr = getLocalIP();
        String port = null;
        try {
            port = getLocalPort();
        } catch (MalformedObjectNameException e) {
            LOGGER.error("端口获取异常！", e);
        }
        if (StringUtils.isNotBlank(ipAddrStr) && StringUtils.isNotBlank(port)) {
            return PROTOCOL_HTTP + ipAddrStr + SPLIT + port;
        }
        return null;
    }

    /**
     * 获取客户端ip
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param request
     *@return ip
     *@description
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip ="";
        if(request!=null){
            ip = request.getHeader("X-Forwarded-For");
            try {
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
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
                    if (ip.equals("127.0.0.1")
                            || ip.endsWith("0:0:0:0:0:0:1")) {
                        // 根据网卡取本机配置的IP
                        InetAddress inet = null;
                        try {
                            inet = InetAddress.getLocalHost();
                        } catch (UnknownHostException e) {
                            LOGGER.error("getIpAddr:UnknownHostException", e);
                        }
                        if (null != inet) {
                            ip = inet.getHostAddress();
                        }
                    }

                } else if (ip.length() > 15) {
                    String[] ips = ip.split(",");
                    for (int index = 0; index < ips.length; index++) {
                        String strIp = (String) ips[index];
                        if (!("unknown".equalsIgnoreCase(strIp))) {
                            ip = strIp;
                            break;
                        }
                    }
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }
            } catch (Exception e) {
                LOGGER.error(null, e);
            }
        }
        return ip;
    }

    /**
     * 查找字符串中的ip地址
     * @param ipString
     * @return
     */
    public static String getIps(String ipString){
        String url = "";
        String regEx = "(\\d+\\.\\d+\\.\\d+\\.\\d+)\\:(\\d+)";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(ipString);
        while (m.find()) {
            url = url + m.group(1)+ ":" + m.group(2);
        }
        return url;
    }
}
