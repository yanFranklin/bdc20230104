package cn.gtmap.realestate.exchange.core.dto;

import java.io.Serializable;

public class AccessDyjeInfo implements Serializable {

    private static final long serialVersionUID = 5152735505032012539L;

    private String xmid;

    private String zgzqse;

    private String bdbzzqse;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getZgzqse() {
        return zgzqse;
    }

    public void setZgzqse(String zgzqse) {
        this.zgzqse = zgzqse;
    }

    public String getBdbzzqse() {
        return bdbzzqse;
    }

    public void setBdbzzqse(String bdbzzqse) {
        this.bdbzzqse = bdbzzqse;
    }

    public static final class AccessDyjeInfoBuilder {
        private String xmid;
        private String zgzqse;
        private String bdbzzqse;

        private AccessDyjeInfoBuilder() {
        }

        public static AccessDyjeInfoBuilder anAccessDyjeInfo() {
            return new AccessDyjeInfoBuilder();
        }

        public AccessDyjeInfoBuilder withXmid(String xmid) {
            this.xmid = xmid;
            return this;
        }

        public AccessDyjeInfoBuilder withZgzqse(String zgzqse) {
            this.zgzqse = zgzqse;
            return this;
        }

        public AccessDyjeInfoBuilder withBdbzzqse(String bdbzzqse) {
            this.bdbzzqse = bdbzzqse;
            return this;
        }

        public AccessDyjeInfo build() {
            AccessDyjeInfo accessDyjeInfo = new AccessDyjeInfo();
            accessDyjeInfo.setXmid(xmid);
            accessDyjeInfo.setZgzqse(zgzqse);
            accessDyjeInfo.setBdbzzqse(bdbzzqse);
            return accessDyjeInfo;
        }
    }
}
