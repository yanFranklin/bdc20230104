package cn.gtmap.realestate.common.core.service.rest.inquiry.changzhou;

import cn.gtmap.realestate.common.core.domain.BdcGgDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version 2021/7/15
 * @description
 */
public interface BdcGgxxRestService {
    /**
     * @param processInsId
     * @return
     * @author <a href ="mailto:sunyuzhaung@gtmap.cn">sunyuzhuang</a>
     * @description 推送公告信息到公告管理管理台账
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcgg/pushBdcGgxxToGgtz")
    int pushBdcGgxxToGgtz(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @param bdcGgDO
     * @return
     * @author <a href ="mailto:sunyuzhaung@gtmap.cn">sunyuzhuang</a>
     * @description 推送公告信息到公告管理管理台账
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdcgg/saveBdcGgxx")
    int updateBdcGgxx(@RequestBody BdcGgDO bdcGgDO);

    /**
     * @param id 不动产公告ID
     * @return
     * @author <a href ="mailto:sunyuzhaung@gtmap.cn">sunyuzhuang</a>
     * @description 获取不动产公告详情
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/ggxx")
    BdcGgDO queryBdcGgxx(@RequestParam(name = "id") String id);
}
