package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjydjkxxhq.response;

import java.util.List;

/**
 * @author: jinfei
 * @email: jinfei@gtmap.cn
 * @date: 2022/9/20 18:00
 **/
public class JkxxgridlbResponse {
    private List<JkxxResponse> jkxx;

    public List<JkxxResponse> getJkxx() {
        return jkxx;
    }

    public void setJkxx(List<JkxxResponse> jkxx) {
        this.jkxx = jkxx;
    }

    @Override
    public String toString() {
        return "JkxxgridlbResponse{" +
                "jkxx=" + jkxx +
                '}';
    }
}
