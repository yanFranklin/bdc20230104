package cn.gtmap.realestate.common.core.dto.exchange.hefei.dzzz.response;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-05-06
 * @description 合肥电子证照请求制证接口响应结果参数
 */
public class HefeiDzzzResponseDTO {
    //响应编码
    private String flag;
    //响应结果
    private String result;

    private HefeiDzzzDataResponseDTO data;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public HefeiDzzzDataResponseDTO getData() {
        return data;
    }

    public void setData(HefeiDzzzDataResponseDTO data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HefeiDzzzResponseDTO{" +
                "flag='" + flag + '\'' +
                ", result='" + result + '\'' +
                ", data=" + data +
                '}';
    }
}
