package cn.gtmap.realestate.common.core.dto.analysis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2018/12/20
 * @description
 */
@ApiModel(value = "BdcCqXxDTO", description = "住房查询信息")
public class BdcCqXxDTO {

    @ApiModelProperty(value = "来源", hidden = true)
    private String ly;

    @ApiModelProperty(value = "项目ID", hidden = true)
    private String xmid;

    @ApiModelProperty("不动产权证号")
    private String bdcqzh;

    @ApiModelProperty("共用不动产权证号")
    private String gybdcqzh;

    @ApiModelProperty("交易合同号")
    private String jyhth;

    @ApiModelProperty("不动产单元号")
    private String bdcdyh;

    @ApiModelProperty("不动产单元唯一编号")
    private String bdcdywybh;

    @ApiModelProperty("权利类型名称")
    private String qllx;

    @ApiModelProperty("权利类型代码")
    private Integer qllxdm;

    @ApiModelProperty("权利性质名称")
    private String qlxz;

    @ApiModelProperty("权利性质代码")
    private Integer qlxzdm;

    @ApiModelProperty("权属状态名称")
    private String qszt;

    @ApiModelProperty("权属状态代码")
    private Integer qsztdm;

    @ApiModelProperty(value = "登簿时间", example = "2018-12-20 20:00:00")
    private String djsj;

    @ApiModelProperty(value = "权利起始时间", example = "2018-12-20 20:00:00")
    private String qlqssj;

    @ApiModelProperty(value = "权利结束时间", example = "2018-12-20 20:00:00")
    private String qljssj;

    @ApiModelProperty("不动产类型名称")
    private String bdclx;

    @ApiModelProperty("不动产类型代码")
    private Integer bdclxdm;

    @ApiModelProperty("房屋结名称")
    private String fwjg;

    @ApiModelProperty("房屋结构代码")
    private Integer fwjgdm;

    @ApiModelProperty("房屋性质名称")
    private String fwxz;

    @ApiModelProperty("房屋性质代码")
    private Integer fwxzdm;

    @ApiModelProperty("房屋类型名称")
    private String fwlx;

    @ApiModelProperty("房屋类型代码")
    private Integer fwlxdm;

    @ApiModelProperty("坐落")
    private String zl;

    @ApiModelProperty("房间号")
    private String fjh;

    @ApiModelProperty("所在层")
    private String szc;

    @ApiModelProperty("所在名义层")
    private String szmyc;

    @ApiModelProperty("总层数")
    private String zcs;

    @ApiModelProperty("名义总层数")
    private String myzcs;

    @ApiModelProperty("建筑面积")
    private String jzmj;

    @ApiModelProperty("套内建筑面积")
    private String tnjzmj;

    @ApiModelProperty("分摊建筑面积")
    private String ftjzmj;

    @ApiModelProperty("专有建筑面积")
    private String zyjzmj;

    @ApiModelProperty("交易价格")
    private String jyjg;

    @ApiModelProperty("规划用途名称")
    private String ghyt;

    @ApiModelProperty("规划用途代码")
    private Integer ghytdm;

    @ApiModelProperty(value = "竣工时间", example = "2018-12-20 20:00:00")
    private String jgsj;

    @ApiModelProperty("分摊土地面积")
    private String fttdmj;

    @ApiModelProperty("独用土地面积")
    private String dytdmj;

    @ApiModelProperty("使用权面积")
    private String syqmj;

    @ApiModelProperty("承包面积")
    private String cbmj;

    @ApiModelProperty("土地使用权人")
    private String tdsyqr;

    @ApiModelProperty(value = "土地使用起始时间", example = "2018-12-20 20:00:00")
    private String tdsyqssj;

    @ApiModelProperty(value = "土地使用结束时间", example = "2018-12-20 20:00:00")
    private String tdsyjssj;

    @ApiModelProperty("宗地宗海用途名称")
    private String zdzhyt;

    @ApiModelProperty("宗地宗海用途代码")
    private Integer zdzhytdm;

    @ApiModelProperty("宗地宗海用途2名称")
    private String zdzhyt2;

    @ApiModelProperty("宗地宗海用途2代码")
    private Integer zdzhyt2dm;

    @ApiModelProperty("宗地宗海用途3名称")
    private String zdzhyt3;

    @ApiModelProperty("宗地宗海用途3代码")
    private Integer zdzhyt3dm;

    @ApiModelProperty("宗地宗海面积")
    private String zdzhmj;

    @ApiModelProperty("项目备注")
    private String bz;

    @ApiModelProperty("权利附记")
    private String fj;

    @ApiModelProperty("共有方式")
    private String gyfs;

    @ApiModelProperty("共有方式代码")
    private Integer gyfsdm;

    @ApiModelProperty("附属设施信息")
    private List<BdcFsssXxDTO> fsssxx;

    @ApiModelProperty("项目内多幢信息")
    private List<BdcXmnDzXxDTO> xmndzxx;

    @ApiModelProperty("权利人信息")
    private List<BdcQlrXxDTO> qlrxx;

    @ApiModelProperty("义务人信息")
    private List<BdcYwrXxDTO> ywrxx;

    @ApiModelProperty("限制状态")
    private BdcXzztXxDTO xzzt;

    @ApiModelProperty("限制信息")
    private BdcXzxxDTO xzxx;

    public String getLy() {
        return ly;
    }

    public void setLy(String ly) {
        this.ly = ly;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getGybdcqzh() {
        return gybdcqzh;
    }

    public void setGybdcqzh(String gybdcqzh) {
        this.gybdcqzh = gybdcqzh;
    }

    public String getJyhth() {
        return jyhth;
    }

    public void setJyhth(String jyhth) {
        this.jyhth = jyhth;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public Integer getQllxdm() {
        return qllxdm;
    }

    public void setQllxdm(Integer qllxdm) {
        this.qllxdm = qllxdm;
    }

    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    public Integer getQlxzdm() {
        return qlxzdm;
    }

    public void setQlxzdm(Integer qlxzdm) {
        this.qlxzdm = qlxzdm;
    }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public Integer getQsztdm() {
        return qsztdm;
    }

    public void setQsztdm(Integer qsztdm) {
        this.qsztdm = qsztdm;
    }

    public String getDjsj() {
        return djsj;
    }

    public void setDjsj(String djsj) {
        this.djsj = djsj;
    }

    public String getQlqssj() {
        return qlqssj;
    }

    public void setQlqssj(String qlqssj) {
        this.qlqssj = qlqssj;
    }

    public String getQljssj() {
        return qljssj;
    }

    public void setQljssj(String qljssj) {
        this.qljssj = qljssj;
    }

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    public Integer getBdclxdm() {
        return bdclxdm;
    }

    public void setBdclxdm(Integer bdclxdm) {
        this.bdclxdm = bdclxdm;
    }

    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    public Integer getFwjgdm() {
        return fwjgdm;
    }

    public void setFwjgdm(Integer fwjgdm) {
        this.fwjgdm = fwjgdm;
    }

    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    public Integer getFwxzdm() {
        return fwxzdm;
    }

    public void setFwxzdm(Integer fwxzdm) {
        this.fwxzdm = fwxzdm;
    }

    public String getFwlx() {
        return fwlx;
    }

    public void setFwlx(String fwlx) {
        this.fwlx = fwlx;
    }

    public Integer getFwlxdm() {
        return fwlxdm;
    }

    public void setFwlxdm(Integer fwlxdm) {
        this.fwlxdm = fwlxdm;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getSzc() {
        return szc;
    }

    public void setSzc(String szc) {
        this.szc = szc;
    }

    public String getSzmyc() {
        return szmyc;
    }

    public void setSzmyc(String szmyc) {
        this.szmyc = szmyc;
    }

    public String getZcs() {
        return zcs;
    }

    public void setZcs(String zcs) {
        this.zcs = zcs;
    }

    public String getMyzcs() {
        return myzcs;
    }

    public void setMyzcs(String myzcs) {
        this.myzcs = myzcs;
    }

    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    public String getTnjzmj() {
        return tnjzmj;
    }

    public void setTnjzmj(String tnjzmj) {
        this.tnjzmj = tnjzmj;
    }

    public String getFtjzmj() {
        return ftjzmj;
    }

    public void setFtjzmj(String ftjzmj) {
        this.ftjzmj = ftjzmj;
    }

    public String getZyjzmj() {
        return zyjzmj;
    }

    public void setZyjzmj(String zyjzmj) {
        this.zyjzmj = zyjzmj;
    }

    public String getJyjg() {
        return jyjg;
    }

    public void setJyjg(String jyjg) {
        this.jyjg = jyjg;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    public Integer getGhytdm() {
        return ghytdm;
    }

    public void setGhytdm(Integer ghytdm) {
        this.ghytdm = ghytdm;
    }

    public String getJgsj() {
        return jgsj;
    }

    public void setJgsj(String jgsj) {
        this.jgsj = jgsj;
    }

    public String getFttdmj() {
        return fttdmj;
    }

    public void setFttdmj(String fttdmj) {
        this.fttdmj = fttdmj;
    }

    public String getDytdmj() {
        return dytdmj;
    }

    public void setDytdmj(String dytdmj) {
        this.dytdmj = dytdmj;
    }

    public String getSyqmj() {
        return syqmj;
    }

    public void setSyqmj(String syqmj) {
        this.syqmj = syqmj;
    }

    public String getCbmj() {
        return cbmj;
    }

    public void setCbmj(String cbmj) {
        this.cbmj = cbmj;
    }

    public String getTdsyqr() {
        return tdsyqr;
    }

    public void setTdsyqr(String tdsyqr) {
        this.tdsyqr = tdsyqr;
    }

    public String getTdsyqssj() {
        return tdsyqssj;
    }

    public void setTdsyqssj(String tdsyqssj) {
        this.tdsyqssj = tdsyqssj;
    }

    public String getTdsyjssj() {
        return tdsyjssj;
    }

    public void setTdsyjssj(String tdsyjssj) {
        this.tdsyjssj = tdsyjssj;
    }

    public String getZdzhyt() {
        return zdzhyt;
    }

    public void setZdzhyt(String zdzhyt) {
        this.zdzhyt = zdzhyt;
    }

    public Integer getZdzhytdm() {
        return zdzhytdm;
    }

    public void setZdzhytdm(Integer zdzhytdm) {
        this.zdzhytdm = zdzhytdm;
    }

    public String getZdzhyt2() {
        return zdzhyt2;
    }

    public void setZdzhyt2(String zdzhyt2) {
        this.zdzhyt2 = zdzhyt2;
    }

    public Integer getZdzhyt2dm() {
        return zdzhyt2dm;
    }

    public void setZdzhyt2dm(Integer zdzhyt2dm) {
        this.zdzhyt2dm = zdzhyt2dm;
    }

    public String getZdzhyt3() {
        return zdzhyt3;
    }

    public void setZdzhyt3(String zdzhyt3) {
        this.zdzhyt3 = zdzhyt3;
    }

    public Integer getZdzhyt3dm() {
        return zdzhyt3dm;
    }

    public void setZdzhyt3dm(Integer zdzhyt3dm) {
        this.zdzhyt3dm = zdzhyt3dm;
    }

    public String getZdzhmj() {
        return zdzhmj;
    }

    public void setZdzhmj(String zdzhmj) {
        this.zdzhmj = zdzhmj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getGyfs() {
        return gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    public Integer getGyfsdm() {
        return gyfsdm;
    }

    public void setGyfsdm(Integer gyfsdm) {
        this.gyfsdm = gyfsdm;
    }

    public List<BdcFsssXxDTO> getFsssxx() {
        return fsssxx;
    }

    public void setFsssxx(List<BdcFsssXxDTO> fsssxx) {
        this.fsssxx = fsssxx;
    }

    public List<BdcXmnDzXxDTO> getXmndzxx() {
        return xmndzxx;
    }

    public void setXmndzxx(List<BdcXmnDzXxDTO> xmndzxx) {
        this.xmndzxx = xmndzxx;
    }

    public List<BdcQlrXxDTO> getQlrxx() {
        return qlrxx;
    }

    public void setQlrxx(List<BdcQlrXxDTO> qlrxx) {
        this.qlrxx = qlrxx;
    }

    public List<BdcYwrXxDTO> getYwrxx() {
        return ywrxx;
    }

    public void setYwrxx(List<BdcYwrXxDTO> ywrxx) {
        this.ywrxx = ywrxx;
    }

    public BdcXzztXxDTO getXzzt() {
        return xzzt;
    }

    public void setXzzt(BdcXzztXxDTO xzzt) {
        this.xzzt = xzzt;
    }

    public BdcXzxxDTO getXzxx() {
        return xzxx;
    }

    public void setXzxx(BdcXzxxDTO xzxx) {
        this.xzxx = xzxx;
    }
}
