package cn.gtmap.realestate.exchange.service.impl.inf.huaian;

import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.encrypt.MD5Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022/09/08
 * @description 水电气加密服务
 */
@Service(value = "sdqEncryptServiceImpl")
public class SdqEncryptServiceImpl {

    /**
     * 淮安淮阴区水过户秘钥
     */
    @Value("${hy.shui.gh.secretkey:hyzls}")
    private String sghSecretKey;

    /**
     * 淮安淮阴区水过户的key值
     * @param consno 户号
     * @return md5加密后的字符串
     */
    public String getKey(String consno){
        String param = sghSecretKey + DateUtils.getCurrDay() + consno;
        return MD5Util.stringToMD5Str(param);
    }

}
