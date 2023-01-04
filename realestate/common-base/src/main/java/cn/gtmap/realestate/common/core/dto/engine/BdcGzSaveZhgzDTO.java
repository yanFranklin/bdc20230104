package cn.gtmap.realestate.common.core.dto.engine;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZhgzDO;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/4
 * @description 不动产组合规则实体定义
 */
public class BdcGzSaveZhgzDTO extends BdcGzZhgzDO {

    /**
     * 规则id
     */
    @ApiModelProperty(value = "规则id")
    private String gzid;

    public String getGzid() {
        return gzid;
    }

    public void setGzid(String gzid) {
        this.gzid = gzid;
    }

    @Override
    public String toString() {
        return "BdcGzSaveZhgzDTO{" +
                "gzid='" + gzid + '\'' +
                '}' + super.toString();
    }
}
