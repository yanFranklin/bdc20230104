package cn.gtmap.realestate.common.core.dto.building;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-24
 * @description
 */
public class ZdtResponseDTO {

    // base64位
    private String base64;

    // 是否要读取 tuxknr表
    private boolean readTuxknr;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public boolean isReadTuxknr() {
        return readTuxknr;
    }

    public void setReadTuxknr(boolean readTuxknr) {
        this.readTuxknr = readTuxknr;
    }
}
