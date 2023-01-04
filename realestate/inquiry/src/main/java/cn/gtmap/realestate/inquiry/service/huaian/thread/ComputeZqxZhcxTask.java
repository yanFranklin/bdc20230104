package cn.gtmap.realestate.inquiry.service.huaian.thread;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcGzltjXmxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.GzltjQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcZhcxFeignService;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcZqxGzltjXmxxVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.inquiry.util.Constants;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 计算周期性综合查询量任务
 *
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0, 2022/10/10
 */
public class ComputeZqxZhcxTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputeZqxZhcxTask.class.getName());
    /**
     * 工作量信息： 线程共享变量，计算并设置 BdcGzltjXmxxVO 中的 综合查询量参数
     */
    private List<BdcZqxGzltjXmxxVO> bdcZqxGzltjXmxxVOList;
    /**
     * 工作量统计QO对象
     */
    private final GzltjQO gzltjQO;

    /**
     * 不动产综合查询日志接口
     */
    private final BdcZhcxFeignService bdcZhcxFeignService;

    public ComputeZqxZhcxTask(GzltjQO gzltjQO, List<BdcZqxGzltjXmxxVO> bdcZqxGzltjXmxxVOList,  BdcZhcxFeignService bdcZhcxFeignService){
        this.gzltjQO = gzltjQO;
        this.bdcZqxGzltjXmxxVOList = bdcZqxGzltjXmxxVOList;
        this.bdcZhcxFeignService = bdcZhcxFeignService;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        // 1、查询对象拷贝处理，防止其他线程修改查询参数
        GzltjQO param = new GzltjQO();
        BeanUtils.copyProperties(gzltjQO, param);

        // 2、根据统计维度，获取综合查询量信息
        List<BdcGzltjXmxxDTO> zhcxList = null;
        if(CommonConstantUtils.GZLTJ_TJWD_RY.equals(param.getDimension())){
            zhcxList = bdcZhcxFeignService.countRyZhcxxx(param);
        }else if(CommonConstantUtils.GZLTJ_TJWD_BM.equals(param.getDimension())){
            zhcxList = bdcZhcxFeignService.countBmZhcxxx(param);
        }

        // 3、将人员办件量信息转换成 Map 对象 ( Key:人员ID, Value: 综合查询量 )
        if(CollectionUtils.isNotEmpty(zhcxList)){
            Map<String, Integer> zhcxMap = this.convertMap(param.getDimension(), zhcxList);
            if(MapUtils.isNotEmpty(zhcxMap)){
                Iterator<BdcZqxGzltjXmxxVO> bdcZqxGzltjXmxxVOIterator = bdcZqxGzltjXmxxVOList.iterator();
                while (bdcZqxGzltjXmxxVOIterator.hasNext()) {
                    BdcZqxGzltjXmxxVO bdcZqxGzltjXmxxVO = bdcZqxGzltjXmxxVOIterator.next();
                    bdcZqxGzltjXmxxVO.setZhcxsl(this.getZhcxCount(bdcZqxGzltjXmxxVO.getTjKey(param.getDimension()), zhcxMap));
                }
            }
        }
        LOGGER.info("计算周期性综合查询量结束，耗时：{}，查询参数为：{}" , System.currentTimeMillis() - startTime, JSON.toJSONString(param));
    }

    /**
     * 将综合查询量信息转换成 Map 对象 ( Key:人员ID 或部门代码, Value: 综合查询量 )
     */
    private Map<String, Integer> convertMap(String tjwd, List<BdcGzltjXmxxDTO> zhcxList){
        if(CommonConstantUtils.GZLTJ_TJWD_BM.equals(tjwd)){
            return zhcxList.stream().filter(t-> StringUtils.isNotBlank(t.getBmdm()))
                    .collect(Collectors.toMap(BdcGzltjXmxxDTO::getBmdm, BdcGzltjXmxxDTO::getNum, (k1,k2)->k1));
        }
        if(CommonConstantUtils.GZLTJ_TJWD_RY.equals(tjwd)){
            return zhcxList.stream().filter(t-> StringUtils.isNotBlank(t.getRyid()))
                    .collect(Collectors.toMap(BdcGzltjXmxxDTO::getRyid, BdcGzltjXmxxDTO::getNum, (k1,k2)->k1));
        }
        return null;
    }

    // 获取综合查询量
    private Integer getZhcxCount(String ryid,  Map<String, Integer> zhcxMap){
        if(StringUtils.isNotBlank(ryid) && zhcxMap.containsKey(ryid)){
            return zhcxMap.get(ryid);
        }else{
           return 0;
        }
    }
}
