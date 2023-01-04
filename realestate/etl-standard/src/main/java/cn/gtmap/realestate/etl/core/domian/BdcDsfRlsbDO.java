package cn.gtmap.realestate.etl.core.domian;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(
        name = "BDC_DSF_RLSB"
)
@JSONType(serialzeFeatures = {
        SerializerFeature.WriteNullStringAsEmpty,
        SerializerFeature.WriteNullListAsEmpty
})
@ApiModel(value = "BdcDsfRlsbDO",description = "人脸识别")
public class BdcDsfRlsbDO {

    @Id
    @ApiModelProperty(value = "主键id")
    private String id;
    private String personid;
    private String personname;
    private String gender;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date birth;
    private String family;
    private String address;
    private String idcode;
    private String department;
    private String realdate;
    private Integer similar;
    private String ywnum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createtime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatetime;
    private String cardimage;
    private String personimage;
    private String facefeature;
    private String video;
    private Integer role;
    private String parentnodeid;

    public String getParentnodeid() {
        return parentnodeid;
    }

    public void setParentnodeid(String parentnodeid) {
        this.parentnodeid = parentnodeid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
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

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
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
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getCardimage() {
        return cardimage;
    }

    public void setCardimage(String cardimage) {
        this.cardimage = cardimage;
    }

    public String getPersonimage() {
        return personimage;
    }

    public void setPersonimage(String personimage) {
        this.personimage = personimage;
    }

    public String getFacefeature() {
        return facefeature;
    }

    public void setFacefeature(String facefeature) {
        this.facefeature = facefeature;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }


    public static final class BdcDsfRlsbDOBuilder {
        private String id;
        private String personid;
        private String personname;
        private String gender;
        private Date birth;
        private String family;
        private String address;
        private String idcode;
        private String department;
        private String realdate;
        private Integer similar;
        private String ywnum;
        private Date createtime;
        private Date updatetime;
        private String cardimage;
        private String personimage;
        private String facefeature;
        private String video;
        private Integer role;
        private String parentnodeid;

        private BdcDsfRlsbDOBuilder() {
        }

        public static BdcDsfRlsbDOBuilder aBdcDsfRlsbDO() {
            return new BdcDsfRlsbDOBuilder();
        }

        public BdcDsfRlsbDOBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public BdcDsfRlsbDOBuilder withPersonid(String personid) {
            this.personid = personid;
            return this;
        }

        public BdcDsfRlsbDOBuilder withPersonname(String personname) {
            this.personname = personname;
            return this;
        }

        public BdcDsfRlsbDOBuilder withGender(String gender) {
            this.gender = gender;
            return this;
        }

        public BdcDsfRlsbDOBuilder withBirth(Date birth) {
            this.birth = birth;
            return this;
        }

        public BdcDsfRlsbDOBuilder withFamily(String family) {
            this.family = family;
            return this;
        }

        public BdcDsfRlsbDOBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public BdcDsfRlsbDOBuilder withIdcode(String idcode) {
            this.idcode = idcode;
            return this;
        }

        public BdcDsfRlsbDOBuilder withDepartment(String department) {
            this.department = department;
            return this;
        }

        public BdcDsfRlsbDOBuilder withRealdate(String realdate) {
            this.realdate = realdate;
            return this;
        }

        public BdcDsfRlsbDOBuilder withSimilar(Integer similar) {
            this.similar = similar;
            return this;
        }

        public BdcDsfRlsbDOBuilder withYwnum(String ywnum) {
            this.ywnum = ywnum;
            return this;
        }

        public BdcDsfRlsbDOBuilder withCreatetime(Date createtime) {
            this.createtime = createtime;
            return this;
        }

        public BdcDsfRlsbDOBuilder withUpdatetime(Date updatetime) {
            this.updatetime = updatetime;
            return this;
        }

        public BdcDsfRlsbDOBuilder withCardimage(String cardimage) {
            this.cardimage = cardimage;
            return this;
        }

        public BdcDsfRlsbDOBuilder withPersonimage(String personimage) {
            this.personimage = personimage;
            return this;
        }

        public BdcDsfRlsbDOBuilder withFacefeature(String facefeature) {
            this.facefeature = facefeature;
            return this;
        }

        public BdcDsfRlsbDOBuilder withVideo(String video) {
            this.video = video;
            return this;
        }

        public BdcDsfRlsbDOBuilder withRole(Integer role) {
            this.role = role;
            return this;
        }

        public BdcDsfRlsbDOBuilder withParentnodeid(String parentnodeid) {
            this.parentnodeid = parentnodeid;
            return this;
        }

        public BdcDsfRlsbDO build() {
            BdcDsfRlsbDO bdcDsfRlsbDO = new BdcDsfRlsbDO();
            bdcDsfRlsbDO.setId(id);
            bdcDsfRlsbDO.setPersonid(personid);
            bdcDsfRlsbDO.setPersonname(personname);
            bdcDsfRlsbDO.setGender(gender);
            bdcDsfRlsbDO.setBirth(birth);
            bdcDsfRlsbDO.setFamily(family);
            bdcDsfRlsbDO.setAddress(address);
            bdcDsfRlsbDO.setIdcode(idcode);
            bdcDsfRlsbDO.setDepartment(department);
            bdcDsfRlsbDO.setRealdate(realdate);
            bdcDsfRlsbDO.setSimilar(similar);
            bdcDsfRlsbDO.setYwnum(ywnum);
            bdcDsfRlsbDO.setCreatetime(createtime);
            bdcDsfRlsbDO.setUpdatetime(updatetime);
            bdcDsfRlsbDO.setCardimage(cardimage);
            bdcDsfRlsbDO.setPersonimage(personimage);
            bdcDsfRlsbDO.setFacefeature(facefeature);
            bdcDsfRlsbDO.setVideo(video);
            bdcDsfRlsbDO.setRole(role);
            bdcDsfRlsbDO.setParentnodeid(parentnodeid);
            return bdcDsfRlsbDO;
        }
    }
}
