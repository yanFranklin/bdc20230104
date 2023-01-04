package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/22
 * @description 交易信息返回结果
 */
public class JyxxResponseDTO<T> {

    @ApiModelProperty(value = "交易数据：包含数据库和非数据库数据")
    private Map<String, Object> htxx;

    @ApiModelProperty(value = "买受人信息")
    private List<T> msrxx;

    @ApiModelProperty(value = "买受人出卖人读取数据来源：1：第三权利人 2：受理申请人 3：登记权利人")
    private String qlrsjly;

    @ApiModelProperty(value = "出卖人信息")
    private List<T> cmrxx;

    public Map<String, Object> getHtxx() {
        return htxx;
    }

    public void setHtxx(Map<String, Object> htxx) {
        this.htxx = htxx;
    }

    public String getQlrsjly() {
        return qlrsjly;
    }

    public void setQlrsjly(String qlrsjly) {
        this.qlrsjly = qlrsjly;
    }

    public List<T> getMsrxx() {
        return msrxx;
    }

    public void setMsrxx(List<T> msrxx) {
        this.msrxx = msrxx;
    }

    public List<T> getCmrxx() {
        return cmrxx;
    }

    public void setCmrxx(List<T> cmrxx) {
        this.cmrxx = cmrxx;
    }
}
