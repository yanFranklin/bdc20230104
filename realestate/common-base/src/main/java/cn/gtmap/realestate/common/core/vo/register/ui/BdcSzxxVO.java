package cn.gtmap.realestate.common.core.vo.register.ui;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/4/29
 * @description 更新完缮证信息后，将缮证信息返回给页面
 */
public class BdcSzxxVO {
    /**
     * 更新的数据量
     */
    int excuteNum;
    /**
     * 缮证人
     */
    String szr;

    /**
     * 缮证日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    Date szrq;

    public int getExcuteNum() {
        return excuteNum;
    }

    public void setExcuteNum(int excuteNum) {
        this.excuteNum = excuteNum;
    }

    public Date getSzrq() {
        return szrq;
    }

    public void setSzrq(Date szrq) {
        this.szrq = szrq;
    }

    public String getSzr() {
        return szr;
    }

    public void setSzr(String szr) {
        this.szr = szr;
    }

    @Override
    public String toString() {
        return "BdcSzxxVO{" +
                "excuteNum=" + excuteNum +
                ", szr='" + szr + '\'' +
                ", szrq='" + szrq + '\'' +
                '}';
    }

}
