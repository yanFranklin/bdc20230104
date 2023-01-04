package cn.gtmap.realestate.exchange.core.dto.bjjk.shzz.response;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-02
 * @description
 */
public class BjjkShzzResponseData {
    private String count;

    private List<BjjkShzzResponseRows> rows;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<BjjkShzzResponseRows> getRows() {
        return rows;
    }

    public void setRows(List<BjjkShzzResponseRows> rows) {
        this.rows = rows;
    }
}
