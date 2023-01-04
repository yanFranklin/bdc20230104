package cn.gtmap.realestate.certificate.core.model.template;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/6/2
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DzzzSignPosition implements Serializable{

    private static final long serialVersionUID = 325544893143440530L;
    // 签章页码
    private Integer page;
    // 签章横坐标起始
    private Integer x;
    // 签章纵坐标起始
    private Integer y;
    // 签章横坐标结束
    private Integer xE;
    // 签章纵坐标结束
    private Integer yE;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getxE() {
        return xE;
    }

    public void setxE(Integer xE) {
        this.xE = xE;
    }

    public Integer getyE() {
        return yE;
    }

    public void setyE(Integer yE) {
        this.yE = yE;
    }
}
