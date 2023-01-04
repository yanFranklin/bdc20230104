package cn.gtmap.realestate.exchange.core.dto.nantong.cxcqxx.request;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-20
 * @description 南通自助交互机 产权信息查询接口参数
 */
public class CxCqxxRequestDTO {

    @NotBlank(message = "姓名不能为空")
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
