package cn.gtmap.realestate.common.core.vo.building;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-19
 * @description 户室合并VO
 */
public class FwHsHbVO {

    @NotBlank(message = "变更编号不能为空")
    private String bgbh;

    @NotBlank(message = "合并方向不能为空")
    private String hbfx;

    /**
     * 上下合并场景下  最低层号
     */
    private Integer ch;

    @NotBlank(message = "合并户室不能为空")
    private String mainIndex;

    @NotEmpty(message = "合并原户室不能为空")
    private List<String> yfwHsIndexList;

    private List<String> zhsList;

    public List<String> getZhsList() {
        return zhsList;
    }

    public void setZhsList(List<String> zhsList) {
        this.zhsList = zhsList;
    }

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

    public List<String> getYfwHsIndexList() {
        return yfwHsIndexList;
    }

    public void setYfwHsIndexList(List<String> yfwHsIndexList) {
        this.yfwHsIndexList = yfwHsIndexList;
    }

    public String getHbfx() {
        return hbfx;
    }

    public void setHbfx(String hbfx) {
        this.hbfx = hbfx;
    }

    public Integer getCh() {
        return ch;
    }

    public void setCh(Integer ch) {
        this.ch = ch;
    }
}
