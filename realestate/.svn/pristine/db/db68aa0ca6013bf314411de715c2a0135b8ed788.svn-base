package cn.gtmap.realestate.certificate.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/3/5
 */
public class ClientInfoUtil {
    private static final Logger logger = LoggerFactory.getLogger(ClientInfoUtil.class);
    private static final String UNKNOWN = "unknown";
    private static final String LESS = "<";
    private static final String EQUAL = "=";
    private static final String MAC = "MAC";

    // 得到客户端IP地址
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0
                || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();

            //这里主要是获取本机的ip,可有可无
            if (ipAddress.equals("127.0.0.1")
                    || ipAddress.endsWith("0:0:0:0:0:0:1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    logger.error("getIpAddr:UnknownHostException", e);
                }
                if (null != inet) {
                    ipAddress = inet.getHostAddress();
                }
            }

        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15 && ipAddress.indexOf(',') > -1) {
            // = 15
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(','));
        }
        //或者这样也行,对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        return ipAddress;
    }

    // 得到客户端MAC地址
    public static String getMACAddress(String ip) {
        String macAddress = "";
        try {
            Process process = Runtime.getRuntime().exec("nbtstat -A " + ip);
            InputStreamReader ir = new InputStreamReader(process.getInputStream(),"GBK");
            LineNumberReader input = new LineNumberReader(ir);
            String str = "";
            while ((str = input.readLine()) != null) {
                str = str.toUpperCase();
                if (StringUtils.isNotBlank(str) && str.indexOf(MAC) > -1) {
                    macAddress = str.substring(str.indexOf(EQUAL) + 2, str.length());
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("getMACAddress", e);
        }
        return macAddress;
    }

    //得到客户端计算机名
    public static String getComputerName(String ip){
        String computerName = "";
        String str = "";
        try {
            Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
            LineNumberReader input = new LineNumberReader(new InputStreamReader(p.getInputStream(),"GBK"));
            while ((str = input.readLine()) != null) {
                str = StringUtil.byteToStrUtf8(str.getBytes());
                if (StringUtils.isNotBlank(str)) {
                    str=str.trim().replaceAll("\\s{1,}", "");
                    int countLess = str.indexOf(LESS);
                    if (countLess > 1 ) {
                        computerName = str.substring(0, countLess);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            logger.error("ClientInfoUtil:getComputerName", e);
        }
        return computerName;
    }

    public static String getMacFromBytes(byte[] bytes) {
        StringBuilder mac = new StringBuilder();
        byte currentByte;
        boolean first = false;
        for (byte b : bytes) {
            if (first) {
                mac.append("-");
            }
            currentByte = (byte) ((b & 240) >> 4);
            mac.append(Integer.toHexString(currentByte));
            currentByte = (byte) (b & 15);
            mac.append(Integer.toHexString(currentByte));
            first = true;
        }
        return mac.toString().toUpperCase();
    }

    private ClientInfoUtil() {
    }
}
