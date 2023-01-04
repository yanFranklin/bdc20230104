package cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.ghcx.response;

import lombok.Data;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-18
 * @description 合肥 皖能燃气过户查询接口 响应参数
 */
@Data
public class HefeiWnRanqiGhcxResponseDTO {

    /**
     true,符合过户条件,false：不符合过户条件
     */
    private Boolean result;

    /**
     * 不能过户原因
     */
    private String resultText ;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 身份证号码
     */
    private String usersfid;

    /**
     * 代扣关系
     */
    private String daikou;

    /**
     * 地址
     */
    private String address;

    /**
     * 欠费， 正数
     */
    private String qianfei;

    /**
     * 账户余额
     */
    private String zhanghuyue;

    /**
     * 最后抄表时间
     */
    private String lastchaobiaoDate;

    /**
     * 最后表见数
     */
    private String lastBjs;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsersfid() {
        return usersfid;
    }

    public void setUsersfid(String usersfid) {
        this.usersfid = usersfid;
    }

    public String getDaikou() {
        return daikou;
    }

    public void setDaikou(String daikou) {
        this.daikou = daikou;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQianfei() {
        return qianfei;
    }

    public void setQianfei(String qianfei) {
        this.qianfei = qianfei;
    }

    public String getZhanghuyue() {
        return zhanghuyue;
    }

    public void setZhanghuyue(String zhanghuyue) {
        this.zhanghuyue = zhanghuyue;
    }

    public String getLastchaobiaoDate() {
        return lastchaobiaoDate;
    }

    public void setLastchaobiaoDate(String lastchaobiaoDate) {
        this.lastchaobiaoDate = lastchaobiaoDate;
    }

    public String getLastBjs() {
        return lastBjs;
    }

    public void setLastBjs(String lastBjs) {
        this.lastBjs = lastBjs;
    }
}
