package cn.gtmap.realestate.portal.ui.core.dto;

/**
 * 特殊流程对象
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0  2020/02/25.
 */
public class BdcTslcDTO {
    /**
     * 特殊流程定义 key
     */
    private String processDefKey;
    /**
     * 创建流程后跳转 url 地址
     */
    private String url;

    public String getProcessDefKey() {
        return processDefKey;
    }

    public void setProcessDefKey(String processDefKey) {
        this.processDefKey = processDefKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
