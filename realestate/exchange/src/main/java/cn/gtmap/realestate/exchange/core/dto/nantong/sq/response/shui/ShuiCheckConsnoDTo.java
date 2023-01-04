package cn.gtmap.realestate.exchange.core.dto.nantong.sq.response.shui;

import cn.gtmap.realestate.exchange.core.dto.nantong.sq.response.AbstractResponse;

/**
 * @Author  <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description 检查户号返回消息体
 * @Date 2022/4/27
 **/
public class ShuiCheckConsnoDTo extends AbstractResponse {
    /**
     * {
     * "code":"0000",
     * "msg":"成功",
     * "data":
     * {
     * "electricFeeAddr":"****滨新村一期8栋14号车库",
     * "arrearage":"118",
     * "electricFeeName":"田玲**",
     * "electricFeeNum":"1000836333",
     * "this_ymd":"2013-01-04 08:14:35.0",
     * "arrearageMessage":"获取欠费信息成功！",
     * }
     * }
     */

    private ShuiGhCheckConsnoDataDTO data;

    public ShuiGhCheckConsnoDataDTO getData() {
        return data;
    }

    public void setData(ShuiGhCheckConsnoDataDTO data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ShuiCheckConsnoDataDTo{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
