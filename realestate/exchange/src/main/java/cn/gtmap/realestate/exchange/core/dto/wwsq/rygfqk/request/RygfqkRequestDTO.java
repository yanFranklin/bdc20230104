package cn.gtmap.realestate.exchange.core.dto.wwsq.rygfqk.request;

import cn.gtmap.realestate.exchange.core.domain.GxRygfqkDO;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-02-06
 * @description 人员购房情况查询参数实体
 */
public class RygfqkRequestDTO {

    // 是否精确查询 true,false
    @NotBlank(message = "是否精确查询参数不能为空")
    private String sfjqcx;

    private List<GxRygfqkDO> ryxx;

    public String getSfjqcx() {
        return sfjqcx;
    }

    public void setSfjqcx(String sfjqcx) {
        this.sfjqcx = sfjqcx;
    }

    public List<GxRygfqkDO> getRyxx() {
        return ryxx;
    }

    public void setRyxx(List<GxRygfqkDO> ryxx) {
        this.ryxx = ryxx;
    }
}
