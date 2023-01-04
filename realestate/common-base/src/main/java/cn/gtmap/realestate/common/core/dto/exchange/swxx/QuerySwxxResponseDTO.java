package cn.gtmap.realestate.common.core.dto.exchange.swxx;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-11
 * @description 标准版 税务信息查询响应结果
 */
public class QuerySwxxResponseDTO {

    // 响应码
    private String responseCode;

    // 响应信息
    private String responseMsg;

    // 完税状态 1代表已完税
    private String wszt;

    // 房屋编码-税务返回
    private String fwuuid;

    // 经办人信息
    private QuerySwxxJbrxxDTO jbrxx;

    // 核税信息列表
    private List<QuerySwxxHsxxDTO> hsxxList;

    @ApiModelProperty(value = "申报提醒函附件地址")
    private String sbtxhfj;

    @ApiModelProperty(value = "企业简易征收标志")
    private String qyjyzsbz;

    @ApiModelProperty(value = "项目id")
    private String xmid;

    @ApiModelProperty(value = "并案业务标记：并案业务为1，单个业务不传或为0")
    private String mergebz;

    public String getQyjyzsbz() {
        return qyjyzsbz;
    }

    public void setQyjyzsbz(String qyjyzsbz) {
        this.qyjyzsbz = qyjyzsbz;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public QuerySwxxJbrxxDTO getJbrxx() {
        return jbrxx;
    }

    public void setJbrxx(QuerySwxxJbrxxDTO jbrxx) {
        this.jbrxx = jbrxx;
    }

    public List<QuerySwxxHsxxDTO> getHsxxList() {
        return hsxxList;
    }

    public void setHsxxList(List<QuerySwxxHsxxDTO> hsxxList) {
        this.hsxxList = hsxxList;
    }

    public String getWszt() {
        return wszt;
    }

    public void setWszt(String wszt) {
        this.wszt = wszt;
    }

    public String getFwuuid() {
        return fwuuid;
    }

    public void setFwuuid(String fwuuid) {
        this.fwuuid = fwuuid;
    }

    public String getSbtxhfj() {
        return sbtxhfj;
    }

    public void setSbtxhfj(String sbtxhfj) {
        this.sbtxhfj = sbtxhfj;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getMergebz() {
        return mergebz;
    }

    public void setMergebz(String mergebz) {
        this.mergebz = mergebz;
    }

    @Override
    public String toString() {
        return "QuerySwxxResponseDTO{" +
                "responseCode='" + responseCode + '\'' +
                ", responseMsg='" + responseMsg + '\'' +
                ", wszt='" + wszt + '\'' +
                ", fwuuid='" + fwuuid + '\'' +
                ", jbrxx=" + jbrxx +
                ", hsxxList=" + hsxxList +
                ", sbtxhfj='" + sbtxhfj + '\'' +
                ", qyjyzsbz='" + qyjyzsbz + '\'' +
                ", xmid='" + xmid + '\'' +
                ", mergebz='" + mergebz + '\'' +
                '}';
    }
}
