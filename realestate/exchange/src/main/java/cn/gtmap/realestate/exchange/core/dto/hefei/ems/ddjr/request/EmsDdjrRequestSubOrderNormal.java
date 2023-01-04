package cn.gtmap.realestate.exchange.core.dto.hefei.ems.ddjr.request;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-16
 * @description
 */
public class EmsDdjrRequestSubOrderNormal {

   // 子单序号
   private String subIndex;

   // 实际重量
   private String weight;

   // 体积重量，单位：克
   private String volumeWeight;

   // 计费重量，单位：克
   private String feeWeigth;


    public String getSubIndex() {
        return subIndex;
    }

    public void setSubIndex(String subIndex) {
        this.subIndex = subIndex;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVolumeWeight() {
        return volumeWeight;
    }

    public void setVolumeWeight(String volumeWeight) {
        this.volumeWeight = volumeWeight;
    }

    public String getFeeWeigth() {
        return feeWeigth;
    }

    public void setFeeWeigth(String feeWeigth) {
        this.feeWeigth = feeWeigth;
    }
}
