package cn.gtmap.realestate.exchange.core.dto.wwsq.getWwsqCqzxx.request;

import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-30
 * @description
 */
@IgnoreCast(ignoreNum = 6)
public class GetWwsqCqzxxRequestData {

    // gxrmc，xmid 和 djslbh 必填
    private String gxrmc;

    private String gxrzjh;

    private String djslbh;

    // 模糊
    private String bdcdyh;

    // 模糊
    private String bdcdyhjq;

    // 模糊
    private String zl;

    private String cqzh;

    private String cqzhjc;

    // gxrmc 与 xmid 必填
    private String xmid;
    // 常州 精确查询坐落
    private String zljq;
    // 常州 roomid
    private String roomid;
    // 常州 cxlx 1存量房买卖 0商品房证明
    private String cxlx;
    //常州 根据施工编号查询产权信息
    private String sgbh;
    //盐城 产权证号模糊
    private String cqzhmh;
    //蚌埠  houseid查询权籍数据
    private String houseid;

    private String xzqdm;

    public String getXzqdm() {
        return xzqdm;
    }

    public void setXzqdm(String xzqdm) {
        this.xzqdm = xzqdm;
    }

    public String getHouseid() {
        return houseid;
    }

    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }

    public String getBdcdyhjq() {
        return bdcdyhjq;
    }

    public void setBdcdyhjq(String bdcdyhjq) {
        this.bdcdyhjq = bdcdyhjq;
    }

    public String getCqzhmh() {
        return cqzhmh;
    }

    public void setCqzhmh(String cqzhmh) {
        this.cqzhmh = cqzhmh;
    }

    public String getCxlx() {
        return cxlx;
    }

    public void setCxlx(String cxlx) {
        this.cxlx = cxlx;
    }

    public String getZljq() {
        return zljq;
    }

    public void setZljq(String zljq) {
        this.zljq = zljq;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getGxrmc() {
        return gxrmc;
    }

    public void setGxrmc(String gxrmc) {
        this.gxrmc = gxrmc;
    }

    public String getGxrzjh() {
        return gxrzjh;
    }

    public void setGxrzjh(String gxrzjh) {
        this.gxrzjh = gxrzjh;
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

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getCqzhjc() {
        return cqzhjc;
    }

    public void setCqzhjc(String cqzhjc) {
        this.cqzhjc = cqzhjc;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSgbh() {
        return sgbh;
    }

    public void setSgbh(String sgbh) {
        this.sgbh = sgbh;
    }

    public String getDjslbh() {
        return djslbh;
    }

    public void setDjslbh(String djslbh) {
        this.djslbh = djslbh;
    }
}
