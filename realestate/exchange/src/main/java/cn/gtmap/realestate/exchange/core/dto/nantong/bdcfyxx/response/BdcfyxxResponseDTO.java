package cn.gtmap.realestate.exchange.core.dto.nantong.bdcfyxx.response;


import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.FieldSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-18
 * @description
 */
public class BdcfyxxResponseDTO {

    private List<BdcfyxxResponseQlxx> qlxx;

    public List<BdcfyxxResponseQlxx> getQlxx() {
        return qlxx;
    }

    public void setQlxx(List<BdcfyxxResponseQlxx> qlxx) {
        this.qlxx = qlxx;
    }
}
