package cn.gtmap.realestate.register.ui.config;

import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
 * @version 1.0, 2020/01/02
 * @description 配置项信息处理类
 * <p>
 * 1、对应配置文件application-config.register.ui.services下配置项
 * </p>
 */
@Component
@ConfigurationProperties(prefix = "register.ui")
public class PropsConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsConfig.class);
    private static final String CLASS_NAME = PropsConfig.class.getName();


    /**
     * 完税验证配置信息
     */
    private Map<Integer, Map<String, String>> wsyz = new HashMap<>();


    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @param djlx 项目中的登记类型代码
     * @param djxl 项目中的登记小类代码
     * @return 配置的登记
     * @description 获取配置的登记原因信息
     */
    public String[] getWsyzDjyy(Integer djlx, String djxl) {
        if (MapUtils.isEmpty(this.getWsyz()) || null == djlx || StringUtils.isBlank(djxl)) {
            LOGGER.error("获取完税验证配置有误！请检查！{}{}", djlx,djxl);
            return null;
        }
        Map<String, String> djxlAndDjyyMap = MapUtils.getMap(this.getWsyz(), djlx);
        if (MapUtils.isEmpty(djxlAndDjyyMap)) {
            LOGGER.warn("登簿前票税真伪/完税验证。登记类型{},没有获取到配置信息！", djlx);
            return null;
        }
        // 根据登记小类获取登记原因
        String djyyStr = MapUtils.getString(djxlAndDjyyMap, djxl);
        return StringUtils.split(djyyStr, CommonConstantUtils.ZF_YW_DH);
    }


    public Map<Integer, Map<String, String>> getWsyz() {
        return wsyz;
    }

    public void setWsyz(Map<Integer, Map<String, String>> wsyz) {
        this.wsyz = wsyz;
    }


}
