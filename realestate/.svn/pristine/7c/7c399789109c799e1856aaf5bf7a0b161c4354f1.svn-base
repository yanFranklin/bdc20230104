package cn.gtmap.realestate.exchange.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryCacheUtil {

    private static Map<String,String> cacheMap = new ConcurrentHashMap<String, String>();

    public static void addStringValue(String key, String obj){
        cacheMap.put(key,obj);
    }

    public static String getStringValue(String key){
        return cacheMap.get(key);
    }

    public static  void deleteKey(String key){
        if(cacheMap.containsKey(key)){
            cacheMap.remove(key);
        }
    }

    public static boolean isExistKey(String key){
        return cacheMap.containsKey(key);
    }
    public static Map<String,String> getMap(){
        return cacheMap;
    }
}
