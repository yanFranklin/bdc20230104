package cn.gtmap.realestate.config.config;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/02/25
 * @description
 *      配置项信息处理类
 *     1、对应配置文件application-config.yml中config下配置项
 */
@Component
@ConfigurationProperties(prefix = "config")
public class PropsConfig {

    /**
     * 是否从数据库加载配置项 false:否 true:是
     */
    private Boolean loadingConfigFlag;

    /**
     * 配置文件中yzhlqfs项对应值集合
     */
    private Map<String, String> yzhlqfs =  new HashMap<>();
    /**
     * 配置文件中fphlqfs项对应值集合
     */
    private Map<String, String> fphlqfs =  new HashMap<>();

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  qxdm 区县代码
     * @return {String} 领取方式
     * @description 获取指定区县代码对应的印制号领取方式
     */
    public String getZsyzhLqfs(String qxdm){
        if(StringUtils.isBlank(qxdm) || MapUtils.isEmpty(yzhlqfs)){
            return null;
        }

        for(Map.Entry<String, String> entry : yzhlqfs.entrySet()){
            if(null != entry && qxdm.equals(entry.getKey())){
                return entry.getValue();
            }
        }
        return null;
    }

    public Map<String, String> getYzhlqfs() {
        return yzhlqfs;
    }

    public void setYzhlqfs(Map<String, String> yzhlqfs) {
        this.yzhlqfs = yzhlqfs;
    }

    public Map<String, String> getFphlqfs() {
        return fphlqfs;
    }

    public void setFphlqfs(Map<String, String> fphlqfs) {
        this.fphlqfs = fphlqfs;
    }

    /**
     * @param qxdm 区县代码
     * @return {String} 领取方式
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description  获取指定区县代码对应的印制号领取方式
     */
    public String getFphlqfs(String qxdm){
        if(StringUtils.isBlank(qxdm) || MapUtils.isEmpty(fphlqfs)){
            return null;
        }

        for(Map.Entry<String, String> entry : fphlqfs.entrySet()){
            if(null != entry && qxdm.equals(entry.getKey())){
                return entry.getValue();
            }
        }
        return null;
    }

    public Boolean getLoadingConfigFlag() {
        return loadingConfigFlag;
    }

    public void setLoadingConfigFlag(Boolean loadingConfigFlag) {
        this.loadingConfigFlag = loadingConfigFlag;
    }
}
