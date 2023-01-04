package cn.gtmap.realestate.common.core.service.rest.config;


import cn.gtmap.realestate.common.core.domain.BdcGgDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtGgDO;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcXtGgVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2021/4/19
 * @description 不动产公告配置服务
 */
public interface BdcXtGgRestService {

    /**
     * @param pageable 分页信息
     * @param bdcXtGgJson 公告查询参数
     * @return 公告配置分页数据
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取公告配置分页数据
     */
    @GetMapping("/realestate-config/rest/v1.0/xtgg/page")
    Page<BdcXtGgVO> listBdcXtGgByPage(Pageable pageable, @RequestParam(name = "bdcXtGgJson", required = false) String bdcXtGgJson);

    /**
     * @param bdcXtGgDO 公告配置信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存更新公告配置信息
     */
    @PutMapping("/realestate-config/rest/v1.0/xtgg")
    int saveOrUpdateBdcXtGg(@RequestBody BdcXtGgDO bdcXtGgDO);

    /**
     * @param xtggidList 需要删除的公告配置集合
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除公告配置信息
     */
    @DeleteMapping("/realestate-config/rest/v1.0/xtgg")
    int deleteBdcXtGg(@RequestBody List<String> xtggidList);

    /**
      * @param gzlslid 工作流实例ID
      * @param xmid 项目ID
      * @param bdcXtGgDO 公告配置信息
      * @return sql执行后的公告信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据配置的sql生成公告信息
      */
    @PostMapping(value = "/realestate-config/rest/v1.0/xtgg/sql")
    BdcGgDO generateXtggBySql(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "xmid") String xmid, @RequestBody BdcXtGgDO bdcXtGgDO);

    /**
      * @param xmDOS 项目集合
      * @return 公告信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 批量根据项目获取配置的sql生成公告信息
      */
    @PostMapping(value = "/realestate-config/rest/v1.0/xtgg/sql/pl")
    List<BdcGgDO> generateXtggBySqlPl(@RequestBody List<BdcXmDO> xmDOS, @RequestParam(name = "sply") Integer sply, @RequestParam(name = "gglx") Integer gglx);

    /**
     * @param bdcGgDOS 不动产公告集合
     * @return 新增数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量新增不动产公告信息
     */
    @PostMapping(value = "/realestate-config/rest/v1.0/xtgg/pl")
    int insertBatchBdcGg(@RequestBody List<BdcGgDO> bdcGgDOS);

}
