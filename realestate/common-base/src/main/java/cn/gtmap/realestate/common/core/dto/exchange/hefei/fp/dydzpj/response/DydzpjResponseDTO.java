package cn.gtmap.realestate.common.core.dto.exchange.hefei.fp.dydzpj.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/12/07 15:13
 */
public class DydzpjResponseDTO {

    @ApiModelProperty(value = "服务响应状态 00-成功;01-失败")
    private String status;

    @ApiModelProperty(value = "响应信息")
    private String responseInfo;

    @ApiModelProperty(value = "票据循环域")
    private Object imgConts;

    @ApiModelProperty(value = "序号")
    private String sn;

    @ApiModelProperty(value = "票据base64值")
    private String imgCont;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseInfo() {
        return responseInfo;
    }

    public void setResponseInfo(String responseInfo) {
        this.responseInfo = responseInfo;
    }

    public Object getImgConts() {
        return imgConts;
    }

    public void setImgConts(Object imgConts) {
        this.imgConts = imgConts;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getImgCont() {
        return imgCont;
    }

    public void setImgCont(String imgCont) {
        this.imgCont = imgCont;
    }

    @Override
    public String toString() {
        return "DydzpjResponseDTO{" +
                "status='" + status + '\'' +
                ", responseInfo='" + responseInfo + '\'' +
                ", imgConts='" + imgConts + '\'' +
                ", sn='" + sn + '\'' +
                ", imgCont='" + imgCont + '\'' +
                '}';
    }
}
