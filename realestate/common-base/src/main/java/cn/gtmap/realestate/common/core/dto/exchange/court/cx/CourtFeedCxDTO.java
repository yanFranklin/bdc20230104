package cn.gtmap.realestate.common.core.dto.exchange.court.cx;

import cn.gtmap.realestate.common.core.dto.exchange.court.ywxxcx.*;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.ywxxcx.*;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

public class CourtFeedCxDTO {
//    @XmlElement(name = "BDCCXJG")
//    String BDCCXJG;
    @XmlElement(name = "CXQQDH")
    String CXQQDH;
    @XmlElement(name = "RESULT")
    String RESULT;
    @XmlElement(name = "MESSAGE")
    String MESSAGE;

    @XmlElement(name = "TDSYQLIST")
    @ApiModelProperty(value = "土地所有权信息")
    List<CourtCxTdsyqDTO> tdsyqList = new ArrayList<>();

    @XmlElement(name = "JSYDSYQLIST")
    @ApiModelProperty(value = "建设用地使用权、宅基地使用权登记信息")
    List<CourtCxJsydsyqDTO> jsydsyqList = new ArrayList<>();

    @XmlElement(name = "FDCQLIST")
    @ApiModelProperty(value = "房地产权登记信息")
    List<CourtCxFdcqDTO> fdcqList = new ArrayList<>();

    @XmlElement(name = "HYSYQLIST")
    @ApiModelProperty(value = "海域（含无居民海岛）使用权登记信息")
    List<CourtCxHysyqDTO> hysyqList = new ArrayList<>();

    @XmlElement(name = "GJZWSYQLIST")
    @ApiModelProperty(value = "构（建）筑物所有权登记信息")
    List<CourtCxGjzwsyqDTO> gjzwsyqList = new ArrayList<>();

    @XmlElement(name = "LQLIST")
    @ApiModelProperty(value = "林权登记信息")
    List<CourtCxLqDTO> lqList = new ArrayList<>();

    @XmlElement(name = "DYAQLIST")
    @ApiModelProperty(value = "抵押权登记信息")
    List<CourtCxDyaqDTO> dyaqList = new ArrayList<>();

    @XmlElement(name = "YGDJLIST")
    @ApiModelProperty(value = "预告登记信息")
    List<CourtCxYgDTO> ygdjList = new ArrayList<>();

    @XmlElement(name = "CFDJLIST")
    @ApiModelProperty(value = "查封登记信息")
    List<CourtCxCfDTO> cfdjList = new ArrayList<>();

    public void setCXQQDH(String CXQQDH) {
        this.CXQQDH = CXQQDH;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public void setTdsyqList(List<CourtCxTdsyqDTO> tdsyqList) {
        this.tdsyqList = tdsyqList;
    }

    public void setJsydsyqList(List<CourtCxJsydsyqDTO> jsydsyqList) {
        this.jsydsyqList = jsydsyqList;
    }

    public void setFdcqList(List<CourtCxFdcqDTO> fdcqList) {
        this.fdcqList = fdcqList;
    }

    public void setHysyqList(List<CourtCxHysyqDTO> hysyqList) {
        this.hysyqList = hysyqList;
    }

    public void setGjzwsyqList(List<CourtCxGjzwsyqDTO> gjzwsyqList) {
        this.gjzwsyqList = gjzwsyqList;
    }

    public void setLqList(List<CourtCxLqDTO> lqList) {
        this.lqList = lqList;
    }

    public void setDyaqList(List<CourtCxDyaqDTO> dyaqList) {
        this.dyaqList = dyaqList;
    }

    public void setYgdjList(List<CourtCxYgDTO> ygdjList) {
        this.ygdjList = ygdjList;
    }

    public void setCfdjList(List<CourtCxCfDTO> cfdjList) {
        this.cfdjList = cfdjList;
    }
}
