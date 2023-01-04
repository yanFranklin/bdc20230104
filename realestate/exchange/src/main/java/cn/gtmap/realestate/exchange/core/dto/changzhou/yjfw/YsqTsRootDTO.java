package cn.gtmap.realestate.exchange.core.dto.changzhou.yjfw;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2021/3/31 16:23
 * @description
 */
public class YsqTsRootDTO {
    @JSONField(name = "ROOT")
    private YsqTsDTO ROOT;

    public YsqTsDTO getROOT() {
        return ROOT;
    }

    public void setROOT(YsqTsDTO ROOT) {
        this.ROOT = ROOT;
    }
}
