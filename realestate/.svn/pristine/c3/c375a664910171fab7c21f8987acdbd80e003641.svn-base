package cn.gtmap.realestate.exchange.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">caolu</a>
 * @version 1.0, 2021/11/24
 * @description 南通获取税票配置
 */
@Component
@ConfigurationProperties(prefix = "sdq")
public class SdqConfig {
    protected static Logger LOGGER = LoggerFactory.getLogger(SdqConfig.class);
    /**
     * 供水
     */
    private Map<String,Map<String,UnitInfo>> gsdwxx;
    /**
     * 供气
     */
    private Map<String,Map<String,UnitInfo>> gqdwxx;
    /**
     * 供水推送信息，key:qxdm, value: TsxxInfo
     */
    private Map<String, TsxxInfo> gstsxx;


    public static class UnitInfo{
        /**
         * 过户访问地址
         */
        private String ghUrl;
        /**
         * 户号验证地址
         */
        private String checkUrl;
        /**
         * 推送参数方式
         * 1、权利人义务人不管是单独还是多个：权利人、义务人均推送，多个的话用逗号隔开；
         * 4、只推送权利人，只推送户主；
         * 5、海地
         */
        private Integer pushStyle;
        /**
         * 过户发送的文件夹名称
         */
        private String clmcs;
        /**
         *
         */
        private String mdmcode;
        /**
         *
         */
        private String receiveFrom;
        /**
         * 获取token的clientId
         */
        private String clientId;
        /**
         * 获取token的secret
         */
        private String secret;
        /**
         * 获取临时的token
         */
        private String tempTokenUrl;
        /**
         * 获取正式的token
         */
        private String tokenUrl;


        public String getTempTokenUrl() {
            return tempTokenUrl;
        }

        public void setTempTokenUrl(String tempTokenUrl) {
            this.tempTokenUrl = tempTokenUrl;
        }

        public String getTokenUrl() {
            return tokenUrl;
        }

        public void setTokenUrl(String tokenUrl) {
            this.tokenUrl = tokenUrl;
        }

        public String getGhUrl() {
            return ghUrl;
        }

        public void setGhUrl(String ghUrl) {
            this.ghUrl = ghUrl;
        }

        public String getCheckUrl() {
            return checkUrl;
        }

        public void setCheckUrl(String checkUrl) {
            this.checkUrl = checkUrl;
        }

        public Integer getPushStyle() {
            return pushStyle;
        }

        public void setPushStyle(Integer pushStyle) {
            this.pushStyle = pushStyle;
        }

        public String getClmcs() {
            return clmcs;
        }

        public void setClmcs(String clmcs) {
            this.clmcs = clmcs;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getMdmcode() {
            return mdmcode;
        }

        public void setMdmcode(String mdmcode) {
            this.mdmcode = mdmcode;
        }

        public String getReceiveFrom() {
            return receiveFrom;
        }

        public void setReceiveFrom(String receiveFrom) {
            this.receiveFrom = receiveFrom;
        }

        @Override
        public String toString() {
            return "UnitInfo{" +
                    "ghUrl='" + ghUrl + '\'' +
                    ", checkUrl='" + checkUrl + '\'' +
                    ", pushStyle=" + pushStyle +
                    ", clmcs='" + clmcs + '\'' +
                    ", mdmcode='" + mdmcode + '\'' +
                    ", receiveFrom='" + receiveFrom + '\'' +
                    ", clientId='" + clientId + '\'' +
                    ", secret='" + secret + '\'' +
                    ", tempTokenUrl='" + tempTokenUrl + '\'' +
                    ", tokenUrl='" + tokenUrl + '\'' +
                    '}';
        }
    }

    public static class TsxxInfo{
        /**
         * 水推送地址
         */
        private String tsurl;
        /**
         * 机构编码
         */
        private String companycode;
        /**
         * 渠道编码
         */
        private String channelcode;
        /**
         * 业务类型编码
         */
        private String businesstypecode;
        /**
         * 业务类型
         */
        private String type;
        /**
         * 加密密钥
         */
        private String signkey;

        public String getTsurl() {
            return tsurl;
        }

        public void setTsurl(String tsurl) {
            this.tsurl = tsurl;
        }

        public String getCompanycode() {
            return companycode;
        }

        public void setCompanycode(String companycode) {
            this.companycode = companycode;
        }

        public String getChannelcode() {
            return channelcode;
        }

        public void setChannelcode(String channelcode) {
            this.channelcode = channelcode;
        }

        public String getBusinesstypecode() {
            return businesstypecode;
        }

        public void setBusinesstypecode(String businesstypecode) {
            this.businesstypecode = businesstypecode;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSignkey() {
            return signkey;
        }

        public void setSignkey(String signkey) {
            this.signkey = signkey;
        }

        @Override
        public String toString() {
            return "TsxxInfo{" +
                    "tsurl='" + tsurl + '\'' +
                    ", companycode='" + companycode + '\'' +
                    ", channelcode='" + channelcode + '\'' +
                    ", businesstypecode='" + businesstypecode + '\'' +
                    ", type='" + type + '\'' +
                    ", signkey='" + signkey + '\'' +
                    '}';
        }
    }

    public Map<String, Map<String, UnitInfo>> getGsdwxx() {
        return gsdwxx;
    }

    public void setGsdwxx(Map<String, Map<String, UnitInfo>> gsdwxx) {
        this.gsdwxx = gsdwxx;
    }

    public Map<String, Map<String, UnitInfo>> getGqdwxx() {
        return gqdwxx;
    }

    public void setGqdwxx(Map<String, Map<String, UnitInfo>> gqdwxx) {
        this.gqdwxx = gqdwxx;
    }

    public Map<String, TsxxInfo> getGstsxx() {
        return gstsxx;
    }

    public void setGstsxx(Map<String, TsxxInfo> gstsxx) {
        this.gstsxx = gstsxx;
    }

    @Override
    public String toString() {
        return "SdqConfig{" +
                "gsdwxx=" + gsdwxx +
                ", gqdwxx=" + gqdwxx +
                ", gstsxx=" + gstsxx +
                '}';
    }
}
