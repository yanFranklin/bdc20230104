package cn.gtmap.realestate.exchange.service.impl.inf.yancheng;

import cn.gtmap.realestate.common.core.domain.BdcGgDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.service.feign.config.BdcXtGgFeignService;
import cn.gtmap.realestate.common.core.vo.etl.BdcXtggVO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.service.inf.yancheng.BdcXtggService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2020-04-21
 * @description (盐城) 定时任务系统公告服务
 */
@Service("bdcXtggServiceImpl")
public class BdcXtggServiceImpl implements BdcXtggService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcXtggServiceImpl.class);

    private static final String SUCCESS_XMIDS_KEY = "successXmids";
    private static final String ALL_XMIDS_KEY = "allXmids";
    private static final String RESPONSE_DATA = "detail";

    @Autowired
    private BdcXmMapper bdcXmMapper;

    @Autowired
    private BdcXtGgFeignService bdcXtGgFeignService;

    /**
     * 盐城_定时任务生成系统公告服务
     *
     * @param bdcXtggVO
     * @return CommonResponse 返回参数
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    public CommonResponse<Map<String,Object>> generateXtgg(BdcXtggVO bdcXtggVO) {
        LOGGER.info("生成公告入参:{}", JSON.toJSONString(bdcXtggVO));
        //校验入参
        if (bdcXtggVO == null || bdcXtggVO.getStartTime() == null || bdcXtggVO.getEndTime() == null || CollectionUtils.isEmpty(bdcXtggVO.getSply()) || CollectionUtils.isEmpty(bdcXtggVO.getGglx())) {
            return CommonResponse.fail("入参缺失！");
        }
        List<BdcGgDO> resultList = new ArrayList<>();
        List<String> xmids = new ArrayList<>();
        List<String> successXmids = new ArrayList<>();
        Map<String,Object> resutlMap = new HashMap<>(4);
        bdcXtggVO.getSply().forEach(sply -> {
            bdcXtggVO.getGglx().forEach(gglx -> {
                //组织项目数据
                List<BdcXmDO> xmxxList = bdcXmMapper.getXmxxByDjsjAndSplyAndGzldyIdWithoutBdcGg(bdcXtggVO.getStartTime(),bdcXtggVO.getEndTime(),sply,bdcXtggVO.getGzldyId());
                if (CollectionUtils.isNotEmpty(xmxxList)) {
                    xmids.addAll(xmxxList.stream().collect(ArrayList::new, (list, item) -> list.add(item.getXmid()), ArrayList::addAll));
                    //调用config工程生成公告
                    List<BdcGgDO> bdcGgDOS = bdcXtGgFeignService.generateXtggBySqlPl(xmxxList, sply, gglx);
                    if (CollectionUtils.isNotEmpty(bdcGgDOS)) {
                        resultList.addAll(bdcGgDOS);
                        ArrayList<String> collect = bdcGgDOS.stream().collect(ArrayList::new, (list, item) -> list.add(item.getXmid()), ArrayList::addAll);
                        successXmids.addAll(collect);
                    }
                }
            });
        });
        resutlMap.put(ALL_XMIDS_KEY,xmids);
        resutlMap.put(SUCCESS_XMIDS_KEY,successXmids);
        resutlMap.put(RESPONSE_DATA,resultList);
        return CommonResponse.ok(resutlMap);
    }

}
