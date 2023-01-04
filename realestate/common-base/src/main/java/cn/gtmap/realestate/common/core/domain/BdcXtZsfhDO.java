package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.type.Alias;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/5
 * @description 不动产废号实体定义
 */
@Alias("BdcXtZsfhDO")
@Table(name = "BDC_XT_ZSFH")
public class BdcXtZsfhDO extends BdcZhDO{
    @Id
    @ApiModelProperty(value = "废除证号ID")
    private String fczhid;

    @ApiModelProperty(value = "项目id")
    private String xmid;

    @ApiModelProperty(value = "登记部门代码")
    private String djbmdm;


    public String getFczhid() {
        return fczhid;
    }

    public void setFczhid(String fczhid) {
        this.fczhid = fczhid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getDjbmdm() {
        return djbmdm;
    }

    public void setDjbmdm(String djbmdm) {
        this.djbmdm = djbmdm;
    }

    @Override
    public String toString() {
        return "BdcXtZsfhDO{" +
                "fczhid='" + fczhid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", djbmdm='" + djbmdm + '\'' +
                '}';
    }
}
