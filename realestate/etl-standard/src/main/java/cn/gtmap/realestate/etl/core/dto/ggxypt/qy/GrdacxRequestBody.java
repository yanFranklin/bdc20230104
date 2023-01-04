package cn.gtmap.realestate.etl.core.dto.ggxypt.qy;

import cn.gtmap.realestate.common.util.CommonConstantUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-26
 * @description 个人档案查询请求实体
 */
public class GrdacxRequestBody {
    /**
     * 权利人名称 必填
     */
    private String qlrmc;
    /**
     * 权利人证件号 必填
     */
    private String qlrzjh;
    /**
     * 受理编号
     */
    private String slbh;
    /**
     * 证件号集合 用于 15位、18位证件号处理
     */
    private List<String> qlrzjhList;

    /**
     * 是否需要证件号 转换
     */
    private Boolean needZjhRevert;
    /**
     * 证号
     */
    private String bdcqzh;
    /**
     * 限制权力类型  目前包含 查封 抵押 异议
     */
    private Integer[] xzqllx;

    /**
     * 限制权力 是否查询项目数据 默认否
     */
    private Boolean cqWithXm = false;

    /**
     * 限制权力 是否查询项目数据 默认否
     */
    private Boolean withXm = false;
    /**
     * 限制权力 是否查询权利人数据 默认否
     */
    private Boolean withQlr;
    /**
     * 限制权力 查询权利人数据的类别 默认全查
     */
    private String qlrlb;

    // 产权权利人类别 默认查权利人
    private String cqQlrlb = CommonConstantUtils.QLRLB_QLR;
    /**
     * 是否查询证书锁定数据 默认否
     */
    private Boolean withZssd;
    /**
     * 是否查询不动产单元锁定数据 默认否
     */
    private Boolean withDysd;

    /**
     * 是否查询房地产权
     */
    private Boolean queryFdcq = true;

    private Boolean queryFdcqXm = true;

    private Boolean queryFdcqQlr = true;

    private Boolean queryFdcqZdjbxx = true;

    /**
     * 是否查询土地所有权和使用权
     */

    private Boolean queryTdsyqAndJsydsyq;

    /**
     * 是否查询预告
     */
    private Boolean queryYg;

    /**
     * 项目主键
     */
    private String xmid;

    private String likeBdcdyh;

    private String bdcdyh;

    private String likeZl;
    // 常州 精确查询坐落
    private String zl;
    // 常州 roomid
    private String roomid;
    // 常州 sgbh
    private String sgbh;
    // 常州 cxlx 1存量房买卖 0商品房证明
    private String cxlx;
    // 海门 模糊产权证号查询
    private String likebdcqzh;

    // 默认查询 现势状态 多个用逗号隔开
    private String xzqlQszt;
    // 证号流水号
    private String zhlsh;
    // bdcdybh
    private String bdcdybh;

    private String ntbdcqzh;

    //盐城需求_关系人名称模糊查询
    private String qlrmcmh;

    /**
     * 权利人证件号 必填
     */
    private String qlrzjhmh;

    //盐城需求_不动产权证号模糊查询
    private String bdcqzhmh;

    //档案查询标识
    private String dacxFlag;

    //45623 【合肥】不动产登记系统给互联网提供的个人档案查询接口增加入参判断的需求
    private String xzqdm;

    //58957 【蚌埠】产权证接口入参需求  houseid为蚌埠权籍特有字段，现要求用这个字段也能查
    private String houseid;

    public String getHouseid() {
        return houseid;
    }

    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getQlrzjhmh() {
        return qlrzjhmh;
    }

    public void setQlrzjhmh(String qlrzjhmh) {
        this.qlrzjhmh = qlrzjhmh;
    }

    public String getXzqdm() {
        return xzqdm;
    }

    public void setXzqdm(String xzqdm) {
        this.xzqdm = xzqdm;
    }

    public String getDacxFlag() {
        return dacxFlag;
    }

    public void setDacxFlag(String dacxFlag) {
        this.dacxFlag = dacxFlag;
    }

    public String getBdcqzhmh() {
        return bdcqzhmh;
    }

    public void setBdcqzhmh(String bdcqzhmh) {
        this.bdcqzhmh = bdcqzhmh;
    }

    public String getQlrmcmh() {
        return qlrmcmh;
    }

    public void setQlrmcmh(String qlrmcmh) {
        this.qlrmcmh = qlrmcmh;
    }

    //南通cqzh，用于查询产权
    public String getNtbdcqzh() {
        return ntbdcqzh;
    }

    public void setNtbdcqzh(String ntbdcqzh) {
        this.ntbdcqzh = ntbdcqzh;
    }

    public String getLikebdcqzh() {
        return likebdcqzh;
    }

    public void setLikebdcqzh(String likebdcqzh) {
        this.likebdcqzh = likebdcqzh;
    }

    public String getSgbh() {
        return sgbh;
    }

    public void setSgbh(String sgbh) {
        this.sgbh = sgbh;
    }

    public String getZhlsh() {
        return zhlsh;
    }

    public void setZhlsh(String zhlsh) {
        this.zhlsh = zhlsh;
    }

    public String getBdcdybh() {
        return bdcdybh;
    }

    public void setBdcdybh(String bdcdybh) {
        this.bdcdybh = bdcdybh;
    }

    public Boolean getQueryTdsyqAndJsydsyq() {
        return queryTdsyqAndJsydsyq;
    }

    public void setQueryTdsyqAndJsydsyq(Boolean queryTdsyqAndJsydsyq) {
        this.queryTdsyqAndJsydsyq = queryTdsyqAndJsydsyq;
    }

    public String getCxlx() {
        return cxlx;
    }

    public void setCxlx(String cxlx) {
        this.cxlx = cxlx;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public Boolean getWithZssd() {
        return withZssd;
    }

    public void setWithZssd(Boolean withZssd) {
        this.withZssd = withZssd;
    }

    public Boolean getWithDysd() {
        return withDysd;
    }

    public void setWithDysd(Boolean withDysd) {
        this.withDysd = withDysd;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public Integer[] getXzqllx() {
        return xzqllx;
    }

    public void setXzqllx(Integer[] xzqllx) {
        this.xzqllx = xzqllx;
    }

    public Boolean getWithXm() {
        return withXm;
    }

    public void setWithXm(Boolean withXm) {
        this.withXm = withXm;
    }

    public Boolean getWithQlr() {
        return withQlr;
    }

    public void setWithQlr(Boolean withQlr) {
        this.withQlr = withQlr;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getLikeBdcdyh() {
        return likeBdcdyh;
    }

    public void setLikeBdcdyh(String likeBdcdyh) {
        this.likeBdcdyh = likeBdcdyh;
    }

    public String getLikeZl() {
        return likeZl;
    }

    public void setLikeZl(String likeZl) {
        this.likeZl = likeZl;
    }

    public Boolean getQueryFdcq() {
        return queryFdcq;
    }

    public void setQueryFdcq(Boolean queryFdcq) {
        this.queryFdcq = queryFdcq;
    }

    public Boolean getQueryYg() {
        return queryYg;
    }

    public void setQueryYg(Boolean queryYg) {
        this.queryYg = queryYg;
    }

    public List<String> getQlrzjhList() {
        return qlrzjhList;
    }

    public void setQlrzjhList(List<String> qlrzjhList) {
        this.qlrzjhList = qlrzjhList;
    }

    public Boolean getNeedZjhRevert() {
        return needZjhRevert;
    }

    public void setNeedZjhRevert(Boolean needZjhRevert) {
        this.needZjhRevert = needZjhRevert;
    }

    public String getXzqlQszt() {
        return xzqlQszt;
    }

    public void setXzqlQszt(String xzqlQszt) {
        this.xzqlQszt = xzqlQszt;
    }

    public Boolean getQueryFdcqXm() {
        return queryFdcqXm;
    }

    public void setQueryFdcqXm(Boolean queryFdcqXm) {
        this.queryFdcqXm = queryFdcqXm;
    }

    public Boolean getQueryFdcqQlr() {
        return queryFdcqQlr;
    }

    public void setQueryFdcqQlr(Boolean queryFdcqQlr) {
        this.queryFdcqQlr = queryFdcqQlr;
    }

    public Boolean getQueryFdcqZdjbxx() {
        return queryFdcqZdjbxx;
    }

    public void setQueryFdcqZdjbxx(Boolean queryFdcqZdjbxx) {
        this.queryFdcqZdjbxx = queryFdcqZdjbxx;
    }

    public String getCqQlrlb() {
        return cqQlrlb;
    }

    public void setCqQlrlb(String cqQlrlb) {
        this.cqQlrlb = cqQlrlb;
    }

    public Boolean getCqWithXm() {
        return cqWithXm;
    }

    public void setCqWithXm(Boolean cqWithXm) {
        this.cqWithXm = cqWithXm;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    @Override
    public String toString() {
        return "GrdacxRequestBody{" +
                "qlrmc='" + qlrmc + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", slbh='" + slbh + '\'' +
                ", qlrzjhList=" + qlrzjhList +
                ", needZjhRevert=" + needZjhRevert +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", xzqllx=" + Arrays.toString(xzqllx) +
                ", cqWithXm=" + cqWithXm +
                ", withXm=" + withXm +
                ", withQlr=" + withQlr +
                ", qlrlb='" + qlrlb + '\'' +
                ", cqQlrlb='" + cqQlrlb + '\'' +
                ", withZssd=" + withZssd +
                ", withDysd=" + withDysd +
                ", queryFdcq=" + queryFdcq +
                ", queryFdcqXm=" + queryFdcqXm +
                ", queryFdcqQlr=" + queryFdcqQlr +
                ", queryFdcqZdjbxx=" + queryFdcqZdjbxx +
                ", queryTdsyqAndJsydsyq=" + queryTdsyqAndJsydsyq +
                ", queryYg=" + queryYg +
                ", xmid='" + xmid + '\'' +
                ", likeBdcdyh='" + likeBdcdyh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", likeZl='" + likeZl + '\'' +
                ", zl='" + zl + '\'' +
                ", roomid='" + roomid + '\'' +
                ", sgbh='" + sgbh + '\'' +
                ", cxlx='" + cxlx + '\'' +
                ", likebdcqzh='" + likebdcqzh + '\'' +
                ", xzqlQszt='" + xzqlQszt + '\'' +
                ", zhlsh='" + zhlsh + '\'' +
                ", bdcdybh='" + bdcdybh + '\'' +
                ", ntbdcqzh='" + ntbdcqzh + '\'' +
                ", qlrmcmh='" + qlrmcmh + '\'' +
                ", qlrzjhmh='" + qlrzjhmh + '\'' +
                ", bdcqzhmh='" + bdcqzhmh + '\'' +
                ", dacxFlag='" + dacxFlag + '\'' +
                ", xzqdm='" + xzqdm + '\'' +
                '}';
    }
}
