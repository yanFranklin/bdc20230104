package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcYhyjTjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcYhyjTjQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/7/28.
 * @description 银行月结查询统计
 */
public interface BdcYhyjTjService {

    /**
     * 查询银行月结统计数据
     * @param bdcYhyjTjQO  银行月结统计QO
     * @return 银行月结数据集合
     */
    List<BdcYhyjTjDTO> listBdcYhyjTj(BdcYhyjTjQO bdcYhyjTjQO);

}
