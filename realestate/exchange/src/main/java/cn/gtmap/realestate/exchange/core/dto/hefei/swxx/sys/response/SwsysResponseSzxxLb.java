package cn.gtmap.realestate.exchange.core.dto.hefei.swxx.sys.response;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-26
 * @description
 */
public class SwsysResponseSzxxLb {
    private List<SwsysResponseSzxxGridlb> szxxGridlb;

    public List<SwsysResponseSzxxGridlb> getSzxxGridlb() {
        return szxxGridlb;
    }

    public void setSzxxGridlb(List<SwsysResponseSzxxGridlb> szxxGridlb) {
        this.szxxGridlb = szxxGridlb;
    }
}
