package cn.gtmap.realestate.exchange.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/8/26.
 * @description
 */
@Component
public class TokenUtil {

    /**
     * 存储token的对象
     */
    private static Map<String,String> tokenMap=new HashMap<>();


    /**
     * 获取token
     * @param id
     * @return
     */
    public static String getToken(String id){
        return tokenMap.get(id);
    }

    /**
     * 存储token
     * @param id
     * @param token
     * @return
     */
    public static void addToken(String id,String token){
        tokenMap.put(id,token);
    }

    /**
     * 判定是否有token
     * @param id
     * @param token
     * @return
     */
    public static boolean checkToken(String id){
        if(tokenMap.containsKey(id) && StringUtils.isNotBlank(tokenMap.get(id))){
           return true;
        }
        return false;
    }

    /**
     * 清除token
     * @param id
     * @return
     */
    public static void removeToken(String id){
        if(checkToken(id)){
            tokenMap.remove(id);
        }
    }
}
