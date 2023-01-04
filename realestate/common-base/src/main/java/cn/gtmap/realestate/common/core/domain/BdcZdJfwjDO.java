package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/08/11/13:58
 * @Description: 字典：解封文件
 */
@Table(name = "BDC_ZD_JFWJ")
public class BdcZdJfwjDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "解封文件代码")
    private String dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "解封文件名称")
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

    @Override
    public String toString() {
        return "BdcZdJfwjDo{" +
                "dm='" + dm + '\'' +
                ", mc='" + mc + '\'' +
                '}';
    }
}
