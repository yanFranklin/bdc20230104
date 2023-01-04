package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.BdcXtJgDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/11/27
 * @description
 */
public interface BdcXtJgpzRestService {

    /**
     * 保存机构配置
     *
     * @param bdcXtJgDO
     * @return
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/jgpz")
    BdcXtJgDO saveJgpz(@RequestBody BdcXtJgDO bdcXtJgDO);

    /**
     * 获取机构配置
     *
     * @param jgid
     * @return
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/jgpz/query")
    BdcXtJgDO queryJgpz(@RequestParam(name = "jgid") String jgid);

    /**
     * 删除机构配置
     *
     * @param jgidList
     * @return
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    @DeleteMapping(value = "/realestate-inquiry/rest/v1.0/jgpz/delete")
    void deleteJgpz(@RequestParam(name = "jgidList") List<String> jgidList);




    /**
     * @param bdcXtJgDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量保存系统机关数据
     * @date : 2020/9/9 10:37
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/jgpz/pl")
    int saveJgpzPl(@RequestBody List<BdcXtJgDO> bdcXtJgDOList);
}
