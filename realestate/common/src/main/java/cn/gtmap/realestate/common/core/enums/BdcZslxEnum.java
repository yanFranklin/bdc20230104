package cn.gtmap.realestate.common.core.enums;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/5
 * @description 不动产证书类型枚举类
 */
public enum BdcZslxEnum {
    ZS(1, "证书", "不动产权"),
    ZM(2, "证明", "不动产证明"),
    SCDJ(3, "不动产权证明单", "不动产权证明单"),
    SCDJ_BB(3, "不动产权利证明书", "不动产权利证明书", CommonConstantUtils.SYSTEM_VERSION_BB),
    SCDJ_YC(3, "首次登记证", "首次登记证", CommonConstantUtils.SYSTEM_VERSION_YC);

    /**
     * 证书版本
     */
    private String version;
    /**
     * 证书类型字典，例如 1 、2、3
     */
    private Integer zslx;

    /**
     * 类型名称，例如证书、证明、不动产权证明单
     */
    private String lxmc;

    /**
     * 证号里面类型名称，例如 皖(2016)芜湖县不动产证明第0000129号 里的不动产证明
     */
    private String zhlx;


    BdcZslxEnum(Integer zslx, String lxmc, String zhlx) {
        this.zslx = zslx;
        this.lxmc = lxmc;
        this.zhlx = zhlx;
        version = null;
    }

    BdcZslxEnum(Integer zslx, String lxmc, String zhlx, String version) {
        this.zslx = zslx;
        this.lxmc = lxmc;
        this.zhlx = zhlx;
        this.version = version;
    }

    /**
     * 根据证书类型获取其对应证号里面应该设置的证书类型名称
     *
     * @param zslx    证书类型
     * @param version 版本
     * @return {String} 证号里面证书类型名称
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    public static String getZhlx(Integer zslx, String version) {
        if (version != null) {
            // 有默认值先走默认值
            for (BdcZslxEnum bdcZslxEnum : BdcZslxEnum.values()) {
                if (StringUtils.equals(bdcZslxEnum.getVersion(), version) && bdcZslxEnum.getZslx().equals(zslx)) {
                    return bdcZslxEnum.getZhlx();
                }
            }
        }
        for (BdcZslxEnum bdcZslxEnum : BdcZslxEnum.values()) {
            if (bdcZslxEnum.getZslx().equals(zslx)) {
                return bdcZslxEnum.getZhlx();
            }
        }
        return null;
    }

    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public String getLxmc() {
        return lxmc;
    }

    public void setLxmc(String lxmc) {
        this.lxmc = lxmc;
    }

    public String getZhlx() {
        return zhlx;
    }

    public void setZhlx(String zhlx) {
        this.zhlx = zhlx;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
