package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.police.idcheck;

import cn.gtmap.realestate.common.util.ParamUtil;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0
 * @date 2021/08/04 09:45
 * @description 3.12公安部-人口库身份核查接口
 */
public class PoliceCheckIdRequestDTO {

    @ApiModelProperty(value = "姓名")
    private String xm;

    @ApiModelProperty(value = "身份证号")
    private String sfzh;

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public void checkParam(){
        ParamUtil.nonNull(this.sfzh, "姓名不能为空");
        ParamUtil.nonNull(this.xm,"身份证号不能为空");
    }


}

