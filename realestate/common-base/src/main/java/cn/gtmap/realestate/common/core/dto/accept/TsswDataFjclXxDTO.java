package cn.gtmap.realestate.common.core.dto.accept;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0  2020-10-30
 * @description 推送税务附件材料文件夹下子文件信息
 */
public class TsswDataFjclXxDTO {
    // 附件名称
    private String fjmc;

    // 文件base64码
    private String base64;

    public String getFjmc() {
        return fjmc;
    }

    public void setFjmc(String fjmc) {
        this.fjmc = fjmc;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    @Override
    public String toString() {
        return "TsswDataFjclXxDTO [fjmc=" + fjmc + ", base64=" + base64 + "]";
    }
}
