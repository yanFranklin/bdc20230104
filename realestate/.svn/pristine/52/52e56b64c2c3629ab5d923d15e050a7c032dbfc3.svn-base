package cn.gtmap.realestate.common.core.dto.exchange.swxx;

import java.util.List;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-08-13
 * @description 主动接收税务推送过来的明细信息
 */
public class ReceiveSwxxRequestDTO {


    // 受理编号
    private String slbh;

    // 完税状态 1代表已完税
    private String wszt;

    // 外网受理编号
    private String wwslbh;

    // 核税信息列表
    private List<QuerySwxxHsxxDTO> hsxxList;


    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public List<QuerySwxxHsxxDTO> getHsxxList() {
        return hsxxList;
    }

    public void setHsxxList(List<QuerySwxxHsxxDTO> hsxxList) {
        this.hsxxList = hsxxList;
    }

    public String getWszt() {
        return wszt;
    }

    public void setWszt(String wszt) {
        this.wszt = wszt;
    }

    public String getWwslbh() {
        return wwslbh;
    }

    public void setWwslbh(String wwslbh) {
        this.wwslbh = wwslbh;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReceiveSwxxRequestDTO{");
        sb.append("slbh='").append(slbh).append('\'');
        sb.append(", wszt='").append(wszt).append('\'');
        sb.append(", wwslbh='").append(wwslbh).append('\'');
        sb.append(", hsxxList=").append(hsxxList);
        sb.append('}');
        return sb.toString();
    }
}
