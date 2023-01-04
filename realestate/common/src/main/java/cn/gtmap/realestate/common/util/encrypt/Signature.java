package cn.gtmap.realestate.common.util.encrypt;

import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
 * @Version 1.0
 * @description 1.0，2019/12/18
 */
public class Signature implements Serializable {
    private static final long serialVersionUID = 146144333607153716L;
    BigInteger r;
    BigInteger s;

    public Signature(BigInteger r, BigInteger s) {
        this.r = r;
        this.s = s;
    }

    public static Signature getDecode(byte[] signData) {
        Signature signature = null;
        String sign = StringToolUtils.byteToStrUtf8(signData);
        if (StringUtils.isNotBlank(sign)) {
            String[] signArr = sign.split(",");
            if (null != signArr && signArr.length == 2) {
                signature = new Signature(new BigInteger(signArr[0]), new BigInteger(signArr[1]));
            }
        }
        return signature;
    }

    @Override
    public String toString() {
        return r.toString(16) + "," + s.toString(16);
    }

    public String getEncode() {
        return Base64Utils.encodeByteToBase64Str(StringToolUtils.strToByteUtf8(r + "," + s));
    }
}
