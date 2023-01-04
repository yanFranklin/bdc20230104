package cn.gtmap.realestate.common.core.dto.exchange.hefei.wxzjyz.request;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-01
 * @description 维修资金验证 请求 DTO （传入姓名、证件号码、合同备案单号进行查询）
 */
public class WxzjYzRequestDTO {

    // 合同备案单号
    @NotBlank
    private String newHouseBarginNo;

    // 用户名
    private String itemName;

    // 幢号
    private String buildingNo;

    // 姓名
    @NotBlank
    private String name;

    // 房间号
    private String houseNo;

    // 坐落
    private String location;

    // 证件号
    @NotBlank
    private String idNo;

    public String getNewHouseBarginNo() {
        return newHouseBarginNo;
    }

    public void setNewHouseBarginNo(String newHouseBarginNo) {
        this.newHouseBarginNo = newHouseBarginNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
}
