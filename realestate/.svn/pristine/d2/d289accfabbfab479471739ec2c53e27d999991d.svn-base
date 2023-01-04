package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.common.core.ex.AppException;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version V1.0, 2022/08/15
 * @description 淮安水过户加解密
 */
public final class Codecs {

    private static HashFunction HF = Hashing.murmur3_128("hp".hashCode());
    private static BaseEncoding BE = BaseEncoding.base64Url().omitPadding();

    public static byte[] hash(byte[] bytes) {
        return HF.hashBytes(bytes).asBytes();
    }

    public static String hash(String str) {
        return encode(hash(bytes(str)));
    }

    public static String hash(String str, int len) {
        return StringUtils.left(hash(str), len);
    }

    public static String md5Hex(byte[] bytes) {
        return Hashing.md5().hashBytes(bytes).toString();
    }

    public static String md5Hex(String str) {
        return md5Hex(bytes(str));
    }

    public static String sha1Hex(byte[] bytes) {
        return Hashing.sha1().hashBytes(bytes).toString();
    }

    public static String sha1Hex(String str) {
        return sha1Hex(bytes(str));
    }

    public static String encode(byte[] bytes) {
        return BE.encode(bytes);
    }

    public static String encode(long l) {
        ByteBuffer buf = ByteBuffer.allocate(8);
        buf.putLong(l);
        return encode(buf.array());
    }

    public static String encode(int i) {
        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.putInt(i);
        return encode(buf.array());
    }

    public static byte[] decode(String str) {
        return BE.decode(str);
    }

    public static byte[] xor(byte[] bytes, byte[] keyBytes) {
        for (int i = 0, len = bytes.length, keyLen = keyBytes.length; i < len; i++) {
            bytes[i] = (byte) (bytes[i] ^ keyBytes[i % keyLen]);
        }
        return bytes;
    }

    public static String xorEncode(String str, String key) {
        return encode(xor(bytes(str), bytes(key)));
    }

    public static String xorDecode(String str, String key) {
        return string(xor(decode(str), bytes(key)));
    }


    public static byte[] bytes(String s) {
        if (s == null) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        try {
            return s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new AppException(e.getMessage());
        }
    }

    public static String string(byte[] bytes) {
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new AppException(e.getMessage());
        }
    }
}
