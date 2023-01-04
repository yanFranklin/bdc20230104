package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcCjxxDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022/08/11
 * @description 不动产持件信息RestService
 */
public interface BdcCjxxRestService {

    /**
     * 保存持件信息
     * @param bdcCjxxDO 不动产持件信息DO
     */
    @PostMapping(value ="/realestate-inquiry/rest/v1.0/bdcCjxx/save")
    BdcCjxxDO saveBdcCjxx(@RequestBody BdcCjxxDO bdcCjxxDO);

    /**
     * 删除持件信息
     * @param ids 持件信息ID集合
     */
    @DeleteMapping(value="/realestate-inquiry/rest/v1.0/bdcCjxx")
    void plDeleteBdcCjxx(@RequestBody List<String> ids);
}
