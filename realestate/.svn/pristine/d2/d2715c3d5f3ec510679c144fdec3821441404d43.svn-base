package cn.gtmap.realestate.common.core.service.rest.etl;

import cn.gtmap.realestate.common.core.dto.etl.HtbaxxDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @program: realestate
 * @description: 外网推送合同备案信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-04-13 15:45
 **/
public interface WwtsHtbaRestService {


    /**
     * @param htbaxxDTOList 推送数据
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 外网推送合同备案信息接口
     * @date : 2021/4/13 15:46
     */
    @PostMapping("/realestate-etl/rest/v1.0/htbaxx/wwts")
    Object wwtsHtbaxx(@RequestBody HtbaxxDTO htbaxxDTO);

    /**
     * @param htbaxxDTOList 撤销数据
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 外网撤销备案信息
     * @date : 2021/4/13 16:12
     */
    @PostMapping("/realestate-etl/rest/v1.0/htbaxx/wwcx")
    Object wwcxHtbaxx(@RequestBody HtbaxxDTO htbaxxDTO);
}
