package cn.gtmap.realestate.common.core.dto.exchange.ykq.dzfpmx.response;

import java.util.List;

public class YkqDzfpmxDTO {


    /**
     * dzpjph :
     * kjrq :
     * fjxx : [{"fjmc":"","fjdx":"","fjnr":"","fjlx":""}]
     */

    private String dzpjph;
    private String kjrq;
    private List<YkqFpFjxxDTO> fjxx;

    public String getDzpjph() {
        return dzpjph;
    }

    public void setDzpjph(String dzpjph) {
        this.dzpjph = dzpjph;
    }

    public String getKjrq() {
        return kjrq;
    }

    public void setKjrq(String kjrq) {
        this.kjrq = kjrq;
    }

    public List<YkqFpFjxxDTO> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<YkqFpFjxxDTO> fjxx) {
        this.fjxx = fjxx;
    }

    @Override
    public String toString() {
        return "YkqDzfpmxDTO{" +
                "dzpjph='" + dzpjph + '\'' +
                ", kjrq='" + kjrq + '\'' +
                ", fjxx=" + fjxx +
                '}';
    }
}
