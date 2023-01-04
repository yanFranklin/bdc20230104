package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.accept.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/7/17
 * @description 推送到税务的数据
 */
@ApiModel(value = "TsswDataDTO", description = "推送到税务的数据模型")
public class TsswDataDTO {

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "房屋uuid，税务接口需要的唯一标识")
    private String fwuuid;

    @ApiModelProperty(value = "土地业务标记，1：土地 0：房屋 ")
    private String tdbz;

    @ApiModelProperty(value = "不动产受理基本信息")
    private BdcSlJbxxDO bdcSlJbxx;

    @ApiModelProperty(value = "不动产受理房屋信息")
    private BdcSlFwxxDO bdcSlFwxx;

    @ApiModelProperty(value = "不动产受理项目")
    private BdcSlXmDO bdcSlXm;

    @ApiModelProperty(value = "不动产受理交易信息")
    private BdcSlJyxxDO bdcSlJyxx;

    @ApiModelProperty(value = "不动产受理交易差额征收信息")
    private BdcSlJyxxCezsDO bdcSlJyxxCezsDO;

    @ApiModelProperty(value = "土地出让金信息")
    private List<BdcSlTdcrjDO> bdcSlTdcrjDOList;

    @ApiModelProperty(value = "不动产房地产权信息")
    private BdcFdcqDO bdcFdcqDO;

    @ApiModelProperty(value = "不动产受理申请人信息")
    private List<BdcSlSqrDO> sqrList;

    @ApiModelProperty(value = "不动产受理申请人（附带配偶信息）信息")
    private List<BdcSlSqrSwDTO> sqrSwList;

    @ApiModelProperty(value = "附件材料信息列表")
    private List<TsswDataFjclDTO> fjclList;

    @ApiModelProperty(value = "合同签订时间")
    private String htqdsj;

    @ApiModelProperty(value = "契税权属转移用途代码")
    private String qsqszyytDm;

    @ApiModelProperty(value = "房屋类型 住房 Y 非住房 N")
    private String sfptzfbz;

    @ApiModelProperty(value = "权属转移面积")
    private Double qszymj;

    @ApiModelProperty(value = "储藏室面积")
    private Double ccsmj;

    @ApiModelProperty(value = "阁楼面积")
    private Double glmj;

    @ApiModelProperty(value = "车库面积")
    private Double ckmj;
    
    @ApiModelProperty(value = "权属转移对象")
    private String qsqszydxDm;

    @ApiModelProperty(value = "项目ID集合")
    private List<String> xmids;

    @ApiModelProperty(value = "标识： 1（商品房）2（存量房）")
    private String bs;

    @ApiModelProperty(value = "附记信息")
    private String fj;

    @ApiModelProperty(value = "不动产项目信息")
    private BdcXmDO bdcxm;

    @ApiModelProperty(value = "不动产项目信息")
    private BdcXmFbDO bdcxmfb;

    @ApiModelProperty(value = "推送系统id")
    private String tsxtid;

    @ApiModelProperty(value = "一业务id")
    private String ythywid;

    @ApiModelProperty(value = "权利人共有方式")
    private String qlrGyfs;

    @ApiModelProperty(value = "义务人共有方式")
    private String ywrGyfs;

    @ApiModelProperty(value = "并案业务信息")
    private SwMergeDTO swMergeDTO;

    @ApiModelProperty(value = "数据归属地区")
    private String sjgsdq;

    @ApiModelProperty(value = "录入人员代码")
    private String lrrdm;

    public List<BdcSlTdcrjDO> getBdcSlTdcrjDOList() {
        return bdcSlTdcrjDOList;
    }

    public void setBdcSlTdcrjDOList(List<BdcSlTdcrjDO> bdcSlTdcrjDOList) {
        this.bdcSlTdcrjDOList = bdcSlTdcrjDOList;
    }

    public String getQsqszydxDm() {
        return qsqszydxDm;
    }

    public void setQsqszydxDm(String qsqszydxDm) {
        this.qsqszydxDm = qsqszydxDm;
    }

    public Double getQszymj() {
        return qszymj;
    }

    public void setQszymj(Double qszymj) {
        this.qszymj = qszymj;
    }

    public Double getCcsmj() {
        return ccsmj;
    }

    public void setCcsmj(Double ccsmj) {
        this.ccsmj = ccsmj;
    }

    public Double getGlmj() {
        return glmj;
    }

    public void setGlmj(Double glmj) {
        this.glmj = glmj;
    }

    public Double getCkmj() {
        return ckmj;
    }

    public void setCkmj(Double ckmj) {
        this.ckmj = ckmj;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public BdcSlJbxxDO getBdcSlJbxx() {
        return bdcSlJbxx;
    }

    public void setBdcSlJbxx(BdcSlJbxxDO bdcSlJbxx) {
        this.bdcSlJbxx = bdcSlJbxx;
    }

    public BdcSlFwxxDO getBdcSlFwxx() {
        return bdcSlFwxx;
    }

    public void setBdcSlFwxx(BdcSlFwxxDO bdcSlFwxx) {
        this.bdcSlFwxx = bdcSlFwxx;
    }

    public BdcSlXmDO getBdcSlXm() {
        return bdcSlXm;
    }

    public void setBdcSlXm(BdcSlXmDO bdcSlXm) {
        this.bdcSlXm = bdcSlXm;
    }

    public BdcSlJyxxDO getBdcSlJyxx() {
        return bdcSlJyxx;
    }

    public void setBdcSlJyxx(BdcSlJyxxDO bdcSlJyxx) {
        this.bdcSlJyxx = bdcSlJyxx;
    }

    public BdcFdcqDO getBdcFdcqDO() {
        return bdcFdcqDO;
    }

    public void setBdcFdcqDO(BdcFdcqDO bdcFdcqDO) {
        this.bdcFdcqDO = bdcFdcqDO;
    }

    public List<BdcSlSqrDO> getSqrList() {
        return sqrList;
    }

    public void setSqrList(List<BdcSlSqrDO> sqrList) {
        this.sqrList = sqrList;
    }

    public List<TsswDataFjclDTO> getFjclList() {
        return fjclList;
    }

    public void setFjclList(List<TsswDataFjclDTO> fjclList) {
        this.fjclList = fjclList;
    }

    public BdcSlJyxxCezsDO getBdcSlJyxxCezsDO() {
        return bdcSlJyxxCezsDO;
    }

    public String getHtqdsj() {
        return htqdsj;
    }

    public void setHtqdsj(String htqdsj) {
        this.htqdsj = htqdsj;
    }

    public String getQsqszyytDm() {
        return qsqszyytDm;
    }

    public void setQsqszyytDm(String qsqszyytDm) {
        this.qsqszyytDm = qsqszyytDm;
    }

    public String getSfptzfbz() {
        return sfptzfbz;
    }

    public void setSfptzfbz(String sfptzfbz) {
        this.sfptzfbz = sfptzfbz;
    }

    public void setBdcSlJyxxCezsDO(BdcSlJyxxCezsDO bdcSlJyxxCezsDO) {
        this.bdcSlJyxxCezsDO = bdcSlJyxxCezsDO;
    }

    public List<BdcSlSqrSwDTO> getSqrSwList() {
        return sqrSwList;
    }

    public void setSqrSwList(List<BdcSlSqrSwDTO> sqrSwList) {
        this.sqrSwList = sqrSwList;
    }

    public List<String> getXmids() {
        return xmids;
    }

    public void setXmids(List<String> xmids) {
        this.xmids = xmids;
    }

    public String getBs() {
        return bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public BdcXmDO getBdcxm() {
        return bdcxm;
    }

    public void setBdcxm(BdcXmDO bdcxm) {
        this.bdcxm = bdcxm;
    }

    public String getFwuuid() {
        return fwuuid;
    }

    public void setFwuuid(String fwuuid) {
        this.fwuuid = fwuuid;
    }

    public String getTsxtid() {
        return tsxtid;
    }

    public void setTsxtid(String tsxtid) {
        this.tsxtid = tsxtid;
    }

    public String getYthywid() {
        return ythywid;
    }

    public void setYthywid(String ythywid) {
        this.ythywid = ythywid;
    }

    public String getQlrGyfs() {
        return qlrGyfs;
    }

    public void setQlrGyfs(String qlrGyfs) {
        this.qlrGyfs = qlrGyfs;
    }

    public String getYwrGyfs() {
        return ywrGyfs;
    }

    public void setYwrGyfs(String ywrGyfs) {
        this.ywrGyfs = ywrGyfs;
    }

    public SwMergeDTO getSwMergeDTO() {
        return swMergeDTO;
    }

    public void setSwMergeDTO(SwMergeDTO swMergeDTO) {
        this.swMergeDTO = swMergeDTO;
    }

    public String getSjgsdq() {
        return sjgsdq;
    }

    public void setSjgsdq(String sjgsdq) {
        this.sjgsdq = sjgsdq;
    }

    public String getLrrdm() {
        return lrrdm;
    }

    public void setLrrdm(String lrrdm) {
        this.lrrdm = lrrdm;
    }

    public String getTdbz() {
        return tdbz;
    }

    public void setTdbz(String tdbz) {
        this.tdbz = tdbz;
    }

    public BdcXmFbDO getBdcxmfb() {
        return bdcxmfb;
    }

    public void setBdcxmfb(BdcXmFbDO bdcxmfb) {
        this.bdcxmfb = bdcxmfb;
    }

    @Override
    public String toString() {
        return "TsswDataDTO{" +
                "xmid='" + xmid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", fwuuid='" + fwuuid + '\'' +
                ", tdbz='" + tdbz + '\'' +
                ", bdcSlJbxx=" + bdcSlJbxx +
                ", bdcSlFwxx=" + bdcSlFwxx +
                ", bdcSlXm=" + bdcSlXm +
                ", bdcSlJyxx=" + bdcSlJyxx +
                ", bdcSlJyxxCezsDO=" + bdcSlJyxxCezsDO +
                ", bdcSlTdcrjDOList=" + bdcSlTdcrjDOList +
                ", bdcFdcqDO=" + bdcFdcqDO +
                ", sqrList=" + sqrList +
                ", sqrSwList=" + sqrSwList +
                ", fjclList=" + fjclList +
                ", htqdsj='" + htqdsj + '\'' +
                ", qsqszyytDm='" + qsqszyytDm + '\'' +
                ", sfptzfbz='" + sfptzfbz + '\'' +
                ", qszymj=" + qszymj +
                ", ccsmj=" + ccsmj +
                ", glmj=" + glmj +
                ", ckmj=" + ckmj +
                ", qsqszydxDm='" + qsqszydxDm + '\'' +
                ", xmids=" + xmids +
                ", bs='" + bs + '\'' +
                ", fj='" + fj + '\'' +
                ", bdcxm=" + bdcxm +
                ", bdcxmfb=" + bdcxmfb +
                ", tsxtid='" + tsxtid + '\'' +
                ", ythywid='" + ythywid + '\'' +
                ", qlrGyfs='" + qlrGyfs + '\'' +
                ", ywrGyfs='" + ywrGyfs + '\'' +
                ", swMergeDTO=" + swMergeDTO +
                ", sjgsdq='" + sjgsdq + '\'' +
                ", lrrdm='" + lrrdm + '\'' +
                '}';
    }
}
