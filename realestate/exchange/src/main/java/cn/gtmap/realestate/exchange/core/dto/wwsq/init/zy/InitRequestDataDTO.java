package cn.gtmap.realestate.exchange.core.dto.wwsq.init.zy;

import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;

@IgnoreCast(ignoreNum = 5)
public class InitRequestDataDTO {
    InitRequestDTO data;

    public InitRequestDTO getData() {
        return data;
    }

    public void setData(InitRequestDTO data) {
        this.data = data;
    }
}
