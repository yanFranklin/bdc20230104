package cn.gtmap.realestate.exchange.core.dto.xuancehng.sjcl;

/**
 * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @Date  2022/12/19
 * @description 提供给互联网附件
 */
public class FjclDTO {

    private String fileName;

    private String base64;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    @Override
    public String toString() {
        return "FjclDTO{" +
                "fileName='" + fileName + '\'' +
                ", base64='" + base64 + '\'' +
                '}';
    }
}
