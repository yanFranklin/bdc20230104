package cn.gtmap.realestate.common.core.dto.exchange.nantong.mkpjq;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/11/18
 * @description 摩科评价器人脸数据
 */
public class MkPjqDTO {

    /**
     * code : 1
     * data : {"address":"陕西省渭南市蒲城县桥陵镇六井村4组","authority":"蒲城县公安局","birthday":"1995年01月08日","cardNum":"610526199501082812","expirydate":"2019.06.26-2029.06.26","iDCardPhoto":"/9j/4AAQSkZJRgABAQAAAQABAAD/2wBD....","isMatch":true,"matchScore":91,"name":"张三","nation":"汉","serviceId":"8578465365","sex":"男","realPhoto":"/9j/4AAQSkZJRgABAQAAAQABAAD/2wBD","reason":"比对成功","bdbd":"人证比对表单Base64","status":"0"}
     * message : SUCCESS
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
