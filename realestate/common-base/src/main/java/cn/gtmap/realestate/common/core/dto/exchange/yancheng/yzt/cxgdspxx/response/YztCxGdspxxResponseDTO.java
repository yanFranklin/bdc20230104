package cn.gtmap.realestate.common.core.dto.exchange.yancheng.yzt.cxgdspxx.response;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/12/23
 * @description 一张图 查询供地审批信息返回结果
 */
public class YztCxGdspxxResponseDTO {

    private List<YztCxGdspxxResponseDataDTO> dataDTOS;

    private String msg;

    private String result;

    public List<YztCxGdspxxResponseDataDTO> getDataDTOS() {
        return dataDTOS;
    }

    public void setDataDTOS(List<YztCxGdspxxResponseDataDTO> dataDTOS) {
        this.dataDTOS = dataDTOS;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "YztCxGdspxxResponseDTO{" +
                "dataDTOS=" + dataDTOS +
                ", msg='" + msg + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
