package cn.gtmap.realestate.common.core.dto.etl;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 2021/07/28,1.0
 * @description
 */
public class DyxxDTO {

    private String dyqbdcdjzmh;     //抵押权不动产登记证明号
    private String dydjbh;          //抵押登记编号
    private String bdcdyh;          //不动产单元号
    private String dyqr;            //抵押权人
    private String dyr;             //抵押人
    private String fwzl;             //房屋坐落
    private String dymj;            //抵押面积
    private String dyje;            //抵押金额
    private String dylx;            //抵押类型
    private String dyqx;            //2014-06-09到2024-06-09
    private String dydbsj;         //抵押登簿时间
    private String dyzxsj;          //抵押注销时间
    private String bz;              //备注

    @XmlElement(name = "dyqbdcdjzmh",nillable = true)
    public String getDyqbdcdjzmh() {
        return dyqbdcdjzmh;
    }

    public void setDyqbdcdjzmh(String dyqbdcdjzmh) {
        this.dyqbdcdjzmh = dyqbdcdjzmh;
    }

    @XmlElement(name = "dydjbh",nillable = true)
    public String getDydjbh() {
        return dydjbh;
    }

    public void setDydjbh(String dydjbh) {
        this.dydjbh = dydjbh;
    }

    @XmlElement(name = "bdcdyh",nillable = true)
    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @XmlElement(name = "dyqr",nillable = true)
    public String getDyqr() {
        return dyqr;
    }

    public void setDyqr(String dyqr) {
        this.dyqr = dyqr;
    }

    @XmlElement(name = "dymj",nillable = true)
    public String getDymj() {
        return dymj;
    }

    public void setDymj(String dymj) {
        this.dymj = dymj;
    }

    @XmlElement(name = "dyje",nillable = true)
    public String getDyje() {
        return dyje;
    }

    public void setDyje(String dyje) {
        this.dyje = dyje;
    }

    @XmlElement(name = "dylx",nillable = true)
    public String getDylx() {
        return dylx;
    }

    public void setDylx(String dylx) {
        this.dylx = dylx;
    }

    @XmlElement(name = "dyqx",nillable = true)
    public String getDyqx() {
        return dyqx;
    }

    public void setDyqx(String dyqx) {
        this.dyqx = dyqx;
    }

    @XmlElement(name = "dydbsj",nillable = true)
    public String getDydbsj() {
        return dydbsj;
    }

    public void setDydbsj(String dydbsj) {
        this.dydbsj = dydbsj;
    }

    @XmlElement(name = "dyzxsj",nillable = true)
    public String getDyzxsj() {
        return dyzxsj;
    }

    public void setDyzxsj(String dyzxsj) {
        this.dyzxsj = dyzxsj;
    }

    @XmlElement(name = "bz",nillable = true)
    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @XmlElement(name = "dyr",nillable = true)
    public String getDyr() {
        return dyr;
    }

    public void setDyr(String dyr) {
        this.dyr = dyr;
    }

    @XmlElement(name = "fwzl",nillable = true)
    public String getFwzl() {
        return fwzl;
    }

    public void setFwzl(String fwzl) {
        this.fwzl = fwzl;
    }
}
