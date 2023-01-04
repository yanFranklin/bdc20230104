package cn.gtmap.realestate.exchange.service.impl.inf.changzhou;

import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.TokenService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * 金坛证照签章 Token 获取
 */
@Service
public class JtZzqzTokenServiceImpl implements TokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JtZzqzTokenServiceImpl.class);

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Value("${zzqz.token.jtyymc: jttoken}")
    private String yymc;

    @Override
    public String getTokenInterfaceName() {
        return Constants.TOKEN.JT_ZZQZ_TOKEN;
    }

    /**
     * 获取token接口
     */
    @Override
    public String getToken() {
        Map yymcMap = new HashMap(1);
        yymcMap.put("yymc", yymc);
        Map<String, Object> dataMap = new HashMap(1);
        dataMap.put("data", yymcMap);
//        LOGGER.info("=======wahaha====>:{}",JSON.toJSONString(dataMap));
        Object token = exchangeBeanRequestService.request("zzqz_token", dataMap);

        if (token == null) {
            LOGGER.info("金坛获取 tonken 失败入参：{},出参：{}", dataMap.toString(), token);
        }
        return token == null ? "" : token.toString();
    }
}
