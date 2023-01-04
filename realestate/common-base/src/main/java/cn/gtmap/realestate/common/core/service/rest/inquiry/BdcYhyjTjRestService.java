package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcYhyjTjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcYhyjTjQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/7/28.
 * @description 银行月结查询
 */
public interface BdcYhyjTjRestService {

    /**
     * 银行月结统计数据集合
     * @param bdcYhyjTjQO  不动产银行月结统计QO
     * @return 银行月结统计数据集合
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcyhyjtj/list")
    List<BdcYhyjTjDTO> listBdcYhyjTj(@RequestBody BdcYhyjTjQO bdcYhyjTjQO);
}
