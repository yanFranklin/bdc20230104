//package cn.gtmap.realestate.certificate.util;
//
//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
///**
// * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
// * @Version 1.0
// * @description 1.0，2019/12/18
// */
//public class Base64Util {
//    /**
//     * @param bytes
//     * @return
//     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
//     * @description 数组 加密 为base64
//     */
//    public static String encodeByteToBase64Str(byte[] bytes) {
//
//        String base64Str = "";
//        if (null != bytes) {
//            base64Str = Base64.encodeBase64String(bytes);
//        }
//        return base64Str;
//    }
//
//
//    /**
//     *
//     * @param base64Str
//     * @return
//     * @description base64字符串解密为数组
//     */
//    public static byte[] decodeBase64StrToByte(String base64Str){
//        byte[] bytes = null;
//        if (StringUtils.isNotBlank(base64Str)) {
//            bytes = Base64.decodeBase64(base64Str);
//        }
//        return bytes;
//    }
//    private Base64Util() {
//    }
//}
