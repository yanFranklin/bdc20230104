package cn.gtmap.realestate.exchange.core.dto.yctb;

import cn.gtmap.realestate.common.util.ParamUtil;

import java.io.Serializable;
import java.util.List;

public class YctbWwcjRequest implements Serializable {

    private static final long serialVersionUID = 8263946388490321905L;

    private String djlx; //string Y 字典见 登记类型
    private String qxdm; //string Y 区县代码
    private String ywh; //string Y 业务号
    private String usIdent; //string N 收件人员id，可为空
    private List<YctbWwcjQlrInfo> applicantList; //权利人
    private YctbWwcjSlInfo acceptanceList; //受理信息
    private List<YctbWwcjDyInfo> houseRightList; //单元信息
    private List<YctbWwcjSjdInfo> receiveList; //附件信息
    private List<YctbWwcjJmsbjtcyInfo> jmssbdlbList; //减免申报家庭成员（包含申请人）(涉税业务，必填)
    private List<YctbWwcjJmsbjtcyzvInfo> jmssbdchildlbList; //减免申报家庭成员子女（存在需减免家庭子女，此集合内字段必填）

    public List<YctbWwcjJmsbjtcyInfo> getJmssbdlbList() {
        return jmssbdlbList;
    }

    public void setJmssbdlbList(List<YctbWwcjJmsbjtcyInfo> jmssbdlbList) {
        this.jmssbdlbList = jmssbdlbList;
    }

    public List<YctbWwcjJmsbjtcyzvInfo> getJmssbdchildlbList() {
        return jmssbdchildlbList;
    }

    public void setJmssbdchildlbList(List<YctbWwcjJmsbjtcyzvInfo> jmssbdchildlbList) {
        this.jmssbdchildlbList = jmssbdchildlbList;
    }

    public List<YctbWwcjQlrInfo> getApplicantList() {
        return applicantList;
    }

    public void setApplicantList(List<YctbWwcjQlrInfo> applicantList) {
        this.applicantList = applicantList;
    }

    public YctbWwcjSlInfo getAcceptanceList() {
        return acceptanceList;
    }

    public void setAcceptanceList(YctbWwcjSlInfo acceptanceList) {
        this.acceptanceList = acceptanceList;
    }

    public List<YctbWwcjDyInfo> getHouseRightList() {
        return houseRightList;
    }

    public void setHouseRightList(List<YctbWwcjDyInfo> houseRightList) {
        this.houseRightList = houseRightList;
    }

    public List<YctbWwcjSjdInfo> getReceiveList() {
        return receiveList;
    }

    public void setReceiveList(List<YctbWwcjSjdInfo> receiveList) {
        this.receiveList = receiveList;
    }

    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getUsIdent() {
        return usIdent;
    }

    public void setUsIdent(String usIdent) {
        this.usIdent = usIdent;
    }

    public void checkParam(){
        ParamUtil.nonNull(this.djlx, "登记类型不能为空");
        ParamUtil.nonNull(this.qxdm,"区县代码不能为空");
        ParamUtil.nonNull(this.ywh," 业务号不能为空");
        ParamUtil.nonNull(this.acceptanceList," 受理信息");
        this.acceptanceList.checkParam();
    }

}
