package cn.gtmap.realestate.exchange.core.dto.nantong.sq.response.qi;

import cn.gtmap.realestate.exchange.core.dto.nantong.sq.response.AbstractResponse;

/**
 * @Author  <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description 检查户号返回消息体
 * @Date 2022/4/27
 **/
public class QiCheckConsnoDTo extends AbstractResponse {
    /**
     * code : 0000
     * msg : 成功
     * data : {"message":"提交电费过户申请成功！","this_ymd":"2020-12-31 00:00:00","app_no":"3002154870123","result":"1"}
     */

    private QiCheckConsnoDataDTo data;

    public QiCheckConsnoDataDTo getData() {
        return data;
    }

    public void setData(QiCheckConsnoDataDTo data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "QiCheckConsnoDataDTo{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
