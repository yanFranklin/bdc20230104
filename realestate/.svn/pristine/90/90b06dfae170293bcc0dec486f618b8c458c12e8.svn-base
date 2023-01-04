package cn.gtmap.realestate.certificate.util;

import cn.gtmap.realestate.certificate.core.model.encrypt.SM4;
import cn.gtmap.realestate.certificate.core.model.encrypt.SM4_Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/5/22
 */
public class SM4Util {
    private static final Logger logger = LoggerFactory.getLogger(SM4Util.class);
    public static String secretKey = "SM4_GTMAP_SECRET";
    public static String iv = "SM4_GTMAP_IV_CBC";
    public static boolean hexString = false;
    public static Pattern p = Pattern.compile("\\s*|\t|\r|\n");

    public static String encryptData_ECB(String plainText)
    {
        try
        {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            if (hexString)
            {
                keyBytes = DataUtil.hexStringToBytes(secretKey);
            }
            else
            {
                keyBytes = secretKey.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_ecb(ctx, StringUtil.strToByteGbk(plainText));
            String cipherText = Base64Util.encodeByteToBase64Str(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0)
            {
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        }
        catch (Exception e)
        {
            logger.error("encryptData_ECB", e);
        }
        return null;
    }

    public static String decryptData_ECB(String cipherText)
    {
        try
        {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes;
            if (hexString)
            {
                keyBytes = DataUtil.hexStringToBytes(secretKey);
            }
            else
            {
                keyBytes = secretKey.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_ecb(ctx, Base64Util.decodeBase64StrToByte(cipherText));
            return StringUtil.byteToStrGbk(decrypted);
        }
        catch (Exception e)
        {
            logger.error("decryptData_ECB", e);
        }
        return null;
    }

    public static String encryptData_CBC(String plainText)
    {
        try
        {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString)
            {
                keyBytes = DataUtil.hexStringToBytes(secretKey);
                ivBytes = DataUtil.hexStringToBytes(iv);
            }
            else
            {
                keyBytes = secretKey.getBytes();
                ivBytes = iv.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, StringUtil.strToByteGbk(plainText));
            String cipherText = Base64Util.encodeByteToBase64Str(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0)
            {
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        }
        catch (Exception e)
        {
            logger.error("encryptData_CBC", e);
        }
        return null;
    }

    public static String decryptData_CBC(String cipherText)
    {
        try
        {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString)
            {
                keyBytes = DataUtil.hexStringToBytes(secretKey);
                ivBytes = DataUtil.hexStringToBytes(iv);
            }
            else
            {
                keyBytes = secretKey.getBytes();
                ivBytes = iv.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, Base64Util.decodeBase64StrToByte(cipherText));
            return StringUtil.byteToStrGbk(decrypted);
        } catch (Exception e)
        {
            logger.error("decryptData_CBC", e);
        }

        return null;
    }
}
