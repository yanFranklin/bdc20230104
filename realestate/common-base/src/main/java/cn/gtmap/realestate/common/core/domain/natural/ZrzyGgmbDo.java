package cn.gtmap.realestate.common.core.domain.natural;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="ZRZY_GGMB")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = ZrzyGgmbDo.class)
@ApiModel(value="ZrzyGgmbDo",description = "自然资源公告模板")
@Data
public class ZrzyGgmbDo {
    /**
     * 自然状况信息ID
     */
    @Id
    @ApiModelProperty(value="模板ID")
    private String mbxxid;

    /**
     * 项目ID
     */
    @NotEmpty
    @ApiModelProperty(value="项目ID")
    private String xmid;

    /**
     * 项目ID
     */
    @NotEmpty
    @ApiModelProperty(value="模板内容")
    private String mbnr;
}
