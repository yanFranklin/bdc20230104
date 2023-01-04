package cn.gtmap.realestate.exchange.core.dto.changzhou.yjfw;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2021/3/31 16:13
 * @description
 */
public class SjclTsRootDTO {
    @JSONField(name = "ROOT")
    private SjclTsDTO ROOT;

    public SjclTsDTO getROOT() {
        return ROOT;
    }

    public void setROOT(SjclTsDTO ROOT) {
        this.ROOT = ROOT;
    }
}
