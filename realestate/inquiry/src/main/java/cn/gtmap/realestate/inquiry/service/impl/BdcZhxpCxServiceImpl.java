package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.inquiry.core.entity.BdcZxywDO;
import cn.gtmap.realestate.inquiry.core.mapper.BdcZhxpCxMapper;
import cn.gtmap.realestate.inquiry.service.BdcZhxpCxService;
import cn.gtmap.realestate.inquiry.util.GetDateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/4
 * @description
 */
@Service
public class BdcZhxpCxServiceImpl implements BdcZhxpCxService {
    @Autowired
    private BdcZhxpCxMapper bdcZhxpCxMapper;
    @Autowired
    private EntityMapper entityMapper;

    @Override
    public List<Map> listBdcZhxpDqhj(String zxmc) {
        List<Map> dqhjList = new ArrayList<>();
        if (StringUtils.isNotEmpty(zxmc)) {
            dqhjList = bdcZhxpCxMapper.listBdcZhxpDqhj(zxmc);
        }
        return dqhjList;
    }

    @Override
    public List<Map> listBdcZhxpDdhj(String zxmc) {
        List<Map> resultList = new ArrayList<>();

        if (StringUtils.isNoneBlank(zxmc)) {
            Example example = new Example(BdcZxywDO.class);
            example.createCriteria().andEqualTo("zxmc", zxmc);
            List<BdcZxywDO> bdcZxywDOList = entityMapper.selectByExample(example);

            if (CollectionUtils.isNotEmpty(bdcZxywDOList)) {
                for (BdcZxywDO bdcZxywDO : bdcZxywDOList) {
                    Map resultMap = new HashMap();
                    resultMap.put("YWMC", bdcZxywDO.getYwmc());

                    Map map = new HashMap();
                    map.put("zxmc", zxmc);
                    map.put("ywmc", bdcZxywDO.getYwmc());
                    map.put("starttime", GetDateUtils.getStartTime());
                    map.put("endtime", GetDateUtils.getEndTime());
                    resultMap.put("DATA", bdcZhxpCxMapper.listBdcZhxpDdhjHjh(map));

                    resultList.add(resultMap);
                }
            }
        }
        return resultList;
    }

    /**
     * @description 每天定时将窗口信息中的当前呼叫清空
     * 夜里24点执行
     **/
    @Scheduled(cron = "0 0 23 * * ?")
    public void updateCkxx() {
        bdcZhxpCxMapper.updateCkxx();
    }
}
