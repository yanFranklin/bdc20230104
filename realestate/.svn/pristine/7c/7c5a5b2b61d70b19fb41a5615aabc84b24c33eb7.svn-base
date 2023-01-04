package cn.gtmap.realestate.exchange.core.dto.jgpt.ywsl;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/9/9
 * @description 具体业务DTO
 */
@ApiModel(description = "统计具体办理业务DTO")
public class JgptYwslDTO {

    @ApiModelProperty("数量")
    private Integer count;

    @ApiModelProperty("详细数据对象")
    private List<JgptYwlcDO> details;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<JgptYwlcDO> getDetails() {
        return details;
    }

    public void setDetails(List<JgptYwlcDO> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "JgptYwslDTO{" +
                "count=" + count +
                ", details=" + details +
                '}';
    }
}
