package cn.gtmap.realestate.exchange.service.impl.inf.huaian;

import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
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
 * 淮安公安 Token 获取
 */
@Service
public class GaTokenServiceImpl implements TokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GaTokenServiceImpl.class);

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Override
    public String getTokenInterfaceName() {
        return Constants.TOKEN.GA_TOKEN;
    }

    @Value("${baselibrary.authorizationCode:ea41f150a7c443d695806f3b1fac9a4d}")
    private String authorizationCode;

    /**
     * 获取token接口
     */
    @Override
    public String getToken() {
        Map<String, Object> dataMap = new HashMap(1);
        dataMap.put("authorizationCode", authorizationCode);
        Object token = exchangeBeanRequestService.request("ga_token", dataMap);
        LOGGER.info("获取 gaToken 返回值信息：{}", token);
        return token == null ? "" : token.toString();
    }

    @Override
    public String getToken(String beanId) {
        Map<String, Object> dataMap = new HashMap(1);
        String authorizationCode = EnvironmentConfig.getEnvironment().getProperty("baselibrary.authorizationCode" + "." + beanId);
        dataMap.put("authorizationCode", authorizationCode);
        Object token = exchangeBeanRequestService.request("ga_token", dataMap);
        LOGGER.info("获取 gaToken 返回值信息：{}", token);
        return token == null ? "" : token.toString();
    }
}
