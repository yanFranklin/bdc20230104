package cn.gtmap.realestate.inquiry.service.huaian.thread;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcGzltjXmxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.GzltjQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcZhcxFeignService;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcRyGzltjXmxxVO;
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
 * 计算人员复审量任务
 *
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/6/29
 */
public class ComputeZqxFslTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputeZqxFslTask.class.getName());

    private static final String jdmc = "复审";

    /**
     * 工作量信息： 线程共享变量，计算并设置 BdcGzltjXmxxVO 中的 fsl（复审量）参数
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

    private final BdcZhcxFeignService bdcZhcxFeignService;

    public ComputeZqxFslTask(GzltjQO gzltjQO,
                             List<BdcZqxGzltjXmxxVO> bdcZqxGzltjXmxxVOList,
                             BdcZhcxFeignService bdcZhcxFeignService,
                             BdcGzltjXmxxMapper bdcGzltjXmxxMapper) {
        this.gzltjQO = gzltjQO;
        this.bdcZqxGzltjXmxxVOList = bdcZqxGzltjXmxxVOList;
        this.bdcZhcxFeignService = bdcZhcxFeignService;
        this.bdcGzltjXmxxMapper = bdcGzltjXmxxMapper;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        // 1、查询对象拷贝处理，防止其他线程修改查询参数
        GzltjQO param = new GzltjQO();
        BeanUtils.copyProperties(gzltjQO, param);
        param.setJdmc(jdmc);
        // 2、根据统计维度
        List<BdcGzltjXmxxDTO> szlList = null;
        if (CommonConstantUtils.GZLTJ_TJWD_RY.equals(param.getDimension())
                && CommonConstantUtils.GZLTJ_TJLX_XM.equals(param.getTjlx())) {
            szlList = this.bdcGzltjXmxxMapper.countRyFslByXmid(param);
        } else if (CommonConstantUtils.GZLTJ_TJWD_RY.equals(param.getDimension())
                && CommonConstantUtils.GZLTJ_TJLZ_JS.equals(param.getTjlx())) {
            szlList = this.bdcGzltjXmxxMapper.countRyFslByGzlslid(param);
        } else if (CommonConstantUtils.GZLTJ_TJWD_BM.equals(param.getDimension())
                && CommonConstantUtils.GZLTJ_TJLX_XM.equals(param.getTjlx())) {
            szlList = this.bdcGzltjXmxxMapper.countBmFslByXmid(param);
        } else {
            szlList = this.bdcGzltjXmxxMapper.countBmFslByGzlslid(param);
        }

        // 3、将人员办件量信息转换成 Map 对象 ( Key:人员ID, Value: 数量 )
        if (CollectionUtils.isNotEmpty(szlList)) {
            Map<String, Integer> resultMap = this.convertMap(param.getDimension(), szlList);
            if (MapUtils.isNotEmpty(resultMap)) {
                // 赋值缮证量
                Iterator<BdcZqxGzltjXmxxVO> bdcGzltjXmxxVOIterator = bdcZqxGzltjXmxxVOList.iterator();
                while (bdcGzltjXmxxVOIterator.hasNext()) {
                    BdcZqxGzltjXmxxVO bdcZqxGzltjXmxxVO = bdcGzltjXmxxVOIterator.next();
                    String key = bdcZqxGzltjXmxxVO.getTjKey(param.getDimension());
                    if (StringUtils.isNotBlank(key) && resultMap.containsKey(key)) {
                        this.handlerGzltjxx(resultMap.get(key), bdcZqxGzltjXmxxVO);
                    }
                }
            }
        }
        LOGGER.info("计算周期性复审量结束，耗时：{},  查询参数：{}", System.currentTimeMillis() - startTime, JSON.toJSONString(param));
    }

    /**
     * 设置缮证的工作量统计信息
     */
    private void handlerGzltjxx(Integer num, BdcZqxGzltjXmxxVO bdcZqxGzltjXmxxVO) {
        bdcZqxGzltjXmxxVO.setFsl(num);
    }

    /**
     * 将缮证量信息转换成 Map 对象 ( Key:人员ID 或部门代码, Value: 打印量 )
     */
    private Map<String, Integer> convertMap(String tjwd, List<BdcGzltjXmxxDTO> bdcGzltjXmxxDTOList) {
        if (CommonConstantUtils.GZLTJ_TJWD_BM.equals(tjwd)) {
            return bdcGzltjXmxxDTOList
                    .stream()
                    .filter(t -> StringUtils.isNotBlank(t.getBmdm()))
                    .collect(Collectors.toMap(BdcGzltjXmxxDTO::getBmdm, BdcGzltjXmxxDTO::getNum, (k1, k2) -> k1));
        }
        if (CommonConstantUtils.GZLTJ_TJWD_RY.equals(tjwd)) {
            return bdcGzltjXmxxDTOList.stream().filter(t -> StringUtils.isNotBlank(t.getRyid()))
                    .collect(Collectors.toMap(BdcGzltjXmxxDTO::getRyid, BdcGzltjXmxxDTO::getNum, (k1, k2) -> k1));
        }
        return null;
    }
}
