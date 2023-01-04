package cn.gtmap.realestate.exchange.config;

import cn.gtmap.realestate.exchange.service.inf.TokenService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 初始化每个获取token的实现类
 */
@Component
public class TokenServiceChoose implements ApplicationContextAware {

    private Map<String, TokenService> chooseMap = new ConcurrentHashMap<>();

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public TokenService getTokenService(String tokenInterfaceName) {
        return chooseMap.get(tokenInterfaceName);
    }

    @PostConstruct
    public void register() {
        Map<String, TokenService> tokenServiceMap = applicationContext.getBeansOfType(TokenService.class);
        for (TokenService temp : tokenServiceMap.values()) {
            chooseMap.put(temp.getTokenInterfaceName(), temp);
        }
    }
}
