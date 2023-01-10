package cn.gtmap.realestate.common.job.biz.model;

import java.io.Serializable;

/**
 * Created by  on 2017-05-10 20:22:42
 */
public class RegistryParam implements Serializable {
    private static final long serialVersionUID = 42L;

    private String registrygroup;
    private String registrykey;
    private String registryvalue;

    public RegistryParam(){}
    public RegistryParam(String registrygroup, String registrykey, String registryvalue) {
        this.registrygroup = registrygroup;
        this.registrykey = registrykey;
        this.registryvalue = registryvalue;
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

    @Override
    public String toString() {
        return "RegistryParam{" +
                "registrygroup='" + registrygroup + '\'' +
                ", registrykey='" + registrykey + '\'' +
                ", registryvalue='" + registryvalue + '\'' +
                '}';
    }
}
