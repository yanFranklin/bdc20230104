package cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.ywxxcx;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/12/07 15:13
 */
public class CourtYwxxcxResponseDTO {

    @ApiModelProperty(value = "土地所有权信息")
    private List<CourtYwxxcxResponseTdsyqDTO> tdsyqList;

    @ApiModelProperty(value = "建设用地使用权、宅基地使用权登记信息")
    private List<CourtYwxxcxResponseJsydsyqDTO> jsydsyqList;

    @ApiModelProperty(value = "房地产权登记信息")
    private List<CourtYwxxcxResponseFdcqDTO> fdcqList;

    @ApiModelProperty(value = "海域（含无居民海岛）使用权登记信息")
    private List<CourtYwxxcxResponseHysyqDTO> hysyqList;

    @ApiModelProperty(value = "构（建）筑物所有权登记信息")
    private List<CourtYwxxcxResponseGjzwsyqDTO> gjzwsyqList;

    @ApiModelProperty(value = "林权登记信息")
    private List<CourtYwxxcxResponseLqDTO> lqList;

    @ApiModelProperty(value = "抵押权登记信息")
    private List<CourtYwxxcxResponseDyaqDTO> dyaqList;

    @ApiModelProperty(value = "预告登记信息")
    private List<CourtYwxxcxResponseYgDTO> ygdjList;

    @ApiModelProperty(value = "查封登记信息")
    private List<CourtYwxxcxResponseCfDTO> cfdjList;

    public List<CourtYwxxcxResponseTdsyqDTO> getTdsyqList() {
        return tdsyqList;
    }

    public void setTdsyqList(List<CourtYwxxcxResponseTdsyqDTO> tdsyqList) {
        this.tdsyqList = tdsyqList;
    }

    public List<CourtYwxxcxResponseJsydsyqDTO> getJsydsyqList() {
        return jsydsyqList;
    }

    public void setJsydsyqList(List<CourtYwxxcxResponseJsydsyqDTO> jsydsyqList) {
        this.jsydsyqList = jsydsyqList;
    }

    public List<CourtYwxxcxResponseFdcqDTO> getFdcqList() {
        return fdcqList;
    }

    public void setFdcqList(List<CourtYwxxcxResponseFdcqDTO> fdcqList) {
        this.fdcqList = fdcqList;
    }

    public List<CourtYwxxcxResponseHysyqDTO> getHysyqList() {
        return hysyqList;
    }

    public void setHysyqList(List<CourtYwxxcxResponseHysyqDTO> hysyqList) {
        this.hysyqList = hysyqList;
    }

    public List<CourtYwxxcxResponseGjzwsyqDTO> getGjzwsyqList() {
        return gjzwsyqList;
    }

    public void setGjzwsyqList(List<CourtYwxxcxResponseGjzwsyqDTO> gjzwsyqList) {
        this.gjzwsyqList = gjzwsyqList;
    }

    public List<CourtYwxxcxResponseLqDTO> getLqList() {
        return lqList;
    }

    public void setLqList(List<CourtYwxxcxResponseLqDTO> lqList) {
        this.lqList = lqList;
    }

    public List<CourtYwxxcxResponseDyaqDTO> getDyaqList() {
        return dyaqList;
    }

    public void setDyaqList(List<CourtYwxxcxResponseDyaqDTO> dyaqList) {
        this.dyaqList = dyaqList;
    }

    public List<CourtYwxxcxResponseYgDTO> getYgdjList() {
        return ygdjList;
    }

    public void setYgdjList(List<CourtYwxxcxResponseYgDTO> ygdjList) {
        this.ygdjList = ygdjList;
    }

    public List<CourtYwxxcxResponseCfDTO> getCfdjList() {
        return cfdjList;
    }

    public void setCfdjList(List<CourtYwxxcxResponseCfDTO> cfdjList) {
        this.cfdjList = cfdjList;
    }

    @Override
    public String toString() {
        return "CourtYwxxcxResponseDTO{" +
                ", tdsyqList=" + tdsyqList +
                ", jsydsyqList=" + jsydsyqList +
                ", fdcqList=" + fdcqList +
                ", hysyqList=" + hysyqList +
                ", gjzwsyqList=" + gjzwsyqList +
                ", lqList=" + lqList +
                ", dyaqList=" + dyaqList +
                ", ygdjList=" + ygdjList +
                ", cfdjList=" + cfdjList +
                '}';
    }
}
