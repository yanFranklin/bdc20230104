package cn.gtmap.realestate.common.core.vo.building;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-12-19
 * @description 项目信息合并VO
 */
public class FwXmxxHbVO {

    @NotBlank(message = "变更编号不能为空")
    private String bgbh;

    @NotBlank(message = "合并项目信息不能为空")
    private String mainIndex;

    @NotEmpty(message = "合并原项目信息不能为空")
    private List<String> yXmxxIndexList;

    /**
     * 权籍管理代码
     */
    private String qjgldm;

    public String getBgbh() {
        return bgbh;
    }

    public void setBgbh(String bgbh) {
        this.bgbh = bgbh;
    }

    public String getMainIndex() {
        return mainIndex;
    }

    public void setMainIndex(String mainIndex) {
        this.mainIndex = mainIndex;
    }

    public List<String> getyXmxxIndexList() {
        return yXmxxIndexList;
    }

    public void setyXmxxIndexList(List<String> yXmxxIndexList) {
        this.yXmxxIndexList = yXmxxIndexList;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }
}
