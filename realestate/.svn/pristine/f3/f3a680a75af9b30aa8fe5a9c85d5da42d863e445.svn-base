package cn.gtmap.realestate.common.core.service.rest.init.changzhou;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.init.BdcGgDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 公告restService
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-21 10:02
 **/
public interface BdcGgRestService {
    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/7/21 10:24
     */
    @GetMapping("/init/rest/v1.0/bdcgg/gzlslid/{gzlslid}")
    List<BdcGgDO> listBdcGg(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/7/21 10:24
     */
    @GetMapping("/init/rest/v1.0/bdcgggx/xmid/{xmid}")
    List<BdcGggxDO> listBdcGggxByXmid(@PathVariable(value = "xmid") String xmid);

    /**
     * @param ggid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据公告id查询公告信息
     * @date : 2022/3/3 8:58
     */
    @GetMapping("/init/rest/v1.0/bdcgg/{ggid}")
    BdcGgDO queryBdcGgByGgid(@PathVariable(value = "ggid") String ggid);

    /**
     * @param ywsjid
     * @author <a href="mailto:wutao2@gtmap.cn">wutao</a>
     * @description 根据ywsjid查询公告业务数据
     * @date : 2022/3/3 8:58
     */
    @GetMapping("/init/rest/v1.0/bdcggywsj/{ywsjid}")
    List<BdcGgywsjDO> queryBdcGgywsjByYwsjid(@PathVariable(value = "ywsjid") String ywsjid);

    /**
     * @param ggid
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 根据公告id查询公告业务数据
     * @date : 2022/3/3 8:58
     */
    @GetMapping("/init/rest/v1.0/bdcggywsjByGgid/{ggid}")
    List<BdcGgywsjDO> queryBdcGgywsjByGgid(@PathVariable(value = "ggid") String ggid);

    /**
     * @param ggid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据公告id查询公告关系信息
     * @date : 2022/3/3 8:58
     */
    @GetMapping("/init/rest/v1.0/bdcgggx/{ggid}")
    List<BdcGggxDO> queryBdcGggxByGgid(@PathVariable(value = "ggid") String ggid);

    /**
     * @param bdcGgDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产公告数据
     * @date : 2021/7/21 10:26
     */
    @PostMapping("/init/rest/v1.0/bdcgg")
    BdcGgDO inserBdcGg(@RequestBody BdcGgDO bdcGgDO);

    /**
     * @param xmidList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产公告数据
     * @date : 2021/7/21 10:26
     */
    @PostMapping("/init/rest/v1.0/bdcgggx")
    int insertBdcGggx(@RequestBody List<String> xmidList, @RequestParam(name = "ggid") String ggid);

    /**
     * @param  bdcGgDTO
     * @param  bdcGgDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产公告数据
     * @date : 2021/7/21 10:26
     */
    @PostMapping("/init/rest/v1.0/bdcggywsj")
    Object insertBdcGgYwsj(@RequestBody BdcGgDTO bdcGgDTO,@RequestParam(name = "sfgl") boolean sfgl);

    /**
     * @param ggids 公告Id
     * @param ggzt 公告状态
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 更新公告状态
     * @date : 2021/7/22 9:22
     */
    @PostMapping("/init/rest/v1.0/updateggzt")
    int batchUpdatebdcggzt(@RequestBody List<String> ggids, @RequestParam(name = "ggzt") String ggzt);

    /**
     * 根据公告ID查询公告关联的流程信息
     * @return 流程信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/init/rest/v1.0/bdcgggx/glxmxx/{ggid}")
    List<BdcXmDO> queryBdcGgGlBdcXmxx(@PathVariable(value = "ggid") String ggid);


    /**
     * 根据公告ID删除公告
     * @return 流程信息
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     */
    @DeleteMapping("/init/rest/v1.0/deleteggxx")
    int deleteBdcGgxx(@RequestBody List<String> ggids);
}
