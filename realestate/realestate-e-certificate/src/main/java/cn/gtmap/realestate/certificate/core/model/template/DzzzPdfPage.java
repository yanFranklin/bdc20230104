package cn.gtmap.realestate.certificate.core.model.template;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/8/19
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DzzzPdfPage implements Serializable{
    private static final long serialVersionUID = 5369958919944857947L;
    // 页码
    private int pageNo;
    // 背景图片
    @XmlElementWrapper(name = "imgs")
    private List<DzzzImage> img;
    // 输出内容
    @XmlElementWrapper(name = "texts")
    private List<DzzzText> text;
    // 表格
    @XmlElementWrapper(name = "tables")
    private List<DzzzTable> table;
    // 二维码
    private DzzzQrcode qrcode;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public List<DzzzImage> getImg() {
        return img;
    }

    public void setImg(List<DzzzImage> img) {
        this.img = img;
    }

    public DzzzQrcode getQrcode() {
        return qrcode;
    }

    public void setQrcode(DzzzQrcode qrcode) {
        this.qrcode = qrcode;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<DzzzText> getText() {
        return text;
    }

    public void setText(List<DzzzText> text) {
        this.text = text;
    }

    public List<DzzzTable> getTable() {
        return table;
    }

    public void setTable(List<DzzzTable> table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "DzzzPdfPage [pageNo=" + pageNo
                + ", backgroundImg=" + img
                + ", text=" + text
                + ", table=" + table
                + ", qrcode=" + qrcode + "]";
    }
}
