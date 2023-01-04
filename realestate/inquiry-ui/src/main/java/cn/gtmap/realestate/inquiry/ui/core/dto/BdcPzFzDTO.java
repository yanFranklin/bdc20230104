package cn.gtmap.realestate.inquiry.ui.core.dto;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/6/28
 * @description 复制配置
 */
public class BdcPzFzDTO {
    /**
     * 复制数量
     */
    private Integer fzsl;
    /**
     * 复制内容
     */
    private String fznr;

    public Integer getFzsl() {
        return fzsl;
    }

    public void setFzsl(Integer fzsl) {
        this.fzsl = fzsl;
    }

    public String getFznr() {
        return fznr;
    }

    public void setFznr(String fznr) {
        this.fznr = fznr;
    }

    @Override
    public String toString() {
        return "BdcPzFzDTO{" +
                "fzsl=" + fzsl +
                ", fznr='" + fznr + '\'' +
                '}';
    }
}
