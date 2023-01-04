package cn.gtmap.realestate.portal.ui.core.thread;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.portal.ui.core.dto.ForwardYzDTO;
import cn.gtmap.realestate.portal.ui.service.BdcGzyzDealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * 规则验证任务
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 4:37 下午 2020/7/28
 */
public class GzyzTask implements Callable<ForwardYzDTO> {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GzyzTask.class.getName());
    /**
     * 规则处理服务
     */
    private final BdcGzyzDealService bdcGzyzDealService;
    /**
     * 工作流实例 id
     */
    private final String gzlslid;
    /**
     * 流程定义 key
     */
    private final String processKey;
    /**
     * 任务 id
     */
    private final String taskid;
    /**
     * 用户信息
     */
    private final UserDto userDto;

    public GzyzTask(BdcGzyzDealService bdcGzyzDealService, String gzlslid, String processKey, String taskid,UserDto userDto) {
        this.bdcGzyzDealService = bdcGzyzDealService;
        this.gzlslid = gzlslid;
        this.processKey = processKey;
        this.taskid = taskid;
        this.userDto=userDto;
    }

    @Override
    public ForwardYzDTO call() {
        long start = System.currentTimeMillis();
        List<BdcGzyzVO> bdcGzyzVOS = bdcGzyzDealService.zfyz(gzlslid, processKey, taskid,userDto);
        ForwardYzDTO forwardYzDTO = new ForwardYzDTO();
        forwardYzDTO.setBdcGzyzVOS(bdcGzyzVOS);
        forwardYzDTO.setGzlslid(gzlslid);
        LOGGER.info("流程 {} 转发验证共耗时：{} ms", gzlslid, System.currentTimeMillis() - start);
        return forwardYzDTO;
    }
}
