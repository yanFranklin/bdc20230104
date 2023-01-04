package cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.response;

import cn.gtmap.realestate.exchange.core.dto.zzcxj.BaseResponseDTO;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-10-24
 * @description
 */
public class ZfcxResponseDTO extends BaseResponseDTO{
    private ZfcxResponseData data;
    /**
     * @param
     * @return
     * @author <a href ="mailto:liangqing@gtmap.cn"></a>
     * version 1.0,2017-11-16
     * discription 构造函数
     */

    public ZfcxResponseDTO() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.qqsj = sdf.format(new Date());
    }

    public ZfcxResponseData getData() {
        return data;
    }

    public void setData(ZfcxResponseData data) {
        this.data = data;
    }
}
