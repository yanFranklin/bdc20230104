package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcMmclFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/12/10
 * @description 不动产密码处理
 */
@RestController
@RequestMapping(value = "/rest/v1.0/mmcl")
public class BdcMmclController extends BaseController {
    @Autowired
    private BdcMmclFeignService bdcMmclFeignService;

    /**
     * 通过密码 生成加密后信息
     *
     * @param password
     * @return
     */
    @GetMapping("/encrypt/{password}")
    public String encrypt(@PathVariable("password") String password) {
        return bdcMmclFeignService.encrypt(password);
    }

    /**
     * 通过密文 公钥 解密密码
     *
     * @param mw
     * @param publickey
     * @return
     */
    @GetMapping("/decrypt")
    public String decrypt(String mw, String publickey) {
        return bdcMmclFeignService.decrypt(mw, publickey);
    }
}
