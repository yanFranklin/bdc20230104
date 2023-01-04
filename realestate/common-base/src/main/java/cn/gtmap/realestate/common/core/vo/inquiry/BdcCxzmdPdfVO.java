package cn.gtmap.realestate.common.core.vo.inquiry;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description  常州生成查询证明单PDF接口返回结果
 */
public class BdcCxzmdPdfVO {
    /**
     * 状态标识：0 成功  1 失败
     */
    private String code;

    /**
     * 状态详细信息
     */
    private String msg;

    /**
     * 详细的证明PDF数据，成功时对应数据，失败时为空
     */
    private List<BdcCxzmdPdfDataVO> data;


    public BdcCxzmdPdfVO() {

    }

    public BdcCxzmdPdfVO(String code, String msg, List<BdcCxzmdPdfDataVO> data) {
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

    public List<BdcCxzmdPdfDataVO> getData() {
        return data;
    }

    public void setData(List<BdcCxzmdPdfDataVO> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BdcCxzmdPdfVO{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
