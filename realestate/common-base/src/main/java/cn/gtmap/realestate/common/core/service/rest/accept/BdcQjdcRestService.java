package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlQjdcsqDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 权籍调查restService
 * @date : 2021/8/5 13:40
 */
public interface BdcQjdcRestService {

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询权籍调查数据
     * @date : 2021/8/5 13:40
     */
    @GetMapping("/realestate-accept/rest/v1.0/qjdc/{gzlslid}")
    List<BdcSlQjdcsqDO> listSlQjdc(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id删除权籍调查数据
     * @date : 2021/8/5 13:41
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/qjdc/{gzlslid}")
    int deleteSlQjdc(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增权籍调查数据
     * @date : 2021/8/5 13:41
     */
    @PostMapping("/realestate-accept/rest/v1.0/qjdc")
    BdcSlQjdcsqDO saveSlQjdc(@RequestBody BdcSlQjdcsqDO bdcSlQjdcsqDO);
}
