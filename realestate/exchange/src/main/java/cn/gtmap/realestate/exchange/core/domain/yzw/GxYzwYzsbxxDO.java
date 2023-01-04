package cn.gtmap.realestate.exchange.core.domain.yzw;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/23
 * @description 共享一张网验证信息
 */
@Table(name = "GX_YZW_YZSBXX")
public class GxYzwYzsbxxDO {

    @Id
    @ApiModelProperty(value = "验证信息ID")
    private String yzxxid;

    @ApiModelProperty(value = "推送信息ID")
    private String tsxxid;

    @ApiModelProperty(value = "验证类型")
    private String yzlx;

    public String getYzxxid() {
        return yzxxid;
    }

    public void setYzxxid(String yzxxid) {
        this.yzxxid = yzxxid;
    }

    public String getTsxxid() {
        return tsxxid;
    }

    public void setTsxxid(String tsxxid) {
        this.tsxxid = tsxxid;
    }

    public String getYzlx() {
        return yzlx;
    }

    public void setYzlx(String yzlx) {
        this.yzlx = yzlx;
    }
}
