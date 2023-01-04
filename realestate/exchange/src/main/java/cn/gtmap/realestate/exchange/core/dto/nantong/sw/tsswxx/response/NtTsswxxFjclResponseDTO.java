package cn.gtmap.realestate.exchange.core.dto.nantong.sw.tsswxx.response;

import java.util.List;

import cn.gtmap.realestate.common.core.dto.accept.TsswDataFjclXxDTO;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-10-16
 * @description 南通推送存量房税务信息附件材料实体
 */
public class NtTsswxxFjclResponseDTO {
    // 材料名称（税务定义的字段）
    private String zlmc;

    // 附件base64内容
    private String base64;
    
    private List<TsswDataFjclXxDTO> fjxx;


    public List<TsswDataFjclXxDTO> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<TsswDataFjclXxDTO> fjxx) {
        this.fjxx = fjxx;
    }

    public String getZlmc() {
        return zlmc;
    }

    public void setZlmc(String zlmc) {
        this.zlmc = zlmc;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}
