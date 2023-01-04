package cn.gtmap.realestate.certificate.core.enums;

import cn.gtmap.realestate.common.util.CommonConstantUtils;

public enum BdcZzlxEnum {
    ZS(CommonConstantUtils.ZSLX_ZS, "11100000MB03271699001", "中华人民共和国不动产权证"),
    ZMD(CommonConstantUtils.ZSLX_ZMD, "11100000MB03271699001", "中华人民共和国不动产权证"),
    ZM(CommonConstantUtils.ZSLX_ZM, "11100000MB03271699022", "中华人民共和国不动产登记证明");

    private Integer zslx;
    private String dm;
    private String mc;

    BdcZzlxEnum(Integer zslx, String dm, String mc) {
        this.zslx = zslx;
        this.dm = dm;
        this.mc = mc;
    }

    public static BdcZzlxEnum getZzlxEnum(Integer zslx) {
        BdcZzlxEnum[] zzlxs = BdcZzlxEnum.values();
        for (BdcZzlxEnum zzlx : zzlxs) {
            if (zzlx.zslx.equals(zslx)) {
                return zzlx;
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

    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }
}