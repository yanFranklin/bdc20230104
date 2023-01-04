package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.register.BdcZxDTO;
import cn.gtmap.realestate.common.core.service.rest.register.BdcBdcZxRestService;
import cn.gtmap.realestate.register.core.mapper.BdcBdcZxQdMapper;
import cn.gtmap.realestate.register.web.main.BaseController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href ="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version
 * @description 注销清单服务
 */
@RestController
@Api(tags = "注销清单")
public class BdcZxQdRestController extends BaseController implements BdcBdcZxRestService {

    @Autowired
    private BdcBdcZxQdMapper bdcBdcZxQdMapper;

    /**
     * 分页查询注销清单
     * @param page
     * @param size
     * @param sort
     * @param gzlslid
     * @return
     */
    @Override
    public Page<BdcZxDTO> listZxQdByPage(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort,  @PathVariable(name = "gzlslid") String gzlslid) {
        Pageable pageable = new PageRequest(page, size, sort);
        Map<String,String> param = new ConcurrentHashMap<>(16);
        param.put("gzlslid",gzlslid);
        return repo.selectPaging("listZxQdByPage", param, pageable);
    }

    /**
     * 查询所有注销清单
     * @param gzlslid
     * @return
     */
    @Override
    public List<BdcZxDTO> listZxQdAll(@PathVariable(name = "gzlslid") String gzlslid) {
         return bdcBdcZxQdMapper.listZxQdByPage(gzlslid);
    }


}
