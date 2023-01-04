package cn.gtmap.realestate.common.core.dto.exchange.ykq.zlfwjs.request;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.yxzlxxjs.request.YrbYwwjxx;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfrwjs.request.*;
import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/14
 * @description 推送税务DTO
 */
public class YkqZlfRwjshouseRequest {

    private List<YrbZlfHouseRequest> housevo;

    private List<YrbZlfJyfxxRequest> jyfxx;

    private List<YrbZlfHyxxRequest> hyxx;

    private List<YrbZlfWcnznRequest> wcnznxx;

    private List<YrbZlfFyjbxxRequest> fyjbxx;

    private List<YrbZlfFcjyxxRequest> fcjycjxx;

    private List<YrbZlfFpxxRequest> zlffpxx;

    private List<YrbZlfCedkxxRequest> cedkxx;

    private List<YrbYwwjxx> yxwjxx;

    private List<YrbZlfQtRequest> qt;

    public List<YrbZlfHouseRequest> getHousevo() {
        return housevo;
    }

    public void setHousevo(List<YrbZlfHouseRequest> housevo) {
        this.housevo = housevo;
    }

    public List<YrbZlfJyfxxRequest> getJyfxx() {
        return jyfxx;
    }

    public void setJyfxx(List<YrbZlfJyfxxRequest> jyfxx) {
        this.jyfxx = jyfxx;
    }

    public List<YrbZlfHyxxRequest> getHyxx() {
        return hyxx;
    }

    public void setHyxx(List<YrbZlfHyxxRequest> hyxx) {
        this.hyxx = hyxx;
    }

    public List<YrbZlfWcnznRequest> getWcnznxx() {
        return wcnznxx;
    }

    public void setWcnznxx(List<YrbZlfWcnznRequest> wcnznxx) {
        this.wcnznxx = wcnznxx;
    }

    public List<YrbZlfFyjbxxRequest> getFyjbxx() {
        return fyjbxx;
    }

    public void setFyjbxx(List<YrbZlfFyjbxxRequest> fyjbxx) {
        this.fyjbxx = fyjbxx;
    }

    public List<YrbZlfFcjyxxRequest> getFcjycjxx() {
        return fcjycjxx;
    }

    public void setFcjycjxx(List<YrbZlfFcjyxxRequest> fcjycjxx) {
        this.fcjycjxx = fcjycjxx;
    }

    public List<YrbZlfFpxxRequest> getZlffpxx() {
        return zlffpxx;
    }

    public void setZlffpxx(List<YrbZlfFpxxRequest> zlffpxx) {
        this.zlffpxx = zlffpxx;
    }

    public List<YrbZlfCedkxxRequest> getCedkxx() {
        return cedkxx;
    }

    public void setCedkxx(List<YrbZlfCedkxxRequest> cedkxx) {
        this.cedkxx = cedkxx;
    }

    public List<YrbYwwjxx> getYxwjxx() {
        return yxwjxx;
    }

    public void setYxwjxx(List<YrbYwwjxx> yxwjxx) {
        this.yxwjxx = yxwjxx;
    }

    public List<YrbZlfQtRequest> getQt() {
        return qt;
    }

    public void setQt(List<YrbZlfQtRequest> qt) {
        this.qt = qt;
    }

    @Override
    public String toString() {
        return "YkqZlfRwjshouseRequest{" +
                "housevo=" + housevo +
                ", jyfxx=" + jyfxx +
                ", hyxx=" + hyxx +
                ", wcnznxx=" + wcnznxx +
                ", fyjbxx=" + fyjbxx +
                ", fcjycjxx=" + fcjycjxx +
                ", zlffpxx=" + zlffpxx +
                ", cedkxx=" + cedkxx +
                ", yxwjxx=" + yxwjxx +
                ", qt=" + qt +
                '}';
    }
}
