package cn.gtmap.realestate.exchange.core.bo.yzw;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0, 2019/9/24
 * @description 一张网 结果证照信息 电子证照XML
 */
@XmlType(name = "licenseModel", propOrder = {"basicModel", "faceModel"})
@XmlRootElement(name = "License")
public class LicenseModel {

    private BasicModel basicModel;

    private FaceModel faceModel;

    @XmlElement(name = "Basic")
    public BasicModel getBasicModel() {
        return basicModel;
    }

    public void setBasicModel(BasicModel basicModel) {
        this.basicModel = basicModel;
    }

    @XmlElement(name = "Face")
    public FaceModel getFaceModel() {
        return faceModel;
    }

    public void setFaceModel(FaceModel faceModel) {
        this.faceModel = faceModel;
    }
}
