package cn.gtmap.realestate.common.core.dto.exchange.nantong.mkpjq;

public class DataBean {

    /**
     * address : 陕西省渭南市蒲城县桥陵镇六井村4组
     * authority : 蒲城县公安局
     * birthday : 1995年01月08日
     * cardNum : 610526199501082812
     * expirydate : 2019.06.26-2029.06.26
     * iDCardPhoto : /9j/4AAQSkZJRgABAQAAAQABAAD/2wBD....
     * isMatch : true
     * matchScore : 91
     * name : 张三
     * nation : 汉
     * serviceId : 8578465365
     * sex : 男
     * realPhoto : /9j/4AAQSkZJRgABAQAAAQABAAD/2wBD
     * reason : 比对成功
     * bdbd : 人证比对表单Base64
     * status : 0
     */

    private String address;
    private String authority;
    private String birthday;
    private String cardNum;
    private String expirydate;
    private String iDCardPhoto;
    private boolean isMatch;
    private int matchScore;
    private String name;
    private String nation;
    private String serviceId;
    private String sex;
    private String realPhoto;
    private String reason;
    private String bdbd;
    private String status;
    /**
     * 身份证的正反面照片
     */
    private String zfmPhoto;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public String getIDCardPhoto() {
        return iDCardPhoto;
    }

    public void setIDCardPhoto(String iDCardPhoto) {
        this.iDCardPhoto = iDCardPhoto;
    }

    public boolean isIsMatch() {
        return isMatch;
    }

    public void setIsMatch(boolean isMatch) {
        this.isMatch = isMatch;
    }

    public int getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRealPhoto() {
        return realPhoto;
    }

    public void setRealPhoto(String realPhoto) {
        this.realPhoto = realPhoto;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getBdbd() {
        return bdbd;
    }

    public void setBdbd(String bdbd) {
        this.bdbd = bdbd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getiDCardPhoto() {
        return iDCardPhoto;
    }

    public void setiDCardPhoto(String iDCardPhoto) {
        this.iDCardPhoto = iDCardPhoto;
    }

    public boolean isMatch() {
        return isMatch;
    }

    public void setMatch(boolean match) {
        isMatch = match;
    }

    public String getZfmPhoto() {
        return zfmPhoto;
    }

    public void setZfmPhoto(String zfmPhoto) {
        this.zfmPhoto = zfmPhoto;
    }
}

