package cn.gtmap.realestate.exchange.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
 * @description 登簿日志配置值初始化
 */

@Component
public class ConfigInit {

    /**
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 判断是否开启国家上报日志
     */
    @Value("${accessLog.nationalEnble:}")
    boolean nationalEnble;
    /**
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 判断是否开启省级上报日志
     */
    @Value("${accessLog.provinceEnble:}")
    boolean provinceEnble;

    @Value("${accessLog.cityEnable:false}")
    boolean cityEnable;
    /**
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 判断是登簿还是办结上报日志
     */
    @Value("${accessLog.conditionTime:}")
    String conditionTime;
    /**
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 上报国家地址
     */
    @Value("${accessLog.nationalUrl:}")
    String nationalUrl;
    /**
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 省级上报登簿日志地址(url)
     */
    @Value("${accessLog.provinceUrl:}")
    String provinceUrl;

    @Value("${accessLog.cityDbrzsbUrl:}")
    String cityUrl;
    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 根据各地区配置相应的名字 ex:standard ,anhui(目前只包含这几种，只能从以上中选择一个配置
     */
    @Value("${accessLog.provinceType:}")
    String provinceType;
    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 根据各地区配置相应的名字 ex:standard (目前只包含这几种，只能从以上中选择一个配置
     */
    @Value("${accessLog.nationalType:}")
    String nationalType;

    @Value("${accessLog.xmlPath:}")
    String xmlPath;

    private ConfigInit() {

    }


    public boolean isNationalEnble() {
        return nationalEnble;
    }

    public void setNationalEnble(boolean nationalEnble) {
        this.nationalEnble = nationalEnble;
    }

    public boolean isProvinceEnble() {
        return provinceEnble;
    }

    public void setProvinceEnble(boolean provinceEnble) {
        this.provinceEnble = provinceEnble;
    }

    public String getConditionTime() {
        return conditionTime;
    }

    public void setConditionTime(String conditionTime) {
        this.conditionTime = conditionTime;
    }

    public String getNationalUrl() {
        return nationalUrl;
    }

    public void setNationalUrl(String nationalUrl) {
        this.nationalUrl = nationalUrl;
    }

    public String getProvinceUrl() {
        return provinceUrl;
    }

    public void setProvinceUrl(String provinceUrl) {
        this.provinceUrl = provinceUrl;
    }


    public String getProvinceType() {
        return provinceType;
    }

    public void setProvinceType(String provinceType) {
        this.provinceType = provinceType;
    }

    public String getNationalType() {
        return nationalType;
    }

    public void setNationalType(String nationalType) {
        this.nationalType = nationalType;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public boolean getCityEnable() {
        return cityEnable;
    }

    public void setCityEnable(boolean cityEnable) {
        this.cityEnable = cityEnable;
    }

    public String getCityUrl() {
        return cityUrl;
    }

    public void setCityUrl(String cityUrl) {
        this.cityUrl = cityUrl;
    }

    @Override
    public String toString() {
        return "ConfigInit{" +
                "nationalEnble=" + nationalEnble +
                ", provinceEnble=" + provinceEnble +
                ", cityEnable=" + cityEnable +
                ", conditionTime='" + conditionTime + '\'' +
                ", nationalUrl='" + nationalUrl + '\'' +
                ", provinceUrl='" + provinceUrl + '\'' +
                ", cityUrl='" + cityUrl + '\'' +
                ", provinceType='" + provinceType + '\'' +
                ", nationalType='" + nationalType + '\'' +
                ", xmlPath='" + xmlPath + '\'' +
                '}';
    }
}
