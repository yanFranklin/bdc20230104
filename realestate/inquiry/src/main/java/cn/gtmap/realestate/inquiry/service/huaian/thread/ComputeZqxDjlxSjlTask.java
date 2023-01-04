package cn.gtmap.realestate.inquiry.service.huaian.thread;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcGzltjXmxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.GzltjQO;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcZqxGzltjXmxxVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.inquiry.core.mapper.BdcGzltjXmxxMapper;
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
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 计算周期性登记类型收件量任务
 *
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/6/29
 */
public class ComputeZqxDjlxSjlTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputeZqxDjlxSjlTask.class.getName());
    /**
     * 工作量信息： 线程共享变量，计算并设置 BdcGzltjXmxxVO 中的 sjl（收件量）参数
     */
    private List<BdcZqxGzltjXmxxVO> bdcZqxGzltjXmxxVOList;
    /**
     * 工作量统计QO对象
     */
    private final GzltjQO gzltjQO;
    /**
     * 工作量统计Mapper
     */
    private final BdcGzltjXmxxMapper bdcGzltjXmxxMapper;

    /**
     * 配置的合并流程的工作流定义IDs
     */
    private final String hbGzldyids;

    public ComputeZqxDjlxSjlTask(GzltjQO gzltjQO, List<BdcZqxGzltjXmxxVO> bdcZqxGzltjXmxxVOList, String hbGzldyids, BdcGzltjXmxxMapper bdcGzltjXmxxMapper){
        this.gzltjQO = gzltjQO;
        this.bdcZqxGzltjXmxxVOList = bdcZqxGzltjXmxxVOList;
        this.bdcGzltjXmxxMapper = bdcGzltjXmxxMapper;
        this.hbGzldyids = hbGzldyids;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();

        // 1、查询对象拷贝处理，防止其他线程修改查询参数
        GzltjQO param = new GzltjQO();
        BeanUtils.copyProperties(gzltjQO, param);
        param.setProcess(hbGzldyids);

        // 1、根据统计类型，获取人员办件量信息
        List<BdcGzltjXmxxDTO> bdcGzltjXmxxDTOList = null;
        // 统计维度 + 统计类型
        String lb = param.getDimension() + param.getTjlx();
        switch (lb){
            case CommonConstantUtils.GZLTJ_TJWD_RY + Constants.GZLTJ_TJLX_XM:
                bdcGzltjXmxxDTOList = this.bdcGzltjXmxxMapper.countRyDjlxSjlxxByXmid(param);
                break;
            case CommonConstantUtils.GZLTJ_TJWD_RY + Constants.GZLTJ_TJLX_SLBH:
                bdcGzltjXmxxDTOList = this.bdcGzltjXmxxMapper.countRyDjlxSjlxxByGzlslid(param);
                break;
            case CommonConstantUtils.GZLTJ_TJWD_BM + Constants.GZLTJ_TJLX_XM:
                bdcGzltjXmxxDTOList = this.bdcGzltjXmxxMapper.countBmDjlxSjlxxByXmid(param);
                break;
            case CommonConstantUtils.GZLTJ_TJWD_BM + Constants.GZLTJ_TJLX_SLBH:
                bdcGzltjXmxxDTOList = this.bdcGzltjXmxxMapper.countBmDjlxSjlxxByGzlslid(param);
                break;
            default:
                break;
        }

        if(CollectionUtils.isNotEmpty(bdcGzltjXmxxDTOList)){
            Map<String, List<BdcGzltjXmxxDTO>> resultMap = this.convertMap(param.getDimension(), bdcGzltjXmxxDTOList);
            if(MapUtils.isNotEmpty(resultMap)){
                Iterator<BdcZqxGzltjXmxxVO> bdcZqxGzltjXmxxVOIterator = bdcZqxGzltjXmxxVOList.iterator();
                while (bdcZqxGzltjXmxxVOIterator.hasNext()) {
                    BdcZqxGzltjXmxxVO bdcZqxGzltjXmxxVO = bdcZqxGzltjXmxxVOIterator.next();
                    String key =bdcZqxGzltjXmxxVO.getTjKey(param.getDimension());
                    if(StringUtils.isNotBlank(key) && resultMap.containsKey(key) && CollectionUtils.isNotEmpty(resultMap.get(key))){
                        Map<Integer, Integer> djlxMap = resultMap.get(key).stream().filter(t-> Objects.nonNull(t.getDjlx()))
                                .collect(Collectors.toMap(BdcGzltjXmxxDTO::getDjlx, BdcGzltjXmxxDTO::getNum));
                        if(MapUtils.isNotEmpty(djlxMap)){
                            bdcZqxGzltjXmxxVO.addDjlxslMap(djlxMap);
                        }
                    }
                }
            }
        }
        LOGGER.info("计算周期性登记类型收件量任务结束，耗时：{}， 查询参数：{}" , System.currentTimeMillis() - startTime, JSON.toJSONString(param));
    }


    /**
     * 将缮证量信息转换成 Map 对象 ( Key:人员ID 或部门代码, Value: 统计信息 List<BdcGzltjXmxxDTO>)
     */
    private Map<String, List<BdcGzltjXmxxDTO>> convertMap(String tjwd, List<BdcGzltjXmxxDTO> bdcGzltjXmxxDTOList){
        if(CommonConstantUtils.GZLTJ_TJWD_BM.equals(tjwd)){
            return bdcGzltjXmxxDTOList.stream().filter(t-> StringUtils.isNotBlank(t.getBmdm()))
                    .collect(Collectors.groupingBy(BdcGzltjXmxxDTO::getBmdm));
        }
        if(CommonConstantUtils.GZLTJ_TJWD_RY.equals(tjwd)){
            return bdcGzltjXmxxDTOList.stream().filter(t-> StringUtils.isNotBlank(t.getRyid()))
                    .collect(Collectors.groupingBy(BdcGzltjXmxxDTO::getRyid));
        }
        return null;
    }

}
