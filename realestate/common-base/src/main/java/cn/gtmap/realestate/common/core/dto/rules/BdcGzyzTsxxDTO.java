package cn.gtmap.realestate.common.core.dto.rules;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/10/20
 * @description 不动产规则验证提示信息
 */
@ApiModel(value = "BdcGzyzTsxxDTO", description = "不动产规则验证信息")
public class BdcGzyzTsxxDTO implements Serializable {

    private static final long serialVersionUID = 7280020555258025179L;
    @ApiModelProperty(value = "验证项数据主键")
    private String yzxsjid;

    @ApiModelProperty(value = "提示信息")
    private String tsxx;

    @ApiModelProperty(value = "验证模式")
    private String yzms;

    public String getYzxsjid() {
        return yzxsjid;
    }

    public void setYzxsjid(String yzxsjid) {
        this.yzxsjid = yzxsjid;
    }

    public String getTsxx() {
        return tsxx;
    }

    public void setTsxx(String tsxx) {
        this.tsxx = tsxx;
    }

    public String getYzms() {
        return yzms;
    }

    public void setYzms(String yzms) {
        this.yzms = yzms;
    }

    @Override
    public String toString() {
        return "BdcGzyzTsxxDTO{" +
                "yzxsjid='" + yzxsjid + '\'' +
                ", tsxx='" + tsxx + '\'' +
                ", yzms='" + yzms + '\'' +
                '}';
    }
}
