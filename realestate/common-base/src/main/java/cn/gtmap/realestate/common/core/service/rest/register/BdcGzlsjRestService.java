package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.domain.register.BdcGzlJkDO;
import cn.gtmap.realestate.common.core.domain.register.BdcGzlSjDO;
import cn.gtmap.realestate.common.core.domain.register.BdcGzlsjJkGxDO;
import cn.gtmap.realestate.common.core.dto.register.BdcGzlsjFzDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcGzlsjPlDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcWorkFlowDTO;
import cn.gtmap.realestate.common.core.qo.register.BdcGzlQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 工作流事件rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-30 13:46
 **/
public interface BdcGzlsjRestService {
    /**
     * @param gzlsjlx 事件类型 bdc_zd_gzlsjlx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 工作流事件统一接口POST
     * @date : 2022/3/23 17:21
     */
    @GetMapping("/realestate-register/rest/v1.0/workflow/{gzlsjlx}")
    void workFlowService(@PathVariable(value = "gzlsjlx") Integer gzlsjlx, BdcWorkFlowDTO workFlowDTO);


    /**
     * @param bdcGzlsjJson
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询工作流事件
     * @date : 2022/3/30 13:47
     */
    @PostMapping("/realestate-register/rest/v1.0/workflow/gzlsj/page")
    Page<BdcGzlSjDO> listGzlsjByPage(Pageable pageable, @RequestParam(name = "bdcGzlsjJson", required = false) String bdcGzlsjJson);

    /**
     * @param bdcGzlSjDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增工作流事件
     * @date : 2022/3/30 13:48
     */
    @PostMapping("/realestate-register/rest/v1.0/workflow/gzlsj")
    BdcGzlSjDO insertGzlsj(@RequestBody BdcGzlSjDO bdcGzlSjDO);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 修改工作流事件
     * @date : 2022/3/30 13:49
     */

    /**
     * @param sjid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除工作流事件
     * @date : 2022/3/30 13:49
     */
    @DeleteMapping("/realestate-register/rest/v1.0/workflow/gzlsj/{sjid}")
    void deleteGzlsj(@PathVariable(value = "sjid") String sjid);


    /**
     * @param bdcGzljkJson
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询工作流接口
     * @date : 2022/3/30 13:48
     */
    @PostMapping("/realestate-register/rest/v1.0/workflow/gzljk/page")
    Page<BdcGzlJkDO> listBdcGzlJkByPage(Pageable pageable, @RequestParam(name = "bdcGzljkJson", required = false) String bdcGzljkJson);


    /**
     * @param bdcGzlJkDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增工作流接口
     * @date : 2022/3/30 16:58
     */
    @PostMapping("/realestate-register/rest/v1.0/workflow/gzljk")
    int insertBdcGzlJk(@RequestBody BdcGzlJkDO bdcGzlJkDO);

    /**
     * @param jkid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除工作流接口
     * @date : 2022/3/30 17:00
     */
    @DeleteMapping("/realestate-register/rest/v1.0/workflow/gzljk/{jkid}")
    void deleteBdcGzlJk(@PathVariable(value = "jkid") String jkid);

    /**
     * @param bdcGzlsjJkGxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增工作流事件接口关系
     * @date : 2022/3/30 17:18
     */
    @PostMapping("/realestate-register/rest/v1.0/workflow/gzlsjjkgx")
    int insertBdcGzlSjjkGx(@RequestBody BdcGzlsjJkGxDO bdcGzlsjJkGxDO);

    /**
     * @param bdcGzlQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询接口信息
     * @date : 2022/4/7 16:36
     */
    @PostMapping("/realestate-register/rest/v1.0/workflow/gzljk/jkxx")
    List<BdcGzlJkDO> listBdcGzlJk(@RequestBody BdcGzlQO bdcGzlQO);

    /**
     * @param bdcGzlQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询工作流事件
     * @date : 2022/4/11 14:40
     */
    @PostMapping("/realestate-register/rest/v1.0/workflow/gzlsj/sjxx")
    List<BdcGzlSjDO> listBdcGzlSj(@RequestBody BdcGzlQO bdcGzlQO);

    /**
     * @param bdcGzlQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询工作流事件和接口关系
     * @date : 2022/4/11 15:15
     */
    @PostMapping("/realestate-register/rest/v1.0/workflow/gzlsjjk/gx")
    List<BdcGzlsjJkGxDO> listBdcGzlsjJkGx(@RequestBody BdcGzlQO bdcGzlQO);

    /**
     * @param sjid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存工作流事件的关联关系
     * @date : 2022/4/12 17:01
     */
    @PostMapping("/realestate-register/rest/v1.0/workflow/gzlsjjk/glgx")
    void saveBdcgzlsjGx(@RequestParam(value = "sjid") String sjid, @RequestParam(value = "jkid") String jkid);

    /**
     * @param sjid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除工作流事件的关联关系
     * @date : 2022/4/12 17:02
     */
    @DeleteMapping("/realestate-register/rest/v1.0/workflow/gzlsjjk/glgx")
    void deleteBdcGzlSjGx(@RequestParam(value = "sjid") String sjid, @RequestParam(value = "jkid") String jkid);

    /**
     * @param sjid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据事件id 删除工作流事件关系
     * @date : 2022/4/13 16:38
     */
    @DeleteMapping("/realestate-register/rest/v1.0/workflow/gzlsjgx/glgx")
    void deleteBdcGzlSjGx(@RequestParam(value = "sjid") String sjid);

    /**
     * @param bdcGzlsjJkGxDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量更新关联关系
     * @date : 2022/4/14 15:18
     */
    @PutMapping("/realestate-register/rest/v1.0/workflow/gzlsjgx/glgx/pl")
    void batchUpdateGzlsjGx(@RequestBody List<BdcGzlsjJkGxDO> bdcGzlsjJkGxDOList);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量更新工作流事件（勾选多个流程或者多个节点）
     * @date : 2022/4/21 15:17
     */
    @PostMapping("/realestate-register/rest/v1.0/workflow/gzlsj/pl")
    void batchSaveGzlsj(@RequestBody BdcGzlsjPlDTO bdcGzlsjPlDTO);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 复制工作流事件
     * @date : 2023/1/4 11:11
     */
    @PostMapping("/realestate-register/rest/v1.0/workflow/gzlsj/copy")
    void copyGzlsj(@RequestBody BdcGzlsjFzDTO bdcGzlsjFzDTO);


}
