package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2019/1/3
 * @description 不动产受理收费项目收费标准
 */
@Table(name = "BDC_SL_SFXM_SFBZ")
@ApiModel(value = "BdcSlSfxmSfbzDO", description = "不动产受理收费项目收费标准")
public class BdcSlSfxmSfbzDO implements Serializable {
    private static final long serialVersionUID = 4103648813071756428L;
    @Id
    @ApiModelProperty(value = "收费项目代码")
    private String sfxmdm;

    @ApiModelProperty(value = "收费项目标准")
    private String sfxmbz;

    @ApiModelProperty(value = "收费项目单价")
    private String sfxmdj;

    public String getSfxmdm() {
        return sfxmdm;
    }

    public void setSfxmdm(String sfxmdm) {
        this.sfxmdm = sfxmdm;
    }

    public String getSfxmbz() {
        return sfxmbz;
    }

    public void setSfxmbz(String sfxmbz) {
        this.sfxmbz = sfxmbz;
    }

    public String getSfxmdj() {
        return sfxmdj;
    }

    public void setSfxmdj(String sfxmdj) {
        this.sfxmdj = sfxmdj;
    }

    @Override
    public String toString() {
        return "BdcSlSfxmSfbzDO{" +
                "sfxmdm='" + sfxmdm + '\'' +
                ", sfxmbz='" + sfxmbz + '\'' +
                ", sfxmdj='" + sfxmdj + '\'' +
                '}';
    }
}
