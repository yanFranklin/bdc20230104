package cn.gtmap.realestate.common.core.dto.building;

import cn.gtmap.realestate.common.core.domain.building.FwJsydzrzxxDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.StringJoiner;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/11/15
 * @description 建设用地自然幢信息DTO
 */
public class FwJsydzrzxxDTO extends FwJsydzrzxxDO {

    @ApiModelProperty(value = "是否预告 1:是 0：不是")
    private Integer sfyg;

    public Integer getSfyg() {
        return sfyg;
    }

    public void setSfyg(Integer sfyg) {
        this.sfyg = sfyg;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FwJsydzrzxxDTO.class.getSimpleName() + "[", "]")
                .add("sfyg=" + sfyg)
                .toString();
    }
}
