package cn.gtmap.realestate.common.core.dto.exchange.wwsq.dydjxxcx.response;

import org.springframework.data.domain.Page;

import java.util.List;

/****
 *
 */
public class WwsqDydjfxxResponseHeadDTO {
    Page<WwsqDydjfxxDTO> data;

    public Page<WwsqDydjfxxDTO> getData() {
        return data;
    }

    public void setData(Page<WwsqDydjfxxDTO> data) {
        this.data = data;
    }
}
