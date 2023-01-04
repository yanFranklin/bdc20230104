package cn.gtmap.realestate.common.core.service.rest.inquiry;


import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BdcYgRestService {

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 更加不动产单元号批量查询预告信息
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcYg/bdcdyhList/search")
    List<BdcYgDO> listBdcYgByBdcdyhs(@RequestBody List<String> bdcdyhList);

    /**
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @param djh 地籍号
     * @description 根据地籍号查询预告
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcYg/djh/search")
    List<BdcYgDO>  listBdcYgByDjh(@RequestParam(value = "djh") String djh);
}
