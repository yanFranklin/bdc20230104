package cn.gtmap.realestate.exchange.core.dto.bjjk.sfpjcx.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-28
 * @description 司法判决查询请求参数
 */
@XStreamAlias("datas")
public class SfpjcxRequestDTO {

    @XStreamImplicit
    private List<SfpjcxRequestData> data;

    public List<SfpjcxRequestData> getData() {
        return data;
    }

    public void setData(List<SfpjcxRequestData> data) {
        this.data = data;
    }
}
