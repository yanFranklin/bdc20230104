package cn.gtmap.realestate.exchange.core.dto.jgpt.ywsl;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/9/8
 * @description 业务受理号和工作流定义id
 */
@ApiModel(description = "业务受理号和工作流定义id")
public class JgptYwslDO {
    @ApiModelProperty("数量")
    private Integer sl;

    @ApiModelProperty("受理编号")
    private Integer sply;

    @ApiModelProperty("工作流定义id")
    private String gzldyid;

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    public Integer getSply() {
        return sply;
    }

    public void setSply(Integer sply) {
        this.sply = sply;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    @Override
    public String toString() {
        return "JgptYwslDO{" +
                "sl=" + sl +
                ", sply=" + sply +
                ", gzldyid='" + gzldyid + '\'' +
                '}';
    }
}
