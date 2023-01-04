package cn.gtmap.realestate.exchange.util.zrpay;

import cn.gtmap.realestate.common.core.ex.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
 * @version 1.0  2022/08/10
 * @description 政融支付平台 HttpUtil
 */
public class HttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * post请求（用于请求json格式的参数）
     *
     * @param urlPath
     * @param Json
     * @return
     */
    public static String doJsonPost(String urlPath, String Json) {
        String result = "";
        BufferedReader reader = null;
        OutputStream outwritestream = null;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置文件类型:
            conn.setRequestProperty("Content-Type", "application/json");
            // 设置接收类型否则返回415错误
            // conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
            conn.setRequestProperty("accept", "application/json");
            // 往服务器里面发送数据

            LOGGER.info("HttpUtil请求地址为：{}， 请求参数值为：{}", urlPath, Json);
            if (Json != null) {
                byte[] writebytes = Json.getBytes();
                // 设置文件长度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                outwritestream = conn.getOutputStream();
                outwritestream.write(Json.getBytes());
                outwritestream.flush();
            }

            if (conn.getResponseCode() == 200) {
                result = readStrFromStream(conn.getInputStream());
            } else {
                result = readStrFromStream(conn.getErrorStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outwritestream != null) {
                    outwritestream.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String readStrFromStream(InputStream in) {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new AppException("socket接受失败");
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOGGER.error("输入流关闭异常", e);
                }
            }
        }
        return builder.toString();
    }
}
