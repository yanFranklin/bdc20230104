package cn.gtmap.realestate.common.core.dto.engine;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/11/16,1.0
 * @description 例外人员登录返回值
 */
@ApiModel(value = "BdcBmdLoginDTO", description = "不动产白名单人员登录")
public class BdcBmdLoginDTO implements Serializable {
    private static final long serialVersionUID = -5134214662792525363L;
    @ApiModelProperty(value = "是否登录")
    private String islogin;

    @ApiModelProperty(value = "操作人员id")
    private String czryid;

    public String getIslogin() {
        return islogin;
    }

    public void setIslogin(String islogin) {
        this.islogin = islogin;
    }

    public String getCzryid() {
        return czryid;
    }

    public void setCzryid(String czryid) {
        this.czryid = czryid;
    }

    @Override
    public String toString() {
        return "BdcBmdLoginDTO{" +
                "islogin='" + islogin + '\'' +
                ", czryid='" + czryid + '\'' +
                '}';
    }
}
