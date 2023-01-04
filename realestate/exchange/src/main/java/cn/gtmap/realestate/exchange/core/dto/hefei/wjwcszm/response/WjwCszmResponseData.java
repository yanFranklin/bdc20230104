package cn.gtmap.realestate.exchange.core.dto.hefei.wjwcszm.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-10
 * @description
 */
@XStreamAlias("data")
public class WjwCszmResponseData {

    private String babyName;

    private String babySexCode;

    private String babySex;

    private String birthArea;

    private String birthTime;

    private String birthCode;

    private String momName;

    private String momIdCode;

    private String dadName;

    private String dadIdCode;

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public String getBabySexCode() {
        return babySexCode;
    }

    public void setBabySexCode(String babySexCode) {
        this.babySexCode = babySexCode;
    }

    public String getBabySex() {
        return babySex;
    }

    public void setBabySex(String babySex) {
        this.babySex = babySex;
    }

    public String getBirthArea() {
        return birthArea;
    }

    public void setBirthArea(String birthArea) {
        this.birthArea = birthArea;
    }

    public String getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(String birthTime) {
        this.birthTime = birthTime;
    }

    public String getBirthCode() {
        return birthCode;
    }

    public void setBirthCode(String birthCode) {
        this.birthCode = birthCode;
    }

    public String getMomName() {
        return momName;
    }

    public void setMomName(String momName) {
        this.momName = momName;
    }

    public String getMomIdCode() {
        return momIdCode;
    }

    public void setMomIdCode(String momIdCode) {
        this.momIdCode = momIdCode;
    }

    public String getDadName() {
        return dadName;
    }

    public void setDadName(String dadName) {
        this.dadName = dadName;
    }

    public String getDadIdCode() {
        return dadIdCode;
    }

    public void setDadIdCode(String dadIdCode) {
        this.dadIdCode = dadIdCode;
    }
}
