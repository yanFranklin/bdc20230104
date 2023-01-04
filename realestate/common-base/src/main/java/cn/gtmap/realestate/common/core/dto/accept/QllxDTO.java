package cn.gtmap.realestate.common.core.dto.accept;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2019/2/25
 * @description
 */
public class QllxDTO {
    private String qlxzdm;
    private String qslxdm;
    private String dzwtzm;
    private String dyhcxlx;

    public String getQlxzdm() {
        return qlxzdm;
    }

    public void setQlxzdm(String qlxzdm) {
        this.qlxzdm = qlxzdm;
    }

    public String getQslxdm() {
        return qslxdm;
    }

    public void setQslxdm(String qslxdm) {
        this.qslxdm = qslxdm;
    }

    public String getDzwtzm() {
        return dzwtzm;
    }

    public void setDzwtzm(String dzwtzm) {
        this.dzwtzm = dzwtzm;
    }

    public String getDyhcxlx() {
        return dyhcxlx;
    }

    public void setDyhcxlx(String dyhcxlx) {
        this.dyhcxlx = dyhcxlx;
    }

    public QllxDTO() {
        super();
    }

    public QllxDTO(String qlxzdm, String qslxdm, String dzwtzm, String dyhcxlx) {
        this.qlxzdm = qlxzdm;
        this.qslxdm = qslxdm;
        this.dzwtzm = dzwtzm;
        this.dyhcxlx = dyhcxlx;
    }

    @Override
    public String toString() {
        return "QllxDTO{" +
                "qlxzdm='" + qlxzdm + '\'' +
                ", qslxdm='" + qslxdm + '\'' +
                ", dzwtzm='" + dzwtzm + '\'' +
                ", dyhcxlx='" + dyhcxlx + '\'' +
                '}';
    }
}
