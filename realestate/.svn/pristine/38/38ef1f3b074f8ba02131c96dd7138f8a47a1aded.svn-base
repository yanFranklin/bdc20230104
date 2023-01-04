package cn.gtmap.realestate.inquiry.web.rest.bengbu;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcNwCjRzDTO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.bengbu.BdcNwCjRzRestService;
import cn.gtmap.realestate.inquiry.service.bengbu.BdcNwCjRzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href ="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2021/01/25
 * @description 蚌埠_互联网+业务内网创建日志查询
 */
@RestController
public class BdcNwCjRzRestController implements BdcNwCjRzRestService {

    @Autowired
    private BdcNwCjRzService bdcNwCjRzService;

    /**
     * 分页查询内网创建日志
     *
     * @param pageable
     * @param bdcNwCjRzQOStr
     * @return BdcNwCjRzDTO                                                             ;>
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @Override
    public Page<BdcNwCjRzDTO> listNwCjRz(Pageable pageable, @RequestParam(name = "bdcNwCjRzQOStr", required = false)String bdcNwCjRzQOStr) {
        return bdcNwCjRzService.listNwCjRz(pageable,bdcNwCjRzQOStr);
    }

    /**
     * 分页查询内网创建日志
     *
     * @return Integer                                                             ;>
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @Override
    public Integer countFailedRecord() {
        return bdcNwCjRzService.countFailedRecord();
    }
}
