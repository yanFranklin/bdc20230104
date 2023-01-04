package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @program: realestate
 * @description: 网签备案，流程关系表
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-03-16 09:19
 **/
@Table(name = "BDC_WQBA_LC_GX")
@ApiModel(value = "BdcWqbaLcGxDO", description = "网签备案，流程关系")
public class BdcWqbaLcGxDO implements Serializable {
    private static final long serialVersionUID = 6553545948877864079L;

    @Id
    @ApiModelProperty(value = "关系id")
    private String gxid;
    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;
    @ApiModelProperty(value = "合同编号")
    private String htbh;

    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    @Override
    public String toString() {
        return "BdcWqbaLcGxDO{" +
                "gxid='" + gxid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", htbh='" + htbh + '\'' +
                '}';
    }
}
