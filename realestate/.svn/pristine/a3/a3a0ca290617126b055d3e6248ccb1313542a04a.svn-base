package cn.gtmap.realestate.exchange;

import java.util.Objects;

public class BeanInfo {

    private String beanId;

    private String filePath;

    private String xmlStr;

    public String getXmlStr() {
        return xmlStr;
    }

    public void setXmlStr(String xmlStr) {
        this.xmlStr = xmlStr;
    }

    public BeanInfo(String beanId, String filePath) {
        this.beanId = beanId;
        this.filePath = filePath;
    }

    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeanInfo beanInfo = (BeanInfo) o;
        return Objects.equals(beanId, beanInfo.beanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanId);
    }

    public static final class BeanInfoBuilder {
        private String beanId;
        private String filePath;
        private String xmlStr;

        private BeanInfoBuilder() {
        }

        public static BeanInfoBuilder aBeanInfo() {
            return new BeanInfoBuilder();
        }

        public BeanInfoBuilder withBeanId(String beanId) {
            this.beanId = beanId;
            return this;
        }

        public BeanInfoBuilder withFilePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public BeanInfoBuilder withXmlStr(String xmlStr) {
            this.xmlStr = xmlStr;
            return this;
        }

        public BeanInfo build() {
            BeanInfo beanInfo = new BeanInfo(beanId, filePath);
            beanInfo.setXmlStr(xmlStr);
            return beanInfo;
        }
    }
}
