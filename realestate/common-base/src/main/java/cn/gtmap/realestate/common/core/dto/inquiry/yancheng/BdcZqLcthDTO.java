package cn.gtmap.realestate.common.core.dto.inquiry.yancheng;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/12/30
 * @description 盐城征迁流程退回参数DTO
 */
public class BdcZqLcthDTO {
    /**
     * 退回状态
     * @see cn.gtmap.realestate.common.core.enums.BdcZqShztEnum
     */
    private Integer thzt;

    /**
     * 退回原因
     */
    private String thyy;

    /**
     * 工作流实例ID
     */
    private String gzlslid;


    public Integer getThzt() {
        return thzt;
    }

    public void setThzt(Integer thzt) {
        this.thzt = thzt;
    }

    public String getThyy() {
        return thyy;
    }

    public void setThyy(String thyy) {
        this.thyy = thyy;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    @Override
    public String toString() {
        return "BdcZqLcthDTO{" +
                "thzt=" + thzt +
                ", thyy='" + thyy + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                '}';
    }
}
