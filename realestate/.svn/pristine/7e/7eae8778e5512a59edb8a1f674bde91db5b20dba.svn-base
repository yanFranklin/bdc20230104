package cn.gtmap.realestate.etl.core.thirdparty.order.response;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

    private static final long serialVersionUID = -6838013706066529495L;

    private String djbh;

    private String mc;

    private List<Img> img;

    @XmlElement
    public List<Img> getImg() {
        return img;
    }

    public void setImgList(List<Img> img) {
        this.img = img;
    }

    @XmlAttribute
    public String getDjbh() {
        return djbh;
    }

    public void setDjbh(String djbh) {
        this.djbh = djbh;
    }

    @XmlAttribute
    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public static final class DataBuilder {
        private String djbh;
        private String mc;
        private List<Img> img;

        private DataBuilder() {
        }

        public static DataBuilder aData() {
            return new DataBuilder();
        }

        public DataBuilder withDjbh(String djbh) {
            this.djbh = djbh;
            return this;
        }

        public DataBuilder withMc(String mc) {
            this.mc = mc;
            return this;
        }

        public DataBuilder withImg(List<Img> img) {
            this.img = img;
            return this;
        }

        public Data build() {
            Data data = new Data();
            data.setDjbh(djbh);
            data.setMc(mc);
            data.img = this.img;
            return data;
        }
    }
}
