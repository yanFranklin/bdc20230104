package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfrwjs.request;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.yxzlxxjs.request.YrbYwwjxx;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfrwjs.request.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/14
 * @description 推送税务DTO
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "RWJSHOUSELIST")
public class YrbClfRwjshouseRequest {


    private YrbZlfHouseRequest housevo;


    private List<YrbZlfJyfxxRequest> jyfxxList;

    private List<YrbZlfHyxxRequest> hyxxRequestList;

    private List<YrbZlfWcnznRequest> wcnznRequestList;

    private YrbClfFyjbxxRequest fyjbxx;

    private YrbClfFcjyxxRequest fcjycjxx;

    private YrbZlfFpxxRequest zlffpxx;

    private YrbZlfCedkxxRequest cedkxx;

    private List<YrbYwwjxx> yxwjxx;

    private YrbZlfQtRequest qt;

    @XmlElement(name = "HOUSEVO")
    public YrbZlfHouseRequest getHousevo() {
        return housevo;
    }

    public void setHousevo(YrbZlfHouseRequest housevo) {
        this.housevo = housevo;
    }

    @XmlElement(name = "JYFXX")
    public List<YrbZlfJyfxxRequest> getJyfxxList() {
        return jyfxxList;
    }

    public void setJyfxxList(List<YrbZlfJyfxxRequest> jyfxxList) {
        this.jyfxxList = jyfxxList;
    }

    @XmlElement(name = "HYXX")
    public List<YrbZlfHyxxRequest> getHyxxRequestList() {
        return hyxxRequestList;
    }

    public void setHyxxRequestList(List<YrbZlfHyxxRequest> hyxxRequestList) {
        this.hyxxRequestList = hyxxRequestList;
    }

    @XmlElement(name = "WCNZNXX")
    public List<YrbZlfWcnznRequest> getWcnznRequestList() {
        return wcnznRequestList;
    }

    public void setWcnznRequestList(List<YrbZlfWcnznRequest> wcnznRequestList) {
        this.wcnznRequestList = wcnznRequestList;
    }

    @XmlElement(name = "FYJBXX")
    public YrbClfFyjbxxRequest getFyjbxx() {
        return fyjbxx;
    }

    public void setFyjbxx(YrbClfFyjbxxRequest fyjbxx) {
        this.fyjbxx = fyjbxx;
    }

    @XmlElement(name = "FCJYCJXX")
    public YrbClfFcjyxxRequest getFcjycjxx() {
        return fcjycjxx;
    }

    public void setFcjycjxx(YrbClfFcjyxxRequest fcjycjxx) {
        this.fcjycjxx = fcjycjxx;
    }

    @XmlElement(name = "ZLFFPXX")
    public YrbZlfFpxxRequest getZlffpxx() {
        return zlffpxx;
    }

    public void setZlffpxx(YrbZlfFpxxRequest zlffpxx) {
        this.zlffpxx = zlffpxx;
    }

    @XmlElement(name = "CEDKXX")
    public YrbZlfCedkxxRequest getCedkxx() {
        return cedkxx;
    }

    public void setCedkxx(YrbZlfCedkxxRequest cedkxx) {
        this.cedkxx = cedkxx;
    }

    @XmlElement(name = "YXWJXX")
    public List<YrbYwwjxx> getYxwjxx() {
        return yxwjxx;
    }

    public void setYxwjxx(List<YrbYwwjxx> yxwjxx) {
        this.yxwjxx = yxwjxx;
    }

    @XmlElement(name = "QT")
    public YrbZlfQtRequest getQt() {
        return qt;
    }

    public void setQt(YrbZlfQtRequest qt) {
        this.qt = qt;
    }

    @Override
    public String toString() {
        return "YrbZlfRwjshouseRequest{" +
                "housevo=" + housevo +
                ", jyfxxList=" + jyfxxList +
                ", hyxxRequestList=" + hyxxRequestList +
                ", wcnznRequestList=" + wcnznRequestList +
                ", fyjbxx=" + fyjbxx +
                ", fcjycjxx=" + fcjycjxx +
                ", zlffpxx=" + zlffpxx +
                ", cedkxx=" + cedkxx +
                ", yxwjxx=" + yxwjxx +
                ", qt=" + qt +
                '}';
    }
}
