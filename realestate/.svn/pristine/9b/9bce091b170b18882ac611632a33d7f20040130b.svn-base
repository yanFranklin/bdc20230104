package cn.gtmap.realestate.accept.config;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/22
 * @description
 */
@Component
@ConfigurationProperties(prefix = "fdjywlc")
public class FdjywConfig {

    /**
     * 工作流定义ID
     */
    private Map<String, String> gzldyid =  new HashMap<>();

    public Map<String, String> getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(Map<String, String> gzldyid) {
        this.gzldyid = gzldyid;
    }

    public List<String> getFdjywlcDyidList(String ywlx){
        List<String> gzldyidList =new ArrayList<>();
        if(MapUtils.isNotEmpty(gzldyid)){
            for(Map.Entry<String, String> entry : gzldyid.entrySet()){
                if(StringUtils.isBlank(ywlx) ||(StringUtils.isNotBlank(ywlx) &&null != entry && ywlx.equals(entry.getKey()))){
                    String value = entry.getValue();
                    if(StringUtils.isNotBlank(value)){
                        Collections.addAll(gzldyidList,value.split(CommonConstantUtils.ZF_YW_DH));
                        if(StringUtils.isNotBlank(ywlx)){
                            return gzldyidList;
                        }
                        Collections.addAll(gzldyidList,value.split(CommonConstantUtils.ZF_YW_DH));
                    }
                }
            }
        }
        return gzldyidList;

    }
}
