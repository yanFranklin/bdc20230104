package cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request;

import cn.gtmap.realestate.exchange.core.domain.sjpt.*;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

@JSONType(serialzeFeatures = {
        SerializerFeature.WriteNullStringAsEmpty,
        SerializerFeature.WriteNullListAsEmpty
})
public class PeCommitCxsqjgWithEmptyString {

    private String wsbh;
    private List<JSONObject> tdsyq;
    private List<JSONObject> jsydsyq;
    private List<JSONObject> fdcq;
    private List<JSONObject> hysyq;
    private List<JSONObject> gjzwsyq;
    private List<JSONObject> lq;
    private List<JSONObject> dyaq;
    private List<JSONObject> ygdj;
    private List<JSONObject> cfdj;
    private List<JSONObject> yydj;
    private List<JSONObject> nydsyq;

    public String getWsbh() {
        return wsbh;
    }

    public void setWsbh(String wsbh) {
        this.wsbh = wsbh;
    }

    public List<JSONObject> getTdsyq() {
        return tdsyq;
    }

    public void setTdsyq(List<JSONObject> tdsyq) {
        this.tdsyq = tdsyq;
    }

    public List<JSONObject> getJsydsyq() {
        return jsydsyq;
    }

    public void setJsydsyq(List<JSONObject> jsydsyq) {
        this.jsydsyq = jsydsyq;
    }

    public List<JSONObject> getFdcq() {
        return fdcq;
    }

    public void setFdcq(List<JSONObject> fdcq) {
        this.fdcq = fdcq;
    }

    public List<JSONObject> getHysyq() {
        return hysyq;
    }

    public void setHysyq(List<JSONObject> hysyq) {
        this.hysyq = hysyq;
    }

    public List<JSONObject> getGjzwsyq() {
        return gjzwsyq;
    }

    public void setGjzwsyq(List<JSONObject> gjzwsyq) {
        this.gjzwsyq = gjzwsyq;
    }

    public List<JSONObject> getLq() {
        return lq;
    }

    public void setLq(List<JSONObject> lq) {
        this.lq = lq;
    }

    public List<JSONObject> getDyaq() {
        return dyaq;
    }

    public void setDyaq(List<JSONObject> dyaq) {
        this.dyaq = dyaq;
    }

    public List<JSONObject> getYgdj() {
        return ygdj;
    }

    public void setYgdj(List<JSONObject> ygdj) {
        this.ygdj = ygdj;
    }

    public List<JSONObject> getCfdj() {
        return cfdj;
    }

    public void setCfdj(List<JSONObject> cfdj) {
        this.cfdj = cfdj;
    }

    public List<JSONObject> getYydj() {
        return yydj;
    }

    public void setYydj(List<JSONObject> yydj) {
        this.yydj = yydj;
    }

    public List<JSONObject> getNydsyq() {
        return nydsyq;
    }

    public void setNydsyq(List<JSONObject> nydsyq) {
        this.nydsyq = nydsyq;
    }
}
