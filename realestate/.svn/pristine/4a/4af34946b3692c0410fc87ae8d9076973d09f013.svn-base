package cn.gtmap.realestate.inquiry.service.huaian.thread;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcGzltjXmxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.GzltjQO;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcRyGzltjXmxxVO;
import cn.gtmap.realestate.inquiry.core.mapper.BdcGzltjXmxxMapper;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 计算人员发证量任务
 *
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/6/29
 */
public class ComputeRyFzlTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputeRyFzlTask.class.getName());
    /**
     * 工作量信息： 线程共享变量，计算并设置 BdcGzltjXmxxVO 中的 fzl（发证量）参数
     */
    private List<BdcRyGzltjXmxxVO> bdcRyGzltjXmxxVOList;
    /**
     * 工作量统计QO对象
     */
    private final GzltjQO gzltjQO;
    /**
     * 工作量统计Mapper
     */
    private final BdcGzltjXmxxMapper bdcGzltjXmxxMapper;

    public ComputeRyFzlTask(GzltjQO gzltjQO, List<BdcRyGzltjXmxxVO> bdcRyGzltjXmxxVOList, BdcGzltjXmxxMapper bdcGzltjXmxxMapper){
        this.gzltjQO = gzltjQO;
        this.bdcRyGzltjXmxxVOList = bdcRyGzltjXmxxVOList;
        this.bdcGzltjXmxxMapper = bdcGzltjXmxxMapper;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        // 1、根据统计类型，获取人员办件量信息
        List<BdcGzltjXmxxDTO> bdcGzltjXmxxDTOList = bdcGzltjXmxxMapper.countRyFzlxx(gzltjQO);

        // 2、将人员办件量信息转换成 Map 对象 ( Key:人员ID, Value: 发证量 )
        if(CollectionUtils.isNotEmpty(bdcGzltjXmxxDTOList)){
            Map<String, Integer> resultMap = bdcGzltjXmxxDTOList.stream().filter(t-> StringUtils.isNotBlank(t.getRyid()))
                    .collect(Collectors.toMap(BdcGzltjXmxxDTO::getRyid,BdcGzltjXmxxDTO::getNum,(k1,k2)->k1));

            Iterator<BdcRyGzltjXmxxVO> bdcGzltjXmxxVOIterator = bdcRyGzltjXmxxVOList.iterator();
            while (bdcGzltjXmxxVOIterator.hasNext()) {
                BdcRyGzltjXmxxVO bdcRyGzltjXmxxVO = bdcGzltjXmxxVOIterator.next();
                if(StringUtils.isNotBlank(bdcRyGzltjXmxxVO.getRyid()) && resultMap.containsKey(bdcRyGzltjXmxxVO.getRyid())){
                    bdcRyGzltjXmxxVO.setFzl(resultMap.get(bdcRyGzltjXmxxVO.getRyid()));
                }
            }
        }
        LOGGER.info("计算人员发证量结束，耗时：{}， 查询参数：{}" , System.currentTimeMillis() - startTime, JSON.toJSONString(gzltjQO));
    }
}
