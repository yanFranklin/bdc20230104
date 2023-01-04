package cn.gtmap.realestate.building.config.ftp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-08
 * @description 宗地图 (庐江 额外)FTP 服务配置
 */
@Configuration
public class ZdtFtpMapConfig{
    /**
     * 根据区县代码分类评价器的调用接口地址
     */
    @Value("#{${zdtFtp.zdtFtpMap:null}}")
    private Map<String, String> zdtFtpMap = new HashMap<>();

    public Map<String, String> getZdtFtpMap() {
        return zdtFtpMap;
    }

    public void setZdtFtpMap(Map<String, String> zdtFtpMap) {
        this.zdtFtpMap = zdtFtpMap;
    }
}
