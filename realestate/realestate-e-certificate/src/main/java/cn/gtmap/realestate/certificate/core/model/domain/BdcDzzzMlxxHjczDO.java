package cn.gtmap.realestate.certificate.core.model.domain;

/**
 * 目录信息汇交差值表
 *
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/4/30
 * @description
 */
public class BdcDzzzMlxxHjczDO {
    /**
     * 行政区代码
     */
    private String xzqdm;
    /**
     * 差值
     */
    private Integer cz;

    public String getXzqdm() {
        return xzqdm;
    }

    public void setXzqdm(String xzqdm) {
        this.xzqdm = xzqdm;
    }

    public Integer getCz() {
        return cz;
    }

    public void setCz(Integer cz) {
        this.cz = cz;
    }
}
