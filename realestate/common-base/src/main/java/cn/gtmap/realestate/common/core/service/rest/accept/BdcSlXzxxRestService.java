package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlXzxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXzxxPlDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXzxxQO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlXzxxVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 流程修正rest服务
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2021-05-25 17:54
 **/
public interface BdcSlXzxxRestService {

    /**
     * @param bdcSlXzxxQO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 流程修正信息
     * @date : 2021/5/27 17:54
     */
    @PostMapping("/realestate-accept/rest/v1.0/xzxx/query")
    BdcSlXzxxDO queryBdcSlXzxx(@RequestBody BdcSlXzxxQO bdcSlXzxxQO);

    /**
     * @param bdcSlXzxxDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 保存流程修正信息
     * @date : 2021/2/23 17:54
     */
    @PostMapping("/realestate-accept/rest/v1.0/xzxx/save")
    BdcSlXzxxDO saveBdcSlXzxx(@RequestBody BdcSlXzxxDO bdcSlXzxxDO);

    /**
     * @param xzxxid,xmid
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除流程修正息
     * @date : 2021/2/23 17:54
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/xzxx/delete")
    int deleteBdcSlXzxx(@RequestParam(value = "xzxxid", required = false) String xzxxid, @RequestParam(value = "xmid", required = false) String xmid);

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 用于删除流程时，同时删除流程修正信息
     * @date : 2021/2/23 17:54
     */
    @GetMapping("/realestate-accept/rest/v1.0/xzxx/remove")
    void deleteBdcSlXzxxByGzlslid(@RequestParam(value = "processInsId") String processInsId);

    /**
     * @param processInsId 工作流实例ID
     * @param bdcSlCshDTO
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 用于创建修正流程时，插入数据到BDC_SL_XZXX
     * @date : 2021/9/9
     */
    @PostMapping("/realestate-accept/rest/v1.0/xzxx/cshBdcXzxx")
    void cshBdcXzxx(@RequestParam(value = "processInsId", required = false) String processInsId, @RequestBody BdcSlCshDTO bdcSlCshDTO);

    /**
     * @param bdcSlXzxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/9/27 19:22
     */
    @PostMapping("/realestate-accept/rest/v1.0/xzxx/list")
    List<BdcSlXzxxDO> listBdcSlXzxx(@RequestBody BdcSlXzxxQO bdcSlXzxxQO);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询修正信息
     * @date : 2021/9/28 10:13
     */
    @PostMapping("/realestate-accept/rest/v1.0/xzxx/list/page")
    Page<BdcSlXzxxVO> listBdcSlxzxxByPage(Pageable pageable, @RequestParam(value = "gzlslid") String gzlslid);

    /**
     * 分页查询一个业务创建多次修正信息
     * */
    @PostMapping("/realestate-accept/rest/v1.0/xzxx/list/page/pl")
    Page<BdcSlXzxxPlDTO> listBdcSlxzxxByPagePl(Pageable pageable, @RequestParam(value = "gzlslids") String gzlslids);

}
