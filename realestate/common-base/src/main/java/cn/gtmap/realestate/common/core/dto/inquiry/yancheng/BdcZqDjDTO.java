package cn.gtmap.realestate.common.core.dto.inquiry.yancheng;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcZqDjDO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2021/01/15
 * @description  征迁冻结信息
 */
public class BdcZqDjDTO {
    /**
     * 不冻结单元（全部冻结时需要排除的）
     */
    private List<BdcZqDjDO> bdjdy;

    /**
     * 查询参数（自定义查询参数）
     */
    private Map<String, Object> cxcs;

    /**
     * 冻结信息
     */
    private BdcZqDjDO djxx;


    public BdcZqDjDO getDjxx() {
        return djxx;
    }

    public void setDjxx(BdcZqDjDO djxx) {
        this.djxx = djxx;
    }

    public List<BdcZqDjDO> getBdjdy() {
        return bdjdy;
    }

    public void setBdjdy(List<BdcZqDjDO> bdjdy) {
        this.bdjdy = bdjdy;
    }

    public Map<String, Object> getCxcs() {
        return cxcs;
    }

    public void setCxcs(Map<String, Object> cxcs) {
        this.cxcs = cxcs;
    }
}
