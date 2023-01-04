package cn.gtmap.realestate.common.core.dto.exchange.standard.esign.cjqslc;

import java.util.List;

public class EsignQswjDTO {

    /**
     * fjid : 附件id
     * qsxxList : [{"qslx":"签署类型；0-不限(需用 户手动拖拽印章完成签 署，自动签署不支持)、 1-单页签、2-多页签、3- 骑缝章、4关键字签","qsy":"签署页","key":"关键字 若signType为关键字签 则不能为空","keyIndex":"指定签署第几个关键 字,-1为最后一个,超过关 键字总数选中最后一 个；当关键字签传了 posPage和keyIndex， 表示在指定页里指定第几个关键字进行签署","posX":"x轴偏移量，正数向右偏 移，负数向左偏移; 单页签/多页签必填，以 相对页左下角为原点进 行偏移；骑缝签无视该参数； 关键字签非必填，以关 键字的左下角为原点进 行偏移","posY":"y轴偏移量，正数向上偏 移，负数向下偏移; 单页签/多页签/骑缝签必 填，以相对页左下角为 原点进行偏移； 关键字签非必填，以关 键字的左下角为原点进 行偏移","width":"签名域宽度，为空则取 印章大小","qswzlx":"签署位置类型， PERSON-个人,ORGANIZE-企业,LEGAL-法定代表 人,NOLIMIT-不限制;多 个用','隔开,默认 NOLIMIT","qsfs":""}]
     */

    private String fjid;
    private List<EsignQsxxDTO> qsxxList;

    public String getFjid() {
        return fjid;
    }

    public void setFjid(String fjid) {
        this.fjid = fjid;
    }

    public List<EsignQsxxDTO> getQsxxList() {
        return qsxxList;
    }

    public void setQsxxList(List<EsignQsxxDTO> qsxxList) {
        this.qsxxList = qsxxList;
    }

    @Override
    public String toString() {
        return "EsignQswjDTO{" +
                "fjid='" + fjid + '\'' +
                ", qsxxList=" + qsxxList +
                '}';
    }
}
