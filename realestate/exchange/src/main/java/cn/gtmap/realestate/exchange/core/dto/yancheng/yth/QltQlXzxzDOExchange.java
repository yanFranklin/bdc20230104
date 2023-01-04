package cn.gtmap.realestate.exchange.core.dto.yancheng.yth;

import java.io.Serializable;


/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 2019/0417,1.0
 * @description
 */
public class QltQlXzxzDOExchange implements Serializable {

    private static final long serialVersionUID = 2218203587372851657L;

    private String ywh;
    private String bdcdyh; //不动产单元号",
    private String xzrqc; //限制人全称",
    private String xzrzjzl; //限制人证件种类",
    private String xzrzjhm; //限制人证件号码",
    private String djsj; //登记时间",
    private String djyy; //登记原因"

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getXzrqc() {
        return xzrqc;
    }

    public void setXzrqc(String xzrqc) {
        this.xzrqc = xzrqc;
    }

    public String getXzrzjzl() {
        return xzrzjzl;
    }

    public void setXzrzjzl(String xzrzjzl) {
        this.xzrzjzl = xzrzjzl;
    }

    public String getXzrzjhm() {
        return xzrzjhm;
    }

    public void setXzrzjhm(String xzrzjhm) {
        this.xzrzjhm = xzrzjhm;
    }

    public String getDjsj() {
        return djsj;
    }

    public void setDjsj(String djsj) {
        this.djsj = djsj;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }
}
