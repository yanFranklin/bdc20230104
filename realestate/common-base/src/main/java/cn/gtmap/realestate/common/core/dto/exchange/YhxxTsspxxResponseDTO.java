package cn.gtmap.realestate.common.core.dto.exchange;

public class YhxxTsspxxResponseDTO {

    /**
     * {
     * "status":"COMPLETE",
     * "code":"000000",
     * "desc":"接口调用成功"
     * }
     */

    /**
     *是否成功
     */
    private String status;

    /**
     * 查询编号
     */
    private String code;

    private String desc;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "YhxxTsspxxResponseDTO{" +
                "status='" + status + '\'' +
                ", code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
