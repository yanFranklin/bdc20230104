package cn.gtmap.realestate.common.config.Encrypt;


import cn.gtmap.sdk.mybatis.plugin.executor.AesCryptExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AESutil {
    @Value("${encrypt.cryptEnable:false}")
    private Boolean cryptEnable;

    /**
     * AES 加密方法
     *
     * @param param
     * @return
     */
    public String encrypt(String param) {
        if (!cryptEnable) {
            return param;
        }
        AesCryptExecutor aesCryptExecutor = new AesCryptExecutor();
        return aesCryptExecutor.encrypt(param);
    }


    /**
     * AES 解密方法
     *
     * @param param
     * @return
     */
    public String decrypt(String param) {
        if (!cryptEnable) {
            return param;
        }
        AesCryptExecutor aesCryptExecutor = new AesCryptExecutor();
        return aesCryptExecutor.decrypt(param);
    }
}
