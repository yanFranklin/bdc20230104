package cn.gtmap.realestate.exchange.service.impl.inf.wwsq;

import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.TokenService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2020/5/28
 * @description
 */
@Service
public class YkqTokenServiceImpl  implements TokenService {
    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Value("${ykq.token.username:gx320101}")
    private String username;

    @Value("${ykq.token.password:6fc1e11f1a3ce72377817f286fd1c70e}")
    private String password;

    @Override
    public String getTokenInterfaceName() {
        return Constants.TOKEN.YKQ_TOKEN;
    }

    /**
     * 获取token接口
     */
    @Override
    public String getToken() {
        JSONObject map = new JSONObject();
        map.put("username", username);
        map.put("password", password);
        Object token = exchangeBeanRequestService.request("ykq_gettoken", map);
        return token == null ? "" : token.toString();
    }

    public JSONObject getYkqTokenJson() {
        String token = getToken();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        return jsonObject;
    }
}
