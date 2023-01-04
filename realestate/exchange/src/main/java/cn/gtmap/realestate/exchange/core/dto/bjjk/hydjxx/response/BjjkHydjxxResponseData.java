package cn.gtmap.realestate.exchange.core.dto.bjjk.hydjxx.response;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-02
 * @description
 */
public class BjjkHydjxxResponseData {

    private String count;

    private List<BjjkHydjxxResponseRows> rows;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<BjjkHydjxxResponseRows> getRows() {
        return rows;
    }

    public void setRows(List<BjjkHydjxxResponseRows> rows) {
        this.rows = rows;
    }
}
