package cn.gtmap.realestate.inquiry.ui.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import cn.gtmap.realestate.common.util.Base64Utils;

/**
 * 加签工具
 * Created by lst on 2015/11/26.
 */
public class RsaUtil {

    private  static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8HMr2CBpoZPm3t9tCVlrKtTmI4jNJc7/HhxjIEiDjC8czP4PV+44LjXvLYcSV0fwi6nE4LH2c5PBPEnPfqp0g8TZeX+bYGvd70cXee9d8wHgBqi4k0J0X33c0ZnW7JruftPyvJo9OelYSofBXQTcwI+3uIl/YvrgQRv6A5mW01QIDAQAB";
    private  static String PRIVATE_KEY ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALwcyvYIGmhk+be320JWWsq1OYjiM0lzv8eHGMgSIOMLxzM/g9X7jguNe8thxJXR/CLqcTgsfZzk8E8Sc9+qnSDxNl5f5tga93vRxd5713zAeAGqLiTQnRffdzRmdbsmu5+0/K8mj056VhKh8FdBNzAj7e4iX9i+uBBG/oDmZbTVAgMBAAECgYEAmgNU5NTDkj9B+Pnt6UU8doSjw3+3j+bV2K2yS3QUOvAUus/Ax7x6ktjWxzCXvDY9IfUil2RNv9vtKEAqYLCWjc+lf8PV/yH1b7NEgyeAPBXtAJRoOnmYL2bdPW92kP9KgxJruF6Dz/C5AmMOncsvq8ABD+9Darn4p8dwj2ZC4O0CQQDf/AHmZsQokEItfCy4mHS9UbxbfIhEUv1ApPh/+Sr7NkJkHWYCtBQo+8jKO6zurAZQgWBPD1XX2UE4R+VIiZazAkEA1wAqtMvGhccyRZr+6kpkpDIa8+9jOE+nGUzqTDvgCID6as8AzOONFVVK6m/UUqkhcJ8Qu1pF36BGojy5BX2KVwJBAJSFpbji0hXXupowqfLp3RcgmNbNWAp+QUJZYhJx5cdYbmO2fssyH+AhPT6knYJR/YnqkDM8hv6vKCkqu2YDHjMCQAOA8TE5EOclM+CGghj3VWSHnIDVKdzFD4gOBNNxNlltIKeU8AJmwunSFgJ0CBXAw9a+ANvMwM7AIeaK7sj0HskCQAvxfDCq7gaNx+pfu0FHG8Gix08A/A6foggBl1fVu+L9sr9ZuOQ3HbXnl28F9ewuB9xdjnLUDjp7W7U0pB+vKoQ=";
    /**
     * 加密方法
     * @param data
     * @return
     */
    public static String enCode(String data){

        String source = data;
        try {
            // 从字符串中得到公钥
            PublicKey publicKey = RsaUtilsHelper.loadPublicKey(PUBLIC_KEY);
            // 加密
            byte[] encryptByte = RsaUtilsHelper.encryptData(source.getBytes("UTF-8"), publicKey);
            // 为了方便观察吧加密后的数据用base64加密转一下，要不然看起来是乱码,所以解密是也是要用Base64先转换
            return Base64Utils.encodeByteToBase64Str(encryptByte);
        } catch (Exception e) {
            e.printStackTrace();
            return "加密错误";
        }
    }

    /**
     * 解密方法
     * @param data
     * @return
     */
    public static String deCode(String data){

        try {
            // 从字符串中得到私钥
            PrivateKey privateKey = RsaUtilsHelper.loadPrivateKey(PRIVATE_KEY);
            // 使用base64解密
            byte[] source = Base64Utils.decodeBase64StrToByte(data);
            // 解密
            byte[] encryptByte = RsaUtilsHelper.decryptData(source, privateKey);
            return (new String(encryptByte));
        } catch (Exception e) {
            e.printStackTrace();
            return "解密错误";
        }
    }

}
