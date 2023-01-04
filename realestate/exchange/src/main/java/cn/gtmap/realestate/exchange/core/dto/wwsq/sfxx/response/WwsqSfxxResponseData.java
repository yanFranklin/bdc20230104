package cn.gtmap.realestate.exchange.core.dto.wwsq.sfxx.response;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/10/17
 * @description 查询收费信息返回值
 */
@JSONType(serialzeFeatures = {
            SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.WriteNullListAsEmpty
        })
public class WwsqSfxxResponseData {
    private String slbh;
    private String slr;
    private String djjg;
    private String slsj;
    private String lcmc;
    private String bdcdyh;
    private String zl;
    private List<WwsqSfxxResponseQlr> qlrList;
    private List<WwsqSfxxResponseSfxx> sfxxList;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getSlr() {
        return slr;
    }

    public void setSlr(String slr) {
        this.slr = slr;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public String getSlsj() {
        return slsj;
    }

    public void setSlsj(String slsj) {
        this.slsj = slsj;
    }

    public String getLcmc() {
        return lcmc;
    }

    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public List<WwsqSfxxResponseQlr> getQlrList() {
        return qlrList;
    }

    public void setQlrList(List<WwsqSfxxResponseQlr> qlrList) {
        this.qlrList = qlrList;
    }

    public List<WwsqSfxxResponseSfxx> getSfxxList() {
        return sfxxList;
    }

    public void setSfxxList(List<WwsqSfxxResponseSfxx> sfxxList) {
        this.sfxxList = sfxxList;
    }
}
