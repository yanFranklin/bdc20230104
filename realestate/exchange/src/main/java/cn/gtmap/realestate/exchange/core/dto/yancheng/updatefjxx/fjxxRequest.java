package cn.gtmap.realestate.exchange.core.dto.yancheng.updatefjxx;

import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTO;

import java.util.List;

public class fjxxRequest {

    private String slbh;

    private List<FjclDTO> fjxx;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public List<FjclDTO> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<FjclDTO> fjxx) {
        this.fjxx = fjxx;
    }

    @Override
    public String toString() {
        return "fjxxRequest{" +
                "slbh='" + slbh + '\'' +
                ", fjxx=" + fjxx +
                '}';
    }
}
