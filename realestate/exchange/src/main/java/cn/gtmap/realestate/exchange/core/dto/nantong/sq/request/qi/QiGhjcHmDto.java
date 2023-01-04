package cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.qi;

/**
 * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description //南通大市，海门气检查请求实体
 * @Date 2022/5/26 9:32
 **/
public class QiGhjcHmDto {
    private String mdmCode;
    private String userCode;

    public String getMdmCode() {
        return mdmCode;
    }

    public void setMdmCode(String mdmCode) {
        this.mdmCode = mdmCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Override
    public String toString() {
        return "QiGhjcHmDto{" +
                "mdmCode='" + mdmCode + '\'' +
                ", userCode='" + userCode + '\'' +
                '}';
    }
}
