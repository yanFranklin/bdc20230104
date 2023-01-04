package cn.gtmap.realestate.common.core.qo.exchange.openapi;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/13 08:41
 */
public class BdcOpenApiQO {

    private List<String> consumerList;

    private String name;

    private String url;

    private String description;

    private String releaseStatus;

    public List<String> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(List<String> consumerList) {
        this.consumerList = consumerList;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }
}
