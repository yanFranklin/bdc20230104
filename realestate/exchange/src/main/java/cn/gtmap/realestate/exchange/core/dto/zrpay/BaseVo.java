package cn.gtmap.realestate.exchange.core.dto.zrpay;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
 * @version 1.0  2022/08/10
 * @description 政融支付平台 调用接口输入输出通用基础实体类
 */
public class BaseVo {

    /**
     * 传输模式  默认为1 AES加密
     */
    @JSONField(name = "Tprt_Mode")
    private String tprtMode = "1";
    /**
     * 传输模式为1时 此处为加密后的密钥
     */
    @JSONField(name = "Tprt_Parm")
    private String tprtParm;

    /**
     * 发起方系统编号  即由政融分配的多实体
     */
    @JSONField(name = "IttParty_Sys_Id")
    private String ittPartySysId;

    /**
     * 业务数据	加密后的业务数据
     */
    @JSONField(name = "Bsn_Data")
    private String BsnData;

    public String getTprtMode() {
        return tprtMode;
    }

    public void setTprtMode(String tprtMode) {
        this.tprtMode = tprtMode;
    }

    public String getTprtParm() {
        return tprtParm;
    }

    public void setTprtParm(String tprtParm) {
        this.tprtParm = tprtParm;
    }

    public String getIttPartySysId() {
        return ittPartySysId;
    }

    public void setIttPartySysId(String ittPartySysId) {
        this.ittPartySysId = ittPartySysId;
    }

    public String getBsnData() {
        return BsnData;
    }

    public void setBsnData(String bsnData) {
        BsnData = bsnData;
    }

    @Override
    public String toString() {
        return "BaseInVo{" +
                "tprtMode='" + tprtMode + '\'' +
                ", tprtParm='" + tprtParm + '\'' +
                ", ittPartySysId='" + ittPartySysId + '\'' +
                ", BsnData='" + BsnData + '\'' +
                '}';
    }
}
