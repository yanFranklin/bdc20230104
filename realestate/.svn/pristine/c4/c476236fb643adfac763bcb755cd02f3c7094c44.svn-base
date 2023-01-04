package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.dto.inquiry.GgptxxDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @version 1.0, 2022/11/03
 * @description 获取工改平台信息
 */
public interface BdcGgptRestService {


    @PostMapping(value = "/realestate-accept/rest/v1.0/ggpt/ggxx")
    List<GgptxxDTO> queryGgptxx(@RequestBody String param);

    /**
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 保存附件
     * @date : 2022/11/03
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/ggpt/download")
    Object downloadGgfj(@RequestParam("fjid") String fjid, @RequestParam("gzlslid") String gzlslid) throws IOException;

}
