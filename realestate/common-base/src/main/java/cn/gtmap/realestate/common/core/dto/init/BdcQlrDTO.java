package cn.gtmap.realestate.common.core.dto.init;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @program: 3.0
 * @description: 权利人信息DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-08-02 13:39
 **/
public class BdcQlrDTO {

    @ApiModelProperty("权利人信息")
    private BdcQlrDO bdcQlrDO;

    @ApiModelProperty("产权证号年份+流水号")
    private String nfZhlsh;

    public BdcQlrDO getBdcQlrDO() {
        return bdcQlrDO;
    }

    public void setBdcQlrDO(BdcQlrDO bdcQlrDO) {
        this.bdcQlrDO = bdcQlrDO;
    }

    public String getNfZhlsh() {
        return nfZhlsh;
    }

    public void setNfZhlsh(String nfZhlsh) {
        this.nfZhlsh = nfZhlsh;
    }
}
