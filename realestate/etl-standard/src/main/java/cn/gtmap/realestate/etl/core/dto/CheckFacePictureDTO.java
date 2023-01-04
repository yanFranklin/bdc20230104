package cn.gtmap.realestate.etl.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CheckFacePictureDTO", description = "人脸识别上传和查询对象")
public class CheckFacePictureDTO {

    @ApiModelProperty(value = "项目id")
    private String ywnum;

    @ApiModelProperty(value = "照片base64")
    private String xczpimg;

    public String getYwnum() {
        return ywnum;
    }

    public void setYwnum(String ywnum) {
        this.ywnum = ywnum;
    }

    public String getXczpimg() {
        return xczpimg;
    }

    public void setXczpimg(String xczpimg) {
        this.xczpimg = xczpimg;
    }


    @Override
    public String toString() {
        return "CheckFacePicture{" +
                "ywnum='" + ywnum + '\'' +
                ", xczpimg='" + xczpimg + '\'' +
                '}';
    }

    public static final class CheckFacePictureBuilder {
        private String ywnum;
        private String xczpimg;

        private CheckFacePictureBuilder() {
        }

        public static CheckFacePictureBuilder aCheckFacePicture() {
            return new CheckFacePictureBuilder();
        }

        public CheckFacePictureBuilder withYwnum(String ywnum) {
            this.ywnum = ywnum;
            return this;
        }

        public CheckFacePictureBuilder withXczpimg(String xczpimg) {
            this.xczpimg = xczpimg;
            return this;
        }

        public CheckFacePictureDTO build() {
            CheckFacePictureDTO checkFacePicture = new CheckFacePictureDTO();
            checkFacePicture.setYwnum(ywnum);
            checkFacePicture.setXczpimg(xczpimg);
            return checkFacePicture;
        }
    }
}
