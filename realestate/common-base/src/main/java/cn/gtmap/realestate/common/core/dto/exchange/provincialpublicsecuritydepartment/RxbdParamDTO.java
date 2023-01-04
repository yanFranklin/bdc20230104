package cn.gtmap.realestate.common.core.dto.exchange.provincialpublicsecuritydepartment;

import io.swagger.annotations.ApiModelProperty;

/**
 * @program: bdcdj3.0
 * @description: 人像比对接口入参DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-10-10 16:48
 **/
public class RxbdParamDTO {

    @ApiModelProperty("公民身份证号码")
    private String gmsfhm;
    @ApiModelProperty("姓名")
    private String xm;
    @ApiModelProperty("图像base64")
    private String img;

    public String getGmsfhm() {
        return gmsfhm;
    }

    public void setGmsfhm(String gmsfhm) {
        this.gmsfhm = gmsfhm;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "RxbdParamDTO{" +
                "gmsfhm='" + gmsfhm + '\'' +
                ", xm='" + xm + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
