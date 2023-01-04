package cn.gtmap.realestate.common.core.dto.register;

import cn.gtmap.realestate.common.core.domain.BdcFdcq3GyxxDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/2/13
 * @description 建筑物区分业主共有信息DTO
 */
public class BdcFdcq3GyxxDTO extends BdcFdcq3GyxxDO {
    @ApiModelProperty("登记日期")
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date djrq;

    public Date getDjrq() {
        return djrq;
    }

    public void setDjrq(Date djrq) {
        this.djrq = djrq;
    }

    @Override
    public String toString() {
        return "BdcFdcq3GyxxDTO{" +
                "djrq=" + djrq +
                '}';
    }

}
