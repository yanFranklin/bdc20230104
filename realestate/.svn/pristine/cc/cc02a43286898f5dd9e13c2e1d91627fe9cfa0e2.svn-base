package cn.gtmap.realestate.exchange.util;


import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2019-09-12
 * @description Md5加密工具
 */
public class Md5Util {
    /**
     * 16进制字符
     */
    static String[] chars = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

    /**
     * 将普通字符串用md5加密，并转化为16进制字符串
     * @param str
     * @return
     */
    public static String StringInMd5(String str) {

        // 消息签名（摘要）
        MessageDigest md5 = null;
        try {
            // 参数代表的是算法名称
            md5 = MessageDigest.getInstance("md5");
            byte[] result = md5.digest(str.getBytes());

            StringBuilder sb = new StringBuilder(32);
            // 将结果转为16进制字符  0~9 A~F
            for (int i = 0; i < result.length; i++) {
                // 一个字节对应两个字符
                byte x = result[i];
                // 取得高位
                int h = 0x0f & (x >>> 4);
                // 取得低位
                int l = 0x0f & x;
                sb.append(chars[h]).append(chars[l]);
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
      *
      * @param plainText 加密字符
      * @return 32位MD5加密文
      * @Date 2020/12/16
      * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
         */
    public static String getMd5To32(String plainText,String encoding){
        try {
            //创建信息摘要对象实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            //获取文本明文为字节
            try {
                if(StringUtils.isNotBlank(encoding)){
                    md.update((plainText).getBytes(encoding));
                }else {
                    md.update((plainText).getBytes("UTF-8"));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            //创建字节摘要数组
            byte[] b = md.digest();
            //创建 int 类型变量i
            int i;
            //创建StringBuffer容器
            StringBuffer buf = new StringBuffer("");
            for (int j = 0; j < b.length; j++) {
                i = b[j];
                if (i < 0) {
                    i += 256;   //md5加密最长32位字符.一个字符占8个字节.所以最长允许256个字节的字符串
                }
                if (i < 16)     //一个字符=8个字节 0-15不足字符俩字符则补0拼接
                {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));//int类型10进制转16进制
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String getStringFromException(Throwable e) {
        String result = "";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        e.printStackTrace(ps);
        try {
            result = bos.toString("UTF-8");
        } catch (IOException ioe) {
        }
        return result;
    }

    public static byte[] encryptMD5(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            String msg = getStringFromException(gse);
            throw new IOException(msg);
        }
        return bytes;
    }

    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }


    /**
     * MD5加密
     *
     * @param origin      字符
     * @param charsetname 编码
     * @return
     */
    public static String MD5Encode(String origin, String charsetname) throws Exception {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (null == charsetname || "".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        } catch (Exception e) {
            throw e;
        }
        return resultString;
    }


    private static String byteArrayToHexString(byte b[]) {
        StringBuilder resultSb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return chars[d1] + chars[d2];
    }

}
