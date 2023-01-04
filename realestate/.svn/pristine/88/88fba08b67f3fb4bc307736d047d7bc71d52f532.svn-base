package cn.gtmap.realestate.exchange.service.impl.inf.hefei;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-17
 * @description EMS 接口相关服务
 */
@Service
public class EmsServiceImpl {

    @Value("${ems.appsecret:}")
    private String appsecret;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestMap
     * @param orderNormalJSON
     * @return java.lang.String
     * @description 获取 签名信息
     */
    public String getSign(Map requestMap,String orderNormalJSON){
        if(StringUtils.isNotBlank(orderNormalJSON)){
            requestMap.put("orderNormal",orderNormalJSON);
        }
        String content = getSortParams(requestMap) + appsecret;//排序
        String sign = sign(content,"UTF-8");//加密
        return sign;
    }


    public static String sign(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            charset = "UTF-8";
        }
        String sign = "";
        try {
            content = new String(content.getBytes(), charset);
            MessageDigest md5 = MessageDigest.getInstance("SHA-256");//MD5

            sign = encode(md5.digest(content.getBytes(charset)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sign;
    }
    public static String toKey(Map<String, String> params){
        String content = "";
        Set<String> keySet = params.keySet();
        List<String> keyList = new ArrayList<String>();
//这个接口做sign签名时，值为空的参数也传
        for (String key : keySet) {
            String value = params.get(key);
            // 将值为空的参数排除
			/*if (!StringUtil.isNull(value)) {
				keyList.add(key);
			}*/
            keyList.add(key);
        }

        //将参数和参数值按照排序顺序拼装成字符串
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            if(i == keyList.size()-1){
                content +=key + "=" + params.get(key);
                return content;
            }
            content += key + "=" + params.get(key) + "&";

        }
        return content;
    }
    public static String getSortParams(Map<String, String> params) {
        params.remove("sign");
        String contnt = "";
        Set<String> keySet = params.keySet();
        List<String> keyList = new ArrayList<String>();
        //这个接口做sign签名时，值为空的参数也传
        for (String key : keySet) {
            String value = params.get(key);
            // 将值为空的参数排除
			/*if (!StringUtil.isNull(value)) {
				keyList.add(key);
			}*/
            keyList.add(key);
        }
        Collections.sort(keyList, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                int length = Math.min(o1.length(), o2.length());
                for (int i = 0; i < length; i++) {
                    char c1 = o1.charAt(i);
                    char c2 = o2.charAt(i);
                    int r = c1 - c2;
                    if (r != 0) {
                        // char值小的排前边
                        return r;
                    }
                }
                // 2个字符串关系是str1.startsWith(str2)==true
                // 取str2排前边
                return o1.length() - o2.length();
            }
        });
        //将参数和参数值按照排序顺序拼装成字符串
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            contnt += key + params.get(key);
        }
        return contnt;
    }

    private static char[] codec_table = { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', '+', '/' };

    public static String encode(byte[] a) {
        int totalBits = a.length * 8;
        int nn = totalBits % 6;
        int curPos = 0;// process bits
        StringBuffer toReturn = new StringBuffer();
        while (curPos < totalBits) {
            int bytePos = curPos / 8;
            switch (curPos % 8) {
                case 0:
                    toReturn.append(codec_table[(a[bytePos] & 0xfc) >> 2]);
                    break;
                case 2:

                    toReturn.append(codec_table[(a[bytePos] & 0x3f)]);
                    break;
                case 4:
                    if (bytePos == a.length - 1) {
                        toReturn.append(codec_table[((a[bytePos] & 0x0f) << 2) & 0x3f]);
                    } else {
                        int pos = (((a[bytePos] & 0x0f) << 2) | ((a[bytePos + 1] & 0xc0) >> 6)) & 0x3f;
                        toReturn.append(codec_table[pos]);
                    }
                    break;
                case 6:
                    if (bytePos == a.length - 1) {
                        toReturn.append(codec_table[((a[bytePos] & 0x03) << 4) & 0x3f]);
                    } else {
                        int pos = (((a[bytePos] & 0x03) << 4) | ((a[bytePos + 1] & 0xf0) >> 4)) & 0x3f;
                        toReturn.append(codec_table[pos]);
                    }
                    break;
                default:
                    // never hanppen
                    break;
            }
            curPos += 6;
        }
        if (nn == 2) {
            toReturn.append("==");
        } else if (nn == 4) {
            toReturn.append("=");
        }
        return toReturn.toString();
    }
}
