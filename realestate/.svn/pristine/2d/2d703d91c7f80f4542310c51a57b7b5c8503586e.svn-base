package cn.gtmap.realestate.exchange.core.dto.dbxxts.zjjg;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.ApiModel;

import java.util.List;


/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/11/1
 * @description 登簿信息
 */
@ApiModel(value = "ZjjgDbxxDTO", description = "登簿信息")
@JSONType(serialzeFeatures = {
        SerializerFeature.WriteNullStringAsEmpty,
        SerializerFeature.WriteNullListAsEmpty
})
public class ZjjgDbxxDTO {

    private List<ZjjgCqxxDTO> cqxx;

    private List<ZjjgDyaxxDTO> dyxx;

    public List<ZjjgCqxxDTO> getCqxx() {
        return cqxx;
    }

    public void setCqxx(List<ZjjgCqxxDTO> cqxx) {
        this.cqxx = cqxx;
    }

    public List<ZjjgDyaxxDTO> getDyxx() {
        return dyxx;
    }

    public void setDyxx(List<ZjjgDyaxxDTO> dyxx) {
        this.dyxx = dyxx;
    }

    @Override
    public String toString() {
        return "ZjjgDbxxDTO{" +
                "cqxx=" + cqxx +
                ", dyxx=" + dyxx +
                '}';
    }
}
