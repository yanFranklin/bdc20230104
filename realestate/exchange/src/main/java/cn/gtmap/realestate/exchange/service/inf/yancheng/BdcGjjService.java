package cn.gtmap.realestate.exchange.service.inf.yancheng;

import cn.gtmap.realestate.common.core.domain.CommonResponse;

/**
 * 盐城公积金接口相关处理服务
 */
public interface BdcGjjService {

    /**
     * 盐城_公积金双预告回调接口
     *
     * @param processInsId
     * @param actionType
     * @param reason
     * @return
     */
    CommonResponse sygdyGjjCallback(String processInsId, String actionType, String reason);

    /**
     * 盐城_公积金首次抵押回调接口
     *
     * @param processInsId
     * @param actionType
     * @param reason
     * @return
     */
    CommonResponse scdyGjjCallback(String processInsId, String actionType, String reason);

    /**
     * 盐城_公积金注销押回调接口
     *
     * @param processInsId
     * @return
     */
    CommonResponse zxdyGjjCallback(String processInsId,String actionType, String reason);

}
