package cn.gtmap.realestate.exchange.core.dto.nantong.dian.response;

public class DianHxxDTO {

    /**
     * code : 0000
     * msg : 成功
     * data : {"meter_period":"单月","electricFeeAddr":"****滨新村一期8栋14号车库","arrearage":"118","electricFeeName":"田玲**","electricFeeNum":"1000836333","this_ymd":"2013-01-04 08:14:35.0","arrearageMessage":"获取欠费信息成功！","contract_cap":"8"}
     */

    private String code;
    private String msg;
    private HxxDataDTO data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HxxDataDTO getData() {
        return data;
    }

    public void setData(HxxDataDTO data) {
        this.data = data;
    }
}
