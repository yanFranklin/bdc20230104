package cn.gtmap.realestate.inquiry.service.bengbu;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcNwCjRzDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author <a href ="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2021/01/25
 * @description 蚌埠_互联网+业务内网创建日志查询
 */
public interface BdcNwCjRzService {

    /**
     * 分页查询内网创建日志
     *
     * @param pageable
     * @param bdcNwCjRzQOStr
     * @return BdcNwCjRzDTO
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    Page<BdcNwCjRzDTO> listNwCjRz(Pageable pageable, String bdcNwCjRzQOStr);

    /**
     * 统计失败的记录
     *
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    int countFailedRecord();
}
