package cn.gtmap.realestate.common.core.dto.exchange.xuancheng.hhxx;

import java.util.Date;
import java.util.List;

public class HhxxResponseBody {

    /**
     * {
     *     "resultCode": "00",
     *     "resultInfo": "处理成功",
     *     "resultTotal": 0,
     *     "resultData": [
     *         {
     *             "SZXM": "xxx",
     *             "SZZJH": "xxx",
     *             "SZXB": "xxx",
     *             "SZHJDZ": "xxx",
     *             "SWRQ": "xxx",
     *             "HHKSSJ": "xxx"
     *         },
     *         {
     *             "SZXM": "yyy",
     *             "SZZJH": "yyy",
     *             "SZXB": "yyy",
     *             "SZHJDZ": "yyy",
     *             "SWRQ": "yyy",
     *             "HHKSSJ": "yyy"
     *         }
     *     ]
     * }
     */
    private String resultCode;

    private String resultInfo;

    private Integer resultTotal;

    private List<HhxxResponseData> resultData;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public Integer getResultTotal() {
        return resultTotal;
    }

    public void setResultTotal(Integer resultTotal) {
        this.resultTotal = resultTotal;
    }

    public List<HhxxResponseData> getResultData() {
        return resultData;
    }

    public void setResultData(List<HhxxResponseData> resultData) {
        this.resultData = resultData;
    }


}
