package cn.gtmap.realestate.building.core.bo;

import cn.gtmap.realestate.building.util.FwbmUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-21
 * @description 房屋编码业务实体
 */
public class FwbmBO {
    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FwbmBO.class);

    /**
     * 530627001105GB00003F00010001
     */
    private String bdcdyh;

    /**
     * G
     */
    private String syqlx;

    /**
     * B
     */
    private String zdtzm;


    /**
     * 3位自然幢号
     */
    private String zrzh;

    /**
     * 3位房序列号
     */
    private String fxlh;

    /**
     * 第一位 5
     */
    private String prefixOne;

    /**
     * 前12位  530627001105   GB00003F00010001
     */
    private String prefixTwe;

    /**
     * 中间5位 530627001105GB 00003 F00010001
     */
    private String middleFive;


    /**
     * GB to 01
     */
    private String syqlxCode;

    /**
     * 最后一位 校验码
     */
    private String zdtzmCode;

    /**
     * F列
     */
    private String fCode;

    /**
     * G列
     */
    private String gCode;

    /**
     * 校验码
     */
    private String jym;

    /**
     * FWBM
     */
    private String fwbm;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return
     * @description 构造函数
     */
    public FwbmBO(String bdcdyh){
        this.bdcdyh = bdcdyh;
        try{
            // 1.拆分
            FwbmUtils.split(this);

            // 2.替换特征码
            FwbmUtils.replaceTzm(this);

            // 3.获取 F 列
            FwbmUtils.setFCode(this);

            // 4. 获取校验码
            FwbmUtils.setJym(this);

            // 5. 获取房屋编码
            FwbmUtils.setFwbm(this);
        }catch (Exception e){
            LOGGER.error("房屋编码处理异常,bdcdyh:{}",bdcdyh,e);
        }
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getSyqlx() {
        return syqlx;
    }

    public void setSyqlx(String syqlx) {
        this.syqlx = syqlx;
    }

    public String getZdtzm() {
        return zdtzm;
    }

    public void setZdtzm(String zdtzm) {
        this.zdtzm = zdtzm;
    }

    public String getZrzh() {
        return zrzh;
    }

    public void setZrzh(String zrzh) {
        this.zrzh = zrzh;
    }

    public String getFxlh() {
        return fxlh;
    }

    public void setFxlh(String fxlh) {
        this.fxlh = fxlh;
    }

    public String getPrefixTwe() {
        return prefixTwe;
    }

    public void setPrefixTwe(String prefixTwe) {
        this.prefixTwe = prefixTwe;
    }

    public String getSyqlxCode() {
        return syqlxCode;
    }

    public void setSyqlxCode(String syqlxCode) {
        this.syqlxCode = syqlxCode;
    }

    public String getZdtzmCode() {
        return zdtzmCode;
    }

    public void setZdtzmCode(String zdtzmCode) {
        this.zdtzmCode = zdtzmCode;
    }

    public String getfCode() {
        return fCode;
    }

    public void setfCode(String fCode) {
        this.fCode = fCode;
    }

    public String getMiddleFive() {
        return middleFive;
    }

    public void setMiddleFive(String middleFive) {
        this.middleFive = middleFive;
    }

    public String getgCode() {
        return gCode;
    }

    public void setgCode(String gCode) {
        this.gCode = gCode;
    }

    public String getPrefixOne() {
        return prefixOne;
    }

    public void setPrefixOne(String prefixOne) {
        this.prefixOne = prefixOne;
    }

    public String getJym() {
        return jym;
    }

    public void setJym(String jym) {
        this.jym = jym;
    }

    public String getFwbm() {
        return fwbm;
    }

    public void setFwbm(String fwbm) {
        this.fwbm = fwbm;
    }
}
