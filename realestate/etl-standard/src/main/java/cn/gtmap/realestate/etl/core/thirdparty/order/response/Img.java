package cn.gtmap.realestate.etl.core.thirdparty.order.response;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

public class Img implements Serializable {

    private static final long serialVersionUID = -6838013706066529495L;

    private String mc;

    private String sl;

    private String id;

    private String url;

    @XmlElement
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlElement
    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    @XmlElement
    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public static final class ImgBuilder {
        private String mc;
        private String sl;
        private String id;
        private String url;

        private ImgBuilder() {
        }

        public static ImgBuilder anImg() {
            return new ImgBuilder();
        }

        public ImgBuilder withMc(String mc) {
            this.mc = mc;
            return this;
        }

        public ImgBuilder withSl(String sl) {
            this.sl = sl;
            return this;
        }

        public ImgBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public ImgBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Img build() {
            Img img = new Img();
            img.setMc(mc);
            img.setSl(sl);
            img.setId(id);
            img.setUrl(url);
            return img;
        }
    }
}
