package cn.gtmap.realestate.common.core.dto.certificate.eCertificate;

import cn.gtmap.realestate.common.util.validator.certificate.DzzzZm;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 电子证照义务人信息
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 */
public class EYwrxxDTO {
    @NotBlank(message = "义务人名称", groups = {DzzzZm.class})
    private String ywr;
    @NotBlank(message = "义务人证件种类", groups = {DzzzZm.class})
    private String zjzl;
    @NotBlank(message = "义务人证件种类名称", groups = {DzzzZm.class})
    private String zjzlmc;
    @NotBlank(message = "义务人证件号", groups = {DzzzZm.class})
    private String zjh;

    @Override
    public String toString() {
        return "EYwrxxDTO{" +
                "ywr='" + ywr + '\'' +
                ", zjzl='" + zjzl + '\'' +
                ", zjzlmc='" + zjzlmc + '\'' +
                ", zjh='" + zjh + '\'' +
                '}';
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public String getZjzl() {
        return zjzl;
    }

    public void setZjzl(String zjzl) {
        this.zjzl = zjzl;
    }

    public String getZjzlmc() {
        return zjzlmc;
    }

    public void setZjzlmc(String zjzlmc) {
        this.zjzlmc = zjzlmc;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }
}
