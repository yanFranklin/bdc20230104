package cn.gtmap.realestate.common.core.dto.natural;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/12/05
 * @description  证书证明打印信息实体
 */
@ApiModel(description = "证书证明打印信息实体")
public class ZrzyZszmPrintDTO {
    @ApiModelProperty(value = "证书类型")
    private Integer zslx;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "证书ID")
    private String zsid;


    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    @Override
    public String toString() {
        return "BdcZszmPrintDTO{" +
                "zslx='" + zslx + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", zsid='" + zsid + '\'' +
                '}';
    }
}
