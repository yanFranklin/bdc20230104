package cn.gtmap.realestate.etl.config;

import com.jcraft.jsch.ChannelSftp;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sftp")
public class SftpProperties {

    private String host;
    private int port = 22;
    private String username = "root";
    private String password = "root";
    private Pool pool = new Pool();

    public static class Pool extends GenericObjectPoolConfig<ChannelSftp> {

        private int maxTotal = DEFAULT_MAX_TOTAL;
        private int maxIdle = DEFAULT_MAX_IDLE;
        private int minIdle = DEFAULT_MIN_IDLE;

        public Pool() {
            super();
        }
        @Override
        public int getMaxTotal() {
            return maxTotal;
        }
        @Override
        public void setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
        }
        @Override
        public int getMaxIdle() {
            return maxIdle;
        }
        @Override
        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }
        @Override
        public int getMinIdle() {
            return minIdle;
        }
        @Override
        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
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

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }
}
