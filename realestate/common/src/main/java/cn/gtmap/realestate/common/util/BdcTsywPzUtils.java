package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.ex.AppException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/4/29
 * @description 特殊业务配置
 */
@Service
public class BdcTsywPzUtils {
    @Autowired
    private RedisUtils redisUtils;

    /**
     * @param pzmc 配置名称
     * @return 配置值字符串
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据配置名称获取配置值
     */
    public String getStringValueOfTsywPzz(String pzmc,String pzzxt){
        if(StringUtils.isBlank(pzmc)){
            throw new AppException("配置名称为空");
        }
        String pzz ="null";
        if(StringUtils.isNotBlank(pzzxt)) {
            pzz = redisUtils.getHashValue(CommonConstantUtils.REDIS_TSYW_PZ + "_" + pzzxt, pzmc);
        }
        if ("null".equals(pzz)) {
            pzz = redisUtils.getHashValue(CommonConstantUtils.REDIS_TSYW_PZ, pzmc);
        }
        if("null".equals(pzz)){
            pzz ="";
        }
        return pzz;

    }

    /**
     * @param pzmc 配置名称
     * @return 配置值字符串
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据配置名称获取配置值列表List
     */
    public List<String> getListValueOfTsywPzz(String pzmc,String pzzxt){
        List<String> listValue =new ArrayList<>();
        String pzz=getStringValueOfTsywPzz(pzmc,pzzxt);
        if(StringUtils.isNotBlank(pzz)){
            listValue =Arrays.asList(pzz.split(CommonConstantUtils.ZF_YW_DH));
        }
        return listValue;
    }

    /**
     * @param pzmc 配置名称
     * @return 配置值字符串
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据配置名称获取配置值布尔型
     */
    public boolean getBooleanValueOfTsywPzz(String pzmc,String pzzxt){
        String pzz=getStringValueOfTsywPzz(pzmc,pzzxt);
        return BooleanUtils.toBoolean(pzz);
    }

    /**
     * @param pzmc 配置名称
     * @return 配置值字符串
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据配置名称获取配置值数组字符串
     */
    public Map<String, String> getHashValueOfTsywPzz(String pzmc,String pzzxt){
        //数组字符串,redis缓存数据格式 a.b.c  1 配置所需为 a.b  c:1
        Map<String, String> mapValue =new HashMap<>();
        if(StringUtils.isNotBlank(pzzxt)) {
            Map<String, String> map = redisUtils.getHash(CommonConstantUtils.REDIS_TSYW_PZ + "_" + pzzxt);
            map.forEach((k, v) -> {
                if (k.contains(pzmc) && k.length() >= (pzmc.length() + 1)) {
                    mapValue.put(k.substring(pzmc.length() + 1), v);
                }

            });
        }
        if(MapUtils.isEmpty(mapValue)) {
            Map<String, String> map = redisUtils.getHash(CommonConstantUtils.REDIS_TSYW_PZ);
            map.forEach((k, v) -> {
                if (k.contains(pzmc) && k.length() >= (pzmc.length() + 1)) {
                    mapValue.put(k.substring(pzmc.length() + 1), v);
                }

            });
        }
        return mapValue;

    }
}
