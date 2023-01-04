package cn.gtmap.realestate.exchange.core.dto.yancheng.yth.respones;

import java.util.List;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2020/12/9 15:45
 * @description
 */
public class YthYrcfResponse {
    private List<YthYrcfFwxx> fwxx;

    public List<YthYrcfFwxx> getFwxx() {
        return fwxx;
    }

    public void setFwxx(List<YthYrcfFwxx> fwxx) {
        this.fwxx = fwxx;
    }
}
