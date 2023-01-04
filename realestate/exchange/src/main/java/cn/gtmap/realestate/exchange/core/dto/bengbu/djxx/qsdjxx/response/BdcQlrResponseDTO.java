package cn.gtmap.realestate.exchange.core.dto.bengbu.djxx.qsdjxx.response;

/**
 * @description 人员信息
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @date 2022/12/22 10:47
 */
public class BdcQlrResponseDTO {

    /**
     * 主体名称
     */
    private String entName;

    /**
     * 主体证件类型
     */
    private String idTypeTs;

    /**
     * 证件号
     */
    private String entId;

    /**
     * 主体类型
     */
    private String entTypeTs;

    /**
     * 份额
     */
    private String portion;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 不动产权证号
     */
    private String bdcqzh;

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getIdTypeTs() {
        return idTypeTs;
    }

    public void setIdTypeTs(String idTypeTs) {
        this.idTypeTs = idTypeTs;
    }

    public String getEntId() {
        return entId;
    }

    public void setEntId(String entId) {
        this.entId = entId;
    }

    public String getEntTypeTs() {
        return entTypeTs;
    }

    public void setEntTypeTs(String entTypeTs) {
        this.entTypeTs = entTypeTs;
    }

    public String getPortion() {
        return portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    @Override
    public String toString() {
        return "BdcQlrResponseDTO{" +
                "entName='" + entName + '\'' +
                ", idTypeTs='" + idTypeTs + '\'' +
                ", entId='" + entId + '\'' +
                ", entTypeTs='" + entTypeTs + '\'' +
                ", portion='" + portion + '\'' +
                ", phone='" + phone + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                '}';
    }
}
