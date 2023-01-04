package cn.gtmap.realestate.common.core.dto.certificate.eCertificate;

import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;

/**
 * @program: realestate
 * @description: 非登记标准数据电子签章DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-08-24 10:05
 **/
public class FdjywDzqzDTO extends EZsDTO {

    private DzqzPositionDTO position;
    private DzqzSealDTO seal;

    @ApiModelProperty("扫码查图")
    private String smct;
    @ApiModelProperty("pdfbyte")
    private byte[] pdfByte;

    @ApiModelProperty("扫码见图汉字")
    private String smjt;


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

    public String getSmct() {
        return smct;
    }

    public void setSmct(String smct) {
        this.smct = smct;
    }

    public byte[] getPdfByte() {
        return pdfByte;
    }

    public void setPdfByte(byte[] pdfByte) {
        this.pdfByte = pdfByte;
    }

    public String getSmjt() {
        return smjt;
    }

    public void setSmjt(String smjt) {
        this.smjt = smjt;
    }

    @Override
    public String toString() {
        return "FdjywDzqzDTO{" +
                "position=" + position +
                ", seal=" + seal +
                ", smct='" + smct + '\'' +
                ", pdfByte=" + Arrays.toString(pdfByte) +
                ", smjt='" + smjt + '\'' +
                '}';
    }
}
