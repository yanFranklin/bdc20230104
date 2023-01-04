package cn.gtmap.realestate.common.core.domain.natural;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href=""mailto:liaoxiang@gtmap.cn>liaoxiang</a>
 * @version 1.0, 2021/10/23.
 * @description 水流类型字典表
 */
@Table(name = "ZRZY_ZD_SZYLX")
public class ZrzyZdSzylxDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "代码")
    private String dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String mc;

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }


}
