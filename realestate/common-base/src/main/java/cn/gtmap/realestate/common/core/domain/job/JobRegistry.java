package cn.gtmap.realestate.common.core.domain.job;

import javax.persistence.Table;
import java.util.Date;

/**
 * 执行器注册表，维护在线的执行器和调度中心机器地址信息
 * Created by xuxueli on 16/9/30.
 */
@Table(name = "BDC_JOB_REGISTRY")
public class JobRegistry {

    private int id;
    private String registryGroup; //注册器 注册类型 枚举值 { EXECUTOR, ADMIN }
    private String registryKey; //注册执行器的appname
    private String registryValue; //执行器注册机器地址

    // 每过30秒客户端执行器会发送一次注册请求，服务端接收到请求也会更新xxl_job_registry表
    private Date updateTime; //更新时间，自动更新

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegistryGroup() {
        return registryGroup;
    }

    public void setRegistryGroup(String registryGroup) {
        this.registryGroup = registryGroup;
    }

    public String getRegistryKey() {
        return registryKey;
    }

    public void setRegistryKey(String registryKey) {
        this.registryKey = registryKey;
    }

    public String getRegistryValue() {
        return registryValue;
    }

    public void setRegistryValue(String registryValue) {
        this.registryValue = registryValue;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
