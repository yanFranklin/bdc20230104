package cn.gtmap.realestate.engine.thread;

import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/04/26
 * @description  子规则验证线程提供者
 */
@Component
public class ThreadProvider implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取子规则执行线程
     * @param bdcGzZgzDTO 子规则信息DTO
     * @param paramMap  验证参数
     * @return 子规则验证处理线程
     */
    public BdcGzZgzBdsThread getThread(BdcGzZgzDTO bdcGzZgzDTO, Map<String, Object> paramMap){
        BdcGzZgzBdsThread bdcGzZgzBdsThread = applicationContext.getBean(BdcGzZgzBdsThread.class);
        bdcGzZgzBdsThread.setBdcGzZgzDTO(bdcGzZgzDTO);
        bdcGzZgzBdsThread.setParamMap(paramMap);
        return bdcGzZgzBdsThread;
    }
}
