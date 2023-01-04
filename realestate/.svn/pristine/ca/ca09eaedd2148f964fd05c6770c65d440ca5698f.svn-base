package cn.gtmap.realestate.certificate.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 存量电子证照生成的业务配置
 *
 * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
 * @version 下午4:23 2021/2/4
 */
@Component
@ConfigurationProperties(prefix = "dzzzbc.clsj.ywxx")
public class DzzzClywConfig {
    /**
     * 查询的证书类型
     */
    private String zslx;

    /**
     * 过滤流程
     */
    private String lwgzldyid;

    /**
     * 存量证照生成的截止时间
     */
    private String endtime;

    /**
     * 处理数目，对应代码中分页数目
     */
    private Integer performSize;

    /**
     * 电子证照下载接口参数，证照使用目的
     */
    private String dzzzsymd;

    /**
     * 电子证照 token 失效时间，默认 7200 s
     */
    private Long timeout;

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Integer getPerformSize() {
        return performSize;
    }

    public void setPerformSize(Integer performSize) {
        this.performSize = performSize;
    }

    public String getZslx() {
        return zslx;
    }

    public void setZslx(String zslx) {
        this.zslx = zslx;
    }

    public String getLwgzldyid() {
        return lwgzldyid;
    }

    public void setLwgzldyid(String lwgzldyid) {
        this.lwgzldyid = lwgzldyid;
    }

    public String getDzzzsymd() {
        return dzzzsymd;
    }

    public void setDzzzsymd(String dzzzsymd) {
        this.dzzzsymd = dzzzsymd;
    }

    public Long getTimeout() {
        if (timeout == null) {
            this.timeout = 7200L;
        }
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "DzzzClywConfig{" +
                "zslx='" + zslx + '\'' +
                ", lwgzldyid='" + lwgzldyid + '\'' +
                ", endtime='" + endtime + '\'' +
                ", performSize=" + performSize +
                ", dzzzsymd='" + dzzzsymd + '\'' +
                '}';
    }
}
