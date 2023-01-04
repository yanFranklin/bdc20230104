package cn.gtmap.realestate.common.core.vo.inquiry;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/12/03/15:45
 * @Description:
 */
public class BdcdjbcxPdfVO {


    /**
     * 状态标识：1 成功  0 失败
     */
    private String code;

    /**
     * 状态详细信息
     */
    private String msg;


    /**
     * 对应PDF文件base64数据
     */
    private String data;
    public BdcdjbcxPdfVO() {

    }
    public BdcdjbcxPdfVO(String code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BdcdjbcxPdfVO{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
