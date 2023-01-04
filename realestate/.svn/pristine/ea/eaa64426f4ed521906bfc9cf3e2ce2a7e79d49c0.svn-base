package cn.gtmap.realestate.engine.thread;

import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzTsxxDTO;
import cn.gtmap.realestate.engine.core.exception.CheckException;
import cn.gtmap.realestate.engine.service.BdcGzDmService;
import cn.gtmap.realestate.engine.service.impl.dataflow.BdcGzSjlResolverFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/6/13
 * @description  子规则验证处理线程任务
 */
@Service
@Scope(SCOPE_PROTOTYPE)
public class BdcGzZgzBdsThread <T> implements Callable<BdcGzZgzTsxxDTO> {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGzZgzBdsThread.class);

    /**
     * 动态代码处理
     */
    @Autowired
    private BdcGzDmService bdcGzDmService;

    /**
     * 数据流处理
     */
    @Autowired
    private BdcGzSjlResolverFactory sjlResolverFactory;

    /**
     * 子规则
     */
    private BdcGzZgzDTO bdcGzZgzDTO;

    /**
     * 验证参数
     */
    private Map<String, Object> paramMap;


    @Override
    public BdcGzZgzTsxxDTO call() throws Exception {
        // 获取数据流查询结果
        Map<String, T> queryResult = this.getSjlQueryResult();

        // 执行校验动态代码
        List<String> tipInfo = this.executeJavaCode(queryResult);

        // 封装处理执行结果
        return new BdcGzZgzTsxxDTO.Builder<T>(bdcGzZgzDTO).paramMap(paramMap).sjljg(queryResult).tsxx(tipInfo).build();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {Map} 执行结果
     * @throws CheckException 执行异常
     * @description
     *      获取数据流查询数据结果
     */
    private Map<String, T> getSjlQueryResult() throws Exception {
        try {
            return sjlResolverFactory.getBdcZgzSjlResult(bdcGzZgzDTO);
        } catch (Exception exception) {
            LOGGER.error("子规则：{}，执行异常！", bdcGzZgzDTO.getGzid());
            throw new CheckException(exception, bdcGzZgzDTO.getGzid(), null);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param sjljg 数据流执行结果
     * @return {List} 执行结果
     * @description
     *      执行校验动态代码，获取执行结果
     */
    private List<String> executeJavaCode(Map<String, T> sjljg){
        if(StringUtils.isBlank(bdcGzZgzDTO.getJydm())){
            return null;
        }
        return bdcGzDmService.executeJavaCode(bdcGzZgzDTO, sjljg);
    }

    public void setBdcGzZgzDTO(BdcGzZgzDTO bdcGzZgzDTO) {
        this.bdcGzZgzDTO = bdcGzZgzDTO;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
}
