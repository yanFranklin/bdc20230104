package cn.gtmap.realestate.exchange.core.dto.wwsq.jtBdcxx.request;

import java.util.List;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 1.0, 2022/8/3 8:54
 * @description 家庭成员信息DB入参
 */
public class JtcyxxReqDTO {

    /**
     * 证件号码
     */
    private List<String> zjhList;

    /**
     * 证件类型
     */
    private List<String> zjlxList;

    /**
     * 姓名
     */
    private List<String> qlrList;

    /**
     * 权利类型
     */
    private List<Integer> qllxList;

    public List<String> getZjhList() {
        return zjhList;
    }

    public void setZjhList(List<String> zjhList) {
        this.zjhList = zjhList;
    }

    public List<String> getZjlxList() {
        return zjlxList;
    }

    public void setZjlxList(List<String> zjlxList) {
        this.zjlxList = zjlxList;
    }

    public List<String> getQlrList() {
        return qlrList;
    }

    public void setQlrList(List<String> qlrList) {
        this.qlrList = qlrList;
    }

    public List<Integer> getQllxList() {
        return qllxList;
    }

    public void setQllxList(List<Integer> qllxList) {
        this.qllxList = qllxList;
    }

    @Override
    public String toString() {
        return "JtcyxxReqDTO{" +
                "zjhList=" + zjhList +
                ", zjlxList=" + zjlxList +
                ", qlrList=" + qlrList +
                ", qllxList=" + qllxList +
                '}';
    }
}
