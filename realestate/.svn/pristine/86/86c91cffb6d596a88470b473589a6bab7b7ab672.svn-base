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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 计算周期性收件量任务
 *
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/7/1
 */
public class ComputeZqxSjlTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputeZqxSjlTask.class.getName());
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

    public ComputeZqxSjlTask(GzltjQO gzltjQO, List<BdcZqxGzltjXmxxVO> bdcZqxGzltjXmxxVOList, BdcGzltjXmxxMapper bdcGzltjXmxxMapper){
        this.gzltjQO = gzltjQO;
        this.bdcZqxGzltjXmxxVOList = bdcZqxGzltjXmxxVOList;
        this.bdcGzltjXmxxMapper = bdcGzltjXmxxMapper;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        // 1、根据统计类型，获取人员办件量信息
        List<BdcGzltjXmxxDTO> bdcGzltjXmxxDTOList = null;
        // 统计维度 + 统计类型
        String lb = gzltjQO.getDimension() + gzltjQO.getTjlx();
        switch (lb){
            case CommonConstantUtils.GZLTJ_TJWD_RY + Constants.GZLTJ_TJLX_XM:
                bdcGzltjXmxxDTOList = this.bdcGzltjXmxxMapper.countRyBjlByXmid(gzltjQO);
                break;
            case CommonConstantUtils.GZLTJ_TJWD_RY + Constants.GZLTJ_TJLX_SLBH:
                bdcGzltjXmxxDTOList = this.bdcGzltjXmxxMapper.countRyBjlByGzlslid(gzltjQO);
                break;
            case CommonConstantUtils.GZLTJ_TJWD_BM + Constants.GZLTJ_TJLX_XM:
                bdcGzltjXmxxDTOList = this.bdcGzltjXmxxMapper.countBmBjlByXmid(gzltjQO);
                break;
            case CommonConstantUtils.GZLTJ_TJWD_BM + Constants.GZLTJ_TJLX_SLBH:
                bdcGzltjXmxxDTOList = this.bdcGzltjXmxxMapper.countBmBjlByGzlslid(gzltjQO);
                break;
            default:
                break;
        }

        // 2、将办件量信息转换成 Map 对象 ( Key:人员ID, Value: 收件量 )
        if(CollectionUtils.isNotEmpty(bdcGzltjXmxxDTOList)){
            this.addSjlxx(gzltjQO.getDimension(), bdcGzltjXmxxDTOList);
        }
        LOGGER.info("计算周期性收件量任务结束，耗时：{}， 查询参数为：{}" , System.currentTimeMillis() - startTime, JSON.toJSONString(gzltjQO));
    }

    /**
     * 添加受理办件量信息
     */
    private void addSjlxx(String tjwd, List<BdcGzltjXmxxDTO> bdcGzltjXmxxDTOList){
        if(CommonConstantUtils.GZLTJ_TJWD_RY.equals(tjwd)){
            Map<String, Integer> resultMap = bdcGzltjXmxxDTOList.stream().filter(t-> StringUtils.isNotBlank(t.getRyid()))
                    .collect(Collectors.toMap(BdcGzltjXmxxDTO::getRyid,BdcGzltjXmxxDTO::getNum,(k1,k2)->k1));
            if(MapUtils.isNotEmpty(resultMap)){
                Iterator<BdcZqxGzltjXmxxVO> bdcZqxGzltjXmxxVOIterator = bdcZqxGzltjXmxxVOList.iterator();
                while (bdcZqxGzltjXmxxVOIterator.hasNext()) {
                    BdcZqxGzltjXmxxVO bdcZqxGzltjXmxxVO = bdcZqxGzltjXmxxVOIterator.next();
                    if(StringUtils.isNotBlank(bdcZqxGzltjXmxxVO.getRyid()) && resultMap.containsKey(bdcZqxGzltjXmxxVO.getRyid())){
                        bdcZqxGzltjXmxxVO.setSjl(resultMap.get(bdcZqxGzltjXmxxVO.getRyid()));
                    }
                }
            }
        }else if(CommonConstantUtils.GZLTJ_TJWD_BM.equals(tjwd)){
            Map<String, Integer> resultMap = bdcGzltjXmxxDTOList.stream().filter(t-> StringUtils.isNotBlank(t.getBmdm()))
                    .collect(Collectors.toMap(BdcGzltjXmxxDTO::getBmdm,BdcGzltjXmxxDTO::getNum,(k1,k2)->k1));

            if(MapUtils.isNotEmpty(resultMap)){
                Iterator<BdcZqxGzltjXmxxVO> bdcZqxGzltjXmxxVOIterator = bdcZqxGzltjXmxxVOList.iterator();
                while (bdcZqxGzltjXmxxVOIterator.hasNext()) {
                    BdcZqxGzltjXmxxVO bdcZqxGzltjXmxxVO = bdcZqxGzltjXmxxVOIterator.next();
                    if(StringUtils.isNotBlank(bdcZqxGzltjXmxxVO.getBmdm()) && resultMap.containsKey(bdcZqxGzltjXmxxVO.getBmdm())){
                        bdcZqxGzltjXmxxVO.setSjl(resultMap.get(bdcZqxGzltjXmxxVO.getBmdm()));
                    }
                }
            }
        }
    }
}
