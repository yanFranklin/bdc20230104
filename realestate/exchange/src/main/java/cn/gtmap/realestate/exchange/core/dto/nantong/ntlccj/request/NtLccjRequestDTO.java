package cn.gtmap.realestate.exchange.core.dto.nantong.ntlccj.request;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-02-04
 * @description
 */
public class NtLccjRequestDTO {

    private List<NtLccjRequestSqxx> sqxx;

    private List<NtLccjRequestFjxx> fjxx;

    public List<NtLccjRequestSqxx> getSqxx() {
        return sqxx;
    }

    public void setSqxx(List<NtLccjRequestSqxx> sqxx) {
        this.sqxx = sqxx;
    }

    public List<NtLccjRequestFjxx> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<NtLccjRequestFjxx> fjxx) {
        this.fjxx = fjxx;
    }
}
