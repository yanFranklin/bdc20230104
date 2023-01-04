package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.inquiry.core.mapper.BdcZhdpCxMapper;
import cn.gtmap.realestate.inquiry.service.BdcZhdpCxService;
import cn.gtmap.realestate.inquiry.util.GetDateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/10
 * @description
 */
@Service
public class BdcZhdpCxServiceImpl implements BdcZhdpCxService {
    @Autowired
    private BdcZhdpCxMapper bdcZhdpCxMapper;

    @Override
    public List<Map> listBdcZhdp() {
        Map map = new HashMap();
        map.put("starttime", GetDateUtils.getStartTime());
        map.put("endtime", GetDateUtils.getEndTime());

        List<Map> resultList = bdcZhdpCxMapper.listBdcZhdp(map);
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (Map result : resultList) {
                if (StringUtils.isNoneBlank(MapUtils.getString(result, "DDSJ"))) {
                    if (MapUtils.getInteger(result, "DDSJ") != 0) {
                        result.put("DDSJ", MapUtils.getString(result, "DDSJ") + "分钟");
                    } else {
                        result.put("DDSJ", "-");
                    }
                    if (MapUtils.getInteger(result, "CKS") == 0) {
                        result.put("CKS", "-");
                    }
                }
            }
        }

        return resultList;
    }
}
