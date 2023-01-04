package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.gasfhc.request;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2021/1/27
 * @description 公安身份核查查询
 */
public class GaSfhcRequestDTO {

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  查询业务参数
     */
    private List<GaSfhcCxywcsDTO> cxywcs;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  受理编号
     */
    private String slbh;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 工作流定义ID
     */
    private String gzldyid;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 工作流定义名称
     */
    private String gzldymc;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 工作流实例ID
     */
    private String gzlslid;

    public List<GaSfhcCxywcsDTO> getCxywcs() {
        return cxywcs;
    }

    public void setCxywcs(List<GaSfhcCxywcsDTO> cxywcs) {
        this.cxywcs = cxywcs;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getGzldymc() {
        return gzldymc;
    }

    public void setGzldymc(String gzldymc) {
        this.gzldymc = gzldymc;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }
}
