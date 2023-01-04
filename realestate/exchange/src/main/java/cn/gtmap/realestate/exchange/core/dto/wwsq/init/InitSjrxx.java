package cn.gtmap.realestate.exchange.core.dto.wwsq.init;

import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/5/29.
 * @description 收件人信息
 */
@IgnoreCast(ignoreNum = 5)
public class InitSjrxx {
    // 领证方式代码
    private String lzfsdm;
    // 领证方式名称
    private String lzfsmc;
    // 收件人联系电话
    private String sjrlxdh;
    // 收件人名称
    private String sjrmc;
    // 收件人所在市
    private String sjrszshi;
    // 收件人所在省
    private String sjrszsheng;
    // 收件人所在县
    private String sjrszx;
    // 收件人详细地址
    private String sjrxxdz;

    private String wlid;

    @ApiModelProperty(value = "电子邮箱")
    private String dzyx;

    @ApiModelProperty(value = "领证人名称")
    private String lzrmc;

    @ApiModelProperty(value = "领证人代码")
    private String lzrzjzldm;

    @ApiModelProperty(value = "领证人代码")
    private String lzrzjzl;

    @ApiModelProperty(value = "领证人证件种类名称")
    private String lzrzjzlmc;

    @ApiModelProperty(value = "领证人证件号")
    private String lzrzjh;

    @ApiModelProperty(value = "领证人联系电话")
    private String lzrlxdh;

    @ApiModelProperty(value = "收件人证件号")
    private String sjrzjh;


    public String getLzrzjzl() {
        return lzrzjzl;
    }

    public void setLzrzjzl(String lzrzjzl) {
        this.lzrzjzl = lzrzjzl;
    }

    public String getLzrmc() {
        return lzrmc;
    }

    public void setLzrmc(String lzrmc) {
        this.lzrmc = lzrmc;
    }

    public String getLzrzjzldm() {
        return lzrzjzldm;
    }

    public void setLzrzjzldm(String lzrzjzldm) {
        this.lzrzjzldm = lzrzjzldm;
    }

    public String getLzrzjzlmc() {
        return lzrzjzlmc;
    }

    public void setLzrzjzlmc(String lzrzjzlmc) {
        this.lzrzjzlmc = lzrzjzlmc;
    }

    public String getLzrzjh() {
        return lzrzjh;
    }

    public void setLzrzjh(String lzrzjh) {
        this.lzrzjh = lzrzjh;
    }

    public String getLzrlxdh() {
        return lzrlxdh;
    }

    public void setLzrlxdh(String lzrlxdh) {
        this.lzrlxdh = lzrlxdh;
    }

    public String getDzyx() {
        return dzyx;
    }

    public void setDzyx(String dzyx) {
        this.dzyx = dzyx;
    }

    public String getLzfsdm() {
        return lzfsdm;
    }

    public void setLzfsdm(String lzfsdm) {
        this.lzfsdm = lzfsdm;
    }

    public String getLzfsmc() {
        return lzfsmc;
    }

    public void setLzfsmc(String lzfsmc) {
        this.lzfsmc = lzfsmc;
    }

    public String getSjrlxdh() {
        return sjrlxdh;
    }

    public void setSjrlxdh(String sjrlxdh) {
        this.sjrlxdh = sjrlxdh;
    }

    public String getSjrmc() {
        return sjrmc;
    }

    public void setSjrmc(String sjrmc) {
        this.sjrmc = sjrmc;
    }

    public String getSjrszshi() {
        return sjrszshi;
    }

    public void setSjrszshi(String sjrszshi) {
        this.sjrszshi = sjrszshi;
    }

    public String getSjrszsheng() {
        return sjrszsheng;
    }

    public void setSjrszsheng(String sjrszsheng) {
        this.sjrszsheng = sjrszsheng;
    }

    public String getSjrszx() {
        return sjrszx;
    }

    public void setSjrszx(String sjrszx) {
        this.sjrszx = sjrszx;
    }

    public String getSjrxxdz() {
        return sjrxxdz;
    }

    public void setSjrxxdz(String sjrxxdz) {
        this.sjrxxdz = sjrxxdz;
    }

    public String getWlid() {
        return wlid;
    }

    public void setWlid(String wlid) {
        this.wlid = wlid;
    }

    public String getSjrzjh() {
        return sjrzjh;
    }

    public void setSjrzjh(String sjrzjh) {
        this.sjrzjh = sjrzjh;
    }
}
