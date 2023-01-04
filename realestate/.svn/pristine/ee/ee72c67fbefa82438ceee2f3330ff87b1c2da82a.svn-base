package cn.gtmap.realestate.common.core.dto.init;

import cn.gtmap.realestate.common.core.domain.BdcGgDO;
import cn.gtmap.realestate.common.core.domain.BdcGgywsjDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.vo.inquiryui.BdcGgVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @program: realestate
 * @description: 公告页面DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-21 16:22
 **/
public class BdcGgDTO extends BdcGgDO {
    @ApiModelProperty(name = "坐落")
    private String zl;

    @ApiModelProperty(name = "项目信息")
    private List<BdcXmDO> bdcXmDOList;

    @ApiModelProperty(name = "公告业务数据信息")
    private List<BdcGgywsjDO> bdcGgywsjDOList;

    @ApiModelProperty(name = "权利人")
    private String qlr;

    @ApiModelProperty(name = "义务人")
    private String ywr;

    @ApiModelProperty("原产权证号")
    private String ycqzh;

    @ApiModelProperty(name = "公告内容")
    private BdcGgVO bdcGgVO;

    @ApiModelProperty(name = "异议书面材料送达地址")
    private String yysmclsddz;

    @ApiModelProperty(name = "联系方式")
    private String lxfs;

    public Boolean getSfsc() {
        return sfsc;
    }

    public void setSfsc(Boolean sfsc) {
        this.sfsc = sfsc;
    }

    @ApiModelProperty(name = "是否删除")
    private Boolean sfsc;


    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public List<BdcXmDO> getBdcXmDOList() {
        return bdcXmDOList;
    }

    public void setBdcXmDOList(List<BdcXmDO> bdcXmDOList) {
        this.bdcXmDOList = bdcXmDOList;
    }

    public List<BdcGgywsjDO> getBdcGgywsjDOList() {
        return bdcGgywsjDOList;
    }

    public void setBdcGgywsjDOList(List<BdcGgywsjDO> bdcGgywsjDOList) {
        this.bdcGgywsjDOList = bdcGgywsjDOList;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public BdcGgVO getBdcGgVO() {
        return bdcGgVO;
    }

    public void setBdcGgVO(BdcGgVO bdcGgVO) {
        this.bdcGgVO = bdcGgVO;
    }

    public String getYcqzh() {
        return ycqzh;
    }

    public void setYcqzh(String ycqzh) {
        this.ycqzh = ycqzh;
    }

    public String getYysmclsddz() { return yysmclsddz; }

    public void setYysmclsddz(String yysmclsddz) { this.yysmclsddz = yysmclsddz; }

    public String getLxfs() { return lxfs; }

    public void setLxfs(String lxfs) { this.lxfs = lxfs; }

    @Override
    public String toString() {
        return "BdcGgDTO{" +
                "zl='" + zl + '\'' +
                ", bdcXmDOList=" + bdcXmDOList +
                ", bdcGgywsjDOList=" + bdcGgywsjDOList +
                ", qlr='" + qlr + '\'' +
                ", ywr='" + ywr + '\'' +
                ", ycqzh='" + ycqzh + '\'' +
                ", bdcGgVO=" + bdcGgVO +
                ", yysmclsddz=" + yysmclsddz +
                ", lxfs=" + lxfs +
                '}';
    }
}
