package cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.request;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-18
 * @description 合肥燃气 申请过户 接口 请求参数
 */
public class HefeiRanqiSqghRequestDTO {

    // 十位燃气用户号
    private String fromUserhuhao;

    // 用户姓名
    private String fromUname;

    // 身份证号码
    private String fromUserSfid;

    // 手机号码
    private String fromUserMobile;

    // 用户姓名
    private String toUame;

    // 身份证号码
    private String toUserSfid;

    // 联系手机
    private String toUserMobile;

    // base64String格式, 新户主身份证照片
    private String toUserSfidimg;

    // base64String格式, 签订合同照片
    private String hetongimg;

    private String slbh;

    private String xmid;

    private String qjgldm;

    private String rqfwbsm;

    public String getFromUserhuhao() {
        return fromUserhuhao;
    }

    public void setFromUserhuhao(String fromUserhuhao) {
        this.fromUserhuhao = fromUserhuhao;
    }

    public String getFromUname() {
        return fromUname;
    }

    public void setFromUname(String fromUname) {
        this.fromUname = fromUname;
    }

    public String getFromUserSfid() {
        return fromUserSfid;
    }

    public void setFromUserSfid(String fromUserSfid) {
        this.fromUserSfid = fromUserSfid;
    }

    public String getFromUserMobile() {
        return fromUserMobile;
    }

    public void setFromUserMobile(String fromUserMobile) {
        this.fromUserMobile = fromUserMobile;
    }

    public String getToUame() {
        return toUame;
    }

    public void setToUame(String toUame) {
        this.toUame = toUame;
    }

    public String getToUserSfid() {
        return toUserSfid;
    }

    public void setToUserSfid(String toUserSfid) {
        this.toUserSfid = toUserSfid;
    }

    public String getToUserMobile() {
        return toUserMobile;
    }

    public void setToUserMobile(String toUserMobile) {
        this.toUserMobile = toUserMobile;
    }

    public String getToUserSfidimg() {
        return toUserSfidimg;
    }

    public void setToUserSfidimg(String toUserSfidimg) {
        this.toUserSfidimg = toUserSfidimg;
    }

    public String getHetongimg() {
        return hetongimg;
    }

    public void setHetongimg(String hetongimg) {
        this.hetongimg = hetongimg;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public String getRqfwbsm() {
        return rqfwbsm;
    }

    public void setRqfwbsm(String rqfwbsm) {
        this.rqfwbsm = rqfwbsm;
    }
}
