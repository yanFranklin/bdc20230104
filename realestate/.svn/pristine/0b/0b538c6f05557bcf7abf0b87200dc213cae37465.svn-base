package cn.gtmap.realestate.accept.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/10/24
 * @description 云签电子材料配置
 */
@Component
@ConfigurationProperties(prefix = "yq")
public class YqDzclConfig {

    /**
     * 云签签署完成后的回调地址
     */
    private String hdtz;
    /**
     * 是否意愿认证： 0否 1是
     */
    private String sfyyrz;
    /**
     * 意愿认证方式：DXYZM（短信验证码）ZFBSL	(支付宝刷脸) TXYSL(腾讯云刷脸) EQBSL(E签宝刷脸) WXXCXSL(微信小程序刷脸) ZFBZNSPRZ(支付宝智能视频认证) WXZNSPRZ(微信智能视频认证)
     */
    private String yyrzfs;
    /**
     * 云签厂商代码
     */
    private String csdm;
    /**
     * Map<工作流定义名称, Map<权利人类别, 材料名称（多个","分割）>>
     *
     */
    private Map<String, Map<String, String>> dzcl;

    /**
     * Map<模板名称, MbInfo>
     * 例：<申请书, MbInfo<dylx:sqs, wjjmc: 电子合同>>
     */
    private Map<String, MbInfo> mbxx;

    public Map<String, Map<String, String>> getDzcl() {
        return dzcl;
    }

    public void setDzcl(Map<String, Map<String, String>> dzcl) {
        this.dzcl = dzcl;
    }

    public Map<String, MbInfo> getMbxx() {
        return mbxx;
    }

    public void setMbxx(Map<String, MbInfo> mbxx) {
        this.mbxx = mbxx;
    }

    public String getHdtz() {
        return hdtz;
    }

    public void setHdtz(String hdtz) {
        this.hdtz = hdtz;
    }

    public String getSfyyrz() {
        return sfyyrz;
    }

    public void setSfyyrz(String sfyyrz) {
        this.sfyyrz = sfyyrz;
    }

    public String getYyrzfs() {
        return yyrzfs;
    }

    public void setYyrzfs(String yyrzfs) {
        this.yyrzfs = yyrzfs;
    }

    public String getCsdm() {
        return csdm;
    }

    public void setCsdm(String csdm) {
        this.csdm = csdm;
    }

    @Override
    public String toString() {
        return "YqDzclConfig{" +
                "hdtz='" + hdtz + '\'' +
                ", sfyyrz='" + sfyyrz + '\'' +
                ", yyrzfs='" + yyrzfs + '\'' +
                ", csdm='" + csdm + '\'' +
                ", dzcl=" + dzcl +
                ", mbxx=" + mbxx +
                '}';
    }

    /**
     * 模板信息实体类
     */
    public static class MbInfo{
        /**
         * 打印类型
         */
        private String dylx;
        /**
         * 文件夹名称
         */
        private String wjjmc;

        public String getDylx() {
            return dylx;
        }

        public void setDylx(String dylx) {
            this.dylx = dylx;
        }

        public String getWjjmc() {
            return wjjmc;
        }

        public void setWjjmc(String wjjmc) {
            this.wjjmc = wjjmc;
        }

        @Override
        public String toString() {
            return "MbInfo{" +
                    "dylx='" + dylx + '\'' +
                    ", wjjmc='" + wjjmc + '\'' +
                    '}';
        }
    }
}
