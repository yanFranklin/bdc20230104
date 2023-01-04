package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2018-12-29
 * @description 预测户室与实测户室关联数据传递
 */
@ApiModel(value = "YcScHsGlDTO", description = "预测户室与实测户室关联数据传递DTO")
public class YcScHsGlRequestDTO {

    @ApiModelProperty(value = "预测户室关联主键list")
    private List<String> ychsIndexList;

    @NotEmpty(message = "实测户室主键不能为空")
    @ApiModelProperty(value = "实测户室关联主键list")
    private List<String> schsIndexList;

    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;

    public List<String> getSchsIndexList() {
        return schsIndexList;
    }

    public void setSchsIndexList(List<String> schsIndexList) {
        this.schsIndexList = schsIndexList;
    }

    public List<String> getYchsIndexList() {
        return ychsIndexList;
    }

    public void setYchsIndexList(List<String> ychsIndexList) {
        this.ychsIndexList = ychsIndexList;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    @Override
    public String toString() {
        return "YcScHsGlRequestDTO{" +
                "ychsIndexList=" + ychsIndexList +
                ", schsIndexList=" + schsIndexList +
                ", qjgldm='" + qjgldm + '\'' +
                '}';
    }
}
