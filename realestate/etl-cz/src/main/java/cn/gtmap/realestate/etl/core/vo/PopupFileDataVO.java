package cn.gtmap.realestate.etl.core.vo;

import java.io.Serializable;
import java.util.List;

public class PopupFileDataVO implements Serializable {

    private static final long serialVersionUID = -3727610827785492428L;

    private String id;

    private String clientId;

    private String type;

    private String name;

    private String downUrl;

    private String suffix;

    private List<PopupFileDataVO> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public List<PopupFileDataVO> getChildren() {
        return children;
    }

    public void setChildren(List<PopupFileDataVO> children) {
        this.children = children;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public static final class PopupFileDataVOBuilder {
        private String id;
        private String clientId;
        private String type;
        private String name;
        private String downUrl;
        private String suffix;
        private List<PopupFileDataVO> children;

        private PopupFileDataVOBuilder() {
        }

        public static PopupFileDataVOBuilder aPopupFileDataVO() {
            return new PopupFileDataVOBuilder();
        }

        public PopupFileDataVOBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public PopupFileDataVOBuilder withClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public PopupFileDataVOBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public PopupFileDataVOBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PopupFileDataVOBuilder withDownUrl(String downUrl) {
            this.downUrl = downUrl;
            return this;
        }

        public PopupFileDataVOBuilder withChildren(List<PopupFileDataVO> children) {
            this.children = children;
            return this;
        }

        public PopupFileDataVOBuilder withSuffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        public PopupFileDataVO build() {
            PopupFileDataVO popupFileDataVO = new PopupFileDataVO();
            popupFileDataVO.setId(id);
            popupFileDataVO.setClientId(clientId);
            popupFileDataVO.setType(type);
            popupFileDataVO.setName(name);
            popupFileDataVO.setSuffix(suffix);
            popupFileDataVO.setDownUrl(downUrl);
            popupFileDataVO.setChildren(children);
            return popupFileDataVO;
        }
    }
}
