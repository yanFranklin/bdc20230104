package cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.shui;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.AbstactFileInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel("南通气过户dto")
public class ShuiGhxxZjDTO implements Serializable {
    private static final long serialVersionUID = -3202278908353311446L;

    @ApiModelProperty(value = "户号")
    protected String consno;

    @ApiModelProperty(value = "地址")
    protected String address;

    @ApiModelProperty(value = "新用户信息")
    private List<ShuiNewUser> newUserList;

    @ApiModelProperty(value = "老用户信息")
    private List<ShuiOriginalUser> originalUserList;

    @ApiModelProperty(value = "资料")
    private List<ShuiFileInfo> data;


    @ApiModel("原用户信息")
    public static class ShuiOriginalUser {
        @ApiModelProperty(value = "原用户名")
        private String originalUserName;
        @ApiModelProperty(value = "原用户身份证")
        private String originalUserCard;
        @ApiModelProperty(value = "原用户联系方式")
        private String oldMobile;

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
            return "ShuiOriginalUser{" +
                    "originalUserName='" + originalUserName + '\'' +
                    ", originalUserCard='" + originalUserCard + '\'' +
                    ", oldMobile='" + oldMobile + '\'' +
                    '}';
        }

        public static ShuiOriginalUser create(BdcQlrDO item) {
            ShuiOriginalUser shuiOriginalUser = new ShuiOriginalUser();
            shuiOriginalUser.setOriginalUserName(item.getQlrmc());
            shuiOriginalUser.setOriginalUserCard(item.getZjh());
            shuiOriginalUser.setOldMobile(item.getDh());
            return shuiOriginalUser;
        }
    }

    @ApiModel("新用户信息")
    public static class ShuiNewUser {
        @ApiModelProperty(value = "新用户名")
        private String newUserName;
        @ApiModelProperty(value = "新用户身份证")
        private String newUserCard;
        @ApiModelProperty(value = "新用户联系方式")
        private String newMobile;

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
            return "ShuiNewUser{" +
                    "newUserName='" + newUserName + '\'' +
                    ", newUserCard='" + newUserCard + '\'' +
                    ", newMobile='" + newMobile + '\'' +
                    '}';
        }

        public static ShuiNewUser create(BdcQlrDO item) {
            ShuiNewUser shuiNewUser = new ShuiNewUser();
            shuiNewUser.setNewUserName(item.getQlrmc());
            shuiNewUser.setNewUserCard(item.getZjh());
            shuiNewUser.setNewMobile(item.getDh());
            return shuiNewUser;
        }

    }

    @ApiModel("文件信息")
    public static class ShuiFileInfo extends AbstactFileInfo {
        @Override
        public String toString() {
            return "ShuiFileInfo{" +
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

    public List<ShuiNewUser> getNewUserList() {
        return newUserList;
    }

    public void setNewUserList(List<ShuiNewUser> newUserList) {
        this.newUserList = newUserList;
    }

    public List<ShuiOriginalUser> getOriginalUserList() {
        return originalUserList;
    }

    public void setOriginalUserList(List<ShuiOriginalUser> originalUserList) {
        this.originalUserList = originalUserList;
    }

    public List<ShuiFileInfo> getData() {
        return data;
    }

    public void setData(List<ShuiFileInfo> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ShuiGhxxZjDTO{" +
                "consno='" + consno + '\'' +
                ", address='" + address + '\'' +
                ", newUserList=" + newUserList +
                ", originalUserList=" + originalUserList +
                ", data=" + data +
                '}';
    }
}
