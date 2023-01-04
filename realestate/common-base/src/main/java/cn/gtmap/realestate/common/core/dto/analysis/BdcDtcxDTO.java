package cn.gtmap.realestate.common.core.dto.analysis;

import cn.gtmap.realestate.common.core.domain.analysis.DtcxCxjgDO;
import cn.gtmap.realestate.common.core.domain.analysis.DtcxCxtjDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author: <a href="@mailto:hynkoala@163.com">hanyaning</a>
 * @version: V1.0, 2019.03.18
 * @description:
 */
@Api(value = "BdcDtcxDTO", description = "动态查询传输对象")
public class BdcDtcxDTO {
    @ApiModelProperty("查询服务名称")
    private String cxmc;
    @ApiModelProperty("查询服务代号")
    private String cxdh;
    @ApiModelProperty("主键id")
    private String cxid;
    @ApiModelProperty("查询sql")
    private String cxsql;
    @ApiModelProperty("创建人")
    private String cjr;
    @ApiModelProperty("创建时间")
    private Date cjsj;
    @ApiModelProperty("变更人")
    private String bgr;
    @ApiModelProperty("变更时间")
    private Date bgsj;
    @ApiModelProperty("查询方式")
    private String cxfs;
    @ApiModelProperty("当前状态，0：为启用；1：已启用")
    private String dqzt;
    @ApiModelProperty("服务url地址")
    private String url;
    @ApiModelProperty("备注")
    private String bz;
    @ApiModelProperty("页面工具")
    private String ymgj;
    @ApiModelProperty("行工具")
    private String hgj;
    @ApiModelProperty("查询条件集")
    private List<DtcxCxtjDO> cxtjDOList;
    @ApiModelProperty("查询结果列")
    private List<DtcxCxjgDO> cxjgDOList;

    public String getYmgj() {
        return ymgj;
    }

    public void setYmgj(String ymgj) {
        this.ymgj = ymgj;
    }

    public String getHgj() {
        return hgj;
    }

    public void setHgj(String hgj) {
        this.hgj = hgj;
    }

    public String getDqzt() {
        return dqzt;
    }

    public void setDqzt(String dqzt) {
        this.dqzt = dqzt;
    }

    public List<DtcxCxtjDO> getCxtjDOList() {
        return cxtjDOList;
    }

    public void setCxtjDOList(List<DtcxCxtjDO> cxtjDOList) {
        this.cxtjDOList = cxtjDOList;
    }

    public List<DtcxCxjgDO> getCxjgDOList() {
        return cxjgDOList;
    }

    public void setCxjgDOList(List<DtcxCxjgDO> cxjgDOList) {
        this.cxjgDOList = cxjgDOList;
    }

    public String getCxdh() {
        return cxdh;
    }

    public void setCxdh(String cxdh) {
        this.cxdh = cxdh;
    }

    public String getCxmc() {
        return cxmc;
    }

    public void setCxmc(String cxmc) {
        this.cxmc = cxmc;
    }

    public String getCxid() {
        return cxid;
    }

    public void setCxid(String cxid) {
        this.cxid = cxid;
    }

    public String getCxsql() {
        return cxsql;
    }

    public void setCxsql(String cxsql) {
        this.cxsql = cxsql;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getBgr() {
        return bgr;
    }

    public void setBgr(String bgr) {
        this.bgr = bgr;
    }

    public Date getBgsj() {
        return bgsj;
    }

    public void setBgsj(Date bgsj) {
        this.bgsj = bgsj;
    }

    public String getCxfs() {
        return cxfs;
    }

    public void setCxfs(String cxfs) {
        this.cxfs = cxfs;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }
}