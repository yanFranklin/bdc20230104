package cn.gtmap.realestate.common.core.dto.building;

import cn.gtmap.realestate.common.core.domain.building.ZhJzbsb;
import cn.gtmap.realestate.common.core.domain.building.ZhQsdcDO;
import cn.gtmap.realestate.common.core.domain.building.ZhZhjnbdyjlb;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/16
 * @description 宗海调查簿DTO
 */
@ApiModel(value = "ZhDjdcbResponseDTO", description = "宗海调查簿DTO")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz",defaultImpl = ZhDjdcbResponseDTO.class)
public class ZhDjdcbResponseDTO extends DjDcbResponseDTO {

    @ApiModelProperty(value = "主键")
    private String zhDjdcbIndex;

    /**
     * 宗海代码
     */
    @ApiModelProperty(value = "宗海代码")
    private String zhdm;

    /**
     * 不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    /**
     * 权属性质
     */
    @ApiModelProperty(value = "权属性质")
    private String qsxz;

    /**
     * 宗海特征码
     */
    @ApiModelProperty(value = "宗海特征码")
    private String zhtzm;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称")
    private String xmmc;

    /**
     * 项目性质
     */
    @ApiModelProperty(value = "项目性质")
    private String xmxz;

    /**
     * 构筑物类型
     */
    @ApiModelProperty(value = "构筑物类型")
    private String gzwlx;

    /**
     * 用海面积
     */
    @ApiModelProperty(value = "用海面积")
    private Double yhzmj;

    /**
     * 宗海面积
     */
    @ApiModelProperty(value = "宗海面积")
    private Double zhmj;

    /**
     * 海域等别
     */
    @ApiModelProperty(value = "海域等别")
    private String db;

    /**
     * 占海案线
     */
    @ApiModelProperty(value = "占海案线")
    private Double zhax;

    /**
     * 用海类型A
     */
    @ApiModelProperty(value = "用海类型A")
    private String yhlxa;

    /**
     * 用海类型B
     */
    @ApiModelProperty(value = "用海类型B")
    private String yhlxb;

    /**
     * 用海位置说明
     */
    @ApiModelProperty(value = "用海位置说明")
    private String yhwzsm;

    /**
     * 海岛名称
     */
    @ApiModelProperty(value = "海岛名称")
    private String hdmc;

    /**
     * 海岛代码
     */
    @ApiModelProperty(value = "海岛代码")
    private String hddm;

    /**
     * 用海范围
     */
    @ApiModelProperty(value = "用海范围")
    private String ydfw;

    /**
     * 用岛面积
     */
    @ApiModelProperty(value = "用岛面积")
    private Double ydmj;

    /**
     * 海岛位置
     */
    @ApiModelProperty(value = "海岛位置")
    private String hdwz;

    /**
     * 海岛用途
     */
    @ApiModelProperty(value = "海岛用途")
    private String hdyt;

    /**
     * 不动产单元状态
     */
    @ApiModelProperty(value = "不动产单元状态")
    private String bdcdyzt;

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
     * 使用金标准依据
     */
    @ApiModelProperty(value = "使用金标准依据")
    private String syjbzyj;

    /**
     * 使用金缴纳情况
     */
    @ApiModelProperty(value = "使用金缴纳情况")
    private String syjjnqk;

    /**
     * 记录表测量人
     */
    @ApiModelProperty(value = "记录表测量人")
    private String jlbclr;

    /**
     * 记录表审核人
     */
    @ApiModelProperty(value = "记录表审核人")
    private String jlbshr;

    /**
     * 记录表测量单位
     */
    @ApiModelProperty(value = "记录表测量单位")
    private String jlbcldw;

    /**
     * 记录表深度记录
     */
    @ApiModelProperty(value = "记录表深度记录")
    private String jlbsdjz;

    /**
     * 坐落
     */
    @ApiModelProperty(value = "坐落")
    private String zl;

    /**
     * 记录表测量日期
     */
    @ApiModelProperty(value = "记录表测量日期",example = "2018-10-01 12:18:21")
    private Date jlbclrq;


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
     * 海域管理编号
     */
    @ApiModelProperty(value = "海域管理编号")
    private String hyglbh;

    /**
     * 宗海及内部单元记录表属性结构描述
     */
    @ApiModelProperty(value = "宗海及内部单元记录表属性结构描述")
    private List<ZhZhjnbdyjlb> zhZhjnbdyjlbList;

    /**
     * 宗海界址标示表
     */
    @ApiModelProperty(value = "宗海界址标示表")
    private List<ZhJzbsb> zhJzbsbList;
    
    /**
     * 宗海权属调查表
     */
    private ZhQsdcDO zhQsdcDO;

    public String getZhDjdcbIndex() {
        return zhDjdcbIndex;
    }

    public void setZhDjdcbIndex(String zhDjdcbIndex) {
        this.zhDjdcbIndex = zhDjdcbIndex;
    }

    public String getZhdm() {
        return zhdm;
    }

    public void setZhdm(String zhdm) {
        this.zhdm = zhdm;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getQsxz() {
        return qsxz;
    }

    public void setQsxz(String qsxz) {
        this.qsxz = qsxz;
    }

    public String getZhtzm() {
        return zhtzm;
    }

    public void setZhtzm(String zhtzm) {
        this.zhtzm = zhtzm;
    }

    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    public String getXmxz() {
        return xmxz;
    }

    public void setXmxz(String xmxz) {
        this.xmxz = xmxz;
    }

    public String getGzwlx() {
        return gzwlx;
    }

    public void setGzwlx(String gzwlx) {
        this.gzwlx = gzwlx;
    }

    public Double getYhzmj() {
        return yhzmj;
    }

    public void setYhzmj(Double yhzmj) {
        this.yhzmj = yhzmj;
    }

    public Double getZhmj() {
        return zhmj;
    }

    public void setZhmj(Double zhmj) {
        this.zhmj = zhmj;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public Double getZhax() {
        return zhax;
    }

    public void setZhax(Double zhax) {
        this.zhax = zhax;
    }

    public String getYhlxa() {
        return yhlxa;
    }

    public void setYhlxa(String yhlxa) {
        this.yhlxa = yhlxa;
    }

    public String getYhlxb() {
        return yhlxb;
    }

    public void setYhlxb(String yhlxb) {
        this.yhlxb = yhlxb;
    }

    public String getYhwzsm() {
        return yhwzsm;
    }

    public void setYhwzsm(String yhwzsm) {
        this.yhwzsm = yhwzsm;
    }

    public String getHdmc() {
        return hdmc;
    }

    public void setHdmc(String hdmc) {
        this.hdmc = hdmc;
    }

    public String getHddm() {
        return hddm;
    }

    public void setHddm(String hddm) {
        this.hddm = hddm;
    }

    public String getYdfw() {
        return ydfw;
    }

    public void setYdfw(String ydfw) {
        this.ydfw = ydfw;
    }

    public Double getYdmj() {
        return ydmj;
    }

    public void setYdmj(Double ydmj) {
        this.ydmj = ydmj;
    }

    public String getHdwz() {
        return hdwz;
    }

    public void setHdwz(String hdwz) {
        this.hdwz = hdwz;
    }

    public String getHdyt() {
        return hdyt;
    }

    public void setHdyt(String hdyt) {
        this.hdyt = hdyt;
    }

    public String getBdcdyzt() {
        return bdcdyzt;
    }

    public void setBdcdyzt(String bdcdyzt) {
        this.bdcdyzt = bdcdyzt;
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

    public String getSyjbzyj() {
        return syjbzyj;
    }

    public void setSyjbzyj(String syjbzyj) {
        this.syjbzyj = syjbzyj;
    }

    public String getSyjjnqk() {
        return syjjnqk;
    }

    public void setSyjjnqk(String syjjnqk) {
        this.syjjnqk = syjjnqk;
    }

    public String getJlbclr() {
        return jlbclr;
    }

    public void setJlbclr(String jlbclr) {
        this.jlbclr = jlbclr;
    }

    public String getJlbshr() {
        return jlbshr;
    }

    public void setJlbshr(String jlbshr) {
        this.jlbshr = jlbshr;
    }

    public String getJlbcldw() {
        return jlbcldw;
    }

    public void setJlbcldw(String jlbcldw) {
        this.jlbcldw = jlbcldw;
    }

    public String getJlbsdjz() {
        return jlbsdjz;
    }

    public void setJlbsdjz(String jlbsdjz) {
        this.jlbsdjz = jlbsdjz;
    }

    public Date getJlbclrq() {
        return jlbclrq;
    }

    public void setJlbclrq(Date jlbclrq) {
        this.jlbclrq = jlbclrq;
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

    public String getHyglbh() {
        return hyglbh;
    }

    public void setHyglbh(String hyglbh) {
        this.hyglbh = hyglbh;
    }

    public List<ZhZhjnbdyjlb> getZhZhjnbdyjlbList() {
        return zhZhjnbdyjlbList;
    }

    public void setZhZhjnbdyjlbList(List<ZhZhjnbdyjlb> zhZhjnbdyjlbList) {
        this.zhZhjnbdyjlbList = zhZhjnbdyjlbList;
    }

    public List<ZhJzbsb> getZhJzbsbList() {
        return zhJzbsbList;
    }

    public void setZhJzbsbList(List<ZhJzbsb> zhJzbsbList) {
        this.zhJzbsbList = zhJzbsbList;
    }
    
    public ZhQsdcDO getZhQsdcDO() {
        return zhQsdcDO;
    }
    
    public void setZhQsdcDO(ZhQsdcDO zhQsdcDO) {
        this.zhQsdcDO = zhQsdcDO;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ZhDjdcbResponseDTO{");
        sb.append("zhDjdcbIndex='").append(zhDjdcbIndex).append('\'');
        sb.append(", zhdm='").append(zhdm).append('\'');
        sb.append(", bdcdyh='").append(bdcdyh).append('\'');
        sb.append(", qsxz='").append(qsxz).append('\'');
        sb.append(", zhtzm='").append(zhtzm).append('\'');
        sb.append(", xmmc='").append(xmmc).append('\'');
        sb.append(", xmxz='").append(xmxz).append('\'');
        sb.append(", gzwlx='").append(gzwlx).append('\'');
        sb.append(", yhzmj=").append(yhzmj);
        sb.append(", zhmj=").append(zhmj);
        sb.append(", db='").append(db).append('\'');
        sb.append(", zhax=").append(zhax);
        sb.append(", yhlxa='").append(yhlxa).append('\'');
        sb.append(", yhlxb='").append(yhlxb).append('\'');
        sb.append(", yhwzsm='").append(yhwzsm).append('\'');
        sb.append(", hdmc='").append(hdmc).append('\'');
        sb.append(", hddm='").append(hddm).append('\'');
        sb.append(", ydfw='").append(ydfw).append('\'');
        sb.append(", ydmj=").append(ydmj);
        sb.append(", hdwz='").append(hdwz).append('\'');
        sb.append(", hdyt='").append(hdyt).append('\'');
        sb.append(", bdcdyzt='").append(bdcdyzt).append('\'');
        sb.append(", jlrq=").append(jlrq);
        sb.append(", gxrq=").append(gxrq);
        sb.append(", syjbzyj='").append(syjbzyj).append('\'');
        sb.append(", syjjnqk='").append(syjjnqk).append('\'');
        sb.append(", jlbclr='").append(jlbclr).append('\'');
        sb.append(", jlbshr='").append(jlbshr).append('\'');
        sb.append(", jlbcldw='").append(jlbcldw).append('\'');
        sb.append(", jlbsdjz='").append(jlbsdjz).append('\'');
        sb.append(", zl='").append(zl).append('\'');
        sb.append(", jlbclrq=").append(jlbclrq);
        sb.append(", qsrq=").append(qsrq);
        sb.append(", zzrq=").append(zzrq);
        sb.append(", hyglbh='").append(hyglbh).append('\'');
        sb.append(", zhZhjnbdyjlbList=").append(zhZhjnbdyjlbList);
        sb.append(", zhJzbsbList=").append(zhJzbsbList);
        sb.append(", zhQsdcDO=").append(zhQsdcDO);
        sb.append('}');
        return sb.toString();
    }
}