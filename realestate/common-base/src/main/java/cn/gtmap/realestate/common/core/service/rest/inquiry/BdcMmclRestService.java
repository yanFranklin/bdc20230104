package cn.gtmap.realestate.common.core.service.rest.inquiry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/12/10
 * @description 不动产密码处理
 */
public interface BdcMmclRestService {

    /**
     * 通过密码 生成加密后信息
     *
     * @param password
     * @return
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/mmcl/encrypt/{password}")
    String encrypt(@PathVariable("password") String password);

    /**
     * 通过密文 公钥 解密密码
     *
     * @param mw
     * @param publickey
     * @return
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/mmcl/decrypt")
    String decrypt(@RequestParam("mw") String mw, @RequestParam("publickey") String publickey);
}
