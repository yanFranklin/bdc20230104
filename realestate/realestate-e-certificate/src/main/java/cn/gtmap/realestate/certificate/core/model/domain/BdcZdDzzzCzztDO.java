package cn.gtmap.realestate.certificate.core.model.domain;



/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, 2019/1/17
 * @description 不动产电子证照持证主体字典表
 */

import java.io.Serializable;

public class BdcZdDzzzCzztDO implements Serializable {

    private static final long serialVersionUID = 2567912475003646124L;
    //持证主体代码
    private String czztdm;
    //持证主体代码类型
    private String czztdmlx;
    //持证主体类别细分
    private String czztlbxf;
    //持证主体类别
    private String czztlb;


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

    public String getCzztlbxf() {
        return czztlbxf;
    }

    public void setCzztlbxf(String czztlbxf) {
        this.czztlbxf = czztlbxf;
    }

    public String getCzztlb() {
        return czztlb;
    }

    public void setCzztlb(String czztlb) {
        this.czztlb = czztlb;
    }
}
