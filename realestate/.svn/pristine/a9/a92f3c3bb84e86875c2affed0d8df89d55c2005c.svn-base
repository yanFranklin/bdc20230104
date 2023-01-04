package cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.shui;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel("南通水过户dto")
public class ShuiGhxxDTO implements Serializable {
    private static final long serialVersionUID = -1577361393211466301L;

    @ApiModelProperty(value = "户号")
    protected String consno;

    @ApiModelProperty(value = "地址")
    protected String address;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "新用户名")
    private String newusername;


    @ApiModelProperty(value = "新用户证件号")
    private String newusercard;

    @ApiModelProperty(value = "新用户证件类型")
    private String newzjzl;

    @ApiModelProperty(value = "新用户联系手机")
    private String newmobile;

    @ApiModelProperty(value = "新用户联系电话")
    private String newtelephon;

    @ApiModelProperty(value = "附件信息")
    private List<ShuiFjxxDto> fjxx;

    @ApiModel("南通文件dto")
    public static class ShuiFjnrDto implements Serializable {
        private static final long serialVersionUID = -1795156047240083011L;
        @ApiModelProperty(value = "附件名称")
        private String fjmc;
        @ApiModelProperty(value = "附件内容（base64）")
        private String fjnr;

        public String getFjmc() {
            return fjmc;
        }

        public void setFjmc(String fjmc) {
            this.fjmc = fjmc;
        }

        public String getFjnr() {
            return fjnr;
        }

        public void setFjnr(String fjnr) {
            this.fjnr = fjnr;
        }

        @Override
        public String toString() {
            return "ShuiFjnrDto{" +
                    "fjmc='" + fjmc + '\'' +
                    ", fjnr='" + fjnr + '\'' +
                    '}';
        }
    }


    @ApiModel("南通材料dto")
    public static class ShuiFjxxDto implements Serializable {
        private static final long serialVersionUID = 6501817661573971813L;
        @ApiModelProperty(value = "材料名称")
        private String clmc;
        @ApiModelProperty(value = "材料内容")
        private List<ShuiFjnrDto> clnr;

        public String getClmc() {
            return clmc;
        }

        public void setClmc(String clmc) {
            this.clmc = clmc;
        }

        public List<ShuiFjnrDto> getClnr() {
            return clnr;
        }

        public void setClnr(List<ShuiFjnrDto> clnr) {
            this.clnr = clnr;
        }

        @Override
        public String toString() {
            return "ShuiFjxxDto{" +
                    "clmc='" + clmc + '\'' +
                    ", clnr=" + clnr +
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

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getNewusername() {
        return newusername;
    }

    public void setNewusername(String newusername) {
        this.newusername = newusername;
    }

    public String getNewusercard() {
        return newusercard;
    }

    public void setNewusercard(String newusercard) {
        this.newusercard = newusercard;
    }

    public String getNewzjzl() {
        return newzjzl;
    }

    public void setNewzjzl(String newzjzl) {
        this.newzjzl = newzjzl;
    }

    public String getNewmobile() {
        return newmobile;
    }

    public void setNewmobile(String newmobile) {
        this.newmobile = newmobile;
    }

    public String getNewtelephon() {
        return newtelephon;
    }

    public void setNewtelephon(String newtelephon) {
        this.newtelephon = newtelephon;
    }

    public List<ShuiFjxxDto> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<ShuiFjxxDto> fjxx) {
        this.fjxx = fjxx;
    }

    @Override
    public String toString() {
        return "ShuiGhxxDTO{" +
                "consno='" + consno + '\'' +
                ", address='" + address + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", newusername='" + newusername + '\'' +
                ", newusercard='" + newusercard + '\'' +
                ", newzjzl='" + newzjzl + '\'' +
                ", newmobile='" + newmobile + '\'' +
                ", newtelephon='" + newtelephon + '\'' +
                ", fjxx=" + fjxx +
                '}';
    }
}
