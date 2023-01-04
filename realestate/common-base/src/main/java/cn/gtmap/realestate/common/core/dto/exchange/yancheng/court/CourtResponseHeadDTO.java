package cn.gtmap.realestate.common.core.dto.exchange.yancheng.court;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/12/09 08:36
 */
public class CourtResponseHeadDTO {

    @ApiModelProperty(value = "响应消息")
    private String msg;

    @ApiModelProperty(value = "响应代码 0000:成功 1000:数据查询失败 2000:用户名密码验证错误 2010:安全token错误")
    private Integer returnCode;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    @Override
    public String toString() {
        return "CourtResponseHeadDTO{" +
                "msg='" + msg + '\'' +
                ", returnCode=" + returnCode +
                '}';
    }
}
