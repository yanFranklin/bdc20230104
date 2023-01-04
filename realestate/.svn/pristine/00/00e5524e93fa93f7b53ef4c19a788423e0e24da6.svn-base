package cn.gtmap.realestate.common.core.enums;

import cn.gtmap.realestate.common.util.CommonConstantUtils;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 公告类型枚举值
 * @date : 2022/3/14 14:21
 */
public enum BdcGglxEnum {
    SCDJ(CommonConstantUtils.GGLX_SCDJ_DM, CommonConstantUtils.GGLX_SCDJ, "不动产首次登记公告"),
    JCYZ(CommonConstantUtils.GGLX_JCYZ_DM, CommonConstantUtils.GGLX_JCYZ, "不动产继承/受遗赠登记公告"),
    ZYDJ1(CommonConstantUtils.GGLX_ZYDJ1_DM, CommonConstantUtils.GGLX_ZYDJ1, "无权利义务承继人不动产转移登记公告（一）"),
    ZYDJ2(CommonConstantUtils.GGLX_ZYDJ2_DM, CommonConstantUtils.GGLX_ZYDJ2, "无权利义务承继人不动产转移登记公告（二）"),
    GZDJ(CommonConstantUtils.GGLX_GZDJ_DM, CommonConstantUtils.GGLX_GZDJ, "不动产更正登记公告"),
    ZXDJ(CommonConstantUtils.GGLX_ZXDJ_DM, CommonConstantUtils.GGLX_ZXDJ, "不动产注销登记公告"),
    CXDJ(CommonConstantUtils.GGLX_CXDJ_DM, CommonConstantUtils.GGLX_CXDJ, "不动产撤销登记公告"),
    ZSZMZF(CommonConstantUtils.GGLX_ZSZMZFDJ_DM, CommonConstantUtils.GGLX_ZSZMZFGG, "不动产权证书/登记证明作废公告"),
    ZSZMYS(CommonConstantUtils.GGLX_ZSZMYSGG_DM, CommonConstantUtils.GGLX_ZSZMYSGG, "不动产权证书/登记证明遗失（灭失）声明"),
    YYGG(CommonConstantUtils.GGLX_ZXYY_DM, CommonConstantUtils.GGLX_ZXYY, "征询异议公告"),
    FWZX(CommonConstantUtils.GGLX_FWZX_DM, CommonConstantUtils.GGLX_FWZX, "范围注销公告");

    private Integer gglx;
    private String dm;
    private String mc;

    BdcGglxEnum(Integer gglx, String dm, String mc) {
        this.gglx = gglx;
        this.dm = dm;
        this.mc = mc;
    }

    public static BdcGglxEnum getGglxEnum(Integer gglx) {
        BdcGglxEnum[] gglxs = BdcGglxEnum.values();
        for (BdcGglxEnum gglxEnum : gglxs) {
            if (gglxEnum.gglx.equals(gglx)) {
                return gglxEnum;
            }
        }
        return null;
    }

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public Integer getGglx() {
        return gglx;
    }

    public void setGglx(Integer gglx) {
        this.gglx = gglx;
    }
}