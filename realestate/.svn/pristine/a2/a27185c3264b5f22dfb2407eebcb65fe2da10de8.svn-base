package cn.gtmap.realestate.common.core.dto.certificate.eCertificate;

import cn.gtmap.realestate.common.util.validator.certificate.DzzzZm;
import cn.gtmap.realestate.common.util.validator.certificate.DzzzZs;
import org.hibernate.validator.constraints.NotBlank;
/**
 * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
 * @description 电子证照权利人信息
 */
public class EQlrxxDTO {
    @NotBlank(message = "权利人名称", groups = {DzzzZm.class, DzzzZs.class})
    private String czzt;
    @NotBlank(message = "权利人名称证件号", groups = {DzzzZm.class, DzzzZs.class})
    private String czztdm;
    @NotBlank(message = "权利人证件类型名称", groups = {DzzzZm.class, DzzzZs.class})
    private String czztdmlx;
    @NotBlank(message = "权利人证件类型代码", groups = {DzzzZm.class, DzzzZs.class})
    private String czztdmlxdm;
    //权利人共有比例，共有方式为按份共有时必填
    private String czztgybl;

    public String getCzzt() {
        return czzt;
    }

    public void setCzzt(String czzt) {
        this.czzt = czzt;
    }

    public String getCzztdm() {
        return czztdm;
    }

    public void setCzztdm(String czztdm) {
        this.czztdm = czztdm;
    }

    public String getCzztdmlx() {
        return czztdmlx;
    }

    public void setCzztdmlx(String czztdmlx) {
        this.czztdmlx = czztdmlx;
    }

    public String getCzztdmlxdm() {
        return czztdmlxdm;
    }

    public void setCzztdmlxdm(String czztdmlxdm) {
        this.czztdmlxdm = czztdmlxdm;
    }

    public String getCzztgybl() {
        return czztgybl;
    }

    public void setCzztgybl(String czztgybl) {
        this.czztgybl = czztgybl;
    }

    @Override
    public String toString() {
        return "EQlrxxDTO{" +
                "czzt='" + czzt + '\'' +
                ", czztdm='" + czztdm + '\'' +
                ", czztdmlx='" + czztdmlx + '\'' +
                ", czztdmlxdm='" + czztdmlxdm + '\'' +
                ", czztgybl='" + czztgybl + '\'' +
                '}';
    }
}
