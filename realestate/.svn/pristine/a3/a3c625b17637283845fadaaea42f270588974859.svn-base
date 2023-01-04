package cn.gtmap.realestate.portal.ui.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcYjdTaskGxDO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcYjdFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcYwsjHxFeignService;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.portal.ui.service.BdcYbYjdService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BdcYbYjdServiceImpl implements BdcYbYjdService {

    private final static String TIME_FORMAT = "yyyyMMdd";
    private final static String LSH_LWLX_YJDH = "YJDH";
    private final static String ZZSJFW = "ALL";

    /**
     * 编号服务
     */
    @Autowired
    private BdcBhFeignService bdcBhFeignService;

    /**
     * 回写服务
     */
    @Autowired
    private BdcYwsjHxFeignService bdcYwsjHxFeignService;

    @Autowired
    private BdcYjdFeignService bdcYjdFeignService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcYbYjdServiceImpl.class);

    /**
     * 生成移交单编号<br> 蚌埠
     *
     * @param taskids 任务id
     * @return {String} 移交单单号
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public String generateYbYjdBh(List<String> taskids) {
        // 生成移交单号
        String dateStr = DateFormatUtils.format(new Date(), TIME_FORMAT);
        // 获取流水号
        Integer count = bdcBhFeignService.queryLsh(LSH_LWLX_YJDH, ZZSJFW);
        String yjdh = dateStr + String.format("%04d", count);

        // 将移交单号和任务关系写入关联关系表
        List<BdcYjdTaskGxDO> bdcYjdTaskGxDOList = new ArrayList<>();
        for (String taskid : taskids) {
            BdcYjdTaskGxDO bdcYjdTaskGxDO = new BdcYjdTaskGxDO();
            bdcYjdTaskGxDO.setGxid(UUIDGenerator.generate16());
            bdcYjdTaskGxDO.setTaskid(taskid);
            bdcYjdTaskGxDO.setYjdh(yjdh);
            bdcYjdTaskGxDO.setDyzt(0);
            bdcYjdTaskGxDOList.add(bdcYjdTaskGxDO);
        }
        bdcYjdFeignService.saveBdcYjdTaskGx(bdcYjdTaskGxDOList);
        return yjdh;
    }

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [ybTaskList] 已办任务列表
     * @description 添加打印状态
     */
    @Override
    public List<Map<String, Object>> addYjdDyzt(List<Map<String, Object>> ybTaskList) {
        for (Map<String, Object> ybTask : ybTaskList) {
            // 将打印状态先设为默认值0，避免以前大云回写数据干扰
            ybTask.put("dyzt", 0);
            Map paramMap = new HashMap();
            paramMap.put("taskid", (String) ybTask.get("taskId"));
            List<BdcYjdTaskGxDO> yjdTaskGxDOList = bdcYjdFeignService.getYjdTaskGx(paramMap);
            if (CollectionUtils.isNotEmpty(yjdTaskGxDOList)) {
                // 如果该任务对应的移交单号有任意一个打印过，则状态显示为已打印
                List<BdcYjdTaskGxDO> ydyYjdTaskGxList = yjdTaskGxDOList.stream().filter(bdcYjdTaskGxDO -> bdcYjdTaskGxDO.getDyzt() == 1).collect(Collectors.toList());
                if (ydyYjdTaskGxList.size() > 0) {
                    ybTask.put("dyzt", 1);
                }
            }
        }
        return ybTaskList;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [ybTaskList]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @description 已办任务列表添加移交单号
     */
    @Override
    public List<Map<String, Object>> addYjdh(List<Map<String, Object>> ybTaskList) {
        for (Map<String, Object> yjdTask : ybTaskList) {
            //先将移交单号设为空，避免之前大云回写数据干扰
            yjdTask.put("yjdh", "");
            Map paramMap = new HashMap();
            paramMap.put("taskid", (String) yjdTask.get("taskId"));
            List<BdcYjdTaskGxDO> yjdTaskGxDOList = bdcYjdFeignService.getYjdTaskGx(paramMap);
            if (CollectionUtils.isNotEmpty(yjdTaskGxDOList)) {
                yjdTask.put("yjdh", yjdTaskGxDOList.get(0).getYjdh());
            }
        }
        return ybTaskList;
    }

    /**
     * 批量更新大云 yjdh 字段
     *
     * @param gzlslids 工作流id集合
     * @param yjdh     移交单号
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private void updateYjdh(List<String> gzlslids, String yjdh) {
        Map<String, Object> processInsExtendDto = new HashMap<>();
        processInsExtendDto.put("YJDH", yjdh);
        for (String gzlslid : gzlslids) {
            bdcYwsjHxFeignService.updateBdcYwsj(gzlslid, processInsExtendDto);
        }
    }
}
