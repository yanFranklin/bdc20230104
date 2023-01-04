package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.domain.BdcTdcbnydsyqDO;
import cn.gtmap.realestate.common.core.dto.register.BdcDkxxDTO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDkxxQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface BdcTdcbjyqNydqtsyqRestService {
    /**
     * 根据项目id查土地承包经营权信息
     *
     * @param xmid 项目id
     * @return BdcTdcbnydsyqDO 土地承包经营权信息实体
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 16:53 2020/8/25
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/qlxx/tdcbjyqnydqtsyq/{xmid}", method = RequestMethod.GET)
    BdcTdcbnydsyqDO queryByxmid(@PathVariable(value = "xmid") String xmid);

    /**
     * 根据gzlslid查询地块信息
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 10:23 2020/8/25
     */
    @GetMapping("/realestate-register/rest/v1.0/djb/qlxx/tdcbjyqdkxx/{gzlslid}")
    List<BdcDkxxDTO> queryDkxxByGzlslid(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcDkxxQO 查询实体
     * @return {List} 地块信息列表
     * @description 土地承包经营权查询项目或证书关联的地块列表
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/djb/qlxx/tdcbjyq/dkxx")
    List<BdcDkxxDTO> queryTdcbjyqDkxx(@RequestBody BdcDkxxQO bdcDkxxQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcDkxxQO 查询实体
     * @return {List} 地块信息列表
     * @description 未生成证书情况下查找目标证书 土地承包经营权地块列表
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/djb/qlxx/tdcbjyq/dkxx/beforeinit")
    List<BdcDkxxDTO> queryTdcbjyqDkxxBeforeZsInit(@RequestBody BdcDkxxQO bdcDkxxQO);
}
