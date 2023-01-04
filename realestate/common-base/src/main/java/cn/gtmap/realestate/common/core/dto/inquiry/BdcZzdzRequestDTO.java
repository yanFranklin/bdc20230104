package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-10-23
 * @description 不动产自助打证requestDTO
 */
@Api(value = "BdcZzdzRequestDTO", description = "不动产自助打证requestDTO")
public class BdcZzdzRequestDTO {

    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;

    @ApiModelProperty(value = "证书类型")
    private Integer zslx;

    @ApiModelProperty(value = "自助打证机用户名")
    private String  zzdzjyhm;

    @ApiModelProperty(value = "领证人人像base64")
    private String lzrrxBase64;

    @ApiModelProperty(value = "领证人签字base64")
    private String lzrqzBase64;

    @ApiModelProperty(value = "自助打证机ip")
    private String zzdzjIp;

    @ApiModelProperty(value = "印制号证书id对应实体集合")
    private List<BdcZsidYzhGxDTO> bdczsidYzhGxDto;

    @ApiModelProperty(value = "不出证流程登记小类")
    private List<String> excludeDjxl;

    public String getZzdzjIp() {
        return zzdzjIp;
    }

    public void setZzdzjIp(String zzdzjIp) {
        this.zzdzjIp = zzdzjIp;
    }

    public String getZzdzjyhm() {
        return zzdzjyhm;
    }

    public void setZzdzjyhm(String zzdzjyhm) {
        this.zzdzjyhm = zzdzjyhm;
    }

    public String getLzrrxBase64() {
        return lzrrxBase64;
    }

    public void setLzrrxBase64(String lzrrxBase64) {
        this.lzrrxBase64 = lzrrxBase64;
    }

    public String getLzrqzBase64() {
        return lzrqzBase64;
    }

    public void setLzrqzBase64(String lzrqzBase64) {
        this.lzrqzBase64 = lzrqzBase64;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public List<BdcZsidYzhGxDTO> getBdczsidYzhGxDto() {
        return bdczsidYzhGxDto;
    }

    public void setBdczsidYzhGxDto(List<BdcZsidYzhGxDTO> bdczsidYzhGxDto) {
        this.bdczsidYzhGxDto = bdczsidYzhGxDto;
    }

    public List<String> getExcludeDjxl() {
        return excludeDjxl;
    }

    public void setExcludeDjxl(List<String> excludeDjxl) {
        this.excludeDjxl = excludeDjxl;
    }

    @Override
    public String toString() {
        return "BdcZzdzRequestDTO{" +
                "qlrzjh='" + qlrzjh + '\'' +
                ", zslx=" + zslx +
                ", bdczsidYzhGxDto=" + bdczsidYzhGxDto +
                ", excludeDjxl=" + excludeDjxl +
                '}';
    }
}
