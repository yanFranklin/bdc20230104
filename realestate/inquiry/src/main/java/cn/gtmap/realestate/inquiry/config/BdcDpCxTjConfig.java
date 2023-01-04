package cn.gtmap.realestate.inquiry.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/04/08/8:44
 * @Description: 不动产大屏查询统计
 */
@Component
@ConfigurationProperties(prefix = "dpcxtj")
public class BdcDpCxTjConfig {
    /**
     * 地区版本
     **/
    String version;
    /**
     * 大屏页面title
     **/
    String dpTitle;
    Map<String,String> qxdmmcdz;

    Map<String,List<String>> geoCoordMap;

    public Map<String, String> getQxdmmcdz() {
        return qxdmmcdz;
    }

    public void setQxdmmcdz(Map<String, String> qxdmmcdz) {
        this.qxdmmcdz = qxdmmcdz;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, List<String>> getGeoCoordMap() {
        return geoCoordMap;
    }

    public void setGeoCoordMap(Map<String, List<String>> geoCoordMap) {
        this.geoCoordMap = geoCoordMap;
    }

    public String getDpTitle() {
        return dpTitle;
    }

    public void setDpTitle(String dpTitle) {
        this.dpTitle = dpTitle;
    }

    @Override
    public String toString() {
        return "BdcDpCxTjConfig{" +
                "version='" + version + '\'' +
                ", dpTitle='" + dpTitle + '\'' +
                ", qxdmmcdz=" + qxdmmcdz +
                ", geoCoordMap=" + geoCoordMap +
                '}';
    }
}
