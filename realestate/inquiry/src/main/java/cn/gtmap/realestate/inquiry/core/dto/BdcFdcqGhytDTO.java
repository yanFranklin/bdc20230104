package cn.gtmap.realestate.inquiry.core.dto;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/04/19
 * @description 房地产权项目规划用途信息
 */
public class BdcFdcqGhytDTO {
    private String xmid;

    private String ghyt;


    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    @Override
    public String toString() {
        return "BdcFdcqGhytDTO{" +
                "xmid='" + xmid + '\'' +
                ", ghyt='" + ghyt + '\'' +
                '}';
    }
}
