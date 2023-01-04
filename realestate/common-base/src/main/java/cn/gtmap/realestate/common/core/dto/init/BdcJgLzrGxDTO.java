package cn.gtmap.realestate.common.core.dto.init;

import cn.gtmap.realestate.common.core.domain.BdcJgLzrGxDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @program: realestate
 * @description: 机构lzr关系实体
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2021-1-26
 **/
@ApiModel(value = "BdcJgLzrGxDTO",description = "机构lzr关系实体")
public class BdcJgLzrGxDTO extends BdcJgLzrGxDO {
    @ApiModelProperty(value = "机构名称")
    private String jgmc;

    @ApiModelProperty(value = "领证人证件种类名称")
    private String lzrzjzlmc;

    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    public String getLzrzjzlmc() {
        return lzrzjzlmc;
    }

    public void setLzrzjzlmc(String lzrzjzlmc) {
        this.lzrzjzlmc = lzrzjzlmc;
    }
}
