package cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdjfk;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/12/07 15:13
 */
public class SbdjfkResquestDTO {

    @ApiModelProperty(value = "申报基本信息，内容要求具体参考《申报登记反馈基本信息定义》",required = true)
    private SbdjfkResquestSbjbxxDTO sbjbxxDTO;

    @ApiModelProperty(value = "申报材料信息，内容具体要求参考《申报材料信息定义》",required = false)
    private SbdjfkResquestSbclxxDTO sbclxxDTO;

    @ApiModelProperty(value = "业务表单信息（可选），内容要求具体参考《业务表单信息定义》",required = false)
    private SbdjfkResquestYwbdxxDTO ywbdxxDTO;

    public SbdjfkResquestSbjbxxDTO getSbjbxxDTO() {
        return sbjbxxDTO;
    }

    public void setSbjbxxDTO(SbdjfkResquestSbjbxxDTO sbjbxxDTO) {
        this.sbjbxxDTO = sbjbxxDTO;
    }

    public SbdjfkResquestSbclxxDTO getSbclxxDTO() {
        return sbclxxDTO;
    }

    public void setSbclxxDTO(SbdjfkResquestSbclxxDTO sbclxxDTO) {
        this.sbclxxDTO = sbclxxDTO;
    }

    public SbdjfkResquestYwbdxxDTO getYwbdxxDTO() {
        return ywbdxxDTO;
    }

    public void setYwbdxxDTO(SbdjfkResquestYwbdxxDTO ywbdxxDTO) {
        this.ywbdxxDTO = ywbdxxDTO;
    }
}
