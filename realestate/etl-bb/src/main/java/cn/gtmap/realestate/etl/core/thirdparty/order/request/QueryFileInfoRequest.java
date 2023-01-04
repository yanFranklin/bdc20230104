package cn.gtmap.realestate.etl.core.thirdparty.order.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "root")
public class QueryFileInfoRequest implements Serializable {

    private static final long serialVersionUID = 1524539488789070843L;

    /**
     * <root>
     * <param flag="3">
     * <djbh>20190719191855</djbh>
     * <mc>身份证</mc>
     * <xh>1</xh>
     * </param>
     * </root>
     */

    private Param param;

    @XmlElement(name = "param")
    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
    }
}
