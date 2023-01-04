package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.register.BdcZxDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/6/7
 * @description 不动产单元相关服务FeignClient调用定义
 */
public interface BdcBdcZxRestService {

    @RequestMapping(value = "/realestate-register/rest/v1.0/zxqd/listZxQdByPage/search/{gzlslid}", method = RequestMethod.POST)
    Page<BdcZxDTO> listZxQdByPage(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort, @PathVariable(name = "gzlslid") String gzlslid);

    @RequestMapping(value = "/realestate-register/rest/v1.0/zxqd/listZxQdAll/search/{gzlslid}", method = RequestMethod.POST)
    List<BdcZxDTO> listZxQdAll(@PathVariable(name = "gzlslid") String gzlslid);


}
