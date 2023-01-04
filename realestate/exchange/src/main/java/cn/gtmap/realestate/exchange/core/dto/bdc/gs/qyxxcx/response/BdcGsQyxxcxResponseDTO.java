package cn.gtmap.realestate.exchange.core.dto.bdc.gs.qyxxcx.response;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-05
 * @description 为不动产提供 企业信息查询接口
 */
public class BdcGsQyxxcxResponseDTO {

    // 接口响应信息
    private String msg;

    // 接口响应码
    private String code;

    private BdcGsQyxxcxQyxxDTO qyxx;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BdcGsQyxxcxQyxxDTO getQyxx() {
        return qyxx;
    }

    public void setQyxx(BdcGsQyxxcxQyxxDTO qyxx) {
        this.qyxx = qyxx;
    }
}
