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

import java.util.*;
import java.util.stream.Collectors;

/**
 * 计算周期性权利类型收件量任务
 * <p>统计权利类型为 37：抵押， 92：居住权的收件量信息</p>
 *
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/7/22
 */
public class ComputeZqxQllxSjlTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputeZqxQllxSjlTask.class.getName());

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

    public ComputeZqxQllxSjlTask(GzltjQO gzltjQO, List<BdcZqxGzltjXmxxVO> bdcZqxGzltjXmxxVOList, String hbGzldyids, BdcGzltjXmxxMapper bdcGzltjXmxxMapper){
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
        // 添加周期性收件量统计的权利类型： 抵押权、居住权
        param.setQllxList(Arrays.asList(CommonConstantUtils.QLLX_DYAQ_DM, CommonConstantUtils.QLLX_JZQ));
        param.setProcess(hbGzldyids);

        List<BdcGzltjXmxxDTO> bdcGzltjXmxxDTOList = null;
        // 统计维度 + 统计类型
        String lb = param.getDimension() + param.getTjlx();
        switch (lb){
            // 人员按项目统计收件量
            case CommonConstantUtils.GZLTJ_TJWD_RY + Constants.GZLTJ_TJLX_XM:
                bdcGzltjXmxxDTOList = this.bdcGzltjXmxxMapper.countRyQllxSjlxxByXmid(param);
                break;
            // 人员按流程统计收件量
            case CommonConstantUtils.GZLTJ_TJWD_RY + Constants.GZLTJ_TJLX_SLBH:
                bdcGzltjXmxxDTOList = this.bdcGzltjXmxxMapper.countRyQllxSjlxxByGzlslid(param);
                break;
            // 部门按项目统计收件量
            case CommonConstantUtils.GZLTJ_TJWD_BM + Constants.GZLTJ_TJLX_XM:
                bdcGzltjXmxxDTOList = this.bdcGzltjXmxxMapper.countBmQllxSjlxxByXmid(param);
                break;
            // 部门按流程统计收件量
            case CommonConstantUtils.GZLTJ_TJWD_BM + Constants.GZLTJ_TJLX_SLBH:
                bdcGzltjXmxxDTOList = this.bdcGzltjXmxxMapper.countBmQllxSjlxxByGzlslid(param);
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
                        Map<Integer, Integer> qllxMap = resultMap.get(key).stream().filter(t-> Objects.nonNull(t.getQllx()))
                                .collect(Collectors.toMap(BdcGzltjXmxxDTO::getQllx, BdcGzltjXmxxDTO::getNum));
                        if(MapUtils.isNotEmpty(qllxMap)){
                            bdcZqxGzltjXmxxVO.addDjlxslMap(qllxMap);
                        }
                    }
                }
            }
        }
        LOGGER.info("计算周期性权利类型收件量任务结束，耗时：{}， 查询参数：{}" , System.currentTimeMillis() - startTime, JSON.toJSONString(param));
    }


    /**
     * 将收件量信息转换成 Map 对象 ( Key:人员ID 或部门代码, Value: 统计信息 List<BdcGzltjXmxxDTO>)
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
