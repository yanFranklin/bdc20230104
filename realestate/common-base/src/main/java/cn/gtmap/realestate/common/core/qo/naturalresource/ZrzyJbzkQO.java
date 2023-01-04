package cn.gtmap.realestate.common.core.qo.naturalresource;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wyh
 * @version 1.0
 * @date 2021/10/26 9:48
 */
@Data
public class ZrzyJbzkQO {
    @ApiModelProperty(value = "要素代码")
    String ysdm;

    @ApiModelProperty(value = "坐落")
    String zl;

    @ApiModelProperty(value = "自然资源登记单元名称")
    String mc;

    public String getYsdm() {
        return ysdm;
    }

    public void setYsdm(String ysdm) {
        this.ysdm = ysdm;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }
}
