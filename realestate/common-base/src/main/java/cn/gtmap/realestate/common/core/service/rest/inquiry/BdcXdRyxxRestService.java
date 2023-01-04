package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.BdcXdryxxDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXdryQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: 3.0
 * @description: 限定人员信息restservice
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-08-03 10:14
 **/
public interface BdcXdRyxxRestService {

    /**
     * @param bdcXdryQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询限定人员信息
     * @date : 2021/8/3 10:15
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdc/xdyrxx")
    List<BdcXdryxxDO> listBdcXdyrxx(@RequestBody BdcXdryQO bdcXdryQO);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询限定人员信息
     * @date : 2021/8/3 11:16
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdc/xdyrxx/page")
    Page<BdcXdryxxDO> listBdcXdryByPage(Pageable pageable, @RequestParam(value = "bdcXdryJson", required = false) String bdcXdryJson);

    /**
     * @param idList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除限定人员信息
     * @date : 2021/8/3 11:16
     */
    @DeleteMapping("/realestate-inquiry/rest/v1.0/bdc/xdyrxx")
    void deletXdryxx(@RequestBody List<String> idList);

    /**
     * @param bdcXdryxxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增限定人员信息
     * @date : 2021/8/3 11:16
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdc/xdyrxx/add")
    BdcXdryxxDO addBdcXdryxx(@RequestBody BdcXdryxxDO bdcXdryxxDO);

    /**
     * @param bdcXdryxxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新限定人员信息
     * @date : 2021/8/3 11:17
     */
    @PutMapping("/realestate-inquiry/rest/v1.0/bdc/xdyrxx")
    BdcXdryxxDO updateBdcXdryxx(@RequestBody BdcXdryxxDO bdcXdryxxDO);

}
