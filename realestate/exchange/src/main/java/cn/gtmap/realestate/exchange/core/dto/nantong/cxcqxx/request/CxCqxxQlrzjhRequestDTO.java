package cn.gtmap.realestate.exchange.core.dto.nantong.cxcqxx.request;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-09-16
 * @description 只限制证件号码不能为空，姓名不做限制
 */
public class CxCqxxQlrzjhRequestDTO {

    private String xm;

    @NotBlank(message = "证件号码不能为空")
    private String zjhm;

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }
}
