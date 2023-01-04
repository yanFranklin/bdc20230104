package cn.gtmap.realestate.common.core.vo.config.ui;

import java.util.List;

/**
 * @author <a href ="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.3, 2021/08/20
 * @description 不动产省市级共享接口字典表VO
 */
public class BdcZdSsjGxVO {

    private String name;
    private String mc;
    private String url;
    private List<BdcZdSsjGxVO> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<BdcZdSsjGxVO> getChildren() {
        return children;
    }

    public void setChildren(List<BdcZdSsjGxVO> children) {
        this.children = children;
    }

    public static final class BdcZdSsjGxVOBuilder {
        private String name;
        private String mc;
        private String url;
        private List<BdcZdSsjGxVO> children;

        private BdcZdSsjGxVOBuilder() {
        }

        public static BdcZdSsjGxVOBuilder aBdcZdSsjGxVO() {
            return new BdcZdSsjGxVOBuilder();
        }

        public BdcZdSsjGxVOBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public BdcZdSsjGxVOBuilder withMc(String mc) {
            this.mc = mc;
            return this;
        }

        public BdcZdSsjGxVOBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public BdcZdSsjGxVOBuilder withChildren(List<BdcZdSsjGxVO> children) {
            this.children = children;
            return this;
        }

        public BdcZdSsjGxVO build() {
            BdcZdSsjGxVO bdcZdSsjGxVO = new BdcZdSsjGxVO();
            bdcZdSsjGxVO.setName(name);
            bdcZdSsjGxVO.setMc(mc);
            bdcZdSsjGxVO.setUrl(url);
            bdcZdSsjGxVO.setChildren(children);
            return bdcZdSsjGxVO;
        }
    }
}
