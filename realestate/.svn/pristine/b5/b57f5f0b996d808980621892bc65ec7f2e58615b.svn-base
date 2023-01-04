package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wangyinghao
 * @version 1.0  2020-12-14
 * @description 一体化信息推送服务
 */
public interface YthxxTsRestService {

    /**
     * 查封业务办结推送一体化信息平台
     *
     * @param processInsId
     * @param currentUserName
     */
    @PostMapping("/realestate-exchange/rest/v1.0/ythxxts/cf")
    void ythcfxxts(@RequestParam(name = "processInsId") String processInsId,
                   @RequestParam(value = "currentUserName", required = false) String currentUserName);

    /**
     * 解封业务办结推送一体化信息平台
     *
     * @param processInsId
     * @param currentUserName
     */
    @PostMapping("/realestate-exchange/rest/v1.0/ythxxts/jf")
    void ythjfxxts(@RequestParam(name = "processInsId") String processInsId,
                   @RequestParam(value = "currentUserName", required = false) String currentUserName);

    /**
     * 手动推送信息
     *
     * @param processInsId
     * @param currentUserName
     */
    @PostMapping("/realestate-exchange/rest/v1.0/ythxxts/hand")
    CommonResponse ythxxsdts(@RequestParam(name = "processInsId") String processInsId);
}
