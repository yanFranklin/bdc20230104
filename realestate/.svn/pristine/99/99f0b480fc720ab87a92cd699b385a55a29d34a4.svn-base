package cn.gtmap.realestate.common.property.configui;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "xzrz-config")
@Component
public class SshConfiguration {

    private List<MpConfig> configs;

    /**
     * 通过内部类来实现对应的配置属性
     */
    public static class MpConfig {
        /**
         *  服务器主机
         */
        private String host;
        /**
         *  服务器端口
         */
        private String port;
        /**
         *  服务器用户名
         */
        private String username;
        /**
         *  服务器密码
         */
        private String password;
        /**
         * 服务器对应的路径
         */
        private String path;
        /**
         * 对应存放日志的地址
         */
        private String applyName;
        /**
         * 对应的应用
         */
        private String name;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getApplyName() {
            return applyName;
        }

        public void setApplyName(String applyName) {
            this.applyName = applyName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public List<MpConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<MpConfig> configs) {
        this.configs = configs;
    }
}