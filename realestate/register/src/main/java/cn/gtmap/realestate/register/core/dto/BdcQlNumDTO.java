package cn.gtmap.realestate.register.core.dto;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/7/21
 * @description 各个权利的数量
 */
public class BdcQlNumDTO {
    /**
     * 产权数量
     */
    Integer cqNum;
    /**
     * 预告
     */
    Integer ygNum;
    /**
     * 异议
     */
    Integer yyNum;
    /**
     * 查封
     */
    Integer cfNum;
    /**
     * 抵押
     */
    Integer dyaqNum;
    /**
     * 地役
     */
    Integer dyiqNum;

    /**
     * 房屋租赁
     */
    Integer fwzlNum;

    /**
     * 土地经营权
     */
    Integer jyqNum;

    /**
     * 居住权
     */
    Integer jzqNum;

    public BdcQlNumDTO() {
        this.cqNum = 0;
        this.cfNum = 0;
        this.dyaqNum = 0;
        this.dyiqNum = 0;
        this.fwzlNum = 0;
        this.ygNum = 0;
        this.yyNum = 0;
        this.jyqNum = 0;
        this.jzqNum = 0;
    }

    public Integer getCfNum() {
        return cfNum;
    }

    public void setCfNum(Integer cfNum) {
        this.cfNum = cfNum;
    }

    public Integer getDyaqNum() {
        return dyaqNum;
    }

    public void setDyaqNum(Integer dyaqNum) {
        this.dyaqNum = dyaqNum;
    }

    public Integer getDyiqNum() {
        return dyiqNum;
    }

    public void setDyiqNum(Integer dyiqNum) {
        this.dyiqNum = dyiqNum;
    }

    public Integer getFwzlNum() {
        return fwzlNum;
    }

    public void setFwzlNum(Integer fwzlNum) {
        this.fwzlNum = fwzlNum;
    }

    public Integer getYgNum() {
        return ygNum;
    }

    public void setYgNum(Integer ygNum) {
        this.ygNum = ygNum;
    }

    public Integer getYyNum() {
        return yyNum;
    }

    public void setYyNum(Integer yyNum) {
        this.yyNum = yyNum;
    }

    public Integer getCqNum() {
        return cqNum;
    }

    public void setCqNum(Integer cqNum) {
        this.cqNum = cqNum;
    }

    public Integer getJyqNum() {
        return jyqNum;
    }

    public void setJyqNum(Integer jyqNum) {
        this.jyqNum = jyqNum;
    }

    public Integer getJzqNum() {
        return jzqNum;
    }

    public void setJzqNum(Integer jzqNum) {
        this.jzqNum = jzqNum;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcQlNumDTO{");
        sb.append("cqNum=").append(cqNum);
        sb.append(", ygNum=").append(ygNum);
        sb.append(", yyNum=").append(yyNum);
        sb.append(", cfNum=").append(cfNum);
        sb.append(", dyaqNum=").append(dyaqNum);
        sb.append(", dyiqNum=").append(dyiqNum);
        sb.append(", fwzlNum=").append(fwzlNum);
        sb.append(", jyqNum=").append(jyqNum);
        sb.append(", jzqNum=").append(jzqNum);
        sb.append('}');
        return sb.toString();
    }
}
