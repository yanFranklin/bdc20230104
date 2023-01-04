package cn.gtmap.realestate.common.core.qo.certificate;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>"
 * @version 1.0, 2018/11/11
 * @description 不动产证书查询QO
 */
@ApiModel(value = "BdcZsQO", description = "不动产证书查询对象")
public class BdcZsQO implements Serializable {

    private static final long serialVersionUID = -7450441730450562901L;
    @ApiModelProperty(value = "记录当前是哪个页面请求的")
    private String resourceName;

    @ApiModelProperty(value = "证书类型")
    private Integer zslx;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "注销原权利")
    private Integer zxyql;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "项目id")
    private String xmid;

    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;

    @ApiModelProperty(value = "工作流实例id集合")
    private List<String> gzlslidList;

    @ApiModelProperty(value = "证书ID", hidden = true)
    private String zsid;

    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "时间")
    private Date date;

    @ApiModelProperty(value = "日期格式模式")
    private String datePattern;


    @ApiModelProperty(value = "项目权属状态List")
    private List<Integer> qsztList;

    @ApiModelProperty(value = "证书IDList")
    private List<String> zsidList;

    @ApiModelProperty(value = "用户信息")
    private UserDto userDto;

    @ApiModelProperty(value = "证照标识")
    private String zzbs;

    @ApiModelProperty(value = "其他查询sql字符串，用于补充查询语句")
    private String sqlStr;

    @ApiModelProperty(value = "权利类型")
    private Integer qllx;

    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;

    @ApiModelProperty(value = "是否查询办结数据 1:查询已办结，0:查询未办结")
    private String queryBj;

    @ApiModelProperty(value = "打印状态 1；已打印，0：未打印")
    private String dyzt;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    //盐城要求 添加产权证号模糊查询
    @ApiModelProperty(value = "产权证号模糊")
    private String bdcqzhmh;

    //39206 【盐城】自助机查询证书信息接口调整-针对外网受理 增加审批来源查询条件
    @ApiModelProperty(value = "审批来源")
    private String sply;

    //43015 【盐城】真伪查询接口修改入参
    @ApiModelProperty(value = "坐落精确")
    private String zljq;

    @ApiModelProperty(value = "项目ID集合")
    private List<String> xmidList;

    @ApiModelProperty(value = "外联项目")
    private Integer wlxm;

    @ApiModelProperty(value = "不动产权证号简称")
    private String bdcqzhjc;

    public String getZljq() {
        return zljq;
    }

    public void setZljq(String zljq) {
        this.zljq = zljq;
    }

    public String getSply() {
        return sply;
    }

    public void setSply(String sply) {
        this.sply = sply;
    }

    public String getBdcqzhmh() {
        return bdcqzhmh;
    }

    public void setBdcqzhmh(String bdcqzhmh) {
        this.bdcqzhmh = bdcqzhmh;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getQueryBj() {
        return queryBj;
    }

    public void setQueryBj(String queryBj) {
        this.queryBj = queryBj;
    }

    public String getDyzt() {
        return dyzt;
    }

    public void setDyzt(String dyzt) {
        this.dyzt = dyzt;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public Integer getZxyql() {
        return zxyql;
    }

    public void setZxyql(Integer zxyql) {
        this.zxyql = zxyql;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    public String getSqlStr() {
        return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public List<String> getZsidList() {
        return zsidList;
    }

    public void setZsidList(List<String> zsidList) {
        this.zsidList = zsidList;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public List<Integer> getQsztList() {
        return qsztList;
    }

    public void setQsztList(List<Integer> qsztList) {
        this.qsztList = qsztList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    public String getZzbs() {
        return zzbs;
    }

    public void setZzbs(String zzbs) {
        this.zzbs = zzbs;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public List<String> getXmidList() {
        return xmidList;
    }

    public void setXmidList(List<String> xmidList) {
        this.xmidList = xmidList;
    }

    public Integer getWlxm() {
        return wlxm;
    }

    public void setWlxm(Integer wlxm) {
        this.wlxm = wlxm;
    }

    public List<String> getGzlslidList() {
        return gzlslidList;
    }

    public void setGzlslidList(List<String> gzlslidList) {
        this.gzlslidList = gzlslidList;
    }

    public String getBdcqzhjc() {
        return bdcqzhjc;
    }

    public void setBdcqzhjc(String bdcqzhjc) {
        this.bdcqzhjc = bdcqzhjc;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcZsQO{");
        sb.append("resourceName='").append(resourceName).append('\'');
        sb.append(", zslx=").append(zslx);
        sb.append(", bdcqzh='").append(bdcqzh).append('\'');
        sb.append(", zxyql=").append(zxyql);
        sb.append(", zl='").append(zl).append('\'');
        sb.append(", bdcdyh='").append(bdcdyh).append('\'');
        sb.append(", xmid='").append(xmid).append('\'');
        sb.append(", gzlslid='").append(gzlslid).append('\'');
        sb.append(", gzlslidList=").append(gzlslidList);
        sb.append(", zsid='").append(zsid).append('\'');
        sb.append(", qlr='").append(qlr).append('\'');
        sb.append(", date=").append(date);
        sb.append(", datePattern='").append(datePattern).append('\'');
        sb.append(", qsztList=").append(qsztList);
        sb.append(", zsidList=").append(zsidList);
        sb.append(", userDto=").append(userDto);
        sb.append(", zzbs='").append(zzbs).append('\'');
        sb.append(", sqlStr='").append(sqlStr).append('\'');
        sb.append(", qllx=").append(qllx);
        sb.append(", qlrzjh='").append(qlrzjh).append('\'');
        sb.append(", queryBj='").append(queryBj).append('\'');
        sb.append(", dyzt='").append(dyzt).append('\'');
        sb.append(", slbh='").append(slbh).append('\'');
        sb.append(", bdcqzhmh='").append(bdcqzhmh).append('\'');
        sb.append(", sply='").append(sply).append('\'');
        sb.append(", zljq='").append(zljq).append('\'');
        sb.append(", xmidList=").append(xmidList);
        sb.append(", wlxm=").append(wlxm);
        sb.append(", bdcqzhjc='").append(bdcqzhjc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
