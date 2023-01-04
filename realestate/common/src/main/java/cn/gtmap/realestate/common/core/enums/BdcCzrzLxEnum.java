package cn.gtmap.realestate.common.core.enums;

/**
 * 不动产操作日志类型
 *
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0
 */
public enum BdcCzrzLxEnum {

    CZRZ_CZLX_WWTJ(1, "外网退件"),
    CZRZ_CZLX_SC(2, "删除"),
    CZRZ_CZLX_TSGX(3, "推送共享"),
    CZRZ_CZLX_UPDATE(4, "数据更新"),
    CZRZ_CZLX_YTHCFTS(5, "一体化查封推送"),
    CZRZ_CZLX_XZTZ(6, "选择台账"),
    CZRZ_CZLX_LCZF(7, "流程转发"),
    CZRZ_CZLX_ZHCX(8, "综合查询"),
    CZRZ_CZLX_LZ(9, "落宗"),
    CZRZ_CZLX_QXLZ(10, "取消落宗"),
    CZRZ_CZLX_PP(11, "匹配"),
    CZRZ_CZLX_QXPP(12, "取消匹配"),
    YZW_SJDR(13, "一张网数据导入流程"),
    CZRZ_CZLX_TSYWXX(14, "推送房产交易业务信息"),
    CZRZ_CZLX_YKQJKRK(15, "一卡清缴库入库"),
    CZRZ_CZLX_TDDR(16,"导入土地信息"),
    CZRZ_CZLX_YWCZ(17,"业务操作"),
    CZRZ_CZLX_WLCQXZQLCL(18,"外联产权限制权利处理"),
    CZRZ_CZLX_FCTS(19,"房产部门获取登记数据回调"),
    ;


    BdcCzrzLxEnum(int key, String value){
        this.key = key;
        this.value = value;
    }

    private Integer key;
    private String value;

    public Integer key() {
        return key;
    }

    public String value() {
        return value;
    }

    public static String codeTodesc(Integer code){
        for (BdcCzrzLxEnum bdcCzrzLxEnum : BdcCzrzLxEnum.values()) {
            if(code.equals(bdcCzrzLxEnum.key)){
                return bdcCzrzLxEnum.value;
            }
        }
        return "";
    }
}
