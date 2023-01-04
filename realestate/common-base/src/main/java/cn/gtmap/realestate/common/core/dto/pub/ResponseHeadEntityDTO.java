package cn.gtmap.realestate.common.core.dto.pub;
/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2018/12/14
 * @description 响应头
 */

public class ResponseHeadEntityDTO {
    //响应代码
    private String responsecode;
    //响应信息
    private String responseinfo;
    //响应时间
    private String responsetime;

    public String getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }

    public String getResponseinfo() {
        return responseinfo;
    }

    public void setResponseinfo(String responseinfo) {
        this.responseinfo = responseinfo;
    }

    public String getResponsetime() {
        return responsetime;
    }

    public void setResponsetime(String responsetime) {
        this.responsetime = responsetime;
    }
}
