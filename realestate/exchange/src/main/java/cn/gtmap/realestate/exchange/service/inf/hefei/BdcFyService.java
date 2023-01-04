package cn.gtmap.realestate.exchange.service.inf.hefei;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.exchange.core.dto.hefei.fw.FycdRequest;
import cn.gtmap.realestate.exchange.core.qo.BdcFjxxQO;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 2022/10/18
 * @description (合肥) 法院对接接口
 */
public interface BdcFyService {

    /**
     * 生成查解封回执单PDF
     *
     * @param gzlslid
     */
    void scCjfhzdPdf(String gzlslid);

    /**
     * 查询附件信息
     *
     * @param bdcFjxxQO
     */
    BdcCommonResponse queryFjxx(BdcFjxxQO bdcFjxxQO);

    /**
     * 生成查档PDF
     *
     * @param fycdRequest
     */
    BdcCommonResponse scCdPdf(FycdRequest fycdRequest);
}
