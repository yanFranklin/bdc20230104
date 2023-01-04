package cn.gtmap.realestate.common.core.enums;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/1
 * @description 审批来源与权利人来源枚举值
 */
public enum BdcSplyQlrlyEnum {

    //互联网
    WWSQ(3, 4),

    //银行
    YHXT(4, 5),

    //互联网一窗
    WWSQ_YCSL(5, 4),

    //互联网银行
    WWSQ_YHXT(6, 5),

    //征迁
    ZQ(7, 8),

    //法院
    FY(9, 7);

    // 审批来源
    private Integer sply;

    // 权利人来源
    private Integer qlrly;

    public Integer getSply() {
        return sply;
    }

    public void setSply(Integer sply) {
        this.sply = sply;
    }

    public Integer getQlrly() {
        return qlrly;
    }

    public void setQlrly(Integer qlrly) {
        this.qlrly = qlrly;
    }

    BdcSplyQlrlyEnum(Integer sply, Integer qlrly){
        this.sply = sply;
        this.qlrly = qlrly;
    }

    public static Integer getQlrly(Integer sply) {
        Integer result = null;
        for(BdcSplyQlrlyEnum splyQlrlyEnum : BdcSplyQlrlyEnum.values()) {
            if(splyQlrlyEnum.sply.equals(sply)) {
                result = splyQlrlyEnum.getQlrly();
                return result;
            }
        }
        return result;
    }


}
