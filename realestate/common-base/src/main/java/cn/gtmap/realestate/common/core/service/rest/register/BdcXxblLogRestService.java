package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.enums.LogEventEnum;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDbDetailVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDbVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcXxblLogVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 信息补录日志服务
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 11:25 上午 2020/3/19
 */
public interface BdcXxblLogRestService {

    /**
     * 根据 event 和 查询参数 获取日志数据
     *
     * @param pageable 分页参数
     * @param paramch  查询参数
     * @param event    日志类型
     * @return {Page} 分页查询日志 VO 对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/log")
    Page<BdcXxblLogVO> listLog(Pageable pageable, @RequestParam(name = "paramch") String paramch, @RequestParam(name = "event") LogEventEnum event);

    /**
     * @param id 回退的日志 id
     * @throws Exception 初始化更新业务信息出现异常
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 选择指定 id 的日志中 before 数据回退
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/log/back/{id}")
    void backLog(@PathVariable("id") String id) throws Exception;

    /**
     * @param xmid 当前项目 id
     * @return {String} 日志中对应的 ywxx json 字符串
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 查询当前补录修改流程的初始化日志，返回 ywxx值
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/log/lc/modify")
    String queryBllcModifyLog(@RequestParam("xmid") String xmid);

    /**
     * @param xmid 当前项目 id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 还原修改的业务的数据 <br>
     * 还原数据取日志中保存的被修改数据的业务信息
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/log/lc/modify/ywxx/back")
    void backBllcModifyLog(@RequestParam("xmid") String xmid) throws Exception;

    /**
     * 信息对比<br/>
     * 获取当前项目相比较于初始化结束后修改了哪些数据
     *
     * @param processInsId 工作流实例 id
     * @return {List<BdcDbVO>} 对比 VO 集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/log/lc/modify/ywxx/change")
    List<BdcDbVO> xxdb(@RequestParam("processInsId") String processInsId) throws Exception;

    /**
     * 查询页面高亮显示内容
     *
     * @param processInsId 修改流程的 processInsId
     * @return {List<BdcDbDetailVO>} 对比 VO 集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/realestate-register/rest/v1.0/blxx/log/lc/modify/xxdb")
    List<BdcDbDetailVO> queryGlxx(@RequestParam("processInsId") String processInsId) throws Exception;

    /**
     * 获取信息补录修改记录对比
     *
     * @param processInsId 工作流定义ID
     * @return {List<BdcDbVO>} 对比 VO 集合
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     */
    @GetMapping("/realestate-register/rest/v1.0/blxx/log/getSjdb")
    List<BdcDbVO> getSjdb(@RequestParam(value = "processInsId") String processInsId,@RequestParam(value = "xmid",required = false) String xmid);
}
