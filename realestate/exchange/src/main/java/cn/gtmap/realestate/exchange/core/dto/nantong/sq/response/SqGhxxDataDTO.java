package cn.gtmap.realestate.exchange.core.dto.nantong.sq.response;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @date 2022/12/30  10:39
 * @description
 */
public class SqGhxxDataDTO {
    // 提交信息 成功：成功，失败：失败原因
    private String message;
    // 提交结果 0:失败 1：成功
    private String result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "SqGhxxDataDTO{" +
                "message='" + message + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
