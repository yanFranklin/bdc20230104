package cn.gtmap.realestate.common.core.qo.natural;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyh
 * @version 1.0
 * @date 2021/10/26 9:48
 */
@Data
@ApiModel(value = "ZrzyXmListQO", description = "自然资源单元信息列表查询请求")
public class ZrzyXmListQO {
    @ApiModelProperty(value = "自然资源登记单元号")
    String zrzydjdyh;

    @ApiModelProperty(value = "坐落")
    String zl;

    @ApiModelProperty(value = "登记原因")
    String djyy;

    @ApiModelProperty(value = "实例id")
    String gzlslid;

    public String getZrzydjdyh() {
        return zrzydjdyh;
    }

    public void setZrzydjdyh(String zrzydjdyh) {
        this.zrzydjdyh = zrzydjdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }
}
