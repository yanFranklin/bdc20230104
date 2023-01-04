package cn.gtmap.realestate.exchange.core.dto.wwsq.bjzt.response;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-20
 * @description 外网申请 获取办件状态响应结构
 */
public class WwsqBjztResponseDTO {

    private String slbh;

    private String bjzt;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getBjzt() {
        return bjzt;
    }

    public void setBjzt(String bjzt) {
        this.bjzt = bjzt;
    }
}
