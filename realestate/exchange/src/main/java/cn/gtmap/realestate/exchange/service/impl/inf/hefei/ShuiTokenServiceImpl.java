package cn.gtmap.realestate.exchange.service.impl.inf.hefei;

import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.TokenService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author <a herf="mailto:zedq@gtmap.cn">zedq</a>
 * @version 1.0, 2020/10/14
 * @description
 */
@Service
public class ShuiTokenServiceImpl implements TokenService {

    private static Logger LOGGER = LoggerFactory.getLogger(ShuiTokenServiceImpl.class);

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Value("${shui.token.appId:d2c7a183144df1911debe73ce3f7d799}")
    private String appId;

    @Value("${shui.token.appSecret:d74709ec37a647362ba93ee2f85342d1}")
    private String appSecret;

    @Override
    public String getTokenInterfaceName() {
        return Constants.TOKEN.SHUI_TOKEN;
    }

    /**
     * 获取token接口
     */
    @Override
    public String getToken() {
        JSONObject map = new JSONObject();
        JSONObject requestData = new JSONObject();
        requestData.put("app_id",appId);
        requestData.put("app_secret",appSecret);
        map.put("requestData", requestData.toJSONString());
        Object token = exchangeBeanRequestService.request("shui_token", map);
        if (token == null){
            LOGGER.info("获取tonken失败入参：{},出参：{}",map.toJSONString(),token);
        }
        return token == null ? "" : token.toString();
    }
}
