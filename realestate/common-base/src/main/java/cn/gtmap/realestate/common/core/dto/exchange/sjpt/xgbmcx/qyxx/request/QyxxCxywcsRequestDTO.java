package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.qyxx.request;
/**
 * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
 * @version 1.0  2019-08-27
 * @description 企业基本信息查询 业务参数实体
 */
public class QyxxCxywcsRequestDTO {
    private String entname;

    private String uniscid;

    public String getEntname() {
        return entname;
    }

    public void setEntname(String entname) {
        this.entname = entname;
    }

    public String getUniscid() {
        return uniscid;
    }

    public void setUniscid(String uniscid) {
        this.uniscid = uniscid;
    }
}
