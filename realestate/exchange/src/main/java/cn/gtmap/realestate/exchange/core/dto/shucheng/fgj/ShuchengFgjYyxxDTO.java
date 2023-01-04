package cn.gtmap.realestate.exchange.core.dto.shucheng.fgj;

import cn.gtmap.realestate.common.core.annotations.Zd;
import cn.gtmap.realestate.common.core.domain.BdcZdQsztDO;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author wangyinghao
 * @Date 2022/4/6
 * @description 舒城县房管局-一手房登记异议数据DTO
 */
@ApiModel("舒城县房管局-一手房登记异议数据DTO")
public class ShuchengFgjYyxxDTO {
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "不动产单元编号")
    private String bdcdybh;

    @ApiModelProperty(value = "异议事项")
    private String yysx;

    @ApiModelProperty(value = "登记时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date djsj;

    @Zd(tableClass = BdcZdQsztDO.class)
    @ApiModelProperty(value = "权属状态")
    private String qszt;

    @ApiModelProperty(value = "附记")
    private String fj;

    @ApiModelProperty(value = "产权来源")
    private String cqly;

    @ApiModelProperty(value = "异议申请人")
    private String yysqr;

    @ApiModelProperty(value = "申请人证件号码")
    private String yysqrzjhm;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcdybh() {
        return bdcdybh;
    }

    public void setBdcdybh(String bdcdybh) {
        this.bdcdybh = bdcdybh;
    }

    public String getYysx() {
        return yysx;
    }

    public void setYysx(String yysx) {
        this.yysx = yysx;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getCqly() {
        return cqly;
    }

    public void setCqly(String cqly) {
        this.cqly = cqly;
    }

    public String getYysqr() {
        return yysqr;
    }

    public void setYysqr(String yysqr) {
        this.yysqr = yysqr;
    }

    public String getYysqrzjhm() {
        return yysqrzjhm;
    }

    public void setYysqrzjhm(String yysqrzjhm) {
        this.yysqrzjhm = yysqrzjhm;
    }
}
