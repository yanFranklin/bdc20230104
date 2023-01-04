package cn.gtmap.realestate.common.core.domain.rules;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/11/15,1.0
 * @description
 */
@Table( name ="BDC_GZ_BMD" )
@ApiModel(value = "BdcGzBmdDO", description = "例外人员信息")
public class BdcGzBmdDO {

    @Id
    @ApiModelProperty(value = "操作人员ID")
    private String czryid;

    @ApiModelProperty(value = "操作人员")
    private String czry;

    @ApiModelProperty(value = "操作人员密码")
    private String czrymm;

    public String getCzryid() {
        return this.czryid;
    }

    public void setCzryid(String czryid) {
        this.czryid = czryid;
    }

    public String getCzry() {
        return this.czry;
    }

    public void setCzry(String czry) {
        this.czry = czry;
    }

    public String getCzrymm() {
        return this.czrymm;
    }

    public void setCzrymm(String czrymm) {
        this.czrymm = czrymm;
    }

    @Override
    public String toString() {
        return "BdcGzBmdDO{" +
                "czryid='" + czryid + '\'' +
                ", czry='" + czry + '\'' +
                ", czrymm='" + czrymm + '\'' +
                '}';
    }
}
