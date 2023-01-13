package cn.gtmap.realestate.common.core.domain.job;

import javax.persistence.Table;
import java.util.Date;

/**
 * 执行器注册表，维护在线的执行器和调度中心机器地址信息
 * Created by  on 16/9/30.
 */
@Table(name = "BDC_JOB_REGISTRY")
public class BdcJobRegistryDO {

    private Integer id;
    private String registrygroup; //注册器 注册类型 枚举值 { EXECUTOR, ADMIN }
    private String registrykey; //注册执行器的appname
    private String registryvalue; //执行器注册机器地址

    // 每过30秒客户端执行器会发送一次注册请求，服务端接收到请求也会更新xxl_job_registry表
    private Date updatetime; //更新时间，自动更新

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegistrygroup() {
        return registrygroup;
    }

    public void setRegistrygroup(String registrygroup) {
        this.registrygroup = registrygroup;
    }

    public String getRegistrykey() {
        return registrykey;
    }

    public void setRegistrykey(String registrykey) {
        this.registrykey = registrykey;
    }

    public String getRegistryvalue() {
        return registryvalue;
    }

    public void setRegistryvalue(String registryvalue) {
        this.registryvalue = registryvalue;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
