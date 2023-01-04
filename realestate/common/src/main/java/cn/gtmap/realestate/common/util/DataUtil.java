package cn.gtmap.realestate.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Blob;
import java.sql.SQLException;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/6/4
 */
public class DataUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataUtil.class);

    private DataUtil() {
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }

        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * @author
     * @param blob
     * @return java.lang.String
     * @description BLOB强转Str
     */
    public static String blobToStr(Blob blob){
        if(blob != null){
            try{
                byte[] bdata = blob.getBytes(1, (int) blob.length());
                return new String(bdata);
            } catch(SQLException e) {
                LOGGER.error("BLOB强转字符串异常",e);
            }
        }
        return "";
    }
}
