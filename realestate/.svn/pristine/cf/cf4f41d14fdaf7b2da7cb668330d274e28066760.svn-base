package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/12
 * @description 不动产受理项目前台
 */
@ApiModel(value = "BdcSlYwxxDTO", description = "不动产受理项目前台")
public class BdcSlYwxxDTO implements Serializable {
    private static final long serialVersionUID = 6977156639172758748L;
    /**
     * 前台已选数据的项目ID
     */
    @ApiModelProperty(value = "项目ID")
    private String xmid;

    /**
     * 前台已选数据的项目ID
     */
    @ApiModelProperty(value = "原项目ID")
    private String yxmid;

    /**
     * 前台已选数据的不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    /**
     * 前台已选数据的不动产单元号
     */
    @ApiModelProperty(value = "不动产单元唯一编号")
    private String bdcdywybh;

    /**
     * 前台已选数据的登记小类
     */
    @ApiModelProperty(value = "登记小类")
    private String djxl;

    /**
     * 前台已选数据的不动产权证号
     */
    @ApiModelProperty(value = "原不动产证号")
    private String ybdcqz;

    /**
     * 前台已选数据的坐落
     */
    @ApiModelProperty(value = "坐落")
    private String zl;

    /**
     * 前台已选数据的权利人
     */
    @ApiModelProperty(value = "权利人")
    private String qlr;

    /**
     * 前台已选数据的权籍ID
     */
    @ApiModelProperty(value = "权籍ID")
    private String qjid;

    @ApiModelProperty(value = "查封类型")
    private String cflx;

    /**
     * 前台已选数据的权利类型
     */
    @ApiModelProperty(value = "权利类型")
    private Integer qllx;

    @ApiModelProperty(value = "权利人数据来源 1：权籍 2：上一手权利人 3：上一手义务人")
    private String qlrsjly;

    @ApiModelProperty(value = "义务人数据来源 1：权籍 2：上一手权利人 3：上一手义务人")
    private String ywrsjly;

    @ApiModelProperty(value = "证书序号：用于组合发证 分组")
    private Integer zsxh;

    @ApiModelProperty(value = "权利数据来源 1：权籍 2：上一手  可组合(1,2)")
    private String qlsjly;

    @ApiModelProperty(value = "是否生成权利 0：否  1：是")
    private Integer sfscql;

    @ApiModelProperty(value = "是否主房  0：否  1：是")
    private Integer sfzf;

    @ApiModelProperty(value = "证书种类   1：证书  2：证明")
    private Integer zszl;

    @ApiModelProperty(value = "是否增量初始化业务  0：否  1：是")
    private Integer sfzlcsh;

    @ApiModelProperty(value = "注销原权利  0：否  1：是")
    private Integer zxyql;

    /**
     * 选择不动产单元时，调用的权籍接口,用于根据不动产单元号判断单元号权利类型
     */
    @ApiModelProperty(value = "单元号查询类型")
    private String dyhcxlx;

    @ApiModelProperty(value = "不动产类型")
    private Integer bdclx;

    /**
     * 前台已选逻辑幢主键，当选择逻辑幢时带入值。如果有值，查询对应的户室并根据户室生成项目
     */
    @ApiModelProperty(value = "房屋调查表主键")
    private String fwDcbIndex;

    /**
     * 选择权籍数据时查询的权籍表,主要用于根据不动产单元判断不动产类型
     */
    @ApiModelProperty(value = "权籍表类型")
    private String lx;

    /**
     * 前台选择数据自动关联外联证书时，外联历史关系集合
     */
    @ApiModelProperty(value = "外联历史关系集合")
    private List<BdcSlXmLsgxDO> bdcWlSlXmLsgxDOList;

    /**
     * 用途
     */
    @ApiModelProperty(value = "用途")
    private String yt;


    /**
     * 房产交易备案信息
     */
    @ApiModelProperty(value = "房产交易备案信息")
    private FcjyBaxxDTO fcjyBaxxDTO;


    /**
     *
     */
    @ApiModelProperty(value = "项目来源")
    private Integer xmly;

    @ApiModelProperty(value = "是否生成证书  0：否  1：是")
    private Integer sfsczs;

    /**
     *  抵押项目ID(带抵押流程)
     */
    @ApiModelProperty(value = "抵押项目ID(带抵押流程)")
    private List<String> dyxmidList;

    /**
     *  权籍管理代码
     */
    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;

    /**
     * 关联预告预抵押信息
     */
    @ApiModelProperty(value = "关联预告预抵押信息")
    private List<BdcSlYgDTO> bdcSlYgDTOS;

    /*
     * 工作流定义id
     * */

    @ApiModelProperty("工作流定义id")
    private String gzldyid;

    //选择的数据的权利类型
    @ApiModelProperty("选择的数据的权利类型")
    private Integer selectDataQllx;


    @ApiModelProperty("是否外联带抵押的产权证书,0:否 1：是")
    private Integer wlwithdyZs;

    @ApiModelProperty(value = "权籍权利人关系ID-用于土地承包经营权，记录承包方与承包宗地关系ID")
    private String qjqlrgxid;

    @ApiModelProperty("建筑面积")
    private Double jzmj;

    @ApiModelProperty("是否查询交易信息并带入")
    private Boolean drjyxx = false;


    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getYbdcqz() {
        return ybdcqz;
    }

    public void setYbdcqz(String ybdcqz) {
        this.ybdcqz = ybdcqz;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getQjid() {
        return qjid;
    }

    public void setQjid(String qjid) {
        this.qjid = qjid;
    }

    public String getCflx() {
        return cflx;
    }

    public void setCflx(String cflx) {
        this.cflx = cflx;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public String getQlrsjly() {
        return qlrsjly;
    }

    public void setQlrsjly(String qlrsjly) {
        this.qlrsjly = qlrsjly;
    }

    public String getYwrsjly() {
        return ywrsjly;
    }

    public void setYwrsjly(String ywrsjly) {
        this.ywrsjly = ywrsjly;
    }

    public Integer getZsxh() {
        return zsxh;
    }

    public void setZsxh(Integer zsxh) {
        this.zsxh = zsxh;
    }

    public String getQlsjly() {
        return qlsjly;
    }

    public void setQlsjly(String qlsjly) {
        this.qlsjly = qlsjly;
    }

    public Integer getSfscql() {
        return sfscql;
    }

    public void setSfscql(Integer sfscql) {
        this.sfscql = sfscql;
    }

    public Integer getSfzf() {
        return sfzf;
    }

    public void setSfzf(Integer sfzf) {
        this.sfzf = sfzf;
    }

    public Integer getZszl() {
        return zszl;
    }

    public void setZszl(Integer zszl) {
        this.zszl = zszl;
    }

    public Integer getSfzlcsh() {
        return sfzlcsh;
    }

    public void setSfzlcsh(Integer sfzlcsh) {
        this.sfzlcsh = sfzlcsh;
    }

    public Integer getZxyql() {
        return zxyql;
    }

    public void setZxyql(Integer zxyql) {
        this.zxyql = zxyql;
    }

    public String getDyhcxlx() {
        return dyhcxlx;
    }

    public void setDyhcxlx(String dyhcxlx) {
        this.dyhcxlx = dyhcxlx;
    }

    public Integer getBdclx() {
        return bdclx;
    }

    public void setBdclx(Integer bdclx) {
        this.bdclx = bdclx;
    }

    public String getFwDcbIndex() {
        return fwDcbIndex;
    }

    public void setFwDcbIndex(String fwDcbIndex) {
        this.fwDcbIndex = fwDcbIndex;
    }

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    public List<BdcSlXmLsgxDO> getBdcWlSlXmLsgxDOList() {
        return bdcWlSlXmLsgxDOList;
    }

    public void setBdcWlSlXmLsgxDOList(List<BdcSlXmLsgxDO> bdcWlSlXmLsgxDOList) {
        this.bdcWlSlXmLsgxDOList = bdcWlSlXmLsgxDOList;
    }

    public FcjyBaxxDTO getFcjyBaxxDTO() {
        return fcjyBaxxDTO;
    }

    public void setFcjyBaxxDTO(FcjyBaxxDTO fcjyBaxxDTO) {
        this.fcjyBaxxDTO = fcjyBaxxDTO;
    }


    public String getYxmid() {
        return yxmid;
    }

    public void setYxmid(String yxmid) {
        this.yxmid = yxmid;
    }

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
    }

    public Integer getXmly() {
        return xmly;
    }

    public void setXmly(Integer xmly) {
        this.xmly = xmly;
    }

    public Integer getSfsczs() {
        return sfsczs;
    }

    public void setSfsczs(Integer sfsczs) {
        this.sfsczs = sfsczs;
    }

    public List<String> getDyxmidList() {
        return dyxmidList;
    }

    public void setDyxmidList(List<String> dyxmidList) {
        this.dyxmidList = dyxmidList;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public List<BdcSlYgDTO> getBdcSlYgDTOS() {
        return bdcSlYgDTOS;
    }

    public void setBdcSlYgDTOS(List<BdcSlYgDTO> bdcSlYgDTOS) {
        this.bdcSlYgDTOS = bdcSlYgDTOS;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public Integer getSelectDataQllx() {
        return selectDataQllx;
    }

    public void setSelectDataQllx(Integer selectDataQllx) {
        this.selectDataQllx = selectDataQllx;
    }

    public Integer getWlwithdyZs() {
        return wlwithdyZs;
    }

    public void setWlwithdyZs(Integer wlwithdyZs) {
        this.wlwithdyZs = wlwithdyZs;
    }

    public String getQjqlrgxid() {
        return qjqlrgxid;
    }

    public void setQjqlrgxid(String qjqlrgxid) {
        this.qjqlrgxid = qjqlrgxid;
    }


    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    public Boolean getDrjyxx() {
        return drjyxx;
    }

    public void setDrjyxx(Boolean drjyxx) {
        this.drjyxx = drjyxx;
    }

    @Override
    public String toString() {
        return "BdcSlYwxxDTO{" +
                "xmid='" + xmid + '\'' +
                ", yxmid='" + yxmid + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdywybh='" + bdcdywybh + '\'' +
                ", djxl='" + djxl + '\'' +
                ", ybdcqz='" + ybdcqz + '\'' +
                ", zl='" + zl + '\'' +
                ", qlr='" + qlr + '\'' +
                ", qjid='" + qjid + '\'' +
                ", cflx='" + cflx + '\'' +
                ", qllx=" + qllx +
                ", qlrsjly='" + qlrsjly + '\'' +
                ", ywrsjly='" + ywrsjly + '\'' +
                ", zsxh=" + zsxh +
                ", qlsjly='" + qlsjly + '\'' +
                ", sfscql=" + sfscql +
                ", sfzf=" + sfzf +
                ", zszl=" + zszl +
                ", sfzlcsh=" + sfzlcsh +
                ", zxyql=" + zxyql +
                ", dyhcxlx='" + dyhcxlx + '\'' +
                ", bdclx=" + bdclx +
                ", fwDcbIndex='" + fwDcbIndex + '\'' +
                ", lx='" + lx + '\'' +
                ", bdcWlSlXmLsgxDOList=" + bdcWlSlXmLsgxDOList +
                ", yt='" + yt + '\'' +
                ", fcjyBaxxDTO=" + fcjyBaxxDTO +
                ", xmly=" + xmly +
                ", sfsczs=" + sfsczs +
                ", dyxmidList=" + dyxmidList +
                ", qjgldm='" + qjgldm + '\'' +
                ", bdcSlYgDTOS=" + bdcSlYgDTOS +
                ", gzldyid='" + gzldyid + '\'' +
                ", selectDataQllx=" + selectDataQllx +
                ", wlwithdyZs=" + wlwithdyZs +
                ", qjqlrgxid='" + qjqlrgxid + '\'' +
                ", jzmj=" + jzmj +
                ", drjyxx=" + drjyxx +
                '}';
    }

    /**
     * @param anObject
     * @author CaptainY
     * @description 重写equals方法
     */
    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof BdcSlYwxxDTO) {
            BdcSlYwxxDTO anotherBdcSlYwxxDTO = (BdcSlYwxxDTO)anObject;
           if(this.bdcdyh!=null&&bdcdyh.equals(anotherBdcSlYwxxDTO.getBdcdyh()) &&(StringUtils.isAnyBlank(this.xmid,anotherBdcSlYwxxDTO.getXmid()) ||this.xmid!=null&&xmid.equals(anotherBdcSlYwxxDTO.getXmid()))){
               return true;
           }
        }
        return false;
    }

}
