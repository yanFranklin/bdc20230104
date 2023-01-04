package cn.gtmap.realestate.exchange.core.dto.wwsq.getDzzzzip.response;

import java.util.Arrays;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0, 2022/10/18
 * @description 获取电子证照压缩包响应实体
 */
public class BdcDzzzzipResponseDTO {
    /**
     * 电子证照压缩包
     */
    private byte[] dzzzzip;

    public byte[] getDzzzzip() {
        return dzzzzip;
    }

    public void setDzzzzip(byte[] dzzzzip) {
        this.dzzzzip = dzzzzip;
    }

    @Override
    public String toString() {
        return "BdcDzzzzipResponseDTO{" +
                "dzzzzip=" + Arrays.toString(dzzzzip) +
                '}';
    }
}
