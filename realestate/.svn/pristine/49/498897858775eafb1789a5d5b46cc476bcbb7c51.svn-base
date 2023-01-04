package cn.gtmap.realestate.common.core.dto.inquiry;

import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-08-12
 * @description
 */
@Api(value = "BdcDysdDTO", description = "不动产单元锁定DTO")
public class BdcDysdDTO extends BdcDysdDO implements Serializable {
    private static final long serialVersionUID = 8422129888262075959L;
    /**
     * 权籍数据返回的 查询哪些表 用来确定bdclx
     */
    @ApiModelProperty("类型")
    private String lx;

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    @Override
    public String toString() {
        return "BdcDysdDTO{" +
                "lx='" + lx + '\'' +
                '}';
    }
}
