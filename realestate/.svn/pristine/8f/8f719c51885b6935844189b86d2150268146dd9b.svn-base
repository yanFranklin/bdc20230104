package cn.gtmap.realestate.common.core.dto.exchange.nantong.fscz;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.fssr.DwpjxxtbVoucher;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.fssr.DzfpXzVoucher;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.fssr.HkDzfpVoucher;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.fssr.ResponseVoucher;

import javax.xml.bind.annotation.*;
import java.util.List;

//@XmlRootElement(name = "MOF")
@XmlSeeAlso({
        DzjkdVoucher.class, DzjkdVoucherCz.class, FsczResponse.class, DzhmVoucher.class, DzpjxzVoucher.class,
        DzpjkpVoucher.class, DwpjxxtbVoucher.class, HkDzfpVoucher.class, DzfpXzVoucher.class,
        ResponseVoucher.class, JfztCxVoucher.class
})
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"voucherCount", "voucherBody"})
public class MOFModel<T> {

    private Integer VoucherCount;


    //    @XmlElement(name = "Voucher")

    private List<T> VoucherBody;

    @XmlElement(name = "VoucherCount")
    public Integer getVoucherCount() {
        return VoucherCount;
    }

    public void setVoucherCount(Integer voucherCount) {
        VoucherCount = voucherCount;
    }

    @XmlElementWrapper(name = "VoucherBody")
    @XmlElementRefs(value = {
            @XmlElementRef(type = DzjkdVoucher.class),
            @XmlElementRef(type = DzjkdVoucherCz.class),
            @XmlElementRef(type = FsczResponse.class),
            @XmlElementRef(type = DzhmVoucher.class),
            @XmlElementRef(type = DzpjxzVoucher.class),
            @XmlElementRef(type = DzpjkpVoucher.class),
            @XmlElementRef(type = DwpjxxtbVoucher.class),
            @XmlElementRef(type = HkDzfpVoucher.class),
            @XmlElementRef(type = DzfpXzVoucher.class),
            @XmlElementRef(type = ResponseVoucher.class),
            @XmlElementRef(type = JfztCxVoucher.class),
    })
    public List<T> getVoucherBody() {
        return VoucherBody;
    }

    public void setVoucherBody(List<T> voucherBody) {
        VoucherBody = voucherBody;
    }

}
