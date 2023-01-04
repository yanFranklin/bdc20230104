package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/10/31
 * @description
 */
@Table(name = "BDC_WTR")
@ApiModel(value = "BdcWtrDO", description = "委托人")
public class BdcWtrDO {
    @Id
    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "委托书编号")
    private String wtsbh;

    @ApiModelProperty(value = "委托人姓名")
    private String wtrxm;

    @ApiModelProperty(value = "委托人性别 ")
    private String wtrxb;

    @ApiModelProperty(value = "委托人证件号")
    private String wtrzjh;


    @ApiModelProperty(value = "委托人电话号")
    private String wtrdh;


    @ApiModelProperty(value = "委托时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date wtsj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWtsbh() {
        return wtsbh;
    }

    public void setWtsbh(String wtsbh) {
        this.wtsbh = wtsbh;
    }

    public String getWtrxm() {
        return wtrxm;
    }

    public void setWtrxm(String wtrxm) {
        this.wtrxm = wtrxm;
    }

    public String getWtrxb() {
        return wtrxb;
    }

    public void setWtrxb(String wtrxb) {
        this.wtrxb = wtrxb;
    }

    public String getWtrzjh() {
        return wtrzjh;
    }

    public void setWtrzjh(String wtrzjh) {
        this.wtrzjh = wtrzjh;
    }

    public Date getWtsj() {
        return wtsj;
    }

    public void setWtsj(Date wtsj) {
        this.wtsj = wtsj;
    }

    public String getWtrdh() {
        return wtrdh;
    }

    public void setWtrdh(String wtrdh) {
        this.wtrdh = wtrdh;
    }
}
