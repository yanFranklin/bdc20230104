package cn.gtmap.realestate.common.core.dto.building;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-21
 * @description 整幢关联DTO
 */
public class YcScHsZzglRequestDTO {

    /**
     * 幢主键
     */
    @NotBlank(message = "幢主键不能为空")
    private String fwDcbIndex;

    /**
     * 关联关系类型
     * 1. FWBM
     * 2. DYH WLCS FJH
     */
    @NotBlank(message = "关联类型不能为空")
    private String glgxType;

    /**
     * 是否重新关联
     * 1. 是  0. 否
     */
    @NotBlank(message = "是否重新关联不能为空")
    private String cxgl;

    /**
     * 权籍管理代码
     */
    private String qjgldm;

    public String getFwDcbIndex() {
        return fwDcbIndex;
    }

    public void setFwDcbIndex(String fwDcbIndex) {
        this.fwDcbIndex = fwDcbIndex;
    }

    public String getGlgxType() {
        return glgxType;
    }

    public void setGlgxType(String glgxType) {
        this.glgxType = glgxType;
    }

    public String getCxgl() {
        return cxgl;
    }

    public void setCxgl(String cxgl) {
        this.cxgl = cxgl;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }
}
