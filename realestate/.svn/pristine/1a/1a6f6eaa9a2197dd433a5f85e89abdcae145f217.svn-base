package cn.gtmap.realestate.building.config.ftp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-08
 * @description 宗地图 (常州-金坛 额外)FTP 服务配置
 */
@Configuration
public class ZdtJtFtpConfig implements FtpConfig {
    private static final Set<String> CODE = new HashSet<>();
    private static final String QXDM = "320482";
    static {
        CODE.add(QXDM);
    }

    @Value("${zdtFtp.jt.ip:}")
    private String ip;

    @Value("${zdtFtp.jt.port:}")
    private String port;

    @Value("${zdtFtp.jt.username:}")
    private String username;

    @Value("${zdtFtp.jt.password:}")
    private String password;

    // 定作物为W的 宗地图 存储地址
    @Value("${zdtFtp.jt.path:}")
    private String path;

    // 定作物为F 的 宗地图 存储地址
    @Value("${zdtFtp.jt.pathF:}")
    private String pathF;

    @Override
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getPathF() {
        return pathF;
    }

    public void setPathF(String pathF) {
        this.pathF = pathF;
    }

    /**
     * @return 接口标识码，同一接口中的标识码不能出现重复
     * @author <a href ="mailto:zhaodongdong@gtmap.cn">zhaodongdong</a>
     * @description 获取实现类的标识码
     */
    @Override
    public Set<String> getInterfaceCode() {
        return CODE;
    }
}
