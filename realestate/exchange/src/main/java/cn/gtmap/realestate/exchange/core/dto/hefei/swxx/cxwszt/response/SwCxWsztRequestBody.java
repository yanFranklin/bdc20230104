package cn.gtmap.realestate.exchange.core.dto.hefei.swxx.cxwszt.response;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-04
 * @description 完税状态查询 响应结果
 */
public class SwCxWsztRequestBody {

    // 1已完税 0 未完税
    private String wsqkjyjg;
    // 未完税的说明信息
    private String message;

    // 出让方
    private List<SwCxWsztRequestJswsxq> crfjswsxq;

    // 受让方
    private List<SwCxWsztRequestJswsxq> srfjswsxq;

    private String fwdz;
    public List<SwCxWsztRequestJswsxq> getCrfjswsxq() {
        return crfjswsxq;
    }

    public void setCrfjswsxq(List<SwCxWsztRequestJswsxq> crfjswsxq) {
        this.crfjswsxq = crfjswsxq;
    }

    public String getWsqkjyjg() {
        return wsqkjyjg;
    }

    public void setWsqkjyjg(String wsqkjyjg) {
        this.wsqkjyjg = wsqkjyjg;
    }

    public List<SwCxWsztRequestJswsxq> getSrfjswsxq() {
        return srfjswsxq;
    }

    public void setSrfjswsxq(List<SwCxWsztRequestJswsxq> srfjswsxq) {
        this.srfjswsxq = srfjswsxq;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFwdz() {
        return fwdz;
    }

    public void setFwdz(String fwdz) {
        this.fwdz = fwdz;
    }
}
