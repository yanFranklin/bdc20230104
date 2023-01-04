package cn.gtmap.realestate.portal.ui.core.thread;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.portal.ui.core.dto.ForwardYzDTO;
import cn.gtmap.realestate.portal.ui.core.dto.WorkFlowDTO;
import cn.gtmap.realestate.portal.ui.core.vo.BdcBtxyzVO;
import cn.gtmap.realestate.portal.ui.service.BdcBtxYzService;
import cn.gtmap.realestate.portal.ui.service.BdcSignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 必填项验证任务
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 4:34 下午 2020/7/28
 */
public class BtxTask implements Callable<ForwardYzDTO> {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BtxTask.class.getName());
    /**
     * 签名服务
     */
    private final BdcSignService bdcSignService;
    /**
     * 必填项验证服务
     */
    private final BdcBtxYzService bdcBtxYzService;
    /**
     * 转发对象
     */
    private final WorkFlowDTO workFlowDTO;
    /**
     * 用户信息
     */
    private final UserDto userDto;
    /**
     * 是否自动签名
     */
    private final boolean aotuSgin;

    public BtxTask(BdcSignService bdcSignService, BdcBtxYzService bdcBtxYzService, WorkFlowDTO workFlowDTO, UserDto userDto, boolean aotuSgin) {
        this.bdcSignService = bdcSignService;
        this.bdcBtxYzService = bdcBtxYzService;
        this.workFlowDTO = workFlowDTO;
        this.userDto = userDto;
        this.aotuSgin = aotuSgin;
    }

    @Override
    public ForwardYzDTO call() {
        long start = System.currentTimeMillis();
        // 是否自动签名
        if (aotuSgin) {
            bdcSignService.signWithUser(Collections.singletonList(workFlowDTO), userDto);
        }
        // 必填项检查
        List<BdcBtxyzVO> btxyzVOS = bdcBtxYzService.checkBtx(workFlowDTO.getFormKey(), workFlowDTO.getProcessInstanceId(), workFlowDTO.getTaskId());
        ForwardYzDTO forwardYzDTO = new ForwardYzDTO();
        forwardYzDTO.setBdcBtxyzVOS(btxyzVOS);
        LOGGER.info("流程 {} 签名必填项共耗时：{} ms", workFlowDTO.getProcessInstanceId(), System.currentTimeMillis() - start);
        return forwardYzDTO;
    }
}
