package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.gtc.sso.domain.dto.RegionDto;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlWxjjxxDO;
import cn.gtmap.realestate.common.core.dto.BdcXmCfDTO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlSfxxVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/19
 * @description
 */
@ApiModel(value = "BdcSlQlxxymDTO", description = "不动产受理权利页面")
public class BdcSlQlxxymDTO implements Serializable {
    private static final long serialVersionUID = -7739312797031369872L;
    @ApiModelProperty(value = "权利名称")
    private String qlmc;
    @ApiModelProperty(value = "权利类型")
    private Integer qllx;
    @ApiModelProperty(value = "表名称")
    private  String tableName;
    @ApiModelProperty(value = "类名称")
    private  String className;
    @ApiModelProperty(value = "是否抵押登记")
    private Boolean dydj;
    @ApiModelProperty(value = "抵押房产面积总和")
    private Double dyfwmjzh;
    @ApiModelProperty(value = "不动产权利")
    private BdcQl bdcQl;
    @ApiModelProperty(value = "更正信息")
    private BdcGzdjDO bdcGzdj;
    @ApiModelProperty(value = "不动产项目")
    private BdcXmDO bdcXm;
    @ApiModelProperty(value = "不动产项目附表")
    private BdcXmFbDO bdcXmFbDO;
    @ApiModelProperty(value = "不动产权利人")
    private List<BdcQlrDO> bdcQlrDOList;
    @ApiModelProperty(value = "不动产受理收件材料")
    private List<BdcSlSjclDO> bdcSlSjclDOList;
    @ApiModelProperty(value = "不动产单元总数")
    private Integer bdcdyCount;

    @ApiModelProperty(value = "抵押登记证明号")
    private String dydjzmh;

    @ApiModelProperty(value = "原产权证号")
    private String ycqzh;

    @ApiModelProperty(value = "外联证书")
    private String wlzs;

    @ApiModelProperty(value = "当前分组项目ID集合")
    private List<String> groupXmidList;
    @ApiModelProperty(value = "当前分组项目bdcdyh集合")
    private List<String> groupBdcdyhList;
    @ApiModelProperty(value = "存在抵押的单元号")
    private String dyaBdcdyh;

    @ApiModelProperty(value = "债务人")
    private String zwr;

    @ApiModelProperty(value = "债务人证件种类（名称）")
    private String zwrzjzl;

    @ApiModelProperty(value = "债务人证件号")
    private String zwrzjh;

    @ApiModelProperty(value = "不动产第三权利人")
    private List<BdcDsQlrDO> bdcDsQlrDOList;

    @ApiModelProperty(value = "登记原因登记小类集合")
    private List<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOList;

    @ApiModelProperty(value = "领证人信息")
    private List<BdcLzrDO> bdcLzrDOList;

    @ApiModelProperty(value = "展示原权利按钮")
    private String showYqlBtn;

    @ApiModelProperty(value = "流程类型1普通2组合3批量4 批量组合")
    private Integer xmlx;

    @ApiModelProperty(value = "水电气过户信息")
    private List<BdcSdqghDO> bdcSdqghDOList;

    @ApiModelProperty(value = "受理页面的产权信息")
    private Map cqxxMap;

    @ApiModelProperty(value = "受理页面的建设用地信息")
    private Map jsydsyqMap;

    @ApiModelProperty(value = "受理页面查封信息")
    private List<SlymCfxxDTO> slymCfxxDTOList;

    @ApiModelProperty(value = "项目信息")
    private List<BdcXmDO> bdcXmDOList;

    @ApiModelProperty(value = "当前权利为限制权利时需要查询对应的产权项目信息")
    private List<BdcXmCfDTO> cqXmList;

    @ApiModelProperty(value = "单元号权利类型")
    private String dyhqllx;

    @ApiModelProperty(value = "产权信息集合，暂用于常州批量流程")
    private List<SlymCqxxDTO> slymCqxxDTOList;

    @ApiModelProperty("单元锁定信息")
    private BdcDysdDO bdcDysdDO;

    @ApiModelProperty("建筑面积总和")
    private Double jzmjSum;

    @ApiModelProperty("行政区划")
    private List<RegionDto> regionDtoList;

    @ApiModelProperty("证明类型")
    private String zmlx;

    @ApiModelProperty("提示信息")
    private String tsxx;

    @ApiModelProperty("批量页面宗地用途拼接值")
    private String zdytpj;

    @ApiModelProperty("批量页面宗地用途2拼接")
    private String zdyt2pj;

    @ApiModelProperty("批量页面宗地用途3拼接")
    private String zdyt3pj;

    @ApiModelProperty("使用权结束时间拼接")
    private String syqjssjpj;

    @ApiModelProperty("使用权结束时间2拼接")
    private String syqjssj2pj;

    @ApiModelProperty("使用权结束时间3拼接")
    private String syqjssj3pj;

    @ApiModelProperty("收费信息")
    private List<BdcSlSfxxVO> bdcSlSfxx;

    @ApiModelProperty("维修基金信息")
    private BdcSlWxjjxxDO bdcSlWxjjxxDO;


    @ApiModelProperty("证书不动产权证号")
    private String zsbdcqzh;

    @ApiModelProperty("证书id")
    private String zsid;

    @ApiModelProperty("证书证号是否可修改")
    private Boolean xgzszh = false;

    public String getYcqzh() {
        return ycqzh;
    }

    public void setYcqzh(String ycqzh) {
        this.ycqzh = ycqzh;
    }

    public String getQlmc() {
        return qlmc;
    }

    public String getDydjzmh() {
        return dydjzmh;
    }

    public void setDydjzmh(String dydjzmh) {
        this.dydjzmh = dydjzmh;
    }

    public void setQlmc(String qlmc) {
        this.qlmc = qlmc;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Boolean getDydj() {
        return dydj;
    }

    public void setDydj(Boolean dydj) {
        this.dydj = dydj;
    }

    public BdcQl getBdcQl() {
        return bdcQl;
    }

    public void setBdcQl(BdcQl bdcQl) {
        this.bdcQl = bdcQl;
    }

    public BdcGzdjDO getBdcGzdj() {
        return bdcGzdj;
    }

    public void setBdcGzdj(BdcGzdjDO bdcGzdj) {
        this.bdcGzdj = bdcGzdj;
    }

    public List<BdcQlrDO> getBdcQlrDOList() {
        return bdcQlrDOList;
    }

    public void setBdcQlrDOList(List<BdcQlrDO> bdcQlrDOList) {
        this.bdcQlrDOList = bdcQlrDOList;
    }

    public List<BdcSlSjclDO> getBdcSlSjclDOList() {
        return bdcSlSjclDOList;
    }

    public void setBdcSlSjclDOList(List<BdcSlSjclDO> bdcSlSjclDOList) {
        this.bdcSlSjclDOList = bdcSlSjclDOList;
    }

    public BdcXmDO getBdcXm() {
        return bdcXm;
    }

    public void setBdcXm(BdcXmDO bdcXm) {
        this.bdcXm = bdcXm;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public Double getDyfwmjzh() {
        return dyfwmjzh;
    }

    public void setDyfwmjzh(Double dyfwmjzh) {
        this.dyfwmjzh = dyfwmjzh;
    }

    public Integer getBdcdyCount() {
        return bdcdyCount;
    }

    public void setBdcdyCount(Integer bdcdyCount) {
        this.bdcdyCount = bdcdyCount;
    }

    public String getWlzs() {
        return wlzs;
    }

    public void setWlzs(String wlzs) {
        this.wlzs = wlzs;
    }

    public List<String> getGroupXmidList() {
        return groupXmidList;
    }

    public void setGroupXmidList(List<String> groupXmidList) {
        this.groupXmidList = groupXmidList;
    }

    public BdcXmFbDO getBdcXmFbDO() {
        return bdcXmFbDO;
    }

    public void setBdcXmFbDO(BdcXmFbDO bdcXmFbDO) {
        this.bdcXmFbDO = bdcXmFbDO;
    }

    public String getDyaBdcdyh() {
        return dyaBdcdyh;
    }

    public void setDyaBdcdyh(String dyaBdcdyh) {
        this.dyaBdcdyh = dyaBdcdyh;
    }

    public String getZwr() {
        return zwr;
    }

    public void setZwr(String zwr) {
        this.zwr = zwr;
    }

    public String getZwrzjzl() {
        return zwrzjzl;
    }

    public void setZwrzjzl(String zwrzjzl) {
        this.zwrzjzl = zwrzjzl;
    }

    public String getZwrzjh() {
        return zwrzjh;
    }

    public void setZwrzjh(String zwrzjh) {
        this.zwrzjh = zwrzjh;
    }

    public List<BdcDsQlrDO> getBdcDsQlrDOList() {
        return bdcDsQlrDOList;
    }

    public void setBdcDsQlrDOList(List<BdcDsQlrDO> bdcDsQlrDOList) {
        this.bdcDsQlrDOList = bdcDsQlrDOList;
    }

    public List<BdcDjxlDjyyGxDO> getBdcDjxlDjyyGxDOList() {
        return bdcDjxlDjyyGxDOList;
    }

    public void setBdcDjxlDjyyGxDOList(List<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOList) {
        this.bdcDjxlDjyyGxDOList = bdcDjxlDjyyGxDOList;
    }

    public List<BdcLzrDO> getBdcLzrDOList() {
        return bdcLzrDOList;
    }

    public void setBdcLzrDOList(List<BdcLzrDO> bdcLzrDOList) {
        this.bdcLzrDOList = bdcLzrDOList;
    }

    public String getShowYqlBtn() {
        return showYqlBtn;
    }

    public void setShowYqlBtn(String showYqlBtn) {
        this.showYqlBtn = showYqlBtn;
    }

    public Integer getXmlx() {
        return xmlx;
    }

    public void setXmlx(Integer xmlx) {
        this.xmlx = xmlx;
    }

    public List<BdcSdqghDO> getBdcSdqghDOList() {
        return bdcSdqghDOList;
    }

    public void setBdcSdqghDOList(List<BdcSdqghDO> bdcSdqghDOList) {
        this.bdcSdqghDOList = bdcSdqghDOList;
    }

    public Map getCqxxMap() {
        return cqxxMap;
    }

    public void setCqxxMap(Map cqxxMap) {
        this.cqxxMap = cqxxMap;
    }

    public Map getJsydsyqMap() {
        return jsydsyqMap;
    }

    public void setJsydsyqMap(Map jsydsyqMap) {
        this.jsydsyqMap = jsydsyqMap;
    }

    public List<SlymCfxxDTO> getSlymCfxxDTOList() {
        return slymCfxxDTOList;
    }

    public void setSlymCfxxDTOList(List<SlymCfxxDTO> slymCfxxDTOList) {
        this.slymCfxxDTOList = slymCfxxDTOList;
    }

    public List<BdcXmDO> getBdcXmDOList() {
        return bdcXmDOList;
    }

    public void setBdcXmDOList(List<BdcXmDO> bdcXmDOList) {
        this.bdcXmDOList = bdcXmDOList;
    }

    public String getDyhqllx() {
        return dyhqllx;
    }

    public void setDyhqllx(String dyhqllx) {
        this.dyhqllx = dyhqllx;
    }

    public List<BdcXmCfDTO> getCqXmList() {
        return cqXmList;
    }

    public void setCqXmList(List<BdcXmCfDTO> cqXmList) {
        this.cqXmList = cqXmList;
    }

    public List<SlymCqxxDTO> getSlymCqxxDTOList() {
        return slymCqxxDTOList;
    }

    public void setSlymCqxxDTOList(List<SlymCqxxDTO> slymCqxxDTOList) {
        this.slymCqxxDTOList = slymCqxxDTOList;
    }

    public BdcDysdDO getBdcDysdDO() {
        return bdcDysdDO;
    }

    public void setBdcDysdDO(BdcDysdDO bdcDysdDO) {
        this.bdcDysdDO = bdcDysdDO;
    }

    public Double getJzmjSum() {
        return jzmjSum;
    }

    public void setJzmjSum(Double jzmjSum) {
        this.jzmjSum = jzmjSum;
    }

    public List<RegionDto> getRegionDtoList() {
        return regionDtoList;
    }

    public void setRegionDtoList(List<RegionDto> regionDtoList) {
        this.regionDtoList = regionDtoList;
    }

    public String getZmlx() {
        return zmlx;
    }

    public void setZmlx(String zmlx) {
        this.zmlx = zmlx;
    }

    public String getTsxx() {
        return tsxx;
    }

    public void setTsxx(String tsxx) {
        this.tsxx = tsxx;
    }

    public List<String> getGroupBdcdyhList() {
        return groupBdcdyhList;
    }

    public void setGroupBdcdyhList(List<String> groupBdcdyhList) {
        this.groupBdcdyhList = groupBdcdyhList;
    }

    public String getZdytpj() {
        return zdytpj;
    }

    public void setZdytpj(String zdytpj) {
        this.zdytpj = zdytpj;
    }

    public String getZdyt2pj() {
        return zdyt2pj;
    }

    public void setZdyt2pj(String zdyt2pj) {
        this.zdyt2pj = zdyt2pj;
    }

    public String getZdyt3pj() {
        return zdyt3pj;
    }

    public void setZdyt3pj(String zdyt3pj) {
        this.zdyt3pj = zdyt3pj;
    }

    public String getSyqjssjpj() {
        return syqjssjpj;
    }

    public void setSyqjssjpj(String syqjssjpj) {
        this.syqjssjpj = syqjssjpj;
    }

    public String getSyqjssj2pj() {
        return syqjssj2pj;
    }

    public void setSyqjssj2pj(String syqjssj2pj) {
        this.syqjssj2pj = syqjssj2pj;
    }

    public String getSyqjssj3pj() {
        return syqjssj3pj;
    }

    public void setSyqjssj3pj(String syqjssj3pj) {
        this.syqjssj3pj = syqjssj3pj;
    }

    public List<BdcSlSfxxVO> getBdcSlSfxx() {
        return bdcSlSfxx;
    }

    public void setBdcSlSfxx(List<BdcSlSfxxVO> bdcSlSfxx) {
        this.bdcSlSfxx = bdcSlSfxx;
    }

    public BdcSlWxjjxxDO getBdcSlWxjjxxDO() {
        return bdcSlWxjjxxDO;
    }

    public void setBdcSlWxjjxxDO(BdcSlWxjjxxDO bdcSlWxjjxxDO) {
        this.bdcSlWxjjxxDO = bdcSlWxjjxxDO;
    }

    public String getZsbdcqzh() {
        return zsbdcqzh;
    }

    public void setZsbdcqzh(String zsbdcqzh) {
        this.zsbdcqzh = zsbdcqzh;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public Boolean getXgzszh() {
        return xgzszh;
    }

    public void setXgzszh(Boolean xgzszh) {
        this.xgzszh = xgzszh;
    }

    @Override
    public String toString() {
        return "BdcSlQlxxymDTO{" +
                "qlmc='" + qlmc + '\'' +
                ", qllx=" + qllx +
                ", tableName='" + tableName + '\'' +
                ", className='" + className + '\'' +
                ", dydj=" + dydj +
                ", dyfwmjzh=" + dyfwmjzh +
                ", bdcQl=" + bdcQl +
                ", bdcGzdj=" + bdcGzdj +
                ", bdcXm=" + bdcXm +
                ", bdcXmFbDO=" + bdcXmFbDO +
                ", bdcQlrDOList=" + bdcQlrDOList +
                ", bdcSlSjclDOList=" + bdcSlSjclDOList +
                ", bdcdyCount=" + bdcdyCount +
                ", dydjzmh='" + dydjzmh + '\'' +
                ", ycqzh='" + ycqzh + '\'' +
                ", wlzs='" + wlzs + '\'' +
                ", groupXmidList=" + groupXmidList +
                ", groupBdcdyhList=" + groupBdcdyhList +
                ", dyaBdcdyh='" + dyaBdcdyh + '\'' +
                ", zwr='" + zwr + '\'' +
                ", zwrzjzl='" + zwrzjzl + '\'' +
                ", zwrzjh='" + zwrzjh + '\'' +
                ", bdcDsQlrDOList=" + bdcDsQlrDOList +
                ", bdcDjxlDjyyGxDOList=" + bdcDjxlDjyyGxDOList +
                ", bdcLzrDOList=" + bdcLzrDOList +
                ", showYqlBtn='" + showYqlBtn + '\'' +
                ", xmlx=" + xmlx +
                ", bdcSdqghDOList=" + bdcSdqghDOList +
                ", cqxxMap=" + cqxxMap +
                ", jsydsyqMap=" + jsydsyqMap +
                ", slymCfxxDTOList=" + slymCfxxDTOList +
                ", bdcXmDOList=" + bdcXmDOList +
                ", cqXmList=" + cqXmList +
                ", dyhqllx='" + dyhqllx + '\'' +
                ", slymCqxxDTOList=" + slymCqxxDTOList +
                ", bdcDysdDO=" + bdcDysdDO +
                ", jzmjSum=" + jzmjSum +
                ", regionDtoList=" + regionDtoList +
                ", zmlx='" + zmlx + '\'' +
                ", tsxx='" + tsxx + '\'' +
                ", zdytpj='" + zdytpj + '\'' +
                ", zdyt2pj='" + zdyt2pj + '\'' +
                ", zdyt3pj='" + zdyt3pj + '\'' +
                ", syqjssjpj='" + syqjssjpj + '\'' +
                ", syqjssj2pj='" + syqjssj2pj + '\'' +
                ", syqjssj3pj='" + syqjssj3pj + '\'' +
                ", bdcSlSfxx=" + bdcSlSfxx +
                ", bdcSlWxjjxxDO=" + bdcSlWxjjxxDO +
                ", zsbdcqzh='" + zsbdcqzh + '\'' +
                ", zsid='" + zsid + '\'' +
                ", xgzszh=" + xgzszh +
                '}';
    }
}
