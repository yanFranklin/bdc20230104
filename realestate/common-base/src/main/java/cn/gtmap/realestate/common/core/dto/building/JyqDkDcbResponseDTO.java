package cn.gtmap.realestate.common.core.dto.building;

import cn.gtmap.realestate.common.core.domain.building.JyqDkLcfDO;
import cn.gtmap.realestate.common.core.domain.building.JyqDkQlrDO;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/6
 * @description 经营权地块调查簿DTO
 */
@ApiModel(value = "JyqDkDcbResponseDTO", description = "经营权地块调查簿DTO")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz",defaultImpl = JyqDkDcbResponseDTO.class)
public class JyqDkDcbResponseDTO extends DjDcbResponseDTO {

    @Id
    @ApiModelProperty(value = "主键")
    private String jyqdkdcbIndex;

    @ApiModelProperty(value = "地块编号")
    private String dkbm;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "经营权起始日期")
    private Date jyqqsrq;

    @ApiModelProperty(value = "经营权终止日期")
    private Date jyqzzrq;

    @ApiModelProperty(value = "地块实测面积")
    private Double dkscmj;

    @ApiModelProperty(value = "地块实测面积亩")
    private Double dkscmjm;

    @ApiModelProperty(value = "地块合同面积")
    private Double dkhtmj;

    @ApiModelProperty(value = "地块合同面积亩")
    private Double dkhtmjm;

    @ApiModelProperty(value = "地块四至东")
    private String dkszd;

    @ApiModelProperty(value = "地块四至南")
    private String dkszn;

    @ApiModelProperty(value = "地块四至西")
    private String dkszx;

    @ApiModelProperty(value = "地块四至北")
    private String dkszb;

    @ApiModelProperty(value = "土地用途")
    private String tdyt;

    @ApiModelProperty(value = "权利性质")
    private String qlxz;

    @ApiModelProperty(value = "经营用途")
    private String jyyt;

    @ApiModelProperty(value = "地籍号")
    private String djh;

    @ApiModelProperty(value = "权属性质")
    private String qsxz;

    @ApiModelProperty(value = "备注")
    private String bz;


    /**
     * 林权字段
     */

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
     * 经营权流出方
     */
    @ApiModelProperty(value = "经营权地块流出方")
    private JyqDkLcfDO jyqDkLcfDO;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 经营权地块权利人
     */
    @ApiModelProperty(value = "经营权地块权利人")
    private List<JyqDkQlrDO> jyqDkQlrDOList;

    public String getJyqdkdcbIndex() {
        return jyqdkdcbIndex;
    }

    public void setJyqdkdcbIndex(String jyqdkdcbIndex) {
        this.jyqdkdcbIndex = jyqdkdcbIndex;
    }

    public String getDkbm() {
        return dkbm;
    }

    public void setDkbm(String dkbm) {
        this.dkbm = dkbm;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Date getJyqqsrq() {
        return jyqqsrq;
    }

    public void setJyqqsrq(Date jyqqsrq) {
        this.jyqqsrq = jyqqsrq;
    }

    public Date getJyqzzrq() {
        return jyqzzrq;
    }

    public void setJyqzzrq(Date jyqzzrq) {
        this.jyqzzrq = jyqzzrq;
    }

    public Double getDkscmj() {
        return dkscmj;
    }

    public void setDkscmj(Double dkscmj) {
        this.dkscmj = dkscmj;
    }

    public Double getDkscmjm() {
        return dkscmjm;
    }

    public void setDkscmjm(Double dkscmjm) {
        this.dkscmjm = dkscmjm;
    }

    public Double getDkhtmj() {
        return dkhtmj;
    }

    public void setDkhtmj(Double dkhtmj) {
        this.dkhtmj = dkhtmj;
    }

    public Double getDkhtmjm() {
        return dkhtmjm;
    }

    public void setDkhtmjm(Double dkhtmjm) {
        this.dkhtmjm = dkhtmjm;
    }

    public String getDkszd() {
        return dkszd;
    }

    public void setDkszd(String dkszd) {
        this.dkszd = dkszd;
    }

    public String getDkszn() {
        return dkszn;
    }

    public void setDkszn(String dkszn) {
        this.dkszn = dkszn;
    }

    public String getDkszx() {
        return dkszx;
    }

    public void setDkszx(String dkszx) {
        this.dkszx = dkszx;
    }

    public String getDkszb() {
        return dkszb;
    }

    public void setDkszb(String dkszb) {
        this.dkszb = dkszb;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public JyqDkLcfDO getJyqDkLcfDO() {
        return jyqDkLcfDO;
    }

    public void setJyqDkLcfDO(JyqDkLcfDO jyqDkLcfDO) {
        this.jyqDkLcfDO = jyqDkLcfDO;
    }

    public List<JyqDkQlrDO> getJyqDkQlrDOList() {
        return jyqDkQlrDOList;
    }

    public void setJyqDkQlrDOList(List<JyqDkQlrDO> jyqDkQlrDOList) {
        this.jyqDkQlrDOList = jyqDkQlrDOList;
    }

    public String getTdyt() {
        return tdyt;
    }

    public void setTdyt(String tdyt) {
        this.tdyt = tdyt;
    }

    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    public String getJyyt() {
        return jyyt;
    }

    public void setJyyt(String jyyt) {
        this.jyyt = jyyt;
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

    public String getLqsllb() {
        return lqsllb;
    }

    public void setLqsllb(String lqsllb) {
        this.lqsllb = lqsllb;
    }

    public String getDjh() {
        return djh;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }

    public String getQsxz() {
        return qsxz;
    }

    public void setQsxz(String qsxz) {
        this.qsxz = qsxz;
    }
}
