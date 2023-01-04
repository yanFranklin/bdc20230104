package cn.gtmap.realestate.common.core.enums;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/6 11:11
 * @description 接口发布状态
 */
public enum OpenApiReleaseStatus {

    /**
     * 已发布
     */
    PUBLISHED(1,"已发布"),

    /**
     * 未发布
     */
    UNPUBLISHED(0,"未发布");

    private Integer releaseStatus;

    private String releaseText;

    OpenApiReleaseStatus(Integer releaseStatus, String releaseText) {
        this.releaseStatus = releaseStatus;
        this.releaseText = releaseText;
    }

    public Integer getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(Integer releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public String getReleaseText() {
        return releaseText;
    }

    public void setReleaseText(String releaseText) {
        this.releaseText = releaseText;
    }
}
