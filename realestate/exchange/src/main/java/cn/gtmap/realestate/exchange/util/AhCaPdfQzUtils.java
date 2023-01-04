package cn.gtmap.realestate.exchange.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

/**
 * @version 1.0, 2020-7-7.
 * @auto <a href="mailto:huangyongkai@gtmap.cn">huangyongkai</a>
 * @description 安徽 CA PDF 签章接口
 */
public class AhCaPdfQzUtils {
    public static Logger logger = LoggerFactory.getLogger(AhCaPdfQzUtils.class);

    /**
     * 通过服务端来对PDF文档加盖固定的章
     * 需将PDF文件传到服务端
     * pdf添加红章
     *
     * @param secretKey   密钥
     * @param uniqueId    唯一标识
     * @param fwdUrl      服务器请求url
     * @param id          log日志打印
     * @param bytes       待盖章的 PDF 文件数据
     * @param pageNum     在第几页进行签章
     * @param leftBottomX 签章图片距离页面左下角的 X 坐标（横向）
     * @param leftBottomY 签章图片距离页面左下角的 Y 坐标（纵向）
     * @param imgWidth    签章图片宽度
     * @param imgHeight   签章图片高度
     * @param reason      签名原因
     * @param location    签名地点，不设置默认为安徽 CA
     * @return 签章后的PDF文件（base64编码）(该值仅当返回码为200时返回)
     */
    public static byte[] pdfAddHz(String secretKey, String uniqueId, String fwdUrl, String id, byte[] bytes, String pageNum, String leftBottomX, String leftBottomY, String imgWidth, String imgHeight, String reason, String location) {
        // dataType传入的 PDF 文件数据类型，不传默认为 1 传 1 表示 16 进制数据，传 4 表示 Base64 格式数据
        String dataType = "4";
        String caCertifyUrl = fwdUrl;
        if (StringUtils.isNoneBlank(caCertifyUrl, uniqueId, secretKey)) {
            // byte数组转base64
            String strData = Base64.getEncoder().encodeToString(bytes);
            if (StringUtils.isBlank(pageNum)) {
                pageNum = "1";
            }
            if (StringUtils.isBlank(leftBottomX)) {
                leftBottomX = "420";
            }
            if (StringUtils.isBlank(leftBottomY)) {
                leftBottomY = "230";
            }
            if (StringUtils.isBlank(imgWidth)) {
                imgWidth = "88";
            }
            if (StringUtils.isBlank(imgHeight)) {
                imgHeight = "88";
            }
            if (StringUtils.isBlank(reason)) {
                reason = "不动产登记信息查询结果";
            }
            if (StringUtils.isBlank(location)) {
                location = "互联网+不动产平台";
            }
            //打印签章入参
            String postDataDy = "uniqueId=" + uniqueId + "&secretKey=" + secretKey
                    + "&strData=" + strData + "&dataType=" + dataType;
            postDataDy += "&pageNum=" + pageNum;
            postDataDy += "&leftBottomX=" + leftBottomX;
            postDataDy += "&leftBottomY=" + leftBottomY;
            postDataDy += "&imgWidth=" + imgWidth;
            postDataDy += "&imgHeight=" + imgHeight;
            postDataDy += "&reason=" + reason;
            postDataDy += "&location=" + location;
            // strData文件数据过多，打印日志时不展示
            String postDataDyLog = "uniqueId=" + uniqueId + "&secretKey=" + secretKey
                    + "&strData=" + "" + "&dataType=" + dataType;
            postDataDyLog += "&pageNum=" + pageNum;
            postDataDyLog += "&leftBottomX=" + leftBottomX;
            postDataDyLog += "&leftBottomY=" + leftBottomY;
            postDataDyLog += "&imgWidth=" + imgWidth;
            postDataDyLog += "&imgHeight=" + imgHeight;
            postDataDyLog += "&reason=" + reason;
            postDataDyLog += "&location=" + location;
            logger.info("通过服务端来对PDF文档加盖固定的章 :{}", id);
            logger.info("发送 POST 请求出url:{} param：{}", caCertifyUrl, postDataDyLog);
            String result = sendPost(caCertifyUrl, postDataDy);
            if (StringUtils.isNotBlank(result)) {
                JSONObject jo = JSONObject.parseObject(result);
                if (jo != null && jo.get("resultCode") != null && "200".equals(jo.get("resultCode").toString())) {
                    //成功,返回base64格式签章后PDF数据
                    byte[] decoded = Base64.getDecoder().decode(jo.get("pdfData").toString());
                    logger.info("签章成功:{}", id);
                    return decoded;
                } else {
                    logger.info("签章失败:id :{} result：{}", id, result);
                }
            } else {
                logger.info("签章失败:id :{}", id);
            }
        } else {
            logger.info("ca.fwdpdfqz.url或者ca.unique.id 或者{}配置为空", secretKey);
        }
        return bytes;
    }

    /**
     * 发送post请求
     *
     * @param url   请求地址
     * @param param 请求参数
     * @return
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("发送 POST 请求出现异常！url:{} ERROR：{}", url, e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                logger.error("发送 POST 请求出现异常！url:{} ERROR：{}", url, ex);
            }
        }
        return result;
    }
}
