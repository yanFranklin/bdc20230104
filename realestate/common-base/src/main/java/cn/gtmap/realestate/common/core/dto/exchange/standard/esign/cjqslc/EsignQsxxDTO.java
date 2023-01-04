package cn.gtmap.realestate.common.core.dto.exchange.standard.esign.cjqslc;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/10/20
 * @description 签署信息
 */
public class EsignQsxxDTO {


    /**
     * qslx : 签署类型；0-不限(需用 户手动拖拽印章完成签 署，自动签署不支持)、 1-单页签、2-多页签、3- 骑缝章、4关键字签
     * qsy : 签署页
     * key : 关键字 若signType为关键字签 则不能为空
     * keyIndex : 指定签署第几个关键 字,-1为最后一个,超过关 键字总数选中最后一 个；当关键字签传了 posPage和keyIndex， 表示在指定页里指定第几个关键字进行签署
     * posX : x轴偏移量，正数向右偏 移，负数向左偏移; 单页签/多页签必填，以 相对页左下角为原点进 行偏移；骑缝签无视该参数； 关键字签非必填，以关 键字的左下角为原点进 行偏移
     * posY : y轴偏移量，正数向上偏 移，负数向下偏移; 单页签/多页签/骑缝签必 填，以相对页左下角为 原点进行偏移； 关键字签非必填，以关 键字的左下角为原点进 行偏移
     * width : 签名域宽度，为空则取 印章大小
     * qswzlx : 签署位置类型， PERSON-个人,ORGANIZE-企业,LEGAL-法定代表 人,NOLIMIT-不限制;多 个用','隔开,默认 NOLIMIT
     * qsfs :
     */

    private String qslx;
    private String qsy;
    private String key;
    private String keyIndex;
    private String posX;
    private String posY;
    private String width;
    private String qswzlx;
    private String qsfs;

    public String getQslx() {
        return qslx;
    }

    public void setQslx(String qslx) {
        this.qslx = qslx;
    }

    public String getQsy() {
        return qsy;
    }

    public void setQsy(String qsy) {
        this.qsy = qsy;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyIndex() {
        return keyIndex;
    }

    public void setKeyIndex(String keyIndex) {
        this.keyIndex = keyIndex;
    }

    public String getPosX() {
        return posX;
    }

    public void setPosX(String posX) {
        this.posX = posX;
    }

    public String getPosY() {
        return posY;
    }

    public void setPosY(String posY) {
        this.posY = posY;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getQswzlx() {
        return qswzlx;
    }

    public void setQswzlx(String qswzlx) {
        this.qswzlx = qswzlx;
    }

    public String getQsfs() {
        return qsfs;
    }

    public void setQsfs(String qsfs) {
        this.qsfs = qsfs;
    }

    @Override
    public String toString() {
        return "EsignQsxxDTO{" +
                "qslx='" + qslx + '\'' +
                ", qsy='" + qsy + '\'' +
                ", key='" + key + '\'' +
                ", keyIndex='" + keyIndex + '\'' +
                ", posX='" + posX + '\'' +
                ", posY='" + posY + '\'' +
                ", width='" + width + '\'' +
                ", qswzlx='" + qswzlx + '\'' +
                ", qsfs='" + qsfs + '\'' +
                '}';
    }
}
