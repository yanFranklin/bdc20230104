package cn.gtmap.realestate.exchange.service.inf.yancheng;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import org.springframework.web.bind.annotation.RequestParam;

public interface YthxxTsService {
    /**
     * 查封业务办结推送一体化信息平台
     *
     * @param processInsId
     * @param currentUserName
     */
    public CommonResponse ythcfxxts(String processInsId, String currentUserName);

    /**
     * 解封业务办结推送一体化信息平台
     *
     * @param processInsId
     * @param currentUserName
     */
    public CommonResponse ythjfxxts(String processInsId, String currentUserName);


    /**
     * 手动推送
     *
     * @param processInsId
     */
    public CommonResponse ythxxsdts(String processInsId);
}
