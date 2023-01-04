package cn.gtmap.realestate.exchange.core.dto.zzcxj;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/5/7.
 * @description
 */

public class GetCurrentTimeResponseDTO {
    /**
     * 执行是否成功
     */
    protected boolean success;
    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
