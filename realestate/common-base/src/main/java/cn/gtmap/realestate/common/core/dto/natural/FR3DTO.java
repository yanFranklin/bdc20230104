package cn.gtmap.realestate.common.core.dto.natural;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/3/26
 * @description
 */
public class FR3DTO {

    private String url;

    @XmlAttribute(name="url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
