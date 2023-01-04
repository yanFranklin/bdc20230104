package cn.gtmap.realestate.exchange.service.inf;

import java.util.List;

import cn.gtmap.realestate.exchange.core.bo.grdacx.GrdacxModel;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getYgxx.request.GetYgxxRequestData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.request.GrdacxRequestBody;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-02
 * @description 标准版 个人档案查询 服务
 */
public interface GrdacxService {


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param grdacxRequestBody
     * @return cn.gtmap.realestate.exchange.core.bo.grdacx.GrdacxModel
     * @description qlrmc qlrzjh
     */
    GrdacxModel grdacx(GrdacxRequestBody grdacxRequestBody);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestBodylist
     * @return java.util.List<cn.gtmap.realestate.exchange.core.bo.grdacx.GrdacxModel>
     * @description 集合查询
     */
    GrdacxModel grdacxBatch(List<GrdacxRequestBody> requestBodylist);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param ygxxRequestDatas 查询参数 
     * @description 预告查询
     */
    GrdacxModel getYgxx(List<GetYgxxRequestData> ygxxRequestDatas);


    /**
     *
     * @param grdacxRequestBody
     * @return
     */
    public GrdacxModel grdacxNt(GrdacxRequestBody grdacxRequestBody);

}
