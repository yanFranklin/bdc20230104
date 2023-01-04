package cn.gtmap.realestate.inquiry.service.huaian.thread;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcGzltjXmxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.GzltjQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcZhcxFeignService;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcRyGzltjXmxxVO;
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
 * 计算周期性打印量任务
 *
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/6/29
 */
public class ComputeZqxPrintTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputeZqxPrintTask.class.getName());
    /**
     * 工作量信息： 线程共享变量，计算并设置 BdcGzltjXmxxVO 中的 打印量参数
     */
    private List<BdcZqxGzltjXmxxVO> bdcZqxGzltjXmxxVOList;
    /**
     * 工作量统计QO对象
     */
    private final GzltjQO gzltjQO;

    private final String dylx;
    /**
     * 不动产综合查询日志接口
     */
    private final BdcZhcxFeignService bdcZhcxFeignService;

    public ComputeZqxPrintTask(GzltjQO gzltjQO, List<BdcZqxGzltjXmxxVO> bdcZqxGzltjXmxxVOList, String dylx, BdcZhcxFeignService bdcZhcxFeignService){
        this.gzltjQO = gzltjQO;
        this.bdcZqxGzltjXmxxVOList = bdcZqxGzltjXmxxVOList;
        this.dylx = dylx;
        this.bdcZhcxFeignService = bdcZhcxFeignService;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        // 1、查询对象拷贝处理，防止其他线程修改查询参数
        GzltjQO param = new GzltjQO();
        BeanUtils.copyProperties(gzltjQO, param);
        param.setDylx(dylx);

        // 2、根据统计维度，获取打印量信息
        List<BdcGzltjXmxxDTO> printList = null;
        if(CommonConstantUtils.GZLTJ_TJWD_RY.equals(param.getDimension())){
            printList = bdcZhcxFeignService.countRyPrintxx(param);
        }else if(CommonConstantUtils.GZLTJ_TJWD_BM.equals(param.getDimension())){
            printList = bdcZhcxFeignService.countBmPrintxx(param);
        }

        // 3、将人员办件量信息转换成 Map 对象 ( Key:人员ID, Value: 打印量 )
        if(CollectionUtils.isNotEmpty(printList)){
            Map<String, Integer> printMap = this.convertMap(param.getDimension(), printList);
            if(MapUtils.isNotEmpty(printMap)){
                Iterator<BdcZqxGzltjXmxxVO> bdcZqxGzltjXmxxVOIterator = bdcZqxGzltjXmxxVOList.iterator();
                while (bdcZqxGzltjXmxxVOIterator.hasNext()) {
                    BdcZqxGzltjXmxxVO bdcZqxGzltjXmxxVO = bdcZqxGzltjXmxxVOIterator.next();
                    switch(dylx){
                        case Constants.GZLTJ_DYLX_YFWFZM:
                            bdcZqxGzltjXmxxVO.setYfwfzmsl(this.getPrintCount(bdcZqxGzltjXmxxVO.getTjKey(param.getDimension()), printMap));
                            break;
                        case Constants.GZLTJ_DYLX_QSZM:
                            bdcZqxGzltjXmxxVO.setQszmsl(this.getPrintCount(bdcZqxGzltjXmxxVO.getTjKey(param.getDimension()), printMap));
                            break;
                        case Constants.GZLTJ_DYLX_BDCDJB:
                            bdcZqxGzltjXmxxVO.setDjbsl(this.getPrintCount(bdcZqxGzltjXmxxVO.getTjKey(param.getDimension()), printMap));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        LOGGER.info("计算周期性打印量结束，耗时：{}，查询参数为：{}" , System.currentTimeMillis() - startTime, JSON.toJSONString(param));
    }

    /**
     * 将打印量信息转换成 Map 对象 ( Key:人员ID 或部门代码, Value: 打印量 )
     */
    private Map<String, Integer> convertMap(String tjwd, List<BdcGzltjXmxxDTO> printList){
        if(CommonConstantUtils.GZLTJ_TJWD_BM.equals(tjwd)){
            return printList.stream().filter(t-> StringUtils.isNotBlank(t.getBmdm()))
                    .collect(Collectors.toMap(BdcGzltjXmxxDTO::getBmdm, BdcGzltjXmxxDTO::getNum, (k1,k2)->k1));
        }
        if(CommonConstantUtils.GZLTJ_TJWD_RY.equals(tjwd)){
            return printList.stream().filter(t-> StringUtils.isNotBlank(t.getRyid()))
                    .collect(Collectors.toMap(BdcGzltjXmxxDTO::getRyid, BdcGzltjXmxxDTO::getNum, (k1,k2)->k1));
        }
        return null;
    }

    // 获取打印量
    private Integer getPrintCount(String ryid,  Map<String, Integer> printMap){
        if(StringUtils.isNotBlank(ryid) && printMap.containsKey(ryid)){
            return printMap.get(ryid);
        }else{
           return 0;
        }
    }
}
