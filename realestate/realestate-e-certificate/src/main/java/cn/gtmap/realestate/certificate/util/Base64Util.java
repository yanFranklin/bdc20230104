package cn.gtmap.realestate.certificate.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/5/10
 */
public class Base64Util {
    private static final Logger logger = LoggerFactory.getLogger(Base64Util.class);

    /**
     * @param pdfFilePath
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 文件 加密 base64
     */
    public static String encodeFileToBase64Str(String pdfFilePath) {
        String base64 = "";

        byte[] bytes = PublicUtil.fileToByte(pdfFilePath);
        if (null != bytes) {
            base64 = encodeByteToBase64Str(bytes);
        }

        return base64;
    }

    /**
     * @param str
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 字符串 加密 base64
     */
    public static String encodeStrToBase64Str(String str) {

        String base64Str = "";
        if (StringUtils.isNotBlank(str)) {
            base64Str = encodeByteToBase64Str(StringUtil.strToByteUtf8(str));
        }
        return base64Str;
    }

    /**
     * @param bytes
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 数组 加密 为base64
     */
    public static String encodeByteToBase64Str(byte[] bytes) {

        String base64Str = "";
        if (null != bytes) {
            base64Str = Base64.encodeBase64String(bytes);
        }
        return base64Str;
    }

    /**
     * @param base64Str
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 字符串base64 解密
     */
    public static String decodeBase64StrToStr(String base64Str) {
        String str = null;
        byte[] bytes = decodeBase64StrToByte(base64Str);
        if (null != bytes) {
            str = StringUtil.byteToStrUtf8(bytes);
        }
        return str;
    }

    /**
     * @param base64Str
     * @param filePath
     * @return
     * @description 文件base64 解密
     */
    public static boolean decodeBase64StrToFile(String base64Str, String filePath) {
        byte[] bytes = decodeBase64StrToByte(base64Str);
        if (null != bytes) {
            String path = PublicUtil.byteToFile(filePath, bytes);
            if (StringUtils.isNotBlank(path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param base64Str
     * @return
     * @description base64字符串解密为数组
     */
    public static byte[] decodeBase64StrToByte(String base64Str) {
        byte[] bytes = null;
        if (StringUtils.isNotBlank(base64Str)) {
            bytes = Base64.decodeBase64(base64Str);
        }
        return bytes;
    }

    private Base64Util() {
    }

    /**
     * 将base64位码转换为文件
     *
     * @param base64
     * @return
     */
    public static MultipartFile base64ToMultipart(String base64) {
        String[] baseStrs = base64.split(",");
        if (baseStrs.length > 1) {
            byte[] b = decodeBase64StrToByte(baseStrs[1]);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new BASE64DecodedMultipartFile(b, baseStrs[0]);
        }
        return null;
    }
}
