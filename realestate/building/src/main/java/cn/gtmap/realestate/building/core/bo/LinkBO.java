package cn.gtmap.realestate.building.core.bo;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-02
 * @description 楼盘表查询配置 链接实体
 */
@XStreamAlias("link")
public class LinkBO {

    /**
     * 链接维度名称
     */
    private String name;

    /**
     * 链接URL
     */
    private String url;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
