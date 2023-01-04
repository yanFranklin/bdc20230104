package cn.gtmap.realestate.inquiry.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/3/24
 * @description 领取人与银行关系对象
 */
@Table(name = "YH_LQR_DZB")
@ApiModel("银行与领取人关系对象")
public class YhLqrDO {

    @Id
    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("领取人姓名")
    private String lqrxm;

    @ApiModelProperty("领取人证件号")
    private String lqrzjh;

    @ApiModelProperty("银行名称")
    private String yhmc;

    @ApiModelProperty("银行证件号")
    private String yhzjh;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLqrxm() {
        return lqrxm;
    }

    public void setLqrxm(String lqrxm) {
        this.lqrxm = lqrxm;
    }

    public String getLqrzjh() {
        return lqrzjh;
    }

    public void setLqrzjh(String lqrzjh) {
        this.lqrzjh = lqrzjh;
    }

    public String getYhmc() {
        return yhmc;
    }

    public void setYhmc(String yhmc) {
        this.yhmc = yhmc;
    }

    public String getYhzjh() {
        return yhzjh;
    }

    public void setYhzjh(String yhzjh) {
        this.yhzjh = yhzjh;
    }

    @Override
    public String toString() {
        return "YhLqrDO{" +
                "id='" + id + '\'' +
                ", lqrxm='" + lqrxm + '\'' +
                ", lqrzjh='" + lqrzjh + '\'' +
                ", yhmc='" + yhmc + '\'' +
                ", yhzjh='" + yhzjh + '\'' +
                '}';
    }
}
