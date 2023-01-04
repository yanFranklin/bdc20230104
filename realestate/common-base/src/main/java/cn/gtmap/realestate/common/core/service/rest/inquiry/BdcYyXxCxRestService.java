package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.BdcYyDO;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcYyVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-07-11
 * @description 查询子系统：不动产异议信息
 */
public interface BdcYyXxCxRestService {
    /**
     * @param pageable
     * @param bdcYyQOJson
     * @return Page<BdcYyDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 分页获取异议信息
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcYyXx/page")
    Page<BdcYyVO> listBdcYyByPage(Pageable pageable,
                                  @RequestParam(name = "bdcYyQOJson", required = false) String bdcYyQOJson);

    /**
     * 常州，分页获取异议信息，拥有同综合查询一样多查询条件
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcYyXx/page/cz")
    Page<BdcYyVO> listBdcYyByPageCz(Pageable pageable,
                                  @RequestParam(name = "bdcYyQOJson", required = false) String bdcYyQOJson);

    /**
     * @param bdcYyQOJson
     * @return Page<BdcYyDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取异议信息 用于导出
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcYyXx")
    List listBdcYy(@RequestParam(name = "bdcYyQOJson", required = false) String bdcYyQOJson);

    /**
     * 常州，获取异议信息 用于导出
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcYyXx/cz")
    List listBdcYyCz(@RequestParam(name = "bdcYyQOJson", required = false) String bdcYyQOJson);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 批量更新 异议
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcYyXx")
    int updateBdcYy(@RequestBody List<BdcYyDO> bdcYyDOList);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询异议
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcYyXx/search/{xmid}")
    List listBdcYyByXmid(@PathVariable(value = "xmid") String xmid);


    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcYyXx/listBdcYyByBdcdyh")
    List listBdcYyByBdcdyh(@RequestParam(name = "bdcdyh") String bdcdyh);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 更加不动产单元号批量查询抵押信息
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcYy/bdcdyhList/search")
    List<BdcYyDO> listBdcYyByBdcdyhs(@RequestBody List<String> bdcdyhList);
}
