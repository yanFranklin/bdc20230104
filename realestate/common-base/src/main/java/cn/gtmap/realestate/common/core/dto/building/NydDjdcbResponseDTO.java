package cn.gtmap.realestate.common.core.dto.building;

import cn.gtmap.realestate.common.core.domain.building.CbzdCbfDO;
import cn.gtmap.realestate.common.core.domain.building.CbzdFbfDO;
import cn.gtmap.realestate.common.core.domain.building.NydJtcyDO;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/16
 * @description 农用地调查簿DTO
 */
@ApiModel(value = "NydDjdcbResponseDTO", description = "农用地调查簿DTO")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz",defaultImpl = NydDjdcbResponseDTO.class)
public class NydDjdcbResponseDTO extends DjDcbResponseDTO {

    /** 农用地 属性 start*/
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String nydDjdcbIndex;
    /**
     * 权利人
     */
    @ApiModelProperty(value = "权利人")
    private String qlr;

    /**
     * 地籍号
     */
    @ApiModelProperty(value = "地籍号")
    private String djh;

    /**
     * 不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    /**
     * 土地使用者名称
     */
    @ApiModelProperty(value = "土地使用者名称")
    private String tdsyzmc;

    /**
     * 所在图幅号
     */
    @ApiModelProperty(value = "所在图幅号")
    private String sztfh;

    /**
     * 权属性质
     */
    @ApiModelProperty(value = "权属性质")
    private String qsxz;

    /**
     * 宗地特征码
     */
    @ApiModelProperty(value = "宗地特征码")
    private String qslx;

    /**
     * 土地坐落
     */
    @ApiModelProperty(value = "土地坐落")
    private String tdzl;

    /**
     * 使用权类型
     */
    @ApiModelProperty(value = "使用权类型")
    private String syqlx;

    /**
     * 比例尺
     */
    @ApiModelProperty(value = "比例尺")
    private String blc;

    /**
     * 土地用途
     */
    @ApiModelProperty(value = "土地用途")
    private String tdyt;

    /**
     * 起始日期
     */
    @ApiModelProperty(value = "起始日期",example = "2018-10-01 12:18:21")
    private Date qsrq;

    /**
     * 终止日期
     */
    @ApiModelProperty(value = "终止日期",example = "2018-10-01 12:18:21")
    private Date zzrq;

    /**
     * 土地用途2
     */
    @ApiModelProperty(value = "土地用途2")
    private String tdyt2;

    /**
     * 起始日期2
     */
    @ApiModelProperty(value = "起始日期2",example = "2018-10-01 12:18:21")
    private Date qsrq2;

    /**
     * 终止日期2
     */
    @ApiModelProperty(value = "终止日期2",example = "2018-10-01 12:18:21")
    private Date zzrq2;

    /**
     * 土地用途3
     */
    @ApiModelProperty(value = "土地用途3")
    private String tdyt3;

    /**
     * 起始日期3
     */
    @ApiModelProperty(value = "起始日期3",example = "2018-10-01 12:18:21")
    private Date qsrq3;

    /**
     * 终止日期3
     */
    @ApiModelProperty(value = "终止日期3",example = "2018-10-01 12:18:21")
    private Date zzrq3;

    /**
     * 面积单位
     */
    @ApiModelProperty(value = "面积单位")
    private String mjdw;

    /**
     * 实测面积
     */
    @ApiModelProperty(value = "实测面积")
    private Double scmj;

    /**
     * 实测面积亩
     */
    @ApiModelProperty(value = "实测面积亩")
    private Double scmjm;

    /**
     * 发证面积
     */
    @ApiModelProperty(value = "发证面积")
    private Double fzmj;

    /**
     * 发证面积亩
     */
    @ApiModelProperty(value = "发证面积亩")
    private Double fzmjm;

    /**
     * 建筑密度
     */
    @ApiModelProperty(value = "建筑密度")
    private Double jzmd;

    /**
     * 批准面积
     */
    @ApiModelProperty(value = "批准面积")
    private Double pzmj;

    /**
     * 批准面积亩
     */
    @ApiModelProperty(value = "批准面积亩")
    private Double pzmjm;

    /**
     * 建筑容积率
     */
    @ApiModelProperty(value = "建筑容积率")
    private Double jzrjl;

    /**
     * 批准用途
     */
    @ApiModelProperty(value = "批准用途")
    private String pzyt;

    /**
     * 土地级别
     */
    @ApiModelProperty(value = "土地级别")
    private String tdjb;

    /**
     * 等级
     */
    @ApiModelProperty(value = "等级")
    private String dj;

    /**
     * 申报低价
     */
    @ApiModelProperty(value = "申报低价")
    private Double sbdj;

    /**
     * 取得价格
     */
    @ApiModelProperty(value = "取得价格")
    private Double qdjg;

    /**
     * 占地用面积
     */
    @ApiModelProperty(value = "占地用面积")
    private Double zdzmj;

    /**
     * 建筑总面积
     */
    @ApiModelProperty(value = "建筑总面积")
    private Double jzzmj;

    /**
     * 建筑限高
     */
    @ApiModelProperty(value = "建筑限高")
    private Double jzxg;

    /**
     * 调查类型
     */
    @ApiModelProperty(value = "调查类型")
    private String dclx;

    /**
     * 权利设定方式
     */
    @ApiModelProperty(value = "权利设定方式")
    private String qlsdfs;

    /**
     * 来源权利编号
     */
    @ApiModelProperty(value = "来源权利编号")
    private String lyqlbh;

    /**
     * 宗地四至 北
     */
    @ApiModelProperty(value = "宗地四至 北")
    private String zdszb;

    /**
     * 宗地四至 东
     */
    @ApiModelProperty(value = "宗地四至 东")
    private String zdszd;

    /**
     * 宗地四至 南
     */
    @ApiModelProperty(value = "宗地四至 南")
    private String zdszn;

    /**
     * 宗地四至 西
     */
    @ApiModelProperty(value = "宗地四至 西")
    private String zdszx;

    /**
     * 土地权属来源证明材料
     */
    @ApiModelProperty(value = "土地权属来源证明材料")
    private String tdqslyzmcl;

    /**
     * 建筑物所有权共有权利人
     */
    @ApiModelProperty(value = "建筑物所有权共有权利人")
    private String jzwsuqgyqlr;

    /**
     * 共有共用情况
     */
    @ApiModelProperty(value = "共有共用情况")
    private String gysyqqk;

    /**
     * 国民经济行业分类代码
     */
    @ApiModelProperty(value = "国民经济行业分类代码")
    private String gmjjhyfldm;

    /**
     * 原编地籍号
     */
    @ApiModelProperty(value = "原编地籍号")
    private String ybdjh;

    /**
     * 原地籍号
     */
    @ApiModelProperty(value = "原地籍号")
    private String ydjh;

    /**
     * 宗地扫描件
     */
    @ApiModelProperty(value = "宗地扫描件")
    private String zdsmj;

    /**
     * 不动产单元号状态
     */
    @ApiModelProperty(value = "不动产单元号状态")
    private String bdcdyhzt;

    /**
     * 是否共用宗
     */
    @ApiModelProperty(value = "是否共用宗")
    private String sfgyz;

    /**
     *  是否虚宗
     */
    @ApiModelProperty(value = "是否虚宗")
    private String sfxz;

    /**
     * 邮政编码
     */
    @ApiModelProperty(value = "邮政编码")
    private String yzbm;

    /**
     * 建立日期
     */
    @ApiModelProperty(value = "建立日期",example = "2018-10-01 12:18:21")
    private Date jlrq;

    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期",example = "2018-10-01 12:18:21")
    private Date gxrq;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;

    /**
     * 用地用海分类
     */
    @ApiModelProperty(value = "用地用海分类")
    private String ydyhfl;

    /** 农用地 属性 end*/

    /** 承包宗地 属性 start*/

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String cbzdDcbIndex;
    /**
     * 宗地调查表主键
     */
    @ApiModelProperty(value = "宗地调查表主键")
    private String cbzdzddcbIndex;
    /**
     * 地籍号
     */
    @ApiModelProperty(value = "地籍号")
    private String cbzddjh;
    /**
     * 不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    private String cbzdbdcdyh;
    /**
     * 土地用途
     */
    @ApiModelProperty(value = "土地用途")
    private String cbzdtdyt;
    /**
     * 地力等级
     */
    @ApiModelProperty(value = "地力等级")
    private String cbzddldj;
    /**
     * 是否基本农田
     */
    @ApiModelProperty(value = "是否基本农田")
    private String cbzdsfjbnt;
    /**
     * 水域滩涂类型
     */
    @ApiModelProperty(value = "水域滩涂类型")
    private String cbzdsyttlx;
    /**
     * 养殖业方式
     */
    @ApiModelProperty(value = "养殖业方式")
    private String cbzdyzyfs;
    /**
     * 适宜载畜量
     */
    @ApiModelProperty(value = "适宜载畜量")
    private Long cbzdsyzxl;
    /**
     * 草层高度
     */
    @ApiModelProperty(value = "草层高度")
    private Double cbzdccgd;
    /**
     * 草地覆盖度
     */
    @ApiModelProperty(value = "草地覆盖度")
    private Double cbzdcdfgd;
    /**
     * 建群
     */
    @ApiModelProperty(value = "建群")
    private String cbzdcdjq;
    /**
     * 优势种
     */
    @ApiModelProperty(value = "优势种")
    private String cbzdcdysz;
    /**
     * 调查记事
     */
    @ApiModelProperty(value = "调查记事")
    private String cbzddcjs;
    /**
     * 调查员
     */
    @ApiModelProperty(value = "调查员")
    private String cbzddcy;
    /**
     * 调查日期
     */
    @ApiModelProperty(value = "调查日期",example = "2018-10-01 12:18:21")
    private Date cbzddcrq;
    /**
     * 审核意见
     */
    @ApiModelProperty(value = "审核意见")
    private String cbzdshyj;
    /**
     * 审核人
     */
    @ApiModelProperty(value = "审核人")
    private String cbzdshr;
    /**
     * 审核日期
     */
    @ApiModelProperty(value = "审核日期",example = "2018-10-01 12:18:21")
    private Date cbzdshrq;
    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期",example = "2018-10-01 12:18:21")
    private Date cbzdgxrq;
    /**
     * 家庭标示
     */
    @ApiModelProperty(value = "家庭标示")
    private String cbzdjtIndex;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String cbzdbz;
    /**
     * 地块编码
     */
    @ApiModelProperty(value = "地块编码")
    private String cbzddkbm;
    /**
     * 确权情况
     */
    @ApiModelProperty(value = "确权情况")
    private String cbzdqqqk;
    /**
     * 发包方编码
     */
    @ApiModelProperty(value = "发包方编码")
    private String cbzdfbfbm;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date cbzdgxsj;

    /** 承包宗地 属性 end*/

    /** 承包宗地合同属性 start*/
    /**
     * 实测面积
     */
    @ApiModelProperty(value = "实测面积亩")
    private Double cbzdcbfscmjm;

    /**
     * 实测面积
     */
    @ApiModelProperty(value = "合同面积亩")
    private Double cbzdcbfhtmjm;

    /** 承包宗地合同属性 end*/

    /** 林权 属性 start*/


    @ApiModelProperty(value = "林权调查表主键")
    private String lqDcbIndex;
    /**
     * 宗地调查表主键
     */
    @ApiModelProperty(value = "宗地调查表主键")
    private String lqzddcbIndex;
    /**
     * 地籍号
     */
    @ApiModelProperty(value = "地籍号")
    private String lqdjh;
    /**
     * 不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    private String lqbdcdyh;
    /**
     * 小地名
     */
    @ApiModelProperty(value = "小地名")
    private String lqxdm;
    /**
     * 坐落
     */
    @ApiModelProperty(value = "坐落")
    private String lqzl;
    /**
     * 林班
     */
    @ApiModelProperty(value = "林班")
    private String lqlb;
    /**
     * 小班
     */
    @ApiModelProperty(value = "小班")
    private String lqxb;
    /**
     * 面积单位
     */
    @ApiModelProperty(value = "面积单位")
    private String lqmjdw;
    /**
     * 面积
     */
    @ApiModelProperty(value = "面积")
    private Double lqmj;
    /**
     * 株树
     */
    @ApiModelProperty(value = "株树")
    private Long lqzs;
    /**
     * 林种
     */
    @ApiModelProperty(value = "林种")
    private String lqlz;
    /**
     * 主要树种
     */
    @ApiModelProperty(value = "主要树种")
    private String lqzysz;
    /**
     * 造林年份
     */
    @ApiModelProperty(value = "造林年份")
    private Integer lqzlnd;
    /**
     * 共有情况
     */
    @ApiModelProperty(value = "共有情况")
    private String lqgyqk;
    /**
     * 起源
     */
    @ApiModelProperty(value = "起源")
    private String lqqy;
    /**
     * 附记
     */
    @ApiModelProperty(value = "附记")
    private String lqfj;
    /**
     * 调查人员
     */
    @ApiModelProperty(value = "调查人员")
    private String lqdcry;
    /**
     * 调查日期
     */
    @ApiModelProperty(value = "调查日期",example = "2018-10-01 12:18:21")
    private Date lqdcrq;
    /**
     * 建立日期
     */
    @ApiModelProperty(value = "建立日期",example = "2018-10-01 12:18:21")
    private Date lqjlrq;
    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期",example = "2018-10-01 12:18:21")
    private Date lqgxrq;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String lqbz;
    /**
     * 森林类别
     */
    @ApiModelProperty(value = "森林类别")
    private String lqsllb;


    /**
     * 承包宗地发包方集合
     */
    @ApiModelProperty(value = "承包宗地发包方集合")
    private List<CbzdFbfDO> cbzdFbfDOList;

    /**
     * 承包宗地承包方集合
     */
    @ApiModelProperty(value = "承包宗地承包方集合")
    private List<CbzdCbfDO> cbzdCbfDOList;
    /**
     * 承包宗地承包方家庭成员集合
     */
    @ApiModelProperty(value = "承包宗地承包方家庭成员集合")
    private List<NydJtcyDO> cbfJtcyDOList;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "其他说明")
    private String qtsm;


    public List<CbzdFbfDO> getCbzdFbfDOList() {
        return cbzdFbfDOList;
    }

    public void setCbzdFbfDOList(List<CbzdFbfDO> cbzdFbfDOList) {
        this.cbzdFbfDOList = cbzdFbfDOList;
    }

    public List<CbzdCbfDO> getCbzdCbfDOList() {
        return cbzdCbfDOList;
    }

    public void setCbzdCbfDOList(List<CbzdCbfDO> cbzdCbfDOList) {
        this.cbzdCbfDOList = cbzdCbfDOList;
    }

    public String getNydDjdcbIndex() {
        return nydDjdcbIndex;
    }

    public void setNydDjdcbIndex(String nydDjdcbIndex) {
        this.nydDjdcbIndex = nydDjdcbIndex;
    }

    public String getDjh() {
        return djh;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getTdsyzmc() {
        return tdsyzmc;
    }

    public void setTdsyzmc(String tdsyzmc) {
        this.tdsyzmc = tdsyzmc;
    }

    public String getSztfh() {
        return sztfh;
    }

    public void setSztfh(String sztfh) {
        this.sztfh = sztfh;
    }

    public String getQsxz() {
        return qsxz;
    }

    public void setQsxz(String qsxz) {
        this.qsxz = qsxz;
    }

    public String getQslx() {
        return qslx;
    }

    public void setQslx(String qslx) {
        this.qslx = qslx;
    }

    public String getTdzl() {
        return tdzl;
    }

    public void setTdzl(String tdzl) {
        this.tdzl = tdzl;
    }

    public String getSyqlx() {
        return syqlx;
    }

    public void setSyqlx(String syqlx) {
        this.syqlx = syqlx;
    }

    public String getBlc() {
        return blc;
    }

    public void setBlc(String blc) {
        this.blc = blc;
    }

    public String getTdyt() {
        return tdyt;
    }

    public void setTdyt(String tdyt) {
        this.tdyt = tdyt;
    }

    public Date getQsrq() {
        return qsrq;
    }

    public void setQsrq(Date qsrq) {
        this.qsrq = qsrq;
    }

    public Date getZzrq() {
        return zzrq;
    }

    public void setZzrq(Date zzrq) {
        this.zzrq = zzrq;
    }

    public String getTdyt2() {
        return tdyt2;
    }

    public void setTdyt2(String tdyt2) {
        this.tdyt2 = tdyt2;
    }

    public Date getQsrq2() {
        return qsrq2;
    }

    public void setQsrq2(Date qsrq2) {
        this.qsrq2 = qsrq2;
    }

    public Date getZzrq2() {
        return zzrq2;
    }

    public void setZzrq2(Date zzrq2) {
        this.zzrq2 = zzrq2;
    }

    public String getTdyt3() {
        return tdyt3;
    }

    public void setTdyt3(String tdyt3) {
        this.tdyt3 = tdyt3;
    }

    public Date getQsrq3() {
        return qsrq3;
    }

    public void setQsrq3(Date qsrq3) {
        this.qsrq3 = qsrq3;
    }

    public Date getZzrq3() {
        return zzrq3;
    }

    public void setZzrq3(Date zzrq3) {
        this.zzrq3 = zzrq3;
    }

    public String getMjdw() {
        return mjdw;
    }

    public void setMjdw(String mjdw) {
        this.mjdw = mjdw;
    }

    public Double getScmj() {
        return scmj;
    }

    public void setScmj(Double scmj) {
        this.scmj = scmj;
    }

    public Double getFzmj() {
        return fzmj;
    }

    public void setFzmj(Double fzmj) {
        this.fzmj = fzmj;
    }

    public Double getJzmd() {
        return jzmd;
    }

    public void setJzmd(Double jzmd) {
        this.jzmd = jzmd;
    }

    public Double getPzmj() {
        return pzmj;
    }

    public void setPzmj(Double pzmj) {
        this.pzmj = pzmj;
    }

    public Double getJzrjl() {
        return jzrjl;
    }

    public void setJzrjl(Double jzrjl) {
        this.jzrjl = jzrjl;
    }

    public String getPzyt() {
        return pzyt;
    }

    public void setPzyt(String pzyt) {
        this.pzyt = pzyt;
    }

    public String getTdjb() {
        return tdjb;
    }

    public void setTdjb(String tdjb) {
        this.tdjb = tdjb;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public Double getSbdj() {
        return sbdj;
    }

    public void setSbdj(Double sbdj) {
        this.sbdj = sbdj;
    }

    public Double getQdjg() {
        return qdjg;
    }

    public void setQdjg(Double qdjg) {
        this.qdjg = qdjg;
    }

    public Double getZdzmj() {
        return zdzmj;
    }

    public void setZdzmj(Double zdzmj) {
        this.zdzmj = zdzmj;
    }

    public Double getJzzmj() {
        return jzzmj;
    }

    public void setJzzmj(Double jzzmj) {
        this.jzzmj = jzzmj;
    }

    public Double getJzxg() {
        return jzxg;
    }

    public void setJzxg(Double jzxg) {
        this.jzxg = jzxg;
    }

    public String getDclx() {
        return dclx;
    }

    public void setDclx(String dclx) {
        this.dclx = dclx;
    }

    public String getQlsdfs() {
        return qlsdfs;
    }

    public void setQlsdfs(String qlsdfs) {
        this.qlsdfs = qlsdfs;
    }

    public String getLyqlbh() {
        return lyqlbh;
    }

    public void setLyqlbh(String lyqlbh) {
        this.lyqlbh = lyqlbh;
    }

    public String getZdszb() {
        return zdszb;
    }

    public void setZdszb(String zdszb) {
        this.zdszb = zdszb;
    }

    public String getZdszd() {
        return zdszd;
    }

    public void setZdszd(String zdszd) {
        this.zdszd = zdszd;
    }

    public String getZdszn() {
        return zdszn;
    }

    public void setZdszn(String zdszn) {
        this.zdszn = zdszn;
    }

    public String getZdszx() {
        return zdszx;
    }

    public void setZdszx(String zdszx) {
        this.zdszx = zdszx;
    }

    public String getTdqslyzmcl() {
        return tdqslyzmcl;
    }

    public void setTdqslyzmcl(String tdqslyzmcl) {
        this.tdqslyzmcl = tdqslyzmcl;
    }

    public String getJzwsuqgyqlr() {
        return jzwsuqgyqlr;
    }

    public void setJzwsuqgyqlr(String jzwsuqgyqlr) {
        this.jzwsuqgyqlr = jzwsuqgyqlr;
    }

    public String getGysyqqk() {
        return gysyqqk;
    }

    public void setGysyqqk(String gysyqqk) {
        this.gysyqqk = gysyqqk;
    }

    public String getGmjjhyfldm() {
        return gmjjhyfldm;
    }

    public void setGmjjhyfldm(String gmjjhyfldm) {
        this.gmjjhyfldm = gmjjhyfldm;
    }

    public String getYbdjh() {
        return ybdjh;
    }

    public void setYbdjh(String ybdjh) {
        this.ybdjh = ybdjh;
    }

    public String getYdjh() {
        return ydjh;
    }

    public void setYdjh(String ydjh) {
        this.ydjh = ydjh;
    }

    public String getZdsmj() {
        return zdsmj;
    }

    public void setZdsmj(String zdsmj) {
        this.zdsmj = zdsmj;
    }

    public String getBdcdyhzt() {
        return bdcdyhzt;
    }

    public void setBdcdyhzt(String bdcdyhzt) {
        this.bdcdyhzt = bdcdyhzt;
    }

    public String getSfgyz() {
        return sfgyz;
    }

    public void setSfgyz(String sfgyz) {
        this.sfgyz = sfgyz;
    }

    public String getSfxz() {
        return sfxz;
    }

    public void setSfxz(String sfxz) {
        this.sfxz = sfxz;
    }

    public String getYzbm() {
        return yzbm;
    }

    public void setYzbm(String yzbm) {
        this.yzbm = yzbm;
    }

    public Date getJlrq() {
        return jlrq;
    }

    public void setJlrq(Date jlrq) {
        this.jlrq = jlrq;
    }

    public Date getGxrq() {
        return gxrq;
    }

    public void setGxrq(Date gxrq) {
        this.gxrq = gxrq;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getCbzdDcbIndex() {
        return cbzdDcbIndex;
    }

    public void setCbzdDcbIndex(String cbzdDcbIndex) {
        this.cbzdDcbIndex = cbzdDcbIndex;
    }

    public String getCbzdzddcbIndex() {
        return cbzdzddcbIndex;
    }

    public void setCbzdzddcbIndex(String cbzdzddcbIndex) {
        this.cbzdzddcbIndex = cbzdzddcbIndex;
    }

    public String getCbzddjh() {
        return cbzddjh;
    }

    public void setCbzddjh(String cbzddjh) {
        this.cbzddjh = cbzddjh;
    }

    public String getCbzdbdcdyh() {
        return cbzdbdcdyh;
    }

    public void setCbzdbdcdyh(String cbzdbdcdyh) {
        this.cbzdbdcdyh = cbzdbdcdyh;
    }

    public String getCbzdtdyt() {
        return cbzdtdyt;
    }

    public void setCbzdtdyt(String cbzdtdyt) {
        this.cbzdtdyt = cbzdtdyt;
    }

    public String getCbzddldj() {
        return cbzddldj;
    }

    public void setCbzddldj(String cbzddldj) {
        this.cbzddldj = cbzddldj;
    }

    public String getCbzdsfjbnt() {
        return cbzdsfjbnt;
    }

    public void setCbzdsfjbnt(String cbzdsfjbnt) {
        this.cbzdsfjbnt = cbzdsfjbnt;
    }

    public String getCbzdsyttlx() {
        return cbzdsyttlx;
    }

    public void setCbzdsyttlx(String cbzdsyttlx) {
        this.cbzdsyttlx = cbzdsyttlx;
    }

    public String getCbzdyzyfs() {
        return cbzdyzyfs;
    }

    public void setCbzdyzyfs(String cbzdyzyfs) {
        this.cbzdyzyfs = cbzdyzyfs;
    }

    public Long getCbzdsyzxl() {
        return cbzdsyzxl;
    }

    public void setCbzdsyzxl(Long cbzdsyzxl) {
        this.cbzdsyzxl = cbzdsyzxl;
    }

    public Double getCbzdccgd() {
        return cbzdccgd;
    }

    public void setCbzdccgd(Double cbzdccgd) {
        this.cbzdccgd = cbzdccgd;
    }

    public Double getCbzdcdfgd() {
        return cbzdcdfgd;
    }

    public void setCbzdcdfgd(Double cbzdcdfgd) {
        this.cbzdcdfgd = cbzdcdfgd;
    }

    public String getCbzdcdjq() {
        return cbzdcdjq;
    }

    public void setCbzdcdjq(String cbzdcdjq) {
        this.cbzdcdjq = cbzdcdjq;
    }

    public String getCbzdcdysz() {
        return cbzdcdysz;
    }

    public void setCbzdcdysz(String cbzdcdysz) {
        this.cbzdcdysz = cbzdcdysz;
    }

    public String getCbzddcjs() {
        return cbzddcjs;
    }

    public void setCbzddcjs(String cbzddcjs) {
        this.cbzddcjs = cbzddcjs;
    }

    public String getCbzddcy() {
        return cbzddcy;
    }

    public void setCbzddcy(String cbzddcy) {
        this.cbzddcy = cbzddcy;
    }

    public Date getCbzddcrq() {
        return cbzddcrq;
    }

    public void setCbzddcrq(Date cbzddcrq) {
        this.cbzddcrq = cbzddcrq;
    }

    public String getCbzdshyj() {
        return cbzdshyj;
    }

    public void setCbzdshyj(String cbzdshyj) {
        this.cbzdshyj = cbzdshyj;
    }

    public String getCbzdshr() {
        return cbzdshr;
    }

    public void setCbzdshr(String cbzdshr) {
        this.cbzdshr = cbzdshr;
    }

    public Date getCbzdshrq() {
        return cbzdshrq;
    }

    public void setCbzdshrq(Date cbzdshrq) {
        this.cbzdshrq = cbzdshrq;
    }

    public Date getCbzdgxrq() {
        return cbzdgxrq;
    }

    public void setCbzdgxrq(Date cbzdgxrq) {
        this.cbzdgxrq = cbzdgxrq;
    }

    public String getCbzdjtIndex() {
        return cbzdjtIndex;
    }

    public void setCbzdjtIndex(String cbzdjtIndex) {
        this.cbzdjtIndex = cbzdjtIndex;
    }

    public String getCbzdbz() {
        return cbzdbz;
    }

    public void setCbzdbz(String cbzdbz) {
        this.cbzdbz = cbzdbz;
    }

    public String getLqDcbIndex() {
        return lqDcbIndex;
    }

    public void setLqDcbIndex(String lqDcbIndex) {
        this.lqDcbIndex = lqDcbIndex;
    }

    public String getLqzddcbIndex() {
        return lqzddcbIndex;
    }

    public void setLqzddcbIndex(String lqzddcbIndex) {
        this.lqzddcbIndex = lqzddcbIndex;
    }

    public String getLqdjh() {
        return lqdjh;
    }

    public void setLqdjh(String lqdjh) {
        this.lqdjh = lqdjh;
    }

    public String getLqbdcdyh() {
        return lqbdcdyh;
    }

    public void setLqbdcdyh(String lqbdcdyh) {
        this.lqbdcdyh = lqbdcdyh;
    }

    public String getLqxdm() {
        return lqxdm;
    }

    public void setLqxdm(String lqxdm) {
        this.lqxdm = lqxdm;
    }

    public String getLqzl() {
        return lqzl;
    }

    public void setLqzl(String lqzl) {
        this.lqzl = lqzl;
    }

    public String getLqlb() {
        return lqlb;
    }

    public void setLqlb(String lqlb) {
        this.lqlb = lqlb;
    }

    public String getLqxb() {
        return lqxb;
    }

    public void setLqxb(String lqxb) {
        this.lqxb = lqxb;
    }

    public String getLqmjdw() {
        return lqmjdw;
    }

    public void setLqmjdw(String lqmjdw) {
        this.lqmjdw = lqmjdw;
    }

    public Double getLqmj() {
        return lqmj;
    }

    public void setLqmj(Double lqmj) {
        this.lqmj = lqmj;
    }

    public Long getLqzs() {
        return lqzs;
    }

    public void setLqzs(Long lqzs) {
        this.lqzs = lqzs;
    }

    public String getLqlz() {
        return lqlz;
    }

    public void setLqlz(String lqlz) {
        this.lqlz = lqlz;
    }

    public String getLqzysz() {
        return lqzysz;
    }

    public void setLqzysz(String lqzysz) {
        this.lqzysz = lqzysz;
    }

    public Integer getLqzlnd() {
        return lqzlnd;
    }

    public void setLqzlnd(Integer lqzlnd) {
        this.lqzlnd = lqzlnd;
    }

    public String getLqgyqk() {
        return lqgyqk;
    }

    public void setLqgyqk(String lqgyqk) {
        this.lqgyqk = lqgyqk;
    }

    public String getLqqy() {
        return lqqy;
    }

    public void setLqqy(String lqqy) {
        this.lqqy = lqqy;
    }

    public String getLqfj() {
        return lqfj;
    }

    public void setLqfj(String lqfj) {
        this.lqfj = lqfj;
    }

    public String getLqdcry() {
        return lqdcry;
    }

    public void setLqdcry(String lqdcry) {
        this.lqdcry = lqdcry;
    }

    public Date getLqdcrq() {
        return lqdcrq;
    }

    public void setLqdcrq(Date lqdcrq) {
        this.lqdcrq = lqdcrq;
    }

    public Date getLqjlrq() {
        return lqjlrq;
    }

    public void setLqjlrq(Date lqjlrq) {
        this.lqjlrq = lqjlrq;
    }

    public Date getLqgxrq() {
        return lqgxrq;
    }

    public void setLqgxrq(Date lqgxrq) {
        this.lqgxrq = lqgxrq;
    }

    public String getLqbz() {
        return lqbz;
    }

    public void setLqbz(String lqbz) {
        this.lqbz = lqbz;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public List<NydJtcyDO> getCbfJtcyDOList() {
        return cbfJtcyDOList;
    }

    public void setCbfJtcyDOList(List<NydJtcyDO> cbfJtcyDOList) {
        this.cbfJtcyDOList = cbfJtcyDOList;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getCbzddkbm() {
        return cbzddkbm;
    }

    public void setCbzddkbm(String cbzddkbm) {
        this.cbzddkbm = cbzddkbm;
    }

    public String getCbzdqqqk() {
        return cbzdqqqk;
    }

    public void setCbzdqqqk(String cbzdqqqk) {
        this.cbzdqqqk = cbzdqqqk;
    }

    public String getCbzdfbfbm() {
        return cbzdfbfbm;
    }

    public void setCbzdfbfbm(String cbzdfbfbm) {
        this.cbzdfbfbm = cbzdfbfbm;
    }

    public Date getCbzdgxsj() {
        return cbzdgxsj;
    }

    public void setCbzdgxsj(Date cbzdgxsj) {
        this.cbzdgxsj = cbzdgxsj;
    }

    public Double getCbzdcbfscmjm() {
        return cbzdcbfscmjm;
    }

    public void setCbzdcbfscmjm(Double cbzdcbfscmjm) {
        this.cbzdcbfscmjm = cbzdcbfscmjm;
    }

    public Double getCbzdcbfhtmjm() {
        return cbzdcbfhtmjm;
    }

    public void setCbzdcbfhtmjm(Double cbzdcbfhtmjm) {
        this.cbzdcbfhtmjm = cbzdcbfhtmjm;
    }

    public Double getScmjm() {
        return scmjm;
    }

    public void setScmjm(Double scmjm) {
        this.scmjm = scmjm;
    }

    public Double getFzmjm() {
        return fzmjm;
    }

    public void setFzmjm(Double fzmjm) {
        this.fzmjm = fzmjm;
    }

    public Double getPzmjm() {
        return pzmjm;
    }

    public void setPzmjm(Double pzmjm) {
        this.pzmjm = pzmjm;
    }

    public String getYdyhfl() {
        return ydyhfl;
    }

    public void setYdyhfl(String ydyhfl) {
        this.ydyhfl = ydyhfl;
    }

    public String getLqsllb() {
        return lqsllb;
    }

    public void setLqsllb(String lqsllb) {
        this.lqsllb = lqsllb;
    }

    public String getQtsm() {
        return qtsm;
    }

    public void setQtsm(String qtsm) {
        this.qtsm = qtsm;
    }
}