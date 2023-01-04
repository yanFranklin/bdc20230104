package cn.gtmap.realestate.inquiry.ui.core.vo;

public class BdcXzrzServerVo {
    /**
     * 注册中心获得实例
     */
    private String serviceId;
    /**
     * 注册中心获得ip
     */
    private String host;
    private String name;

    public BdcXzrzServerVo(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
