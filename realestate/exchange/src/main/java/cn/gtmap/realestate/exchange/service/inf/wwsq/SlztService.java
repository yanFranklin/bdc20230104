package cn.gtmap.realestate.exchange.service.inf.wwsq;


import cn.gtmap.realestate.common.core.dto.accept.BdcDsfSfxxDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.jfxx.TsjfxxRequestBody;
import cn.gtmap.realestate.exchange.core.dto.wwsq.updateslzt.UpdateSlztRequestBody;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/10/16
 * @description 受理状态相关服务
 */
public interface SlztService {
    /**
     * 根据工作流实例id获取对应数据
     *
     * @param map
     * @return
     */
    void updateSlzt(Map map);

    /**
     * 根据工作流实例id获取对应数据 常州
     *
     * @param map
     * @return
     */
    void updateSlztCz(Map map);

    /**
     * 根据工作流实例id获取对应数据  南通一体化特殊处理
     *
     * @param map
     * @return
     */
    void updateSlztNt(Map map);

    /**
     * @param
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 审核状态推送
     */
    void shztTs(Map map);

    /**
     * 根据slbh转发或删除流程
     *
     * @param updateSlztRequestBody updateSlztRequestBody
     * @return Object
     * @Date 2021/12/3
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Object forwardOrDelete(@RequestBody UpdateSlztRequestBody updateSlztRequestBody);

    /**
     * 根据slbh查询缴费二维码
     *
     * @param updateSlztRequestBody updateSlztRequestBody
     * @return Object
     * @Date 2021/12/3
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Object queryJfewmUrl(@RequestBody UpdateSlztRequestBody updateSlztRequestBody);

    /**
     * 外网推送缴费信息
     *
     * @param bdcDsfSfxxDTO bdcDsfSfxxDTO
     * @return Object
     * @Date 2021/12/3
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Object wwsqTsjfxx(@RequestBody TsjfxxRequestBody tsjfxxRequestBody);


}
