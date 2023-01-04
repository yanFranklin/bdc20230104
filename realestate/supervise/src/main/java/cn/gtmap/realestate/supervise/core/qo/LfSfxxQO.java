package cn.gtmap.realestate.supervise.core.qo;

import cn.gtmap.realestate.supervise.core.dto.BdcLfSfxxDTO;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/09/27
 * @description 收费信息查询QO
 */
public class LfSfxxQO extends BdcLfSfxxDTO {
    @ApiModelProperty(value = "收费时间(起)")
    private String sfsjq;

    @ApiModelProperty(value = "收费时间(止)")
    private String sfsjz;


    public String getSfsjq() {
        return sfsjq;
    }

    public void setSfsjq(String sfsjq) {
        this.sfsjq = sfsjq;
    }

    public String getSfsjz() {
        return sfsjz;
    }

    public void setSfsjz(String sfsjz) {
        this.sfsjz = sfsjz;
    }

    @Override
    public String toString() {
        return "LfSfxxQO{" +
                "sfsjq='" + sfsjq + '\'' +
                ", sfsjz='" + sfsjz + '\'' +
                '}';
    }
}
