package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcMmclRestService;
import com.alibaba.druid.filter.config.ConfigTools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/12/10
 * @description 不动产密码处理
 */
@RestController
public class BdcMmclRestController implements BdcMmclRestService {
    @Override
    public String encrypt(@PathVariable("password")String password) {
        StringBuilder result = new StringBuilder();
        try {
            // 获取密钥
            String[] arr = ConfigTools.genKeyPair(512);
            result.append("公钥：").append(arr[1]).append("\n");
            // 生成密文
            String encryptMm = ConfigTools.encrypt(arr[0], password);
            result.append("密码：").append(encryptMm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result.toString();
    }

    @Override
    public String decrypt(String mw, String publickey) {
        String result = "";
        if (StringUtils.isAnyBlank(mw, publickey)) {
            throw new AppException("必须输入密文和公钥");
        }
        try {
            // 解密
            result = ConfigTools.decrypt(publickey, mw);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException("解密失败");
        }
        return result;
    }
}
