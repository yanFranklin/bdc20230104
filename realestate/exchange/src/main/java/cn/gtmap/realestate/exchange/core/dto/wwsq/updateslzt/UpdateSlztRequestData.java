package cn.gtmap.realestate.exchange.core.dto.wwsq.updateslzt;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/10/16
 * @description
 */
@JSONType(serialzeFeatures = {
        SerializerFeature.WriteNullStringAsEmpty
})
public class UpdateSlztRequestData {
    private String slbh;
    private String slzt;
    private String spyj;
    private String slztmc;
    private String jfzt;
    private String czrmc;
    private String czsj;

    public String getJfzt() {
        return jfzt;
    }

    public void setJfzt(String jfzt) {
        this.jfzt = jfzt;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getSlzt() {
        return slzt;
    }

    public void setSlzt(String slzt) {
        this.slzt = slzt;
    }

    public String getSpyj() {
        return spyj;
    }

    public void setSpyj(String spyj) {
        this.spyj = spyj;
    }

    public String getSlztmc() {
        return slztmc;
    }

    public void setSlztmc(String slztmc) {
        this.slztmc = slztmc;
    }

    public String getCzrmc() {
        return czrmc;
    }

    public void setCzrmc(String czrmc) {
        this.czrmc = czrmc;
    }

    public String getCzsj() {
        return czsj;
    }

    public void setCzsj(String czsj) {
        this.czsj = czsj;
    }

    @Override
    public String toString() {
        return "UpdateSlztRequestData{" +
                "slbh='" + slbh + '\'' +
                ", slzt='" + slzt + '\'' +
                ", spyj='" + spyj + '\'' +
                ", slztmc='" + slztmc + '\'' +
                ", jfzt='" + jfzt + '\'' +
                ", czrmc='" + czrmc + '\'' +
                ", czsj='" + czsj + '\'' +
                '}';
    }
}
