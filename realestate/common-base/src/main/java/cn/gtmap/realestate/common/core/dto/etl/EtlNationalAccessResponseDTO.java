package cn.gtmap.realestate.common.core.dto.etl;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-25
 * @description ETL 处理外市县汇交上报 响应结果
 */
public class EtlNationalAccessResponseDTO {

    private boolean success;

    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
