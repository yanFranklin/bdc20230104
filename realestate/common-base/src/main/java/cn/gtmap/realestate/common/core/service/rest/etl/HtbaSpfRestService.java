package cn.gtmap.realestate.common.core.service.rest.etl;

import cn.gtmap.realestate.common.core.dto.etl.HtbaSpfWqxxDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: realestate
 * @description: 合同备案商品房rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-21 18:02
 **/
public interface HtbaSpfRestService {

    @GetMapping("/realestate-etl/rest/v1.0/htbaxx/getSpfWqhtxx")
    List<HtbaSpfWqxxDTO> getSpfWqhtxx(@RequestParam("gzlslid") String gzlslid);


}
