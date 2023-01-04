package cn.gtmap.realestate.exchange.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/2 11:21
 * @description ip地址工具类
 */
public class IPUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(cn.gtmap.realestate.common.util.IPUtils.class);

    /**
     * 请求通过反向代理之后，可能包含请求客户端真实IP的HTTP HEADER
     * 如果后续扩展，有其他可能包含IP的HTTP HEADER，加到这里即可
     */
    private final static String[] POSSIBLE_HEADERS = new String[] {
            "X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP",
            "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"
    };

    /**
     * 获取请求客户端的真实IP地址
     * @param request javax.servlet.http.HttpServletRequest
     * @return 客户端端真实IP地址
     */
    public static String getRequestClientRealIP(HttpServletRequest request) {
        String ip;
        // 先检查代理：逐个HTTP HEADER检查过去，看看是否存在客户端真实IP
        for (String header : POSSIBLE_HEADERS) {
            ip = request.getHeader(header);
            if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                // 请求经过多次反向代理后可能会有多个IP值（以英文逗号分隔），第一个IP才是客户端真实IP
                return ip.contains(",") ? ip.split(",")[0] : ip;
            }
        }
        // 从所有可能的HTTP HEADER中都没有找到客户端真实IP，采用request.getRemoteAddr()来兜底
        ip = request.getRemoteAddr();
        if ("0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip)) {
            // 说明是从本机发出的请求，直接获取并返回本机IP地址
            return getLocalRealIP();
        }
        return ip;
    }

    /**
     * 获取本机IP地址
     * @return 若配置了外网IP则优先返回外网IP；否则返回本地IP地址。如果本机没有被分配局域网IP地址（例如本机没有连接任何网络），则默认返回127.0.0.1
     */
    public static String getLocalRealIP() {
        String localIP = "127.0.0.1";
        String netIP = null;

        Enumeration<NetworkInterface> netInterfaces;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
            // 发生异常则返回null
            return null;
        }
        InetAddress ip;
        // 是否找到外网IPS
        boolean netIPFound = false;
        while (netInterfaces.hasMoreElements() && !netIPFound) {
            Enumeration<InetAddress> address = netInterfaces.nextElement().getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {
                    // 外网IP
                    netIP = ip.getHostAddress();
                    netIPFound = true;
                    break;
                } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {
                    // 内网IP
                    localIP = ip.getHostAddress();
                }
            }
        }

        if (StringUtils.isNotBlank(netIP)) {
            // 如果配置了外网IP则优先返回外网IP地址
            return netIP;
        }
        return localIP;
    }

    /**
     * @description: 获取本机mac地址
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @date: 2022/12/21 16:22
     * @param
     * @return: java.lang.String
     **/
    public static String getLocalMac(){
        byte[] mac = new byte[0];
        try {
            InetAddress ipAddress = InetAddress.getLocalHost();
            mac = NetworkInterface.getByInetAddress(ipAddress).getHardwareAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            LOGGER.error("获取主机失败：{}",e);
            return null;
        } catch (SocketException e) {
            e.printStackTrace();
            LOGGER.error("获取mac地址失败：{}",e);
            return null;
        }
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            //字节转换为整数
            int temp = mac[i] & 0xff;
            String str = Integer.toHexString(temp);
            if (str.length() == 1) {
                sb.append("0" + str);
            } else {
                sb.append(str);
            }
        }
        return sb.toString().toUpperCase();
    }

}
