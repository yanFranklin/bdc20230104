package cn.gtmap.realestate.certificate.core.model.encrypt;


import cn.gtmap.realestate.certificate.util.Base64Util;
import cn.gtmap.realestate.certificate.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/6/11
 */
public class Signature implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(Signature.class);
    private static final long serialVersionUID = 146144333607153716L;
    BigInteger r;
    BigInteger s;

    public Signature(BigInteger r, BigInteger s) {
        this.r = r;
        this.s = s;
    }

    @Override
    public String toString() {
        return r.toString(16) + "," + s.toString(16);
    }

    public String getEncode(){
        return Base64Util.encodeByteToBase64Str(StringUtil.strToByteUtf8(r + "," + s));
    }

    public static Signature getDecode(byte[] signData){
        Signature signature = null;
        String sign = StringUtil.byteToStrUtf8(signData);
        if (StringUtils.isNotBlank(sign)) {
            String[] signArr = sign.split(",");
            if (null != signArr && signArr.length == 2) {
                signature = new Signature(new BigInteger(signArr[0]),new BigInteger(signArr[1]));
            }
        }
        return signature;
    }
}
