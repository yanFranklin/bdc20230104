package cn.gtmap.realestate.register.service.xxbl;

import cn.gtmap.gtc.sso.domain.dto.AuditLogDto;
import cn.gtmap.realestate.common.core.enums.LogEventEnum;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDbVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcXxblLogVO;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 信息补录日志服务
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 10:25 下午 2020/3/18
 */
@Validated
public interface BdcXxblLogService {

    /**
     * 根据参数和日志类型查询日志
     *
     * @param pageable 分页参数，不传默认 page 0，size 10
     * @param paramch  查询参数
     * @param event    对应的 event 类型
     * @return {Page<AuditLogDto>} 大云分页对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    Page<AuditLogDto> listLog(Pageable pageable, @NotBlank(message = "查询参数不能为空") String paramch, LogEventEnum event);

    /**
     * 处理日志分页数据<br/>
     * 数据回溯的分页处理
     *
     * @param auditLogDtoPage 日志分页对象
     * @return 日志 VO 对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    List<BdcXxblLogVO> dealLogPage(Page<AuditLogDto> auditLogDtoPage);

    /**
     * @param id 回退的日志 id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 选择指定 id 的日志中 before 数据回退
     */
    void backLog(String id) throws Exception;

    /**
     * @param json json 字符串
     * @return BdcYwxxDTO 业务信息对象
     * @throws Exception 初始化更新业务信息出现异常
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 将 json 字符串转换为 ywxx 后通过初始化接口更新到数据库中
     * <strong>bdcQl 是接口所以在转换时需要特殊处理固提取此方法</strong>
     */
    void backYwxxFromJson(String json) throws Exception;

    /**
     * @param json json 字符串
     * @return BdcYwxxDTO 业务信息对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 将 json 字符串转换成 ywxx 对象 <br>
     * <strong>bdcQl 是接口所以在转换时需要特殊处理固提取此方法</strong>
     */
    BdcYwxxDTO convertJsonToYwxx(String json);

    /**
     * @param xmid 当前项目 id
     * @return {String} 日志中对应的 ywxx json 字符串
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 查询当前补录修改流程的初始化日志，返回 ywxx值
     */
    String queryBllcModifyLog(String xmid);

    /**
     * 获取信息补录修改记录对比
     *
     * @param processInsId 工作流定义ID
     * @return {List<BdcDbVO>} 对比 VO 集合
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     */
    List<BdcDbVO> getSjdb(String processInsId,String xmid);
}
