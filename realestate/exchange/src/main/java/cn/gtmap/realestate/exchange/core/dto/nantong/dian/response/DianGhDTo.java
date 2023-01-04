package cn.gtmap.realestate.exchange.core.dto.nantong.dian.response;

public class DianGhDTo {

    /**
     * code : 0000
     * msg : 成功
     * data : {"message":"提交电费过户申请成功！","this_ymd":"2020-12-31 00:00:00","app_no":"3002154870123","result":"1"}
     */

    private String code;
    private String msg;
    private DianghDataDTO data;

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

    public DianghDataDTO getData() {
        return data;
    }

    public void setData(DianghDataDTO data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DianGhDTo{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
