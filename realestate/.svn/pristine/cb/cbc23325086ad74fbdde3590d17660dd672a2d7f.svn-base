package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/12/20
 * @description
 */
@Api(value = "BdcZzdzZmxxResponseDTO", description = "自助打证机证明查询接口返回结果")
public class BdcZzdzZmxxResponseDTO {


    /**
     * 成功编码
     */
    public static final String SUCCESS_CODE = "1";


    @ApiModelProperty(value = "响应编码")
    private String code;

    @ApiModelProperty(value = "响应描述")
    private String message;

    @ApiModelProperty(value = "响应数据")
    private List<BdcZzdzZmxxDTO> bdcZzdzZmxxDTOList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BdcZzdzZmxxDTO> getBdcZzdzZmxxDTOList() {
        return bdcZzdzZmxxDTOList;
    }

    public void setBdcZzdzZmxxDTOList(List<BdcZzdzZmxxDTO> bdcZzdzZmxxDTOList) {
        this.bdcZzdzZmxxDTOList = bdcZzdzZmxxDTOList;
    }

    @Override
    public String toString() {
        return "BdcZzdzZmxxResponseDTO{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", bdcZzdzZmxxDTOList=" + bdcZzdzZmxxDTOList +
                '}';
    }

        public static BdcZzdzZmxxResponseDTO fail(String code, String message) {
        BdcZzdzZmxxResponseDTO bdcZzdzZmxxResponseDTO =new BdcZzdzZmxxResponseDTO();
        bdcZzdzZmxxResponseDTO.code = code;
        bdcZzdzZmxxResponseDTO.message = message;
        return bdcZzdzZmxxResponseDTO;
    }

    public static BdcZzdzZmxxResponseDTO ok(List<BdcZzdzZmxxDTO> bdcZzdzZmxxDTOList,String message) {
        BdcZzdzZmxxResponseDTO bdcZzdzZmxxResponseDTO =new BdcZzdzZmxxResponseDTO();
        bdcZzdzZmxxResponseDTO.code = SUCCESS_CODE;
        bdcZzdzZmxxResponseDTO.message = message;
        bdcZzdzZmxxResponseDTO.bdcZzdzZmxxDTOList = bdcZzdzZmxxDTOList;
        return bdcZzdzZmxxResponseDTO;
    }
}
