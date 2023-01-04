package cn.gtmap.realestate.certificate.config;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
 * @version 1.0, 2018/11/19
 * @description 配置项信息处理类
 * <p>
 * 1、对应配置文件application-config.certificate.services下配置项
 * </p>
 */
@Component
@ConfigurationProperties(prefix = "certificate")
public class PropsConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsConfig.class);

    /**
     * 配置文件中templatePaths项对应值集合，application-config.yml中同名配置项会设值到该集合
     */
    private Map<String, String> templatePaths = new HashMap<>();

    /**
     * 电子证照的证照主体在登记中的zjzl和主体代码类型对照
     */
    private Map<String, String> eZzztdm = new HashMap<>();

    /*
     * 不动产登记档案URL配置
     * */
    private Map<String, String> url;


    /**
     * @param zjzl 证件种类
     * @return 证照对照信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据证件种类
     */
    public List<String> getZzztDmXx(Integer zjzl) {
        if (MapUtils.isEmpty(this.getEZzztdm()) || StringUtils.isBlank(String.valueOf(zjzl))) {
            LOGGER.info("获取指定的模板配置有误或zjzl为空！");
            return null;
        }
        String dmxx = MapUtils.getString(this.getEZzztdm(), String.valueOf(zjzl));
        if (StringUtils.isBlank(dmxx)) {
            LOGGER.error("证件种类{}未配置证照对照信息！", zjzl);
            return null;
        }
        String[] dzxxArr = StringUtils.split(dmxx, CommonConstantUtils.ZF_YW_DH);
        if (2 != dzxxArr.length) {
            LOGGER.error("证件种类{}未配置证照对照信息有误，请检查！", zjzl);
            return null;
        }
        return Arrays.asList(dzxxArr);
    }

    public Map<String, String> getTemplatePaths() {
        return templatePaths;
    }

    public void setTemplatePaths(Map<String, String> templatePaths) {
        this.templatePaths = templatePaths;
    }

    public Map<String, String> getEZzztdm() {
        return eZzztdm;
    }

    public void setEZzztdm(Map<String, String> eZzztdm) {
        this.eZzztdm = eZzztdm;
    }

    public Map<String, String> getUrl() {
        return url;
    }

    public void setUrl(Map<String, String> url) {
        this.url = url;
    }
}
