package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.matcher.TaskHandleClientMatcher;
import cn.gtmap.gtc.workflow.domain.manage.EventExtendDTO;
import cn.gtmap.realestate.common.core.enums.HlwSlztEnum;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 大云任务工具类
 *
 * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
 * @version 2:55 下午 2020/6/18
 */
@Component
public class TaskUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskUtils.class);
    @Autowired
    private TaskHandleClientMatcher taskHandleClient;


    /**
     * 组织删除工作流事件中需要的参数<br>
     * <strong>新增删除事件中的参数均需要在此方法中添加，否则不会获取到参数</strong>
     *
     * @param gzlslid 工作流实例 id
     * @param reason  删除原因
     * @return {EventExtendDTO} 工作流事件扩展方法
     */
    public EventExtendDTO initEventExtend(String gzlslid, String reason) {
        EventExtendDTO eventExtendDTO = new EventExtendDTO();
        Map<String, String> requestParam = Maps.newHashMap();
        requestParam.put("gzlslid", gzlslid);
        requestParam.put("reason", reason);
        //slzt由现场配置,不再代码写死
//        requestParam.put("slzt", HlwSlztEnum.DELETE.getSlzt());
        eventExtendDTO.setRequestParam(requestParam);
        return eventExtendDTO;
    }

    /**
     * 根据流程实例id删除流程
     *
     * @param gzlslid 流程实例ID <strong>必填</strong>
     * @param reason  删除原因
     * @return {boolean} 是否删除成功
     */
    public boolean deleteTask(String gzlslid, String reason) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid");
        }
        EventExtendDTO eventExtendDTO = initEventExtend(gzlslid, reason);
        LOGGER.error("TaskUtils 删除数据传递参数：{}", JSON.toJSONString(eventExtendDTO));
        return taskHandleClient.deleteProcessWithReason(gzlslid, reason, eventExtendDTO);
    }
}
