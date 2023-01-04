package cn.gtmap.realestate.common.core.dto.certificate.eCertificate;

import io.swagger.annotations.ApiModelProperty;

/**
 * 常州签章参数 DTO
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 */
public class DzqzEZsDTO extends EZsTimeStampDTO {
    private DzqzPositionDTO position;
    private DzqzSealDTO seal;

    @ApiModelProperty("扫码查图")
    private String smct;
    @ApiModelProperty("gglx")
    private Integer gglx;
    @ApiModelProperty("公告pdfbyte")
    private byte[] pdfByte;

    @ApiModelProperty("扫码见图汉字")
    private String smjt;

    public String getSmjt() {
        return smjt;
    }

    public void setSmjt(String smjt) {
        this.smjt = smjt;
    }

    public String getSmct() {
        return smct;
    }

    public void setSmct(String smct) {
        this.smct = smct;
    }

    public DzqzPositionDTO getPosition() {
        return position;
    }

    public void setPosition(DzqzPositionDTO position) {
        this.position = position;
    }

    public DzqzSealDTO getSeal() {
        return seal;
    }

    public void setSeal(DzqzSealDTO seal) {
        this.seal = seal;
    }

    public Integer getGglx() {
        return gglx;
    }

    public void setGglx(Integer gglx) {
        this.gglx = gglx;
    }

    public byte[] getPdfByte() {
        return pdfByte;
    }

    public void setPdfByte(byte[] pdfByte) {
        this.pdfByte = pdfByte;
    }
}
