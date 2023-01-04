package cn.gtmap.realestate.etl.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@ApiModel(value = "BdcDsfRlsbVO",description = "人脸识别")
public class BdcDsfRlsbVO {

    private String personId;
    private String personName;
    private String gender;
    private String birth;
    private String family;
    private String address;
    private String idCode;
    private String department;
    private String realdate;
    private Integer similar;
    private String ywnum;
    private Date Createtime;
    private Date Updatetime;
    private String cardImage;
    private String personImage;
    private String faceFeature;
    private Integer role;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRealdate() {
        return realdate;
    }

    public void setRealdate(String realdate) {
        this.realdate = realdate;
    }

    public Integer getSimilar() {
        return similar;
    }

    public void setSimilar(Integer similar) {
        this.similar = similar;
    }

    public String getYwnum() {
        return ywnum;
    }

    public void setYwnum(String ywnum) {
        this.ywnum = ywnum;
    }

    public Date getCreatetime() {
        return Createtime;
    }

    public void setCreatetime(Date createtime) {
        Createtime = createtime;
    }

    public Date getUpdatetime() {
        return Updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        Updatetime = updatetime;
    }

    public String getCardImage() {
        return cardImage;
    }

    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
    }

    public String getPersonImage() {
        return personImage;
    }

    public void setPersonImage(String personImage) {
        this.personImage = personImage;
    }

    public String getFaceFeature() {
        return faceFeature;
    }

    public void setFaceFeature(String faceFeature) {
        this.faceFeature = faceFeature;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }


    public static final class BdcDsfRlsbVOBuilder {
        private String personId;
        private String personName;
        private String gender;
        private String birth;
        private String family;
        private String address;
        private String idCode;
        private String department;
        private String realdate;
        private Integer similar;
        private String ywnum;
        private Date Createtime;
        private Date Updatetime;
        private String cardImage;
        private String personImage;
        private String faceFeature;
        private Integer role;

        private BdcDsfRlsbVOBuilder() {
        }

        public static BdcDsfRlsbVOBuilder aBdcDsfRlsbVO() {
            return new BdcDsfRlsbVOBuilder();
        }

        public BdcDsfRlsbVOBuilder withPersonId(String personId) {
            this.personId = personId;
            return this;
        }

        public BdcDsfRlsbVOBuilder withPersonName(String personName) {
            this.personName = personName;
            return this;
        }

        public BdcDsfRlsbVOBuilder withGender(String gender) {
            this.gender = gender;
            return this;
        }

        public BdcDsfRlsbVOBuilder withBirth(String birth) {
            this.birth = birth;
            return this;
        }

        public BdcDsfRlsbVOBuilder withFamily(String family) {
            this.family = family;
            return this;
        }

        public BdcDsfRlsbVOBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public BdcDsfRlsbVOBuilder withIdCode(String idCode) {
            this.idCode = idCode;
            return this;
        }

        public BdcDsfRlsbVOBuilder withDepartment(String department) {
            this.department = department;
            return this;
        }

        public BdcDsfRlsbVOBuilder withRealdate(String realdate) {
            this.realdate = realdate;
            return this;
        }

        public BdcDsfRlsbVOBuilder withSimilar(Integer similar) {
            this.similar = similar;
            return this;
        }

        public BdcDsfRlsbVOBuilder withYwnum(String ywnum) {
            this.ywnum = ywnum;
            return this;
        }

        public BdcDsfRlsbVOBuilder withCreatetime(Date Createtime) {
            this.Createtime = Createtime;
            return this;
        }

        public BdcDsfRlsbVOBuilder withUpdatetime(Date Updatetime) {
            this.Updatetime = Updatetime;
            return this;
        }

        public BdcDsfRlsbVOBuilder withCardImage(String cardImage) {
            this.cardImage = cardImage;
            return this;
        }

        public BdcDsfRlsbVOBuilder withPersonImage(String personImage) {
            this.personImage = personImage;
            return this;
        }

        public BdcDsfRlsbVOBuilder withFaceFeature(String faceFeature) {
            this.faceFeature = faceFeature;
            return this;
        }

        public BdcDsfRlsbVOBuilder withRole(Integer role) {
            this.role = role;
            return this;
        }

        public BdcDsfRlsbVO build() {
            BdcDsfRlsbVO bdcDsfRlsbVO = new BdcDsfRlsbVO();
            bdcDsfRlsbVO.setPersonId(personId);
            bdcDsfRlsbVO.setPersonName(personName);
            bdcDsfRlsbVO.setGender(gender);
            bdcDsfRlsbVO.setBirth(birth);
            bdcDsfRlsbVO.setFamily(family);
            bdcDsfRlsbVO.setAddress(address);
            bdcDsfRlsbVO.setIdCode(idCode);
            bdcDsfRlsbVO.setDepartment(department);
            bdcDsfRlsbVO.setRealdate(realdate);
            bdcDsfRlsbVO.setSimilar(similar);
            bdcDsfRlsbVO.setYwnum(ywnum);
            bdcDsfRlsbVO.setCreatetime(Createtime);
            bdcDsfRlsbVO.setUpdatetime(Updatetime);
            bdcDsfRlsbVO.setCardImage(cardImage);
            bdcDsfRlsbVO.setPersonImage(personImage);
            bdcDsfRlsbVO.setFaceFeature(faceFeature);
            bdcDsfRlsbVO.setRole(role);
            return bdcDsfRlsbVO;
        }
    }
}
