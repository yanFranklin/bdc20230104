package cn.gtmap.realestate.common.core.dto.accept;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @program: realestate
 * @description: 自助查询机收费信息页面数据DTO
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2019-07-17 10:31
 **/
@ApiModel(value = "BdcSlSfxxZzcxjResponseDTO", description = "受理收费信息页面数据")
public class BdcSlSfxxZzcxjResponseDTO implements Serializable {

    @ApiModelProperty(value = "业务号")
    private String YWH;

    @ApiModelProperty(value = "权力类型")
    private String QLLX;

    @ApiModelProperty(value = "受理单名称")
    private String SLDMC;

    @ApiModelProperty(value = "受理号")
    private String SLH;

    @ApiModelProperty(value = "登记类型")
    private String SQLXMC;

    @ApiModelProperty(value = "权利人")
    private String QLR;

    @ApiModelProperty(value = "不动产单元号")
    private String BDCDYH;

    @ApiModelProperty(value = "SCJZMJ")
    private Double SCJZMJ;

    @ApiModelProperty(value = "ROWNUM_")
    private Integer ROWNUM_;

    @ApiModelProperty(value = "用途")
    private String YT;

    @ApiModelProperty(value = "义务人")
    private String YWR;

    @ApiModelProperty(value = "坐落")
    private String ZL;

    @ApiModelProperty(value = "收件时间")
    private String SJSJ;

    @ApiModelProperty(value = "分摊土地面积")
    private Double FTTDMJ;

    @ApiModelProperty(value = "当前节点")
    private String JDMC;

    @ApiModelProperty(value = "生成收费信息返回的msg:")
    private String SFXXMSG;

    @ApiModelProperty(value = "收费项目信息")
    private List<BdcSfxmxxDTO> SFXMXX;

    @JSONField(name = "YWH")
    public String getYWH() {
        return YWH;
    }

    public void setYWH(String YWH) {
        this.YWH = YWH;
    }

    @JSONField(name = "QLLX")
    public String getQLLX() {
        return QLLX;
    }

    public void setQLLX(String QLLX) {
        this.QLLX = QLLX;
    }

    @JSONField(name = "SLDMC")
    public String getSLDMC() {
        return SLDMC;
    }

    public void setSLDMC(String SLDMC) {
        this.SLDMC = SLDMC;
    }

    @JSONField(name = "SLH")
    public String getSLH() {
        return SLH;
    }

    public void setSLH(String SLH) {
        this.SLH = SLH;
    }

    @JSONField(name = "SQLXMC")
    public String getSQLXMC() {
        return SQLXMC;
    }

    public void setSQLXMC(String SQLXMC) {
        this.SQLXMC = SQLXMC;
    }

    @JSONField(name = "QLR")
    public String getQLR() {
        return QLR;
    }

    public void setQLR(String QLR) {
        this.QLR = QLR;
    }

    @JSONField(name = "BDCDYH")
    public String getBDCDYH() {
        return BDCDYH;
    }

    public void setBDCDYH(String BDCDYH) {
        this.BDCDYH = BDCDYH;
    }

    @JSONField(name = "SCJZMJ")
    public Double getSCJZMJ() {
        return SCJZMJ;
    }

    public void setSCJZMJ(Double SCJZMJ) {
        this.SCJZMJ = SCJZMJ;
    }

    @JSONField(name = "ROWNUM_")
    public Integer getROWNUM_() {
        return ROWNUM_;
    }

    public void setROWNUM_(Integer ROWNUM_) {
        this.ROWNUM_ = ROWNUM_;
    }

    @JSONField(name = "YT")
    public String getYT() {
        return YT;
    }

    public void setYT(String YT) {
        this.YT = YT;
    }

    @JSONField(name = "YWR")
    public String getYWR() {
        return YWR;
    }

    public void setYWR(String YWR) {
        this.YWR = YWR;
    }

    @JSONField(name = "ZL")
    public String getZL() {
        return ZL;
    }

    public void setZL(String ZL) {
        this.ZL = ZL;
    }

    @JSONField(name = "SJSJ")
    public String getSJSJ() {
        return SJSJ;
    }

    public void setSJSJ(String SJSJ) {
        this.SJSJ = SJSJ;
    }

    @JSONField(name = "FTTDMJ")
    public Double getFTTDMJ() {
        return FTTDMJ;
    }

    public void setFTTDMJ(Double FTTDMJ) {
        this.FTTDMJ = FTTDMJ;
    }

    @JSONField(name = "JDMC")
    public String getJDMC() {
        return JDMC;
    }

    public void setJDMC(String JDMC) {
        this.JDMC = JDMC;
    }

    @JSONField(name = "SFXXMSG")
    public String getSFXXMSG() {
        return SFXXMSG;
    }

    public void setSFXXMSG(String SFXXMSG) {
        this.SFXXMSG = SFXXMSG;
    }

    @JSONField(name = "SFXMXX")
    public List<BdcSfxmxxDTO> getSFXMXX() {
        return SFXMXX;
    }

    public void setSFXMXX(List<BdcSfxmxxDTO> SFXMXX) {
        this.SFXMXX = SFXMXX;
    }

    @Override
    public String toString() {
        return "BdcSlSfxxZzcxjResponseDTO{" +
                "YWH='" + YWH + '\'' +
                ", QLLX='" + QLLX + '\'' +
                ", SLDMC='" + SLDMC + '\'' +
                ", SLH='" + SLH + '\'' +
                ", SQLXMC='" + SQLXMC + '\'' +
                ", QLR='" + QLR + '\'' +
                ", BDCDYH='" + BDCDYH + '\'' +
                ", SCJZMJ=" + SCJZMJ +
                ", ROWNUM_=" + ROWNUM_ +
                ", YT='" + YT + '\'' +
                ", YWR='" + YWR + '\'' +
                ", ZL='" + ZL + '\'' +
                ", SJSJ='" + SJSJ + '\'' +
                ", FTTDMJ=" + FTTDMJ +
                ", JDMC='" + JDMC + '\'' +
                ", SFXXMSG='" + SFXXMSG + '\'' +
                ", SFXMXX=" + SFXMXX +
                '}';
    }
}


