package cn.gtmap.realestate.exchange.core.dto.nantong.sq.response.qi;
/**
 * @Author  <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description 检查户号返回实体类
 * @Date 2022/4/27
 **/
public class QiCheckConsnoDataDTo {

    /**
     * 原户主姓名
     */
    private String originalUserName;
    /**
     * 地址
     */
    private String address;
    /**
     * 是否可过户 0:可过户1：不可过户
     */
    private Integer sfgh;
    /**
     * 不可过户原因
     */
    private String yy;

    public String getOriginalUserName() {
        return originalUserName;
    }

    public void setOriginalUserName(String originalUserName) {
        this.originalUserName = originalUserName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSfgh() {
        return sfgh;
    }

    public void setSfgh(Integer sfgh) {
        this.sfgh = sfgh;
    }

    public String getYy() {
        return yy;
    }

    public void setYy(String yy) {
        this.yy = yy;
    }

    @Override
    public String toString() {
        return "QiCheckConsnoDataDTo{" +
                "originalUserName='" + originalUserName + '\'' +
                ", address='" + address + '\'' +
                ", sfgh=" + sfgh +
                ", yy='" + yy + '\'' +
                '}';
    }
}
