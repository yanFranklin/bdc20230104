package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCfDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcCfxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCfxxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-07-10
 * @description 查封信息查询
 */
public interface BdcCfxxRestService {

    /**
     * 分页查询查封信息
     *
     * @param pageable
     * @param bdcCfxxQOJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.inquiry.BdcCfxxDTO                                                               ;>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcCfxx/page")
    Page<BdcCfxxDTO> listBdcCfByPage(Pageable pageable,
                                     @RequestParam(name = "bdcCfxxQOJson", required = false) String bdcCfxxQOJson);

    /**
     * 查询续封信息
     *
     * @param bdcXfxxQOJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.inquiry.BdcCfxxDTO                                                                                                                               ;>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcXfxx/page")
    List<Map> listBdcXf(@RequestParam(name = "bdcXfxxQOJson", required = false) String bdcXfxxQOJson);

    /**
     * 解封信息
     *
     * @param jfxxQO
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.inquiry.BdcCfxxDTO                                                                                                                               ;>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcCf/jf")
    void jfBdcCf(@RequestParam(name = "jfxxQO", required = false) String jfxxQO);

    /**
     * 更新查封信息
     *
     * @param jfxxQO
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.inquiry.BdcCfxxDTO                                                                                                                               ;>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcCf/edit")
    void editBdcCf(@RequestParam(name = "jfxxQO", required = false) String jfxxQO);


    /**
     * 通过bdcdyh查询续封未超期的数量
     * @param bdcdyhs
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcCf/bdcdyhs")
    int queryWdqXfByBdcyhs(@RequestParam(name = "bdcdyhs", required = false) String bdcdyhs,@RequestParam(name = "xmids", required = false) String xmids);

    /**
     * 提供到期查封信息
     *
     * @param bdcCfxxQOJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.inquiry.BdcCfxxDTO                                                               ;>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcCfDq/list")
    Object bdcCfDqList(Pageable pageable, @RequestParam(name = "bdcXfxxQOJson", required = false) String
            bdcCfxxQOJson);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询查封
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcCfDq/search/{xmid}")
    List listBdcCfByXmid(@PathVariable(value = "xmid") String xmid);

    /**
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/7
     * @description 根据不动产单元号查询查封信息
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcCfDq/listBdccfByBdcdyh")
    List listBdccfByBdcdyh(@RequestParam(name = "bdcdyh") String bdcdyh);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 更加不动产单元号批量查询查封信息
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcCfDq/bdcdyhList/search")
    List<BdcCfDO> listBdcCfByBdcdyhs(@RequestBody List<String> bdcdyhList);

    /**
     * @param bdcCfxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据查封类型和查封单元号查询
     * @date : 2021/7/30 14:37
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcCf/cflx")
    List<BdcCfDTO> listBdcCfxx(@RequestBody BdcCfxxQO bdcCfxxQO);

    /**
     * @param bdcCfxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询续封的上一首查封的查封编号
     * @date : 2021/7/30 14:37
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcCf/ycfbh")
    List<BdcCfDTO> listCfxxYcfbh(@RequestBody BdcCfxxQO bdcCfxxQO);


    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID查询原查封信息
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/ycf")
    List<BdcCfDO> listYcfxxByGzlslid(@RequestParam(name = "gzlslid") String gzlslid);


    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查封查询罗辑调整-根据查封顺序和查封起始时间排序
     * @date : 2021/11/1 10:58
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcCf/cfsx")
    List<BdcCfDTO> listBdcCfxxByCfsx(@RequestBody BdcCfxxQO bdcCfxxQO);
}
