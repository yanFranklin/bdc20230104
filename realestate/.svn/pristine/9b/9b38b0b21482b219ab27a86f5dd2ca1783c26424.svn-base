package cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.qi;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.AbstactFileInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel("南通气过户dto")
public class QiGhxxZjDTO implements Serializable {
    private static final long serialVersionUID = -3202278908353311446L;

    @ApiModelProperty(value = "户号")
    protected String consno;

    @ApiModelProperty(value = "地址")
    protected String address;

    @ApiModelProperty(value = "新用户信息")
    private List<QiNewUser> newUserList;

    @ApiModelProperty(value = "老用户信息")
    private List<QiOriginalUser> originalUserList;

    @ApiModelProperty(value = "资料")
    private List<QiFileInfo> data;


    @ApiModel("原用户信息")
    public static class QiOriginalUser {
        @ApiModelProperty(value = "原用户名")
        private String originalUserName;
        @ApiModelProperty(value = "原用户身份证")
        private String originalUserCard;
        @ApiModelProperty(value = "原用户联系方式")
        private String oldMobile;

        public static QiOriginalUser create(BdcQlrDO item) {
            QiOriginalUser qiOriginalUser = new QiOriginalUser();
            qiOriginalUser.setOriginalUserName(item.getQlrmc());
            qiOriginalUser.setOriginalUserCard(item.getZjh());
            qiOriginalUser.setOldMobile(item.getDh());
            return qiOriginalUser;
        }

        public String getOriginalUserName() {
            return originalUserName;
        }

        public void setOriginalUserName(String originalUserName) {
            this.originalUserName = originalUserName;
        }

        public String getOriginalUserCard() {
            return originalUserCard;
        }

        public void setOriginalUserCard(String originalUserCard) {
            this.originalUserCard = originalUserCard;
        }

        public String getOldMobile() {
            return oldMobile;
        }

        public void setOldMobile(String oldMobile) {
            this.oldMobile = oldMobile;
        }

        @Override
        public String toString() {
            return "QiOriginalUser{" +
                    "originalUserName='" + originalUserName + '\'' +
                    ", originalUserCard='" + originalUserCard + '\'' +
                    ", oldMobile='" + oldMobile + '\'' +
                    '}';
        }
    }

    @ApiModel("新用户信息")
    public static class QiNewUser {
        @ApiModelProperty(value = "新用户名")
        private String newUserName;
        @ApiModelProperty(value = "新用户身份证")
        private String newUserCard;
        @ApiModelProperty(value = "新用户联系方式")
        private String newMobile;

        public static QiNewUser create(BdcQlrDO item) {
            QiNewUser qiNewUser = new QiNewUser();
            qiNewUser.setNewUserName(item.getQlrmc());
            qiNewUser.setNewUserCard(item.getZjh());
            qiNewUser.setNewMobile(item.getDh());
            return qiNewUser;
        }

        public String getNewUserName() {
            return newUserName;
        }

        public void setNewUserName(String newUserName) {
            this.newUserName = newUserName;
        }

        public String getNewUserCard() {
            return newUserCard;
        }

        public void setNewUserCard(String newUserCard) {
            this.newUserCard = newUserCard;
        }

        public String getNewMobile() {
            return newMobile;
        }

        public void setNewMobile(String newMobile) {
            this.newMobile = newMobile;
        }

        @Override
        public String toString() {
            return "QiNewUser{" +
                    "newUserName='" + newUserName + '\'' +
                    ", newUserCard='" + newUserCard + '\'' +
                    ", newMobile='" + newMobile + '\'' +
                    '}';
        }
    }

    @ApiModel("新用户信息")
    public static class QiFileInfo extends AbstactFileInfo {


        @Override
        public String toString() {
            return "QiFileInfo{" +
                    "fileName='" + fileName + '\'' +
                    ", fileData='" + fileData + '\'' +
                    '}';
        }
    }

    public String getConsno() {
        return consno;
    }

    public void setConsno(String consno) {
        this.consno = consno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<QiNewUser> getNewUserList() {
        return newUserList;
    }

    public void setNewUserList(List<QiNewUser> newUserList) {
        this.newUserList = newUserList;
    }

    public List<QiOriginalUser> getOriginalUserList() {
        return originalUserList;
    }

    public void setOriginalUserList(List<QiOriginalUser> originalUserList) {
        this.originalUserList = originalUserList;
    }

    public List<QiFileInfo> getData() {
        return data;
    }

    public void setData(List<QiFileInfo> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "QiGhxxZjDTO{" +
                "consno='" + consno + '\'' +
                ", address='" + address + '\'' +
                ", newUserList=" + newUserList +
                ", originalUserList=" + originalUserList +
                ", data=" + data +
                '}';
    }
}
