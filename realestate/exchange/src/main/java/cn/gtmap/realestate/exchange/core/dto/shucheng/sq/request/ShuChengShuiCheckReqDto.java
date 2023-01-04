package cn.gtmap.realestate.exchange.core.dto.shucheng.sq.request;

import java.io.Serializable;

/**
 * @author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @date 2022/5/9
 * @description
 */
public class ShuChengShuiCheckReqDto implements Serializable {

    private static final long serialVersionUID = -2042460758953050858L;

    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "ShuChengShuiCheckReqDto{" +
                "userID='" + userID + '\'' +
                '}';
    }
}
