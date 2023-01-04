package cn.gtmap.realestate.common.core.qo.init;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @description 不动产项目水电气业务查询用QO
 */
@ApiModel(value = "BdcSdqywQO",description = "不动产水电气查询参数封装对象")
public class BdcSdqywQO {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;

    @ApiModelProperty(value = "户号")
    private String consno ;

    @ApiModelProperty(value = "户主名称")
    private String hzmc;

    @ApiModelProperty(value = "新户主名称")
    private String xhzmc;

    @ApiModelProperty(value = "业务类型")
    private String type;

    @ApiModelProperty(value = "户主坐落")
    private String hzzl;

    @ApiModelProperty(value = "水业务状态")
    private Integer sywblzt;

    @ApiModelProperty(value = "电业务状态")
    private Integer dywblzt;

    @ApiModelProperty(value = "气业务状态")
    private Integer qywblzt;

    @ApiModelProperty(value = "是否办理水业务")
    private Integer sfblsyw;

    @ApiModelProperty(value = "是否办理电业务")
    private Integer sfbldyw;

    @ApiModelProperty(value = "是否办理气业务")
    private Integer sfblqyw;

    @ApiModelProperty(value = "是否是一窗")
    private String ycsl;

    @ApiModelProperty(value = "业务类型")
    private Integer ywlx;

    @ApiModelProperty(value = "业务类型列表")
    private List<Integer> ywlxList;

    @ApiModelProperty(value = "办理状态")
    private Integer blzt;

    @ApiModelProperty(value = "是否办理")
    private Integer sfbl;

    @ApiModelProperty(value = "燃气类型")
    private String rqlx;

    @ApiModelProperty(value = "燃气服务标识码")
    private String rqfwbsm;

    @ApiModelProperty(value = "推送次数")
    private Integer tscs;

    @ApiModelProperty(value = "排序字段,有值按照排序字段排序")
    private String sortParameter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getConsno() {
        return consno;
    }

    public void setConsno(String consno) {
        this.consno = consno;
    }

    public Integer getSywblzt() {
        return sywblzt;
    }

    public void setSywblzt(Integer sywblzt) {
        this.sywblzt = sywblzt;
    }

    public Integer getDywblzt() {
        return dywblzt;
    }

    public void setDywblzt(Integer dywblzt) {
        this.dywblzt = dywblzt;
    }

    public Integer getQywblzt() {
        return qywblzt;
    }

    public void setQywblzt(Integer qywblzt) {
        this.qywblzt = qywblzt;
    }

    public Integer getSfblsyw() {
        return sfblsyw;
    }

    public void setSfblsyw(Integer sfblsyw) {
        this.sfblsyw = sfblsyw;
    }

    public Integer getSfbldyw() {
        return sfbldyw;
    }

    public void setSfbldyw(Integer sfbldyw) {
        this.sfbldyw = sfbldyw;
    }

    public Integer getSfblqyw() {
        return sfblqyw;
    }

    public void setSfblqyw(Integer sfblqyw) {
        this.sfblqyw = sfblqyw;
    }

    public String getHzmc() {
        return hzmc;
    }

    public void setHzmc(String hzmc) {
        this.hzmc = hzmc;
    }

    public String getXhzmc() {
        return xhzmc;
    }

    public void setXhzmc(String xhzmc) {
        this.xhzmc = xhzmc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHzzl() {
        return hzzl;
    }

    public void setHzzl(String hzzl) {
        this.hzzl = hzzl;
    }

    public String getYcsl() {
        return ycsl;
    }

    public void setYcsl(String ycsl) {
        this.ycsl = ycsl;
    }

    public Integer getYwlx() {
        return ywlx;
    }

    public void setYwlx(Integer ywlx) {
        this.ywlx = ywlx;
    }

    public Integer getBlzt() {
        return blzt;
    }

    public void setBlzt(Integer blzt) {
        this.blzt = blzt;
    }

    public Integer getSfbl() {
        return sfbl;
    }

    public void setSfbl(Integer sfbl) {
        this.sfbl = sfbl;
    }

    public String getSortParameter() {
        return sortParameter;
    }

    public void setSortParameter(String sortParameter) {
        this.sortParameter = sortParameter;
    }

    public String getRqlx() {
        return rqlx;
    }

    public void setRqlx(String rqlx) {
        this.rqlx = rqlx;
    }

    public String getRqfwbsm() {
        return rqfwbsm;
    }

    public void setRqfwbsm(String rqfwbsm) {
        this.rqfwbsm = rqfwbsm;
    }

    public List<Integer> getYwlxList() {
        return ywlxList;
    }

    public void setYwlxList(List<Integer> ywlxList) {
        this.ywlxList = ywlxList;
    }

    public Integer getTscs() {
        return tscs;
    }

    public void setTscs(Integer tscs) {
        this.tscs = tscs;
    }
}
