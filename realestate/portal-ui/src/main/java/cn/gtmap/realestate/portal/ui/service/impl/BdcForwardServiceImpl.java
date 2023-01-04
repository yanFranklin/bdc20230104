package cn.gtmap.realestate.portal.ui.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.dto.AutoCompleteDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcZsXmDTO;
import cn.gtmap.realestate.common.core.dto.portal.BdcZfhyzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.common.matcher.SyncAutoCmpleteClientMatcher;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.portal.ui.core.dto.ForwardYzDTO;
import cn.gtmap.realestate.portal.ui.core.dto.WorkFlowDTO;
import cn.gtmap.realestate.portal.ui.core.thread.BtxTask;
import cn.gtmap.realestate.portal.ui.core.thread.GzyzTask;
import cn.gtmap.realestate.portal.ui.service.BdcBtxYzService;
import cn.gtmap.realestate.portal.ui.service.BdcForwardService;
import cn.gtmap.realestate.portal.ui.service.BdcGzyzDealService;
import cn.gtmap.realestate.portal.ui.service.BdcSignService;
import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 转发验证服务
 *
 * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
 * @version 4:30 下午 2020/7/28
 */
@Service
public class BdcForwardServiceImpl implements BdcForwardService {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcForwardServiceImpl.class.getName());

    /**
     * 转发验证线程池
     */
    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            // 核心线程数量
            20,
            // 最大线程数
            40,
            // 空闲超时一小时（调用频繁）
            3600, TimeUnit.SECONDS,
            // 阻塞队列
            new ArrayBlockingQueue<>(100),
            // 线程工厂
            new ThreadFactoryBuilder().setNameFormat("forward-pool-%d").build(),
            // 过多任务直接主线程处理
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    /**
     * 规则验证处理服务
     */
    @Autowired
    private BdcGzyzDealService bdcGzyzDealService;
    /**
     * 签名服务
     */
    @Autowired
    private BdcSignService bdcSignService;
    /**
     * 必填项验证服务
     */
    @Autowired
    private BdcBtxYzService bdcBtxYzService;
    /**
     * 用户信息服务
     */
    @Autowired
    private UserManagerUtils userManagerUtils;
    /**
     * 处理流程自动办结出现的问题
     */
    @Autowired
    private SyncAutoCmpleteClientMatcher syncAutoCmpleteClient;

    /**
     * 转发验证 <br/>
     * 规则验证和必填项验证
     *
     * @param workFlowDTO 转发数据
     * @param autoSign    是否自动签名
     * @return 验证结果
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    @Override
    public List<Future<ForwardYzDTO>> zfyz(WorkFlowDTO workFlowDTO, boolean autoSign) {
        // 提交规则验证
        GzyzTask gzyzTask = new GzyzTask(bdcGzyzDealService, workFlowDTO.getProcessInstanceId(), workFlowDTO.getProcessDefKey(), workFlowDTO.getTaskId(),userManagerUtils.getCurrentUser());
        Future<ForwardYzDTO> gzyzFuture = threadPoolExecutor.submit(gzyzTask);
        // 提交必填项验证
        BtxTask btxTask = new BtxTask(bdcSignService, bdcBtxYzService, workFlowDTO, userManagerUtils.getCurrentUser(), autoSign);
        Future<ForwardYzDTO> btxFuture = threadPoolExecutor.submit(btxTask);
        // 返回验证结果
        List<Future<ForwardYzDTO>> result = Lists.newArrayList();
        result.add(gzyzFuture);
        result.add(btxFuture);
        return result;
    }

    /**
     * 未办结流程补偿事件
     *
     * @param bdcZsXmDTOS 不动产项目 DTO
     * @param szrid       缮证人 id
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    @Override
    public void makeUpWbjyw(List<BdcZsXmDTO> bdcZsXmDTOS, String szrid) {
        // 1. 校验参数
        if (CollectionUtils.isEmpty(bdcZsXmDTOS)) {
            throw new MissingArgumentException("证书项目信息");
        }
        if (StringUtils.isBlank(szrid)) {
            throw new MissingArgumentException("缮证人id");
        }
        // 2. 组织参数
        List<AutoCompleteDTO> autoCompleteDtos = this.generateParam(bdcZsXmDTOS, szrid);
        LOGGER.info("处理流程自动办结出现的问题，传入参数：{}", JSON.toJSONString(autoCompleteDtos));
        // 3. 调用平台接口处理问题数据
        syncAutoCmpleteClient.syncAutoCmpleteDataList(autoCompleteDtos);
    }

    @Override
    public List<BdcGzyzVO> zfhyz(BdcZfhyzDTO bdcZfhyzDTO) {
        //规则验证
        return bdcGzyzDealService.zfhyz(bdcZfhyzDTO);
    }

    /**
     * @param workFlowDTO
     * @param autoSign
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 转发验证必填项
     * @date : 2021/8/26 16:00
     */
    @Override
    public List<Future<ForwardYzDTO>> zfyzBtx(WorkFlowDTO workFlowDTO, boolean autoSign) {
        // 提交规则验证
        GzyzTask gzyzTask = new GzyzTask(bdcGzyzDealService, workFlowDTO.getProcessInstanceId(), workFlowDTO.getProcessDefKey(), workFlowDTO.getTaskId(),userManagerUtils.getCurrentUser());
        Future<ForwardYzDTO> gzyzFuture = threadPoolExecutor.submit(gzyzTask);
        // 返回验证结果
        List<Future<ForwardYzDTO>> result = Lists.newArrayList();
        result.add(gzyzFuture);
        return result;
    }

    /**
     * @param workFlowDTO
     * @param autoSign
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 转发验证规则
     * @date : 2021/8/26 16:05
     */
    @Override
    public List<Future<ForwardYzDTO>> zfyzZhgz(WorkFlowDTO workFlowDTO, boolean autoSign) {
        // 提交必填项验证
        BtxTask btxTask = new BtxTask(bdcSignService, bdcBtxYzService, workFlowDTO, userManagerUtils.getCurrentUser(), autoSign);
        Future<ForwardYzDTO> btxFuture = threadPoolExecutor.submit(btxTask);
        // 返回验证结果
        List<Future<ForwardYzDTO>> result = Lists.newArrayList();
        result.add(btxFuture);
        return result;
    }


    List<AutoCompleteDTO> generateParam(List<BdcZsXmDTO> bdcZsXmDTOS, String szrid) {
        UserDto user = userManagerUtils.getUserByUserid(szrid);
        if (null == user) {
            throw new AppException("未查询到用户信息：id:" + szrid);
        }
        return bdcZsXmDTOS.stream().map(bdcZsXmDTO -> {
            AutoCompleteDTO autoCompleteDto = new AutoCompleteDTO();
            autoCompleteDto.setProcessInsId(bdcZsXmDTO.getGzlslid());
            autoCompleteDto.setEndTime(bdcZsXmDTO.getSzsj());
            // 实际大云使用的是 username 而非 userid
            autoCompleteDto.setUserId(user.getUsername());
            return autoCompleteDto;
        }).collect(Collectors.toList());
    }
}
