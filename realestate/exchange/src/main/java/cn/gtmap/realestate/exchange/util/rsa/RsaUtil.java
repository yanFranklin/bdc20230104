package cn.gtmap.realestate.exchange.util.rsa;

import cn.gtmap.realestate.exchange.util.Base64Util;
import com.inspur.uaccess.common.utils.RSASignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PublicKey;

/**
 * 加签工具
 * Created by lst on 2015/11/26.
 */
public class RsaUtil {
    public static final String RSA_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANP1Ow58aqG/7dPF5T+M3CyQ6Vc8XfkZ9+bigaNTILZqOmUSJFn7nvHnBhe2qHUHe6WdJBjU8s78/DyLSODW7BCg7SLMaTfUKsiWgWkoBy97kl8bHRcErVx9+wakW+PMv+C9Fkce+oY4fUE7JJPwRw6sOe5PdjcW1hsZ14OUfoAZAgMBAAECgYEAt0IFEI5Dx5vg7cPhZOPODX4xMWqROWnZa7eVLHgYBX+tA2v/IAmssCv1mZUk6yJQJq3J4upjENGs6E/o7/UC3f3juJCIAj0xyJ49x8FoKfHob+VZkkZEBIt7mPyvo1Pl9vW6dE50SQGrEkIIVOZdlKeWKzMKhsyFZqKGNM5cAgECQQD6EeHoHnakfQicM7LHwho2QEtJO+CjKl3EhmDbE3qKGn/W/xVpaDB/EqTfSR45r48009s1bmFjxIslIjNT10lBAkEA2Pv6uPZenhC/cvQNzUZIhHdUJti086N8AAedMkW9sr1Bp3O3hspKvvNYtzwEfGvQWa42qVPLrIjmAmlxNn9o2QJAWFJ2kpAn4ULUBq9vxoP01BJzRMNkPNzaz22Sye2gSyS+4EWp31fQQSFpn/9oMIGkN8lX1BBPT3h8mLnynPsdwQJBALkanNTVN/pYBzqlgHCxmIOI2L0a+aMuwEW2OR/95spoMW4MhW/zerhTGEeYZ6tMvj0DJZZl6caSMsWz9eSr5GECQDq4SKdjEu21caizzRHqyTIlo3675w+iPPrWxx9sKbqfN3OhEPgA7DKdH7s19Smw3kszdu0hVVAR0RpznGAp0Y4=";
    private static Logger logger = LoggerFactory.getLogger(RsaUtil.class);
    private  static String PUCLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDl4sr6M8btqSBxgS+QKSgHWbEqN/op1u1P1Epyg9p0fVdV/Tv0zW0tL7LS3L9601whP6PDSadD6c3rCVFDJIOmyj5aCgHM+bEMQQH1ny9U6lbAezLK78KJTwcOfr8kl4ZhLgOU57v2hHM76Hmz1dZFMqdOWxk7cVp6MnPXVz6q+QIDAQAB";


    /**
     * 传输xml字符串数据，添加head的电子签名
     *
     * @param xml
     * @return
     */
    public static String setRsaSign(String xml) {
        //获取加签后报文
        String msg = null;
        try {
            msg = RSASignature.getNewMsgWithSignature(xml, RSA_KEY);
        } catch (Exception e) {
            logger.error("error:", e);
        }
        return msg;
    }

    /**
     * 加密方法（接口XML配置中有使用）
     * @param data
     * @return
     */
    public static String enCode(String data){
        String source = data;
        try {
            // 从字符串中得到公钥
            PublicKey publicKey = RsaUtilsHelper.loadPublicKey(PUCLIC_KEY);
            // 从文件中得到公钥
//                InputStream inPublic = new FileInputStream(RSA_PUBLIC_KEY_FILE);
//                PublicKey publicKey = RsaUtilsHelper.loadPublicKey(inPublic);
            // 加密
            byte[] encryptByte = RsaUtilsHelper.encryptData(source.getBytes("UTF-8"), publicKey);
            // 为了方便观察吧加密后的数据用base64加密转一下，要不然看起来是乱码,所以解密是也是要用Base64先转换
            return Base64Util.encode(encryptByte);
        } catch (Exception e) {
            e.printStackTrace();
            return "加密错误";
        }
    }

}