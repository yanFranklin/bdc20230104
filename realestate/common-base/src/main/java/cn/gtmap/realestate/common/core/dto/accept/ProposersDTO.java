package cn.gtmap.realestate.common.core.dto.accept;

import java.io.Serializable;

/**
 * @program: realestate
 * @description: 限购信息查询返回人员信息DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-06-26 10:28
 **/
public class ProposersDTO implements Serializable{
    private static final long serialVersionUID = 436546883533764524L;

    private String Name;//姓名

    private  String CardNo;//证件号码

    private  String IsMain;//是否主购房人（是、否）

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String cardNo) {
        CardNo = cardNo;
    }

    public String getIsMain() {
        return IsMain;
    }

    public void setIsMain(String isMain) {
        IsMain = isMain;
    }

    @Override
    public String toString() {
        return "ProposersDTO{" +
                "Name='" + Name + '\'' +
                ", CardNo='" + CardNo + '\'' +
                ", IsMain='" + IsMain + '\'' +
                '}';
    }
}
