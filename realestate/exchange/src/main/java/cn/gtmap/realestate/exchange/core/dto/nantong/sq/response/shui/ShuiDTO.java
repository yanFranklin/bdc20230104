package cn.gtmap.realestate.exchange.core.dto.nantong.sq.response.shui;

import cn.gtmap.realestate.exchange.core.dto.nantong.sq.response.AbstractResponse;

public class ShuiDTO extends AbstractResponse {

    /**
     * code : 0000
     * msg : 成功
     * data : {"message":"提交电费过户申请成功！","this_ymd":"2020-12-31 00:00:00","app_no":"3002154870123","result":"1"}
     */


    private ShuiGhDataDTO data;


    public ShuiGhDataDTO getData() {
        return data;
    }

    public void setData(ShuiGhDataDTO data) {
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
