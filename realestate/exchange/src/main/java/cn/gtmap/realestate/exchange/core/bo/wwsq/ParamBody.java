package cn.gtmap.realestate.exchange.core.bo.wwsq;

import java.util.Map;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/5/4.
 * @description
 */
public class ParamBody {
    private Map<String,Object> head;
    private Map<String,Object> data;
    private Map<String,Object> pageinfo;

    public Map<String, Object> getHead() {
        return head;
    }

    public void setHead(Map<String, Object> head) {
        this.head = head;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getPageinfo() {
        return pageinfo;
    }

    public void setPageinfo(Map<String, Object> pageinfo) {
        this.pageinfo = pageinfo;
    }

    @Override
    public String toString() {
        return "ParamBody{" +
                "head=" + head +
                ", data=" + data +
                ", pageinfo=" + pageinfo +
                '}';
    }
}
